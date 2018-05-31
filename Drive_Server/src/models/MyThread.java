package models;

public abstract class MyThread implements Runnable{

	private Thread thread;
	private boolean isPause;
	private boolean isRunning;

	public MyThread() {
		thread = new Thread(this);
	}

	public void start(){
		thread.start();
		this.isRunning = true;
	}

	@Override
	public void run() {
		while (isRunning) {
			executeTask();
			synchronized (this) {
				while (isPause) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (!isRunning) {
					break;
				}
			}
		}
	}

	public synchronized void stop() {
		this.isRunning = false;
	}

	public synchronized void resume() {
		this.isPause = false;
		notify();
	}

	public synchronized void pause() {
		this.isPause = true;
	}
	
	public void join(){
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	abstract void executeTask();
}