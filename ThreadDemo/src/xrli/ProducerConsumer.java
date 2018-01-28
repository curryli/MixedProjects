package xrli;

//Producer�����߹���˼������������ݵ��̣߳�Consumer�����߾���ʹ�����ݵ��̡߳������ж�������ߣ�Ҳ�����ж�������ߣ��������ߺ������߶���һ����ʱ���ֽ����ܵ�Pipe Pattern��
//�����д�˸����ӣ�һ���̼߳�1��һ���̼߳�1��һ�������ߡ�һ�������ߣ�������������1��������������1����������Ѿ���֤ok��


//Info���ݽṹ�����������������ߺ�������ʹ�õ�����

class Info {

    private int count;

    private boolean flag;

    public synchronized void set() {

            if(flag) {

                try {

                        super.wait();  //������û��super������ν��  Java ��ͨ�� Object ��� wait��notify��notifyAll �⼸��������ʵ���̼߳��ͨ�ŵģ�����Ϊ���е��඼�Ǵ� Object �̳еģ������κ�   �඼����ֱ��ʹ����Щ������

                } catch(Exception e) {

                        e.printStackTrace();

                }

            }

            System.out.println("Producer Inc 1 --- " + (++count));

            try {

                Thread.sleep(100);

            } catch(Exception e) {

                e.printStackTrace();

            }

            flag = true;

            super.notify();

    }

    public synchronized void get() {

            if(!flag) {

                try {

                        super.wait();

                } catch(Exception e) {

                        e.printStackTrace();

                }

            }

            System.out.println("Consumer Dec 1 --- " + (--count));

            try {

                Thread.sleep(100);

            } catch(Exception e) {

                 e.printStackTrace();

            }

            flag = false;

            super.notify();

    }

}

//Producer������:

class Producer implements Runnable {

    private Info info = null;

    public Producer(Info info) {

            this.info = info;

    }

    @Override

    public void run() {

            for(int i=0; i<20; i++) {

                this.info.set();

            }

    }

}

 

//Consumer�����ߣ�

class Consumer implements Runnable {

    private Info info = null;

    public Consumer(Info info) {

            this.info = info;

    }

    @Override

    public void run() {

            for(int i=0; i<20; i++) {

                this.info.get();

            }

    }

}

 

//������Test��

public class ProducerConsumer {

    public static void main(String[] args) {

            Info info = new Info();
 
            Producer producer = new Producer(info);

            new Thread(producer).start();
 
            Consumer consumer = new Consumer(info);

            new Thread(consumer).start();

    }

}
