package com.mvvmproject.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mvvmproject.R
import com.mvvmproject.listing.StoriesFragment
import com.mvvmproject.util.PREF_IS_DARK_APP_THEME
import com.mvvmproject.util.UserPreferenceManager
import com.mvvmproject.util.*
import kotlinx.android.synthetic.main.layout_login.*

class LoginFragment : Fragment() {

    private val TAG = this.javaClass.simpleName
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
        return inflater.inflate(R.layout.layout_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtChangeTheme.setOnClickListener {
            if (userPreferenceManager.getBoolean(PREF_IS_DARK_APP_THEME)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                userPreferenceManager.putBoolean(PREF_IS_DARK_APP_THEME, false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                userPreferenceManager.putBoolean(PREF_IS_DARK_APP_THEME, true)
            }
        }

        edtUserName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                isValidEmail()
            }
        }

        edtPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                isValidPassword()
            }
        }

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        btnLogin.setOnClickListener {
            checkValidCredentialsAndOpenStories()
        }
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        observeViewModel()
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
    }

    private fun checkValidCredentialsAndOpenStories() {
        isValidEmail()
        if (isValidPassword()) {
            loginViewModel.validateUser(edtUserName.text.toString(), edtPassword.text.toString())
        }
    }

    private fun isValidEmail(): Boolean {
        if (!edtUserName.text.isValidEmail()) {
            tilUserName.error = getString(R.string.invalid_email)
            return false
        }
        tilUserName.error = null
        return true
    }

    private fun isValidPassword(): Boolean {
        val passwordValidation = edtPassword.text.toString().isValidPassword()
        if (passwordValidation.first) {
            tilPassword.error = null
            return true
        }
        tilPassword.error = passwordValidation.second?.convertListToCSV()
        return false
    }
}
