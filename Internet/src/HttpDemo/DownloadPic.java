package HttpDemo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadPic {

    /**
     * @param args
     */
    public static void main(String[] args) {
        URL url;
        HttpURLConnection connection = null;
        int code = 0;
        try {
            url = new URL("http://b.zol-img.com.cn/desk/bizhi/image/6/1440x900/1439952540956.jpg");//��Դ��ַ
            connection = (HttpURLConnection) url.openConnection();//������
            code = connection.getResponseCode();//���ص�״̬��
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(code == HttpURLConnection.HTTP_OK){//���ʳɹ�
            InputStream in;
            try {
                in = connection.getInputStream();//�õ���Դ����
                byte[] b = new byte[1024];//���û���������С
                int len = -1;
                //��ͼƬ���浽����
                OutputStream out = new FileOutputStream("�羰.jpg");
                while ((len = in.read(b)) != -1) {
                    out.write(b, 0, len);
                }
                in.close();
                out.close();
                System.out.println("������ɣ�");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}