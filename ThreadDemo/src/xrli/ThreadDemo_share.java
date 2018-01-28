package xrli;
//Thread 类实现了 Runnable 接口，也就是说 Thread 类也是Runnable 接口的一个子类。  Thread就是线程的意思
public class ThreadDemo_share {
	public static void main(String args[])
	{
		Test_Thread3 t = new Test_Thread3();
		
		new Thread(t).start();  
		new Thread(t).start(); 
		new Thread(t).start(); 
		new Thread(t).start(); 
		 
	}

}

class Test_Thread3 implements Runnable{
	private int tickets = 20;
	private boolean flag = true;
	
	public void run(){
		while(flag){
			if(tickets>0){
				try {
					Thread.sleep(1);
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
}

