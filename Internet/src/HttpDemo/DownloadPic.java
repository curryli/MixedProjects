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
            url = new URL("http://b.zol-img.com.cn/desk/bizhi/image/6/1440x900/1439952540956.jpg");//资源地址
            connection = (HttpURLConnection) url.openConnection();//打开连接
            code = connection.getResponseCode();//返回的状态码
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(code == HttpURLConnection.HTTP_OK){//访问成功
            InputStream in;
            try {
                in = connection.getInputStream();//得到资源对象
                byte[] b = new byte[1024];//设置缓冲区及大小
                int len = -1;
                //将图片保存到磁盘
                OutputStream out = new FileOutputStream("风景.jpg");
                while ((len = in.read(b)) != -1) {
                    out.write(b, 0, len);
                }
                in.close();
                out.close();
                System.out.println("下载完成！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}