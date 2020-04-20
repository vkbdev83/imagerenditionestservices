# imagerenditionrestservices
Transformation of Image file from one format to another using ImagMagick & SpringBoot. This App is packaged and deployed using Docker

ImageMagick Supports various format. Visit  - https://imagemagick.org/script/formats.php to know the formats.

This project is build an Restful API's over the ImagaeMagick(OpenSource) using Spring Boot and run the service as Docker container.. 

ImageMagick have many other capabiliites , this project is expose most of the critical functionalities as service . For now this App does transformation image files to JPEG formats 

The prebuild image is published to Docker hub -- https://hub.docker.com/repository/docker/vishnube818/imagerenditionrestservices

Deployment Steps

1. Pull the docker image to local repo using

docker pull vishnube818/imagerenditionrestservices:latest

2. Run the Container using the below command

docker run -p 8080:8080 -d --name imageservices vishnube818/imagerenditionrestservices

3. To validate the App . Try using Postman or similar API tester tools

  i - Submit a Post request http://localhost:8080/transformfile/jpeg , with the body having the File input to be converted to JPEG. 

  

