package notboot.ferreira.edson.br.bancopanex.Telas

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import kotlinx.android.synthetic.main.info_games.*
import notboot.ferreira.edson.br.bancopanex.CacheBD.ManipulaBanco
import notboot.ferreira.edson.br.bancopanex.R





/**
 * Created by edson.ferreira on 24/01/2018.
 */
class InfoGames : AppCompatActivity() {

    var idGame : Int = 0

    val NomeBanco:String = "BancoPanEx"
    var Banco: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.info_games)
        Banco = openOrCreateDatabase(NomeBanco, Context.MODE_PRIVATE, null)


        var b = ManipulaBanco()
        val iin = intent
        val a = iin.getExtras()

        idGame = a.get("idGame") as Int

        var InfoGames : Cursor = b.SelectInfoJogo(idGame, (Banco as SQLiteDatabase?)!!)

        if(InfoGames.count > 0){

            InfoGames.moveToFirst()

            textView.setText("Nome do Jogo: " + InfoGames.getString(1))
            textView2.setText("Quantidade de Canais: " + InfoGames.getString(4))
            textView3.setText("Visualizações: " + InfoGames.getString(3))

            var decodedString: ByteArray? = Base64.decode(InfoGames.getString(2), Base64.DEFAULT)
            val myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString!!.size)

            if (myBitmap != null) {
                imageView.setImageBitmap(myBitmap)

            }

        }
    }

    fun getId(id : Int){

        idGame = id

    }
}