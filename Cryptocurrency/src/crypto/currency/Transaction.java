package crypto.currency;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import crypto.app.Home;
import crypto.blockchain.BlockChain;

public class Transaction {
	
	
	
	private String transactionId; 
	private PublicKey sender; 
	private PublicKey receiver;
	private double amount;
	private byte[] signature;
	
	public List<TransactionInput> inputs;
	public List<TransactionOutput> outputs;
	
	public Transaction(PublicKey sender, PublicKey receiver, double amount,  List<TransactionInput> inputs) {
		this.inputs = new ArrayList<TransactionInput>();
		this.outputs = new ArrayList<TransactionOutput>();
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.inputs = inputs;
		calulateHash();
	}
	
	public boolean verifyTransaction() {
		
		if(!verifySignature()) {
			sendGUI("Invalid transaction because of invalid signature...");
			return false;
		}
				
		for(TransactionInput transactionInput : inputs) {
			transactionInput.setUTXO( BlockChain.UTXOs.get(transactionInput.getTransactionOutputId()));
		}
		
		outputs.add(new TransactionOutput( this.receiver, amount,transactionId)); 
		outputs.add(new TransactionOutput( this.sender, getInputsSum() - amount,transactionId));		
				
		for(TransactionOutput transactionOutput : outputs) {
			BlockChain.UTXOs.put(transactionOutput.getId() , transactionOutput);
		}
		
		for(TransactionInput transactionInput : inputs) {
			if(transactionInput.getUTXO() != null)   
				BlockChain.UTXOs.remove(transactionInput.getUTXO().getId());
		}
		
		return true;
	}
	
	public double getInputsSum() {
		
		double sum = 0;
		
		for(TransactionInput transactionInput : inputs) {
			if(transactionInput.getUTXO() != null) 
				sum += transactionInput.getUTXO().getAmount();
		}
		return sum;
	}
	
	public void generateSignature(PrivateKey privateKey) {
		String data = sender.toString() + receiver.toString() + Double.toString(amount);
		signature = CryptographyHelper.applyECDSASignature(privateKey,data);		
	}
	
	public boolean verifySignature() {
		String data = sender.toString() + receiver.toString() + Double.toString(amount);
		return CryptographyHelper.verifyECDSASignature(sender, data, signature);
	}
	
	private void calulateHash() {
		String hashData = sender.toString()+receiver.toString()+Double.toString(amount);
		this.transactionId = CryptographyHelper.generateHash(hashData);
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public PublicKey getSender() {
		return sender;
	}

	public void setSender(PublicKey sender) {
		this.sender = sender;
	}

	public PublicKey getReceiver() {
		return receiver;
	}

	public void setReceiver(PublicKey receiver) {
		this.receiver = receiver;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public List<TransactionInput> getInputs() {
		return inputs;
	}

	public void setInputs(List<TransactionInput> inputs) {
		this.inputs = inputs;
	}

	public List<TransactionOutput> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<TransactionOutput> outputs) {
		this.outputs = outputs;
	}
	public static void sendGUI(String str){
		Home.textPane.setText(Home.textPane.getText() + "\n" +str);
	}
}
