package com.newsapp.userexperience.auth.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.newsapp.R
import com.newsapp.databinding.LayoutUserRegistrationBinding
import com.newsapp.userexperience.headlines.HeadlinesFragment
import com.newsapp.util.*
import dagger.Lazy
import kotlinx.android.synthetic.main.layout_login.tilPassword
import kotlinx.android.synthetic.main.layout_user_registration.*
import javax.inject.Inject

class UserRegistrationFragment: Fragment() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    @Inject
    lateinit var userRegistrationViewModelCreator: Lazy<UserRegistrationViewModel>
    private lateinit var userRegistrationViewModel: UserRegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        userRegistrationViewModel = getFragmentViewModel(userRegistrationViewModelCreator)
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
        val dataBinding: LayoutUserRegistrationBinding = DataBindingUtil.inflate(inflater,
            R.layout.layout_user_registration, container, false)
        dataBinding.userRegistrationViewModel = this.userRegistrationViewModel
        observeViewModel()
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        edtConfirmPassword.onDoneButtonClick(inputMethodManager) {
            userRegistrationViewModel.registerAndLogin()
        }
    }

    private fun observeViewModel() {
        userRegistrationViewModel.validCredentialEventLiveData.observe(viewLifecycleOwner, Observer {
                loginResponseEvent ->
            loginResponseEvent.getContentIfNotHandled()?.let {
                registerAndLogin()
            }
        })

        userRegistrationViewModel.usernameErrorLiveData.observe(viewLifecycleOwner, Observer {
                usernameError ->
            if (usernameError == null) {
                tilEmail.error = null
            } else {
                tilEmail.error = getString(usernameError)
            }
        })

        userRegistrationViewModel.passwordErrorLiveData.observe(viewLifecycleOwner, Observer {
                passwordError ->
            if (passwordError == null) {
                tilPassword.error = null
            } else {
                tilPassword.error = passwordError
            }
        })

        userRegistrationViewModel.confirmPasswordErrorLiveData.observe(viewLifecycleOwner, Observer {
                confirmPasswordError ->
            if (confirmPasswordError == null) {
                tilConfirmPassword.error = null
            } else {
                tilConfirmPassword.error = getString(confirmPasswordError)
            }
        })
    }

    private fun registerAndLogin() {
        firebaseAuth.createUserWithEmailAndPassword(
            userRegistrationViewModel.username, userRegistrationViewModel.password)
            .addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                if (task.isSuccessful) {
                    requireActivity().clearBackStackAndAddFragment(fragmentClass = HeadlinesFragment::class.java)
                } else {
                    requireActivity().showSnackBar(task.exception?.message!!)
                }
            })
    }
}