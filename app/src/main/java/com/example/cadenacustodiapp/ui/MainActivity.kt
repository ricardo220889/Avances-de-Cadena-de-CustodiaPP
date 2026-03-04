package com.example.cadenacustodiapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.ActivityMainBinding
import com.example.cadenacustodiapp.pdf.CreatePdf
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toogle : ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout= findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setBackgroundColor(getResources().getColor(R.color.gray))
        navView.itemIconTintList=null

        toogle= ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportFragmentManager.beginTransaction()
            .replace(R.id.FragmentContainer, CreatePdf())
            .commit()
        navView.setCheckedItem(R.id.createPdf_nav)

        navView.setNavigationItemSelectedListener {
            it.isChecked=true
            when (it.itemId) {


                R.id.nav_1 -> replaceFragment(GeneralesFragment())
                R.id.nav_2 -> replaceFragment(InicioFragment())
                R.id.nav_3 -> replaceFragment(IdentidadFragment())
                R.id.nav_4 -> replaceFragment(DocumentacionFragment())
                R.id.nav_5 -> replaceFragment(RecoleccionFragment())
                R.id.nav_6 -> replaceFragment(EmbalajeFragment())
                R.id.nav_7 -> replaceFragment(ServPubFragment())
                R.id.nav_8 -> replaceFragment(TrasladoFragment())
                R.id.createPdf_nav ->replaceFragment(CreatePdf())

            }

            true
        }

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction= fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.FragmentContainer,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
    }
}