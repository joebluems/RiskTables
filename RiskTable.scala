import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

/*********************************************
**** Calculate Smoothed Risk Table from CSV
*********************************************/

object RiskTable {

  def main(args: Array[String]) {
    val sc = new SparkContext("local[2]", "Smoothed Risk Table")
    // convert CSV input to a set of records of form (tag, predictor )
    val rawdata = sc.textFile("input.csv").map(line => line.split(",")).map(record => (record(0), record(1)))

    // map-reduce counts by category, then map-reduce those for overall counts
    val categoryCounts = rawdata.map{ case (tag, predictor1) => (predictor1, 1)}.reduceByKey((a,b)=> a+b)
    val categoryFrauds= rawdata.filter{case(tag, predictor1) => tag.equals("1")}.map{ case (tag, predictor1) => (predictor1, 1)}.reduceByKey((a,b)=> a+b)
    val totalCount = categoryCounts.map{ case (cat, count) => count.toDouble }.sum()
    val totalFraud = categoryFrauds.map{ case (cat, count) => count.toDouble }.sum()
    val data = categoryCounts.join(categoryFrauds)

    // set smoothing parameter, then calculate smoothed risk by category 
    var smooth = 50.0
    if (args.length>0) { try {smooth = args(0).toDouble } catch { case _ => None } }
    data.foreach{ case(a,(b,c)) => println(a + " , " + b + " , " + c + " , " + risk(b.toDouble,c.toDouble,totalCount,totalFraud,smooth))}
  }

  def risk(a:Double, b:Double, c:Double, d:Double, s:Double):Double = {
    var total = ( b + s * d / c ) / (a + s) / ( d / c )
    return total
  }

}

