package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import artifactP.PublicationNew;
import artifactP.PublicationServer;
import artifactP.PublicationServerService;
import artifactR.ResearcherNew;
import artifactR.ResearcherServer;
import artifactR.ResearcherServerService;

public class MainClient {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.println("||-----Welcome to the DataBase Client-----||");
		System.out.println("||--------------Version 1.0---------------||");
		System.out.println("");
		boolean done=true;

		while(done){
			System.out.println("1) Access Researchers DB \n2) Access Publications DB \n3) Access Info of a Researcher's Publications\n4) Access Info of a Publication's Author \n5) Exit");
			System.out.print("Select option: ");
			int option1=scan.nextInt();
			int option2 = 0;
			switch (option1) {
			case 1:
				client_Res();
				break;
			case 2:
				client_Pub();
				break;
			case 3:
				client_Res_Pub();
				break;
			case 4:
				client_Pub_Auth();
				break;
			case 5:
				done=false;
				break;
			default:
				System.out.println("No such option available.");
				break;
			}
			if(!done) break; //Checkpoint

			System.out.println("");
			System.out.println("1)Back to Main Menu \n2)Exit");

			while(option2!=1 && option2!=2){
				System.out.print("Select option: ");
				while (!scan.hasNextInt()){
					System.out.print("Please insert a number... Option: ");
					scan.next();
				}
				option2 = scan.nextInt();
			}

			if(option2==2){
				done=false;
			}

			System.out.println("===========================================");
		}
		scan.close();
		System.out.println("Client Terminated.");
	}

	public static void client_Res(){
		ResearcherServerService s = new ResearcherServerService();
		ResearcherServer server = s.getResearcherServerPort();
		
		@SuppressWarnings("resource")
		Scanner scan2 = new Scanner(System.in);

		System.out.println("|-----------------------------------------|");
		System.out.println("|---------- RESEARCHER DATABASE ----------|");
		System.out.println("|-----------------------------------------|");
		System.out.println("Select one of the following options:");
		System.out.println("1) List all researchers.");
		System.out.println("2) List N researchers that have the highest citation count.");
		System.out.println("3) List researcher sorted by a specific attribute.");
		int option=0;
		int n = 0;
		int a = 0;
		int o = 0;
		List<ResearcherNew> L = new ArrayList<ResearcherNew>();
		
		while(option!=1 && option!=2 && option!=3){
			System.out.print("Option: ");
			while (!scan2.hasNextInt()){
				System.out.print("Please insert a number... Option: ");
				scan2.next();
			}
			option = scan2.nextInt();
		}
		if(option==1){
			System.out.println(" ");
			System.out.println("...Processing...            ");
			System.out.println(" ");
			
			L = server.readAllR();
		}
		if(option==2){
			System.out.println("Choose N = ");
			while (!scan2.hasNextInt()){
				System.out.print("Please insert a number... N = ");
				scan2.next();
			}
			n = scan2.nextInt();
			
			System.out.println(" ");
			System.out.println("...Processing...            ");
			System.out.println(" ");
			
			L = server.readNR(n);
		}
		if(option==3){
			System.out.println("Attributes: 1 - ID , 2 - Name , 3 - University.");
			do{
				System.out.println("Choose: ");
				while (!scan2.hasNextInt()){
					System.out.print("Please insert a number... Choose: ");
					scan2.next();
				}
				a = scan2.nextInt();
			} while(a<1 || a>3);
			System.out.println("Sorting order: 1 - Ascending , 2 - Descending ");
			do{
				System.out.println("Choose: ");
				while (!scan2.hasNextInt()){
					System.out.print("Please insert a number... Choose: ");
					scan2.next();
				}
				o = scan2.nextInt();
			} while(o<1 || o>2);
			
			System.out.println(" ");
			System.out.println("...Processing...            ");
			System.out.println(" ");
			
			L = server.readSortR(a,o);
		}

		System.out.println("Success! Selected Researchers (" + L.size() + "): ");
		for (int i=0 ; i<L.size() ; i++){
			System.out.println(L.get(i));
		}

		System.out.println("-----------------------------------------");	
	}

	public static void client_Pub(){
		PublicationServerService s = new PublicationServerService();
		PublicationServer server = s.getPublicationServerPort();
		
		@SuppressWarnings("resource")
		Scanner scan3 = new Scanner(System.in);

		System.out.println("|------------------------------------------|");
		System.out.println("|---------- PUBLICATION DATABASE ----------|");
		System.out.println("|------------------------------------------|");
		System.out.println("Select one of the following options:");
		System.out.println("1) List all publications.");
		System.out.println("2) List a set of publications.");
		int option = 0;
		List<PublicationNew> L = new ArrayList<PublicationNew>();
		
		while(option!=1 && option!=2){
			System.out.print("Option: ");
			while (!scan3.hasNextInt()){
				System.out.print("Please insert a number... Option: ");
				scan3.next();
			}
			option = scan3.nextInt();
		}

		if(option==1){
			System.out.println(" ");
			System.out.println("...Processing...            ");
			System.out.println(" ");
			
			L = server.readAllP();
		}
		
		if(option==2){
			List <String> wanted = new ArrayList<String>();
			wanted.add(""); 
			List<PublicationNew> X = server.readSortP();

			System.out.println("Options available:");
			for(int i=0; i<X.size(); i++){
				System.out.println(X.get(i).getTitle());
			}
			System.out.println();
			System.out.println("----------");
			scan3.nextLine();
			boolean done = false;
			while (!done){
				System.out.print("Choose Title (empty to finish): ");
				String title = scan3.nextLine();
				done = title.equals("");
				if (!done){
					wanted.add(title);
				}
			}
			
			System.out.println(" ");
			System.out.println("...Processing...            ");
			System.out.println(" ");
			
			L = server.readWantedP(wanted);
		}
		
		if(L.size()>0){
			System.out.println("Success! Selected publications (" + L.size() + "): ");
			for(int i=0 ; i<L.size(); i++){
				System.out.println(L.get(i));
			}
		}
		else{
			System.out.println("No publications selected.");
		}
		System.out.println("-----------------------------------------");
	}

	public static void client_Res_Pub(){
		//---Access Researcher
		ResearcherServerService s = new ResearcherServerService();
		ResearcherServer server = s.getResearcherServerPort();
		
		@SuppressWarnings("resource")
		Scanner scan4 = new Scanner(System.in);

		System.out.println("|------------------------------------------|");
		System.out.println("|----------- CROSS INFO RES-PUB -----------|");
		System.out.println("|------------------------------------------|");

		List<ResearcherNew> L = server.readAllR();

		System.out.println("Available Researchers (" + L.size() + "): ");
		for (int i=0 ; i<L.size() ; i++){
			System.out.println(L.get(i).getName());
		}
		System.out.println("----------");
		ResearcherNew selected = null;
		boolean proceed = true;
		int k = 0;
		while(selected==null){
			System.out.print("Choose one researcher: ");
			String name = scan4.nextLine();
		
			selected = server.selectR(name);
			k+=1;
			
			if(selected==null) System.out.println("No such researcher available.");
			if(k==3){
				System.out.println("Try limit reached.");
				proceed = false;
				break;
			}
		}
		
		//---Access Publications
		if(proceed){
			PublicationServerService s2 = new PublicationServerService();
			PublicationServer server2 = s2.getPublicationServerPort();

			List<String> wanted = new ArrayList<String>();
			for(int i=0;i<3;i++){
				wanted.add(selected.getPublications().get(i).getTitle());
			}

			List<PublicationNew> L2 = server2.readWantedP(wanted);

			//---Print it!
			System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(selected);
			System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			for(int i=0;i<L2.size();i++){
				System.out.println(L2.get(i));
			}
		}

		System.out.println("-----------------------------------------");
	}

	public static void client_Pub_Auth(){
		PublicationServerService s = new PublicationServerService();
		PublicationServer server = s.getPublicationServerPort();
		
		@SuppressWarnings("resource")
		Scanner scan3 = new Scanner(System.in);
		
		System.out.println("|------------------------------------------|");
		System.out.println("|----------- CROSS INFO PUB-RES -----------|");
		System.out.println("|------------------------------------------|");
		
		List<PublicationNew> L1 = server.readSortP();
		
		System.out.println("Available publications(" + L1.size() + "):");
		for(int i=0; i<L1.size(); i++){
			System.out.println(L1.get(i).getTitle());
		}
		System.out.println();
		System.out.println("----------");
		
		PublicationNew selected = null;
		boolean proceed = true;
		int k = 0;
		while(selected==null){
			System.out.print("Choose one publication: ");
			String title = scan3.nextLine();
		
			selected = server.selectP(title);
			k+=1;
			
			if(selected==null) System.out.println("No such publication available.");
			if(k==3){
				System.out.println("Try limit reached.");
				proceed = false;
				break;
			}
		}
		
		//---Access Researchers
		if(proceed){
			ResearcherServerService s2 = new ResearcherServerService();
			ResearcherServer server2 = s2.getResearcherServerPort();
			
			List<ResearcherNew> L = server2.readAllR();
			List<ResearcherNew> L2 = new ArrayList<ResearcherNew>(); 
			
			for(int i=0;i<L.size();i++){
				for(int j=0;j<3;j++){
					if(L.get(i).getPublications().get(j).getTitle().equals(selected.getTitle())){
						L2.add(L.get(i));
					}
				}
			}
			
			//---Print it!
			System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			System.out.println(selected);
			System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			for(int i=0;i<L2.size();i++){
				System.out.println(L2.get(i));
			}
		}
	}
}