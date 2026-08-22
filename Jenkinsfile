pipeline {

    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Build Application') {
            steps {
                echo 'Building Spring Boot application...'
                sh 'mvn clean install -DskipTests'
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