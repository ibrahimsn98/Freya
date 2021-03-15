package me.ibrahimsn.freya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import me.ibrahimsn.lib.FreyaForm
import me.ibrahimsn.lib.api.rule.Ruler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val freya = findViewById<FreyaForm>(R.id.freya)
        val submit = findViewById<Button>(R.id.submit)

        /*
         * Reports is the whole form valid
         */
        freya.onValidationChangeListener = {
            Log.d(TAG, "Is form valid: $it")
        }

        /*
         * Reports on validation change of any form field
         */
        freya.onFieldValidationChangeListener = {
            when (it.error) {
                is Ruler.Required -> {
                    it.setError("This field is required.")
                }
                is Ruler.Email -> {
                    it.setError("Please enter a valid email address.")
                }
                is Ruler.PhoneNumber -> {
                    it.setError("Please enter a valid phone number.")
                }
                is Ruler.MinSize -> {
                    it.setError("This field must contain at least ${it.error?.param} characters.")
                }
                is Ruler.MaxSize -> {
                    it.setError("This field must contain at most ${it.error?.param} characters.")
                }
                is Ruler.Regex -> {
                    it.setError("Please provide a valid data.")
                }
            }
        }

        /*
         * Reports form validation errors after submit
         */
        freya.onErrorListener = {
            Log.d(TAG, "Validation errors: $it")
        }

        /*
         * Pre-fills form fields
         */
        freya.setup(
            mapOf(
                R.id.inputUsername to "Thomas"
            )
        )

        submit.setOnClickListener {
            Log.d(TAG, "Is form valid: ${freya.validate()} values: ${freya.values}")
        }
    }

    companion object {
        private const val TAG = "mainActivity"
    }
}
