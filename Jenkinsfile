pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "ashutoshchauhan149/order-management-service"
        IMAGE_TAG = "latest"
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

                sh 'docker build -t $DOCKER_IMAGE:$IMAGE_TAG .'
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'

                withCredentials([
                    usernamePassword(
                        credentialsId: 'docker-hub-credentials',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login \
                        -u "$DOCKER_USERNAME" \
                        --password-stdin

                        docker push $DOCKER_IMAGE:$IMAGE_TAG

                        docker logout
                    '''
                }
            }
        }
    }
}