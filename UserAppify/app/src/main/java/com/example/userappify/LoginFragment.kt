package com.example.userappify

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.userappify.api.registerUser
import com.example.userappify.auth.AuthManager
import com.example.userappify.databinding.FragmentLoginBinding
import com.example.userappify.deconding_utils.getPemCertificate
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private fun areLoginDataValid(username: String, password: String): Boolean {
        val notEmptyStringRegex = Regex("([A-Z]+[a-z]+)") //Uppercase then lowercase
        val passwordRegex = Regex("^" +
                "(?=.*[@#$%^&+=])" +     // at least 1 special character
                "(?=\\S+$)" +            // no white spaces
                ".{4,}" +                // at least 4 characters
                "$")
        return username.matches(notEmptyStringRegex) and password.matches(passwordRegex)
    }

    private fun areLoginInformationCorrect(username: String, password: String): Boolean {
        val authManager = activity?.let { AuthManager(it) }
        val loginUser = authManager?.getLoginUser()
        return (loginUser?.username == username) and (loginUser?.password == password)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }

        val loginView = view

        binding.signIn.setOnClickListener {
            val username =
                loginView.findViewById<TextInputEditText>(R.id.login_username)?.text.toString()
            val password =
                loginView.findViewById<TextInputEditText>(R.id.login_password)?.text.toString()

            if (!areLoginDataValid(username, password)) {
                Snackbar.make(it, "Input not valid", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
                return@setOnClickListener
            }

            if (areLoginInformationCorrect(username, password)) {
                Snackbar.make(it, "Login successful", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
                val intent = Intent(this.context, AuthenticatedActivity::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(it, "Login information not valid", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}