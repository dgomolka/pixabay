package com.psycodeinteractive.pixabay.data.source.local

import androidx.room.TypeConverter

class DatabaseTypeConverters {
    @TypeConverter
    fun fromListIntToString(intList: List<Int>): String = intList.toString()

    @TypeConverter
    fun toListIntFromString(stringList: String): List<Int> {
        val result = mutableListOf<Int>()
        val split = stringList.replace("[", "").replace("]", "").replace(" ", "").split(",")
        for (n in split) {
            result.add(n.toInt())
        }
        return result
    }
}
