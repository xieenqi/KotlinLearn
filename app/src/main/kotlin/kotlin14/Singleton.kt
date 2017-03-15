package kotlin14

/** 申明 object 的单利模式
 * Created by qiqi on 17/3/12.
 */
object Singleton {
    fun printHello() {
        println("helloHHHH")
    }

    //方法可以有 默认参数 "@JvmOverloads" Java 调用 就可以使用默认参数了
    @JvmOverloads
    fun overload(a: Int, b: Int = 7, c: Int = 10) {
        println("$a,$b.$c")
    }
}