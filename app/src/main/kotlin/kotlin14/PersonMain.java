package kotlin14;

/**
 * Created by qiqi on 17/3/12.
 */

public class PersonMain {
    public static void main(String... args) {
        //调用 kotlin的属性
        Person person = new Person("吕错无人", 67);
        System.out.println(person.getName());
        person.setName("我好了 A   A ");
        person.age = 78;
        System.out.println(person.getName());
        //调用单例
        Singleton.INSTANCE.printHello();
        //使用默认参数
        Singleton.INSTANCE.overload(1);
        Singleton.INSTANCE.overload(2, 3);
        Singleton.INSTANCE.overload(10, 9, 7);
        //调用定义的扩展方法
        ExtensionMethodsKt.noEmpty("guhik");
    }
}
