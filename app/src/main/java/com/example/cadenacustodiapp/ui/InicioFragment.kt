package com.example.cadenacustodiapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.FragmentInicioBinding
import com.example.cadenacustodiapp.pdf.CreatePdf
import com.example.cadenacustodiapp.viewmodel.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class InicioFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentInicioBinding
    private lateinit var viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        viewModel= ViewModelProvider(activity).get(ViewModel::class.java)
        binding.btn2.setOnClickListener{
            saveAction()
            onDismiss()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentInicioBinding.inflate(inflater,container,false)

        binding.localizacionText.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se inicio la Cadena de Custodia por localización")
            builder.setMessage(
                "La localizacion le corresponde a los indicios que fueron hallados a simple vista, es decir, sin haber realizado una inspección más minuciosa"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.descubrimientotext.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se inicio la Cadena de Custodia por descubrimiento")
            builder.setMessage(
                "El descubrimiento se aplica cuando derivado de una inspección  de  personas,  vehículos, inmuebles,  entre  otros,  se  encuentre  un  indicio,  evidencia,  objeto, instrumento  o  producto  del hecho  delictivo. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.aportaciontext.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se inicio la Cadena de Custodia por descubrimiento")
            builder.setMessage(
                " Cuando un particular te hace la entrega de un indicio."
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        val tachar = resources.getStringArray(R.array.Tachar)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,tachar)
        binding.autocompletelocalizacion.setAdapter(arrayAdapter)
        binding.autocompleteDescubrimiento.setAdapter(arrayAdapter)
        binding.autocompleteAportacion.setAdapter(arrayAdapter)




        return binding.root
    }

    private fun saveAction(){

        viewModel.localizacionvm.value =binding.autocompletelocalizacion.text.toString()
        viewModel.descubrimientovm.value =binding.autocompleteDescubrimiento.text.toString()
        viewModel.aportacionvm.value =binding.autocompleteAportacion.text.toString()




    }

    private fun onDismiss(){

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("¿Haz llenado la información necesaria?")
        builder.setNegativeButton("No"){_,_->

        }
        builder.setPositiveButton("Sí"){_,_->
            binding.autocompletelocalizacion.setText("")
            binding.autocompleteDescubrimiento.setText("")
            binding.autocompleteAportacion.setText("")
            dismiss()

            val fragment = CreatePdf()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.FragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }


}