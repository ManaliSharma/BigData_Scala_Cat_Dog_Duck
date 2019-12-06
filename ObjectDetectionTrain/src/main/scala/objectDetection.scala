import com.intel.analytics.zoo.models.image.objectdetection._
import com.intel.analytics.zoo.common.NNContext
import com.intel.analytics.zoo.feature.image._
import com.intel.analytics.bigdl.tensor.TensorNumericMath.TensorNumeric.NumericFloat

object objectDetection {
  def main(args: String): Unit = {
    val imagePath = "C:/Users/Aswani/IdeaProjects/ObjectDetectionTrain/src/main/resources/ILSVRC/images/n01440764/n01440764_36.jpg"
    val sc = NNContext.initNNContext()
    val model = ObjectDetector.loadModel("C:/Users/Aswani/IdeaProjects/ObjectDetectionTrain/src/main/resources/model/used/analytics-zoo_ssd-vgg16-300x300_PASCAL_0.1.0.model")
    val data = ImageSet.read(imagePath, sc)
    val output = model.predictImageSet(data)
    println(output)
  }
}