package br.senac.pi4.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import br.senac.pi4.R
import br.senac.pi4.activity.HomeActivity
import br.senac.pi4.activity.SettingsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationFragment : Fragment(R.layout.fragment_bottom_navigation) {

    companion object {
        private const val ARG_SELECTED_ITEM_ID = "selectedItemId"

        fun newInstance(selectedItemId: Int = R.id.home): BottomNavigationFragment {
            return BottomNavigationFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SELECTED_ITEM_ID, selectedItemId)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Recupera o argumento ou usa "home" como padrão
        val selectedItemId = arguments?.getInt(ARG_SELECTED_ITEM_ID) ?: R.id.home

        bottomNavigationView.selectedItemId = selectedItemId

        // Configura o listener para cliques no menu
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    Log.d("menu 01", "CLICK")
                    true
                }

                R.id.myProject -> {
                    Log.d("menu 02", "CLICK")
                    true
                }

                R.id.home -> {
                    Log.d("menu HOME", "CLICK")
                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                    true
                }

                R.id.settings -> {
                    startActivity(Intent(requireContext(), SettingsActivity::class.java))
                    true
                }

                else -> false
            }
        }

        bottomNavigationView.setOnItemReselectedListener { item ->
            Log.d("BottomNav", "Item ${item.title} foi selecionado novamente!")
            (activity as? BottomNavigationListener)?.onItemReselected(item.itemId)
        }
    }

    interface BottomNavigationListener {
        fun onItemReselected(itemId: Int)
    }
}
