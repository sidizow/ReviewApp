package com.example.reviewapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<ViewBinding : androidx.viewbinding.ViewBinding>(
    @LayoutRes layoutId: Int,
    private val inflate: Inflate<ViewBinding>,
) : Fragment(layoutId) {

    private var _binding: ViewBinding? = null
    val binding get() = _binding!!

    abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}