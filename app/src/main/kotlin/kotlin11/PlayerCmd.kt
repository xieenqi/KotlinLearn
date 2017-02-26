package kotlin11

/**
 * Created by qiqi on 17/2/26.
 *
 * sealed  关键字'密封'  私有的 继承只能写在内部 外部不能继承 子类是有限个数的
 * 扩展版本的枚举
 */
sealed class PlayerCmd {
    class Play(val url: String, val postin: Long) : PlayerCmd()
    class Seek(val postin: Long) : PlayerCmd()
    object Pause : PlayerCmd()
    object Resume : PlayerCmd()
    object Stop : PlayerCmd()
}