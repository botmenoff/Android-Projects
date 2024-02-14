package com.example.menu_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    // Hacer que se renderize en la MainActivity
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    // Añadir eventos de clic a cada item del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.help -> {
                Toast.makeText(this, "Item1", Toast.LENGTH_LONG).show()
                true
            }
            R.id.help2 -> {
                Toast.makeText(this, "Item2", Toast.LENGTH_LONG).show()
                // Mirar si el fragment se esta mostrando
                val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

                // Verificar si el fragment esta activo, si esta activo lo eliminas
                if (fragment != null && fragment.isVisible) {
                    supportFragmentManager
                        .beginTransaction()
                        .remove(fragment)
                        .commit()
                } else {
                    Toast.makeText(this, "NO ESTA EK GRAGMENT", Toast.LENGTH_LONG).show()
                }
                true
            }
            R.id.help3 -> {
                Toast.makeText(this, "Item3", Toast.LENGTH_LONG).show()
                // renderFragment()
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainerView, FragmentMenu.newInstance("null", "null"))
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun renderFragment() {
        // Obtiene una instancia del FragmentManager asociado con esta actividad
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val fragment: Fragment = FragmentMenu()

        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)

        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }

}