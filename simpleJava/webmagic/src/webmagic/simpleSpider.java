package webmagic;

import us.codecraft.webmagic.Site;  
import us.codecraft.webmagic.model.ConsolePageModelPipeline;  
import us.codecraft.webmagic.model.OOSpider;  
import us.codecraft.webmagic.model.annotation.ExtractBy;  
import us.codecraft.webmagic.model.annotation.TargetUrl;  
  
  
@TargetUrl("http://a.qidian.com/")  
@ExtractBy(value = "//ul[@class=\"all-img-list cf\"]/li",multi = true)  
public class simpleSpider {  
    @ExtractBy("//div[@class=book-mid-info]/h4/a/text()")  
    private String title;  
    @ExtractBy("//div[@class=book-mid-info]/p[@class=author]/a[@class=name]/text()")  
    private String author;  
  
    @ExtractBy("//div[@class=book-mid-info]/p[@class=author]/a[@class=go-sub-type]/text()")  
    private String type;  
    @ExtractBy("//div[@class=book-mid-info]/p[@class=author]/span/text()")  
    private String status;  
  
    @ExtractBy("//div[@class=book-mid-info]/p[@class=intro]/text()")  
    private String intro;  
    @ExtractBy("//div[@class=book-mid-info]/p[@class=update]/span/text()")  
    private String count;  
    public static void main(String[] args) {  
//        OOSpider.create(Site.me(), new ConsolePageModelPipeline(), Qidian.class).addUrl("http://a.qidian.com/").thread(4).run();  
       OOSpider ooSpider = OOSpider.create(Site.me().setSleepTime(100), new ConsolePageModelPipeline(), simpleSpider.class);  
  
       simpleSpider qidian= ooSpider.get("http://a.qidian.com/");  
  
        System.out.println(qidian);  
    }  
  
}  