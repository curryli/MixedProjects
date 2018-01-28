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
 * 模拟登陆知乎
 */
public class HttpLogin_Cookie {
	
    private static CloseableHttpResponse res = null;
    
    private static String getRedirectLocation() { 
        /*获取响应的头 url*/ 
        Header locationHeader = res.getFirstHeader("Location");  
        if (locationHeader == null) {  
            return null;  
        }  
        return locationHeader.getValue();  
    }  
    
 
  public static void main(String[] args) throws java.text.ParseException {
 
     
    // 全局请求设置
    RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
    // 创建cookie store的本地实例
    CookieStore cookieStore = new BasicCookieStore();
    // 创建HttpClient上下文
    HttpClientContext context = HttpClientContext.create();
    context.setCookieStore(cookieStore);
 
    // 创建一个HttpClient
    CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
        .setDefaultCookieStore(cookieStore).build();
 
 
    // 创建本地的HTTP内容
    try {
      try {
        // 创建一个get请求用来获取必要的Cookie，如_xsrf信息
        HttpGet get = new HttpGet("http://www.renren.com/PLogin.do");
 
        res = httpClient.execute(get, context);
        // 获取常用Cookie,包括_xsrf信息
        System.out.println("访问知乎首页后的获取的常规Cookie:===============");
        for (Cookie c : cookieStore.getCookies()) {
          System.out.println(c.getName() + ": " + c.getValue());
        }
        res.close();
 
        // 构造post数据
        List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
        
        valuePairs.add(new BasicNameValuePair("domain", "renren.com"));  
        valuePairs.add(new BasicNameValuePair("isplogin", "true"));  
        valuePairs.add(new BasicNameValuePair("submit", "登录"));  
        valuePairs.add(new BasicNameValuePair("email", "17612133298"));  
        valuePairs.add(new BasicNameValuePair("password", "1234567ok"));  
         
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
//        entity.setContentType("application/x-www-form-urlencoded");
 
        // 创建一个post请求
        HttpPost post = new HttpPost("http://www.renren.com/PLogin.do");
        // 注入post数据
        post.setEntity(entity);
        res = httpClient.execute(post, context);
 
        // 打印响应信息，查看是否登陆是否成功
        System.out.println("打印响应信息===========");

        res.close();
 
        System.out.println("登陆成功后,新的Cookie:===============");
        for (Cookie c : context.getCookieStore().getCookies()) {
          System.out.println(c.getName() + ": " + c.getValue());
        }
 
        
        String newpage = getRedirectLocation();
        System.out.println("登录成功后跳转到新的页面:" + newpage);
//        // 构造一个新的get请求(直接用保存的cookie，不用用户名和密码了)，用来测试登录是否成功
//        //HttpGet newGet = new HttpGet("http://www.renren.com/Home.do");        
        HttpGet newGet = new HttpGet(newpage);
        res = httpClient.execute(newGet, context);
        String content = EntityUtils.toString(res.getEntity());
        System.out.println("登陆成功后访问的页面===============");
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