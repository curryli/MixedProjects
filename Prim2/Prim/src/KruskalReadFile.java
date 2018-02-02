import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;


public class KruskalReadFile {
	  /** 
     * 求最小树的Kruskal算法 
     * 算法思想：克鲁斯卡尔算法从另一个途径求网中的最小生成树。假设联通网N=(V,{E})，则令 
     * 最小生成树的厨师状态为只有n个顶点而无边的非连通图T=(V,{})，途中每个顶点自称一个连通分量。 
     * 在E中选择代价最小的边，若该边衣服的顶点落在T中不同的连通分量上，则将此边加入到T中，否则舍去此边 
     * 而选择下一条最小的边。以此类推，直至T中所有的顶点都在同一连通分量上为止。 
     * @param v 图中的节点集合 
     * @param e 图中边的集合 
     */  
	public static void KRUSKAL(HashSet<Long> v, ArrayList<Edge> E){  
		Collections.sort(E);//将边按照权重w升序排序  
        ArrayList<HashSet> vSets=new ArrayList<HashSet>();  
        for(Long id: v){  
            HashSet<Long> set=new HashSet<Long>();  
            set.add(id);  
            vSets.add(set);            
        }  
        
        System.out.println(E); 
        
        System.out.println(vSets); 
		 
        for(int n=0;n<E.size();n++){  
            Long src=E.get(n).i,dst=E.get(n).j;  
            int counti=-1,countj=-1;  
            
            for(int j=0;j<vSets.size();j++){  
                HashSet set=vSets.get(j);  
                if(set.contains(src)){  
                    counti=j;  
                }  
                      
                if(set.contains(dst)){  
                    countj=j;  
                }  
            }   
            
            if(counti!=countj){   //不含圈
                System.out.println("<"+ src + "," + dst + "> w="+E.get(n).w); 
                HashSet<Long> setj=vSets.get(countj);  
                System.out.println(setj); 
                vSets.remove(countj);  
                 
                HashSet<Long> seti=vSets.get(counti); 
              System.out.println(seti);
                vSets.remove(counti);  
                seti.addAll(setj);  
                vSets.add(seti);  
                 System.out.println(vSets);
            } 
        }  
	}
      
    public static void main(String [] args){  
    	HashSet<Long> V= new HashSet<Long> ();
    	ArrayList<Edge> E= new ArrayList<Edge>();
        
    	File file = new File("test.csv");
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            
          
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                //System.out.println("line " +  tempString);
                
                Long srcId = Long.parseLong(tempString.split("\\t")[0]);
                Long dstId = Long.parseLong(tempString.split("\\t")[1]);
                Double weight =  1/Double.parseDouble(tempString.split("\\t")[2]);
                E.add(new Edge(srcId,dstId,weight));
                V.add(srcId);
                V.add(dstId);
                
        
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("读文件完成.");
                } catch (IOException e1) {
                }
            }
        }
        
         
        KRUSKAL(V, E);  
    }  
  

}
