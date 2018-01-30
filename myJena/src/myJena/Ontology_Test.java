package myJena;
//本体操作
import java.util.Iterator;  

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
  
public class Ontology_Test {  
      
    public static void main(String[] args) {  
        //用Jena处理本体首先就是要建立一个本体模型 
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);  //使用OWL语言、基于内存，支持RDFS推理。内存模型就是只在程序运行时存在的模型，它没有将数据写回磁盘文件或者数据库表
          
        ontModel.read("file:./food.owl");  // 读取当前路径下的文件，加载模型
          
       // 定义一个类作为模型中Food类的等价类，并添加注释
        OntClass cls = ontModel.createClass(":FoodClass");  
        cls.addComment("the EquivalentClass of Food...", "EN");  
          
      // 通过完整的URI取得模型中的Food类
        OntClass oc = ontModel.getOntClass("http://www.w3.org/2001/sw/WebOnt/guide-src/food#ConsumableThing");  
        oc.addEquivalentClass(cls);   // 将先前定义的类添加为Food的等价类
          
     // 迭代显示模型中的类，在迭代过程中完成各种操作
        for (Iterator<OntClass> i = ontModel.listClasses(); i.hasNext(); ) {  
            OntClass c = i.next();  
            if (!c.isAnon()) {  //测试c是否匿名  
                System.out.print("Class");  
                System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()));  
                  
                
              // 处理Food类
                if (c.getLocalName().equals("ConsumableThing")) {  
                    System.out.println("  URI@" + c.getURI());  // 输出它的完整URI                 
                    System.out.println("Food's EquivalentClass is " + c.getEquivalentClass());  // 取得它的的等价类并打印
                    System.out.println("[Comments:" + c.getEquivalentClass().getComment("EN")  + "]");      // 输出等价类的注释
                }  
                  
                
             // 迭代显示当前类的直接父类
                for (Iterator<OntClass> it = c.listSuperClasses(); it.hasNext(); ) {  
                    OntClass sp = it.next();  
                    String str = c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI());  // 获取URI
                    String strSP = sp.getURI();  
                    try {   // 另一种简化处理URI的方法
                        str = str + ":" + strSP.substring(strSP.indexOf('#') + 1);  
                        System.out.println("  Class" + str);  
                    } catch (Exception e) {}  
                }  
                  
             // 迭代显示当前类的直接子类
                for (Iterator<OntClass> it = c.listSubClasses(); it.hasNext(); ) {  
                    System.out.print("  Class");  
                    OntClass sb = it.next();  
                    System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()) +  
                            "'s suberClass is " + sb.getModel().getGraph().getPrefixMapping().shortForm(sb.getURI()));  
                }  
             // 迭代显示与当前类相关的所有属性    
                for (Iterator<OntProperty> ipp = c.listDeclaredProperties(); ipp.hasNext(); ) {  
                    OntProperty p = ipp.next();  
                    System.out.println("  associated property: " + p.getLocalName());  
                }  
                /**/  
            }  
            else {}  // 是匿名类
        }  
    }  
}  
