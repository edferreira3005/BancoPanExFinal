package notboot.ferreira.edson.br.bancopanex.Telas

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.info_games.*
import notboot.ferreira.edson.br.bancopanex.CacheBD.ManipulaBanco
import notboot.ferreira.edson.br.bancopanex.R

/**
 * Created by edson.ferreira on 24/01/2018.
 */
class InfoGames : AppCompatActivity() {

    val NomeBanco:String = "BancoPanEx"
    var Banco: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.info_games)
        Banco = openOrCreateDatabase(NomeBanco, Context.MODE_PRIVATE, null)


        val b = ManipulaBanco()

        var idGame : Int = b.idt



        var InfoGames : Cursor = b.SelectInfoJogo(idGame, (Banco as SQLiteDatabase?)!!)

        if(InfoGames.count > 0){

            textView.setText("Nome do Jogo: " + InfoGames.getString(1))
            textView2.setText("Quantidade de Canais: " + InfoGames.getString(3))
            textView3.setText("Visualizações: " + InfoGames.getString(2))

        }
    }
}