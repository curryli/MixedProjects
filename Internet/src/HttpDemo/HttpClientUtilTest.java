package HttpDemo;
import java.io.File;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;  
  
public class HttpClientUtilTest {  
	public static void main(String args[]){
//		testSendHttpsGet1();
//		testSendHttpGet2();
//		testSendHttpPost1();
//		testSendHttpPost2();
		testSendHttpPost3();
//		testSendHttpPost4();
	}
       
    public static void testSendHttpsGet1() {  
        String responseContent = HttpClientUtil.getInstance()  
                .sendHttpsGet("https://www.baidu.com");  
        System.out.println("reponse content:" + responseContent);  
    }  
    
	
    public static void testSendHttpGet2() {  
        String responseContent = HttpClientUtil.getInstance()  
                .sendHttpGet("http://localhost:8089/test/send?username=test01&password=123456");  
        System.out.println("reponse content:" + responseContent);  
    }  
        

    
    
    public static void testSendHttpPost1() {  
        String responseContent = HttpClientUtil.getInstance()  
                .sendHttpPost("http://localhost:8089/test/send?username=test01&password=123456");  
        System.out.println("reponse content:" + responseContent);  
    }  
       
    public static void testSendHttpPost2() {  
        String responseContent = HttpClientUtil.getInstance()  
                .sendHttpPost("http://localhost:8089/test/send", "username=test01&password=123456");  
        System.out.println("reponse content:" + responseContent);  
    }  
       
    public static void testSendHttpPost3() {  
        Map<String, String> maps = new HashMap<String, String>();  
        maps.put("domain", "renren.com");  
        maps.put("isplogin", "true");  
        maps.put("submit", "登录");  
        maps.put("email", "17612133298");  
        maps.put("password", "1234567ok");  
 
        String responseContent = HttpClientUtil.getInstance()  
                .sendHttpPost("http://www.renren.com/PLogin.do", maps);  //人人网对于登录成功的用户跳转到该请求返回的一个新网址中：
        System.out.println("reponse content:" + responseContent);  
    }  

    public static void testSendHttpPost4() {  
        Map<String, String> maps = new HashMap<String, String>();  
        maps.put("username", "test01");  
        maps.put("password", "123456");  
        List<File> fileLists = new ArrayList<File>();  
        fileLists.add(new File("D://test//httpclient//1.png"));  
        fileLists.add(new File("D://test//httpclient//1.txt"));  
        String responseContent = HttpClientUtil.getInstance()  
                .sendHttpPost("http://localhost:8089/test/sendpost/file", maps, fileLists);  
        System.out.println("reponse content:" + responseContent);  
    }  
    

    
  
}  
  