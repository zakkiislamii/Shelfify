package com.example.shelfify.utils.converters
import androidx.room.TypeConverter
import com.example.shelfify.contracts.enumerations.Role

class RoleConverter {
    @TypeConverter
    fun toRole(value: String?) = value?.let { enumValueOf<Role>(it) }

    @TypeConverter
    fun fromRole(value: Role?) = value?.name
}