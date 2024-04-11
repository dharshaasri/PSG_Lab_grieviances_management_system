package com.example.myapplication
//
import ReportGrievanceFragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class BottomNav : MenuActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var username: String // Variable to hold the username
    private lateinit var email: String
    private lateinit var className: String
    private lateinit var rollNumber: String
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_nav)
        auth = FirebaseAuth.getInstance()
        username = intent.getStringExtra("username") ?: "DefaultUsername"
        email = intent.getStringExtra("email") ?: "DefaultEmail"
        className = intent.getStringExtra("class") ?: "DefaultClass"
        rollNumber = intent.getStringExtra("rollNumber") ?: "DefaultRollnumber"// Default value if username is not provided
        bottomNavigationView=findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.action_home->{
                    replaceFragment(HomeFragment(),username,email,className,rollNumber)
                    true
                }
                R.id.action_report->{
                    replaceFragment(ReportGrievanceFragment(),username,email,className,rollNumber)
                    true
                }
                R.id.action_view->{
                    replaceFragment(ViewGrievanceFragment(),username,email,className,rollNumber)
                    true
                }
                R.id.action_profile->{
                    replaceFragment(ProfileFragment(),username,email,className,rollNumber)
                    true
                }
                R.id.btnLogout->{
                    // Handle logout
                    logout()
                    true
                }
                else->false
            }
        }
        replaceFragment(HomeFragment(),username,email,className,rollNumber)
    }
    private fun replaceFragment(fragment: Fragment, username: String, email: String, className: String, rollNumber: String){
        val bundle = Bundle()
        bundle.putString("username", username)
        bundle.putString("email", email)
        bundle.putString("class", className)
        bundle.putString("rollnumber",rollNumber)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit()
    }
    private fun logout() {
        // Clear user authentication credentials
        auth.signOut()

        // Redirect the user to the login screen
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

