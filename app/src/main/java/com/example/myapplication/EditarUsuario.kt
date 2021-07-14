package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomappbar.BottomAppBar
import java.sql.SQLClientInfoException
//se realizan cambios
class EditarUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_usuario)
        val id = intent.getIntExtra("id",0)

        val usuario = ESqliteHelperUsuario(this).consultarUsuarioPorId(id)
        val textId = findViewById<TextView>(
            R.id.txt_idE
        )
        val textNombre= findViewById<TextView>(
            R.id.txt_nombreE
        )
        val textDescip = findViewById<TextView>(
            R.id.txt_descripcionE
        )
        val textInfo = findViewById<TextView>(
            R.id.txt_info
        )
        textId.setText(id.toString())
        textNombre.setText(usuario!!.nombre)
        textDescip.setText(usuario!!.descipcion)


        val botonActualizar = findViewById<Button>(
            R.id.btn_actualizar
        )
        botonActualizar.setOnClickListener{
            if(textNombre.getText().toString() != "" && textDescip.getText().toString() != ""){
                ESqliteHelperUsuario(this).actualizarUsuarioFormulario(textNombre.getText().toString(),textDescip.getText().toString(),id)
                textInfo.setText("Usuario Actualizado")
                textInfo.setTextColor(ContextCompat.getColor(applicationContext,R.color.ok))
            }else{
                textInfo.setText("Llene todos los campos")
                textInfo.setTextColor(ContextCompat.getColor(applicationContext,R.color.error))
            }
        }
    }


}