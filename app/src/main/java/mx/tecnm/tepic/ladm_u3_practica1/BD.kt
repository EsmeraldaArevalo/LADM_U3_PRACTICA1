package mx.tecnm.tepic.ladm_u3_practica1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BD(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(p: SQLiteDatabase) {
        p.execSQL("CREATE TABLE CONDUCTORES( ID_CONDUCTOR INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR(200), DOMICILIO VARCHAR(200), NOLICENCIA VARCHAR(50), VENCE VARCHAR(50))")
        p.execSQL("CREATE TABLE VEHICULOS( ID_VEHICULO INTEGER PRIMARY KEY AUTOINCREMENT, PLACA VARCHAR(200), MARCA VARCHAR(200), MODELO VARCHAR(200), AÑO INTEGER, ID_CONDUCTOR INTEGER REFERENCES CONDUCTORES(ID_CONDUCTOR))")
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}