package myJena;
/* 基于 Jena 的本体构建方法
 * 为了更好的使用Jena API，我们应该对OWL有个大致的了解。OWL核心是围绕三元组的，即我们在很多资料中看到的陈述（Statement），
 * 它的组成为：Statement=(Subject,Property,Object)，其中Subject我们熟成为主体，Property成为属性，Object成为客体。
 * 在编程中容易让人搞混的他们和Individual的区别。Individual熟称为个体。它是Subject、Object的一个实例，
 * 例如在Statement=（Animals,Eat,Plants）陈述中sheep是Animals的一个Individual，grass是Plants的一个Individual。
 * 从这个角度上来说我们理解为什么主体、客体被说成是类（Class）。
 * 而这一切的主体、客体、个体、类、属性等我们都可以称为资源（Resource）。
 * 在编程中OWL常用的是OntModel,Ontclass,OntProperty,Individual。
 * OntClass可以用来创建主体，客体，OntPropety用来创建属性，Individual用来创建个体。 
 * 常用的方法有CreatClass,CreatIndividual,listObjects,listObjectsofProperty,listSubjectswithProperty,listPropertyvalues等，
 * 具体可以参照下面的一个简单例子。
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.rdf.model.Resource;
 

public class CreateClass {
	  
    public static void main(String[] args) {  
    		OntModel ontmodel=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    		//namespace  一个标准的本体开头部分里包括一组XML命名空间（namespace）声明（被包含在rdf:RDF标签里）。 提供了一种无歧义地解释标识符的方式
    		String NS="http://OWLTEST/Things#";
    		
    	//1、OntClass可以用来创建主体，客体
    		//动物类和动物名称类
    		OntClass animals=ontmodel.createClass(NS+"Animals");
    		OntClass animalsname=ontmodel.createClass(NS+"AnimalsName");
    		//植物类和植物名称类
    		OntClass plants=ontmodel.createClass(NS+"Plants");
    		OntClass plantsname=ontmodel.createClass(NS+"PlantsName");
    		
    	//2、OntPropety用来创建属性
    		//动物类和植物类的对象关系rant
    		OntProperty rant=ontmodel.createObjectProperty(NS+"Rant");
    	 
    	 	OntProperty hasanimalsname=ontmodel.createObjectProperty(NS+"HasAnimalsName");
    		OntProperty hasplantsname=ontmodel.createObjectProperty(NS+"HasPlantsName");
    	 
    	 	OntProperty hasname=ontmodel.createDatatypeProperty(NS+"HasName");
    		
    	//3、将属性和类关联起来
    	 	//将属性 hasanimalsname 的定义域设 为animals类 ， 值域设为animalsname类。 
    	 	//即 hasanimalsname 只可 以用于这样 的 RDF陈述:  [animals类实例 ， hasanimalsname，animalsname类实例】。
    	 	hasanimalsname.addDomain(animals);
    		hasanimalsname.addRange(animalsname);
    	 
    	 	hasplantsname.addDomain(plants);
    		hasplantsname.addRange(plantsname);
    	 
    	 	rant.addDomain(animals);
    		rant.addRange(plants);
    	 
    	 	hasanimalsname.addDomain(animals);
    		hasanimalsname.addRange(animalsname);
    	 
    	 	hasplantsname.addDomain(plants);
    		hasplantsname.addRange(plantsname);
    	 
    	 	hasname.addDomain(animalsname);
    		hasname.addDomain(plantsname);
    	 
    		
    	//4、创建实例 （并填充其属性值（不是必须））
    		//下面创建了一个 叫"A"的animals类实例。
    	 	Individual a=animals.createIndividual(NS+"A");
    		Individual b=animals.createIndividual(NS+"B");
    		//下面创建了一个 叫"Sheep"的animalsname类实例。
    	 	Individual sheep=animalsname.createIndividual(NS+"Sheep");
    		Individual horse=animalsname.createIndividual(NS+"Horse");
    	  
    		
    		//一切的主体、客体、个体、类、属性等我们都可以称为资源（Resource），资源都可以用addProperty 方法添加属性
    	 	//为个体实例添加属性
    		a.addProperty(hasanimalsname, sheep);
    	 	
    		//为类添加属性
    	 	animalsname.addProperty(hasname, sheep);
    		animalsname.addProperty(hasname, horse);
    		
    		Individual grass=plants.createIndividual(NS+"Grass");
    	 	System.out.println(ontmodel);
    		
    		System.out.println("..............................");
    	 
    		for(Iterator i=ontmodel.listObjects();i.hasNext();){
    			Resource r=(Resource)i.next();
    			System.out.println(r.getLocalName());
    		}
    		System.out.println("..............................");
    	 
    	 	for (Iterator i=ontmodel.listIndividuals();i.hasNext();){
    			Individual ind=(Individual)i.next();
    			System.out.println(ind.getLocalName());
    		}
    		System.out.println("..............................");
    	 
    	 	for (Iterator i=ontmodel.listObjectProperties();i.hasNext();){
    			Property p=(Property)i.next();
    			System.out.println("Property:"+p.getLocalName());
    			for (Iterator j=ontmodel.listObjectsOfProperty(p);j.hasNext();){
    				Resource node=(Resource)j.next();
    				System.out.println("Objects:"+node.getLocalName());
    			}
    		}
    		System.out.println("..............................");
    	 
    	 	for (Iterator i=animalsname.listPropertyValues(hasname);i.hasNext();){
    			Resource value=(Resource)i.next();
    			System.out.println("HasName's values:"+value.getLocalName());
    		}
    		System.out.println("..............................");
    	}
    
}
