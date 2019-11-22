package com.example.gamecompanion.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.gamecompanion.R
import com.example.gamecompanion.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //1 Listener
        loginButton.setOnClickListener {
            //2 Read TextFields
            val username = usernameEditText.text?.toString().orEmpty()
            val email = emailEditText.text?.toString().orEmpty()
            val password = passwordEditText.text?.toString().orEmpty()
            //3 Validacio
            //3.1 Username validation
            if(username.trim().isEmpty()){
                //Error
                Toast.makeText(this, getString(R.string.tab_profile_userError),Toast.LENGTH_LONG).show()
                //or
                usernameEditText.error = "Username required"
                return@setOnClickListener

            }
            //3.2 Email Validation
            if(email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                //Error
                Toast.makeText(this, getString(R.string.tab_profile_emailError),Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //3.3 Password Validation

            if(password.isBlank() || !isPasswordValid(password)){
                //Error
                Toast.makeText(this, getString(R.string.tab_profile_passwordError),Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //4 Create User
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener { authResult ->
                    Toast.makeText(this, "Success creating new user",Toast.LENGTH_LONG).show()
                    val userModel = UserModel(
                        id = authResult.user?.uid ?: "",
                        username = username,
                        email = email
                    )
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(authResult.user?.uid ?: "")
                        .set(userModel)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Success creating new user", Toast.LENGTH_LONG).show()
                            finish()
                        }
                }
                .addOnFailureListener {
                    //5 Handle Errors
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun isPasswordValid(password: String): Boolean{

        //password >= 4 characters

        if(password.length <4 )
            return false

        var digit =0
        var let =0

        //Contain letter and number

         for(letter in password){
             if(letter.isDigit())
                 digit++
             else if(letter.isLetter())
                 let++
         }

        if(digit== password.length)
            return false

        if(let==password.length)
            return false


        return true
    }
}
