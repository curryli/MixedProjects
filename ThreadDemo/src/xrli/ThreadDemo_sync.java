package xrli;
//Thread ��ʵ���� Runnable �ӿڣ�Ҳ����˵ Thread ��Ҳ��Runnable �ӿڵ�һ�����ࡣ  Thread�����̵߳���˼
public class ThreadDemo_sync {
	public static void main(String args[])
	{
		sync_Thread t = new sync_Thread();
		
		new Thread(t).start();  
		new Thread(t).start(); 
		new Thread(t).start(); 
		new Thread(t).start(); 
		 
	}

}

class sync_Thread implements Runnable{
	private int tickets = 20;
	private boolean flag = true;
	
	public void run(){
		while(flag){
			synchronized(this){
			  if(tickets>0){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"����Ʊ"+tickets--);
				}
			  else{
				  flag = false; 
			  }
			}
		}
	}
}

