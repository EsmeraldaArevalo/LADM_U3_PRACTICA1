package mx.tecnm.tepic.ladm_u3_practica1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.inicio.*

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio)

        conductores.setOnClickListener {
            var activity1 = Intent(this, MainActivity2::class.java)
            startActivity(activity1)
        }

        vehiculos.setOnClickListener {
            var activity3 = Intent(this, MainActivity3::class.java)
            startActivity(activity3)
        }
    }
}