package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperUsuario(
    contexto : Context?
) :SQLiteOpenHelper(
    contexto,
    "Moviles",
    null,
    1
){


    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario =
            """
                CREATE TABLE USUARIO(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50))
            """.trimIndent()
        Log.i("bbd", "Creando la tabla de usuario")
        db?.execSQL(scriptCrearTablaUsuario)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearUsuarioFormulario(
        nombre: String,
        descripcion : String
    ):Boolean{
        val conexionEscritura = writableDatabase

        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion",descripcion)

        val resultadoEscritura: Long = conexionEscritura
            .insert(
                "Usuario",
                null,
                valoresAGuardar
            )
        conexionEscritura.close()
        return if (resultadoEscritura.toInt() == -1) false else true
    }

    fun consultarUsuarioPorId(id: Int): EUsuarioBDD?{
        val scriptConsultarUsuario = "SELECT * FROM USUARIO WHERE ID = ${id}"
        val baseDatosLectura = readableDatabase

        val resultadoConsultaLectura = baseDatosLectura
            .rawQuery(scriptConsultarUsuario,
                null
            )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = EUsuarioBDD(0,"","")
        //Para varios usuarios
        //val arregloUsuarios = arrayListOf<EUsuarioBDD>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)//Columna indice -> ID
                val nombre = resultadoConsultaLectura.getString(1)//Columna indice1 -> NOMBRE
                val descripción = resultadoConsultaLectura.getString(2)//Columna de la descripcón
                if (id !=null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descipcion = descripción
                }
            }while(resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun eliminarUsuarioFormulario(id: Int): Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "Usuario",// tabla
                "id=?",//clausula where
                arrayOf(
                    id.toString(),

                    )//arreglo de parámetros
            )
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarUsuarioFormulario(
        nombre: String,
        descripcion : String,
        idActualizar: Int
    ): Boolean{
        val conexionEscritura = writableDatabase

        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)

        val resultadoActualizacion = conexionEscritura
            .update(
                "Usuario",
                valoresAActualizar,
                "id=?",
                arrayOf(
                    idActualizar.toString()
                )
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun listarUsuario(): ArrayList<EUsuarioBDD>{
        val scriptConsultarUsuario = "SELECT * FROM USUARIO"
        val baseDatosLectura = readableDatabase

        val resultadoConsultaLectura = baseDatosLectura
            .rawQuery(scriptConsultarUsuario,
                null
            )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = EUsuarioBDD(0,"","")
        //Para varios usuarios
        val arregloUsuarios:ArrayList<EUsuarioBDD> = arrayListOf<EUsuarioBDD>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)//Columna indice -> ID
                val nombre = resultadoConsultaLectura.getString(1)//Columna indice1 -> NOMBRE
                val descripción = resultadoConsultaLectura.getString(2)//Columna de la descripcón
                arregloUsuarios.add(EUsuarioBDD(id,nombre,descripción))
            }while(resultadoConsultaLectura.moveToNext())
        }else{
            arregloUsuarios.add(usuarioEncontrado)
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arregloUsuarios
    }
}