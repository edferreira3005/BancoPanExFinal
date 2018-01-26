package notboot.ferreira.edson.br.bancopanex.Sincronização

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import notboot.ferreira.edson.br.bancopanex.CacheBD.ManipulaBanco
import notboot.ferreira.edson.br.bancopanex.Customizacao.CursorImagemAdapter
import notboot.ferreira.edson.br.bancopanex.Telas.InfoGames




/**
 * Created by edson.ferreira on 26/01/2018.
 */

 class SincronizaJogos(internal var BancoDeDados: SQLiteDatabase, internal var g: ListView,
                              internal var c: Context, internal var sw : SwipeRefreshLayout) : AsyncTask<Void, Int, Void>() {

    var Atualizou : Boolean = false
    override fun doInBackground(vararg param: Void): Void? {

           var bd = ManipulaBanco()

        Atualizou = bd.InsereBanco(BancoDeDados,c)

        return null
    }

    override fun onPostExecute(aVoid: Void?) {
        super.onPostExecute(aVoid)

        val cursor: Cursor = BancoDeDados.rawQuery("SELECT " +
                "                            A._id," +
               "                            NOME_JOGO, " +
                "                            IMG_JOGO " +
                "                        FROM " +
                "                           JOGO_TOP A" +
                "                        ORDER " +
                "                            BY A._id", null)

        if (!Atualizou){

            Toast.makeText(c, "Falha na Internet",
                    Toast.LENGTH_SHORT).show()
        }

        if (cursor.count > 0) {


           var AdapterImagem = CursorImagemAdapter(c, cursor)

            g.adapter = AdapterImagem


            g.onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(adapter: AdapterView<*>, v: View, position: Int,
                                         arg3: Long) {

                    var m = InfoGames()
                    m.getId(cursor.getInt(0))
                    val intent = Intent(c, InfoGames::class.java)
                    intent.putExtra("idGame", cursor.getInt(0)) //where v is button that is cliked, you will find it as a parameter to onClick method
                    c.startActivity(intent)

                }
            };

        }
        sw.isRefreshing = false

    }

    override fun onPreExecute() {

        sw.isRefreshing = true

    }


}
