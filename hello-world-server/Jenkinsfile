pipeline {
    agent any
    tools {
        jdk 'openjdk17'
        maven 'maven3'
    }
    environment {
        DOCKER_USERNAME = "amolsontakke96"
        APP_NAME = "hello-server"
    }
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', changelog: false, credentialsId: 'git-cred', poll: false, url: 'https://github.com/amolsontakke96/java'
                }
        }
        stage('Mvn Build') {
            steps {
                sh 'mvn -f hello-world-server/pom.xml clean package -DskipTests'
            }
        }
        stage('Docker login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-cred', passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                        sh "echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin"
                        sh "docker --version"
                        sh 'echo "welcome to docker"'
                    }
                }
            }
        }
        stage('Docker build') {
            steps {
                dir('hello-world-server') {
                    sh 'docker build -t $DOCKER_USERNAME/$APP_NAME:${BUILD_NUMBER} .'
                }
            }
        }
        stage('Docker push ') {
            steps {
                sh 'docker push $DOCKER_USERNAME/$APP_NAME:${BUILD_NUMBER}'
            }
        }
        stage('Deploy Kubernetes ') {
            steps {
                dir('hello-world-server'){
                sh "sed -i 's|amolsontakke96/hello-server:.*|amolsontakke96/hello-server:${BUILD_NUMBER}|' deployment.yaml"
                sh 'kubectl apply -f deployment.yaml'
                }
            }
        }

    }
}