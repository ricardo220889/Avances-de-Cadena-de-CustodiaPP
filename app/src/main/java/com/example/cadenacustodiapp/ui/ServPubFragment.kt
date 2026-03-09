package com.example.cadenacustodiapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.FragmentServPubBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ServPubFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentServPubBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentServPubBinding.inflate(inflater, container,false)

        binding.nombreComp1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar Nombre Completo")
            builder.setMessage(
                "Nombre completo del personal interviniente en el lugar y que tiene contacto con los indicios "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.institucionComp1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar Institución completa")
            builder.setMessage(
                "Institución del personal interviniente en el lugar y que tiene contacto con los indicios, por ejemplo: Comisaría de Seguridad Pública y Tránsito Municipal de Coacalco"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.cargoComp1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar cargo completo")
            builder.setMessage(
                "Cargo del personal interviniente en el lugar y que tiene contacto con los indicios, por ejemplo: Policía "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.etapaComp1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar la etapa(s) del procesamiento en la que participo")
            builder.setMessage(
                "SOLO COLOQUE LA ETAPA(S) EN LA QUE PARTICIPO (puede ser una o varias): Observación / Identificación / Documentación / Recolección / Embalaje / Sellado / Etiquetado / Inventario y recomendaciones para el traslado "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        val etapas = resources.getStringArray(R.array.Etapas)

        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,etapas)

        listOf(
            binding.etpEt1,
            binding.etpEt2,
            binding.etpEt3,
            binding.etpEt4,
            binding.etpEt5,
            binding.etpEt6,
            binding.etpEt7
        ).forEach {
            it.setAdapter(arrayAdapter)
        }

        return binding.root

    }


}