package edu.ccsu.cs560.cs560_hw4

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    val initialBun = "bun1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        viewModel.buns.observe(this, Observer {
            updateSharedPreferences(it)
        })
        viewModel.chosenBun.value = initialBun

        importSharedPreferences();

        setContentView(R.layout.activity_main)


        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            // We are in portrait orientation
            // Load Detail fragment, i.e., replace listview fragment with detail fragment

            val fragmentTransaction = supportFragmentManager.beginTransaction();


            fragmentTransaction.replace(R.id.main_fragment, AnimalList())
                .addToBackStack("list view")
                .commit()
        } else {

            val fragmentTransaction = supportFragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.view_fragment, AnimalView())
                .replace(R.id.list_fragment, AnimalList())
                .addToBackStack(null)
                .commit()


        }
    }

    fun importSharedPreferences() {

        val gson = Gson()
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        val stringRatings= sharedPreferences.getString("ratings", "")

        val sType = object: TypeToken<HashMap<String, Int?>>() {}.type
        val ratings = gson.fromJson<HashMap<String, Int?>>(stringRatings, sType)
        viewModel.buns.value = ratings ?: HashMap<String, Int?>()
    }

    /**
     * Sends the new ratings variables to the list fragment to update the existing ratings
     */
    fun updateSharedPreferences(ratings: HashMap<String, Int?>) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val gson = Gson()
        val stringRatings = gson.toJson(ratings)
        sharedPreferences
            .edit()
            .clear()
            .putString("ratings", stringRatings)
            .apply()
    }
}

