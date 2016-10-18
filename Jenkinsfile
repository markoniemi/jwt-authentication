node {
   sh 'env > env.txt'
stage 'Checkout'
   git credentialsId: '73534043-e92f-42d2-b0a3-c954b09ebd49', url: 'https://github.com/markoniemi/jwt-authentication.git'
   def mvnHome = tool 'Maven 3.3'
stage 'Build'
   env.JAVA_HOME="${tool 'JDK 1.8'}"
   env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
   sh 'java -version'
   sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore clean package -DskipTests=true"
stage 'Test'
   sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore package -DskipITs=true"
   step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
stage 'Integration test'
   sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore install -P tomcat"
   step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
stage 'Sonar'
   sh "${mvnHome}/bin/mvn -Dmaven.test.failure.ignore sonar:sonar -DskipTests=true -Dsonar.host.url=${env.SONAR_URL}"
}
