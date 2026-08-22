pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "ashutoshchauhan149/order-management-service"
    }

    stages {

        stage('Build Application') {
            steps {
                echo 'Building Spring Boot application...'
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh 'docker build -t $DOCKER_IMAGE:latest .'
            }
        }

    }
}