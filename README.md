# proxy-pattern
深入理解代理模式

代理模式(Proxy Pattern)是指为其他对象提供一种代理，以控制对这个对象的访问，属于结构型
模式。在某些情况下，一个对象不适合或者不能直接引用另一个对象，而代理对象可以在客户端和目标
对象之间起到中介的作用。

代理模式一般包含三种角色:

**抽象主题角色(Subject):**抽象主题类的主要职责是声明真实主题与代理的共同接口方法，该类可以是接口也可以是抽象类;

**真实主题角色(RealSubject):**该类也被称为被代理类，该类定义了代理所表示的真实对象，是负 责执行系统真正的逻辑业务对象;

**代理主题角色(Proxy):**也被称为代理类，其内部持有 RealSubject 的引用，因此具备完全的对 RealSubject 的代理权。客户端调用代理对象的方法，同时也调用被代理对象的方法，但是会在代理对 象前后增加一些处理代码。
在代码中，一般代理会被理解为代码增强，实际上就是在原代码逻辑前后增加一些代码逻辑，而使 调用者无感知。代理模式属于结构型模式，分为静态代理和动态代理。

代理模式的应用场景

生活中的租房中介、售票黄牛、婚介、经纪人、快递、事务代理、非侵入式日志监听等，都是代理 模式的实际体现。当无法或不想直接引用某个对象或访问某个对象存在困难时，可以通过也给代理对象 来间接访问。使用代理模式主要有两个目的:一是保护目标对象，二是增强目标对象。

代理模式的通用写法

下面是代理模式的通用代码展示，首先创建代理主题角色 ISubject 类:

`public interface ISubject {
     void request();
 }`
 
 创建真实主题角色 RealSubject 类:
 
 `public class RealSubject implements ISubject {
      public void request() {
         System.out.println("real service is called.");
      }
  }`
  
 创建代理主题角色 Proxy 类:
  
  `public class Proxy implements ISubject {
       private ISubject subject;
       public Proxy(ISubject subject){
          this.subject = subject;
       }
       public void request() {
          before();
          subject.request();
          after(); 
        }
       public void before(){
          System.out.println("called before request().");
       }
       public void after(){
          System.out.println("called after request().");
       }
   }`
   
  客户端调用代码:
   
   `public static void main(String[] args) {
        Proxy proxy = new Proxy(new RealSubject());
        proxy.request();
    }`
    
 静态代理，参考 staticproxy 包下面的代码
 
 动态代理，参考 dynamicproxy 包下面的代码
 
 手写JDK动态代理实现
 
 不仅知其然，还得知其所以然。既然 JDK 动态代理功能如此强大，那么它是如何实现的呢?我们现 在来探究一下原理，并模仿 JDK 动态代理动手写一个属于自己的动态代理。
 我们都知道 JDK 动态代理采用字节重组，重新生成对象来替代原始对象，以达到动态代理的目的。 JDK 动态代理生成对象的步骤如下:

(1)获取被代理对象的引用，并且获取它的所有接口，反射获取。

(2)JDK 动态代理类重新生成一个新的类，同时新的类要实现被代理类实现的所有接口。

(3)动态生成 Java 代码，新加的业务逻辑方法由一定的逻辑代码调用(在代码中体现)。

(4)编译新生成的 Java 代码.class 文件。

(5)重新加载到 JVM 中运行。

以上过程就叫字节码重组。JDK 中有一个规范，在 ClassPath 下只要是$开头的.class 文件，一般都是自动生成的。那么我们有没有办法看到代替后的对象的“真容”呢?做一个这样测试，我们将内存中 的对象字节码通过文件流输出到一个新的.class 文件，然后利用反编译工具查看其源代码。

