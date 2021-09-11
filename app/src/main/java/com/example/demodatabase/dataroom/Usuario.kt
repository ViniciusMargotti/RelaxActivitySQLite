package com.example.demodatabase.dataroom

import androidx.room.*

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "email") val email: String?
) {
    override fun toString(): String {
        return "$id - $name - $email"
    }
}
