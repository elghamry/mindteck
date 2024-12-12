package com.test.mindteck.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.mindteck.R
import com.test.mindteck.databinding.CurrentPageBottomSheetBinding
import com.test.mindteck.model.ItemModel

class CurrentPageBottomSheet(
    val activity: Activity,
    val currentData: List<ItemModel>,
) : BottomSheetDialogFragment() {
    private lateinit var mcontext: Context


    companion object {
        const val TAG = "BottomSheetDialog"
        fun newInstance(
            activity: Activity,
            currentData: List<ItemModel>,
        ): CurrentPageBottomSheet {
            return CurrentPageBottomSheet(
                activity, currentData
            )
        }
    }


    private var _binding: CurrentPageBottomSheetBinding? = null

    private val binding get() = _binding!!
    var itemNameArray: ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CurrentPageBottomSheetBinding.inflate(inflater, container, false)

        binding.root.setBackgroundResource(R.drawable.rounded_dialog_top_left_right_20)

        binding.txtPageData.text = "CurrentPage Item size : " +currentData.size.toString() + " Items"

        for (data in currentData) {
            itemNameArray.add(data.name)
        }
        binding.txtItems.text = itemNameArray.joinToString(", ")
        topThreeOccurrences(itemNameArray)
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogThemeNoFloating
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheetDialog = dialog as BottomSheetDialog
        val behavior = bottomSheetDialog.behavior
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.skipCollapsed = true
        val displayMetrics = resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val maxHeight = (screenHeight * 0.4).toInt()
        behavior.maxHeight = maxHeight
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context

    }

    override fun onDetach() {
        super.onDetach()
    }
    private fun topThreeOccurrences(words: List<String>) {
        val topThree = words
            .flatMap { it.toList() }
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .take(3)
            .joinToString(", ") { "${it.key} = ${it.value}" }

        binding.txtTop3occurrence.text = topThree
    }


}