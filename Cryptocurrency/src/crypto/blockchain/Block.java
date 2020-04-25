package crypto.blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import crypto.app.Home;
import crypto.constants.Constants;
import crypto.currency.CryptographyHelper;
import crypto.currency.Transaction;

public class Block {
	
	private int id;
	private int nonce;
	private long timeStamp;
	private String hash;
	private String previousHash;
	public List<Transaction> transactions;
	
	public Block(String previousHash ) {
		this.transactions = new ArrayList<Transaction>();
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		generateHash(); 
	}
	
	public void generateHash() {
		String dataToHash = Integer.toString(id)+previousHash+Long.toString(timeStamp)+transactions.toString()+Integer.toString(nonce);
		String hashValue = CryptographyHelper.generateHash(dataToHash);
		this.hash = hashValue;
	}
	
	public void incrementNonce() {
		this.nonce++;
	}
	
	public String getHash() {
		return this.hash;
	}
	

	public boolean addTransaction(Transaction transaction) {
		
		if(transaction == null) return false;
		

		if((!previousHash.equals(Constants.GENESIS_PREV_HASH))) {
			if((!transaction.verifyTransaction())) {
				sendGUI("Transaction is not valid...");
				return false;
			}
		}

		transactions.add(transaction);
		sendGUI("Transaction is valid and it's added to the block "+this);
		return true;
	}
	
	public static void sendGUI(String str){
		Home.textPane.setText(Home.textPane.getText() + "\n" +str);
	}
	
}
