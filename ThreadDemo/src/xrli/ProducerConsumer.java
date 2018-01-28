package xrli;

//Producer生产者顾名思义就是生产数据的线程，Consumer消费者就是使用数据的线程。可以有多个生产者，也可以有多个消费者，当生产者和消费者都是一个的时候，又叫做管道Pipe Pattern。
//下面简单写了个例子，一个线程加1，一个线程减1，一个生产者、一个消费者，生产者用来加1，消费者用来减1。下面代码已经验证ok。


//Info数据结构对象，用来保存生产者和消费者使用的数据

class Info {

    private int count;

    private boolean flag;

    public synchronized void set() {

            if(flag) {

                try {

                        super.wait();  //这里有没有super都无所谓。  Java 是通过 Object 类的 wait、notify、notifyAll 这几个方法来实现线程间的通信的，又因为所有的类都是从 Object 继承的，所以任何   类都可以直接使用这些方法。

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

//Producer生产者:

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

 

//Consumer消费者：

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

 

//测试类Test：

public class ProducerConsumer {

    public static void main(String[] args) {

            Info info = new Info();
 
            Producer producer = new Producer(info);

            new Thread(producer).start();
 
            Consumer consumer = new Consumer(info);

            new Thread(consumer).start();

    }

}
