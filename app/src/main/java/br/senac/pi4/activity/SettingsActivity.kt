package br.senac.pi4.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.senac.pi4.R
import br.senac.pi4.fragment.BottomNavigationFragment

class SettingsActivity : AppCompatActivity(), BottomNavigationFragment.BottomNavigationListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        // Adiciona o fragmento da Bottom Navigation
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, BottomNavigationFragment.newInstance(R.id.settings))
            .commit()
    }

    override fun onItemReselected(itemId: Int) {
        when (itemId) {
            R.id.home -> Log.d("MainActivity", "Home foi re-selecionado")
            R.id.settings -> Log.d("MainActivity", "Settings foi re-selecionado")
            else -> Log.d("MainActivity", "Outro item foi re-selecionado")
        }
    }
}