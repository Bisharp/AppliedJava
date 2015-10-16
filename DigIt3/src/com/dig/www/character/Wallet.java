package com.dig.www.character;

public class Wallet {
	
	private int money;
	
	public Wallet(int x) {
		setMoney(x);
	}

	public Wallet() {
		setMoney(0);
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
		if(getDigits()>9){
			setMoney(999999999);
		}
	}
	
	public void addMoney(int money) {
		this.money += money;
		if(getDigits()>9){
			setMoney(999999999);
		}
	}

	public void spendMoney(int money) {
		this.money -= money;
	}

	public int getDigits() {
		// TODO Auto-generated method stub
		return ("" + money).length();
	}
}
