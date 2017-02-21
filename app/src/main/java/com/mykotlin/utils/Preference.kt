package com.mykotlin.utils

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by qiqi on 16/12/5.
 */
class Preference<T>(val context: Context, val name: String, val deafault: T) : ReadWriteProperty<Any?, T> {

    val prefs by lazy { context.getSharedPreferences("deafault", Context.MODE_PRIVATE) }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name,value)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name,deafault)
    }

    private fun <U> findPreference(name: String, deafault: U): U = with(prefs) {
        val res: Any = when (deafault) {
            is Long -> getLong(name, deafault)
            is String -> getString(name, deafault)
            is Float -> getFloat(name, deafault)
            is Boolean -> getBoolean(name, deafault)
            is Int -> getInt(name, deafault)
            else -> throw IllegalArgumentException("参数异常!!!")
        }
        res as U
    }

    private fun <U> putPreference(name: String, value: U) = with(prefs.edit()) {
        when(value){
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Float -> putFloat(name, value)
            is Boolean -> putBoolean(name, value)
            is Int -> putInt(name, value)
            else -> throw IllegalArgumentException("参数异常!!!")
        }.apply()
    }
}