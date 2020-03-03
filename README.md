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
    
 从静态代理到动态代理