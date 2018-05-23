package com.bizsp.eaccount.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;


public class MakeExcelFile {
private static final String XML_ENCODING = "UTF-8";
    
    public static void main(String[] args) throws Exception {

        // Step 1. Create a template file. Setup sheets and workbook-level objects such as
        // cell styles, number formats, etc.

    	HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Big Grid");

        Map styles = createStyles(wb);
        //name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
        String sheetRef = sheet.getSheetName();

        //save the template
        FileOutputStream os = new FileOutputStream("template.xlsx");
        wb.write(os);
        os.close();

        //Step 2. Generate XML file.
        File tmp = File.createTempFile("sheet", ".xml");
        Writer fw = new OutputStreamWriter(new FileOutputStream(tmp), XML_ENCODING);
        generate(fw, styles, null);
        fw.close();

        //Step 3. Substitute the template entry with the generated data
        FileOutputStream out = new FileOutputStream("big-grid.xlsx");
        substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
        out.close();
    }

    /**
     * Create a library of cell styles.
     */
    private static Map createStyles(HSSFWorkbook wb){
        Map styles = new HashMap();
        HSSFDataFormat fmt = wb.createDataFormat();

        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style1.setDataFormat(fmt.getFormat("0.0%"));
        styles.put("percent", style1);

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setDataFormat(fmt.getFormat("0.0X"));
        styles.put("coeff", style2);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style3.setDataFormat(fmt.getFormat("$#,##0.00"));
        styles.put("currency", style3);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style4.setDataFormat(fmt.getFormat("mmm dd"));
        styles.put("date", style4);

        HSSFCellStyle style5 = wb.createCellStyle();
        HSSFFont headerFont = wb.createFont();
        headerFont.setBoldweight((short) 2);
        style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style5.setFont(headerFont);
        styles.put("header", style5);

        return styles;
    }

    private static void generate(Writer out, Map styles, Map data) throws Exception {

        Random rnd = new Random();
        Calendar calendar = Calendar.getInstance();

        SpreadsheetWriter sw = new SpreadsheetWriter(out);
        sw.beginSheet();

        //insert header row
        sw.insertRow(0);
        int styleIndex = ((HSSFCellStyle) styles.get("header")).getIndex();
        sw.createCell(0, "Title", styleIndex);
        sw.createCell(1, "% Change", styleIndex);
        sw.createCell(2, "Ratio", styleIndex);
        sw.createCell(3, "Expenses", styleIndex);
        sw.createCell(4, "Date", styleIndex);

        sw.endRow();

        //write data rows
        for (int rownum = 1; rownum < 100000; rownum++) {
            sw.insertRow(rownum);

            sw.createCell(0, "Hello, " + rownum + "!");
            sw.createCell(1, (double)rnd.nextInt(100)/100, ((HSSFCellStyle) styles.get("percent")).getIndex());
            sw.createCell(2, (double)rnd.nextInt(10)/10, ((HSSFCellStyle) styles.get("coeff")).getIndex());
            sw.createCell(3, rnd.nextInt(10000), ((HSSFCellStyle) styles.get("currency")).getIndex());
            sw.createCell(4, calendar, ((HSSFCellStyle) styles.get("date")).getIndex());

            sw.endRow();

            calendar.roll(Calendar.DAY_OF_YEAR, 1);
        }
        sw.endSheet();
    }

    /**
     *
     * @param zipfile the template file
     * @param tmpfile the XML file with the sheet data
     * @param entry the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
     * @param out the stream to write the result to
     */
	private static void substitute(File zipfile, File tmpfile, String entry, OutputStream out) throws IOException {
        ZipFile zip = new ZipFile(zipfile);

        ZipOutputStream zos = new ZipOutputStream(out);

        @SuppressWarnings("unchecked")
        Enumeration en = (Enumeration) zip.entries();
        while (en.hasMoreElements()) {
            ZipEntry ze = (ZipEntry) en.nextElement();
            if(!ze.getName().equals(entry)){
                zos.putNextEntry(new ZipEntry(ze.getName()));
                InputStream is = zip.getInputStream(ze);
                copyStream(is, zos);
                is.close();
            }
        }
        zos.putNextEntry(new ZipEntry(entry));
        InputStream is = new FileInputStream(tmpfile);
        copyStream(is, zos);
        is.close();

        zos.close();
    }

    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >=0 ) {
          out.write(chunk,0,count);
        }
    }

    /**
     * Writes spreadsheet data in a Writer.
     * (YK: in future it may evolve in a full-featured API for streaming data in Excel)
     */
    public static class SpreadsheetWriter {
        private final Writer _out;
        private int _rownum;

        public SpreadsheetWriter(Writer out){
            _out = out;
        }

        public void beginSheet() throws IOException {
            _out.write("" +
                    "" );
            _out.write("\n");
        }

        public void endSheet() throws IOException {
            _out.write("");
            _out.write("");
        }

        /**
         * Insert a new row
         *
         * @param rownum 0-based row number
         */
        public void insertRow(int rownum) throws IOException {
            _out.write("\n");
            this._rownum = rownum;
        }

        /**
         * Insert row end marker
         */
        public void endRow() throws IOException {
            _out.write("\n");
        }

        public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
            String ref = new CellReference(_rownum, columnIndex).formatAsString();
            _out.write("");
            _out.write(""+value+"");
            _out.write("");
        }

        public void createCell(int columnIndex, String value) throws IOException {
            createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
            String ref = new CellReference(_rownum, columnIndex).formatAsString();
            _out.write("");
            _out.write(""+value+"");
            _out.write("");
        }

        public void createCell(int columnIndex, double value) throws IOException {
            createCell(columnIndex, value, -1);
        }

        public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException {
            createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
        }
    }
}
