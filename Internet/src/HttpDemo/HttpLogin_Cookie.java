package HttpDemo;
 
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
 
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
 
/**
 * ģ���½֪��
 */
public class HttpLogin_Cookie {
	
    private static CloseableHttpResponse res = null;
    
    private static String getRedirectLocation() { 
        /*��ȡ��Ӧ��ͷ url*/ 
        Header locationHeader = res.getFirstHeader("Location");  
        if (locationHeader == null) {  
            return null;  
        }  
        return locationHeader.getValue();  
    }  
    
 
  public static void main(String[] args) throws java.text.ParseException {
 
     
    // ȫ����������
    RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
    // ����cookie store�ı���ʵ��
    CookieStore cookieStore = new BasicCookieStore();
    // ����HttpClient������
    HttpClientContext context = HttpClientContext.create();
    context.setCookieStore(cookieStore);
 
    // ����һ��HttpClient
    CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
        .setDefaultCookieStore(cookieStore).build();
 
 
    // �������ص�HTTP����
    try {
      try {
        // ����һ��get����������ȡ��Ҫ��Cookie����_xsrf��Ϣ
        HttpGet get = new HttpGet("http://www.renren.com/PLogin.do");
 
        res = httpClient.execute(get, context);
        // ��ȡ����Cookie,����_xsrf��Ϣ
        System.out.println("����֪����ҳ��Ļ�ȡ�ĳ���Cookie:===============");
        for (Cookie c : cookieStore.getCookies()) {
          System.out.println(c.getName() + ": " + c.getValue());
        }
        res.close();
 
        // ����post����
        List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
        
        valuePairs.add(new BasicNameValuePair("domain", "renren.com"));  
        valuePairs.add(new BasicNameValuePair("isplogin", "true"));  
        valuePairs.add(new BasicNameValuePair("submit", "��¼"));  
        valuePairs.add(new BasicNameValuePair("email", "17612133298"));  
        valuePairs.add(new BasicNameValuePair("password", "1234567ok"));  
         
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
//        entity.setContentType("application/x-www-form-urlencoded");
 
        // ����һ��post����
        HttpPost post = new HttpPost("http://www.renren.com/PLogin.do");
        // ע��post����
        post.setEntity(entity);
        res = httpClient.execute(post, context);
 
        // ��ӡ��Ӧ��Ϣ���鿴�Ƿ��½�Ƿ�ɹ�
        System.out.println("��ӡ��Ӧ��Ϣ===========");

        res.close();
 
        System.out.println("��½�ɹ���,�µ�Cookie:===============");
        for (Cookie c : context.getCookieStore().getCookies()) {
          System.out.println(c.getName() + ": " + c.getValue());
        }
 
        
        String newpage = getRedirectLocation();
        System.out.println("��¼�ɹ�����ת���µ�ҳ��:" + newpage);
//        // ����һ���µ�get����(ֱ���ñ����cookie�������û�����������)���������Ե�¼�Ƿ�ɹ�
//        //HttpGet newGet = new HttpGet("http://www.renren.com/Home.do");        
        HttpGet newGet = new HttpGet(newpage);
        res = httpClient.execute(newGet, context);
        String content = EntityUtils.toString(res.getEntity());
        System.out.println("��½�ɹ�����ʵ�ҳ��===============");
        System.out.println(content);
        res.close();
 
      } finally {
        httpClient.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}