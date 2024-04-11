package com.example.myapplication
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import android.view.MenuItem
//import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.drawerlayout.widget.DrawerLayout
//import com.google.android.material.navigation.NavigationView
//
//class TopNav : AppCompatActivity() {
//
//    private lateinit var drawerLayout: DrawerLayout
//    private lateinit var navView: NavigationView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.menu_nav)
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
//
//        drawerLayout = findViewById(R.id.drawer_layout)
//        navView = findViewById(R.id.nav_view)
//
//        val toggle = ActionBarDrawerToggle(
//            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
//        )
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        navView.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.nav_my_profile -> {
//                    startActivity(Intent(this, MyProfileActivity::class.java))
//                    true
//                }
//                R.id.nav_labs -> {
//                    startActivity(Intent(this, LabsActivity::class.java))
//                    true
//                }
//                R.id.nav_report_grievance -> {
//                    startActivity(Intent(this, ReportGrievanceActivity::class.java))
//                    true
//                }
//                R.id.nav_view_grievances -> {
//                    startActivity(Intent(this, ViewGrievanceActivity::class.java))
//                    true
//                }
//                else -> false
//            }
//        }
//
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                drawerLayout.openDrawer(navView)
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//}