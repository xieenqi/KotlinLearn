import java.math.BigInteger

/**
 * Created by qiqi on 17/2/3.
 */

/**
 * 递归循环
 */
fun factorial(number: Int): BigInteger {
    if (number == 0) return BigInteger.valueOf(1)
    return BigInteger.valueOf(number.toLong()).times(factorial(number - 1))
}

fun main(vararg aa: String) {
    print(factorial(5000))
}