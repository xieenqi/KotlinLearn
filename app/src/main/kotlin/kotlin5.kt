/**
 * w.f.b.e.f.j.w.b.d.s.k.w.n.n.w.d.j.w.b.j.c.d.f.w
 * Created by qiqi on 17/1/23.
 */
fun main(vararg a: String) {//vararg 相当于Java中无限传 a... (可变参数)
    a.flatMap { it.split(".") }.map { print("$it${it.length}" )
        print(" -- ")}

//    map 和 forearch差不多  $字符串模板
}