/**
 * Created by qiqi on 17/1/23.
 */
enum class Type(var typeName: String) {
    PEOPLE("人员->类型"),
    CAR("汽车->类型"),
    HOUSE("房屋->类型"),
    COMPANY("公司->类型"),
    NONE("不存在的");

    init {
        //构造方法
    }

    fun sayType() {
        println(typeName)
    }

    companion object {
        //半生对象 静态方法
        fun parse(name: String): Type {
            try {
                return valueOf(name.toUpperCase())//名字忽略大小写
            } catch(e: IllegalArgumentException) {
                println("name 参数异常")
            }
            return valueOf("NONE");
        }
    }
}

fun main(vararg a: String) {
    val type = Type.parse("HOUSEs")
    println(type)
    type.sayType()
    type.function()
}

fun Type.function() {//Type 的扩展方法
    var functionType = when (this) {
        Type.PEOPLE -> "所以的使用者"
        Type.CAR -> "由人来开"
        Type.HOUSE -> "人的居所"
        Type.COMPANY -> "人来运作"
        Type.NONE -> "人不知道干什么?"
    }
    println(functionType)
}




