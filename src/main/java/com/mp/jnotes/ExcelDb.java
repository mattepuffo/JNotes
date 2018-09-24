package com.mp.jnotes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.io.FilenameUtils;

public class ExcelDb {

    private WritableCellFormat timesBold;
    private WritableCellFormat times;

    public void create(String inputFile, String[] header, List<Nota> list) throws IOException, WriteException {
        String ext = FilenameUtils.getExtension(inputFile);
        if (ext.isEmpty()) {
            inputFile += ".xls";
        }
        File file = new File(inputFile);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("it", "IT"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Note", 0);
        WritableSheet excelSheet = workbook.getSheet(0);

        createLabel(excelSheet, header);
        createContent(excelSheet, list);

        workbook.write();
        workbook.close();
    }

    private void createLabel(WritableSheet sheet, String[] header) throws WriteException {
        WritableFont timesFontBold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD);
        timesBold = new WritableCellFormat(timesFontBold);
        timesBold.setWrap(false);

        CellView cv = new CellView();
        cv.setFormat(timesBold);
        cv.setAutosize(true);

        for (int i = 0; i < header.length; i++) {
            addCaption(sheet, i, 0, header[i]);
        }
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s) throws WriteException {
        Label label = new Label(column, row, s, timesBold);
        sheet.addCell(label);
    }

    private void createContent(WritableSheet sheet, List<Nota> list) throws WriteException {
        WritableFont timesFont = new WritableFont(WritableFont.TIMES, 12);
        times = new WritableCellFormat(timesFont);
        times.setWrap(false);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setAutosize(true);

        int row = 1;
        for (Nota n : list) {
            sheet.addCell(new Label(0, row, n.getTitolo()));
            sheet.addCell(new Label(1, row, n.getTesto()));
            sheet.addCell(new Label(2, row, n.getGruppo()));
            sheet.addCell(new Label(3, row, n.getAggiunta()));
            sheet.addCell(new Label(4, row, n.getModifica()));
            row++;
        }
    }
}
