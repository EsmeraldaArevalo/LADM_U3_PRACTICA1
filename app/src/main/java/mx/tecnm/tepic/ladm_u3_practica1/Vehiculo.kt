package mx.tecnm.tepic.ladm_u3_practica1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import java.util.ArrayList

class Vehiculo (no:String, dom:String, li: String, ve: String) {
    var id = 0
    var placa = no
    var marca = dom
    var modelo = li
    var año = ve
    var idcon = ""

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

            datos.put("PLACA", placa)
            datos.put("MARCA", marca)
            datos.put("MODELO", modelo)
            datos.put("AÑO", año)
            datos.put("ID_CONDUCTOR", idcon.toInt())

            var res = insertar.insert("VEHICULO", "ID_VEHICULO", datos)

            if (res.toInt() == -1) {
                return false
            }
        }catch (e: SQLiteException) {
            return false
        }
        return true
    }


    fun recuperarDatos(): ArrayList<Vehiculo> {
        var data = ArrayList<Vehiculo>()
        try{
            var bd = BD(puntero!!,nombre,null,1 )
            var select = bd.readableDatabase
            var columnas = arrayOf("*")

            var cursor  = select.query("VEHICULO", columnas, null, null, null, null, null)
            if(cursor.moveToFirst()){
                do{
                    var temp = Vehiculo(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4))

                    temp.id = cursor.getInt(0)
                    data.add(temp)
                }while (cursor.moveToNext())
            }else{
            }
        }catch (e: SQLiteException){
        }
        return data
    }

    fun consultaID(id:String): Vehiculo{
        var registro = Vehiculo("","","","")

        try {
            var bd = BD(puntero!!, nombre, null, 1)
            var select = bd.readableDatabase
            var busca = arrayOf("*")
            var buscaID = arrayOf(id)

            var res = select.query("VEHICULO", busca, "ID_VEHICULO = ?",buscaID, null, null, null)
            if(res.moveToFirst()){
                registro.id = id.toInt()
                registro.placa = res.getString(1)
                registro.marca = res.getString(2)
                registro.modelo = res.getString(3)
                registro.año = res.getString(4)
            }
        }catch (e: SQLiteException){
            e.message.toString()
        }
        return registro
    }

    fun ConsultaNombre(f:String): ArrayList<Vehiculo> {
        var data = ArrayList<Vehiculo>()
        try{
            var bd = BD(puntero!!,nombre,null,1 )
            var select = bd.readableDatabase
            var columnas = arrayOf("*")
            var nombres = arrayOf(f)

            var cursor  = select.query("VEHICULO", columnas, "PLACA = ?", nombres, null, null, null)
            if(cursor.moveToFirst()){
                do{
                    var temp = Vehiculo(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4))

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

            datos.put("PLACA", placa)
            datos.put("MARCA", marca)
            datos.put("MODELO", modelo)
            datos.put("AÑO", año)

            var res = actualizar.update("VEHICULO",datos,"ID_VEHICULO = ?", actualizarID)
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

            var res = eliminar.delete("VEHICULO","ID_VEHICULO = ?",eliminarID)
            if(res.toInt() == 0){
                return false
            }
        }catch (e: SQLiteException){
            return false
        }
        return true
    }


}