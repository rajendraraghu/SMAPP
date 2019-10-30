package com.canny.snowflakemigration.service.util;

import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

public class ListColumns {
    public static String[] listFileColumns(MigrationProcessDTO migrationProcessDTO, String fileName) throws IOException  {
        String url = migrationProcessDTO.getSourceConnectionUrl();
        String filepath = url + '/' + fileName;
        @SuppressWarnings("rawtypes")
        ArrayList headercols = new ArrayList();
        String[] colNames = null;
        if (filepath.contains("xls")) {
            File inputFile = new File(filepath);
            InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
            XSSFWorkbook wBook = new XSSFWorkbook(in);
            XSSFSheet sheet = wBook.getSheetAt(0);
            Row row;
            Cell cell;
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    headercols.add(cell.toString());
                }
            } else {
                headercols.add("NoColsAvailable");
            }
            colNames = (String[]) headercols.toArray(new String[headercols.size()]);
        } else if (filepath.contains("csv")) {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line = null;
            line = reader.readLine();
            if (line != null) {
                colNames = line.split(",");
            }
        }
        return colNames;
    }
}