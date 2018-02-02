package myJena;

import org.apache.jena.rdf.model.StmtIterator;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

public class Navigate {
	static final String inputFileName = "data/vc-db-1.rdf";
    static final String johnSmithURI = "http://somewhere/JohnSmith/";
    
    public static void main (String args[]) {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();
       
        // use the FileManager to find the input file
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException( "File: " + inputFileName + " not found");
        }
         
        // read the RDF/XML file
        model.read(new InputStreamReader(in), "");
        
        // retrieve the Adam Smith vcard resource from the model
        Resource vcard = model.getResource(johnSmithURI);

        //FN表示一个VCARD对象的名称，N表示这个对象名称的组成部分. 一个vcard对象必须包含FN类型。
        // retrieve the value of the N property  
        Resource name = (Resource) vcard.getRequiredProperty(VCARD.N).getObject();
        System.out.println("The name is:" + name);
        
        // retrieve the given name property
        String fullName = vcard.getRequiredProperty(VCARD.FN).getString();
        System.out.println("The fullName is:" + fullName);
        
        // add two nick name properties to vcard
        // 获取johnSmithURI资源后，对其添加两个nickname属性
        vcard.addProperty(VCARD.NICKNAME, "Smithy")
             .addProperty(VCARD.NICKNAME, "Adman");
        
        // set up the output
        System.out.println("The nicknames of \"" + fullName + "\" are:");
        // list the nicknames
        //返回声明迭代器，获取对象
        StmtIterator iter = vcard.listProperties(VCARD.NICKNAME);
        while (iter.hasNext()) {
            System.out.println(iter.nextStatement().getObject().toString());
        }
    }
 
}
