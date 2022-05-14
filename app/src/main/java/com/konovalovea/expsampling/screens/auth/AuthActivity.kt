package com.konovalovea.expsampling.screens.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.konovalovea.expsampling.R
import com.konovalovea.expsampling.app.appComponent
import com.konovalovea.expsampling.screens.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : AppCompatActivity(R.layout.activity_auth) {

    @Inject
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
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