package com.newsapp.userexperience.auth.external

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.newsapp.R
import com.newsapp.helperclasses.PREF_IS_DARK_APP_THEME
import com.newsapp.helperclasses.UserPreferenceManager
import com.newsapp.userexperience.headlines.HeadlinesFragment
import com.newsapp.util.appComponent
import com.newsapp.util.replaceFragmentWithoutBackStack
import com.newsapp.util.showSnackBar
import kotlinx.android.synthetic.main.layout_external_login.*
import javax.inject.Inject

const val REQUEST_CODE_SIGN_IN = 1000
class ExternalLoginFragment: Fragment(R.layout.layout_external_login) {
    private val TAG = "ExternalLoginFragment"
    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    @Inject
    lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        checkUserLoggedInAndUpdateUI()
    }

    private fun setOnClickListeners() {
        googleSignInButton.setOnClickListener { signIn() }
        txtChangeTheme.setOnClickListener { changeTheme() }
    }

    private fun checkUserLoggedInAndUpdateUI() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            requireActivity().replaceFragmentWithoutBackStack(
                fragmentClass = HeadlinesFragment::class.java)
            return
        }

    }

    private fun changeTheme() {
        if (userPreferenceManager.getBoolean(PREF_IS_DARK_APP_THEME)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            userPreferenceManager.putBoolean(PREF_IS_DARK_APP_THEME, false)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            userPreferenceManager.putBoolean(PREF_IS_DARK_APP_THEME, true)
        }
    }

    private fun signIn() {
        val googleSignInClient =
            GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken)
                } catch (e: ApiException) {
                    e.localizedMessage?.let {
                        requireActivity().showSnackBar(it)
                    }
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    requireActivity().replaceFragmentWithoutBackStack(
                        fragmentClass = HeadlinesFragment::class.java)
                } else {
                    requireActivity().showSnackBar(task.exception?.message!!)
                }
            }
    }
}