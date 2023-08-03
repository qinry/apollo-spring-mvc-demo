# Apollo Spring MVC Demo

## 一、搭建演示环境

前提：安装docker,jdk8,tomcat7

### 1.1 Apollo依赖数据库创建

apollo-db有两个sql文件，里面都有ServiceConfig的插入语句，
分别将eureka.service.url和apollo.portal.meta.servers的ip改为实际本机的ip
后执行下面操作：

```
cd docker/apollo-db
docker compose up -d
```

### 1.2 搭建Apollo服务端

```
cd ../
docker compose up -d
```

Apollo 默认账号apollo，密码是admin

### 1.3 Apollo控制台创建App

1. 创建App,app id 为demo-app
2. 使用默认env为DEV
3. 创建集群test
4. 将src/main/resources/application.properties内容复制到apollo默认的namespace，application中
5. 点击发布

## 二、启动Apollo 客户端

使用IDEA的Tomcat运行配置运行这个Spring MVC应用即可