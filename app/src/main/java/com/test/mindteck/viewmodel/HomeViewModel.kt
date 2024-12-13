package com.test.mindteck.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.test.mindteck.R
import com.test.mindteck.model.ItemModel

class HomeViewModel: ViewModel() {

    private var _recyclerViewItems = emptyList<ItemModel>()
    val recyclerViewItems: MutableState<List<ItemModel>> = mutableStateOf( _recyclerViewItems)
    private var _itemNameArray: ArrayList<String> = ArrayList<String>()
    @SuppressLint("MutableCollectionMutableState")
    val itemNameArray : MutableState<ArrayList<String>> = mutableStateOf(_itemNameArray)


    val _images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5
    )

    val images = mutableStateOf(_images)
    // Predefined data for ViewPager pages
    private val dataForPages = listOf(
        listOf(ItemModel("Apple", R.drawable.image1), ItemModel("Banana", R.drawable.image1), ItemModel("Orange", R.drawable.image1),ItemModel("Blueberry", R.drawable.image1),ItemModel("Apricot", R.drawable.image1),ItemModel("Avocado", R.drawable.image1),ItemModel("Peach", R.drawable.image1),ItemModel("BlackBerry", R.drawable.image1),ItemModel("Mango", R.drawable.image1),ItemModel("Guava", R.drawable.image1)),
        listOf(ItemModel("Tomato", R.drawable.image2), ItemModel("Bottle Gourd", R.drawable.image2), ItemModel("Potato", R.drawable.image2), ItemModel("Pea", R.drawable.image2), ItemModel("Green Chili", R.drawable.image2), ItemModel("Cucumber", R.drawable.image2), ItemModel("Onion", R.drawable.image2), ItemModel("Cauliflower", R.drawable.image2), ItemModel("Bean", R.drawable.image2), ItemModel("Capsicum", R.drawable.image2), ItemModel("Ginger", R.drawable.image2)),
        listOf(ItemModel("India", R.drawable.image3), ItemModel("Azerbaijan", R.drawable.image3), ItemModel("Bahrain", R.drawable.image3), ItemModel("Bangladesh", R.drawable.image3), ItemModel("Barbados", R.drawable.image3), ItemModel("Belarus", R.drawable.image3), ItemModel("Andorra", R.drawable.image3), ItemModel("Angola", R.drawable.image3), ItemModel("Antigua", R.drawable.image3), ItemModel("Afghanistan", R.drawable.image3), ItemModel("Australia", R.drawable.image3), ItemModel("Nepal", R.drawable.image3)),
        listOf(ItemModel("Dolphin", R.drawable.image4), ItemModel("Eagle", R.drawable.image4), ItemModel("Goat", R.drawable.image4), ItemModel("Cow", R.drawable.image4), ItemModel("Albatross", R.drawable.image4), ItemModel("Frog", R.drawable.image4), ItemModel("Jackal", R.drawable.image4), ItemModel("Horse", R.drawable.image4), ItemModel("Fish", R.drawable.image4), ItemModel("Alligator", R.drawable.image4), ItemModel("Fox", R.drawable.image4), ItemModel("Armadillor", R.drawable.image4), ItemModel("Dog", R.drawable.image4), ItemModel("Pigeon", R.drawable.image4)),
        listOf(ItemModel("Rose", R.drawable.image5), ItemModel("Sunflower", R.drawable.image5), ItemModel("Lily", R.drawable.image5), ItemModel("Hibiscus", R.drawable.image5), ItemModel("Peony", R.drawable.image5), ItemModel("Dahlia", R.drawable.image5), ItemModel("Cosmos", R.drawable.image5), ItemModel("Plumeria", R.drawable.image5), ItemModel("Feverfew", R.drawable.image5), ItemModel("Orchid", R.drawable.image5), ItemModel("Elderberry", R.drawable.image5), ItemModel("Lotus", R.drawable.image5), ItemModel("Carnation", R.drawable.image5))
    )
    private var currentPageItems = dataForPages[0]

    init {
        recyclerViewItems.value = currentPageItems
        getNames()
    }

    private fun getNames()
    {
        for (data in recyclerViewItems.value ) {
            _itemNameArray.add(data.name)
        }
    }

}