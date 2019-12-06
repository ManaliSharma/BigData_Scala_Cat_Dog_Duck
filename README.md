# BigData_Scala_Cat_Dog_Duck
# Image Classification and Object Detection- Tensorflow , Spark and Scala
## Overview
This project is an attempt at developing an object detection model using modern computer vision technology. The project aims to incorporate state-of-the-art techniques for object detection with the goal of achieving high accuracy in detecting objects. The model is build using the MXNet Scala Inference API or Tensorflow for scala. 

### Dataset being worked upon can be downloaded from here :- https://www.kaggle.com/c/imagenet-object-localization-challenge/data
- Input: Images
- Output: Identified Objects (Persian cat, mountain bike, hot dog, etc.) maximum 4 objects will be detected in this image
- Dataset: ImageNet Dataset (Kaggle)

### Pretrained Model can be downloaded from here: -http://download.tensorflow.org/models/object_detection/ssd_inception_v2_coco_2017_11_17.tar.gz 
(for object detection)

## Requirements (Libraries)
- Tensorflow = "0.2.4"
- BetterFiles = "3.4.0"
- DL4J = "1.0.0-alpha"
- Janino = "2.6.1"
- LogbackClassic = "1.2.3"
- ScalaCheck = "1.13.5"
- ScalaTest  = "3.0.4"
    
## Folder Structure

- BigData_Scala_Cat_Dog_Duck
- README.md
- Object_Detector
  > - Tensorflow
  > - src
  > - target
  > - model
  > - Target
- Classification
  > - src
- Object Detection Model
  > - src
  > - target
  > - .pom files
- Spark_trial_S3
- Final_Project.ppt

  

## How to Run
- Clone the repository in your local disk.
- Refer the folder structure mentioned above, and open the folder "BigData_Scala_Cat_Dog_Duck".
- This is the master folder and it links to all the different parts of the project.
- Please make sure that the libraries mentioned above are all installed
- Please make sure you have an active AWS account for running the clusters
