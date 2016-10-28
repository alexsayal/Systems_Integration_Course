package common;

import java.io.Serializable;

public class Purchase implements Serializable{

	private static final long serialVersionUID = 1L;
	private String topic;
	private int cost;
	private String cardname;
	private int cardnumber;
	private int cardcode;
	private String state;
	
	public Purchase(){};
	
	public Purchase(String topic, String cardname, int cardnumber, int cardcode, int cost) {
		this.topic = topic;
		this.cardcode = cardcode;
		this.cardname = cardname;
		this.cardnumber = cardnumber;
		this.cost = cost;
		this.state = "pending";
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getCardname() {
		return cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public int getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(int cardnumber) {
		this.cardnumber = cardnumber;
	}

	public int getCardcode() {
		return cardcode;
	}

	public void setCardcode(int cardcode) {
		this.cardcode = cardcode;
	}
	
	public String toString(){
		return "Topic:" + this.topic + "   |   " + "Cost: " + this.cost + "€" + "\n" 
				+ "Name:" + this.cardname + "   |   " + "Card Number:*****   |   " 
				+ "Card Code:*****   |   "  + "State: " + this.state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
