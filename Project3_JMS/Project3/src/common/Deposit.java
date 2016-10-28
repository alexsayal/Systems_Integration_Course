package common;

import java.io.Serializable;

public class Deposit implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private long amount;
	
	public Deposit(String name, long amount) {
		this.amount = amount;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String toString(){
		return "Deposit: \nAmount: " + this.amount + "   |   Name: " + this.name;
	}

	
}
