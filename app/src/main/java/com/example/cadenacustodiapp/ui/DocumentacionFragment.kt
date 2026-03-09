package com.example.cadenacustodiapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.FragmentDocumentacionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DocumentacionFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDocumentacionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentDocumentacionBinding.inflate(inflater,container,false)

        binding.escritoSi.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se documento el lugar de forma escrita")
            builder.setMessage(
                "Registro a través del cual, se establecen las generalidades del lugar (calle principal, número del domicilio, fachada, material, dimensiones y colindancias del lugar, entradas y salidas, etc.), se especifica el sitio exacto del suceso y los indicios localizados (posición y orientación), a través de elementos deductivos, completos, cronológicos y específicos."
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.fotograficoSi.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se documentaron los indicios de forma fotográfica")
            builder.setMessage(
                "Registro en el que se capta y muestra el estado original del lugar, ofreciendo registros tangibles y corroborativos de forma objetiva, imparcial y exacta, para la validez de los indicios. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.croquisSi.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se realizo croquis general y a detalle para documentar los indicios")
            builder.setMessage(
                "Es una representación gráfica, la cual proporciona una panorámica superior del lugar, se realiza a mano alzada, y contiene la orientación norte, la representación de los indicios o elementos materiales probatorios a través de simbología, y las medidas del lugar, así como de la localización de los indicios. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.otroSi.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se realizo otra técnica de documentación de indicios")
            builder.setMessage(
                " Si entre las opciones no se encuentra la documentación realizada, se coloca la “X” en la opción otro y se especifica la que se elaboró, ej. representación 3D "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        val tachar = resources.getStringArray(R.array.Tachar)

        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,tachar)

        listOf(
            binding.autocompleteescritoSi,
            binding.autocompleteescritoNo,
            binding.autocompletefotograficoSi,
            binding.autocompletefotograficoNo,
            binding.autocompletecroquisSi,
            binding.autocompletecroquisNo,
            binding.autocompleteotroSi,
            binding.autocompleteotroNo
        ).forEach {
            it.setAdapter(arrayAdapter)
        }

        return binding.root

    }


}