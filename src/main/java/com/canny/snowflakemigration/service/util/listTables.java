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

public class listTables {
    public static String listTable(MigrationProcessDTO migrationProcessDTO) throws SQLException,ClassNotFoundException,NullPointerException {
       // Connection con = DriverManager.getConnection(migrationProcessDTO.getSourceConnectionUrl(), migrationProcessDTO.getSourceConnectionUsername(), migrationProcessDTO.getSourceConnectionPassword());
        //Statement stmt = con.createStatement();
        Properties properties0 = new Properties();
		properties0.put("user", migrationProcessDTO.getSourceConnectionUsername());
		properties0.put("password", migrationProcessDTO.getSourceConnectionPassword());
		properties0.put("db",migrationProcessDTO.getSourceConnectionDatabase());
	    properties0.put("schema",migrationProcessDTO.getSourceConnectionSchema());	
	    Connection con = DriverManager.getConnection(migrationProcessDTO.getSourceConnectionUrl(),properties0);
        Statement stmt0 = con.createStatement();
        Statement stmt1 = con.createStatement();
        Statement stmt2 = con.createStatement();
        JsonObject jsonResponse = new JsonObject(); 
        JsonArray data = new JsonArray();
        
  
        ResultSet rs1 = stmt0.executeQuery("SELECT a.TABLE_NAME,b.COLUMN_NAME as PrimaryKey FROM INFORMATION_SCHEMA.TABLES a JOIN INFORMATION_SCHEMA.COLUMNS b ON a.TABLE_NAME = b.TABLE_NAME AND b.COLUMN_KEY = 'PRI' AND a.TABLE_SCHEMA = b.TABLE_SCHEMA  WHERE a.TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionSchema()+"';");
        while(rs1.next()) {
        	JsonArray row = new JsonArray();
        	row.add(rs1.getString("TABLE_NAME"));
        	row.add(rs1.getString("PrimaryKey"));
        	ResultSet rs2 = stmt1.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND TABLE_NAME = '"+rs1.getString("TABLE_NAME")+"';");
        	JsonArray cols = new JsonArray();
        	while(rs2.next() ) 
    		{
    		  //JsonArray row = new JsonArray();
    		  cols.add(new JsonPrimitive(rs2.getString("COLUMN_NAME")));    		
            }
        	row.add(cols);
        	ResultSet rs3 = stmt2.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionSchema()+"' AND TABLE_NAME = '"+rs1.getString("TABLE_NAME")+"' AND DATA_TYPE IN ('timestamp','datetime');");
        	JsonArray cdccols = new JsonArray();
        	while(rs3.next() ) 
    		{
    		  //JsonArray row = new JsonArray();
    		  cdccols.add(new JsonPrimitive(rs3.getString("COLUMN_NAME")));    		
            }
        	row.add(cdccols);
        	data.add(row);
        }
        jsonResponse.add("tableinfo",data);
        return jsonResponse.toString();
        
       /* ArrayList tn = new ArrayList();
        while(rs1.next())
        {
        	String s1 = rs1.getString("TABLE_NAME");
        	s1 =s1.concat("|");
            tn.add(s1.concat(rs1.getString("PrimaryKey")));
        }
        String[] tableNames = (String[])tn.toArray(new String[tn.size()]);
        return tableNames;*/
    }
}
//SELECT * FROM _v_tables;