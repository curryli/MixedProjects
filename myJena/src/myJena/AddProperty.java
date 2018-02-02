package myJena;
import java.io.InputStream;

//https://www.jianshu.com/p/2709946c2f76
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

public class AddProperty {
    static final String inputFileName = "data/vc-db-1.rdf";

    public static void main(String[] args) {
        // some definitions
        String personURI = "http://somewhere/JohnSmith";
        String givenName = "John";
        String familyName = "Smith";
        String fullName = givenName + " " + familyName;

        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // create the resource
        // and add the properties cascading style
        Resource johnSmith = model.createResource(personURI)
        		.addProperty(VCARD.FN, fullName).addProperty(VCARD.N, model.createResource()
        		.addProperty(VCARD.Given, givenName).addProperty(VCARD.Family, familyName));

        
        System.out.print("iterator遍历 每行包含三个字段，表示每个语句的主题，谓词和对象:\n");
        // list the statements in the graph
        StmtIterator iter = model.listStatements();
        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();  //每一个三元组SPO打印
            Resource subject = stmt.getSubject(); // get the subject
            Property predicate = stmt.getPredicate(); // get the predicate
            RDFNode object = stmt.getObject(); // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }
        
        
        
        System.out.print("now write the model in XML form to a file:\n");
        model.write(System.out, "RDF/XML-ABBREV");  //这边是打印到System.out，也可以直接写到文件
        
        System.out.print("now write the model in N-TRIPLES form to a file:\n");
        model.write(System.out, "N-TRIPLES");
        

        System.out.print("Read a RDF file and print it in N-TRIPLES form:\n");
        Read_RDF(inputFileName);
  
    }
    
    
    public static void Read_RDF(String filename) {
    	   
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the FileManager to find the input file
        InputStream in = FileManager.get().open(filename);
       if (in == null) {
           throw new IllegalArgumentException(
                                        "File: " + filename+ " not found");
       }

       // read the RDF/XML file
       model.read(in, null);

       // write it to standard out
       model.write(System.out,"N-TRIPLES");
    }
    
    
}
