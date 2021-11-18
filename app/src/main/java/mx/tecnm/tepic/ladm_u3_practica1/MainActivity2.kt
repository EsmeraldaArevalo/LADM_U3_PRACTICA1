package mx.tecnm.tepic.ladm_u3_practica1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var extras = intent.extras

        descripcion.setText(extras!!.getString("desc"))
        Fcaptura.setText(extras!!.getString("fcap"))
        Fentrega.setText(extras!!.getString("fent"))

        id = extras.getString("id").toString()

        actualizar.setOnClickListener {
            var c = Conductor(descripcion.text.toString(), Fcaptura.text.toString(), Fentrega.text.toString(), Fentrega.text.toString())
            c.id = id.toInt()
            c.asignarPuntero(this)

            if(c.actualizar()) {
                Toast.makeText(this,"SE ACTUALIZÓ LA ACTIVIDAD", Toast.LENGTH_LONG)
                    .show()
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("AL ACTUALIZAR LA ACTIVIDAD")
                    .setPositiveButton("OK"){d,i->}
                    .show()
            }
            finish()
        }
    }
}