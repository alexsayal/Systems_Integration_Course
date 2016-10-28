package main;

import java.io.IOException;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.naming.NamingException;

public class teste {

	public static void main(String[] args) throws NamingException, JMSException, IOException {
		
		
		Thread thread2 = new Thread(new Runnable() {
			public void run(){
				System.out.println("OLA");
				while(true){
					@SuppressWarnings("resource")
					Scanner scan =new Scanner(System.in);
					System.out.println("Estou aqui.");
					int a= scan.nextInt();
					if(a==2){
						break;
					}
					System.out.println(a);
				}
			}
		});  
		thread2.start();
		
		Thread thread1 = new Thread(new Runnable() {
			public void run(){
					System.out.println("Press enter");
					try {
						thread2.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				System.out.println("Estou na nova thread.");

			}
		});  
		thread1.start();

		
		
		
	}

}
