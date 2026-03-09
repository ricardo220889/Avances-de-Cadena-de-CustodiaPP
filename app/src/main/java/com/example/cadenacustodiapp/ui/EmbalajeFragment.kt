package com.example.cadenacustodiapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.FragmentEmbalajeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EmbalajeFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEmbalajeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmbalajeBinding.inflate(inflater,container,false)

        binding.bolsa.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar los indicios que embaló en bolsas")
            builder.setMessage(
                "Se debe colocar el número de identificación del indicio que se embaló en bolsas(plástico/papel), por ejemplo: elementos balísticos "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.caja.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar los indicios que embaló en cajas")
            builder.setMessage(
                "Se debe colocar el número de identificación del indicio que se embaló en cajas, por ejemplo: armas de fuego "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.recipiente.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar los indicios que embaló en recipientes")
            builder.setMessage(
                "Se debe colocar el número de identificación del indicio que se embaló en recipientes, por ejemplo: fluidos corporales"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        return binding.root

    }


}