package kotlin14


/******  Java 调用 kotlin ***********/


/**数据类
 * Created by qiqi on 17/3/12.
 *   "@JvmField" 成员不能自己定义set get 方法 同事不能声明private
 *   将 CREATOR 变量注解为纯 Java 的 field，去掉 kotlin 的特性
 *   （例如自动生成 getter, setter），直接暴露 CREATOR 变量给 Java 代码。
 */
data class Person(var name: String, @JvmField var age: Int)