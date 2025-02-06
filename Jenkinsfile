pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = 'dockerhub-credentials-id'  // Docker Hub credentials ID
        DOCKER_IMAGE_NAME = 'your-dockerhub-username/your-image-name'  // Docker image name
        KUBE_CONFIG_CREDENTIALS = 'kubeconfig-credentials-id'  // Kubernetes credentials ID
    }
    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/your-username/your-repository.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE_NAME}:latest")
                }
            }
        }

        stage('Login to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_HUB_CREDENTIALS}", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    sh "docker push ${DOCKER_IMAGE_NAME}:latest"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    withCredentials([file(credentialsId: "${KUBE_CONFIG_CREDENTIALS}", variable: 'KUBECONFIG')]) {
                        sh "kubectl --kubeconfig=${KUBECONFIG} apply -f k8s/deployment.yaml"
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Cleaning up resources...'

            // Clean up Docker resources if there's a failure
            script {
                echo 'Stopping any running containers...'
                sh 'docker ps -q | xargs docker stop || true'

                echo 'Removing all Docker images...'
                sh 'docker images -q | xargs docker rmi || true'

                // Clean up Kubernetes resources if there's a failure
                echo 'Deleting Kubernetes resources...'
                sh 'kubectl --kubeconfig=${KUBECONFIG} delete -f k8s/deployment.yaml || true'
            }
        }
        always {
            cleanWs()  // Clean workspace after job is done
        }
    }
}
