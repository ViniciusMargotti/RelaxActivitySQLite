package com.example.demodatabase

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class SharedPrefsActivity : AppCompatActivity() {

    private lateinit var sharedPref:SharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_prefs)

        sharedPref = getSharedPreferences("com.example.demodatabase.PERFIL", Context.MODE_PRIVATE) ?: return

        atualizaTela()
    }

    fun atualizaTela() {
        val nomeAtualizado = recuperaNome()
        val emailAtualizado = recuperaEmail()
        if(nomeAtualizado != null && nomeAtualizado != "") {
            findViewById<TextView>(R.id.viewName).text = nomeAtualizado + " / " + emailAtualizado
        } else {
            findViewById<TextView>(R.id.viewName).text = getString(R.string.empty_name)
        }
    }

    fun clickBtnSave(view:View) {
        val textName = findViewById<EditText>(R.id.textName)
        val nameValue = textName.text.toString()

        val textEmail = findViewById<EditText>(R.id.textEmail)
        val emailValue = textEmail.text.toString()

        guardaNome(nameValue, emailValue)
        atualizaTela()
    }

    fun guardaNome(nome:String, email:String) {
        with(sharedPref.edit()) {
            putString("NOME_USUARIO", nome)
            putString("EMAIL_USUARIO", email)
            commit()
        }
    }

    fun recuperaNome() : String? {
        return sharedPref.getString("NOME_USUARIO", null)
    }

    fun recuperaEmail() : String? {
        return sharedPref.getString("EMAIL_USUARIO", null)
    }
}