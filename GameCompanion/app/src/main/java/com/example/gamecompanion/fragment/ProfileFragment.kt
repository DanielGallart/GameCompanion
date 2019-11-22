package com.example.gamecompanion.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gamecompanion.R
import com.example.gamecompanion.activities.RegisterActivity
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.utils.COLLECTION_USERS
import com.example.gamecompanion.utils.SharedPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

        //Init /Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        }

    override fun onResume() {
        super.onResume()
        initUI()
    }

        private fun initUI() {
            //TODO: if user == null
            //Check user available
            if (FirebaseAuth.getInstance().currentUser == null) {

                logoutButton.visibility= View.GONE
                usernameTextView.visibility = View.GONE
                //show register button
                registerButton.visibility = View.VISIBLE
                registerButton.setOnClickListener {
                    //TODO: Go to Register
                    startActivity(Intent(requireContext(), RegisterActivity::class.java))
                }
            } else {
                //hide register button
                registerButton.visibility = View.GONE
                usernameTextView.visibility = View.VISIBLE
                //else: Show Profile
                logoutButton.visibility = View.VISIBLE
                logoutButton.setOnClickListener {
                    //logout user
                    FirebaseAuth.getInstance().signOut()
                    //Clear shared preferences
                    SharedPreferencesManager().clear(requireContext())
                    //Rebuild UI
                    initUI()
                }
                showUser()
            }
        }

    private fun showUser(){
        Log.i("ProfileFragment", "Getting user")
        SharedPreferencesManager().getUsername(requireContext())?.let { username ->
            //Got username
            Log.i("ProfileFragment", "Got user")
            usernameTextView.text = "Hello ${username.capitalize()}"


        }?: run {
            //No username
            //Get user profile(from firestore)

            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            FirebaseFirestore.getInstance().collection(COLLECTION_USERS).document(userId).get()
                .addOnSuccessListener { documentSnapshot ->
                    Log.i("ProfileFragment", "Got user from firestore")
                    val userProfile = documentSnapshot.toObject(UserModel::class.java)
                    usernameTextView.text = "Hello ${userProfile?.username?.capitalize()}"
                    //Save locally
                    SharedPreferencesManager().setUsername(requireContext(), userProfile?.username)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }
    }
    }

