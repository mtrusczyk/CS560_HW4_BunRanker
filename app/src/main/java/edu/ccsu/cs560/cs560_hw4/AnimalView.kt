package edu.ccsu.cs560.cs560_hw4


import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_animal_view.view.*

/**
 * A simple [Fragment] subclass.
 */
class AnimalView : Fragment() {
    // Create an instance of our ViewModel
    lateinit var viewModel: MainViewModel
    lateinit var bunRatings: HashMap<String, Int?>
    var bun = "bun1"
    val TAG = "AnimalView"
    var ranking = 0
    val goldStar = R.drawable.ic_star_gold_24dp
    val greyStar = R.drawable.ic_star_grey_24dp

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_animal_view, container, false)

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        viewModel.chosenBun.observe(requireActivity(), Observer {
            bun = it
            setBun()
        })

        view.star1.setOnClickListener {
            updateRanking(1)
        }
        view.star2.setOnClickListener {
            updateRanking(2)
        }
        view.star3.setOnClickListener {
            updateRanking(3)
        }
        view.star4.setOnClickListener {
            updateRanking(4)
        }
        view.star5.setOnClickListener {
            updateRanking(5)
        }

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            view.submit_button.setOnClickListener {
                    // We are in portrait orientation
                    // Load Detail fragment, i.e., replace listview fragment with detail fragment

                    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction();


                    fragmentTransaction.replace(R.id.main_fragment, AnimalList())
                        .addToBackStack("list view")
                        .commit()
                    bunRatings.set(bun, ranking)
                    viewModel.buns.value = bunRatings
            }
        } else {
            view.submit_button.visibility = View.INVISIBLE
        }



        return view
    }

    override fun onStart() {
        super.onStart()
        bunRatings = viewModel.buns.value ?: HashMap<String, Int?>()
        setBun()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("bun", bun)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val restoredState = savedInstanceState?.getString("bun")
        if (!restoredState.isNullOrEmpty()) {
            viewModel.chosenBun.value = restoredState
            updateRanking(bunRatings?.get(restoredState) ?: 0)
        }

    }

    fun setBun() {
        val imageId = when (bun) {
            "bun1" -> R.drawable.bun1
            "bun2" -> R.drawable.bun2
            "bun3" -> R.drawable.bun3
            else -> R.drawable.bun4
        }

        view?.large_bun?.setImageResource(imageId)
        updateRanking(bunRatings.get(bun)?: 0)
    }

    fun updateRanking(star: Int) {
        ranking = star
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bunRatings.set(bun, ranking)
            viewModel.buns.value = bunRatings
        }

        when (star) {
            1 -> {
                view?.star1?.setImageResource(goldStar)
                view?.star2?.setImageResource(greyStar)
                view?.star3?.setImageResource(greyStar)
                view?.star4?.setImageResource(greyStar)
                view?.star5?.setImageResource(greyStar)
            }
            2 -> {
                view?.star1?.setImageResource(goldStar)
                view?.star2?.setImageResource(goldStar)
                view?.star3?.setImageResource(greyStar)
                view?.star4?.setImageResource(greyStar)
                view?.star5?.setImageResource(greyStar)
            }
            3 -> {
                view?.star1?.setImageResource(goldStar)
                view?.star2?.setImageResource(goldStar)
                view?.star3?.setImageResource(goldStar)
                view?.star4?.setImageResource(greyStar)
                view?.star5?.setImageResource(greyStar)
            }
            4 -> {
                view?.star1?.setImageResource(goldStar)
                view?.star2?.setImageResource(goldStar)
                view?.star3?.setImageResource(goldStar)
                view?.star4?.setImageResource(goldStar)
                view?.star5?.setImageResource(greyStar)
            }
            5 -> {
                view?.star1?.setImageResource(goldStar)
                view?.star2?.setImageResource(goldStar)
                view?.star3?.setImageResource(goldStar)
                view?.star4?.setImageResource(goldStar)
                view?.star5?.setImageResource(goldStar)
            }
            else -> {
                Log.d(TAG, "No Ranking Found")
                view?.star1?.setImageResource(greyStar)
                view?.star2?.setImageResource(greyStar)
                view?.star3?.setImageResource(greyStar)
                view?.star4?.setImageResource(greyStar)
                view?.star5?.setImageResource(greyStar)
            }
        }
    }

}
