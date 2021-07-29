package com.example.foodapplication.fragments

import android.app.KeyguardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.foodapplication.*
import com.example.foodapplication.userDatabase.DatabaseHandler
import com.example.foodapplication.userDatabase.User
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment(),TextWatcher {

    private lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false
    private lateinit var cancellationSignal: CancellationSignal
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback = @RequiresApi(Build.VERSION_CODES.P)
    object : BiometricPrompt.AuthenticationCallback(){
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
            super.onAuthenticationError(errorCode, errString)
            notifyUser("Authentication error $errString")
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
            val userEmail = checkNotNull(sharedPreferences.getString("userEmail","email"))
            val isBiometricsEnabled = sharedPreferences.getBoolean("enabled",false)
            val dbHandler=DatabaseHandler(requireContext())
            val user = User(userEmail,isBiometricsEnabled)
            if(isBiometricsEnabled && dbHandler.userExists(user)){
                super.onAuthenticationSucceeded(result)
                notifyUser("Authentication success")
                val user = User()
                gotoInputFrag(user)
            }
            else{
                notifyUser("This account has not enabled biometrics")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(
        R.layout.fragment_login, container, false)

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.loginEmail)
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.loginPassword)
        val fingerPrint = root.findViewById<TextView>(R.id.useFingerPrint)
        val email = textInputEmail.editText
        val password = textInputPassword.editText
        email?.addTextChangedListener(this)
        password?.addTextChangedListener(this)

        checkBiometricSupport()
        onClickLogin()

        sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)
        if (isRemembered){
            val user = User()
            gotoInputFrag(user)
        }
        fingerPrint.setOnClickListener {
            val biometricPrompt = BiometricPrompt.Builder(requireContext())
            .setTitle("Fingerprint Authentication")
            .setSubtitle("Authentication is required")
            .setDescription("This app uses biometrics to keep your data secure")
            .setNegativeButton("Cancel",requireActivity().mainExecutor,
                { _, _ ->
                notifyUser("Authentication canceled")
            })
            .build()
            biometricPrompt.authenticate(getCancellationSignal(), requireActivity().mainExecutor,authenticationCallback)
        }
    }

    private fun onClickLogin(){

        val root = requireView()
        val textRegister = root.findViewById<TextView>(R.id.txtReg)
        val loginBtn = root.findViewById<Button>(R.id.loginBtn)
        val forgotPassword = root.findViewById<TextView>(R.id.forgotPassword)

        textRegister.setOnClickListener{
            gotoRegistration()
        }

        loginBtn.setOnClickListener{
            confirmInputAndGotoFoodInputScreen()
        }

        forgotPassword.setOnClickListener{
            gotoResetPasswordFragment()
        }
    }

    private fun validateEmail():Boolean{
        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.loginEmail)
        val email = textInputEmail.editText?.text.toString().trim()

        return if(email.isEmpty()){
            textInputEmail.error = "Field cannot be empty"
            false
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputEmail.error = "Email is not in the correct format"
            false
        } else{
            textInputEmail.error = null
            true
        }
    }

    private fun validatePassword():Boolean{

        val root = requireView()
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.loginPassword)
        val password = textInputPassword.editText?.text.toString().trim()

        return if(password.isEmpty()){
            textInputPassword.error = "Field cannot be empty"
            false
        }else{
            textInputPassword.error = null
            true
        }
    }

    private fun confirmInputAndGotoFoodInputScreen(){

        val root = requireView()
        val cbRememberMe = root.findViewById<CheckBox>(R.id.rememberMeCB)
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.loginEmail)
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.loginPassword)
        val email = textInputEmail.editText?.text.toString().trim()
        val password = textInputPassword.editText?.text.toString().trim()

        if(!validateEmail() or !validatePassword()){
            return
        }
        else{
            val dbHandler = DatabaseHandler(requireContext())
            val user = User(email, password)
            if(dbHandler.checkUserDetails(user)){
                val checked: Boolean = cbRememberMe.isChecked
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("CHECKBOX", checked)
                editor.putString("userEmail",email)
                editor.commit()
                gotoInputFrag(user)
            }
            else{
                Toast.makeText(requireContext(),"incorrect email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoInputFrag(user: User){

        val inputFragment = InputFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("userEmail", user.email)
        inputFragment.arguments = bundle
        transaction.replace(R.id.mainLayout, inputFragment)
        transaction.commit()
    }

    private fun gotoRegistration(){

        val registerFragment = RegisterFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, registerFragment)
        transaction.commit()
    }

    private fun gotoResetPasswordFragment(){

        val resetPasswordFragment = ResetPasswordFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, resetPasswordFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        checkIfTextHasChanged()
    }

    override fun afterTextChanged(s: Editable?) {
    }

    private fun checkIfTextHasChanged(){

        val root = requireView()
        val textInputEmail = root.findViewById<TextInputLayout>(R.id.loginEmail)
        val textInputPassword = root.findViewById<TextInputLayout>(R.id.loginPassword)
        val email = textInputEmail.editText?.text.toString().trim()
        val password = textInputPassword.editText?.text.toString().trim()

        if(textInputEmail.error == "Field cannot be empty" && email.isNotEmpty()){
            textInputEmail.error = null
        }
        else if(textInputEmail.error == "Email is not in the correct format" && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputEmail.error = null
        }
        else if(textInputPassword.error == "Field cannot be empty" && password.isNotEmpty()){
            textInputPassword.error = null
        }
    }

    private fun notifyUser(message:String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getCancellationSignal():CancellationSignal{
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was canceled")
        }
        return cancellationSignal
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport():Boolean{
        val keyguardManager = activity?.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if(!keyguardManager.isKeyguardSecure){
            notifyUser("Fingerprint authentication has not been enabled in settings")
            return false
        }
        if(ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.USE_BIOMETRIC)
            != PackageManager.PERMISSION_GRANTED){
            return false
        }
        return requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)

        return true
    }
}