package com.example.pricetracker.ui.auth

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.pricetracker.R
import com.example.pricetracker.ui.BaseFragment
import kotlinx.android.synthetic.main.auth_fragment.*

class AuthFragment : BaseFragment(R.layout.auth_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        next_button.setOnClickListener {
            if (!isPasswordValid(password_edit_text.text)) {
                password_text_input.error = getString(R.string.error_password)
            } else {
                password_text_input.error = null
                redirectLogin()
            }
        }

        password_edit_text.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(password_edit_text.text)) {
                password_text_input.error = null
            }
            false
        }
    }

    private fun redirectLogin() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.authFragment, true)
            .build()
        findNavController().navigate(
            AuthFragmentDirections.actionAuthFragmentToProductsGridFragment(),
            navOptions
        )
    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }
}