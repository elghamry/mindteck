package com.test.mindteck.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.test.mindteck.R
import com.test.mindteck.adapter.ItemAdapter
import com.test.mindteck.adapter.ViewPagerAdapter
import com.test.mindteck.databinding.ActivityHomeBinding
import com.test.mindteck.fragment.CurrentPageBottomSheet
import com.test.mindteck.model.ItemModel
import com.test.mindteck.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    private lateinit var itemAdapter: ItemAdapter

    private lateinit var dots: Array<ImageView?>
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val viewPagerAdapter = ViewPagerAdapter(this, viewModel.images)
        binding.viewPager.adapter = viewPagerAdapter


        itemAdapter = ItemAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = itemAdapter

        // Observe changes in RecyclerView data
        viewModel.recyclerViewItems.observe(this, Observer { items ->
            itemAdapter.updateData(items)
        })
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterItems(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        dots = arrayOfNulls(viewModel.images.size)
        for (i in 0 until viewModel.images.size) {
            dots[i] = ImageView(this).apply {
                setImageResource(R.drawable.default_dot)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = 8 // Add space between dots
                }
            }
            binding.dotsLayout.addView(dots[i])
        }
        dots[0]?.setImageResource(R.drawable.selected_dot)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                for (i in 0 until viewModel.images.size) {
                    dots[i]?.setImageResource(R.drawable.default_dot)
                }
                dots[position]?.setImageResource(R.drawable.selected_dot)
                viewModel.onPageChanged(position)
                binding.edtSearch.text.clear()
            }
        })
        binding.fab.setOnClickListener {
            val currentData = viewModel.getCurrentPageArrayList()
            openBottomSheet(currentData)
        }
    }

    private fun openBottomSheet(currentData: List<ItemModel>) {
        val currentPageDialog: CurrentPageBottomSheet = CurrentPageBottomSheet.newInstance(
            this, currentData
        )
        currentPageDialog.show(
            supportFragmentManager, CurrentPageBottomSheet.TAG
        )
    }


}