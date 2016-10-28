package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import common.PublicationBank;
import common.Purchase;

public class Moderator implements MessageListener{

	private ConnectionFactory connectionFactory;
	private Destination destinationResModQueue;
	private Destination destinationCFPTopic;
	private Destination destinationBankQueue;
	private Destination destinationDecisionQueue;
	private Destination destinationPurchaseDecisionQueue;

	public Moderator() throws NamingException{
		this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
		this.destinationResModQueue = InitialContext.doLookup("jms/queue/ResModQueue");
		this.destinationCFPTopic = InitialContext.doLookup("jms/topic/CFPTopic");
		this.destinationBankQueue = InitialContext.doLookup("jms/queue/BankQueue");
		this.destinationDecisionQueue = InitialContext.doLookup("jms/queue/DecisionQueue");
		this.destinationPurchaseDecisionQueue = InitialContext.doLookup("jms/queue/PurchaseDecisionQueue");
	}

	public static void main(String[] args) throws NamingException, JMSException, IOException, InterruptedException {
		Moderator moderator = new Moderator();
		moderator.checkMessages(moderator);
	}
	
	@Override
	public void onMessage(Message msg){
		ObjectMessage textMsg = (ObjectMessage) msg;
		try{
			System.out.println();
			System.out.println("========== Listener: Bank decision received! ==========");
			System.out.println(textMsg.getObject());
			System.out.println("=======================================================");
			System.out.println();
			
			sendFulltext((Purchase) textMsg.getObject());
		}
		catch (JMSException e){
			e.printStackTrace();
		}
	}
	
