package notboot.ferreira.edson.br.bancopanex;

import android.test.UiThreadTest
import android.widget.GridView
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import notboot.ferreira.edson.br.bancopanex.Telas.MainActivity


@Suppress("DEPRECATION")
/**
 * Created by edson.ferreira on 23/01/2018.
 */
public class MainActivityTest {

    private var mainActivity: MainActivity? = null
    private var gridView:GridView? = null

    @Throws(Exception::class)


    protected fun setUp() {

        mainActivity = MainActivity()
        gridView = mainActivity!!.findViewById(R.id.gListaTopGames) as GridView

    }
    fun TesteTelaPrincipal(){

        assertNotNull("mainActivity nao encontrada", mainActivity)
        assertNotNull("grid vazia",gridView)

    }

    @UiThreadTest
    fun testButtonClick() {
        gridView!!.performClick()
        assertEquals("Vendo se a grid funciona", "grid")
    }

}