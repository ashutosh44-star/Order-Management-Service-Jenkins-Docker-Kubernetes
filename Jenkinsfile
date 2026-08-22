pipeline {

    agent any

    stages {

        stage('Build Application') {
            steps {
                echo 'Building Spring Boot application using Maven Wrapper...'

                sh 'chmod +x mvnw'
                sh './mvnw clean install -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'

                sh 'docker build -t ashutoshchauhan149/order-management-service:latest .'
            }
        }
    }
}