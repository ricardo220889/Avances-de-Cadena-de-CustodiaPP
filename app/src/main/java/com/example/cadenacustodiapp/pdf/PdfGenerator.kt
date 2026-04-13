package com.example.cadenacustodiapp.pdf
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.example.cadenacustodiapp.R
import com.example.cadenacustodiapp.viewmodel.ViewModel
import androidx.core.content.ContextCompat
import com.itextpdf.text.*
import com.itextpdf.text.pdf.*
import java.io.ByteArrayOutputStream
import android.content.Context
import java.io.OutputStream

class PdfGenerator {

    private val imageCache = mutableMapOf<Int, Image>()
    private val Verde: BaseColor = BaseColor(21, 109, 24)
    private val FONT_CELL: Font =
        Font(Font.FontFamily.HELVETICA, 9.5f, Font.BOLD, BaseColor.WHITE)
    private val FONT_CELL2: Font =
        Font(Font.FontFamily.HELVETICA, 8.5f, Font.BOLD, BaseColor.WHITE)





    fun generarPdf(context: Context, vm: ViewModel, outputStream: OutputStream) {

        val document = Document(PageSize.A4)

        //PdfWriter.getInstance(document, outputStream)

        val writer = PdfWriter.getInstance(document, outputStream)

        writer.pageEvent = object : PdfPageEventHelper() {

            val fontFooter = Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL, BaseColor.BLACK)

            override fun onEndPage(writer: PdfWriter, document: Document) {

                ColumnText.showTextAligned(
                    writer.directContent,
                    Element.ALIGN_LEFT,
                    Phrase("Registro de Cadena de Custodia", fontFooter),
                    50f,
                    55f,
                    0f
                )

                ColumnText.showTextAligned(
                    writer.directContent,
                    Element.ALIGN_RIGHT,
                    Phrase("Página ${writer.pageNumber}", fontFooter),
                    520f,
                    55f,
                    0f
                )
            }
        }


        document.open()

        addLogos(context, document, vm)
        addInicioCadena(context, document, vm)
        addIdeInd(context, document, vm)
        addDoc(context, document, vm)
        addRec(context, document, vm)
        addEmb(context, document, vm)
        addServPub(context, document, vm)
        addTra(context, document, vm)
        addTraZa(context, document, vm)




