package com.github.generaldon;

import org.bukkit.entity.Player;

public class WarrantTimer extends Thread{
	private boolean revoked;
	private long delay;
	private Player target;
	
	public WarrantTimer(Player targeted,int minutes)
	{
		target=targeted;
		delay=minutes*60000;
		revoked=false;
		this.start();
	}
	
	public void run()
	{
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(!revoked)
		{
			//Do stuff if revoked
		}
	}
	
	public void revoke()
	{
		revoked=true;
	}
	
}