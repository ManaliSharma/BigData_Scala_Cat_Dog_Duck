import org.apache.spark.sql.SparkSession


object te extends App {


  /** starting spark session **/
  val spark = SparkSession
    .builder
    .appName("Project_we_are_serious")
    .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    .config("spark.hadoop.fs.s3a.access.key", "")
    .config("spark.hadoop.fs.s3a.secret.key", "")
    .config("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
    .config("spark.hadoop.fs.s3a.fast.upload", "true")
    .config("spark.sql.parquet.mergeSchema", "false")
    .master("local")
    .getOrCreate

  spark.close()

}
