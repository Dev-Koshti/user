package com.example.User;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class PdfService {
    public static void main(String[] args) {
        try {
            PdfService pdfService = new PdfService();
            byte[] pdfBytes = pdfService.createMergedCenteredCellPdf();

            // Write the byte array to a file
            try (FileOutputStream fos = new FileOutputStream("output.pdf")) {
                fos.write(pdfBytes);
            }
            System.out.println("PDF created successfully: output.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] createMergedCenteredCellPdf() throws Exception {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        // Add first image
        Image img1 = Image.getInstance(getClass().getClassLoader().getResource("ic_Cashup_B4yZ20eH0sy3y2gD.png"));
        img1.scaleToFit(90, 90); // Scale the image to fit within a width and height
        img1.setAlignment(Image.ALIGN_CENTER); // Center align the image
        document.add(img1);

        // Add second image
        Image img2 = Image.getInstance(getClass().getClassLoader().getResource("ic_Cashup_B4yZ20eH0sy3y2gD.png"));
        img2.scaleToFit(20, 20); // Scale the image to fit within a width and height
        img2.setAlignment(Image.ALIGN_CENTER); // Center align the image
        document.add(img2);

        // Add some spacing before the table
        document.add(new Paragraph("\n\n"));

        // Create a table with 3 columns
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100); // Table width as a percentage of the page width

        // Create a merged cell
        PdfPCell mergedCell = new PdfPCell(new Paragraph("Centered Cell"));
        mergedCell.setColspan(3); // Merge 3 columns
        mergedCell.setHorizontalAlignment(Element.ALIGN_CENTER); // Center horizontally
        mergedCell.setVerticalAlignment(Element.ALIGN_MIDDLE);   // Center vertically
        mergedCell.setPadding(10); // Add padding for spacing

        table.addCell(mergedCell); // Add merged cell to the table

        // Add another row to the table
        table.addCell("Row 2, Col 1");
        table.addCell("Row 2, Col 2");
        table.addCell("Row 2, Col 3");

        document.add(table);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
