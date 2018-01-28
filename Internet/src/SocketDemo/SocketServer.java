package SocketDemo;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws Exception {
        new TCPServer().listen();      //����TCPServer���󣬲�����listen��������
    }
}
//TCP��������
class TCPServer{
    private static final int PORT= 7788;//����һ���˿ں�

    public void listen() throws Exception{ //����һ��listen�����������׳�һ���쳣
        ServerSocket serverSocket = new ServerSocket(PORT);//����ServerSocket����
        Socket client=serverSocket.accept();  //����ServerSocket��accept����������������
        OutputStream os = client.getOutputStream(); //��ȡ�ͻ��˵������
        System.out.println("��ʼ��ͻ��˽�������");
        os.write(("Java��ӭ�㣡").getBytes());
        Thread.sleep(5000);      //ģ��ִ����������ռ�õ�ʱ��
        System.out.println("������ͻ��˽�������");
        os.close();
        client.close();
    }
}