`public class JDKProxyTest {
    public static void main(String[] args) {
        try {
            Person obj = (Person)new JDKMeipo().getInstance(new Customer()); 
            obj.findLove();
            //通过反编译工具可以查看源代码
            byte [] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
            FileOutputStream os = new FileOutputStream("E://$Proxy0.class"); os.write(bytes);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
 }`
 
 运行以上代码，我们能在 E 盘下找到一个$Proxy0.class 文件。使用反编译工具打开查看：
 
 我们发现，$Proxy0 继承了 Proxy 类，同时还实现了 Person 接口，而且重写了 findLove()等方法。 在静态块中用反射查找到了目标对象的所有方法，而且保存了所有方法的引用，重写的方法用反射调用 目标对象的方法。“小伙伴们”此时一定会好奇:这些代码是哪里来的呢?其实是 JDK 帮我们自动生成 的。现在我们不依赖 JDK，自己来动态生成源代码、动态完成编译，然后替代目标对象并执行。
 
 具体代码参考 dynamicproxy.customproxy 包下的代码实现
 
 
 CGLib 代理调用 API 及原理分析
 
 参考 cglib包下的代码实现
 
 有个小细节，CGLib 代理的目标对象不需要实现任何接口，它是通过动态继承目标对象实现动态代理的。
 
 CGLib 代理的实现原理又是怎样的呢?我们可以在测试代码中加上一句代码，将 CGLib 代理后 的.class 文件写入磁盘，然后反编译来一探究竟，代码如下:
 
 `public static void main(String[] args) { 
    try {
        //利用 CGlib 的代理类可以将内存中的.class 文件写入本地磁盘
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E://cglib_proxy_class/"); 
        Customer obj = (Customer)new CglibMeipo().getInstance(Customer.class);
        obj.findLove();
    } catch (Exception e) {
         e.printStackTrace();
    }
  }`
  
 重新执行代码，我们会发现在 E://cglib_proxy_class 目录下多了三个.class 文件
  
 通过调试跟踪发现，Customer$$EnhancerByCGLIB$$3feeb52a.class 就是 CGLib 代理生成的代 理类，继承了 Customer 类。（可通过反编译软件进行查看具体内容）
  
 我们重写了 Customer 类的所有方法，通过代理类的源码可以看到，代理类会获得所有从父类继承来的方法，并且会有 MethodProxy 与之对应，比如 Method CGLIB$findLove$0$Method、
 MethodProxy CGLIB$findLove$0$Proxy 这些方法在代理类的 findLove()方法中都有调用。 
 
 调用过程为:代理对象调用 this.findLove()方法→调用拦截器→methodProxy.invokeSuper→ CGLIB$findLove$0→被代理对象 findLove()方法。
 此时，我们发现拦截器 MethodInterceptor 中就是由 MethodProxy 的 invokeSuper()方法调用代 理方法的，MethodProxy 非常关键，我们分析一下它具体做了什么。
 
 `
  package net.sf.cglib.proxy;
  import java.lang.reflect.InvocationTargetException; import java.lang.reflect.Method;
  import net.sf.cglib.core.AbstractClassGenerator; import net.sf.cglib.core.CodeGenerationException; import net.sf.cglib.core.GeneratorStrategy;
  import net.sf.cglib.core.NamingPolicy;
  import net.sf.cglib.core.Signature;
  import net.sf.cglib.reflect.FastClass;
  import net.sf.cglib.reflect.FastClass.Generator;
  
  public class MethodProxy {
      private Signature sig1;
      private Signature sig2;
      private MethodProxy.CreateInfo createInfo;
      private final Object initLock = new Object();
      private volatile MethodProxy.FastClassInfo fastClassInfo;
      
      public static MethodProxy create(Class c1, Class c2, String desc, String name1, String name2) { 
          MethodProxy proxy = new MethodProxy();
          proxy.sig1 = new Signature(name1, desc);
          proxy.sig2 = new Signature(name2, desc);
          proxy.createInfo = new MethodProxy.CreateInfo(c1, c2);
          return proxy; 
      }
      ...
      private static class CreateInfo { 
          Class c1;
          Class c2;
          NamingPolicy namingPolicy; 
          GeneratorStrategy strategy; 
          boolean attemptLoad;
     
          public CreateInfo(Class c1, Class c2) { 
              this.c1 = c1;
              this.c2 = c2;
              AbstractClassGenerator fromEnhancer = AbstractClassGenerator.getCurrent(); 
              if(fromEnhancer != null) {
                this.namingPolicy = fromEnhancer.getNamingPolicy(); 
                this.strategy = fromEnhancer.getStrategy(); 
                this.attemptLoad = fromEnhancer.getAttemptLoad();
              }
          } 
      }
      ... 
  }`
 继续看 invokeSuper()方法:
 
 `
 public Object invokeSuper(Object obj, Object[] args) throws Throwable { 
    try {
        this.init();
        MethodProxy.FastClassInfo fci = this.fastClassInfo; 
        return fci.f2.invoke(fci.i2, obj, args);
    } catch (InvocationTargetException var4) {
        throw var4.getTargetException(); 
    }
 }
 ...
 private static class FastClassInfo { 
     FastClass f1;
     FastClass f2; 
     int i1;
     int i2;
     private FastClassInfo() {
     } 
 }
 `
