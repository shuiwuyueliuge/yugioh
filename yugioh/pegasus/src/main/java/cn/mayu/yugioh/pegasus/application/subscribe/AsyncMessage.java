package cn.mayu.yugioh.pegasus.application.subscribe;

public abstract class AsyncMessage {

}

// 解析bean配置
// BeanDefinitionReader -> xml|注解 -> BeanDefinition

//经过多个beanFactoryPostProcessor

// 注册BeanDefinition
//对BeanDefinition进行验证，判断是否注册过等等 -> beanDefinitionMap.put()

//bean的实例化
//
// org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton
// ->
// org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean
// ->
// org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean
// ->
// 选择构造函数 创建bean, org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance
// (必须是单例，允许循环依赖，singletonsCurrentlyInCreation存在)boolean earlySingletonExposure = mbd.isSingleton() && this.allowCircularReferences && this.isSingletonCurrentlyInCreation(beanName);
// 保存到单例对象工厂中,检查a对象是否依赖b对象，创建bean
// 3级缓存查询bean org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton
/* DefaultSingletonBeanRegistry => 3级缓存
 * 正在创建单例对象
 * private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap(16));
 *  if (singletonObject == null && allowEarlyReference) {
 *                 synchronized(this.singletonObjects) {
 *                     singletonObject = this.singletonObjects.get(beanName);
 *                     if (singletonObject == null) {
 *                         singletonObject = this.earlySingletonObjects.get(beanName);
 *                         if (singletonObject == null) {
 *                             ObjectFactory<?> singletonFactory = (ObjectFactory)this.singletonFactories.get(beanName);
 *                             if (singletonFactory != null) {
 *                                 singletonObject = singletonFactory.getObject();
 *                                 单例工厂获取未增强的bean放到提前曝光的map中
 *                                 this.earlySingletonObjects.put(beanName, singletonObject);
 *                                 删除单例工厂的bean
 *                                 this.singletonFactories.remove(beanName);
 *                             }
 *                         }
 *                     }
 *                 }
 *             }
 */
// 此时b通过构造函数创建bean对象放入单例工厂中，设置属性，保存到单例map中删除单例工厂和提前曝光的map对象

// 构造函数循环依赖：singletonsCurrentlyInCreation就是用来保存是否试图创建某个对象的beanName，
// 不管有没有创建成功，为后来从singletonFactories缓存中或earlySingletonObjects缓存中取值做个标识。
// 构造函数注入无法先调用构造函数实例化对象，所以缓存中不会有bean，getSingleton()的时候
// Object singletonObject = this.singletonObjects.get(beanName); == null
/* 直接报错
 *     protected void beforeSingletonCreation(String beanName) {
 *         if (!this.inCreationCheckExclusions.contains(beanName) && !this.singletonsCurrentlyInCreation.add(beanName)) {
 *             throw new BeanCurrentlyInCreationException(beanName);
 *         }
 *     }
 */
// AbstractBeanFactory
// 创建原型对象
// private final ThreadLocal<Object> prototypesCurrentlyInCreation = new NamedThreadLocal("Prototype beans currently in creation");
// 原型对象beanName保存到ThreadLocal，创建b需要doGetBean(a)
/*
 *      if (this.isPrototypeCurrentlyInCreation(beanName)) {
 *                 throw new BeanCurrentlyInCreationException(beanName);
 *      }
 */
// bean创建设置属性完成之后执行*aware接口，执行beanPostProcessor 发布事件，容器换关闭，使用bean