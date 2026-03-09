package com.example.cadenacustodiapp.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.FragmentGeneralesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class GeneralesFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentGeneralesBinding
    private val calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGeneralesBinding.inflate(inflater, container,false)

        binding.referencia.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar Número de Referencia")
            builder.setMessage(
                "Conforme a la Guía de llenado del Informe Policial Homologado el Número de Referencia se coloca\n" +
                        "Estado/Gobierno/Municipio/Fecha/Hora(de la puesta a disposición)\n" +
                        "Ejemplo: 15/PM/03/220889/2330"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.institucion.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar la Institución")
            builder.setMessage(
                "Se refiere a la institución a la cual pertenece el servidor público\n" +
                        "que atendió el evento, pudiendo ser: policías, Ministerios Publicos, peritos, etc."
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.folio.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar el Número de folio/llamado")
            builder.setMessage(
                "solicitara este folio a su centro de mando"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.lugInt.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Coloca el Lugar de Intervención completo")
            builder.setMessage(
                "Sitio en el que se ha cometido un hecho presuntamente \n" +
                        "delictivo, o en el que se localizan o aportan indicios relacionados con el mismo."
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.fecha.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Coloca la fecha de intervención")
            builder.setMessage(
                "Tienes que ver con la Fecha en que se llevo a cabo la intervención\n" +
                        "CON LOS INDICIOS"
            )

            builder.setPositiveButton("Colocar fecha"){_,_->
                showDatePicker()
            }


            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.hora.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Coloca la hora de intervención")
            builder.setMessage(
                "Tienes que ver con la Hora en que se llevo a cabo la intervención\n" +
                        "CON LOS INDICIOS"
            )

            builder.setPositiveButton("Colocar hora"){_,_->
                showTimePicker()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }



        return binding.root

    }

    private fun showDatePicker() {
        val datePickerDialog= DatePickerDialog(requireActivity(),{ DatePicker, year:Int,monthOfYear:Int,dayOfMonth:Int->
            val selectedDate= Calendar.getInstance()
            selectedDate.set(year,monthOfYear,dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formatteDate = dateFormat.format(selectedDate.time)
            binding.fechaEt.setText(formatteDate)
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()

    }



    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTitleText("Select Time")
            .build()

        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
            binding.horaEt.setText(selectedTime)
        }

        picker.show(requireActivity().supportFragmentManager, "time_picker")
    }


}