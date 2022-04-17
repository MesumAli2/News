package com.example.news

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var navController: NavController

    private lateinit var viewModel: NewsViewModel
    private lateinit var viewModelFactory: NewsViewModel.Factory
//    lateinit var toggle: ActionBarDrawerToggle
    //lateinit var hostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModelFactory = NewsViewModel.Factory(this.application )
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)

        // Retrieve NavController from the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        //val appBarConfiguration = AppBarConfiguration(navController.graph)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.headlines)
        )

       findViewById<Toolbar>(R.id.toolbar)
          .setupWithNavController(navController, appBarConfiguration)

        findViewById<Toolbar>(R.id.toolbar)
            .setTitleTextAppearance(this, R.style.toolBarTextFont)

        /*   val navHostFragment =   supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
           navController = navHostFragment.navController

           NavigationUI.setupActionBarWithNavController(this, navController)
   */

       /* navView.setNavigationItemSelectedListener() {
           when(it.itemId){
               R.id.miItem1 -> {
                   viewModel.serachNews("business")
                   viewModel.playlist.observe(this){
                       bussinesnews -> bussinesnews.let {   val adapter = MainAdapter{}
                       adapter.submitList(it) }
                      drawerlayout.closeDrawer(Gravity.LEFT)
                       val busines = listOf<String>("Latest Business News", "Business", "Business update")
                       Snackbar.make(findViewById(R.id.drawerlayout), "${busines.random()}", Snackbar.LENGTH_LONG).show()
                   }

               }
               R.id.miitem2 -> {
                   viewModel.serachNews("Healthy")
                   viewModel.playlist.observe(this){
                           bussinesnews -> bussinesnews.let {   val adapter = MainAdapter{}
                       adapter.submitList(it) }
                      drawerlayout.closeDrawer(Gravity.LEFT)
                       val Health = listOf<String>("Health Updates", "Stay Healthy", "Health Tips")
                       Snackbar.make(findViewById(R.id.drawerlayout), "${Health.random()}", Snackbar.LENGTH_LONG).show()
                   }
               }
               R.id.miitem3 -> {
                   viewModel.serachNews("Travel")
                   viewModel.playlist.observe(this){
                           bussinesnews -> bussinesnews.let {   val adapter = MainAdapter{}
                       adapter.submitList(it) }
                     drawerlayout.closeDrawer(Gravity.LEFT)
                       val Travel = listOf<String>("Travel Updates", "Travel", "Travel News")
                       Snackbar.make(findViewById(R.id.drawerlayout), "${Travel.random()}", Snackbar.LENGTH_LONG).show()
                   }
               }
               R.id.miitem4 ->{
                   viewModel.serachNews("variants")
                   viewModel.playlist.observe(this){
                           bussinesnews -> bussinesnews.let {   val adapter = MainAdapter{}
                       adapter.submitList(it) }
                      drawerlayout.closeDrawer(Gravity.LEFT)
                       val variants = listOf<String>("Covid19", "Stay Safe and up to date", "Stay Home")
                       Snackbar.make(findViewById(R.id.drawerlayout), "${variants.random()}", Snackbar.LENGTH_LONG).show()
                   }}

               R.id.miitem5 -> {

               }
           }
           true
       }
 */
    }
 /*   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)

    }*/


    /*override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
*/


    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()


    }



}
