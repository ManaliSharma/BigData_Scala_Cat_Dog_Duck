import java.io.File
import java.nio.file.Paths

import com.intel.analytics.bigdl.transform.vision.image.ImageFeature
import com.intel.analytics.zoo.feature.image.{ImageSet, LocalImageSet}
import scopt.OptionParser
import com.intel.analytics.zoo.models.image.objectdetection.common.dataset._

/**
 * Read roi images and store to sequence file
 */
object ImageSeqGenerator {

  case class RoiImageSeqGeneratorParams(
                                         folder: String = ".",
                                         output: String = "-",
                                         parallel: Int = 3,
                                         blockSize: Int = 12800,
                                         imageSet: Option[String] = None
                                       )

  private val parser = new OptionParser[RoiImageSeqGeneratorParams]("BigDL Pascal VOC " +
    "Sequence File Generator") {
    head("BigDL Pascal VOC Sequence File Generator")
    opt[String]('f', "folder")
      .text("where you put the image data, if this is labeled pascal voc data, put the devkit path")
      .action((x, c) => c.copy(folder = x))
      //.required()
    opt[String]('o', "output folder")
      .text("where you put the generated seq files")
      .action((x, c) => c.copy(output = x))
      .required()
    opt[Int]('p', "parallel")
      .text("parallel num")
      .action((x, c) => c.copy(parallel = x))
    opt[Int]('b', "blockSize")
      .text("block size")
      .action((x, c) => c.copy(blockSize = x))
    opt[String]('i', "imageSet")
      .text("image set, if this is the pascal voc data, put the image set name, e.g. voc_2007_test")
      .action((x, c) => c.copy(imageSet = Some(x)))
  }

  def localImagePaths(folder: String): LocalImageSet = {
    val arr = new File(folder).listFiles().map(x => {
      val imf = ImageFeature()
      imf(ImageFeature.uri) = x.getAbsolutePath
      imf
    })
    ImageSet.array(arr)
  }

  def main(args: Array[String]): Unit = {
    //val input = Array("src/main/resorces/ILSVRC", "src/main/resorces/Output", "4")

   // parser.parse(input, RoiImageSeqGeneratorParams()).map(param => {
     val roidbs =  Imdb.getImdb("train","C:/Users/Aswani/IdeaProjects/ObjectDetectionTrain/src/main/resources/ILSVRC").getRoidb(false)
       //if (param.imageSet.isEmpty) {
        // no label
        //require(new File(param.folder).exists(), s"${param.folder} not exists!")
      /*  localImagePaths("src/main/resorces/ILSVRC")
          //param.folder)
      } else {
        Imdb.getImdb("train","src/main/resorces/ILSVRC").getRoidb(false)
          //param.imageSet.get, param.folder).getRoidb(false)
      }*/

      val total = roidbs.array
      val iter = Imdb.data(roidbs.array)

      (0 until 4).map(tid => {
        val workingThread = new Thread(new Runnable {
          override def run(): Unit = {
            val fileIter = RoiByteImageToSeq(4, Paths.get("C:/Users/Aswani/IdeaProjects/ObjectDetectionTrain/src/main/resources/Output",  //param.output,
              s"$total-seq-$tid"))(iter)
            while (fileIter.hasNext) {
              println(s"Generated file ${ fileIter.next() }")
            }
          }
        })
        workingThread.setDaemon(false)
        workingThread.start()
        workingThread
      }).foreach(_.join())
   // })
  }
}