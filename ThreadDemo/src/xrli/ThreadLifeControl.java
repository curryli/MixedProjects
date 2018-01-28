package xrli;
//Thread 类实现了 Runnable 接口，也就是说 Thread 类也是Runnable 接口的一个子类。  Thread就是线程的意思
public class ThreadLifeControl {
	public static void main(String args[]) throws InterruptedException
	{
		life_Thread  t = new life_Thread();
		new Thread(t).start();
		 
		for(int i=0;i<10; i++)
		 {
			System.out.println("Main 线程在运行" + i);
			if(i == 5)
				t.stopMe();
			Thread.sleep(100);

		 }
		
	}

}

class life_Thread implements Runnable{
	private boolean flag = true;
	
	public void stopMe(){
		flag = false;
	}
	
	public void run(){
		 while(flag)
			{
			 try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 System.out.println(Thread.currentThread().getName()+" 在运行");
			}
		 
	}
}

