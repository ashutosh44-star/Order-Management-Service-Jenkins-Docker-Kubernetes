pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build Application') {
            steps {
                echo 'Building Spring Boot application...'
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                bat 'docker build -t ashutoshchauhan149/order-management-service:latest .'
            }
        }
    }
}