package mx.tecnm.tepic.ladm_u3_practica1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.lista
import java.lang.Exception

class MainActivity1 : AppCompatActivity() {
    var listaID = ArrayList<String>()
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BTNBUSCAR.setOnClickListener {
            Buscar()
        }

        lista.setOnItemClickListener{ adapterView, view, i, l ->
            mostrarAlertEliminarActualizar(i)
        }

        BORRAR.setOnClickListener {
            eliminar(NOMBRE.text.toString())
        }

        BTNINSERTAR.setOnClickListener {
            if (NOMBRE.text.toString() == "") {
                Toast.makeText(this,"NO SE HA AGREGADO EL NOMBRE", Toast.LENGTH_LONG)
                        .show()
                return@setOnClickListener
            }
            else if (DOMICILIO.text.toString() == "") {
                Toast.makeText(this,"NO SE HA AGREGADO EL DOMICILIO", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            else if (LICENCIA.text.toString() == "") {
                Toast.makeText(this,"INGRESE EL NUMERO DE LICENCIA", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            else if (VENCE.text.toString() == "") {
                Toast.makeText(this,"NOSE HA AGREGADO LA FECHA DE VENCIMIENTO", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            else if (NOMBRE.text.toString() == "" && DOMICILIO.text.toString() == "" && LICENCIA.text.toString() == "" && VENCE.text.toString() == "") {
                Toast.makeText(this,"NO SE PUEDE INSERTAR CONDUCTOR, RELLENE LOS CAMPOS", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            var cond = Conductor(
                    NOMBRE.text.toString(),
                    DOMICILIO.text.toString(),
                    LICENCIA.text.toString(),
                    VENCE.text.toString()
            )

            cond.asignarPuntero(this)

            var res = cond.insertar()

            if(res == true) {
                mensaje("SE INSERTÓ UN NUEVO CONDUCTOR")
                NOMBRE.setText("")
                DOMICILIO.setText("")
                LICENCIA.setText("")
                VENCE.setText("")
                llenarLista()
            } else {
                mensaje("ERROR! VUELVA A INTENTAR")
            }
        }

        BTNBUSCAR.setOnClickListener {
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
            var c = Conductor("","","","")
            c.asignarPuntero(this)
            var datos = c.recuperarDatos()

            var tamaño = datos.size-1
            var v = Array<String>(datos.size,{""})

            listaID = ArrayList<String>()
            (0..tamaño).forEach {
                var actividad = datos[it]
                var item = "NOMBRE: "+actividad.nombrecond+"\n"+"DOMICILIO: "+actividad.domicilio+"\n"+"LICENCIA: "+actividad.licencia+"\n"+"VENCE: "+actividad.vence
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

    private fun ActualizarActividad(idLista: String) {
        var ventana = Intent(this,MainActivity2::class.java)
        var c = Conductor("","","","")
        c.asignarPuntero(this)

        var d = c.consultaID(idLista).nombrecond
        var fcap = c.consultaID(idLista).domicilio
        var fent = c.consultaID(idLista).licencia
        var v = c.consultaID(idLista).vence

        ventana.putExtra("ID",idLista)
        ventana.putExtra("NOMBRE",d)
        ventana.putExtra("DOMICILIO",fcap)
        ventana.putExtra("NOLICENCIA",fent)
        ventana.putExtra("VENCE",v)

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
        var c = Conductor("","","","")
        c.asignarPuntero(this)

        ventana.putExtra("id",idLista)

        startActivity(ventana)
    }

    private fun eliminar(id:String) {
        var c = Conductor("","","","")
        c.asignarPuntero(this)

        if(c.eliminar(NOMBRE.text.toString())) {
            mensaje("SE HA ELIMINADO LA ACTIVIDAD Y EVIDENCIA")

            var d = Vehiculo("","","","")
            d.asignarPuntero(this)
            d.eliminar(id)

            NOMBRE.setText("")
            var v = Array<String>(0,{""})
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,v)

        } else {
            mensaje("ERROR EN LA ELIMINACION")
        }
    }

    private fun Buscar(){
        try {
            var c = Conductor("","","","")
            c.asignarPuntero(this)
            var datos = c.ConsultaNombre(NOMBRE.text.toString())

            var tamaño = datos.size-1
            var v = Array<String>(datos.size,{""})

            listaID = ArrayList<String>()
            (0..tamaño).forEach {
                var actividad = datos[it]
                var item = actividad.nombrecond+"\n"+actividad.domicilio+"\n"+actividad.licencia+"\n"+actividad.vence
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