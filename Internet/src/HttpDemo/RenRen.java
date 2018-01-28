package HttpDemo;
//http://blog.csdn.net/qy20115549/article/details/52249232

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class RenRen {
     /*������ģ���½����*/
     /*��������û��������� ����������*/
    private static String userName = "17612133298";  
    private static String password = "1234567ok"; //   
    // Don't change the following URL  
    private static String renRenLoginURL = "http://www.renren.com/PLogin.do";  
    // The HttpClient is used in one session  
    private HttpResponse response;  
    private DefaultHttpClient httpclient = new DefaultHttpClient();  
    /*����ץ���Ĳ����������ݵĲ���*/
    private boolean login() {  
        HttpPost httpost = new HttpPost(renRenLoginURL);  
        // All the parameters post to the web site
       //����һ��NameValuePair���飬���ڴ洢�����͵Ĳ����������ز���������ͼ�еĲ���
        //ȷ����¼����ҪЯ���Ĳ���  �� ץ������fiddler ��һ��    http://blog.csdn.net/zq710727244/article/details/53883659
        List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
        nvps.add(new BasicNameValuePair("domain", "renren.com"));  
        nvps.add(new BasicNameValuePair("isplogin", "true"));  
        nvps.add(new BasicNameValuePair("submit", "��¼"));  
        nvps.add(new BasicNameValuePair("email", userName));  
        nvps.add(new BasicNameValuePair("password", password));  
        try {  
            /*��½�ɹ�����ȡ���ص����ݣ���html�ļ�*/
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
            response = httpclient.execute(httpost);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        } finally {  
            httpost.abort();  
        }  
        return true;  
    }  

    private String getRedirectLocation() { 
        /*��ȡ��Ӧ��ͷ url*/ 
        Header locationHeader = response.getFirstHeader("Location");  
        if (locationHeader == null) {  
            return null;  
        }  
        return locationHeader.getValue();  
    }  
     /*��ȡhtml�ı�*/ 
    private String getText(String redirectLocation) {  
        HttpGet httpget = new HttpGet(redirectLocation);  
        // Create a response handler  
        ResponseHandler<String> responseHandler = new BasicResponseHandler();  
        String responseBody = "";  
        try {  
            responseBody = httpclient.execute(httpget, responseHandler);  
        } catch (Exception e) {  
            e.printStackTrace();  
            responseBody = null;  
        } finally {  
            httpget.abort();  
            httpclient.getConnectionManager().shutdown();  
        }  
        return responseBody;  
    }  

    public void printText() {
        /*���ע��ɹ��ˣ�������Ӧ���html*/   
        if (login()) {  
            String redirectLocation = getRedirectLocation();  
            if (redirectLocation != null) {  
                System.out.println(getText(redirectLocation));  
            }  
        }  
    }  
    /*main����*/
    public static void main(String[] args) {  
        RenRen renRen = new RenRen();  
        renRen.printText();  
    }  
}