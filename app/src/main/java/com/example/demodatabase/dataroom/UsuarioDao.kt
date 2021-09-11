package com.example.demodatabase.dataroom

import androidx.room.*

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE id = :usuarioId")
    fun getUsuario(usuarioId: String): Usuario

    @Query("SELECT * FROM usuarios")
    fun getAll(): List<Usuario>

    @Query("SELECT * FROM usuarios WHERE name LIKE :name")
    fun loadAllByName(name: String): List<Usuario>

    @Insert
    fun insertAll(vararg usuarios: Usuario)

    @Delete
    fun delete(usuario: Usuario)
}
