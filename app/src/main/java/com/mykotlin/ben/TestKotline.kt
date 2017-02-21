package com.mykotlin.ben

/**
 * Created by qiqi on 17/1/12.
 */
fun main(args:Array<out String>){
println("我的第一个line")
    print(Main(120,"李冬梅"))
    args.map {
        println(it)
    }
}
/*data class  数据类*/
data class Main(var id:Int,var name:String)