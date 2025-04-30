package br.senac.pi4

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.senac.pi4.activity.HomeActivity
import br.senac.pi4.activity.LoginActivity
import br.senac.pi4.activity.SettingsActivity
import com.google.android.material.internal.EdgeToEdgeUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, SettingsActivity::class.java))
        finish()

    }
}