package scripts.jTears.graphics;

import org.tribot.api.Timing;
//import org.tribot.api2007.Interfaces;
import org.tribot.script.Script;

import scripts.jTears.data.Vars;

public class PaintInfoThread extends Thread {
	
	private long startTime;
	private Script script;
	
	private boolean running;
	
	public PaintInfoThread(Script script) {
		this.script = script;
		this.startTime = Timing.currentTimeMillis();

		running = true;
	}
	
	@Override
	public void run() {
		while (running) {
			Vars.get().runTime = Timing.currentTimeMillis() - this.startTime;
			//Vars.get().tears = Interfaces.get(5,6).getText(); need parent and child numbers
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			running = this.script.isActive();
		}
	}
}