package com.canny.snowflakemigration.service.util;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FilenameFilter;
import static com.canny.snowflakemigration.service.util.PasswordProtector.decrypt;

public class ListTables {
    public static String listTable(MigrationProcessDTO migrationProcessDTO) throws SQLException,ClassNotFoundException,NullPointerException {

        String system = migrationProcessDTO.getSourceType();
		System.out.println("system:"+system);
		JsonObject jsonResponse = new JsonObject(); 
        JsonArray data = new JsonArray();        
        String tablename;
        ResultSet rs1 = null;
		if(system.equals("Flatfiles"))
		{
			String filepath = migrationProcessDTO.getSourceConnectionUrl();
			File folder = new File(filepath);
            File[] listOfFiles = folder.listFiles(new FilenameFilter()
			{@Override
				public boolean accept(File folder, String name) 
				{
					if(name.toLowerCase().endsWith(".csv")){return true;}
					else if(name.toLowerCase().endsWith(".xls")){return true;}
					else if(name.toLowerCase().endsWith(".xlsx")){return true;}
					else {return false;}
				}
			}
			);
			for (int i = 0; i < listOfFiles.length; i++) 
			{               
		        JsonObject row = new JsonObject();
				row.addProperty("tableName",listOfFiles[i].getName());
				// row.addProperty("PrimaryKey","NoPrimaryKey");
				row.addProperty("PrimaryKey","");
				JsonArray cols = new JsonArray();
				cols.add(new JsonPrimitive("selectCol"));
                row.add("columnList",cols);
				JsonArray cdccols = new JsonArray();
				cdccols.add(new JsonPrimitive("NoCdcColumn")); 
				row.add("cdcColumnList",cdccols);
                row.addProperty("selectedcdccol","");
        	    data.add(row);
            }
		}
		else{
        Properties properties0 = new Properties();
		properties0.put("user", migrationProcessDTO.getSourceConnectionUsername());
		properties0.put("password", decrypt(migrationProcessDTO.getSourceConnectionPassword()));
		properties0.put("db",migrationProcessDTO.getSourceConnectionDatabase());
	    //properties0.put("schema",migrationProcessDTO.getSourceConnectionSchema());	
		//Connection con = DriverManager.getConnection("jdbc:SQLServer://localhost:1433;databaseName=DEMO_DB",properties0);
		Connection con = DriverManager.getConnection(migrationProcessDTO.getSourceConnectionUrl(),properties0);
        Statement stmt0 = con.createStatement();
        Statement stmt1 = con.createStatement();
        Statement stmt2 = con.createStatement();
		
		
        if(system.equals("MySQL")) {rs1 = stmt0.executeQuery("SELECT a.TABLE_NAME,group_concat(b.COLUMN_NAME SEPARATOR'-') as PrimaryKey FROM INFORMATION_SCHEMA.TABLES a LEFT JOIN INFORMATION_SCHEMA.COLUMNS b ON a.TABLE_NAME = b.TABLE_NAME AND b.COLUMN_KEY = 'PRI' AND a.TABLE_SCHEMA = b.TABLE_SCHEMA  WHERE a.TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionSchema()+"' GROUP BY a.TABLE_NAME;");}
        else if(system.equals("SQLServer")) {rs1 = stmt0.executeQuery("SELECT KU.table_name as TABLE_NAME,string_agg(column_name,'-') as PrimaryKey FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS TC INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KU ON TC.CONSTRAINT_TYPE = 'PRIMARY KEY' AND TC.CONSTRAINT_NAME = KU.CONSTRAINT_NAME AND TC.TABLE_CATALOG = '"+migrationProcessDTO.getSourceConnectionDatabase() +"'  GROUP BY KU.TABLE_NAME;");}
        else if(system.equals("Netezza")) {rs1 = stmt0.executeQuery("SELECT DISTINCT TableName as TABLE_NAME,'NoPrimaryKey' as PrimaryKey FROM DBC.ColumnsV WHERE DatabaseName = '"+migrationProcessDTO.getSourceConnectionSchema()+"';");}
        else if(system.equals("Teradata")) {rs1 = stmt0.executeQuery("SELECT DISTINCT TABLE_NAME, 'NoPrimaryKey' as PrimaryKey FROM _V_SYS_COLUMNS WHERE TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionSchema()+"';");}
        else if(system.equals("Oracle")) {System.out.println("inside first query loop"+migrationProcessDTO.getSourceConnectionSchema());
			rs1 = stmt0.executeQuery("SELECT cols.TABLE_NAME, LISTAGG(cols.column_name,'-') WITHIN GROUP (ORDER BY cols.column_name)as \"PrimaryKey\" FROM all_constraints cons, all_cons_columns cols WHERE cols.OWNER = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND cons.constraint_type = 'P' AND cons.constraint_name = cols.constraint_name AND cons.owner = cols.owner GROUP BY cols.TABLE_NAME");}
			//if (listOfFiles[i].isFile()) {
			//else if (listOfFiles[i].isDirectory()) {
                //System.out.println("Directory " + listOfFiles[i].getName());}
                
        while(rs1.next()) {
        	System.out.println("loop1");
			JsonObject row = new JsonObject();
        	row.addProperty("tableName",rs1.getString("TABLE_NAME"));
        	row.addProperty("PrimaryKey",rs1.getString("PrimaryKey"));
        	
        	ResultSet rs2 = null;
			if(system.equals("MySQL")) {rs2 = stmt1.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND TABLE_NAME = '"+rs1.getString("TABLE_NAME")+"';");}
            else if(system.equals("SQLServer")) {rs2 = stmt1.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG = '"+migrationProcessDTO.getSourceConnectionDatabase()+"' AND TABLE_NAME = '"+rs1.getString("TABLE_NAME")+"';");}
            else if(system.equals("Netezza")) {rs2 = stmt1.executeQuery("SELECT COLUMN_NAME FROM _V_SYS_COLUMNS WHERE TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND TABLE_NAME  = '"+rs1.getString("TABLE_NAME")+"';");}
            else if(system.equals("Teradata")) {rs2 = stmt1.executeQuery("SELECT  ColumnName as COLUMN_NAME FROM DBC.ColumnsV WHERE DatabaseName = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND TableName = '"+rs1.getString("TABLE_NAME")+"';");}
            else if(system.equals("Oracle")) {System.out.println("inside loop2 oracle if");
				rs2 = stmt1.executeQuery("SELECT Column_Name as COLUMN_NAME FROM  All_Tab_Columns WHERE  OWNER = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND  Table_Name = '"+rs1.getString("TABLE_NAME")+"'");}
            
        	
        	JsonArray cols = new JsonArray();
        	while(rs2.next() ) 
    		{
    		  cols.add(new JsonPrimitive(rs2.getString("COLUMN_NAME")));   
              System.out.println("loop2");			  
            }
        	row.add("columnList",cols);
        	
        	//ResultSet rs3 = stmt2.executeQuery("SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS");
			ResultSet rs3 = null;
			//stmt2.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS");
            if(system.equals("MySQL")) {rs3 = stmt2.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND TABLE_NAME = '"+rs1.getString("TABLE_NAME")+"' AND DATA_TYPE IN ('timestamp','datetime');");}
            else if(system.equals("SQLServer")) {rs3 = stmt2.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG = '"+migrationProcessDTO.getSourceConnectionDatabase()+"' AND TABLE_NAME = '"+rs1.getString("TABLE_NAME")+"' AND DATA_TYPE IN ('timestamp','datetime');");}
            else if(system.equals("Netezza")) {rs3 = stmt2.executeQuery("SELECT COLUMN_NAME FROM _V_SYS_COLUMNS WHERE TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND TABLE_NAME  = '"+rs1.getString("TABLE_NAME")+"' AND TYPE_NAME IN ('TIMESTAMP','DATE');");}
            else if(system.equals("Teradata")) {rs3 = stmt2.executeQuery("SELECT  ColumnName as COLUMN_NAME FROM DBC.ColumnsV WHERE DatabaseName = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND TableName = '"+rs1.getString("TABLE_NAME")+"' AND COLUMNTYPE IN ('DA','TS','SZ');");}
            else if(system.equals("Oracle")) {rs3 = stmt2.executeQuery("SELECT Column_Name as COLUMN_NAME FROM  All_Tab_Columns WHERE  OWNER = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND  Table_Name = '"+rs1.getString("TABLE_NAME")+"' AND DATA_TYPE IN ('TIMESTAMP','DATE')");}
    
        	JsonArray cdccols = new JsonArray();
        	while(rs3.next() ) 
    		{
			  System.out.println("loop3");	
    		  cdccols.add(new JsonPrimitive(rs3.getString("COLUMN_NAME")));    		
            }
        	row.add("cdcColumnList",cdccols);
            row.addProperty("selectedcdccol","");
        	data.add(row);
         }
	    }
        jsonResponse.add("tableinfo",data);
        return jsonResponse.toString();        
    }
    
		
		

}