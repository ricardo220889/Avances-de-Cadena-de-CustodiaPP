package com.example.cadenacustodiapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.FragmentTrasladoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TrasladoFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTrasladoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentTrasladoBinding.inflate(inflater,container,false)

        binding.terrestre.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si los indicios se trasladaron vía terrestre")
            builder.setMessage(
                "Debe colocar una X si los indicios se trasldaron vía terrestre, por ejemplo: en un vehículo  policial"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }


        binding.aerea.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si los indicios se trasladaron vía aérea")
            builder.setMessage(
                "Debe colocar una X si los indicios se trasldaron vía aérea, por ejemplo: en un helicoptero "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }


        binding.maritima.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si los indicios se trasladaron vía marítima")
            builder.setMessage(
                "Debe colocar una X si los indicios se trasldaron vía marítima, por ejemplo: en una moto acuática"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.condicionesSi.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se necestaron condiciones especiales para trasladar los indicios")
            builder.setMessage(
                "Se deben especificar las recomendaciones a considerar, por ejemplo: manéjese con precaución objeto frágil, liquido o sustancia altamente explosiva o toxica etc."
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        val tachar = resources.getStringArray(R.array.Tachar)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,tachar)

        listOf(
            binding.autocompleteterrestre,
            binding.autocompleteaerea,
            binding.autocompletemaritima,
            binding.autocompletecondicionesSi,
            binding.autocompletecondicionesNo
        ).forEach {
            it.setAdapter(arrayAdapter)
        }

        return binding.root
    }


}