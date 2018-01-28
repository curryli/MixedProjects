package xrli;
//Thread 类实现了 Runnable 接口，也就是说 Thread 类也是Runnable 接口的一个子类。  Thread就是线程的意思
public class ThreadDemo_runnable {
	public static void main(String args[])
	{
		Test_Thread2 t = new Test_Thread2();
		
		new Thread(t).start();  
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(10);  //这是为了看得清楚，每个线程可能打印的速度不一样
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("main 线程在运行");
		}
	}

}

class Test_Thread2 implements Runnable{
	public void run(){
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("TestThread在运行");
		}
	}
}
