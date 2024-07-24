pipeline{
	agent any

	options{

		buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
	}

	tools{
		maven 'maven_3.9.4'
	}


	stages{

		stage('Code Compilation'){

			steps{

				echo 'Code Compilation is in progress!'
				sh 'mvn clean compile'
				echo 'Code Compilation is completed successfully!'
			}
		}

		stage('Code QA Execution'){

			steps{

				echo 'JUnit Tast Case Check is in progress!'
				sh 'mvn clean test'
				echo 'JUnit Test Case Check is completed successfully!'
			}
		}

		stage('Code Package'){

			steps{

				echo 'Creating WAR Artefact!'
				sh 'mvn clean package'
				echo 'WAR Artefact Creation is completed successfully!'
			}
		}


	}


}