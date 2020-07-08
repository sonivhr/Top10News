package com.newsapp.userexperience.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.newsapp.R
import com.newsapp.databinding.LayoutLoginBinding
import com.newsapp.userexperience.headlines.HeadlinesFragment
import com.newsapp.helperclasses.PREF_IS_DARK_APP_THEME
import com.newsapp.helperclasses.UserPreferenceManager
import com.newsapp.userexperience.auth.register.UserRegistrationFragment
import com.newsapp.util.*
import dagger.Lazy
import kotlinx.android.synthetic.main.layout_login.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    @Inject
    lateinit var loginViewModelCreator: Lazy<LoginViewModel>
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        loginViewModel = getFragmentViewModel(loginViewModelCreator)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding: LayoutLoginBinding = DataBindingUtil.inflate(inflater,
            R.layout.layout_login, container, false)
        dataBinding.loginViewModel = this.loginViewModel
        dataBinding.loginFragment = this
        observeViewModel()
        return dataBinding.root
    }

    fun changeTheme() {
        if (userPreferenceManager.getBoolean(PREF_IS_DARK_APP_THEME)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            userPreferenceManager.putBoolean(PREF_IS_DARK_APP_THEME, false)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            userPreferenceManager.putBoolean(PREF_IS_DARK_APP_THEME, true)
        }
    }

    fun navigateToUserRegistration() {
        requireActivity().replaceFragmentWithBackStack(fragmentClass = UserRegistrationFragment::class.java,
        tag = UserRegistrationFragment::class.java.simpleName)
    }

    private fun observeViewModel() {
        loginViewModel.validCredentialEventLiveData.observe(viewLifecycleOwner, Observer {
            loginResponseEvent ->
            loginResponseEvent.getContentIfNotHandled()?.let {
                login()
            }
        })

        loginViewModel.usernameErrorLiveData.observe(viewLifecycleOwner, Observer {
            usernameError ->
            if (usernameError == null) {
                tilUserName.error = null
            } else {
                tilUserName.error = getString(usernameError)
            }
        })

        loginViewModel.passwordErrorLiveData.observe(viewLifecycleOwner, Observer {
                passwordError ->
            if (passwordError == null) {
                tilPassword.error = null
            } else {
                tilPassword.error = passwordError
            }
        })
    }

    private fun login() {
        firebaseAuth.signInWithEmailAndPassword(loginViewModel.username!!, loginViewModel.password!!)
            .addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                if (task.isSuccessful) {
                    requireActivity().replaceFragmentWithoutBackStack(fragmentClass = HeadlinesFragment::class.java)
                } else {
                    requireActivity().showSnackBar(task.exception?.message!!)
                }
            })
    }
}
