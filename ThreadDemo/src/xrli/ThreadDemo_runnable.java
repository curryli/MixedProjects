package xrli;
//Thread ��ʵ���� Runnable �ӿڣ�Ҳ����˵ Thread ��Ҳ��Runnable �ӿڵ�һ�����ࡣ  Thread�����̵߳���˼
public class ThreadDemo_runnable {
	public static void main(String args[])
	{
		Test_Thread2 t = new Test_Thread2();
		
		new Thread(t).start();  
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(10);  //����Ϊ�˿��������ÿ���߳̿��ܴ�ӡ���ٶȲ�һ��
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("main �߳�������");
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
			System.out.println("TestThread������");
		}
	}
}
