package com.konovalovea.expsampling.screens.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.repository.PreferenceService
import com.konovalovea.expsampling.screens.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity(R.layout.activity_auth) {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        helpButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(HelpBottomSheet.newInstance(Bundle()), "bottom_sheet")
                .commit()
        }

        signInButton.setOnClickListener {
            viewModel.onSignInButtonClick(codeEditText.text.toString())
        }

        viewModel.authResult.observe(this, Observer { authResult ->
            if (authResult != null && authResult.isSuccess) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else if (authResult != null && !authResult.isSuccess) {
                Toast.makeText(this, getString(R.string.failed_to_sign_in), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }


}