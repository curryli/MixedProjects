package xrli;

public class No_ThreadDemo1 {
	public static void main(String args[])
	{
		new Test_NoThread().run();
		for(int i=0;i<10;i++){
			System.out.println("main 线程在运行");
		}
	}

}

class Test_NoThread{
	public void run(){
		for(int i=0;i<10;i++){
			System.out.println("Test_NoThread 在运行");
		}
	}
}
