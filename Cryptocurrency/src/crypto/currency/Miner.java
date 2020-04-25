package crypto.currency;

import crypto.app.Home;
import crypto.blockchain.Block;
import crypto.blockchain.BlockChain;
import crypto.constants.Constants;

public class Miner {

private double reward;
	
	public void mine(Block block, BlockChain blockChain) {
		
		while(notGoldenHash(block)) {
			block.generateHash();
			block.incrementNonce();
		}
		
		sendGUI(block+" has just mined...");
		sendGUI("Hash is: "+block.getHash());
		
		
		blockChain.addBlock(block);
		reward+=Constants.MINER_REWARD;
	}
	
	public boolean notGoldenHash(Block block) {
		String leadingZeros = new String(new char[Constants.DIFFICULTY]).replace('\0', '0');
		return !block.getHash().substring(0,Constants.DIFFICULTY).equals(leadingZeros);
	}
	
	public double getReward() {
		return this.reward;
	}
	public static void sendGUI(String str){
		Home.textPane.setText(Home.textPane.getText() + "\n" +str);
	}
	
}
