## WEB公共增删改查说明（基于Mybatis-Plus）
### 说明
   此增删改查通用接口封装基于*Mybatis-Plus(version-3.4.0)*,目的是为了简化WEB通用代码而做的封装，若此封装不满足所需业务可自行实现。[Mybatis-Plus官方文档](https://baomidou.com/guide/)
    
### 如何使用（基于Mybatis-Plus）
1.第一步（pom引入allinabc-web-starter)
        
       <dependency>
         <groupId>com.allinabc</groupId>
         <artifactId>allinabc-web-starter</artifactId>
         <version>1.0-SNAPSHOT</version>
       </dependency> 
      
2.第二步（对应controller、service、mapper继承封装的类)
        
        例如： 
        controller层：public class ApplicationController extends MybatisBaseCrudController<Application, ApplicationService>       
        
        service接口：public interface ApplicationService extends MybatisCommonService<Application> 
        
        service接口实现类：public class ApplicationServiceImpl extends MybatisCommonServiceImpl<Application, ApplicationMapper> implements ApplicationService 
        
        mapper层：public interface ApplicationMapper extends MybatisCommonBaseMapper<Application>     
        
        xml:如需要可自行生成，不生成可使用内部集成的方法实现业务逻辑（非必须）xml默认统一放在此resources/mapper目录下，**可自行分组存放，但需要设置xml扫描路径**！！   
  
3.第三步
        
         编写自己的代码实现业务逻辑

### 接口说明    
#### insert(新增单条记录)    
|                   url                    |    请求类型       |   Content-Type      | 请求参数   |返回参数    |说明|
|:----------------------------------------:|:----------------:|:-------------------:|----------:|-----------:| ----------:|  
| 根路径/业务url/insert                     |    POST          |  application/json   |  业务实体  |入库实体    |    |
 
#### delete(删除单条或多条记录)    
|                   url                    |    请求类型       |   Content-Type      | 请求参数   |返回参数    | 说明|
|:----------------------------------------:|:----------------:|:-------------------:|----------:|----------:|  -------------------------------------:|
| 根路径/业务url/delete/{ids}               |    DELETE        |  application/json   |  数据主键  |  入库实体  |  根据多个主键删除对象,多个主键(逗号分隔)  |

#### update(更新单条记录)    
|                   url                    |    请求类型       |   Content-Type      | 请求参数   |返回参数    |说明|
|:----------------------------------------:|:----------------:|:-------------------:|----------:|----------:|  -------------------------------------:|
| 根路径/业务url/update/{id}                |    PUT           |  application/json   |  业务实体和主键id  |入库实体    | update(更新单条记录) |   

#### get(单个查询)    
|                   url                    |    请求类型       |   Content-Type      | 请求参数   |返回参数    |说明|
|:----------------------------------------:|:----------------:|:-------------------:|----------:|----------:|  -------------------------------------:|
| 根路径/业务url/get/id/{id}                |    GET           |  application/json   |  主键id  |实体数据    | 单个查询 |  

#### list(列表查询)    
|                   url                    |    请求类型       |   Content-Type      | 请求参数   |返回参数    |说明|
|:----------------------------------------:|:----------------:|:-------------------:|----------:|----------:|  -------------------------------------:|
| 根路径/业务url/list             |    POST           |  application/json   |  QueryParam  |List<T>    | 列表查询 |   
  
#### findPage(分页查询)    
|                   url                    |    请求类型       |   Content-Type      | 请求参数   |返回参数    |说明|
|:----------------------------------------:|:----------------:|:-------------------:|----------:|-----------:|  -------------------------------------:|
| 根路径/业务url/page/{pageNo}/{pageSize}   |    POST           |  application/json   |  pageNo/pageSize/QueryParam  |List<T>    | 分页查询 |       

### 注意事项    

日期类型问题：[Mybatis-Plus常见问题](https://baomidou.com/guide/faq.html)

 目前基于此版本的Mybatis-plus,若数据库为mysql类型，则支持LocalDateTime,oracle目前暂未找到解决方案，暂时以Date类型去处理日期！！！