        document.close()
    }

    private fun addLogos(context: Context, document: Document,  vm: ViewModel) {
        val table = PdfPTable(1).apply {
            widthPercentage = 100f
            defaultCell.border = PdfPCell.NO_BORDER
            defaultCell.verticalAlignment = Element.ALIGN_RIGHT
            defaultCell.horizontalAlignment = Element.ALIGN_TOP
        }

        val logoCadena = getPdfImage(context,R.drawable.cadena).apply {
            scaleToFit(140f, 60f)
            alignment = Element.ALIGN_RIGHT
        }

        val cellLogo = PdfPCell(logoCadena).apply {
            horizontalAlignment = Element.ALIGN_RIGHT
            verticalAlignment = Element.ALIGN_TOP
            isUseAscender = true
            border = PdfPCell.NO_BORDER
            setPadding(1f)
        }

        table.addCell(cellLogo)
        document.add(table)

        addEmptyLine(document, 2)

        //  TABLA NUMERO REFERENCIA
        val NumeroReferencia = PdfPTable(2).apply {
            horizontalAlignment = Element.ALIGN_RIGHT
            widthPercentage = 100f
            setWidths(floatArrayOf(4f, 2.3f))
        }

        val logoRcc = getPdfImage(context, R.drawable.rcc).apply {
            scaleToFit(200f, 100f)
            alignment = Element.ALIGN_CENTER
        }

        val cell1 = PdfPCell(logoRcc).apply {
            rowspan = 2
            horizontalAlignment = Element.ALIGN_CENTER
            verticalAlignment = Element.ALIGN_MIDDLE
            isUseAscender = true
            border = PdfPCell.NO_BORDER
        }
        NumeroReferencia.addCell(cell1)

        val cell2 = PdfPCell().apply {
            horizontalAlignment = Element.ALIGN_RIGHT
            verticalAlignment = Element.ALIGN_TOP
            isUseAscender = true
            backgroundColor = Verde
            borderWidth = 0.7f
            setPadding(1f)
        }

        val temp = Paragraph(
            "No. de referencia",
            Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD, BaseColor.WHITE)
        ).apply { alignment = Element.ALIGN_CENTER }

        cell2.addElement(temp)
        NumeroReferencia.addCell(cell2)

        val cell3 = PdfPCell().apply {
            horizontalAlignment = Element.ALIGN_RIGHT
            verticalAlignment = Element.ALIGN_TOP
            isUseAscender = true
            backgroundColor = BaseColor.WHITE
            borderWidth = 0.7f
            setPadding(3f)
        }

        val NoRef = Paragraph(
            vm.referenciavm.value ?: " ",
            Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, BaseColor.BLACK)
        ).apply {
            alignment = Element.ALIGN_CENTER
        }

        cell3.addElement(NoRef)
        NumeroReferencia.addCell(cell3)


        document.add(NumeroReferencia)

        addEmptyLine (document, 2)

        val header = PdfPTable(4)
        //headerRow.setKeepTogether(true);
        header.widthPercentage = 100F
        header.setWidths(intArrayOf(2, 2, 4, 3))
        header.addCell(createCell("Institución o unidad  administrativa", 1, 1))
        header.addCell(createCell("Folio o \n llamado", 1, 1))
        header.addCell(createCell("Lugar de intervención", 1, 1))
        header.addCell(createCell("Fecha y hora de \n intervención", 1, 1))

        val firstRow = PdfPTable(4)
        firstRow.widthPercentage = 100F
        firstRow.setWidths(intArrayOf(2, 2, 4, 3))
        val cell6 = PdfPCell()
        cell6.rowspan = 2

        val inst: Paragraph =
            Paragraph(
                "",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
            )
        inst.add(vm.institucionvm.value ?: " ")
        inst.spacingAfter = 1.0f
        inst.alignment = Element.ALIGN_CENTER
        cell6.addElement(inst)
        firstRow.addCell(cell6)
        val cell7 = PdfPCell()
        cell7.rowspan = 2

        val fol: Paragraph =
            Paragraph(
                "",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
            )
        fol.add(vm.foliovm.value ?: " ")
        fol.spacingAfter = 1.0f
        fol.alignment = Element.ALIGN_CENTER
        cell7.addElement(fol)
        firstRow.addCell(cell7)
        val cell8 = PdfPCell()
        cell8.rowspan = 4
        cell8.isUseAscender = true
        cell8.setPadding(3f)


        val lugInt =
            Paragraph(
                "",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
            )
        lugInt.add(vm.lugarvm.value ?: " ")
        lugInt.spacingAfter = 1.0f
        lugInt.alignment = Element.ALIGN_JUSTIFIED_ALL
        cell8.addElement(lugInt)
        firstRow.addCell(cell8)
        val cell11 = PdfPCell()
        cell11.rowspan = 1

        val fecha =
            Paragraph(
                "",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
            )
        fecha.add(vm.fechavm.value ?: " ")
        fecha.spacingAfter = 1.0f
        fecha.alignment = Element.ALIGN_CENTER
        cell11.addElement(fecha)
        firstRow.addCell(cell11)
        val cell12 = PdfPCell()
        cell12.rowspan = 1

        val hora =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        hora.add(vm.horavm.value ?: " ")
        hora.spacingAfter = 1.0f
        hora.alignment = Element.ALIGN_CENTER
        cell12.addElement(hora)
        firstRow.addCell(cell12)

        document.add(header)
        document.add(firstRow)
        addMediaEmptyLine(document, 1)
    }

    private fun addInicioCadena (context: Context, document: Document,  vm: ViewModel){

        val instrucciones = PdfPTable(2)
        instrucciones.horizontalAlignment = Element.ALIGN_LEFT
        instrucciones.widthPercentage = 100F
        instrucciones.setWidths(intArrayOf(3, 6))
        val cell2 = PdfPCell()
        cell2.horizontalAlignment = Element.ALIGN_LEFT
        cell2.verticalAlignment = Element.ALIGN_MIDDLE
        cell2.isUseAscender = true
        cell2.border = PdfPCell.NO_BORDER
        cell2.setPadding(3f)
        val temp = Paragraph(
            "Inicio de la cadena de custodia. ",
            Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
        )
        temp.alignment = Element.ALIGN_RIGHT
        cell2.addElement(temp)
        instrucciones.addCell(cell2)
        val cell3 = PdfPCell()
        cell3.horizontalAlignment = Element.ALIGN_LEFT
        cell3.verticalAlignment = Element.ALIGN_MIDDLE
        cell3.isUseAscender = true
        cell3.border = PdfPCell.NO_BORDER
        cell3.setPadding(3f)
        val temp1 = Paragraph(
            "(Marque con “X” el motivo por el cual comienza el registro). ",
            Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
        )
        temp1.alignment = Element.ALIGN_LEFT
        cell3.addElement(temp1)
        instrucciones.addCell(cell3)
        document.add(instrucciones)

        addMediaEmptyLine(document, 1)

        val inicio = PdfPTable(6)
        inicio.widthPercentage = 100F
        inicio.setWidths(floatArrayOf(1.5f, 1f, 1.5f, 1f, 1.5f, 1f))
        val cell15 = PdfPCell(Phrase("Localización", FONT_CELL))
        cell15.isUseAscender = true
        cell15.backgroundColor = Verde
        cell15.setPadding(6f)
        cell15.horizontalAlignment = Element.ALIGN_CENTER
        cell15.verticalAlignment = Element.ALIGN_MIDDLE
        inicio.addCell(cell15)

        val cell14 = PdfPCell()
        val loc =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        loc.add(vm.localizacionvm.value?: " ")
        loc.spacingAfter = 2.0f
        loc.alignment = Element.ALIGN_CENTER
        cell14.addElement(loc)
        cell14.setPadding(6f)
        inicio.addCell(cell14)

        val cell19 = PdfPCell(Phrase("Descubrimiento", FONT_CELL))
        cell19.isUseAscender = true
        cell19.backgroundColor = Verde
        cell19.setPadding(6f)
        cell19.horizontalAlignment = Element.ALIGN_CENTER
        cell19.verticalAlignment = Element.ALIGN_MIDDLE
        inicio.addCell(cell19)

        val cell18 = PdfPCell()
        val desc=
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        desc.add(vm.descubrimientovm.value?: " ")
        desc.spacingAfter = 2.0f
        desc.alignment = Element.ALIGN_CENTER
        cell18.addElement(desc)
        cell18.setPadding(6f)
        inicio.addCell(cell18)

        val cell20 = PdfPCell(Phrase("Aportación", FONT_CELL))
        cell20.isUseAscender = true
        cell20.backgroundColor = Verde
        cell20.setPadding(6f)
        cell20.horizontalAlignment = Element.ALIGN_CENTER
        cell20.verticalAlignment = Element.ALIGN_MIDDLE
        inicio.addCell(cell20)

        val cell16 = PdfPCell()
        val apor =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        apor.add(vm.aportacionvm.value?: " ")
        apor.spacingAfter = 2.0f
        apor.alignment = Element.ALIGN_CENTER
        cell16.addElement(apor)
        cell16.setPadding(6f)
        inicio.addCell(cell16)

        document.add(inicio)

        addMediaEmptyLine(document, 1)

    }

    private fun addIdeInd(context: Context, document: Document,  vm: ViewModel){

        val instrucciones2 = PdfPTable(2)
        instrucciones2.horizontalAlignment = Element.ALIGN_LEFT
        instrucciones2.widthPercentage = 100F
        instrucciones2.setWidths(floatArrayOf(1.6f, 7f))
        val cell21 = PdfPCell(
            Phrase(
                "1.    Identidad.",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
            )
        )
        cell21.isUseAscender = true
        cell21.border = PdfPCell.NO_BORDER
        cell21.setPadding(3f)
        cell21.horizontalAlignment = Element.ALIGN_RIGHT
        cell21.verticalAlignment = Element.ALIGN_MIDDLE
        instrucciones2.addCell(cell21)
        val cell22 = PdfPCell(
            Phrase(
                "(Número, letra o combinación alfanumérica asignada al indicio o elemento material probatorio, descripción general,\n",
                Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell22.isUseAscender = true
        cell22.border = PdfPCell.NO_BORDER
        cell22.setPadding(3f)
        cell22.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
        cell22.verticalAlignment = Element.ALIGN_MIDDLE
        instrucciones2.addCell(cell22)
        val cell17 = PdfPCell()
        val temp2 = Paragraph(
            "incluyendo en su caso el estado o condición original en el momento de su recolección, ubicación en el lugar de intervención y hora de \n" +
                    "recolección. Relacione la identificación por secuencias cuando se trate de indicios o elementos materiales probatorios del mismo tipo \n" +
                    "o clase; en caso contrario, registre individualmente. Cancele los espacios sobrantes)",
            Font(Font.FontFamily.HELVETICA, 7.4f, Font.NORMAL, BaseColor.BLACK)
        )
        temp2.alignment = Element.ALIGN_LEFT
        temp2.indentationLeft = 35.4f
        cell17.addElement(temp2)
        cell17.border = PdfPCell.NO_BORDER
        cell17.isUseAscender = true
        cell17.colspan = 2
        //cell17.setPadding(2f);
        cell17.horizontalAlignment = Element.ALIGN_TOP
        cell17.verticalAlignment = Element.ALIGN_TOP
        instrucciones2.addCell(cell17)
        document.add(instrucciones2)

        addMediaEmptyLine(document, 1)

        val header2 = PdfPTable(4)
        //headerRow.setKeepTogether(true);
        header2.widthPercentage = 100F
        header2.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))
        header2.addCell(createCell("Identificación", 1, 1))
        header2.addCell(createCell("Descripción", 1, 1))
        header2.addCell(createCell("Ubicación \n" + "en el lugar", 1, 1))
        header2.addCell(createCell("Hora de\n" + "recolección", 1, 1))

        document.add(header2)

        val row1 = PdfPTable(4)
        run {

            row1.widthPercentage = 100F
            row1.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))
            val cell23: PdfPCell = PdfPCell()
            val ident1 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            ident1.add(vm.identificacionvm1.value?: " ")
            ident1.spacingAfter = 2.0f
            ident1.alignment = Element.ALIGN_CENTER
            cell23.addElement(ident1)
            cell23.setPadding(6f)
            row1.addCell(cell23)

            val cell24: PdfPCell = PdfPCell()
            val desc1 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            desc1.add(vm.descripcionvm1.value?: " ")
            desc1.spacingAfter = 2.0f
            desc1.alignment = Element.ALIGN_CENTER
            cell24.addElement(desc1)
            row1.addCell(cell24)

            val cell25: PdfPCell = PdfPCell()
            val ubi1 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            ubi1.add(vm.ubicacionvm1.value?: " ")
            ubi1.spacingAfter = 2.0f
            ubi1.alignment = Element.ALIGN_CENTER
            cell25.addElement(ubi1)
            row1.addCell(cell25)

            val cell26: PdfPCell = PdfPCell()
            val hora1 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            hora1.add(vm.recoleccionvm1.value?: " ")
            hora1.spacingAfter = 2.0f
            hora1.alignment = Element.ALIGN_CENTER
            cell26.addElement(hora1)
            cell26.setPadding(6f)
            row1.addCell(cell26)

            document.add(row1)
        }
        val row2 = PdfPTable(4)
        run {
            row2.widthPercentage = 100F
            row2.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))

            val cell23: PdfPCell = PdfPCell()
            val ident2 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            ident2.add(vm.identificacionvm2.value?: " ")
            ident2.spacingAfter = 2.0f
            ident2.alignment = Element.ALIGN_CENTER
            cell23.addElement(ident2)
            cell23.setPadding(6f)
            row2.addCell(cell23)

            val cell24: PdfPCell = PdfPCell()
            val desc2 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            desc2.add(vm.descripcionvm2.value?: " ")
            desc2.spacingAfter = 2.0f
            desc2.alignment = Element.ALIGN_CENTER
            cell24.addElement(desc2)
            row2.addCell(cell24)

            val cell25: PdfPCell = PdfPCell()
            val ubi2 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            ubi2.add(vm.ubicacionvm2.value?: " ")
            ubi2.spacingAfter = 2.0f
            ubi2.alignment = Element.ALIGN_CENTER
            cell25.addElement(ubi2)
            row2.addCell(cell25)

            val cell26: PdfPCell = PdfPCell()
            val hora2 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            hora2.add(vm.recoleccionvm2.value?: " ")
            hora2.spacingAfter = 2.0f
            hora2.alignment = Element.ALIGN_CENTER
            cell26.addElement(hora2)
            cell26.setPadding(6f)
            row2.addCell(cell26)

            document.add(row2)
        }
        val row3 = PdfPTable(4)
        run {
            row3.widthPercentage = 100F
            row3.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))

            val cell23: PdfPCell = PdfPCell()
            val ident3 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            ident3.add(vm.identificacionvm3.value?: " ")
            ident3.spacingAfter = 2.0f
            ident3.alignment = Element.ALIGN_CENTER
            cell23.addElement(ident3)
            cell23.setPadding(6f)
            row3.addCell(cell23)

            val cell24: PdfPCell = PdfPCell()
            val desc3 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            desc3.add(vm.descripcionvm3.value?: " ")
            desc3.spacingAfter = 2.0f
            desc3.alignment = Element.ALIGN_CENTER
            cell24.addElement(desc3)
            row3.addCell(cell24)

            val cell25: PdfPCell = PdfPCell()
            val ubi3 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            ubi3.add(vm.ubicacionvm3.value?: " ")
            ubi3.spacingAfter = 2.0f
            ubi3.alignment = Element.ALIGN_CENTER
            cell25.addElement(ubi3)
            row3.addCell(cell25)

            val cell26: PdfPCell = PdfPCell()
            val hora3 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            hora3.add(vm.recoleccionvm3.value?: " ")
            hora3.spacingAfter = 2.0f
            hora3.alignment = Element.ALIGN_CENTER
            cell26.addElement(hora3)
            cell26.setPadding(6f)
            row3.addCell(cell26)

            document.add(row3)
        }
        val row4 = PdfPTable(4)
        run {
            row4.widthPercentage = 100F
            row4.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))

            val cell23: PdfPCell = PdfPCell()
            val ident4 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            ident4.add(vm.identificacionvm4.value?: " ")
            ident4.spacingAfter = 2.0f
            ident4.alignment = Element.ALIGN_CENTER
            cell23.addElement(ident4)
            cell23.setPadding(6f)
            row4.addCell(cell23)

            val cell24: PdfPCell = PdfPCell()
            val desc4 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            desc4.add(vm.descripcionvm4.value?: " ")
            desc4.spacingAfter = 2.0f
            desc4.alignment = Element.ALIGN_CENTER
            cell24.addElement(desc4)
            row4.addCell(cell24)

            val cell25: PdfPCell = PdfPCell()
            val ubi4 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            ubi4.add(vm.ubicacionvm4.value?: " ")
            ubi4.spacingAfter = 2.0f
            ubi4.alignment = Element.ALIGN_CENTER
            cell25.addElement(ubi4)
            row4.addCell(cell25)

            val cell26: PdfPCell = PdfPCell()
            val hora4 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            hora4.add(vm.recoleccionvm4.value?: " ")
            hora4.spacingAfter = 2.0f
            hora4.alignment = Element.ALIGN_CENTER
            cell26.addElement(hora4)
            cell26.setPadding(6f)
            row4.addCell(cell26)

            document.add(row4)
        }
        val row5 = PdfPTable(4)
        run {
            row5.widthPercentage = 100F
            row5.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))

            val cell23: PdfPCell = PdfPCell()
            val ident5 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            ident5.add(vm.identificacionvm5.value?: " ")
            ident5.spacingAfter = 2.0f
            ident5.alignment = Element.ALIGN_CENTER
            cell23.addElement(ident5)
            cell23.setPadding(6f)
            row5.addCell(cell23)

            val cell24: PdfPCell = PdfPCell()
            val desc5 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            desc5.add(vm.descripcionvm5.value?: " ")
            desc5.spacingAfter = 2.0f
            desc5.alignment = Element.ALIGN_CENTER
            cell24.addElement(desc5)
            row5.addCell(cell24)

            val cell25: PdfPCell = PdfPCell()
            val ubi5 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            ubi5.add(vm.ubicacionvm5.value?: " ")
            ubi5.spacingAfter = 2.0f
            ubi5.alignment = Element.ALIGN_CENTER
            cell25.addElement(ubi5)
            row5.addCell(cell25)

            val cell26: PdfPCell = PdfPCell()
            val hora5 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            hora5.add(vm.recoleccionvm5.value?: " ")
            hora5.spacingAfter = 2.0f
            hora5.alignment = Element.ALIGN_CENTER
            cell26.addElement(hora5)
            cell26.setPadding(6f)
            row5.addCell(cell26)
            document.add(row5)
        }

        addMediaEmptyLine(document, 1)


    }

    private fun addDoc(context: Context, document: Document,  vm: ViewModel){
        val instrucciones3 = PdfPTable(2)
        run {
            instrucciones3.horizontalAlignment = Element.ALIGN_LEFT
            instrucciones3.widthPercentage = 100F
            instrucciones3.setWidths(floatArrayOf(2.0f, 7f))
            val cell28: PdfPCell = PdfPCell(
                Phrase(
                    "2. Documentación.",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
                )
            )
            cell28.isUseAscender = true
            cell28.border = PdfPCell.NO_BORDER
            //cell28.setPadding(3f);
            cell28.horizontalAlignment = Element.ALIGN_RIGHT
            cell28.verticalAlignment = Element.ALIGN_BASELINE
            instrucciones3.addCell(cell28)
            val cell29: PdfPCell = PdfPCell(
                Phrase(
                    "(Marque con “X” los métodos empleados o especifique cualquier otro en caso necesario).",
                    Font(Font.FontFamily.HELVETICA, 7.8f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell29.isUseAscender = true
            cell29.border = PdfPCell.NO_BORDER
            //cell29.setPadding(3f);
            cell29.horizontalAlignment = Element.ALIGN_LEFT
            cell29.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones3.addCell(cell29)
            document.add(instrucciones3)
        }

        addMediaEmptyLine(document, 1)

        val outerTable = PdfPTable(1)
        outerTable.widthPercentage = 100F
        outerTable.horizontalAlignment = Element.ALIGN_LEFT
        val innerTable = PdfPTable(12)
        innerTable.widthPercentage = 100F
        innerTable.spacingAfter = 4.0f
        innerTable.setWidths(floatArrayOf(1.5f, 1f, 1f, 1f, 2.3f, 1f, 1f, 1f, 1.5f, 1f, 1f, 1f))
        val cell1 = PdfPCell(
            Phrase(
                "Escrito:   Sí",
                Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell1.horizontalAlignment = Element.ALIGN_CENTER
        cell1.verticalAlignment = Element.ALIGN_MIDDLE
        cell1.border = PdfPCell.NO_BORDER
        cell1.isUseAscender = false
        innerTable.addCell(cell1)

        val cell30 =
            PdfPCell()
        val escSi =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        escSi.add( vm.escritovmSi.value ?: " ")
        escSi.spacingAfter = 2.0f
        escSi.alignment = Element.ALIGN_CENTER
        cell30.addElement(escSi)
        cell30.horizontalAlignment = Element.ALIGN_CENTER
        cell30.verticalAlignment = Element.ALIGN_MIDDLE
        cell30.borderWidth = 1F
        innerTable.addCell(cell30)

        val cell31 =
            PdfPCell(Phrase("No", Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)))
        cell31.horizontalAlignment = Element.ALIGN_RIGHT
        cell31.verticalAlignment = Element.ALIGN_MIDDLE
        cell31.border = PdfPCell.NO_BORDER
        innerTable.addCell(cell31)

        val cell4 =
            PdfPCell()
        val escNo =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        escNo.add(vm.escritovmNo.value ?: " ")
        escNo.spacingAfter = 2.0f
        escNo.alignment = Element.ALIGN_CENTER
        cell4.addElement(escNo)
        cell4.horizontalAlignment = Element.ALIGN_LEFT
        cell4.verticalAlignment = Element.ALIGN_LEFT
        cell4.borderWidth = 1F
        innerTable.addCell(cell4)

        val cell5 = PdfPCell(
            Phrase(
                "Fotográfico:  Sí",
                Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell5.horizontalAlignment = Element.ALIGN_CENTER
        cell5.verticalAlignment = Element.ALIGN_MIDDLE
        cell5.border = PdfPCell.NO_BORDER
        innerTable.addCell(cell5)

        val cell32 =
            PdfPCell()
        val fotSi =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        fotSi.add(vm.fotograficovmSi.value ?: " ")
        fotSi.spacingAfter = 2.0f
        fotSi.alignment = Element.ALIGN_CENTER
        cell32.addElement(fotSi)
        cell32.horizontalAlignment = Element.ALIGN_LEFT
        cell32.verticalAlignment = Element.ALIGN_LEFT
        cell32.borderWidth = 1F
        innerTable.addCell(cell32)

        val cell33 =
            PdfPCell(Phrase("No ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)))
        cell33.horizontalAlignment = Element.ALIGN_RIGHT
        cell33.verticalAlignment = Element.ALIGN_MIDDLE
        cell33.border = PdfPCell.NO_BORDER
        innerTable.addCell(cell33)

        val cell34 =
            PdfPCell()
        val fotNo =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        fotNo.add(vm.fotograficovmNo.value ?: " ")
        fotNo.spacingAfter = 2.0f
        fotNo.alignment = Element.ALIGN_CENTER
        cell34.addElement(fotNo)
        cell34.horizontalAlignment = Element.ALIGN_LEFT
        cell34.verticalAlignment = Element.ALIGN_LEFT
        cell34.borderWidth = 1F
        innerTable.addCell(cell34)

        val cell9 = PdfPCell(
            Phrase(
                "Croquis:  Sí",
                Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell9.horizontalAlignment = Element.ALIGN_CENTER
        cell9.verticalAlignment = Element.ALIGN_MIDDLE
        cell9.border = PdfPCell.NO_BORDER
        innerTable.addCell(cell9)

        val cell10 =
            PdfPCell()
        val croqSi =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        croqSi.add(vm.croquisvmSi.value ?: " ")
        croqSi.spacingAfter = 2.0f
        croqSi.alignment = Element.ALIGN_CENTER
        cell10.addElement(croqSi)
        cell10.horizontalAlignment = Element.ALIGN_LEFT
        cell10.verticalAlignment = Element.ALIGN_LEFT
        cell10.borderWidth = 1F
        innerTable.addCell(cell10)

        val cell35 =
            PdfPCell(Phrase("No ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)))
        cell35.horizontalAlignment = Element.ALIGN_RIGHT
        cell35.verticalAlignment = Element.ALIGN_MIDDLE
        cell35.border = PdfPCell.NO_BORDER
        innerTable.addCell(cell35)

        val cell36 =
            PdfPCell()
        val croqNo =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        croqNo.add(vm.croquisvmNo.value ?: " ")
        croqNo.spacingAfter = 2.0f
        croqNo.alignment = Element.ALIGN_CENTER
        cell36.addElement(croqNo)
        cell36.horizontalAlignment = Element.ALIGN_LEFT
        cell36.verticalAlignment = Element.ALIGN_LEFT
        cell36.borderWidth = 1F
        innerTable.addCell(cell36)

        val secondRow = PdfPTable(4)
        secondRow.widthPercentage = 31.5f
        secondRow.spacingAfter = 4.0f
        secondRow.horizontalAlignment = Element.ALIGN_LEFT
        secondRow.setWidths(floatArrayOf(1.5f, 1f, 1f, 1f))
        val cell13 = PdfPCell(
            Phrase(
                "Otro:       Sí",
                Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell13.horizontalAlignment = Element.ALIGN_CENTER
        cell13.verticalAlignment = Element.ALIGN_MIDDLE
        cell13.border = PdfPCell.NO_BORDER
        secondRow.addCell(cell13)

        val cell37 =
            PdfPCell()
        val otrSi =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        otrSi.add(vm.otrovmSi.value ?: " ")
        otrSi.spacingAfter = 2.0f
        otrSi.alignment = Element.ALIGN_CENTER
        cell37.addElement(otrSi)
        cell37.horizontalAlignment = Element.ALIGN_LEFT
        cell37.verticalAlignment = Element.ALIGN_LEFT
        cell37.borderWidth = 1F
        secondRow.addCell(cell37)

        val cell38 =
            PdfPCell(Phrase("No ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)))
        cell38.horizontalAlignment = Element.ALIGN_RIGHT
        cell38.verticalAlignment = Element.ALIGN_MIDDLE
        cell38.border = PdfPCell.NO_BORDER
        secondRow.addCell(cell38)

        val cell39 =
            PdfPCell()
        val otrNo =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        otrNo.add(vm.otrovmNo.value ?: " ")
        otrNo.spacingAfter = 2.0f
        otrNo.alignment = Element.ALIGN_CENTER
        cell39.addElement(otrNo)
        cell39.horizontalAlignment = Element.ALIGN_CENTER
        cell39.verticalAlignment = Element.ALIGN_MIDDLE
        cell39.borderWidth = 1F
        secondRow.addCell(cell39)

        val thirdRow = PdfPTable(2)
        thirdRow.widthPercentage = 70.0f
        thirdRow.spacingAfter = 4.0f
        thirdRow.horizontalAlignment = Element.ALIGN_LEFT
        thirdRow.setWidths(floatArrayOf(1.6f, 9.2f))
        val cell40 = PdfPCell(
            Phrase(
                "Especifique:",
                Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell40.horizontalAlignment = Element.ALIGN_CENTER
        cell40.verticalAlignment = Element.ALIGN_MIDDLE
        cell40.border = PdfPCell.NO_BORDER
        thirdRow.addCell(cell40)

        val cell41 =
            PdfPCell()
        val esp =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        esp.add(vm.especifiquevm.value ?: " ")
        esp.spacingAfter = 2.0f
        esp.alignment = Element.ALIGN_CENTER
        cell41.addElement(esp)
        cell41.horizontalAlignment = Element.ALIGN_LEFT
        cell41.verticalAlignment = Element.ALIGN_LEFT
        cell41.border = 2
        thirdRow.addCell(cell41)

        val cell42 = PdfPCell()
        cell42.setPadding(5F)

        cell42.addElement(innerTable)
        cell42.addElement(secondRow)
        cell42.addElement(thirdRow)

        outerTable.addCell(cell42)

        document.add(outerTable)

        addMediaEmptyLine(document, 1)

    }

    private fun addRec(context: Context, document: Document,  vm: ViewModel){


        val instrucciones4 = PdfPTable(2)
        instrucciones4.horizontalAlignment = Element.ALIGN_LEFT
        instrucciones4.widthPercentage = 100F
        instrucciones4.setWidths(floatArrayOf(1.6f, 7f))
        val cell43 = PdfPCell(
            Phrase(
                "3.    Recolección.",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
            )
        )
        cell43.isUseAscender = true
        cell43.border = PdfPCell.NO_BORDER
        cell43.setPadding(3f)
        cell43.horizontalAlignment = Element.ALIGN_RIGHT
        cell43.verticalAlignment = Element.ALIGN_MIDDLE
        instrucciones4.addCell(cell43)
        val cell44 = PdfPCell(
            Phrase(
                "(Coloque el número, letra o combinación de los indicios o elementos materiales probatorios de acuerdo a las\n",
                Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell44.isUseAscender = true
        cell44.border = PdfPCell.NO_BORDER
        cell44.setPadding(3f)
        cell44.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
        cell44.verticalAlignment = Element.ALIGN_MIDDLE
        instrucciones4.addCell(cell44)
        val cell45 = PdfPCell()
        val temp3 = Paragraph(
            "condiciones de cómo fueron levantados según corresponda. Puede emplear intervalos). \n",
            Font(Font.FontFamily.HELVETICA, 7.4f, Font.NORMAL, BaseColor.BLACK)
        )
        temp3.alignment = Element.ALIGN_LEFT
        temp3.indentationLeft = 23.0f
        cell45.addElement(temp3)
        cell45.border = PdfPCell.NO_BORDER
        cell45.isUseAscender = true
        cell45.colspan = 2
        cell45.horizontalAlignment = Element.ALIGN_TOP
        cell45.verticalAlignment = Element.ALIGN_TOP
        instrucciones4.addCell(cell45)
        document.add(instrucciones4)

        addMediaEmptyLine(document, 1)

        val header3 = PdfPTable(2)
        header3.widthPercentage = 100F
        header3.addCell(createCell("Manual", 1, 1))
        header3.addCell(createCell("Instrumental", 1, 1))
        val header4 = PdfPTable(2)
        header4.widthPercentage = 100F

        val cell46 =
            PdfPCell()
        val man =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        man.add(vm.rmanualvm.value ?: " ")
        man.spacingAfter = 2.0f
        man.alignment = Element.ALIGN_CENTER
        cell46.addElement(man)
        cell46.horizontalAlignment = Element.ALIGN_CENTER
        cell46.verticalAlignment = Element.ALIGN_MIDDLE
        header4.addCell(cell46)

        val cell47 =
            PdfPCell()
        val instru =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        instru.add(vm.rinstrumentalvm.value ?: " ")
        instru.spacingAfter = 2.0f
        instru.alignment = Element.ALIGN_CENTER
        cell47.addElement(instru)
        cell47.horizontalAlignment = Element.ALIGN_CENTER
        cell47.verticalAlignment = Element.ALIGN_MIDDLE
        header4.addCell(cell47)

        document.add(header3)
        document.add(header4)

        addEmptyLine(document, 1)




        document.newPage()

    }

    private fun addEmb(context: Context, document: Document,  vm: ViewModel){

        addEnc(context, document, vm)

        val instrucciones5 = PdfPTable(2)
        instrucciones5.horizontalAlignment = Element.ALIGN_LEFT
        instrucciones5.widthPercentage = 100F
        instrucciones5.setWidths(floatArrayOf(2.5f, 7f))
        val cell54 = PdfPCell(
            Phrase(
                "4.    Empaque/Embalaje.",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
            )
        )
        cell54.isUseAscender = true
        cell54.border = PdfPCell.NO_BORDER
        cell54.setPadding(3f)
        cell54.horizontalAlignment = Element.ALIGN_RIGHT
        cell54.verticalAlignment = Element.ALIGN_MIDDLE
        instrucciones5.addCell(cell54)
        val cell55 = PdfPCell(
            Phrase(
                "(Coloque el número, letra o combinación de los indicios o elementos materiales de acuerdo al tipo de\n",
                Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell55.isUseAscender = true
        cell55.border = PdfPCell.NO_BORDER
        cell55.setPadding(3f)
        cell55.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
        cell55.verticalAlignment = Element.ALIGN_MIDDLE
        instrucciones5.addCell(cell55)
        val cell56 = PdfPCell()
        val temp5 = Paragraph(
            "embalaje que se empleó para su preservación o conservación, según corresponda. Puede emplear intervalos). \n",
            Font(Font.FontFamily.HELVETICA, 7.4f, Font.NORMAL, BaseColor.BLACK)
        )
        temp5.alignment = Element.ALIGN_LEFT
        temp5.indentationLeft = 27.5f
        cell56.addElement(temp5)
        cell56.border = PdfPCell.NO_BORDER
        cell56.isUseAscender = true
        cell56.colspan = 2
        cell56.horizontalAlignment = Element.ALIGN_TOP
        cell56.verticalAlignment = Element.ALIGN_TOP
        instrucciones5.addCell(cell56)
        document.add(instrucciones5)

        addMediaEmptyLine(document, 1)

        val header5 = PdfPTable(3)
        header5.widthPercentage = 100F
        header5.addCell(createCell("Bolsa", 1, 1))
        header5.addCell(createCell("Caja", 1, 1))
        header5.addCell(createCell("Recipientes", 1, 1))
        val header6 = PdfPTable(3)
        header6.widthPercentage = 100F

        val cell57 =
            PdfPCell()
        val bol =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        bol.add(vm.bolsavm.value ?: " ")
        bol.spacingAfter = 2.0f
        bol.alignment = Element.ALIGN_CENTER
        cell57.addElement(bol)
        cell57.horizontalAlignment = Element.ALIGN_CENTER
        cell57.verticalAlignment = Element.ALIGN_MIDDLE
        header6.addCell(cell57)

        val cell58 =
            PdfPCell()
        val caj =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        caj.add(vm.cajavm.value ?: " ")
        caj.spacingAfter = 2.0f
        caj.alignment = Element.ALIGN_CENTER
        cell58.addElement(caj)
        cell58.horizontalAlignment = Element.ALIGN_CENTER
        cell58.verticalAlignment = Element.ALIGN_MIDDLE
        header6.addCell(cell58)

        val cell59 =
            PdfPCell()
        val rec =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        rec.add(vm.recipientevm.value ?: " ")
        rec.spacingAfter = 2.0f
        rec.alignment = Element.ALIGN_CENTER
        cell59.addElement(rec)
        cell59.horizontalAlignment = Element.ALIGN_CENTER
        cell59.verticalAlignment = Element.ALIGN_MIDDLE
        header6.addCell(cell59)
        document.add(header5)
        document.add(header6)

        addMediaEmptyLine(document, 1)

    }

    private fun addServPub(context: Context, document: Document,  vm: ViewModel){
        val instrucciones6 = PdfPTable(2)
        instrucciones6.horizontalAlignment = Element.ALIGN_LEFT
        instrucciones6.widthPercentage = 100F
        instrucciones6.setWidths(floatArrayOf(2.5f, 7f))
        val cell60 = PdfPCell(
            Phrase(
                "5.    Servidores públicos.",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
            )
        )
        cell60.isUseAscender = true
        cell60.border = PdfPCell.NO_BORDER
        cell60.setPadding(3f)
        cell60.horizontalAlignment = Element.ALIGN_RIGHT
        cell60.verticalAlignment = Element.ALIGN_MIDDLE
        instrucciones6.addCell(cell60)
        val cell61 = PdfPCell(
            Phrase(
                " (Todo servidor público que haya participado en el procesamiento de los indicios o elementos materiales\n",
                Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell61.isUseAscender = true
        cell61.border = PdfPCell.NO_BORDER
        cell61.setPadding(3f)
        cell61.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
        cell61.verticalAlignment = Element.ALIGN_MIDDLE
        instrucciones6.addCell(cell61)
        val cell62 = PdfPCell()
        val temp6 = Paragraph(
            "probatorios deberá escribir su nombre completo, la Institución  a  la  que  pertenece, su cargo, la  etapa  del  procesamiento  en  la  que \n" +
                    "intervino y su firma autógrafa. Se deberán cancelar los espacios sobrantes). \n",
            Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
        )
        temp6.alignment = Element.ALIGN_LEFT
        temp6.indentationLeft = 22.5f
        cell62.addElement(temp6)
        cell62.border = PdfPCell.NO_BORDER
        cell62.isUseAscender = true
        cell62.colspan = 2
        cell62.horizontalAlignment = Element.ALIGN_TOP
        cell62.verticalAlignment = Element.ALIGN_TOP
        instrucciones6.addCell(cell62)
        document.add(instrucciones6)
        addEmptyLine(document, 1)

        val header7 = PdfPTable(4)
        header7.widthPercentage = 100F
        header7.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
        header7.addCell(createCell("Nombre Completo", 1, 1))
        header7.addCell(createCell("Institución y cargo", 1, 1))
        header7.addCell(createCell("Etapa", 1, 1))
        val cell98 = PdfPCell(Phrase("Firma", FONT_CELL))
        cell98.isUseAscender = true
        cell98.backgroundColor = Verde
        cell98.setPadding(12f)
        cell98.horizontalAlignment = Element.ALIGN_CENTER
        cell98.verticalAlignment = Element.ALIGN_MIDDLE
        header7.addCell(cell98)

        val header8 = PdfPTable(4)
        header8.widthPercentage = 100F
        header8.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
        val cell63 =
            PdfPCell()
        val nom1 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        nom1.add(vm.servpubvm1.value ?: " ")
        nom1.spacingAfter = 1.0f
        nom1.alignment = Element.ALIGN_CENTER
        cell63.addElement(nom1)
        cell63.horizontalAlignment = Element.ALIGN_CENTER
        cell63.verticalAlignment = Element.ALIGN_MIDDLE
        cell63.rowspan = 2
        header8.addCell(cell63)
        val cell64 =
            PdfPCell()
        val insti1 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        insti1.add(vm.instvm1.value ?: " ")
        insti1.spacingAfter = 1.0f
        insti1.alignment = Element.ALIGN_CENTER
        cell64.addElement(insti1)
        cell64.setPadding(4f)
        cell64.horizontalAlignment = Element.ALIGN_CENTER
        cell64.verticalAlignment = Element.ALIGN_MIDDLE
        cell64.rowspan = 1
        header8.addCell(cell64)
        val cell65 =
            PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
        val etp1 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        etp1.add(vm.etpvm1.value ?: " ")
        etp1.spacingAfter = 1.0f
        etp1.alignment = Element.ALIGN_CENTER
        cell65.addElement(etp1)
        cell65.horizontalAlignment = Element.ALIGN_CENTER
        cell65.verticalAlignment = Element.ALIGN_MIDDLE
        cell65.rowspan = 2
        header8.addCell(cell65)

        val cell66 =
            PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
        cell66.horizontalAlignment = Element.ALIGN_CENTER
        cell66.verticalAlignment = Element.ALIGN_MIDDLE
        cell66.setPadding(6f)
        cell66.rowspan = 2
        header8.addCell(cell66)

        val cell67 =
            PdfPCell()
        val crg1 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        crg1.add(vm.crgvm1.value ?: " ")
        crg1.spacingAfter = 1.0f
        crg1.alignment = Element.ALIGN_CENTER
        cell67.addElement(crg1)
        cell67.horizontalAlignment = Element.ALIGN_CENTER
        cell67.verticalAlignment = Element.ALIGN_MIDDLE
        cell67.rowspan = 1
        header8.addCell(cell67)
        document.add(header7)
        document.add(header8)

        val header9 = PdfPTable(4)
        header9.widthPercentage = 100F
        header9.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))

        val cell68 =
            PdfPCell()
        val nom2 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        nom2.add(vm.servpubvm2.value ?: " ")
        nom2.spacingAfter = 2.0f
        nom2.alignment = Element.ALIGN_CENTER
        cell68.addElement(nom2)
        cell68.horizontalAlignment = Element.ALIGN_CENTER
        cell68.verticalAlignment = Element.ALIGN_MIDDLE
        cell68.rowspan = 2
        header9.addCell(cell68)

        val cell69 =
            PdfPCell()
        val insti2 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        insti2.add(vm.instvm2.value ?: " ")
        insti2.spacingAfter = 2.0f
        insti2.alignment = Element.ALIGN_CENTER
        cell69.addElement(insti2)
        cell69.setPadding(5f)
        cell69.horizontalAlignment = Element.ALIGN_CENTER
        cell69.verticalAlignment = Element.ALIGN_MIDDLE
        cell69.rowspan = 1
        header9.addCell(cell69)

        val cell70 =
            PdfPCell()
        val etp2 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        etp2.add(vm.etpvm2.value ?: " ")
        etp2.spacingAfter = 2.0f
        etp2.alignment = Element.ALIGN_CENTER
        cell70.addElement(etp2)
        cell70.horizontalAlignment = Element.ALIGN_CENTER
        cell70.verticalAlignment = Element.ALIGN_MIDDLE
        cell70.rowspan = 2
        header9.addCell(cell70)

        val cell71 =
            PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
        cell71.horizontalAlignment = Element.ALIGN_CENTER
        cell71.verticalAlignment = Element.ALIGN_MIDDLE
        cell71.setPadding(8f)
        cell71.rowspan = 2
        header9.addCell(cell71)

        val cell72 =
            PdfPCell()
        val crg2 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        crg2.add(vm.crgvm2.value ?: " ")
        crg2.spacingAfter = 2.0f
        crg2.alignment = Element.ALIGN_CENTER
        cell72.addElement(crg2)
        cell72.horizontalAlignment = Element.ALIGN_CENTER
        cell72.verticalAlignment = Element.ALIGN_MIDDLE
        cell72.rowspan = 1
        header9.addCell(cell72)
        document.add(header9)

        val header10 = PdfPTable(4)
        header10.widthPercentage = 100F
        header10.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
        val cell73 =
            PdfPCell()
        val nom3 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        nom3.add(vm.servpubvm3.value ?: " ")
        nom3.spacingAfter = 2.0f
        nom3.alignment = Element.ALIGN_CENTER
        cell73.addElement(nom3)
        cell73.horizontalAlignment = Element.ALIGN_CENTER
        cell73.verticalAlignment = Element.ALIGN_MIDDLE
        cell73.rowspan = 2
        header10.addCell(cell73)

        val cell74 =
            PdfPCell()
        val insti3 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        insti3.add(vm.instvm3.value ?: " ")
        insti3.spacingAfter = 2.0f
        insti3.alignment = Element.ALIGN_CENTER
        cell74.addElement(insti3)
        cell74.setPadding(5f)
        cell74.horizontalAlignment = Element.ALIGN_CENTER
        cell74.verticalAlignment = Element.ALIGN_MIDDLE
        cell74.rowspan = 1
        header10.addCell(cell74)

        val cell75 =
            PdfPCell()
        val etp3 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        etp3.add(vm.etpvm3.value ?: " ")
        etp3.spacingAfter = 2.0f
        etp3.alignment = Element.ALIGN_CENTER
        cell75.addElement(etp3)
        cell75.horizontalAlignment = Element.ALIGN_CENTER
        cell75.verticalAlignment = Element.ALIGN_MIDDLE
        cell75.rowspan = 2
        header10.addCell(cell75)

        val cell76 =
            PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
        cell76.horizontalAlignment = Element.ALIGN_CENTER
        cell76.verticalAlignment = Element.ALIGN_MIDDLE
        cell76.setPadding(8f)
        cell76.rowspan = 2
        header10.addCell(cell76)
        val cell77 =
            PdfPCell()
        val crg3 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        crg3.add(vm.crgvm3.value ?: " ")
        crg3.spacingAfter = 2.0f
        crg3.alignment = Element.ALIGN_CENTER
        cell77.addElement(crg3)
        cell77.horizontalAlignment = Element.ALIGN_CENTER
        cell77.verticalAlignment = Element.ALIGN_MIDDLE
        cell77.rowspan = 1
        header10.addCell(cell77)
        document.add(header10)

        val header11 = PdfPTable(4)
        header11.widthPercentage = 100F
        header11.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
        val cell78 =
            PdfPCell()
        val nom4 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        nom4.add(vm.servpubvm4.value ?: " ")
        nom4.spacingAfter = 2.0f
        nom4.alignment = Element.ALIGN_CENTER
        cell78.addElement(nom4)
        cell78.horizontalAlignment = Element.ALIGN_CENTER
        cell78.verticalAlignment = Element.ALIGN_MIDDLE
        cell78.rowspan = 2
        header11.addCell(cell78)

        val cell79 =
            PdfPCell()
        val insti4 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        insti4.add(vm.instvm4.value ?: " ")
        insti4.spacingAfter = 2.0f
        insti4.alignment = Element.ALIGN_CENTER
        cell79.addElement(insti4)
        cell79.setPadding(5f)
        cell79.horizontalAlignment = Element.ALIGN_CENTER
        cell79.verticalAlignment = Element.ALIGN_MIDDLE
        cell79.rowspan = 1
        header11.addCell(cell79)

        val cell80 =
            PdfPCell()
        val etp4 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        etp4.add(vm.etpvm4.value ?: " ")
        etp4.spacingAfter = 2.0f
        etp4.alignment = Element.ALIGN_CENTER
        cell80.addElement(etp4)
        cell80.horizontalAlignment = Element.ALIGN_CENTER
        cell80.verticalAlignment = Element.ALIGN_MIDDLE
        cell80.rowspan = 2
        header11.addCell(cell80)

        //Firma4
        val cell81 =
            PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
        cell81.horizontalAlignment = Element.ALIGN_CENTER
        cell81.verticalAlignment = Element.ALIGN_MIDDLE
        cell81.setPadding(8f)
        cell81.rowspan = 2
        header11.addCell(cell81)

        val cell82 =
            PdfPCell()
        val crg4 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        crg4.add(vm.crgvm4.value ?: " ")
        crg4.spacingAfter = 2.0f
        crg4.alignment = Element.ALIGN_CENTER
        cell82.addElement(crg4)
        cell82.horizontalAlignment = Element.ALIGN_CENTER
        cell82.verticalAlignment = Element.ALIGN_MIDDLE
        cell82.rowspan = 1
        header11.addCell(cell82)
        document.add(header11)

        val header12 = PdfPTable(4)
        header12.widthPercentage = 100F
        header12.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
        val cell83 =
            PdfPCell()
        val nom5 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        nom5.add(vm.servpubvm5.value ?: " ")
        nom5.spacingAfter = 2.0f
        nom5.alignment = Element.ALIGN_CENTER
        cell83.addElement(nom5)
        cell83.horizontalAlignment = Element.ALIGN_CENTER
        cell83.verticalAlignment = Element.ALIGN_MIDDLE
        cell83.rowspan = 2
        header12.addCell(cell83)

        val cell84 =
            PdfPCell()
        val insti5 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        insti5.add(vm.instvm5.value ?: " ")
        insti5.spacingAfter = 2.0f
        insti5.alignment = Element.ALIGN_CENTER
        cell84.addElement(insti5)
        cell84.setPadding(5f)
        cell84.horizontalAlignment = Element.ALIGN_CENTER
        cell84.verticalAlignment = Element.ALIGN_MIDDLE
        cell84.rowspan = 1
        header12.addCell(cell84)
        val cell85 =
            PdfPCell()
        val etp5 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        etp5.add(vm.etpvm5.value ?: " ")
        etp5.spacingAfter = 2.0f
        etp5.alignment = Element.ALIGN_CENTER
        cell85.addElement(etp5)
        cell85.horizontalAlignment = Element.ALIGN_CENTER
        cell85.verticalAlignment = Element.ALIGN_MIDDLE
        cell85.rowspan = 2
        header12.addCell(cell85)
        val cell86 =
            PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
        cell86.horizontalAlignment = Element.ALIGN_CENTER
        cell86.verticalAlignment = Element.ALIGN_MIDDLE
        cell86.setPadding(6f)
        cell86.rowspan = 2
        header12.addCell(cell86)

        val cell87 =
            PdfPCell()
        val crg5 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        crg5.add(vm.crgvm5.value ?: " ")
        crg5.spacingAfter = 1.0f
        crg5.alignment = Element.ALIGN_CENTER
        cell87.addElement(crg5)
        cell87.horizontalAlignment = Element.ALIGN_CENTER
        cell87.verticalAlignment = Element.ALIGN_MIDDLE
        cell87.rowspan = 1
        header12.addCell(cell87)
        document.add(header12)

        val header13 = PdfPTable(4)
        header13.widthPercentage = 100F
        header13.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
        val cell88 =
            PdfPCell()
        val nom6 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        nom6.add(vm.servpubvm6.value ?: " ")
        nom6.spacingAfter = 2.0f
        nom6.alignment = Element.ALIGN_CENTER
        cell88.addElement(nom6)
        cell88.horizontalAlignment = Element.ALIGN_CENTER
        cell88.verticalAlignment = Element.ALIGN_MIDDLE
        cell88.rowspan = 2
        header13.addCell(cell88)

        val cell89 =
            PdfPCell()
        val insti6 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        insti6.add(vm.instvm6.value ?: " ")
        insti6.spacingAfter = 2.0f
        insti6.alignment = Element.ALIGN_CENTER
        cell89.addElement(insti6)
        cell89.setPadding(5f)
        cell89.horizontalAlignment = Element.ALIGN_CENTER
        cell89.verticalAlignment = Element.ALIGN_MIDDLE
        cell89.rowspan = 1
        header13.addCell(cell89)

        val cell90 =
            PdfPCell()
        val etp6 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        etp6.add(vm.etpvm6.value ?: " ")
        etp6.spacingAfter = 2.0f
        etp6.alignment = Element.ALIGN_CENTER
        cell90.addElement(etp6)
        cell90.horizontalAlignment = Element.ALIGN_CENTER
        cell90.verticalAlignment = Element.ALIGN_MIDDLE
        cell90.rowspan = 2
        header13.addCell(cell90)

        val cell91 =
            PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
        cell91.horizontalAlignment = Element.ALIGN_CENTER
        cell91.verticalAlignment = Element.ALIGN_MIDDLE
        cell91.setPadding(8f)
        cell91.rowspan = 2
        header13.addCell(cell91)

        val cell92 =
            PdfPCell()
        val crg6 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        crg6.add(vm.crgvm6.value ?: " ")
        crg6.spacingAfter = 2.0f
        crg6.alignment = Element.ALIGN_CENTER
        cell92.addElement(crg6)
        cell92.horizontalAlignment = Element.ALIGN_CENTER
        cell92.verticalAlignment = Element.ALIGN_MIDDLE
        cell92.rowspan = 1
        header13.addCell(cell92)
        document.add(header13)

        val header14 = PdfPTable(4)
        header14.widthPercentage = 100F
        header14.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
        val cell93 =
            PdfPCell()
        val nom7 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        nom7.add(vm.servpubvm7.value ?: " ")
        nom7.spacingAfter = 2.0f
        nom7.alignment = Element.ALIGN_CENTER
        cell93.addElement(nom7)
        cell93.horizontalAlignment = Element.ALIGN_CENTER
        cell93.verticalAlignment = Element.ALIGN_MIDDLE
        cell93.rowspan = 2
        header14.addCell(cell93)

        val cell94 =
            PdfPCell()
        val insti7 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        insti7.add(vm.instvm7.value ?: " ")
        insti7.spacingAfter = 2.0f
        insti7.alignment = Element.ALIGN_CENTER
        cell94.addElement(insti7)
        cell94.setPadding(5f)
        cell94.horizontalAlignment = Element.ALIGN_CENTER
        cell94.verticalAlignment = Element.ALIGN_MIDDLE
        cell94.rowspan = 1
        header14.addCell(cell94)

        val cell95 =
            PdfPCell()
        val etp7 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        etp7.add(vm.etpvm7.value ?: " ")
        etp7.spacingAfter = 2.0f
        etp7.alignment = Element.ALIGN_CENTER
        cell95.addElement(etp7)
        cell95.horizontalAlignment = Element.ALIGN_CENTER
        cell95.verticalAlignment = Element.ALIGN_MIDDLE
        cell95.rowspan = 2
        header14.addCell(cell95)

        val cell96 =
            PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
        cell96.horizontalAlignment = Element.ALIGN_CENTER
        cell96.verticalAlignment = Element.ALIGN_MIDDLE
        cell96.setPadding(8f)
        cell96.rowspan = 2
        header14.addCell(cell96)

        val cell97 =
            PdfPCell()
        val crg7 =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        crg7.add(vm.crgvm7.value ?: " ")
        crg7.spacingAfter = 2.0f
        crg7.alignment = Element.ALIGN_CENTER
        cell97.addElement(crg7)
        cell97.horizontalAlignment = Element.ALIGN_CENTER
        cell97.verticalAlignment = Element.ALIGN_MIDDLE
        cell97.rowspan = 1
        header14.addCell(cell97)
        document.add(header14)

        addMediaEmptyLine(document, 1)

    }

    private fun addTra(context: Context, document: Document,  vm: ViewModel){
        val instrucciones7 = PdfPTable(2)
        instrucciones7.horizontalAlignment = Element.ALIGN_LEFT
        instrucciones7.widthPercentage = 100F
        instrucciones7.setWidths(floatArrayOf(1.25f, 7f))
        val cell99 = PdfPCell(
            Phrase(
                "6.    Traslado.",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
            )
        )
        cell99.isUseAscender = true
        cell99.border = PdfPCell.NO_BORDER
        cell99.setPadding(3f)
        cell99.horizontalAlignment = Element.ALIGN_RIGHT
        cell99.verticalAlignment = Element.ALIGN_MIDDLE
        instrucciones7.addCell(cell99)
        val cell100 = PdfPCell(
            Phrase(
                "  (Marque con “X” la vía empleada. En caso de ser necesaria alguna condición especial para la conservación o\n",
                Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell100.isUseAscender = true
        cell100.border = PdfPCell.NO_BORDER
        cell100.setPadding(3f)
        cell100.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
        cell100.verticalAlignment = Element.ALIGN_MIDDLE
        instrucciones7.addCell(cell100)
        val cell101 = PdfPCell()
        val temp7 = Paragraph(
            "preservación  de  un  indicio  o  elemento  material  probatorio  en  particular,  el  personal  pericial  o  policial  con  capacidades  para  el \n" +
                    "procesar, según sea el caso, deberá recomendarla). \n",
            Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
        )
        temp7.alignment = Element.ALIGN_LEFT
        temp7.indentationLeft = 22.5f
        cell101.addElement(temp7)
        cell101.border = PdfPCell.NO_BORDER
        cell101.isUseAscender = true
        cell101.colspan = 2
        cell101.horizontalAlignment = Element.ALIGN_TOP
        cell101.verticalAlignment = Element.ALIGN_TOP
        instrucciones7.addCell(cell101)
        document.add(instrucciones7)

        val outerTable1 = PdfPTable(1)
        outerTable1.widthPercentage = 100F
        outerTable1.horizontalAlignment = Element.ALIGN_LEFT
        val innerTable1 = PdfPTable(8)
        innerTable1.widthPercentage = 100F
        innerTable1.spacingAfter = 4.0f
        innerTable1.setWidths(floatArrayOf(1.6f, 2.0f, 0.8f, 3.0f, 0.8f, 3.0f, 0.8f, 0.5f))
        val cell102 = PdfPCell(
            Phrase(
                "a) Vía:",
                Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell102.horizontalAlignment = Element.ALIGN_CENTER
        cell102.verticalAlignment = Element.ALIGN_MIDDLE
        cell102.paddingLeft = 8.0f
        cell102.border = PdfPCell.NO_BORDER
        cell102.isUseAscender = false
        innerTable1.addCell(cell102)
        val cell103 = PdfPCell(
            Phrase(
                "Terrestre  ",
                Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell103.horizontalAlignment = Element.ALIGN_RIGHT
        cell103.verticalAlignment = Element.ALIGN_MIDDLE
        cell103.paddingRight = 10.0f
        cell103.border = PdfPCell.NO_BORDER
        innerTable1.addCell(cell103)

        val cell104 =
            PdfPCell()
        val terr =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        terr.add(vm.terrestrevm.value ?: " ")
        terr.spacingAfter = 2.0f
        terr.alignment = Element.ALIGN_CENTER
        cell104.addElement(terr)
        cell104.horizontalAlignment = Element.ALIGN_CENTER
        cell104.verticalAlignment = Element.ALIGN_MIDDLE
        cell104.borderWidth = 1F
        innerTable1.addCell(cell104)

        val cell105 = PdfPCell(
            Phrase(
                "Aérea  ",
                Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell105.horizontalAlignment = Element.ALIGN_RIGHT
        cell105.paddingRight = 8.0f
        cell105.verticalAlignment = Element.ALIGN_MIDDLE
        cell105.border = PdfPCell.NO_BORDER
        innerTable1.addCell(cell105)

        val cell106 =
            PdfPCell()
        val aer =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        aer.add(vm.aereavm.value ?: " ")
        aer.spacingAfter = 2.0f
        aer.alignment = Element.ALIGN_CENTER
        cell106.addElement(aer)
        cell106.horizontalAlignment = Element.ALIGN_LEFT
        cell106.verticalAlignment = Element.ALIGN_LEFT
        cell106.borderWidth = 1F
        innerTable1.addCell(cell106)

        val cell107 = PdfPCell(
            Phrase(
                "Marítima  ",
                Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell107.horizontalAlignment = Element.ALIGN_RIGHT
        cell107.verticalAlignment = Element.ALIGN_MIDDLE
        cell107.paddingRight = 10.0f
        cell107.border = PdfPCell.NO_BORDER
        innerTable1.addCell(cell107)

        val cell108 =
            PdfPCell()
        val mar =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        mar.add(vm.maritimavm.value ?: " ")
        mar.spacingAfter = 2.0f
        mar.alignment = Element.ALIGN_CENTER
        cell108.addElement(mar)
        cell108.horizontalAlignment = Element.ALIGN_LEFT
        cell108.verticalAlignment = Element.ALIGN_LEFT
        cell108.borderWidth = 1F
        innerTable1.addCell(cell108)

        val cell109 = PdfPCell()
        cell109.horizontalAlignment = Element.ALIGN_LEFT
        cell109.verticalAlignment = Element.ALIGN_LEFT
        cell109.border = PdfPCell.NO_BORDER
        innerTable1.addCell(cell109)

        val secondRow1 = PdfPTable(6)
        secondRow1.widthPercentage = 100F
        secondRow1.spacingAfter = 4.0f
        secondRow1.horizontalAlignment = Element.ALIGN_LEFT
        secondRow1.setWidths(floatArrayOf(7.0f, 0.85f, 0.85f, 3.2f, 0.85f, 0.5f))
        val cell110 = PdfPCell(
            Phrase(
                "b) Se requieren condiciones especiales para su traslado:",
                Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell110.horizontalAlignment = Element.ALIGN_RIGHT
        cell110.verticalAlignment = Element.ALIGN_MIDDLE
        cell110.paddingRight = 2.0f
        cell110.border = PdfPCell.NO_BORDER
        secondRow1.addCell(cell110)
        val cell111 =
            PdfPCell(Phrase("No ", Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)))
        cell111.horizontalAlignment = Element.ALIGN_CENTER
        cell111.verticalAlignment = Element.ALIGN_MIDDLE
        cell111.border = PdfPCell.NO_BORDER
        secondRow1.addCell(cell111)

        val cell112 =
            PdfPCell()
        val conNo =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        conNo.add(vm.condNovm.value ?: " ")
        conNo.spacingAfter = 2.0f
        conNo.alignment = Element.ALIGN_CENTER
        cell112.addElement(conNo)
        cell112.horizontalAlignment = Element.ALIGN_LEFT
        cell112.verticalAlignment = Element.ALIGN_LEFT
        cell112.borderWidth = 1F
        secondRow1.addCell(cell112)

        val cell113 =
            PdfPCell(Phrase("Sí ", Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)))
        cell113.horizontalAlignment = Element.ALIGN_RIGHT
        cell113.verticalAlignment = Element.ALIGN_MIDDLE
        cell113.paddingRight = 8.5f
        cell113.border = PdfPCell.NO_BORDER
        secondRow1.addCell(cell113)

        val cell114 =
            PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)))
        val conSi =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        conSi.add(vm.condSivm.value ?: " ")
        conSi.spacingAfter = 2.0f
        conSi.alignment = Element.ALIGN_CENTER
        cell114.addElement(conSi)
        cell114.horizontalAlignment = Element.ALIGN_LEFT
        cell114.verticalAlignment = Element.ALIGN_LEFT
        cell114.borderWidth = 1F
        secondRow1.addCell(cell114)

        val cell115 = PdfPCell()
        cell115.horizontalAlignment = Element.ALIGN_LEFT
        cell115.verticalAlignment = Element.ALIGN_LEFT
        cell115.border = PdfPCell.NO_BORDER
        secondRow1.addCell(cell115)

        val cell116 = PdfPCell()
        cell116.setPadding(5F)
        cell116.addElement(innerTable1)
        cell116.addElement(secondRow1)
        outerTable1.addCell(cell116)
        document.add(outerTable1)
        val outerTable2 = PdfPTable(1)
        outerTable2.widthPercentage = 100F
        outerTable2.horizontalAlignment = Element.ALIGN_LEFT
        val instrucciones8 = PdfPTable(1)
        instrucciones8.horizontalAlignment = Element.ALIGN_LEFT
        instrucciones8.widthPercentage = 100F
        val cell117 = PdfPCell(
            Phrase(
                "Recomendaciones: ",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
            )
        )
        cell117.isUseAscender = true
        cell117.border = PdfPCell.NO_BORDER
        cell117.horizontalAlignment = Element.ALIGN_LEFT
        cell117.verticalAlignment = Element.ALIGN_TOP
        instrucciones8.addCell(cell117)
        val cell118 = PdfPCell()
        val recom =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        recom.add(vm.recomendacionvm.value ?: " ")
        recom.spacingAfter = 2.0f
        recom.alignment = Element.ALIGN_LEFT
        recom.indentationLeft = 20f
        cell118.addElement(recom)

        cell118.border = PdfPCell.NO_BORDER
        cell118.setPadding(6F)
        cell118.isUseAscender = true
        cell118.horizontalAlignment = Element.ALIGN_TOP
        cell118.verticalAlignment = Element.ALIGN_TOP
        instrucciones8.addCell(cell118)
        val cell119 = PdfPCell()
        cell119.setPadding(1F)
        cell119.addElement(instrucciones8)
        outerTable2.addCell(cell119)
        document.add(outerTable2)
        addEmptyLine(document, 2)



        document.newPage()

    }

    private fun addTraZa(context: Context, document: Document, vm: ViewModel) {


        addEnc(context, document, vm)



        val instrucciones9: PdfPTable = PdfPTable(2)
        instrucciones9.horizontalAlignment = Element.ALIGN_LEFT
        instrucciones9.widthPercentage = 100F
        instrucciones9.setWidths(floatArrayOf(4.2f, 8.6f))

        val fontTitulo = Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD, BaseColor.BLACK)
        val fontTexto1 = Font(Font.FontFamily.HELVETICA, 7.3f, Font.NORMAL, BaseColor.BLACK)
        val fontTexto2 = Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)


// 🔹 TÍTULO IZQUIERDA
        val cell122 = PdfPCell(
            Phrase("7.   Continuidad y trazabilidad.", fontTitulo)
        ).apply {
            isUseAscender = true
            border = PdfPCell.NO_BORDER
            setPadding(3f)
            horizontalAlignment = Element.ALIGN_RIGHT
            verticalAlignment = Element.ALIGN_MIDDLE
        }
        instrucciones9.addCell(cell122)


// 🔹 PRIMER TEXTO (AHORA SÍ JUSTIFICADO)
        val p1 = Paragraph(
            "Fecha y hora de la entrega-recepción, nombre completo de quien entrega y de quien recibe los",
            fontTexto1
        ).apply {
            alignment = Element.ALIGN_RIGHT
            leading = 9f
        }

        val cell123 = PdfPCell().apply {
            addElement(p1)
            border = PdfPCell.NO_BORDER
            setPadding(1f)
        }
        instrucciones9.addCell(cell123)


// 🔹 TEXTO LARGO INFERIOR (JUSTIFICADO)
        val p2 = Paragraph(
            " indicios o elementos materiales probatorios en los cambios de custodia que realicen, institución a la que pertenecen, cargo o identificación dentro de la misma, propósito de la transferencia, firmas autógrafas y lugar de permanencia en la actividad respectiva. " +
                    "Anote las observaciones relacionadas con el embalaje, el indicio o elementos material probatorio o cualquier otra que considere necesario realizar. " +
                    "Agregue cuantas hojas sean necesarias. Cancele los espacios sobrantes después de que se haya cumplido con el destino final del indicio o elemento material probatorio.",
            fontTexto2
        ).apply {
            alignment = Element.ALIGN_JUSTIFIED
            leading = 9f
            indentationLeft = 22.5f
        }

        val cell124 = PdfPCell().apply {
            addElement(p2)
            border = PdfPCell.NO_BORDER
            colspan = 2
            setPadding(1f)
        }
        instrucciones9.addCell(cell124)

        document.add(instrucciones9)

        addMediaEmptyLine(document, 1)

        agregarBloqueCustodia(document)
        agregarBloqueCustodia(document)
        agregarBloqueCustodia(document)
        agregarBloqueCustodia(document)

        addMediaEmptyLine(document, 1)

        val p3 = Paragraph(
            " Se anexa registro de trazabilidad        Si [  ]     No   [  ]",
            Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL, BaseColor.BLACK)
        ).apply {
            alignment = Element.ALIGN_LEFT
            leading = 9f
            indentationLeft = 13f
        }

        document.add(p3)

    }

    private fun getPdfImage(context: Context, drawableId: Int): Image {
        imageCache[drawableId]?.let { return it }

        val drawable = ContextCompat.getDrawable(context, drawableId)!!
        val bitmap = if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            val bmp = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bmp)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bmp
        }

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        val image = Image.getInstance(stream.toByteArray())
        imageCache[drawableId] = image
        return image
    }

    fun createCell(content: String?, colspan: Int, rowspan: Int): PdfPCell {
        val cell = PdfPCell(Phrase(content, FONT_CELL))
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.isUseAscender = true
        cell.backgroundColor = Verde
        cell.setPadding(6f)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        return cell
    }

    private fun addEmptyLine(document: Document, number: Int) {
        for (i in 0 until number) {
            document.add(Paragraph(" "))
        }
    }

    private fun addMediaEmptyLine(document: Document, number: Int) {
        for (i in 0 until number) {
            document.add(
                Paragraph(
                    " ",
                    Font(Font.FontFamily.HELVETICA, 6.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
        }
    }

    private fun agregarBloqueCustodia(document: Document) {

        val header15: PdfPTable = PdfPTable(4)
        header15.widthPercentage = 100F
        header15.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
        header15.addCell(createCell("Fecha y hora de \nentrega recepción", 1, 1))
        header15.addCell(createCell("Nombre, institución y cargo o identificación de \nquien entrega", 1, 1))
        header15.addCell(createCell("Actividad/propósito", 1, 1))
        header15.addCell(createCell("Firma", 1, 1))

        val header16: PdfPTable = PdfPTable(4)
        header16.widthPercentage = 100F
        header16.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))

        val font = Font(Font.FontFamily.HELVETICA, 7f, Font.BOLD, BaseColor.BLACK)

        fun emptyCell(rowspan: Int) = PdfPCell(Phrase(" ", font)).apply {
            this.rowspan = rowspan
            borderWidth = 0.5f
            minimumHeight = 12f
        }

        header16.addCell(emptyCell(2))
        header16.addCell(emptyCell(1))
        header16.addCell(emptyCell(1))
        header16.addCell(emptyCell(2))
        header16.addCell(emptyCell(1))
        header16.addCell(emptyCell(1))

        document.add(header15)
        document.add(header16)

        val header17: PdfPTable = PdfPTable(4)
        header17.widthPercentage = 100F
        header17.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
        header17.addCell(createCell("Lugar de \npermanencia", 1, 1))
        header17.addCell(createCell("Nombre, institución y cargo o identificación de \nquien recibe", 1, 1))
        header17.addCell(createCell("Actividad/propósito", 1, 1))
        header17.addCell(createCell("Firma", 1, 1))

        val header18: PdfPTable = PdfPTable(4)
        header18.widthPercentage = 100F
        header18.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))

        header18.addCell(emptyCell(2))
        header18.addCell(emptyCell(1))
        header18.addCell(emptyCell(1))
        header18.addCell(emptyCell(2))
        header18.addCell(emptyCell(1))
        header18.addCell(emptyCell(1))

        document.add(header17)
        document.add(header18)

        val header19: PdfPTable = PdfPTable(1)
        header19.widthPercentage = 100F
        header19.addCell(createCell("Observaciones", 1, 1))
        header19.addCell(PdfPCell(Phrase(" ", font)).apply { minimumHeight = 10f })
        header19.addCell(PdfPCell(Phrase(" ", font)).apply { minimumHeight = 10f })

        document.add(header19)



    }

    private fun addEnc(  context: Context, document: Document, vm: ViewModel ){

        val table = PdfPTable(1).apply {
            widthPercentage = 100f
            defaultCell.border = PdfPCell.NO_BORDER
            defaultCell.verticalAlignment = Element.ALIGN_RIGHT
            defaultCell.horizontalAlignment = Element.ALIGN_TOP
        }

        val logoCadena = getPdfImage(context, R.drawable.cadena).apply {
            scaleToFit(140f, 60f)
            alignment = Element.ALIGN_RIGHT
        }

        val cellLogo = PdfPCell(logoCadena).apply {
            horizontalAlignment = Element.ALIGN_RIGHT
            verticalAlignment = Element.ALIGN_TOP
            border = PdfPCell.NO_BORDER
            paddingTop = 0f
            paddingBottom = 0f   // 👈 CLAVE
            paddingLeft = 0f
            paddingRight = 0f
        }

        table.addCell(cellLogo)
        table.spacingAfter = 0f
        document.add(table)

        addMediaEmptyLine(document, 1)

        val table2: PdfPTable = PdfPTable(2)
        table2.widthPercentage = 100F
        table2.spacingBefore = 0f
        table2.setWidths(floatArrayOf(4f, 2.3f))
        table2.defaultCell.border = PdfPCell.NO_BORDER
        table2.defaultCell.verticalAlignment = Element.ALIGN_RIGHT
        table2.defaultCell.horizontalAlignment = Element.ALIGN_RIGHT
        val cell53: PdfPCell =
            PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
        cell53.horizontalAlignment = Element.ALIGN_LEFT
        cell53.verticalAlignment = Element.ALIGN_LEFT
        cell53.border = PdfPCell.NO_BORDER
        cell53.rowspan = 2
        table2.addCell(cell53)
        val cell51: PdfPCell = PdfPCell()
        cell51.horizontalAlignment = Element.ALIGN_RIGHT
        cell51.verticalAlignment = Element.ALIGN_TOP
        cell51.isUseAscender = true
        cell51.rowspan = 1
        cell51.backgroundColor = Verde
        cell51.borderWidth = 0.7f
        //cell.setBorder(PdfPCell.NO_BORDER);
        cell51.setPadding(1f)
        val temp4: Paragraph = Paragraph(
            "No. de referencia",
            Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.WHITE)
        )
        temp4.alignment = Element.ALIGN_CENTER
        cell51.addElement(temp4)
        table2.addCell(cell51)
        val cell52: PdfPCell = PdfPCell()
        cell52.horizontalAlignment = Element.ALIGN_RIGHT
        cell52.verticalAlignment = Element.ALIGN_TOP
        cell52.isUseAscender = true
        cell52.backgroundColor = BaseColor.WHITE
        cell52.borderWidth = 0.7f
        cell52.rowspan = 1
        cell52.setPadding(1f)
        val titulo_para1: Paragraph =
            Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
        titulo_para1.add(vm.referenciavm.value?:" ")
        titulo_para1.spacingAfter = 1.0f
        titulo_para1.alignment = Element.ALIGN_CENTER
        cell52.addElement(titulo_para1)
        table2.addCell(cell52)
        document.add(table2)

        addMediaEmptyLine(document, 1)

    }
}