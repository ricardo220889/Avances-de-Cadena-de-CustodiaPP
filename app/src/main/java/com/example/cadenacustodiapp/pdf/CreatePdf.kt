package com.example.cadenacustodiapp.pdf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.databinding.FragmentCreatePdfBinding
import com.example.cadenacustodiapp.viewmodel.ViewModel


class CreatePdf : Fragment() {

    private var _binding: FragmentCreatePdfBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePdfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ViewModel::class.java]


        observarDatos()

        binding.btnCrearPdf.setOnClickListener {

            generarPdf()
        }

    }

    private fun observarDatos() {

        viewModel.referenciavm.observe(viewLifecycleOwner) {
            binding.tvreferencia.text = it ?: ""
        }

        viewModel.institucionvm.observe(viewLifecycleOwner) {
            binding.tvinstitucion.text = it ?: ""
        }

        viewModel.foliovm.observe(viewLifecycleOwner) {
            binding.tvfolio.text = it ?: ""
        }

        viewModel.lugarvm.observe(viewLifecycleOwner) {
            binding.tvLugInt.text = it ?: ""
        }

        viewModel.fechavm.observe(viewLifecycleOwner) {
            binding.tvfecha.text = it ?: ""
        }

        viewModel.horavm.observe(viewLifecycleOwner) {
            binding.tvhora.text = it ?: ""
        }

        viewModel.localizacionvm.observe(viewLifecycleOwner) {
            binding.tvlocalizacion.text = it ?: ""
        }

        viewModel.descubrimientovm.observe(viewLifecycleOwner) {
            binding.tvdescubrimiento.text = it ?: ""
        }

        viewModel.aportacionvm.observe(viewLifecycleOwner) {
            binding.tvaportacion.text = it ?: ""
        }

        viewModel.identificacionvm1.observe(viewLifecycleOwner) {
            binding.tvidentificacion1.text = it ?: ""
        }

        viewModel.descripcionvm1.observe(viewLifecycleOwner) {
            binding.tvdescripcion1.text = it ?: ""
        }

        viewModel.ubicacionvm1.observe(viewLifecycleOwner) {
            binding.tvubicacion1.text = it ?: ""
        }

        viewModel.recoleccionvm1.observe(viewLifecycleOwner) {
            binding.tvrecoleccion1.text = it ?: ""
        }

        viewModel.identificacionvm2.observe(viewLifecycleOwner) {
            binding.tvidentificacion2.text = it ?: ""
        }

        viewModel.descripcionvm2.observe(viewLifecycleOwner) {
            binding.tvdescripcion2.text = it ?: ""
        }

        viewModel.ubicacionvm2.observe(viewLifecycleOwner) {
            binding.tvubicacion2.text = it ?: ""
        }

        viewModel.recoleccionvm2.observe(viewLifecycleOwner) {
            binding.tvrecoleccion2.text = it ?: ""
        }

        viewModel.identificacionvm3.observe(viewLifecycleOwner) {
            binding.tvidentificacion3.text = it ?: ""
        }

        viewModel.descripcionvm3.observe(viewLifecycleOwner) {
            binding.tvdescripcion3.text = it ?: ""
        }

        viewModel.ubicacionvm3.observe(viewLifecycleOwner) {
            binding.tvubicacion3.text = it ?: ""
        }

        viewModel.recoleccionvm3.observe(viewLifecycleOwner) {
            binding.tvrecoleccion3.text = it ?: ""
        }

        viewModel.identificacionvm4.observe(viewLifecycleOwner) {
            binding.tvidentificacion4.text = it ?: ""
        }

        viewModel.descripcionvm4.observe(viewLifecycleOwner) {
            binding.tvdescripcion4.text = it ?: ""
        }

        viewModel.ubicacionvm4.observe(viewLifecycleOwner) {
            binding.tvubicacion4.text = it ?: ""
        }

        viewModel.recoleccionvm4.observe(viewLifecycleOwner) {
            binding.tvrecoleccion4.text = it ?: ""
        }

        viewModel.identificacionvm5.observe(viewLifecycleOwner) {
            binding.tvidentificacion5.text = it ?: ""
        }

        viewModel.descripcionvm5.observe(viewLifecycleOwner) {
            binding.tvdescripcion5.text = it ?: ""
        }

        viewModel.ubicacionvm5.observe(viewLifecycleOwner) {
            binding.tvubicacion5.text = it ?: ""
        }

        viewModel.recoleccionvm5.observe(viewLifecycleOwner) {
            binding.tvrecoleccion5.text = it ?: ""
        }

        viewModel.escritovmSi.observe(viewLifecycleOwner) {
            binding.tvescritoSi.text = it ?: ""
        }

        viewModel.escritovmNo.observe(viewLifecycleOwner) {
            binding.tvescritoNo.text = it ?: ""
        }

        viewModel.fotograficovmSi.observe(viewLifecycleOwner) {
            binding.tvfotograficoSi.text = it ?: ""
        }

        viewModel.fotograficovmNo.observe(viewLifecycleOwner) {
            binding.tvfotograficoNo.text = it ?: ""
        }

        viewModel.croquisvmSi.observe(viewLifecycleOwner) {
            binding.tvcroquisSi.text = it ?: ""
        }

        viewModel.croquisvmNo.observe(viewLifecycleOwner) {
            binding.tvcroquisNo.text = it ?: ""
        }

        viewModel.otrovmSi.observe(viewLifecycleOwner) {
            binding.tvOtroSi.text = it ?: ""
        }

        viewModel.otrovmNo.observe(viewLifecycleOwner) {
            binding.tvOtroNo.text = it ?: ""
        }

        viewModel.especifiquevm.observe(viewLifecycleOwner) {
            binding.tvespecifique.text = it ?: ""
        }

        viewModel.rmanualvm.observe(viewLifecycleOwner) {
            binding.tvrmanual.text = it ?: ""
        }

        viewModel.rinstrumentalvm.observe(viewLifecycleOwner) {
            binding.tvrinstrumental.text = it ?: ""
        }

        viewModel.bolsavm.observe(viewLifecycleOwner) {
            binding.tvbolsa.text = it ?: ""
        }

        viewModel.cajavm.observe(viewLifecycleOwner) {
            binding.tvcaja.text = it ?: ""
        }

        viewModel.recipientevm.observe(viewLifecycleOwner) {
            binding.tvrecipiente.text = it ?: ""
        }

        viewModel.servpubvm1.observe(viewLifecycleOwner) {
            binding.tvservpub1.text = it ?: ""
        }

        viewModel.servpubvm2.observe(viewLifecycleOwner) {
            binding.tvservpub2.text = it ?: ""
        }

        viewModel.servpubvm3.observe(viewLifecycleOwner) {
            binding.tvservpub3.text = it ?: ""
        }

        viewModel.servpubvm4.observe(viewLifecycleOwner) {
            binding.tvservpub4.text = it ?: ""
        }

        viewModel.servpubvm5.observe(viewLifecycleOwner) {
            binding.tvservpub5.text = it ?: ""
        }

        viewModel.servpubvm6.observe(viewLifecycleOwner) {
            binding.tvservpub6.text = it ?: ""
        }

        viewModel.servpubvm7.observe(viewLifecycleOwner) {
            binding.tvservpub7.text = it ?: ""
        }

        viewModel.instvm1.observe(viewLifecycleOwner) {
            binding.tvinst1.text = it ?: ""
        }

        viewModel.instvm2.observe(viewLifecycleOwner) {
            binding.tvinst2.text = it ?: ""
        }

        viewModel.instvm3.observe(viewLifecycleOwner) {
            binding.tvinst3.text = it ?: ""
        }

        viewModel.instvm4.observe(viewLifecycleOwner) {
            binding.tvinst4.text = it ?: ""
        }

        viewModel.instvm5.observe(viewLifecycleOwner) {
            binding.tvinst5.text = it ?: ""
        }

        viewModel.instvm6.observe(viewLifecycleOwner) {
            binding.tvinst6.text = it ?: ""
        }

        viewModel.instvm7.observe(viewLifecycleOwner) {
            binding.tvinst7.text = it ?: ""
        }

        viewModel.crgvm1.observe(viewLifecycleOwner) {
            binding.tvcrg1.text = it ?: ""
        }

        viewModel.crgvm2.observe(viewLifecycleOwner) {
            binding.tvcrg2.text = it ?: ""
        }

        viewModel.crgvm3.observe(viewLifecycleOwner) {
            binding.tvcrg3.text = it ?: ""
        }

        viewModel.crgvm4.observe(viewLifecycleOwner) {
            binding.tvcrg4.text = it ?: ""
        }

        viewModel.crgvm5.observe(viewLifecycleOwner) {
            binding.tvcrg5.text = it ?: ""
        }

        viewModel.crgvm6.observe(viewLifecycleOwner) {
            binding.tvcrg6.text = it ?: ""
        }

        viewModel.crgvm7.observe(viewLifecycleOwner) {
            binding.tvcrg7.text = it ?: ""
        }


        viewModel.etpvm1.observe(viewLifecycleOwner) {
            binding.tvetp1.text = it ?: ""
        }
        viewModel.etpvm2.observe(viewLifecycleOwner) {
            binding.tvetp2.text = it ?: ""
        }

        viewModel.etpvm3.observe(viewLifecycleOwner) {
            binding.tvetp3.text = it ?: ""
        }

        viewModel.etpvm4.observe(viewLifecycleOwner) {
            binding.tvetp4.text = it ?: ""
        }

        viewModel.etpvm5.observe(viewLifecycleOwner) {
            binding.tvetp5.text = it ?: ""
        }

        viewModel.etpvm6.observe(viewLifecycleOwner) {
            binding.tvetp6.text = it ?: ""
        }

        viewModel.etpvm7.observe(viewLifecycleOwner) {
            binding.tvetp7.text = it ?: ""
        }

        viewModel.terrestrevm.observe(viewLifecycleOwner) {
            binding.tvterrestre.text = it ?: ""
        }

        viewModel.aereavm.observe(viewLifecycleOwner) {
            binding.tvaerea.text = it ?: ""
        }

        viewModel.maritimavm.observe(viewLifecycleOwner) {
            binding.tvmaritima.text = it ?: ""
        }

        viewModel.condNovm.observe(viewLifecycleOwner) {
            binding.tvconNo.text = it ?: ""
        }

        viewModel.condSivm.observe(viewLifecycleOwner) {
            binding.tvConSi.text = it ?: ""
        }

        viewModel.recomendacionvm.observe(viewLifecycleOwner) {
            binding.tvRecom.text = it ?: ""
        }




    }

    private fun generarPdf() {

        val context = requireContext()

        val uri = PdfStorageManager().createPdfUri(context, "CadenaCustodia")

        if (uri == null) {
            Toast.makeText(context, "No se pudo crear el archivo", Toast.LENGTH_SHORT).show()
            return
        }

        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            PdfGenerator().generarPdf(context, viewModel, outputStream)
        } ?: run {
            Toast.makeText(context, "Error al abrir archivo", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(context, "PDF generado correctamente", Toast.LENGTH_SHORT).show()


        PdfViewer().openPdf(context, uri)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}