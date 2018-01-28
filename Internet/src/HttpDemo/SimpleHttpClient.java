package HttpDemo;
 

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
 
public class SimpleHttpClient {
  // ʹ��HttpClient��ȡ����԰��ҳ
  public static void main(String[] args) throws ClientProtocolException, IOException {
    String targetUrl = "http://www.cnblogs.com/";
     
    // 1.����HttpClient����
    CloseableHttpClient client = HttpClients.createDefault();
 
    // 2.����Get����
    HttpGet get = new HttpGet(targetUrl);
 
    // 3.����Get����
    CloseableHttpResponse res = client.execute(get);
 
    // 4.����������
    if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      HttpEntity entity = res.getEntity();
      ContentType contentType = ContentType.getOrDefault(entity);
      Charset charset = contentType.getCharset();
      String mimeType = contentType.getMimeType();
      // ��ȡ�ֽ�����
      byte[] content = EntityUtils.toByteArray(entity);
      if (charset == null) {
        // Ĭ�ϱ���ת���ַ���
        String temp = new String(content);
        String regEx = "(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(temp);
        if (m.find() && m.groupCount() == 1) {
          charset = Charset.forName(m.group(1));
        } else {
          charset = Charset.forName("ISO-8859-1");
        }
      }
      System.out.println(new String(content, charset));
    }
 
  }
 
}