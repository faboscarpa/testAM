FROM maven:3.3-jdk-8
WORKDIR callcenter
COPY src/ src/
COPY pom.xml pom.xml
RUN mvn clean install
CMD java -jar target/callCenter.jar