package com.canny.snowflakemigration.service.util;

import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;
import com.google.gson.JsonArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import static com.canny.snowflakemigration.service.util.PasswordProtector.decrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;

public class ListColumns {
    public static String[] listFileColumns(JsonObject conn_obj, String fileName) throws IOException {
        String url = conn_obj.get("url").getAsString();
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

    public static String listColumns(JsonObject conn_obj, String tableName) throws IOException, SQLException {
        ResultSet rs = null;
        JsonObject jsonResponse = new JsonObject();
        String system = conn_obj.get("system").getAsString();
        Properties properties0 = new Properties();
        properties0.put("user", conn_obj.get("username").getAsString());
        properties0.put("password", decrypt(conn_obj.get("password").getAsString()));
        properties0.put("db", conn_obj.get("database").getAsString());
        Connection con = DriverManager.getConnection(conn_obj.get("url").getAsString(), properties0);
        Statement stmt1 = con.createStatement();
        Statement stmt2 = con.createStatement();
        if (system.equals("MySQL")) {
            rs = stmt1.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"
                    + conn_obj.get("schema").getAsString() + "' AND TABLE_NAME = '" + tableName + "';");
        } else if (system.equals("SQLServer")) {
            rs = stmt1.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG = '"
                    + conn_obj.get("database").getAsString() + "' AND TABLE_NAME = '" + tableName + "';");
        } else if (system.equals("Netezza")) {
            rs = stmt1.executeQuery("SELECT COLUMN_NAME FROM _V_SYS_COLUMNS WHERE TABLE_SCHEMA = '"
                    + conn_obj.get("schema").getAsString() + "' AND TABLE_NAME  = '" + tableName + "';");
        } else if (system.equals("Teradata")) {
            rs = stmt1.executeQuery("SELECT TRIM(ColumnName) as COLUMN_NAME FROM DBC.ColumnsV WHERE DatabaseName = '"
                    + conn_obj.get("database").getAsString() + "' AND TableName = '" + tableName + "';");
        } else if (system.equals("Oracle")) {
            System.out.println("inside loop2 oracle if");
            rs = stmt1.executeQuery("SELECT Column_Name as COLUMN_NAME FROM  All_Tab_Columns WHERE  OWNER = '"
                    + conn_obj.get("schema").getAsString() + "' AND  Table_Name = '" + tableName + "'");
        }

        JsonArray cols = new JsonArray();
        while (rs.next()) {
            cols.add(new JsonPrimitive(rs.getString("COLUMN_NAME")));
        }
        jsonResponse.add("columnList", cols);

        // if(system.equals("MySQL")) {rs3 = stmt2.executeQuery("SELECT COLUMN_NAME FROM
        // INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA =
        // '"+conn_obj.get("schema").getAsString()+"' AND TABLE_NAME = '"+tableName+"'
        // AND DATA_TYPE IN ('timestamp','datetime');");}
        // else if(system.equals("SQLServer")) {rs3 = stmt2.executeQuery("SELECT
        // COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG =
        // '"+conn_obj.get("database").getAsString()+"' AND TABLE_NAME = '"+tableName+"'
        // AND DATA_TYPE IN ('timestamp','datetime');");}
        // else if(system.equals("Netezza")) {rs3 = stmt2.executeQuery("SELECT
        // COLUMN_NAME FROM _V_SYS_COLUMNS WHERE TABLE_SCHEMA =
        // '"+conn_obj.get("schema").getAsString()+"' AND TABLE_NAME = '"+tableName+"'
        // AND TYPE_NAME IN ('TIMESTAMP','DATE');");}
        // else if(system.equals("Teradata")) {rs3 = stmt2.executeQuery("SELECT
        // TRIM(ColumnName) as COLUMN_NAME FROM DBC.ColumnsV WHERE DatabaseName =
        // '"+conn_obj.get("database").getAsString()+"' AND TableName = '"+tableName+"'
        // AND COLUMNTYPE IN ('DA','TS','SZ');");}
        // else if(system.equals("Oracle")) {rs3 = stmt2.executeQuery("SELECT
        // Column_Name as COLUMN_NAME FROM All_Tab_Columns WHERE OWNER =
        // '"+conn_obj.get("schema").getAsString()+"' AND Table_Name = '"+tableName+"'
        // AND DATA_TYPE IN ('TIMESTAMP','DATE')");}

        // JsonArray cdccols = new JsonArray();
        // while(rs3.next() )
        // {
        // cdccols.add(new JsonPrimitive(rs3.getString("COLUMN_NAME")));
        // }
        // jsonResponse.add("cdcColumnList",cdccols);
        // jsonResponse.addProperty("selectedcdccol","");
        return jsonResponse.toString();
    }

    public static String listCdcColumns(JsonObject conn_obj, String tableName) throws IOException, SQLException {
        ResultSet rs = null;
        JsonObject jsonResponse = new JsonObject();
        String system = conn_obj.get("system").getAsString();
        Properties properties0 = new Properties();
        properties0.put("user", conn_obj.get("user").getAsString());
        properties0.put("password", decrypt(conn_obj.get("password").getAsString()));
        properties0.put("db", conn_obj.get("database").getAsString());
        Connection con = DriverManager.getConnection(conn_obj.get("url").getAsString(), properties0);
        Statement stmt = con.createStatement();
        if (system.equals("MySQL")) {
            rs = stmt.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"
                    + conn_obj.get("schema").getAsString() + "' AND TABLE_NAME = '" + tableName
                    + "' AND DATA_TYPE IN ('timestamp','datetime');");
        } else if (system.equals("SQLServer")) {
            rs = stmt.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG = '"
                    + conn_obj.get("database").getAsString() + "' AND TABLE_NAME = '" + tableName
                    + "' AND DATA_TYPE IN ('timestamp','datetime');");
        } else if (system.equals("Netezza")) {
            rs = stmt.executeQuery("SELECT COLUMN_NAME FROM _V_SYS_COLUMNS WHERE TABLE_SCHEMA = '"
                    + conn_obj.get("schema").getAsString() + "' AND TABLE_NAME  = '" + tableName
                    + "' AND TYPE_NAME IN ('TIMESTAMP','DATE');");
        } else if (system.equals("Teradata")) {
            rs = stmt.executeQuery("SELECT TRIM(ColumnName) as COLUMN_NAME FROM DBC.Columns V WHERE DatabaseName = '"
                    + conn_obj.get("database").getAsString() + "' AND TableName = '" + tableName
                    + "' AND COLUMNTYPE IN ('DA','TS','SZ');");
        } else if (system.equals("Oracle")) {
            rs = stmt.executeQuery("SELECT Column_Name as COLUMN_NAME FROM  All_Tab_Columns WHERE  OWNER = '"
                    + conn_obj.get("schema").getAsString() + "' AND  Table_Name = '" + tableName
                    + "' AND DATA_TYPE IN ('TIMESTAMP','DATE')");
        }

        JsonArray cdccols = new JsonArray();
        while (rs.next()) {
            cdccols.add(new JsonPrimitive(rs.getString("COLUMN_NAME")));
        }
        jsonResponse.add("cdcColumnList", cdccols);
        jsonResponse.addProperty("selectedcdccol", "");
        return jsonResponse.toString();
    }
}