	private void checkMessages(Moderator moderator) throws InterruptedException{
		//--- Sync
		Thread thread_sync = new Thread(new Runnable() {
			public void run(){
				System.out.println("----------------------------------------------------");
				System.out.println("Moderator Running.");
				System.out.println("----------------------------------------------------");
				
				while(true){
					try {
						moderator.moderate();
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});  
		thread_sync.start();
		
		//--- ASync
		try (JMSContext context = connectionFactory.createContext("john", "!1secret");){
			JMSConsumer consumer = context.createConsumer(destinationDecisionQueue);
			consumer.setMessageListener(this);
			thread_sync.join(); //Wait for the other thread
		}
		catch (Exception re){
			re.printStackTrace();
		}
	}
	
	public void moderate() throws JMSException{
		ObjectMessage msg = null;
		try (JMSContext context = connectionFactory.createContext("john", "!1secret");){

			JMSConsumer consumer = context.createConsumer(destinationResModQueue);
			msg = (ObjectMessage) consumer.receive();

			JMSProducer producer = context.createProducer();

			//--List publication titles
			if(msg.getJMSType().equals("1")){
				System.out.println("========== New Message - List publications ==========");
				
				ArrayList<String> list = accessDB();

				ObjectMessage objectMessage = context.createObjectMessage();
				objectMessage.setObject(list);

				producer.send(msg.getJMSReplyTo(), objectMessage);
				System.out .println("Sent reply to " + msg.getJMSReplyTo());
				System.out.println("=====================================================");
			}

			//--Call for Papers
			else if(msg.getJMSType().equals("2")){
				System.out.println("========== New Message - CFP ==========");
				
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);

				System.out.println("CFP with topic '" + msg.getObject() + "'.");
				boolean proceed = false;
				String decision = "";
				String d = "";

				while(!proceed){
					System.out.print("Approve (Y) or Reject (N)? ");
					d = scan.nextLine();
					if(d.equals("Y")){
						decision = "approved"; 
						proceed = true;
					}
					else if(d.equals("N")){
						decision = "rejected";
						proceed = true;
					}
				}
				
				producer.send(msg.getJMSReplyTo(), decision);
				System.out .println("Sent reply to " + msg.getJMSReplyTo());
				
				if(proceed && d.equals("Y")){
					ObjectMessage objectMessage = context.createObjectMessage();
					
					String cfp = "========== Listener: New CFP received! ========== \nTopic: " + msg.getObject() 
							+ "\n=================================================";
					
					objectMessage.setObject(cfp);
					objectMessage.setJMSType("cfp");
					
					producer.send(destinationCFPTopic, objectMessage);
					System.out.println("Sent CFP for all researchers.");
				}
				System.out.println("=======================================");
			}

			//--Purchase Text
			else if(msg.getJMSType().equals("3")){
				System.out.println("========== New Message - Purchase ==========");
				
				Purchase purchase = (Purchase) msg.getObject();
				
				long cost =  getPublicationCost(purchase.getTopic());
				
				if(cost!=-999){ //---If publication exists
					producer.send(msg.getJMSReplyTo(), "received");
					System.out .println("Sent reply to " + msg.getJMSReplyTo());
					
					purchase.setCost((int) cost);
					
					Destination tmp = context.createTemporaryQueue();
					
					ObjectMessage msgtobank = context.createObjectMessage();
					msgtobank.setObject(purchase);
					msgtobank.setJMSType(msg.getJMSType());
					msgtobank.setJMSReplyTo(tmp);
					
					producer.send(destinationBankQueue,msgtobank);
					
					JMSConsumer cons = context.createConsumer(tmp);
					String answer = cons.receiveBody(String.class);
					
					System.out.println("Purchase order " + answer + " by the bank.");
				}
				else{
					producer.send(msg.getJMSReplyTo(), "not accepted. No such publication available");
					System.out .println("Sent reply to " + msg.getJMSReplyTo());
				}
				System.out.println("============================================");
			}

			context.close();
		}
		catch (JMSRuntimeException re){
			re.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void sendFulltext(Purchase purchase) throws JMSException{
		try (JMSContext context = connectionFactory.createContext("john", "!1secret");){
			JMSProducer producer = context.createProducer();
			
			if(purchase.getState().equals("accepted")){
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceforBank");
				EntityManager em = emf.createEntityManager();
				
				String query = "SELECT s FROM PublicationBank s WHERE s.title='" + purchase.getTopic() + "'";
				
				List<PublicationBank> mylist = (List<PublicationBank>) em.createQuery(query).getResultList();
				
				em.close();
				emf.close();
				
				ObjectMessage fulltextpurchase = context.createObjectMessage();
				fulltextpurchase.setObject(mylist.get(0));
				fulltextpurchase.setJMSType("fulltext");
				fulltextpurchase.setObjectProperty("client", purchase.getCardname());
				
				producer.send(destinationPurchaseDecisionQueue,fulltextpurchase);
				
				Date date = new Date();
				ObjectMessage receipt = context.createObjectMessage();
				receipt.setObject("========== Receipt received! ========== \nTopic: " + mylist.get(0).getTitle()
						+ "\nCost: " + purchase.getCost() + "€ \nDate: " + date.toString() + "\n=================================================");
				receipt.setObjectProperty("client", purchase.getCardname());
				receipt.setJMSType("receipt");
				
				producer.send(destinationPurchaseDecisionQueue,receipt);
			}
			else if(purchase.getState().equals("pending")){
				ObjectMessage bankreport = context.createObjectMessage();
				bankreport.setObject("========== Bank Report received! ========== \nPayment Pending \n===========================================");
				bankreport.setObjectProperty("client", purchase.getCardname());
				bankreport.setJMSType("bankreport");
				
				producer.send(destinationPurchaseDecisionQueue,bankreport);
			}
			else if(purchase.getState().equals("rejected")){
				ObjectMessage bankreport = context.createObjectMessage();
				bankreport.setObject("========== Bank Report received! ========== \nPayment Rejected \n===========================================");
				bankreport.setObjectProperty("client", purchase.getCardname());
				bankreport.setJMSType("bankreport");
				
				producer.send(destinationPurchaseDecisionQueue,bankreport);
			}
			System.out.println("Sent purchase decision to Client.");
			System.out.println("=====================================================");
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> accessDB(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceforBank");
		EntityManager em = emf.createEntityManager();
		
		String query = "SELECT s FROM PublicationBank s";
		
		List<PublicationBank> mylist = (List<PublicationBank>) em.createQuery(query).getResultList();
		
		em.close();
		emf.close();
		
		ArrayList<String> titles = new ArrayList<String>();
		for(int i=0;i<mylist.size();i++){
			titles.add(mylist.get(i).getTitle());
		}
		Collections.sort(titles);
		
		return titles;
	}
	
	@SuppressWarnings("unchecked")
	public long getPublicationCost(String title){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceforBank");
		EntityManager em = emf.createEntityManager();
		
		String query = "SELECT s FROM PublicationBank s WHERE s.title='" + title + "'";
		
		List<PublicationBank> mylist = (List<PublicationBank>) em.createQuery(query).getResultList();
		
		em.close();
		emf.close();
		
		if(mylist.size()>0) return mylist.get(0).getCost();
		else return -999; //In case there is no such publication
	}
}