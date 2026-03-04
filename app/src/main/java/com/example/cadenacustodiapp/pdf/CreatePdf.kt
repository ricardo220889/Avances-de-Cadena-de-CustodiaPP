package com.example.cadenacustodiapp.pdf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.FragmentCreatePdfBinding


class CreatePdf : Fragment() {

    private var _binding: FragmentCreatePdfBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePdfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}