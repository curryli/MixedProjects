def predict(testData: RDD[Vector]): RDD[Double] = { //testData为测试数据集，
    val localWeights = weights 
    val bcWeights = testData.context.broadcast(localWeights) //获取权重，广播权重
    val localIntercept = intercept
    testData.mapPartitions { iter =>   //在每个分区得带这个权重值，调用predictPoint方法进行预测
      val w = bcWeights.value
      iter.map(v => predictPoint(v, w, localIntercept))
    } 
  }
  
  
def predictPoint(dataMatrix: Vector, weightMatrix: Vector, intercept: Double) = {
    //保证dataMatrix和features的特征的维度相同。
    require(dataMatrix.size == numFeatures)
 
    //如果是二元回归的话，
    if (numClasses == 2) {
      //这个是W*X+intercept(加上偏置)做向量的点乘
      val margin = dot(weightMatrix, dataMatrix) + intercept
      //这个是计算s函数 1/(1+exp(-margin))
      val score = 1.0 / (1.0 + math.exp(-margin))

      threshold match {
         //如果得到的这个score大于阈值，则设置为1，否则设置为0
        case Some(t) => if (score > t) 1.0 else 0.0
        case None => score //如果没有设置阈值，则返回计算的score，默认的阈值为0.5.
      }
    }
	
	
class LRFTRL(numFeatures:Int,iter:Int,a:Double) extends Serializable {
  private val gradient = new LRGradient()
  def compute(data: RDD[(Double, Vector)],ww:Array[Double],n:Array[Double],z:Array[Double]): (Vector,Array[Double],Array[Double]) = {
    val numWeight = numFeatures
    val b = 1.0
    val L1 = 1.0
    val L2 = 1.0
    val minibatch=1.0
    for (it <- 0 until iter) {
      val bcWeights = data.context.broadcast(ww)
      val tmp=data.sample(false, minibatch, 42)
        .treeAggregate((BDV.zeros[Double](numWeight), 0.0, 0L,Vectors.zeros(numWeight)))(
          seqOp = (c, v) => {
            val l = gradient.compute(v._2, v._1, Vectors.dense(bcWeights.value), Vectors.fromBreeze(c._1))
            (c._1, c._2 + l, c._3 + 1, v._2)
          },
          combOp = (c1, c2) => {
            (c1._1 += c2._1, c1._2 + c2._2, c1._3 + c2._3,Vectors.fromBreeze(c1._4.toBreeze+c2._4.toBreeze))
          })
      val g = Vectors.fromBreeze(tmp._1 / minibatch)
      val feature=Vectors.fromBreeze(tmp._4.toBreeze/minibatch)
      feature.foreachActive {
        case (i, v) =>
          var sign = -1.0
          if (z(i) > 0) sign = 1.0
          if (sign * z(i) < L1) ww(i) = 0
          else ww(i) = (sign * L1 - z(i)) / ((b + sqrt(n(i))) / a + L2)
          val sigma = (sqrt(n(i) + g(i) * g(i)) - sqrt(n(i))) / a
          z(i) += g(i) - sigma * ww(i)
          n(i) += g(i) * g(i)
      }
    }
    (Vectors.dense(ww),n,z)
  }
}


def loss(y, y_hat):
        return np.sum(-y * np.log(y_hat) - (1 - y) * np.log(1 - y_hat)))