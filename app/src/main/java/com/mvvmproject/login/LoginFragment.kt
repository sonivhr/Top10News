package com.mvvmproject.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.mvvmproject.R
import com.mvvmproject.fragmentutil.addFragmentWithBackStack
import com.mvvmproject.listing.StoriesFragment
import kotlinx.android.synthetic.main.layout_login.*

class LoginFragment : Fragment() {

    private val TAG = this.javaClass.simpleName
    private var darkTheme = false

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
            Log.e(TAG, "txtChangeTheme clicked")
            if (darkTheme) {
                darkTheme = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                darkTheme = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        edtUserName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !edtUserName.text.toString().isValidEmail()) {
                tilUserName.error = getString(R.string.invalid_email)
            } else {
                tilUserName.error = null
            }
        }

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        btnLogin.setOnClickListener {
            activity?.addFragmentWithBackStack(fragmentClass = StoriesFragment::class.java,
                tag = StoriesFragment::class.java.simpleName)
        }
    }
}