package com.example.myweather

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentSettings.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSettings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var myview: View
    lateinit var rbCelsius: RadioButton
    lateinit var rbFahrenheit: RadioButton
    lateinit var rbEnglish: RadioButton
    lateinit var rbPortuguese: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myview = inflater.inflate(R.layout.fragment_settings, container, false)

        rbCelsius = myview.findViewById(R.id.rb_celsius)
        rbFahrenheit = myview.findViewById(R.id.rb_fahrenheit)
        rbEnglish = myview.findViewById(R.id.rb_english)
        rbPortuguese = myview.findViewById(R.id.rb_portuguese)

        loadData()

        rbCelsius.setOnClickListener {
            saveData()
        }
        rbFahrenheit.setOnClickListener {
            saveData()
        }
        rbEnglish.setOnClickListener {
            saveData()
        }
        rbPortuguese.setOnClickListener {
            saveData()
        }

        return myview
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentSettings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentSettings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    // SharedPreferences
    private fun saveData() {
        val celsiusTempSelected = rbCelsius.isChecked()
        val englishLanguageSelected = rbEnglish.isChecked()

        val sharedPreferences = myview.context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.apply {
            putBoolean("CELSIUS SELECTED", celsiusTempSelected)
            putBoolean("ENGLISH SELECTED", englishLanguageSelected)
        }.apply()
    }

    // SharedPreferences
    private fun loadData() {
        val sharedPreferences = myview.context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val celsiusTempSelected = sharedPreferences.getBoolean("CELSIUS SELECTED", true)
        val englishLanguageSelected = sharedPreferences.getBoolean("ENGLISH SELECTED", true)

        if (celsiusTempSelected == true) {
            rbCelsius.setChecked(true)
        } else {
            rbFahrenheit.setChecked(true)
        }

        if (englishLanguageSelected == true) {
            rbEnglish.setChecked(true)
        } else {
            rbPortuguese.setChecked(true)
        }
    }

}