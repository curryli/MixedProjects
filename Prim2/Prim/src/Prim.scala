import scala.collection.BitSet
import scala.util.Random

object Prim {
  
  class Edge(val from: Int, val to: Int, val w: Int) {
    override def toString(): String = {
      return "%d -> %d (%d)".format(from, to, w)
    }
  }
  
  def kruskal(n: Int, graph: List[Edge]): Long = {
    val par = (0 to n + 1).toArray
    
    val R = new Random()
    
    def getParent(p: Int): Int = if (par(p) == p) p else getParent(par(p))
    
    def same(l: Int, r: Int): Boolean = getParent(l) == getParent(r)
    
    def merge(l: Int, r: Int) {
      val lp = getParent(l)
      val rp = getParent(r)
      if (R.nextInt(2) == 0) {
    	  par(lp) = rp
      } else {
    	  par(rp) = lp
      }
    }
    
    val sorted = graph.sortBy(_.w)
    var q = 0
    var ans = 0L
    for (e <- sorted) {
      if (!same(e.from, e.to)) {
        q += 1
        ans += e.w
        merge(e.from, e.to)
      }
    }
    ans
  }
  
  def prim(n: Int, graph: List[Edge]): Long = {
    var visited = BitSet()
    val dist = Array.fill(n + 1)(Int.MaxValue)
    var ans = 0L
    var q = 0
    dist(1) = 0
    do {
      val to = dist.zipWithIndex.filter(x => !visited(x._2)).min
      val cur = to._2
      visited += cur
      ans += to._1
      q += 1
      val adj = graph filter (x => x.from == cur || x.to == cur)
      for (e <- adj) {
    	  val another = if (e.from == cur) e.to else e.from
    	  dist(another) = dist(another).min(e.w)
      }
    } while (visited.size != n)
    ans
  }
  
  def readGraph() : (Int, List[Edge]) = {
    val source = io.Source.fromURL("http://spark-public.s3.amazonaws.com/algo2/datasets/edges.txt")
    val lines = source.getLines
    val data = lines.map(x => x.split(" ").map(_.toInt).toList).toList
    val n = data.head(0)
    val m = data.head(1)
    val edges = data.tail map (x => new Edge(x(0).min(x(1)), x(0).max(x(1)), x(2)))
    (n, edges)
  }
  
  def main(args: Array[String]) {
    val g = readGraph()
    println(prim(g._1, g._2))
    println(kruskal(g._1, g._2))
  }
}