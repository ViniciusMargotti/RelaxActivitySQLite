package com.example.demodatabase

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.demodatabase.datasql.AppDbHelper
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SqlActivity : AppCompatActivity() {

    private lateinit var dbHelper: AppDbHelper;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sql)

        dbHelper = AppDbHelper(this)

        atualizaTela()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    fun atualizaTela() {
        val logView = findViewById<TextView>(R.id.log)
        val encontrados:List<String> = recuperaFolgas()
        if(encontrados.size > 0) {
            logView.setText(TextUtils.join("\n", encontrados))
        } else {
            logView.setText("Nenhuma folga analisada")
        }
    }

    fun clickBtnSave(view:View) {
        val textDia = findViewById<EditText>(R.id.textDia)
        val textMes = findViewById<EditText>(R.id.textMes)
        val textAno = findViewById<EditText>(R.id.textAno)

        if(textDia.text.isNullOrEmpty() && textMes.text.isNullOrEmpty() && textAno.text.isNullOrEmpty() ){
          return;
        }


        val dateStr: String = textDia.getText().toString() + "/" + textMes.getText() + "/" + textAno.getText()
        val data = Calendar.getInstance()
        val simpleFormat = SimpleDateFormat("dd/MM/yyyy")
        data.time = simpleFormat.parse(dateStr)

        val vaiFolgar = verificaData(data);

        val dia = textDia.text.toString()
        val mes = textMes.text.toString()
        val ano = textAno.text.toString()
        val vaifolgar = vaiFolgar;
        val novoId = guardaFolga(vaiFolgar,dia, mes,ano)

        textDia.setText("")
        textMes.setText("")
        textAno.setText("")
        atualizaTela()
    }

    fun  verificaData(data: Calendar): Int {
        // se for domingo
        if (data.get(Calendar.DAY_OF_WEEK) === Calendar.SUNDAY) {
            return 1;
        } else if (data.get(Calendar.DAY_OF_WEEK) === Calendar.SATURDAY) {
            return 1;
        } else {
            return 0;
        }
    }

    fun recuperaFolgas():List<String> {
        val db = dbHelper.readableDatabase

        val campos = arrayOf("id", "dia", "mes","ano","vaifolgar")
        val ordem = "id DESC"

        // montando a query e criando um Cursor para acesso dos registros
        val cursor = db.query(
            "folgas",       // nome da tabela a ser consultada
            campos,           // Campos a serem selecionados, passar null para pegar todos
            null,         // Parte "WHERE" desssa query de SELECT
            null,  // Argumentos dinâmicos para a montagem do WHERE
            null,             // GROUP BY - se precisar
            null,             // HAVING - se precisar
            ordem             // Ordenação dos resultados (ORDER BY)
        )

        var folgas:ArrayList<String> = ArrayList<String>()

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndex("id"));
                val dia = getString(getColumnIndex("dia"));
                val mes = getString(getColumnIndex("mes"));
                val ano = getString(getColumnIndex("ano"));
                var vaifolgar = "";
                if(getInt(getColumnIndex("vaifolgar")) == 1){
                    vaifolgar = "você vai folgar  😍 \n";
                }else{
                    vaifolgar = "você não irá volgar  😢 \n";
                }
                folgas.add("No dia $dia/$mes/$ano $vaifolgar ")
            }
        }
        cursor.close()

        return folgas
    }

    fun guardaFolga(vaifolgar:Int, dia:String, mes:String, ano:String):Long? {
        val db = dbHelper.writableDatabase

        val dadosFolga = ContentValues().apply {
            put("dia", dia)
            put("mes", mes)
            put("ano", ano)
            put("vaifolgar", vaifolgar)
        }

        return db?.insert("folgas", null, dadosFolga)
    }

}