package com.joseph.thedarknessbeyond.threads;

/**
 * Thread will run when the program terminates with code 0. Used to do final saving of data to disk 
 * in the event that the data wasn't saved.
 * @author Joseph Terribile
 *
 */
public class ShutdownThread extends Thread {
	public ShutdownThread() {
		super("FinilazerThread");
	}
	
	@Override
	public void run() {
		
	}
}