上面的代码调用就是获取代理类对应的 FastClass，并执行代理方法。还记得之前生成的三个.class 文件吗?Customer$$EnhancerByCGLIB$$3feeb52a$$FastClassByCGLIB$$6aad62f1.class 就是代理类的 FastClass，Customer$$FastClassByCGLIB$$2669574a.class 就是被代理类的 FastClass。

CGLib 代理执行代理方法的效率之所以比 JDK 的高，是因为 CGlib 采用了 FastClass 机制，它的原理简单来说就是:为代理类和被代理类各生成一个类，这个类会为代理类或被代理类的方法分配一个 index(int 类型);这个 index 当作一个入参，FastClass 就可以直接定位要调用的方法并直接进行调用，省去了反射调用，所以调用效率比 JDK 代理通过反射调用高。下面我们反编译一个 FastClass 看看:

FastClass 并不是跟代理类一起生成的，而是在第一次执行 MethodProxy 的 invoke()或 invokeSuper()方法时生成的，并放在了缓存中。

CGLib 和 JDK 动态代理对比

(1)JDK 动态代理实现了被代理对象的接口，CGLib 代理继承了被代理对象。

(2)JDK 动态代理和 CGLib 代理都在运行期生成字节码，JDK 动态代理直接写 Class 字节码，CGLib 代理使用 ASM 框架写 Class 字节码，CGlib 代理实现更复杂，生成代理类比 JDK 动态代理效率低。

(3)JDK 动态代理调用代理方法是通过反射机制调用的，CGLib 代理是通过 FastClass 机制直接调 用方法的，CGLib 代理的执行效率更高。

代理模式于 Spring 生态

1、代理模式在 Spring 中的应用

先看 ProxyFactoryBean 核心方法 getObject()，源码如下:

`
 public Object getObject() throws BeansException { 
    initializeAdvisorChain();
    if (isSingleton()) {
        return getSingletonInstance(); 
    }
    else {
        if (this.targetName == null) {
            logger.warn("Using non-singleton proxies with singleton targets is often undesirable. " + "Enable prototype proxies by setting the 'targetName' property.");
        }
        return newPrototypeInstance(); 
    }
 }
 `
在 getObject()方法中，主要调用 getSingletonInstance()和 newPrototypeInstance()。在 Spring 的配置中如果不做任何设置，那么 Spring 代理生成的 Bean 都是单例对象。如果修改 scope，则每次 创建一个新的原型对象。

Spring 利用动态代理实现 AOP 时有两个非常重要的类:JdkDynamicAopProxy 类和CglibAopProxy 类

2. Spring 中的代理选择原则

(1)当 Bean 有实现接口时，Spring 就会用 JDK 动态代理。

(2)当 Bean 没有实现接口时，Spring 会选择 CGLib 代理。

(3)Spring 可以通过配置强制使用 CGLib 代理，只需在 Spring 的配置文件中加入如下代码:<aop:aspectj-autoproxy proxy-target-class="true"/>


静态代理和动态代理的本质区别

(1)静态代理只能通过手动完成代理操作，如果被代理类增加了新的方法，代理类需要同步增加， 违背开闭原则。

(2)动态代理采用在运行时动态生成代码的方式，取消了对被代理类的扩展限制，遵循开闭原则。

(3)若动态代理要对目标类的增强逻辑进行扩展，结合策略模式，只需要新增策略类便可完成，无 须修改代理类的代码。

代理模式的优缺点

代理模式具有以下优点:

(1)代理模式能将代理对象与真实被调用目标对象分离。

(2)在一定程度上降低了系统的耦合性，扩展性好。

(3)可以起到保护目标对象的作用。

(4)可以增强目标对象的功能。

当然，代理模式也有缺点:

(1)代理模式会造成系统设计中类的数量增加。

(2)在客户端和目标对象中增加一个代理对象，会导致请求处理速度变慢。

(3)增加了系统的复杂度。