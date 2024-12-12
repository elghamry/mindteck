package com.test.mindteck.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.test.mindteck.R

class ViewPagerFragment : Fragment() {

    companion object {
        private const val ARG_IMAGE_RES_ID = "image_res_id"

        fun newInstance(imageResId: Int): ViewPagerFragment {
            val fragment = ViewPagerFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_IMAGE_RES_ID, imageResId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val imageView: ImageView = view.findViewById(R.id.imageView)
        val imageResId = arguments?.getInt(ARG_IMAGE_RES_ID) ?: 0
        imageView.setImageResource(imageResId)

        return view
    }
}