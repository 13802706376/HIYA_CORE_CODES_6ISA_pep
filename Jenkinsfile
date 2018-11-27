#!/usr/bin/env groovy
@Library('common-library')

//定义包ID, name和路径
def APPS = [
	['pep-mod-biz','pep-mod-biz', 'pep-mod-biz/target/pep-mod-biz-*.zip'],
	['pep-web-admin','pep-web-admin', 'pep-web-admin/target/pep-web-admin-*.war'],
]

//定义构建的参数
def buildMapParams=[:]
//构建pom所在目录
buildMapParams.pomDir=''
buildMapParams.mvnCommand='mvn -Dmaven.test.skip=true clean deploy'

pipeline {
    agent any
    tools {
        maven 'mvn3'
        jdk 'jdk8'
    }
	
    stages {
       stage('构建') {
	        steps {
			    commonBuild(buildMapParams)
			}   
    	}
    	stage('打包和上传') {
    		steps{
    			packageAndUpload(APPS)
    		}    		
        }
    }
}
