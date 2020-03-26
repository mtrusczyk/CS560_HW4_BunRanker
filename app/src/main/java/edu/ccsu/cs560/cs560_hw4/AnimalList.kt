package edu.ccsu.cs560.cs560_hw4


import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_animal_list.*
import kotlinx.android.synthetic.main.fragment_animal_list.view.*

/**
 * A simple [Fragment] subclass.
 */
class AnimalList : Fragment() {

    val TAG = "AnimalList"

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_animal_list, container, false)

        view.bun1.setOnClickListener() {
            Log.d(TAG, "Bun1 Clicked")
            sendToDetailsFragment("bun1")
        }
        view.bun2.setOnClickListener() {
            Log.d(TAG, "Bun2 Clicked")
            sendToDetailsFragment("bun2")
        }
        view.bun3.setOnClickListener() {
            Log.d(TAG, "Bun3 Clicked")
            sendToDetailsFragment("bun3")
        }
        view.bun4.setOnClickListener() {
            Log.d(TAG, "Bun4 Clicked")
            sendToDetailsFragment("bun4")
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        viewModel.buns.observe(requireActivity(), Observer {
            updateRatings(it)
        })

    }

    fun updateRatings(ratings: HashMap<String, Int?>) {
        setRating(bun1Rating, ratings["bun1"])
        setRating(bun2Rating, ratings["bun2"])
        setRating(bun3Rating, ratings["bun3"])
        setRating(bun4Rating, ratings["bun4"])
    }

    fun setRating(textBox: TextView?, rating: Int?){
        if (textBox != null) {
            if (rating != null) {
                textBox?.text = "Your Rating: ${rating ?: 0}"
            } else {
                textBox?.text = "Click to set rating"
            }
        }
    }

    fun sendToDetailsFragment(bunClicked: String) {

        viewModel.chosenBun.value = bunClicked

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            // We are in portrait orientation
            // Load Detail fragment, i.e., replace listview fragment with detail fragment

            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction();


                fragmentTransaction.replace(R.id.main_fragment, AnimalView())
                    .addToBackStack("list view")
                    .commit()
        }
    }
}
