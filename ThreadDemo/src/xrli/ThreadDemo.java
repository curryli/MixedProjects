package xrli;
//��Ҫ�ã�������Runnable��
public class ThreadDemo {
	public static void main(String args[])
	{
		new Test_Thread1().start();
		for(int i=0;i<10;i++){
			System.out.println("main �߳�������");
		}
	}

}

class Test_Thread1 extends Thread{
	public void run(){
		for(int i=0;i<10;i++){
			System.out.println("TestThread������");
		}
	}
}
