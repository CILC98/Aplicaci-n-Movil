package com.example.myapplication

class EUsuarioBDD (
    var id: Int,
    var nombre:String,
    var descipcion: String
    )
{
    override fun toString(): String {
        return "${id}.-${nombre}\n${descipcion}"
    }
}