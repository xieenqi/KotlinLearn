// IMyAidlInterface.aidl
package com.ipcdemo;

// Declare any non-default types here with import statements  定义的AIDL接口文件

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     *  AIDL默认实现的方法
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

               /**
                 *  定义了一个登录方法，包含用户名、密码两个参数
                 */
                void login(String username, String password);
}
