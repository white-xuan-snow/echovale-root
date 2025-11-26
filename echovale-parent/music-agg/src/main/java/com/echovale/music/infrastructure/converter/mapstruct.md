## MapStruct 进阶用法
### 常见问题
#### 1. 自动映射满足不了业务需求
使用default TargetType method(SourceType res) {
    自定义逻辑
}

#### 2. 自定义映射中需要调用其它MapStruct Mapper或者Spring Bean
首先创建一个Qualifier类，注入到IoC容器中管理
再引入需要的MapStruct Mapper或Spring Bean
创建自定义映射方法
@Named("MethodName)
TargetType method(SourceType res) {
    自定义逻辑(可以使用MapStruct Mapper或Spring Bean)
}
最后在Mapper中uses这个Qualifier
在@Mapping指定qualifiedByName("MethodName")