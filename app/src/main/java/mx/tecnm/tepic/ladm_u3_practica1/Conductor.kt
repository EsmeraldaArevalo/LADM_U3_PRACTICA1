package mx.tecnm.tepic.ladm_u3_practica1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import java.util.ArrayList

class Conductor (no:String, dom:String, li: String, ve: String) {
    var id = 0
    var nombrecond = no
    var domicilio = dom
    var licencia = li
    var vence = ve

    val nombre = "EMPRESA"
    var puntero : Context?= null

    fun asignarPuntero(p: Context) {
        puntero = p
    }

    fun insertar():Boolean {
        try {
            var base = BD(puntero!!, nombre, null, 1)
            var insertar = base.writableDatabase
            var datos = ContentValues()

            datos.put("NOMBRE", nombrecond)
            datos.put("DOMICILIO", domicilio)
            datos.put("NOLICENCIA", licencia)
            datos.put("VENCE", vence)

            var res = insertar.insert("CONDUCTORES", null, datos)

            if (res.toInt() == -1) {
                return false
            }
        }catch (e: SQLiteException) {
            return false
        }
        return true
    }


    fun recuperarDatos(): ArrayList<Conductor> {
        var data = ArrayList<Conductor>()
        try{
            var bd = BD(puntero!!,nombre,null,1 )
            var select = bd.readableDatabase
            var columnas = arrayOf("*")

            var cursor  = select.query("CONDUCTORES", columnas, null, null, null, null, null)
            if(cursor.moveToFirst()){
                do{
                    var temp = Conductor(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4))

                    temp.id = cursor.getInt(0)
                    data.add(temp)
                }while (cursor.moveToNext())
            }else{
            }
        }catch (e: SQLiteException){
        }
        return data
    }

    fun consultaID(id:String): Conductor{
        var registro = Conductor("","","","")

        try {
            var bd = BD(puntero!!, nombre, null, 1)
            var select = bd.readableDatabase
            var busca = arrayOf("*")
            var buscaID = arrayOf(id)

            var res = select.query("CONDUCTORES", busca, "ID_CONDUCTOR = ?",buscaID, null, null, null)
            if(res.moveToFirst()){
                registro.id = id.toInt()
                registro.nombrecond = res.getString(1)
                registro.domicilio = res.getString(2)
                registro.licencia = res.getString(3)
                registro.vence = res.getString(4)
            }
        }catch (e: SQLiteException){
            e.message.toString()
        }
        return registro
    }

    fun ConsultaNombre(f:String): ArrayList<Conductor> {
        var data = ArrayList<Conductor>()
        try{
            var bd = BD(puntero!!,nombre,null,1 )
            var select = bd.readableDatabase
            var columnas = arrayOf("*")
            var nombres = arrayOf(f)

            var cursor  = select.query("CONDUCTORES", columnas, "NOMBRE = ?", nombres, null, null, null)
            if(cursor.moveToFirst()){
                do{
                    var temp = Conductor(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4))

                    temp.id = cursor.getInt(0)
                    data.add(temp)
                }while (cursor.moveToNext())
            }else{
            }
        }catch (e: SQLiteException){
        }
        return data
    }
    fun actualizar():Boolean{
        try{
            var base = BD(puntero!!, nombre,null,1)
            var actualizar = base.writableDatabase
            var datos = ContentValues()
            var actualizarID = arrayOf(id.toString())

            datos.put("NOMBRE", nombrecond)
            datos.put("DOMICILIO", domicilio)
            datos.put("NOLICENCIA", licencia)
            datos.put("VENCE", vence)

            var res = actualizar.update("CONDUCTORES",datos,"ID_CONDUCTOR = ?", actualizarID)
            if(res == 0){
                return false
            }
        }catch (e: SQLiteException){
            return false
        }
        return true
    }

    fun eliminar(id:String):Boolean{
        try{
            var base = BD(puntero!!, nombre,null,1)
            var eliminar = base.writableDatabase
            var eliminarID = arrayOf(id)

            var res = eliminar.delete("CONDUCTORES","ID_CONDUCTOR = ?",eliminarID)
            if(res.toInt() == 0){
                return false
            }
        }catch (e: SQLiteException){
            return false
        }
        return true
    }


}