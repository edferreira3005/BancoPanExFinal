package Permissoes

/**
 * Created by edson.ferreira on 24/01/2018.
 */
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat

class BuscaPermissoes {

    internal var PERMISSOES = arrayOf(Manifest.permission.INTERNET)
    internal var PERMITIR_TODOS = 1

    fun setPermitions(context: Context, main: Activity) {

        if (!temPermissao(context, *PERMISSOES)) {
            ActivityCompat.requestPermissions(main, PERMISSOES, PERMITIR_TODOS)
        }

    }

    companion object {

        fun temPermissao(context: Context?, vararg permissions: String): Boolean {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false
                    }
                }
            }
            return true
        }
    }

}
