package com.mvvmproject.userexperience.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mvvmproject.R
import com.mvvmproject.databinding.LayoutLoginBinding
import com.mvvmproject.userexperience.listing.StoriesFragment
import com.mvvmproject.util.PREF_IS_DARK_APP_THEME
import com.mvvmproject.util.UserPreferenceManager
import com.mvvmproject.util.*
import kotlinx.android.synthetic.main.layout_login.*

class LoginFragment : Fragment() {

    private val userPreferenceManager: UserPreferenceManager by lazy {
        UserPreferenceManager(requireContext())
    }
    private lateinit var loginViewModel: LoginViewModel

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding: LayoutLoginBinding = DataBindingUtil.inflate(inflater,
            R.layout.layout_login, container, false)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
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

    private fun observeViewModel() {
        loginViewModel.loginResponseLiveData.observe(viewLifecycleOwner, Observer {
            loginResponse ->
            if (!loginResponse.token.isNullOrBlank()) {
                activity?.addFragmentWithBackStack(fragmentClass = StoriesFragment::class.java,
                    tag = StoriesFragment::class.java.simpleName)
            } else {
                requireActivity().showSnackBar(loginResponse.description!!)
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
}
