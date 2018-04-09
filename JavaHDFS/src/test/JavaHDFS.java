package test;

import java.io.BufferedReader;
import java.io.IOException;  
import java.io.InputStreamReader;
import java.net.URI;  
  


import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.FSDataInputStream;  
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.fs.Path;  

public class JavaHDFS {
	    
	   public void WriteFile(String hdfs,  Configuration cfg) throws IOException {
	      FileSystem fs = FileSystem.get(URI.create(hdfs),cfg);  
	   	  FSDataOutputStream hdfsOutStream = fs.create(new Path(hdfs));
	   	  hdfsOutStream.writeChars("Hello world!\n");
	   	  hdfsOutStream.close();
	   	  fs.close();  
	   	 }
	   	 
	   public void ReadFile(String hdfs,  Configuration cfg) throws IOException {
	      FileSystem fs = FileSystem.get(URI.create(hdfs),cfg);  
	      FSDataInputStream hdfsInStream = fs.open(new Path(hdfs)); 
	   	  
	   	  byte[] ioBuffer = new byte[1024];
	   	  int readLen = hdfsInStream.read(ioBuffer);
	   	  while(readLen!=-1)
	   	  {
	   	   System.out.write(ioBuffer, 0, readLen);
	   	   readLen = hdfsInStream.read(ioBuffer);
	   	  }
	   	  hdfsInStream.close();
	   	  fs.close(); 
	   	 }
	   
	   public void ReadDir(String hdfs,  Configuration cfg) throws IOException {
		      FileSystem fs = FileSystem.get(URI.create(hdfs),cfg);  
		      FileStatus fileList[] = fs.listStatus(new Path(hdfs));  
		      
		      BufferedReader br = null;
		      for (FileStatus file : fileList) { 
//		    	  if (!file.getPath().getName().startsWith("part-")) {
//		                continue;
//		            }

		        FSDataInputStream hdfsInStream = fs.open(file.getPath());
		        br = new BufferedReader(new InputStreamReader(hdfsInStream));  
		    	  
		        String line = null;
	            while (null != (line = br.readLine())) {
	        	  System.out.println(line);
	            }// end of while    
		   	  hdfsInStream.close();
		   	 }
		      
			  fs.close(); 
		   }
	
	
	public static void main(String[] args) {  
	        try {  
	        String hdfs = "hdfs://nameservice1/user/hdanaly/xrli/graphTest2.txt";                //hadoop fs -ls hdfs://nameservice1/user/hdanaly/xrli/graphTest.txt    由于集群进行了相关设置，"xrli/graphTest.txt"也行，
	        Configuration cfg = new Configuration();  
	        cfg.set("fs.defaultFS", "hdfs://nameservice1");
	        cfg.set("dfs.nameservices", "nameservice1");
	        cfg.set("dfs.ha.namenodes.nameservice1", "namenode1285,namenode1176");
	        cfg.set("dfs.client.failover.proxy.provider.nameservice1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
	        cfg.set("dfs.namenode.rpc-address.nameservice1.namenode1285", "bB0203002:8020");   ///etc/hadoop/conf    到hdfs-site.xml文件里找8020端口（默认的namenode RPC交互端口进行配置）  
	        cfg.set("dfs.namenode.rpc-address.nameservice1.namenode1176", "bB0103002:8020");  //有两个8020端口，都配置进去HDFS 的HA 功能通过配置Active/Standby 两个NameNodes 实现在集群中对NameNode 的热备

	        JavaHDFS t = new JavaHDFS();  
	        t.WriteFile(hdfs,cfg);
	        System.out.println("Write done.");
	        t.ReadFile(hdfs,cfg);
	        System.out.println("Read done.");
	        
	        
	        String hdfsdir = "hdfs://nameservice1/user/hdanaly/xrli/hdfstest";  
	        t.ReadDir(hdfsdir,cfg);
	        System.out.println("Read dir done.");
	        
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	          
	    }  
	  
	}  


//java -cp JavaHDFS.jar -Djava.ext.dirs=hdfsjar test.JavaHDFS   由于不是用hadoop命令运行的，直接使用java命令，而集群本地机器上上没有Hadoop相关jar包，需要把以下jar包先上传到集群上的，这里全放在了统一的目录下hdfsjar
//hdanaly@bB0105014:~/xrli/graphx/GStream> ls hdfsjar     手动放到服务器本地目录上
//commons-cli-1.2.jar            hadoop-auth-2.6.0-cdh5.6.0.jar     protobuf-java-2.5.0.jar
//commons-collections-3.2.2.jar  hadoop-common-2.6.0-cdh5.6.0.jar   servlet-api-2.5.jar
//commons-configuration-1.6.jar  hadoop-hdfs-2.6.0-cdh5.6.0.jar     slf4j-api-1.7.5.jar
//commons-lang-2.6.jar           htrace-core4-4.0.1-incubating.jar  slf4j-log4j12.jar
//commons-logging-1.1.3.jar      JavaHDFS.jar
//guava-11.0.2.jar               log4j-1.2.17.jar



//或者 hadoop jar JavaHDFS.jar test.JavaHDFS -Dmapreduce.job.queuename=root.default  就不用放jar包了