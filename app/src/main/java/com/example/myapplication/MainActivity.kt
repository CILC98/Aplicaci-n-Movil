package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.strictmode.SqliteObjectLeakedViolation
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val botonCrearUsuario = findViewById<Button>(
            R.id.btn_anadir
        )
        botonCrearUsuario.setOnClickListener{
            abrirUsuario(CrearUsuario::class.java)
        }
        val listViewUsuario = actualizarItemsUsuario()
        registerForContextMenu(listViewUsuario)
    }

    fun actualizarItemsUsuario(): ListView{
        val arregloUsuario =  obtenerUsuarios()
        val listViewUsuario = findViewById<ListView>(R.id.txv_usuario)
        if(arregloUsuario[0].nombre != "" ){
            arregloUsuario.removeAt(0)
        }
        val adaptador = ArrayAdapter(
            this,//Contexto
            android.R.layout.simple_list_item_1,//Layout
            arregloUsuario//Arreglo
        )

        listViewUsuario.adapter = adaptador
        adaptador.notifyDataSetChanged()
        return listViewUsuario
    }
    fun obtenerUsuarios(): ArrayList<EUsuarioBDD>{
        val arregloUsuario : ArrayList<EUsuarioBDD> = ESqliteHelperUsuario(this).listarUsuario()
        return arregloUsuario
    }

    fun abrirUsuario(
        clase:Class<*>
    ){
        val intentE = Intent(
            this,
            clase
        )
        startActivity(intentE)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleccionado = id
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId){
            //Editar
            R.id.editarM -> {
                val usuarios = obtenerUsuarios()
                abrirActividadConParametros(
                    EditarUsuario::class.java,
                    usuarios[posicionItemSeleccionado+1].id
                )
                return true
            }
            //Eliminar
            R.id.eliminarM ->{
                val usuarios = obtenerUsuarios()
                ESqliteHelperUsuario(this).eliminarUsuarioFormulario(usuarios[posicionItemSeleccionado+1].id)
                actualizarItemsUsuario()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    fun  abrirActividadConParametros(clase:Class<*>,id : Int){
        val intentExplicito = Intent(this,clase)
        intentExplicito.putExtra("id",id)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }
    override fun onResume() {
        super.onResume()
        actualizarItemsUsuario()
    }

}