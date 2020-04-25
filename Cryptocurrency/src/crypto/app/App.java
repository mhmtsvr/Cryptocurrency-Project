package crypto.app;

import java.security.Security;

import crypto.blockchain.Block;
import crypto.blockchain.BlockChain;
import crypto.constants.Constants;
import crypto.currency.Miner;
import crypto.currency.Transaction;
import crypto.currency.TransactionOutput;
import crypto.currency.Wallet;

public class App {

	public static void run(){
	
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		Wallet userA = new Wallet();
		Wallet userB = new Wallet();		
		Wallet lender = new Wallet();
		BlockChain chain = new BlockChain();
		Miner miner = new Miner();
		
		Transaction genesisTransaction = new Transaction(lender.getPublicKey(), userA.getPublicKey(), 500, null);
		genesisTransaction.generateSignature(lender.getPrivateKey());	
		genesisTransaction.setTransactionId("0"); 
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.getReceiver(), genesisTransaction.getAmount(), genesisTransaction.getTransactionId())); 
		BlockChain.UTXOs.put(genesisTransaction.outputs.get(0).getId(), genesisTransaction.outputs.get(0)); 
		sendGUI("Constructing the genesis block...");
		Block genesis = new Block(Constants.GENESIS_PREV_HASH);
		genesis.addTransaction(genesisTransaction);
		miner.mine(genesis,chain);
		
		Block block1 = new Block(genesis.getHash());
		sendGUI("\nuserA's balance is: " + userA.calculateBalance());
		sendGUI("\nuserA tries to send money (120 coins) to userB...");
		block1.addTransaction(userA.transferMoney(userB.getPublicKey(), 120));
		miner.mine(block1,chain);
		sendGUI("\nuserA's balance is: " + userA.calculateBalance());
		sendGUI("userB's balance is: " + userB.calculateBalance());
		
		Block block2 = new Block(block1.getHash());
		sendGUI("\nuserA sends more funds (600) than it has...");
		block2.addTransaction(userA.transferMoney(userB.getPublicKey(), 600));
		miner.mine(block2,chain);
		sendGUI("\nuserA's balance is: " + userA.calculateBalance());
		sendGUI("userB's balance is: " + userB.calculateBalance());
		
		Block block3 = new Block(block2.getHash());
		sendGUI("\nuserB is attempting to send funds (110) to userA...");
		block3.addTransaction(userB.transferMoney( userA.getPublicKey(), 110));
		sendGUI("\nuserA's balance is: " + userA.calculateBalance());
		sendGUI("userB's balance is: " + userB.calculateBalance());
		miner.mine(block3,chain);
		
		sendGUI("Miner's reward: "+miner.getReward());
	}
	
	
	public static void sendGUI(String str){
		Home.textPane.setText(Home.textPane.getText() + "\n" +str);
	}
}
