package main;

import java.util.ArrayList;
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

import common.ClientBank;
import common.Deposit;
import common.Purchase;

public class BankManager implements MessageListener{

	private ConnectionFactory connectionFactory;
	private Destination destinationBankQueue;
	private Destination destinationDecisionQueue;
	private Destination destinationDepositQueue;
	private ArrayList<Purchase> acceptedpurchase;
	private ArrayList<Purchase> pendingpurchase;
	private ArrayList<Long> pendingtime;
	private long maxpendingtime;
	private ArrayList<Purchase> rejectedpurchase;
	
	public BankManager() throws NamingException {
		this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
		this.destinationBankQueue = InitialContext.doLookup("jms/queue/BankQueue");
		this.destinationDecisionQueue = InitialContext.doLookup("jms/queue/DecisionQueue");
		this.destinationDepositQueue = InitialContext.doLookup("jms/queue/DepositQueue");
		this.acceptedpurchase = new ArrayList<Purchase>(); //List of accepted purchases
		this.pendingpurchase = new ArrayList<Purchase>(); //List of pending purchases
		this.rejectedpurchase = new ArrayList<Purchase>(); //List of rejected purchases
		this.pendingtime = new ArrayList<Long>(); //List of pending time of pending purchases
		this.maxpendingtime = 60000; //Maximum pending time in milliseconds
	}

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		BankManager bank = new BankManager();
		bank.checkMessages(bank);
	}
	
	@Override
	public void onMessage(Message msg){
		ObjectMessage textMsg = (ObjectMessage) msg;
		try{
			manage(textMsg);
		}
		catch (JMSException e){
			e.printStackTrace();
		}
	}
	
	public void checkMessages(BankManager bank) throws JMSException, InterruptedException{
		try (JMSContext context = connectionFactory.createContext("john", "!1secret");){
			JMSConsumer consumer = context.createConsumer(destinationBankQueue);
			JMSConsumer consumer2 = context.createConsumer(destinationDepositQueue);
			
			consumer.setMessageListener(this);
			consumer2.setMessageListener(this);
			
			bank_ops();
			
			System.out.println("Bank Moderator Terminated.");
		}
		catch (JMSRuntimeException e)
		{
			e.printStackTrace();
		}
	}
	
	public void bank_ops() throws JMSException{
		Scanner scan = new Scanner(System.in);
		while(true){
			checkpendingtime(); //---Automatically reject purchases based on time
			
			System.out.println("----------------------------------------------------");
			System.out.println("Bank Manager Options:");
			System.out.println("1 - View approved, pendent and rejected purchases.");
			System.out.println("2 - Approve pendent purchases.");
			System.out.println("3 - Exit.");
			System.out.print("Insert option: ");
			
			int option = intvalidation(3,scan);
			
			//--- A,P,R purchases
			if(option==1){
				checkpendingtime(); //---Automatically reject purchases based on time
				System.out.println("Approved Purchases:");
				for(int i=0;i<acceptedpurchase.size();i++){
					System.out.println(acceptedpurchase.get(i));
				}
				System.out.println("---------------");
				System.out.println("Pendent Purchases:");
				for(int i=0;i<pendingpurchase.size();i++){
					System.out.println(pendingpurchase.get(i));
					System.out.println("Arrival time: " + pendingtime.get(i));
				}
				System.out.println("---------------");
				System.out.println("Rejected Purchases:");
				for(int i=0;i<rejectedpurchase.size();i++){
					System.out.println(rejectedpurchase.get(i));
				}
				System.out.println("---------------");
			}
			
			//--- Approve Pendent Purchases
			else if(option==2){
				checkpendingtime(); //---Automatically reject purchases based on time
				
				if(pendingpurchase.size()>0){
					ArrayList<Integer> pendingpurchasetoapprove = new ArrayList<Integer>();
					for(int i=0;i<pendingpurchase.size();i++){
						boolean b = checkbalanceDB(pendingpurchase.get(i));
						if(b){ //---If accepted this time
							pendingpurchasetoapprove.add(i);
						}
						else{ //---If denied, undo repeated addition to pending list
							pendingpurchase.remove(pendingpurchase.size()-1);
							pendingtime.remove(pendingtime.size()-1);
						}
					}
					if(pendingpurchasetoapprove.size()>0){
						System.out.println("Pendent Purchases:");
						for(int i=0;i<pendingpurchasetoapprove.size();i++){
							System.out.println((i+1) + ") " + pendingpurchase.get(pendingpurchasetoapprove.get(i)));
						}

						System.out.print("Select one pendent purchase: ");
						int a = intvalidation(pendingpurchasetoapprove.size()+1,scan);

						pendingpurchase.remove(pendingpurchasetoapprove.get(a-1));
						pendingtime.remove(pendingpurchasetoapprove.get(a-1));

						sendASync(acceptedpurchase.get(acceptedpurchase.size()-1));
					}
					else{
						System.out.println("No pending purchases available to approve.");
					}
				}
				else{
					System.out.println("No pending purchases to approve.");
				}
			}

			//--- Exit
			else if(option==3){
				break;
			}
		}
		scan.close();
	}
	
	public void checkpendingtime() throws JMSException{
		long current = System.currentTimeMillis();
		for(int i=0;i<pendingtime.size();i++){
			if((current-pendingtime.get(i)) > maxpendingtime ){ //--- If pending time > maxpendingtime
				rejectedpurchase.add(pendingpurchase.get(i));

				pendingpurchase.get(i).setState("rejected");
				
				sendASync(pendingpurchase.get(i));
				
				pendingpurchase.remove(i);
				pendingtime.remove(i);
			}
		}
	}
	
	public void manage(ObjectMessage msg) throws JMSException{
		//--Purchase
		if(msg.getJMSType().equals("3")){
			System.out.println("");
			System.out.println("========== New Message - Purchase ==========");
			
			Purchase purchase = (Purchase) msg.getObject();

			System.out.println(purchase);
			System.out.println("");
			
			sendSync(msg.getJMSReplyTo(),"received");
			System.out .println("Sent reply to " + msg.getJMSReplyTo());
			
			boolean decision = checkbalanceDB(purchase);

			System.out.println("Purchase accepted: " + decision);
			
			sendASync(purchase);
			System.out.println("Sent decision to Moderator.");
			System.out.println("============================================");
		}

		//--Deposit
		else if(msg.getJMSType().equals("4")){
			System.out.println("");
			System.out.println("========== New Message - Deposit ==========");
			
			Deposit deposit = (Deposit) msg.getObject();

			depositDB(deposit.getAmount(),deposit.getName());
			
			System.out.println("");
			sendSync(msg.getJMSReplyTo(), "completed");
			System.out .println("Sent reply to " + msg.getJMSReplyTo());
			System.out.println("===========================================");
		}
		System.out.print("Insert option: ");
	}
	
	public void sendASync(Purchase purchase) throws JMSException{
		try (JMSContext context = connectionFactory.createContext("john", "!1secret");){
			JMSProducer producer = context.createProducer();
			
			ObjectMessage objectMessage = context.createObjectMessage();
			objectMessage.setObject(purchase);
			
			producer.send(destinationDecisionQueue, objectMessage);
		}
	}
	
	public void sendSync(Destination dest , String decision) throws JMSException{
		try (JMSContext context = connectionFactory.createContext("john", "!1secret");){
			JMSProducer producer = context.createProducer();
			
			producer.send(dest, decision);
		}
	}
	
	public void depositDB(long amount , String name){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceforBank");
		EntityManager em = emf.createEntityManager();
		
		String query = "SELECT s FROM ClientBank s WHERE s.name='" + name + "'";
		
		@SuppressWarnings("unchecked")
		List<ClientBank> mylist = (List<ClientBank>) em.createQuery(query).getResultList();
		
		ClientBank wanted = mylist.get(0);
		
		em.getTransaction().begin();
		wanted.setBalance(wanted.getBalance() + amount);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		System.out.println("Deposit of " + amount + "€ completed on " + name + "'s account.");
	}
	
	public boolean checkbalanceDB( Purchase purchase ){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceforBank");
		EntityManager em = emf.createEntityManager();
		
		String query = "SELECT s FROM ClientBank s WHERE s.name='" + purchase.getCardname() + "'";
		
		@SuppressWarnings("unchecked")
		List<ClientBank> mylist = (List<ClientBank>) em.createQuery(query).getResultList();
		
		ClientBank wanted = mylist.get(0);
		
		if(wanted.getBalance()>=purchase.getCost()){
			em.getTransaction().begin();
			wanted.setBalance(wanted.getBalance() - purchase.getCost());
			em.getTransaction().commit();
			em.close();
			emf.close();
			purchase.setState("accepted");
			acceptedpurchase.add(purchase);
			return true;	
		}
		else{
			em.close();
			emf.close();
			pendingpurchase.add(purchase);
			pendingtime.add(System.currentTimeMillis());
			return false;
		}
	}
	
	public int intvalidation(int maxrange, Scanner scan){
		int option=0;
		boolean done = false;
		while(!done){
			while (!scan.hasNextInt()){
				System.out.print("Please insert a number... Option: ");
				scan.nextLine();
			}
			option = scan.nextInt();
			if(option<=0 || option>maxrange){
				System.out.print("Option does not exist... Option: ");
			}
			else{
				done = true;
			}
		}
		return option;	
	}
}