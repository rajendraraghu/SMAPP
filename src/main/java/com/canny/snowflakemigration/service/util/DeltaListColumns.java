package com.canny.snowflakemigration.service.util;

import com.canny.snowflakemigration.service.dto.DeltaProcessDTO;

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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DeltaListColumns {
    public static String[] listFileColumns(DeltaProcessDTO DeltaProcessDTO, String fileName) throws IOException {
        String url = DeltaProcessDTO.getSourceConnectionUrl();
        String filepath = url + '/' + fileName;
        @SuppressWarnings("rawtypes")
        ArrayList headercols = new ArrayList();
        String[] colNames = null;
        if (filepath.contains("xls")) {
            File inputFile = new File(filepath);
            InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
            Workbook wBook = WorkbookFactory.create(in);
            Sheet sheet = wBook.getSheetAt(0);
            Row row;
            Cell cell;
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    String cellvalue = cell.toString();
                    if (cellvalue.contains(",")) {
                        cellvalue = cellvalue.replace(",", "");
                        headercols.add(cellvalue);
                    } else if (cellvalue.contains("-")) {
                        cellvalue = cellvalue.replace("-", "");
                        headercols.add(cellvalue);
                    } else if (cellvalue.contains(" ")) {
                        cellvalue = cellvalue.replace(" ", "");
                        headercols.add(cellvalue);
                    } else {
                        headercols.add(cellvalue);
                    }
                }
            } else {
                headercols.add("NoColsAvailable");
            }
            colNames = (String[]) headercols.toArray(new String[headercols.size()]);
        } else if (filepath.contains("csv")) {
            FileReader filereader = new FileReader(filepath);
            BufferedReader reader = new BufferedReader(filereader);
            String line = null;
            line = reader.readLine();
            // system.out.print(line);
            if (line != null) {
                colNames = line.split(",");
                System.out.print(colNames);
                // int cols = colNames.length;
                for (int i = 0; i < colNames.length; i++) {
                    if (colNames[i].contains("-")) {
                        colNames[i] = colNames[i].replace("-", "");
                    } else if (colNames[i].contains(" ")) {
                        colNames[i] = colNames[i].replace(" ", "");
                    } else if (colNames[i].contains(" ")) {
                        colNames[i] = colNames[i].replace("_", "");
                    } else {
                        colNames[i] = colNames[i];
                    }
                }
            }
        }
        return colNames;
    }
}