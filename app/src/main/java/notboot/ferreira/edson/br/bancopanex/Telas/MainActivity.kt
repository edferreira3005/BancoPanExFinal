package notboot.ferreira.edson.br.bancopanex.Telas

import Permissoes.BuscaPermissoes
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import notboot.ferreira.edson.br.bancopanex.CacheBD.CriaBanco
import notboot.ferreira.edson.br.bancopanex.CacheBD.ManipulaBanco
import notboot.ferreira.edson.br.bancopanex.R


class MainActivity : AppCompatActivity() {

    //Criando/ Abrindo o banco de dados para cache
    val NomeBanco:String = "BancoPanEx"
    var Banco: SQLiteDatabase? = null
    val m = ManipulaBanco()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Banco = openOrCreateDatabase(NomeBanco, Context.MODE_PRIVATE, null)

        setContentView(R.layout.activity_main)


        activity_main.setOnRefreshListener()




        val p = BuscaPermissoes()
        var main = MainActivity()
        p.setPermitions(applicationContext, main)




        //Atualiza Lista
        var c = CriaBanco()
        if(c.criaBanco(Banco)) {

            m.InsereBanco((Banco as SQLiteDatabase?)!!,gListaTopGames,applicationContext)

        }


    }



}

private fun SwipeRefreshLayout.setOnRefreshListener() {

        Handler().postDelayed({
           // m.InsereBanco((Banco as SQLiteDatabase?)!!,gListaTopGames,applicationContext)
            activity_main.isRefreshing = false }, 5000)

}




