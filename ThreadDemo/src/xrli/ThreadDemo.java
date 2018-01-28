package xrli;
//不要用，还是用Runnable好
public class ThreadDemo {
	public static void main(String args[])
	{
		new Test_Thread1().start();
		for(int i=0;i<10;i++){
			System.out.println("main 线程在运行");
		}
	}

}

class Test_Thread1 extends Thread{
	public void run(){
		for(int i=0;i<10;i++){
			System.out.println("TestThread在运行");
		}
	}
}
