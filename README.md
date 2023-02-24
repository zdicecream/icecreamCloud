# icecreamCloud

>**技术选型**  

|组件名称|软件名|
|---| --- |  
|配置中心|nacos|
|服务注册中心|nacos|
|服务熔断|sentinel|
|服务调用|openFeign|
|服务路由|gateway|
|消息队列|rocketMQ|
|负载均衡|Spring Cloud Loadbalancer|
|分布式事务|seata|



>**文件目录**
  
project  
　　|--common 通用       
　　　　|--base           
　　　　　　|--base（response controller constant(常量) enums(枚举) exception）     
　　　　　　|--config    
　　　　　　　　|--序列化(fastJson、jackson**)    
　　　　　　　　|--日志框架（logback）     
　　　　　　　　|--全局异常  
　　　　　　　　|--swagger2 接口文档           
　　　　　　|--util（excel，httpClient，responseUtil）  
　　　　|--db  
　　　　　　|--config  
　　　　　　　　|--mybatis  
　　　　　　　　|--mybatis-plus  
　　　　　　　　|--DruidConfig Druid连接池  
　　　　　　　　|--idGenerator id生成器（待定）  
　　　　　　　　|--codeGenerator 代码生成器  
　　　　|--entity Vo通用实体  
　　　　|--feign 服务调用  
　　　　|--redis  
　　　　　　|--config   
　　　　　　　　|--redis    
　　|--gateway 网关   
　　　　|--config (权限认证，登录异常处理)  
　　|--dispatch（Quartz或者xxl-job）调度(和springBatch批量任务合并)  
　　|--auth 权限  
　　　　|--config (登录后做权限登记)  
　　　　|--auth  
　　　　　　|--controller  
　　　　　　|--entity  
　　　　　　|--service  
　　　　　　|--mapper  
　　|--monitor 系统监控 springbootAdmin  
　　|--business 业务模块  
　　　　|--系统管理  
　　　　　　|--字典  
　　　　　　|--日历  
　　　　　　|--基础数据管理  
　　　　　　|--通知公告  
　　　　　　|--产品类型  
　　　　|--外围通讯  
　　　　　　|--kafka    
　　　　　　|--接口注册  
　　　　　　|--报文日志查询  
　　　　　　|--通讯状态  
　　　　　　|--错误报文监控  
　　　　|--客户信息  
　　　　　　|--基本信息  
　　　　　　|--签约开通管理  
　　　　|--账务往来  
　　　　　　|--资金监控  
　　　　　　|--流水查询  
　　　　　　|--账务核对  
　　　　　　|--会记分录  
　　　　|--风险管理  
　　　　　　|--规则引擎  
　　　　　　|--校验管理  
　　　　　　|--风险预警  
　　　　　　|--授信管理  
　　　　|--报表生成  
　　　　　　|--报表  
　　　　　　|--业务跟踪  
　　　　|--审批管理  
　　　　　　|--工作流引擎  
　　　　　　|--工作流业务模块  
　　|--search 搜索  
　　|--项目文档
