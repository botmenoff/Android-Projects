package com.example.rickyapidemo

import CharacterSpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.myapplication.ApiFiles.Character
import com.example.myapplication.ApiFiles.CharacterAPIService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pagCounter:Int = 1

        val buttonPagUp = findViewById<Button>(R.id.pagUp)
        val buttonPagDown = findViewById<Button>(R.id.pagDown)
        val pagdisplay = findViewById<TextView>(R.id.pagDisplay)
        val characterSpinner: Spinner = findViewById(R.id.characterSpinner)
        characterSpinner.setSelection(0)

        makeApiRequest(pagCounter)
        buttonPagUp.setOnClickListener {
            if (pagCounter == 42) {
                Toast.makeText(this, "No rompas la app =(", Toast.LENGTH_LONG).show()
            } else {
                pagCounter = pagCounter + 1
                makeApiRequest(pagCounter)
                pagdisplay.text = "Pag " + pagCounter + "/42"
            }

        }
        buttonPagDown.setOnClickListener {
            if (pagCounter == 1) {
                Toast.makeText(this, "No rompas la app =(", Toast.LENGTH_LONG).show()
            } else {
                pagCounter = pagCounter - 1
                makeApiRequest(pagCounter)
                pagdisplay.text = "Pag " + pagCounter
            }

        }


        characterSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            // Buscas en el array del adapter el que coincida
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCharacter: com.example.myapplication.ApiFiles.Character = characterSpinner.selectedItem as Character // Devuelve el character seleccionado actualmente
                // Toast.makeText(this@MainActivity, "Selected Character: ${selectedCharacter.image}", Toast.LENGTH_SHORT).show()
                // Cargar la imagen con el Glide
                val characterImage = findViewById<ImageView>(R.id.characterImage)
                Glide.with(this@MainActivity)
                    .load(selectedCharacter.image)
                    .into(characterImage)
                // Cargar todos los otros valores del personaje
                val statusDisplay = findViewById<TextView>(R.id.statusDisplay)
                statusDisplay.text = "Status: " + selectedCharacter.status
                val speciesDisplay = findViewById<TextView>(R.id.speciesDisplay)
                speciesDisplay.text = "Species: " + selectedCharacter.species
                val genderDisplay = findViewById<TextView>(R.id.genderDisplay)
                genderDisplay.text = "Gender: " + selectedCharacter.gender

                // Cambiar el color del texto del status dependiendo del estado del personaje
                when(selectedCharacter.status) {
                    "Dead" -> statusDisplay.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                    "Alive" -> statusDisplay.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.green))
                    else -> statusDisplay.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.grey))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada pero lo tengo que implementar porque sino peta
            }
        })
    }

    private fun makeApiRequest(pageNumber: Int) {
        GlobalScope.launch {
            try {
                val characterSpinner: Spinner = findViewById(R.id.characterSpinner)
                val response = getRetrofit().create(CharacterAPIService::class.java).getAll(pageNumber).execute()
                if (response.isSuccessful) {
                    val charactersResponse = response.body()
                    charactersResponse?.let { characterResponse ->
                        val characters = characterResponse.results
                        val spinnerAdapter = CharacterSpinnerAdapter(this@MainActivity, characters)

                        runOnUiThread {
                            characterSpinner.adapter = spinnerAdapter
                        }
                    } ?: Log.e("MainActivity", "Response body is null")
                } else {
                    Log.e("MainActivity", "Failed to fetch characters. Error code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching characters", e)
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
