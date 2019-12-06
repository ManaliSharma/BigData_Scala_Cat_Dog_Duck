import com.intel.analytics.bigdl._
import com.intel.analytics.bigdl.dataset.MiniBatch
import com.intel.analytics.bigdl.optim._
import com.intel.analytics.bigdl.tensor.TensorNumericMath.TensorNumeric.NumericFloat
import com.intel.analytics.bigdl.utils.LoggerFilter
import com.intel.analytics.zoo.common.NNContext
import com.intel.analytics.zoo.feature.FeatureSet
import com.intel.analytics.zoo.models.image.objectdetection.ObjectDetector
import com.intel.analytics.zoo.models.image.objectdetection.common.ModuleUtil
import com.intel.analytics.zoo.models.image.objectdetection.common.evaluation.MeanAveragePrecision
import com.intel.analytics.zoo.models.image.objectdetection.common.loss.{MultiBoxLoss, MultiBoxLossParam}
import com.intel.analytics.zoo.models.image.objectdetection.ssd.{SSDDataSet, SSDMiniBatch, SSDVGG}
import com.intel.analytics.zoo.pipeline.estimator.Estimator
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import scopt.OptionParser

import scala.io.Source

object TrainModel {

  LoggerFilter.redirectSparkInfoLogs()
  Logger.getLogger("com.intel.analytics.bigdl.optim").setLevel(Level.INFO)
  Logger.getLogger("com.intel.analytics.zoo").setLevel(Level.INFO)

  val logger = Logger.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {
   // trainParser.parse(args, TrainParams()).map(param => {
      // initial zoo context
    val sparkConf = new SparkConf().setAppName("objectDetection")
        .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    /*.set("spark.hadoop.fs.s3a.access.key", "")
    .set("spark.hadoop.fs.s3a.secret.key", "")
    .set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
    .set("spark.hadoop.fs.s3a.fast.upload", "true")
    .set("spark.sql.parquet.mergeSchema", "false")*/
    //.setExecutorEnv("driver-memory","10")
    //.setExecutorEnv("executor-memory","10")


    val sc = NNContext.initNNContext(sparkConf)
      // load data
      val classes = Source.fromFile("s3://object-detection-logs/classname.txt").getLines().toArray
      val trainSet = SSDDataSet.loadSSDTrainSet("s3://object-detection-logs/train", sc,
        300, 4, None)
      val valSet = SSDDataSet.loadSSDValSet("s3://object-detection-logs/val", sc,
        300, 4,
        None)

      // create ssd model and load weights from pretrained model
      val model = SSDVGG[Float](classes.length,300, "pascal")
      val m = ObjectDetector.loadModel(
        "s3://object-detection-logs/model/analytics-zoo_ssd-vgg16-300x300_PASCAL_0.1.0.model")
          ModuleUtil.loadModelWeights(m, model, false)

      // create estimator and train
      /*val optimMethod = if (param.stateSnapshot.isDefined) {

      } else {*/
      val optimMethod =  new Adam[Float](learningRate = 0.0001,learningRateDecay = 0.0005)
      //}
      optimize(model, trainSet, valSet, optimMethod,
        Trigger.maxEpoch(2), classes)

      model.saveModel("s3://object-detection-logs/final", overWrite = false)
  }

  private def optimize(model: SSDVGG[Float],
                       trainSet: FeatureSet[SSDMiniBatch],
                       valSet: FeatureSet[SSDMiniBatch],
                        optimMethod: OptimMethod[Float],
                       endTrigger: Trigger,
                       classes: Array[String]): Unit = {
    val estimator =
      Estimator[Float](model, optimMethod, "s3://object-detection-logs/final/logs")

    estimator.train(trainSet = trainSet.asInstanceOf[FeatureSet[MiniBatch[Float]]],
      criterion = new MultiBoxLoss[Float](MultiBoxLossParam(nClasses = classes.length)),
      endTrigger = Some(Trigger.maxEpoch(3)),
      //checkPointTrigger = Some(Trigger.everyEpoch),
      validationSet = valSet.asInstanceOf[FeatureSet[MiniBatch[Float]]],
      validationMethod = Array(new MeanAveragePrecision(true,
        normalized = true, classes = classes)))

  }
}