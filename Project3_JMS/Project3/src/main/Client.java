package main;

import java.util.ArrayList;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import common.Deposit;
import common.Purchase;

public class Client implements MessageListener {

	private ConnectionFactory connectionFactory;
	private Destination destinationResModQueue;
	private Destination destinationDepositQueue;
	private Destination destinationCFPTopic;
	private Destination destinationPurchaseDecisionQueue;
	
	public Client() throws NamingException {
		this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
		this.destinationResModQueue = InitialContext.doLookup("jms/queue/ResModQueue");
		this.destinationDepositQueue = InitialContext.doLookup("jms/queue/DepositQueue");
		this.destinationCFPTopic = InitialContext.doLookup("jms/topic/CFPTopic");
		this.destinationPurchaseDecisionQueue = InitialContext.doLookup("jms/queue/PurchaseDecisionQueue");
	}
	
	public static void main(String[] args) throws NamingException {
		Client client = new Client();
		client.checkCFP(client);
	}
	
	@Override
	public void onMessage(Message msg){
		ObjectMessage textMsg = (ObjectMessage) msg;
		try{
			System.out.println();
			System.out .println(textMsg.getObject());
			
			if(!textMsg.getJMSType().equals("fulltext")){
				System.out.println("Client Options:");
				System.out.println("1 - List all publication titles.");
				System.out.println("2 - Announce a Call for Papers (CFP).");
				System.out.println("3 - Purchase Full Text of a publication.");
				System.out.println("4 - Bank Deposit.");
				System.out.println("5 - Exit.");
				System.out.print("Select option: ");
			}
		}
		catch (JMSException e){
			e.printStackTrace();
		}
	}
	
	private void checkCFP(Client client){
		try(Connection context = connectionFactory.createConnection("john", "!1secret");){
			context.setClientID("Henrique Madeira");
			//context.setClientID("Marco Vieira");
			//context.setClientID("Mikko Sams");
			//context.setClientID("Fausto Pinto");
			//context.setClientID("Nuno C. Ferreira");
			context.start();
			
			Session session = context.createSession(false,Session.AUTO_ACKNOWLEDGE);
			
			MessageConsumer purchaseconsumer = session.createConsumer(destinationPurchaseDecisionQueue, "client = '" + context.getClientID() + "'");
		
			TopicSubscriber subscriber = session.createDurableSubscriber((Topic) destinationCFPTopic, "topic");
			
			subscriber.setMessageListener(this);
			purchaseconsumer.setMessageListener(this);
			
			client.client_ops(client,context.getClientID());
			
			session.close();
			context.close();
			
			System.out .println("Researcher Client Terminated.");
		}
		catch (Exception re){
			re.printStackTrace();
		}
	}
	
	public void client_ops(Client sender,String clientid){
		Scanner scan = new Scanner(System.in);
		System.out.println("----------------------------------------------------");
		System.out.println("           Welcome " + clientid + "!");
		System.out.println("----------------------------------------------------");
		System.out.println("Client Options:");
		System.out.println("1 - List all publication titles.");
		System.out.println("2 - Announce a Call for Papers (CFP).");
		System.out.println("3 - Purchase Full Text of a publication.");
		System.out.println("4 - Bank Deposit.");
		System.out.println("5 - Exit.");
		System.out.print("Select option: ");
		
		while(true){
			int option = intvalidation(1,5);
			
			if(option==5){
				break;
			}
			else if(option==1){
				sender.send(option,"");
			}
			else if(option==2){
				System.out.print("Insert topic: ");
				String CFPtopic = scan.nextLine();
				
				sender.send(option,CFPtopic);
			}
			else if(option==3){
				System.out.print("Insert topic: ");
				String topic = scan.nextLine();

				int cardnumber = intvalidation(2,5);
				int cardcode = intvalidation(3,5);
				
				Purchase purchase = new Purchase(topic,clientid,cardnumber,cardcode,0);
				
				sender.send_purchase(option,purchase);
			}
			else if(option==4){
				long amount = 0;
				boolean done = false;
				System.out.print("Insert amount to deposit: ");
				
				while(!done){
					while (!scan.hasNextLong()){
						System.out.print("Please insert a number... Amount: ");
						scan.next();
					}
					amount = scan.nextLong();
					if(option<=0){
						System.out.print("Deposit must be a positive number... Amount: ");
					}
					else{
						done = true;
					}
				}
				sender.send_deposit(option, amount, clientid);
			}
		}
		scan.close();
	}
	
