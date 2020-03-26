package edu.ccsu.cs560.cs560_hw4

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val buns = MutableLiveData<HashMap<String, Int?>>()
    val chosenBun = MutableLiveData<String>()
}