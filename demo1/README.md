
## 这是一个个人开源项目，仅供个人学习测试使用，如有必要，请联系作者 jiantang2000@qq.com
## 整个项目的所有文件，均为本人手动书写或有正当合理的来源路径。文件结构、配置等均为本人自定义，阅历有限，不甚雅观，详细心路历程请见个人博客demo专栏 https://blog.csdn.net/qq_20241587。

## 本项目仅仅为后台服务，对应的前台项目详见:https://github.com/JianTang2000/demo1-ElementUI-frontend

## 书写方向:
## 简明的分布式系统，拆为多个maven项目，包括两个后台服务中心maven项目，一个公共base maven项目，一个公共api maven项目，两个服务中心均引用公共api和base;
## 添加通用的日志功能;
## 为两个服务中心添加Spring+dubbo，接口层，实现层，Spring扫描配置文件，包扫描路径等，建立两个中心之间的dubbo通信配置;
## 为两个服务中心添加Mybatis，添加测试类，DAO层;
## 在两个服务中心添加Controller层,引入的是spring-boot-starter-tomcat，实现对外的restFul服务的提供;

## 自写部分工具如下（代码可参见base目录）:
## 本地缓存框架和类似redis客户端的界面的实现;
![Image text](https://github.com/JianTang2000/demo1/blob/master/demo1/dbfile/LocalCache.pdf)
## 线程级上下文及流程拼装框架实现;
## 分布式系统下oracle sequence获取工具;
## oracle/mySQl SQL层的分页工具;
## 反射/Json转换等工具类;

## 接下来有时间可能会实现的是：定时job框架的实现，异步通信机制的集成，redis集成,redis实现异步通信等

## 因为自己的笔记本一直没有搭好部署的机器环境，等搭好了，后面可以用两种形式试一下部署运维：
## 基于linux的部署，多服务器ZK集群形式，
## 基于docker的分布式集群形式

## 更新较慢，一来书写方向经常变动,二来水平有限边看边写，三来时间不多~




