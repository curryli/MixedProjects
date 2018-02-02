import org.yaml.snakeyaml.Yaml;    //下载jar包      http://www.java2s.com/Code/Jar/y/Downloadyamljar.htm

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.text.DecimalFormat; 
 

public class HelloSnakeYAML {
    public static void test1() {
        try {
            Yaml yaml = new Yaml();
            URL url = HelloSnakeYAML.class.getClassLoader().getResource("conf.yaml");
            if (url != null) {
                Object obj = yaml.load(new FileInputStream(url.getFile()));
                System.out.println(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
    public static void test3() {
        try {
        	HashMap<String,HashMap<String,String>> cardTrans= new HashMap<String,HashMap<String,String>>();

        	HashMap<String,String> cardTrans1= new HashMap<String,String>();
             cardTrans1.put("dt", "aaaaaaaaaaaaa");
             cardTrans1.put("dm", "bbbbbbbbbbbbb");
             cardTrans1.put("cm", "ccccccccccccc");

             HashMap<String,String> cardTrans2= new HashMap<String,String>();
             cardTrans2.put("dt", "aaaaaaaaaaaaa");
             cardTrans2.put("dm", "bbbbbbbbbbbbb");
             cardTrans2.put("cm", "ccccccccccccc");
             
             cardTrans.put("card1", cardTrans1);
             cardTrans.put("card2", cardTrans2);
             
            Yaml yaml = new Yaml();
            yaml.dump(cardTrans, new FileWriter("cardTrans.yaml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void test4()  {
     try {
    	 //初始化Yaml解析器
        Yaml yaml = new Yaml();
        File f=new File("cardTrans.yaml");
        //读入文件
        Object result= yaml.load(new FileInputStream(f));
        System.out.println(result.getClass());
        System.out.println(result);
       }
     catch (Exception e) {
        e.printStackTrace();
       }
    }
 
    public static void main(String[] args)   {
    	 
    	DecimalFormat df = new DecimalFormat("######0");   

    	double d1 = 3.23456;  
    	System.out.println(df.format(d1)); 
    	
    	String temp = "10728232fca5fa139d58107756d6e5a9	moneySum: 2620000.0 moneyAvg: 655000.0 allCount: 4";
     
    	System.out.println(temp.split("\t")[0]);
    	
    	test1();
 
        test3();
        test4();
    }
}