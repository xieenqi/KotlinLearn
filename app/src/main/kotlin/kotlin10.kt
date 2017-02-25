/**
 * Created by qiqi on 17/2/25.
 */
/* 浅谈 单例模式*/

/**
 * 第二种  【加锁就是线程安全的】
 * 懒 加载 模式
 * 在用的时候才去创建对象
 * lazy意思是 instance被调用才去加载
 */
class LayzNotThreadSafe {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            LayzNotThreadSafe()
        }
    }

    //第二种等价写法  @Synchronized 相当于Java加锁
    private var instance2: LayzNotThreadSafe? = null;

    @Synchronized
    fun get(): LayzNotThreadSafe {
        if (instance2 == null) {
            instance2 = LayzNotThreadSafe()
        }
        return instance2!!;
    }
}

/**
 * 第三种 !!调用方法
 */
class LayzThreadSafeDbleCheck {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            LayzThreadSafeDbleCheck()
        }
    }

    //等价写法
    private @Volatile var instans2: LayzThreadSafeDbleCheck? = null

    fun get(): LayzThreadSafeDbleCheck {
        if (instans2 == null) {
            synchronized(this) {
                if (instans2 == null) {
                    instans2 = LayzThreadSafeDbleCheck()
                }
            }
        }
        return instans2!!
    }
}

/**
 * 第四种 静态内部类
 */

class LayzThreadSafeStaticInnerObfect {
    private object Holder {
        val instance = LayzThreadSafeStaticInnerObfect()
    }

    companion object {
        fun getInstance() = Holder.instance
    }
}