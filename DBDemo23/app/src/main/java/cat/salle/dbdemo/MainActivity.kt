package cat.salle.dbdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Es otro hilo que no es el de las views
        GlobalScope.launch {
            // Instanciamos el AppDatabase
            db = AppDatabase.getInstance(applicationContext)!!
            // Hacemos el insert en la BBDD
            db.SerieDAO().insert(Serie(null, "Avatar", "Nose"))
            db.SerieDAO().insert(Serie(null, "Batman", "Gotham"))
            db.SerieDAO().insert(Serie(null, "La niña exorzista", "Nose"))

            val allSeries = db.SerieDAO().loadAllSeries()
            val allNose = db.SerieDAO().loadNose()

            // Renderizar el fragment
            supportFragmentManager
                .beginTransaction()
                .add(R.id.bbddView, SerieFragment.newInstance(1))
                .commit()

            // Coger los dos inputs
            val firstNameInput = findViewById<TextInputLayout>(R.id.firstNameInput)
            val secondNameInput = findViewById<TextInputLayout>(R.id.secondNameInput)
            val addButton = findViewById<Button>(R.id.addButton)

            // Accion de añadir
            addButton.setOnClickListener {
                try {
                    val firstName = findViewById<TextInputEditText>(R.id.firstNameInput).text.toString()
                    val secondName = findViewById<TextInputEditText>(R.id.secondNameInput).text.toString()
                    db.SerieDAO().insert(Serie(null, firstName, secondName))
                } catch (e: Exception) {
                    Log.i("---->", "Error al insertar datos", e)
                }
            }

            allNose.forEach {
                Log.i("---->", it.toString())
            }

            allSeries.forEach {
                Log.i("-->", it.toString())
            }
        }
    }
}