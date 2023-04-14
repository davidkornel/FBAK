package com.example.userappify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.userappify.api.registerUser
import com.example.userappify.auth.AuthManager
import com.example.userappify.databinding.FragmentRegisterBinding
import com.example.userappify.deconding_utils.*
import com.example.userappify.model.Card
import com.google.android.material.snackbar.Snackbar
import com.example.userappify.model.RegistrationUser
import com.example.userappify.model.User
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun isDateStringValid(dateString: String): Boolean {
        val dateRegex = Regex("(\\d\\d/\\d\\d)")
        return dateString.matches(dateRegex)
    }

    fun isRegistrationDataValid(data: RegistrationUser): Boolean {
        val notEmptyStringRegex = Regex("([A-Z]+[a-z]+)") //Uppercase then lowercase
        val passwordRegex = Regex("^" +
                "(?=.*[@#$%^&+=])" +     // at least 1 special character
                "(?=\\S+$)" +            // no white spaces
                ".{4,}" +                // at least 4 characters
                "$")
        val cvvRegex = Regex("(\\d\\d\\d)")
        val cardNumberRegex = Regex("(\\d{16})")
        val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.+)")
        return data.username.matches(notEmptyStringRegex) and data.name.matches(notEmptyStringRegex) and data.surname.matches(
            notEmptyStringRegex
        ) and data.email.matches(emailRegex) and (data.password?.matches(passwordRegex)
            ?: false) and data.card.number.matches(cardNumberRegex) and data.card.cvc.matches(
            cvvRegex
        )

    }

    private fun onResponse(response: JSONObject, registrationUser: RegistrationUser) {
        val userId = response.get("userId");
        val superMarketPublicKey = response.get("superMarketPublicKey");
        val user = User(
            UUID.fromString(userId.toString()),
            registrationUser.username,
            registrationUser.password,
            registrationUser.email,
            registrationUser.name,
            registrationUser.surname,
            registrationUser.publicKey,
            registrationUser.transactions,
            registrationUser.vouchers,
            registrationUser.card
        )
        val authManager = activity?.let { AuthManager(it) }
        authManager?.setLoginUser(user);
        authManager?.setBEPublicKey(superMarketPublicKey.toString());
        val intent = Intent(this.context, AuthenticatedActivity::class.java)
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
        }

        val registerView = view

        binding.registerSignUp.setOnClickListener {
            try {
                val username =
                    registerView.findViewById<TextInputEditText>(R.id.register_username)?.text.toString()
                val name =
                    registerView.findViewById<TextInputEditText>(R.id.register_name)?.text.toString()
                val surname =
                    registerView.findViewById<TextInputEditText>(R.id.register_surname)?.text.toString()
                val email =
                    registerView.findViewById<TextInputEditText>(R.id.register_email)?.text.toString()
                val password =
                    registerView.findViewById<TextInputEditText>(R.id.register_password)?.text.toString()
                val cardNumber =
                    registerView.findViewById<TextInputEditText>(R.id.register_card_number)?.text.toString()
                val cardExpirationDateString =
                    registerView.findViewById<TextInputEditText>(R.id.register_card_expiration_date)?.text.toString()
                val cardCsv =
                    registerView.findViewById<TextInputEditText>(R.id.register_card_csv)?.text.toString()

                var cardExpirationDate: Date?

                if (isDateStringValid(cardExpirationDateString)) {
                    val month = cardExpirationDateString.split('/')[0]
                    val year = "20" + cardExpirationDateString.split('/')[1]
                    cardExpirationDate = Date.from(
                        LocalDate.of(year.toInt(), month.toInt(), 1).atStartOfDay()
                            .atZone(ZoneId.of("UTC")).toInstant()
                    )
                } else {
                    Snackbar.make(view, "Expiration date is not correct", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
                    return@setOnClickListener
                }

                val registrationUser = RegistrationUser(
                    username, password, email, name, surname, "", ArrayList(), ArrayList(), Card(
                        UUID.randomUUID(), cardNumber, cardCsv, cardExpirationDate
                    )
                )

                if (!isRegistrationDataValid(registrationUser)) {
                    Snackbar.make(view, "Input not valid", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
                    return@setOnClickListener
                }

                // check if key exists
                if (!checkKeyExists()) {
                    //generateAndStoreKeys(requireContext())
                    generateKey()
                }


                val pemCertificate = getPemCertificate()
                Log.d("cert",pemCertificate)
                registrationUser.publicKey = pemCertificate

                this.context?.let { it1 ->
                    registerUser(registrationUser, it1, onResponse = this::onResponse, view)
                }

            } catch (e: Exception) {
                Snackbar.make(view, "Wrong input", Snackbar.LENGTH_SHORT).setAction("Action", null)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}