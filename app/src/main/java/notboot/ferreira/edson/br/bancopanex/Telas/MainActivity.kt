package notboot.ferreira.edson.br.bancopanex.Telas

import Permissoes.BuscaPermissoes
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import notboot.ferreira.edson.br.bancopanex.CacheBD.CriaBanco
import notboot.ferreira.edson.br.bancopanex.R
import notboot.ferreira.edson.br.bancopanex.Sincronização.SincronizaJogos


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {


    //Criando/ Abrindo o banco de dados para cache
    val NomeBanco:String = "BancoPanEx"
    var Banco: SQLiteDatabase? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Banco = openOrCreateDatabase(NomeBanco, Context.MODE_PRIVATE, null)

        setContentView(R.layout.activity_main)


        swGrid.setOnRefreshListener(this)




        val p = BuscaPermissoes()
        var main = MainActivity()
        p.setPermitions(applicationContext, main)




        //Atualiza Lista
        var c = CriaBanco()
        if(c.criaBanco(Banco)) {


            SincronizaJogos((Banco as SQLiteDatabase?)!!,gListaTopGames!!,applicationContext!!,swGrid!!).execute()


        }


    }


    override fun onRefresh() {
        var c = CriaBanco()
        if(c.criaBanco(Banco)) {

        SincronizaJogos(Banco!!,gListaTopGames,applicationContext,swGrid).execute()
        }

    }
}




