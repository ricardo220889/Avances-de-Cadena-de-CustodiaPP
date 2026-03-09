package com.example.cadenacustodiapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.FragmentIdentidadBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Locale


class IdentidadFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentIdentidadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentIdentidadBinding.inflate(inflater,container,false)

        binding.identificacion1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar número/letra de identificación del indicio.")
            builder.setMessage(
                "Se asigna una letra, número o combinación de ambos, al indicio obtenido en el lugar de intervención mediante la inspección realizada  en un hecho probable delictivo. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.descripcion1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar descripción detallada del indicio.")
            builder.setMessage(
                "En este apartado se realiza una descripción objetiva del indicio en la que se enlistan los rasgos generales, es importante dejar asentadas las particularidades que diferencien al indicio de otros. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.ubicacion1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar donde se encontró el indicio.")
            builder.setMessage(
                " Se debe establecer donde se obtuvo el indicio: en el lugar de intervención (sobre el piso, mesa, a un costado de un poste, debajo del cadáver etc.,); en el indiciado (bolsa derecha del pantalón, en su chamarra, zapato etc.)"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.horaRecoleccion1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar la hora de la recolección del indicio.")
            builder.setMessage(
                "Es en el momento en el que se hace la recolección (lugar de intervención) o el aseguramiento de los indicios."
            )
            builder.setPositiveButton("Colocar hora"){_,_->

                showTimePicker(binding.recoleccionET1)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.horaRecoleccion2.setStartIconOnClickListener{

            showTimePicker(binding.recoleccionET2)

        }

        binding.horaRecoleccion3.setStartIconOnClickListener{

            showTimePicker(binding.recoleccionET3)

        }

        binding.horaRecoleccion4.setStartIconOnClickListener{

            showTimePicker(binding.recoleccionET4)

        }

        binding.horaRecoleccion5.setStartIconOnClickListener{

            showTimePicker(binding.recoleccionET5)

        }

        val indicios = resources.getStringArray(R.array.Indicios)

        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,indicios)

        listOf(
            binding.identificacionET1,
            binding.identificacionET2,
            binding.identificacionET3,
            binding.identificacionET4,
            binding.identificacionET5
        ).forEach {
            it.setAdapter(arrayAdapter)
        }




        return binding.root

    }

    private fun showTimePicker(editText: TextInputEditText) {
        val calendar = Calendar.getInstance()

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTitleText("Select Time")
            .build()

        picker.addOnPositiveButtonClickListener {
            val time = String.format(
                Locale.getDefault(),
                "%02d:%02d",
                picker.hour,
                picker.minute
            )
            editText.setText(time)
        }

        picker.show(requireActivity().supportFragmentManager, "time_picker")
    }






}