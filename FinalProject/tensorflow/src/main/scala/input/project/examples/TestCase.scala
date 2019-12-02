package input.project.examples

/*import org.scalatest.{FlatSpec, Matchers}*/

object TestCase {

  def main(args: Array[String]): Unit = {
    val input = Array("src/main/resources/n01440764_36/n01440764_36.jpeg", "tensorflow/n01440764_36.jpeg")
    ObjectDetector.main(input)
  }

}
