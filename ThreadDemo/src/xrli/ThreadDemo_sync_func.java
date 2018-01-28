package xrli;
//Thread 类实现了 Runnable 接口，也就是说 Thread 类也是Runnable 接口的一个子类。  Thread就是线程的意思
public class ThreadDemo_sync_func {
	public static void main(String args[])
	{
		sync_func_Thread t = new sync_func_Thread();
		
		new Thread(t).start();  
		new Thread(t).start(); 
		new Thread(t).start(); 
		new Thread(t).start(); 
		 
	}

}

class sync_func_Thread implements Runnable{
	private int tickets = 20;
	private boolean flag = true;
	public void run(){
		while(flag){
			sync_func();
		}
	}
	
	public synchronized void sync_func(){
		if(tickets>0){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"出售票"+tickets--);
		}
		else{
			flag = false;
		}
	}
}

