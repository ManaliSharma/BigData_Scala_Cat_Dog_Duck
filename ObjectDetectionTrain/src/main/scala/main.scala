import com.intel.analytics.zoo.common.NNContext
import org.apache.spark.SparkConf
import com.intel.analytics.zoo.examples.objectdetection.finetune.ssd.Train
class Main  {
  def main(args: Array[String]): Unit = {
    objectDetection.main("")
    //Train --resolution 300 --model analytics-zoo_ssd-vgg16-300x300_PASCAL_0.1.0.model --class "C:/Users/Aswani/IdeaProjects/ObjectDetectionTrain/src/main/resources/ILSVRC/classname.txt" -f "C:/Users/Aswani/IdeaProjects/ObjectDetectionTrain/src/main/resources/Output/train" -v "C:/Users/Aswani/IdeaProjects/ObjectDetectionTrain/src/main/resources/Output/val" -e 1 -b 8 --modelDir "C:/Users/Aswani/IdeaProjects/ObjectDetectionTrain/src/main/resources/model/used" -l 0.0001 -learningRateDecay 0.000
  }
}