	@SuppressWarnings("unchecked")
	private void send(int option, String title){
		try (JMSContext context = connectionFactory.createContext("john", "!1secret");){
			JMSProducer messageProducer = context.createProducer();
			
			ObjectMessage msg = context.createObjectMessage();
			Destination tmp = context.createTemporaryQueue();
			
			msg.setJMSReplyTo(tmp);
			msg.setJMSType(String.valueOf(option));
			if(option==2) msg.setObject(title);
			
			messageProducer.send(destinationResModQueue, msg);
			
			JMSConsumer cons = context.createConsumer(tmp);
			
			if(option==1){
				ArrayList<String> titles = cons.receiveBody(ArrayList.class);
				Thread.sleep(500);
				System.out.println("-----Publication titles-----");
				for(int i=0;i<titles.size();i++){
					System.out.println("Title " + (i+1) +": " + titles.get(i));
				}
				System.out.print("Select option: ");
			}
			else if(option==2){
				String decision = cons.receiveBody(String.class);

				System.out.println("The CFP was " + decision + ".");
				if(decision.equals("rejected")) System.out.print("Select option: ");
			}
			context.close();
		}
		catch (Exception re){
			re.printStackTrace();
		}
	}
	
	private void send_purchase(int option, Purchase purchase){
		try (JMSContext context = connectionFactory.createContext("john", "!1secret");){
			JMSProducer messageProducer = context.createProducer();
			
			ObjectMessage msg = context.createObjectMessage();
			Destination tmp = context.createTemporaryQueue();
			
			msg.setJMSReplyTo(tmp);
			msg.setJMSType(String.valueOf(option));
			msg.setObject(purchase);

			messageProducer.send(destinationResModQueue, msg);
			
			JMSConsumer cons = context.createConsumer(tmp);
			String answer = cons.receiveBody(String.class);
			
			System.out.println("The purchase order was " + answer + ".");
			System.out.print("Select option: ");
			context.close();
		}
		catch (Exception re){
			re.printStackTrace();
		}
	}
	
	private void send_deposit(int option, long amount, String clientid){
		try (JMSContext context = connectionFactory.createContext("john", "!1secret");){
			JMSProducer messageProducer = context.createProducer();
			
			ObjectMessage msg = context.createObjectMessage();
			Destination tmp = context.createTemporaryQueue();
			
			Deposit deposit = new Deposit(clientid,amount); 
			
			msg.setJMSReplyTo(tmp);
			msg.setJMSType(String.valueOf(option));
			msg.setObject(deposit);

			messageProducer.send(destinationDepositQueue, msg);
			
			JMSConsumer cons = context.createConsumer(tmp);
			
			String answerfrombank = cons.receiveBody(String.class);
			
			Thread.sleep(500); //Just to keep the console on the Client...
			System.out.println("The deposit order was " + answerfrombank + ".");
			System.out.print("Select option: ");
			context.close();
		}
		catch (Exception re){
			re.printStackTrace();
		}
	}

	public int intvalidation(int val_type,int maxrange){
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		if(val_type==1){ // Validate option
			int option=0;
			boolean done = false;
			while(!done){
				while (!scan.hasNextInt()){
					System.out.print("Please insert a number... Option: ");
					scan.nextLine();
				}
				option = scan.nextInt();
				if(option<=0 || option>maxrange){
					System.out.print("Option out of range...Option: ");
				}
				else{
					done = true;
				}
			}
			return option;	
		}

		else if(val_type==2){ // Validate card number
			int card_number=0;
			boolean done = false;
			System.out.println("Credit Card Information:");
			System.out.print("Number: ");
			while(!done){
				while (!scan.hasNextInt()){
					System.out.print("Please insert a number... Number: ");
					scan.nextLine();
				}
				card_number = scan.nextInt();
				if(card_number<0){
					System.out.print("Negative number not valid... Number: ");
				}
				else{
					done = true;
				}
			}
			return card_number;	
		}

		else if(val_type==3){ // Validate card security code
			int card_code=0;
			boolean done = false;
			System.out.print("Security Code: ");
			while(!done){
				while (!scan.hasNextInt()){
					System.out.print("Please insert a number... Card Code: ");
					scan.nextLine();
				}
				card_code = scan.nextInt();
				if(card_code<0){
					System.out.print("Negative number not valid... Card Code: ");
				}
				else{
					done = true;
				}
			}
			return card_code;	
		}
		else{
			return -999;
		}
	}
}