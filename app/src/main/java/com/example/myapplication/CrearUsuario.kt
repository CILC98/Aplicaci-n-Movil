package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat

class CrearUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario2)

        val botonCrear = findViewById<Button>(
            R.id.btn_agregar
        )
        val nombre = findViewById<EditText>(
            R.id.txt_nombre
        )
        val descipcion = findViewById<EditText>(
            R.id.txt_descripcion
        )
        val textviewR = findViewById<TextView>(
            R.id.txt_aviso
        )
        botonCrear.setOnClickListener{
            if(nombre.getText().toString() != "" && descipcion.getText().toString() != ""){
                ESqliteHelperUsuario(this).crearUsuarioFormulario(nombre.getText().toString(),descipcion.getText().toString())
                textviewR.setText("Usuario Creado")
                textviewR.setTextColor(ContextCompat.getColor(applicationContext,R.color.ok))
                nombre.setText("")
                descipcion.setText("")
            }else{
                textviewR.setText("Llene todos los campos")
                textviewR.setTextColor(ContextCompat.getColor(applicationContext,R.color.error))
            }
        }
    }
}