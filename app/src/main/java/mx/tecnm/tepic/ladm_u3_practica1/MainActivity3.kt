package mx.tecnm.tepic.ladm_u3_practica1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main3.*
import java.lang.Exception

class MainActivity3 : AppCompatActivity() {
    var listaID = ArrayList<String>()
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listaID = ArrayList<String>()

        BUSCAR.setOnClickListener {
            Buscar()
        }

        lista.setOnItemClickListener{ adapterView, view, i, l ->
            mostrarAlertEliminarActualizar(i)
        }

        BTNBORRAR.setOnClickListener {
            eliminar(PLACA.text.toString())
        }

        INSERTAR.setOnClickListener {
            if (PLACA.text.toString() == "") {
                Toast.makeText(this,"NO SE HA AGREGADO EL PLACA", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            else if (MARCA.text.toString() == "") {
                Toast.makeText(this,"NO SE HA AGREGADO EL MARCA", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            else if (MODELO.text.toString() == "") {
                Toast.makeText(this,"INGRESE EL NUMERO DE MODELO", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            else if (AÑO.text.toString() == "") {
                Toast.makeText(this,"NOSE HA AGREGADO EL AÑO", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            else if (PLACA.text.toString() == "" && MARCA.text.toString() == "" && MODELO.text.toString() == "" && AÑO.text.toString() == "") {
                Toast.makeText(this,"NO SE PUEDE INSERTAR EL HEVICULO, RELLENE LOS CAMPOS", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            var cond = Vehiculo(
                PLACA.text.toString(),
                MARCA.text.toString(),
                MODELO.text.toString(),
                AÑO.text.toString()
                
            )

            cond.asignarPuntero(this)

            var res = cond.insertar()

            if(res == true) {
                mensaje("SE INSERTÓ UN NUEVO CONDUCTOR")
                PLACA.setText("")
                MARCA.setText("")
                MODELO.setText("")
                AÑO.setText("")
                llenarLista()
            } else {
                mensaje("ERROR! VUELVA A INTENTAR")
            }
        }

        BUSCAR.setOnClickListener {
            var ventana = Intent(this,MainActivity3::class.java)
            startActivityForResult(ventana,0)
        }

        llenarLista()
    }

    private fun mensaje(s: String) {
        AlertDialog.Builder(this).setTitle("ATENCION").setMessage(s)
            .setPositiveButton("OK"){
                    d,i-> d.dismiss()
            }
            .show()
    }

    private fun llenarLista(){
        try {
            var c = Vehiculo("","","","")
            c.asignarPuntero(this)
            var datos = c.recuperarDatos()

            var tamaño = datos.size-1
            var v = Array<String>(datos.size,{""})

            listaID = ArrayList<String>()
            (0..tamaño).forEach {
                var actividad = datos[it]
                var item = "PLACA: "+actividad.placa+"\n"+"MARCA: "+actividad.marca+"\n"+"MODELO: "+actividad.modelo+"\n"+"AÑO: "+actividad.año
                v[it] = item
                listaID.add(actividad.id.toString())
            }

            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,v)

            lista.setOnItemClickListener{ adapterView, view, i, l ->
                mostrarAlertEAI(i)
            }
        }catch (e: Exception){
            mensaje(e.message.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        llenarLista()
    }

    private fun ActualizarActividad(idLista: String) {
        var ventana = Intent(this,MainActivity2::class.java)
        var c = Vehiculo("","","","")
        c.asignarPuntero(this)

        var d = c.consultaID(idLista).placa
        var fcap = c.consultaID(idLista).marca
        var fent = c.consultaID(idLista).modelo
        var v = c.consultaID(idLista).año

        ventana.putExtra("ID",idLista)
        ventana.putExtra("PLACA",d)
        ventana.putExtra("MARCA",fcap)
        ventana.putExtra("NOMODELO",fent)
        ventana.putExtra("AÑO",v)

        startActivityForResult(ventana,0)
    }
    private fun mostrarAlertEAI(posicion: Int) {
        var idLista = listaID.get(posicion)

        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage("¿ACTIVIDAD?")
            .setNegativeButton("CANCELAR") {d,i-> }
            .setNeutralButton("ACTUALIZAR") {d,i-> ActualizarActividad(idLista)}
            .setPositiveButton("INSERTAR EVIDENCIA") {d,i-> EntregarEvidencia(idLista)}
            .show()
    }

    private fun EntregarEvidencia(idLista: String) {
        var ventana = Intent(this,Inicio::class.java)

        ventana.putExtra("id",idLista)

        startActivityForResult(ventana,0)
    }



    private fun mostrarAlertEliminarActualizar(posicion: Int) {
        var idLista = listaID.get(posicion)

        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage("¿Que DESEA HACER CON LA ACTIVIDAD?")
            .setPositiveButton("ELIMINAR") {d,i-> eliminar(idLista)}
            .setNeutralButton("VER DETALLES") {d,i-> llamarVentanaDetalles(idLista)}
            .setNegativeButton("CANCELAR") {d,i->}
            .show()
    }

    private fun llamarVentanaDetalles(idLista: String) {
        var ventana = Intent(this,MainActivity4::class.java)
        var c = Vehiculo("","","","")
        c.asignarPuntero(this)

        ventana.putExtra("id",idLista)

        startActivity(ventana)
    }

    private fun eliminar(id:String) {
        var c = Vehiculo("","","","")
        c.asignarPuntero(this)

        if(c.eliminar(PLACA.text.toString())) {
            mensaje("SE HA ELIMINADO LA ACTIVIDAD Y EVIDENCIA")

            var d = Vehiculo("","","","")
            d.asignarPuntero(this)
            d.eliminar(id)

            PLACA.setText("")
            var v = Array<String>(0,{""})
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,v)

        } else {
            mensaje("ERROR EN LA ELIMINACION")
        }
    }

    private fun Buscar(){
        try {
            var c = Vehiculo("","","","")
            c.asignarPuntero(this)
            var datos = c.ConsultaNombre(PLACA.text.toString())

            var tamaño = datos.size-1
            var v = Array<String>(datos.size,{""})

            listaID = ArrayList<String>()
            (0..tamaño).forEach {
                var actividad = datos[it]
                var item = actividad.placa+"\n"+actividad.marca+"\n"+actividad.modelo+"\n"+actividad.año
                v[it] = item
                listaID.add(actividad.id.toString())
            }

            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,v)

            lista.setOnItemClickListener{ adapterView, view, i, l ->
                mostrarAlertEliminarActualizar(i)
            }
        }catch (e: Exception){
            mensaje(e.message.toString())
        }
    }

}