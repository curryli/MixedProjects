package SocketDemo;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientServer {
    public static void main(String[] args) throws Exception{
        new TCPClient().connect();//����TCPClient���󣬲�����connect��������
    }
}
//TCP�ͻ���
class TCPClient{
    private static final int PORT=7788;//����˵Ķ˿ں�
    public void connect() throws Exception{
        //����һ��Socket�����ӵ�������ַ�Ͷ˿ںŵļ����
        Socket client = new Socket(InetAddress.getLocalHost(),PORT);
        InputStream is = client.getInputStream(); //�õ��������ݵ���
        byte[] buf = new byte[1024];  //����1024���ֽ�����Ļ�����
        int len=is.read(buf);    //�����ݶ�ȡ����������
        System.out.println(new String(buf,0,len));  //���������е��������
        client.close();       //�ر�Socket�����ͷ���Դ
    }
}