pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Maju-java-developer/KafkaProducer.git'
            }
        }

        stage('Remove Containers') {
            steps {
                sh 'docker-compose down'
            }
        }

        stage('Build Docker') {
            steps {
                sh 'docker-compose build'
            }
        }

        stage('Run App') {
            steps {
                sh 'docker-compose up -d'
            }
        }

    }
}
