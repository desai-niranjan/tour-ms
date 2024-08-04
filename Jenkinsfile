pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = "767397888377"
        REGION = "us-east-1"
        ECR_URL = "${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com"
        IMAGE_NAME = "desainiranjan8/tour-ms:tour-ms-v.1.${env.BUILD_NUMBER}"
        ECR_IMAGE_NAME = "${AWS_ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/tour-ms:tour-ms-v.1.${env.BUILD_NUMBER}"
        NEXUS_IMAGE_NAME = "54.226.107.248:8085/tour-ms:tour-ms-v.1.${env.BUILD_NUMBER}"
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    tools {
        maven 'maven_3.9.4'
        // sonarqubeScanner 'sonarqube-scanner'
    }

    stages {
        stage('Code Compilation') {
            steps {
                echo 'Code Compilation is In Progress!'
                sh 'mvn clean compile'
                echo 'Code Compilation is Completed Successfully!'
            }
        }
        stage('Code Package') {
            steps {
                echo 'Creating WAR Artifact'
                sh 'mvn clean package'
                echo 'Artifact Creation Completed'
            }
        }
        stage('Building & Tag Docker Image') {
            steps {
                echo "Starting Building Docker Image: ${env.IMAGE_NAME}"
                sh "docker build -t ${env.IMAGE_NAME} ."
                echo 'Docker Image Build Completed'
            }
        }
        stage('Docker Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_CRED', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    echo "Pushing Docker Image to DockerHub: ${env.IMAGE_NAME}"
                    sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                    sh "docker push ${env.IMAGE_NAME}"
                    echo "Docker Image Push to DockerHub Completed"
                }
            }
        }
        stage('Docker Image Push to Amazon ECR') {
            steps {
                echo "Tagging Docker Image for ECR: ${env.ECR_IMAGE_NAME}"
                sh "docker tag ${env.IMAGE_NAME} ${env.ECR_IMAGE_NAME}"
                echo "Docker Image Tagging Completed"

                withDockerRegistry([credentialsId: 'ecr:us-east-1:ecr-credentials', url: "https://${ECR_URL}"]) {
                    echo "Pushing Docker Image to ECR: ${env.ECR_IMAGE_NAME}"
                    sh "docker push ${env.ECR_IMAGE_NAME}"
                    echo "Docker Image Push to ECR Completed"
                }
            }
        }
        stage('Upload the Docker Image to Nexus') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh 'docker login http://54.226.107.248:8085/repository/tour-ms/ -u admin -p ${PASSWORD}'
                        echo "Push Docker Image to Nexus: In Progress"
                        sh "docker tag ${env.IMAGE_NAME} ${env.NEXUS_IMAGE_NAME}"
                        sh "docker push ${env.NEXUS_IMAGE_NAME}"
                        echo "Push Docker Image to Nexus: Completed"
                    }
                }
            }
        }
        stage('Delete Local Docker Images') {
            steps {
                echo "Deleting Local Docker Images: ${env.IMAGE_NAME} ${env.ECR_IMAGE_NAME} ${env.NEXUS_IMAGE_NAME}"
                sh "docker rmi ${env.IMAGE_NAME} ${env.ECR_IMAGE_NAME} ${env.NEXUS_IMAGE_NAME}"
                echo "Local Docker Images Deletion Completed"
            }
        }

    }
}