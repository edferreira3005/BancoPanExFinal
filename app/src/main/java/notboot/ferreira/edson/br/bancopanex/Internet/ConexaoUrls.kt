package notboot.ferreira.edson.br.bancopanex.Internet

import android.os.StrictMode
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.methods.HttpGet
import org.apache.http.conn.params.ConnManagerParams
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.params.BasicHttpParams
import org.apache.http.params.HttpConnectionParams
import java.io.IOException


class ConexaoUrls {


    // private String[] params;

    fun chamadaGet(url: String): String {

        val httpParameters = BasicHttpParams()

        ConnManagerParams.setTimeout(httpParameters, CONN_MGR_TIMEOUT)
        HttpConnectionParams.setConnectionTimeout(httpParameters, CONN_TIMEOUT)
        HttpConnectionParams.setSoTimeout(httpParameters, SO_TIMEOUT)


        val httpclient = DefaultHttpClient(httpParameters)

        val chamadaget = HttpGet(url)
        // Log.i("enviado", url);
        var retorno = ""
        //Instantiate a GET HTTP method
        try {


            //Aqui o ideal é colocar a requesição assíncrona
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

            StrictMode.setThreadPolicy(policy)


            val responseHandler = BasicResponseHandler()
            val responseBody = httpclient.execute(chamadaget,
                    responseHandler)
            //  Log.i("retorno", "foi");


            retorno = responseBody

            return retorno


        } catch (e: ClientProtocolException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            // Log.i("erro", e.toString());
            retorno = ""
            return retorno

        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            // Log.i("erro", e.toString());
            retorno = ""
            return retorno
        } catch (t: Throwable) {
            // Log.i("erro", t.toString());
            retorno = ""
            return retorno
        }


    }

    companion object {
        private val CONN_MGR_TIMEOUT: Long = 30000
        private val CONN_TIMEOUT = 30000
        private val SO_TIMEOUT = 30000
    }
}
