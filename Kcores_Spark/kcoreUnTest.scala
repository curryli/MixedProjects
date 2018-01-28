package graphx.test

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{Path, FileSystem}
import org.apache.spark.SparkContext
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

object SimpleIterAppTriggersStackOverflow {

  def main(args: Array[String]) {

    val sc = new SparkContext("local[2]", "Kcore")
    val checkpointPath = "D:\\data\\checkpoint"
    sc.setCheckpointDir(checkpointPath)


    val edges: RDD[(Long, Long)] =
      sc.parallelize(Array(
        (1L, 17L), (2L, 4L),
        (3L, 4L), (4L, 17L),
        (4L, 16L), (5L, 15L),
        (6L, 7L), (7L, 15L),
        (8L, 12L), (9L, 12L),
        (10L, 12L), (11L, 12L),
        (12L, 18L), (13L, 14L),
        (13L, 17L), (14L, 17L),
        (15L, 16L), (15L, 19L),
        (16L, 17L), (16L, 18L),
        (16L, 19L), (17L, 18L),
        (17L, 19L), (18L, 19L)))


    val graph = Graph.fromEdgeTuples(edges, 1).cache()

    var degreeGraph = graph.outerJoinVertices(graph.degrees) {
      (vid, vd, degree) => degree.getOrElse(0)
    }.cache()

    var filteredCount = 0L
    var iters = 0

    val kNum = 5
    val checkpointInterval = 10

    do {

      val subGraph = degreeGraph.subgraph(vpred = (vid, degree) => degree >= kNum).cache()

      val preDegreeGraph = degreeGraph
      degreeGraph = subGraph.outerJoinVertices(subGraph.degrees) {
        (vid, vd, degree) => degree.getOrElse(0)
      }.cache()

      if (iters % checkpointInterval == 0) {

        try {
          val fs = FileSystem.get(new Configuration())
          if (fs.exists(new Path(checkpointPath)))
            fs.delete(new Path(checkpointPath), true)
        } catch {
          case e: Throwable => {
            e.printStackTrace()
            println("Something Wrong in GetKCoreGraph Checkpoint Path " + checkpointPath)
            System.exit(0)
          }
        }

        degreeGraph.edges.checkpoint()
        degreeGraph.vertices.checkpoint()

      }

      val dVertices = degreeGraph.vertices.count()
      val dEdges = degreeGraph.edges.count()

      println("[Iter " + iters + "] dVertices = " + dVertices + ", dEdges = " + dEdges)

      filteredCount = degreeGraph.vertices.filter {
        case (vid, degree) => degree < kNum
      }.count()

      preDegreeGraph.unpersistVertices()
      preDegreeGraph.edges.unpersist()
      subGraph.unpersistVertices()
      subGraph.edges.unpersist()


      iters += 1
    } while (filteredCount >= 1L) 

    println(degreeGraph.vertices.count())
  }
}