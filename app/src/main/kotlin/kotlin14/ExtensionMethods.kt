package kotlin14

/** 定义扩展方法
 * Created by qiqi on 17/3/12.
 * internal(内部的,内在的) 修饰 跨模块是不能调用的(修饰 类:Java 可以调用, kotlin 不可以调用 )
 * 修饰了 方法、属性 Java 不管在模块内外都是不可见的
 */
fun String.noEmpty(): Boolean {
    return this != ""
}