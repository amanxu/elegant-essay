####elegant-essay 
####项目集成了sharding-sphere读写分离分库分表、rocketMQ、kafka、elasticsearch等基本用法
登录用户名和密码（明文）分别为：
账户名：admin
密码：123456
数据库表初始化SQL目录：elegant-essay/src/main/resources/db/
本项目用于测试sharding-sphere的使用，目前只测试读写分离模块儿，分库分表暂时不做包含在目前测试工程中
```
1.测试sharding-jdbc 读写分离，已测试通过
相关的配置参考application.yml中读写分离配置
2.测试sharding-proxy 的读写分离
1）下载官方sharding-proxy的zip包，根据官方教程配置，后成功启动代理，同时可以通过客户端访问
2）本工程直连代理服务的虚拟数据库，ORM框架是mybatis，预编译情况下无法设置参数
```
