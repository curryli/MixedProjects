package myJena;
//TDB是Jena提供的两个subsystem之一，另外一个叫做SDB。TDB使用triple store的形式对RDF数据提供持久性存储（persistent store）, SDB则支持使用传统的关系数据库存储RDF数据(例如mysql)
// 官方推荐的则是TDB，速度快，操作简单，支持几十亿条记录，且支持几百个并行查询。
//一是：把jena的版本回归到2.5之前的版本，其中会有com.hp.jena.db.*的类。
//二是：采用新jena版本中的sdb包，这个包究竟是不是上面的替代，还不知道。

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.RDF;

import org.apache.jena.jdbc.*;
import org.apache.jena.jdbc.JenaJDBC;

public class Jena_mysql {
     

}