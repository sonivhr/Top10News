package com.newsapp.userexperience.auth.forgotpassword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.newsapp.R
import com.newsapp.databinding.LayoutForgotPasswordBinding
import com.newsapp.util.appComponent
import com.newsapp.util.getFragmentViewModel
import com.newsapp.util.onDoneButtonClick
import com.newsapp.util.showSnackBar
import dagger.Lazy
import kotlinx.android.synthetic.main.layout_forgot_password.*
import kotlinx.android.synthetic.main.layout_forgot_password.edtEmail
import kotlinx.android.synthetic.main.layout_forgot_password.tilEmail
import kotlinx.android.synthetic.main.layout_user_registration.*
import javax.inject.Inject

class ForgotPasswordFragment: Fragment() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    @Inject
    lateinit var forgotPasswordViewModelCreator: Lazy<ForgotPasswordViewModel>
    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        forgotPasswordViewModel = getFragmentViewModel(forgotPasswordViewModelCreator)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val layoutForgotPasswordBinding: LayoutForgotPasswordBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_forgot_password, container, false)
        layoutForgotPasswordBinding.forgotPasswordViewModel = forgotPasswordViewModel
        observeViewModel()
        return layoutForgotPasswordBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        edtEmail.onDoneButtonClick(inputMethodManager){ forgotPasswordViewModel.validateEmail() }
    }

    private fun observeViewModel() {
        forgotPasswordViewModel.validCredentialEventLiveData.observe(viewLifecycleOwner, Observer {
            validEmailEvent ->
            validEmailEvent.getContentIfNotHandled()?.let {
                sendForgotPasswordRequest()
            }
        })

        forgotPasswordViewModel.emailErrorLiveData.observe(viewLifecycleOwner, Observer {
                usernameError ->
            if (usernameError == null) {
                tilEmail.error = null
            } else {
                tilEmail.error = getString(usernameError)
            }
        })
    }

    private fun sendForgotPasswordRequest() {
        firebaseAuth.sendPasswordResetEmail(forgotPasswordViewModel.email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    requireActivity().showSnackBar(task.result.toString())
                } else {
                    requireActivity().showSnackBar(task.exception?.message!!)
                }
            }
    }
}