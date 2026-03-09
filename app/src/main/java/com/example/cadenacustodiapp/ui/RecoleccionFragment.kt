package com.example.cadenacustodiapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.FragmentRecoleccionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RecoleccionFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentRecoleccionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecoleccionBinding.inflate(inflater,container,false)

        binding.manual.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar los indicios que recolectó con guantes")
            builder.setMessage(
                "Se debe colocar el número de identificación del indicio que se recolectó con las manos(con guantes) "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.instrumental.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar los indicios que recolectó con instrumentos")
            builder.setMessage(
                "Se debe colocar el número de identificación del indicio que se recolectó con algún instrumento, por ejemplo: pinzas plastica. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        return binding.root
    }


}