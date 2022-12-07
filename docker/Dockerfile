FROM openjdk:8-jre
MAINTAINER Dorian Gillioz <dorian.gillioz@heig-vd.ch>
  
#
# When we build the image, we copy the executable jar in the image file system. 
#
COPY MockMock-1.4.1-SNAPSHOT.one-jar.jar /opt/app/server.jar

#
# This is the command that is executed when the Docker container starts
#
CMD ["java", "-jar", "/opt/app/server.jar"]