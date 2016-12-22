package im.kitillt.yandexmoneyclient

import org.jetbrains.anko.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            padding = dip(30)
            val name = editText {
                hint = "Name"
                textSize = 24f
            }
            val password = editText {
                hint = "Password"
                textSize = 24f
            }
            button("Login") {
                textSize = 26f
            }.onClick { longToast("Login, ${name.text} with ${password.text}") }
        }
    }
}
