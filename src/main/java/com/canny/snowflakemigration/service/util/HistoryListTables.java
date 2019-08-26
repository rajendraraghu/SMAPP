package com.canny.snowflakemigration.service.util;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.canny.snowflakemigration.service.dto.SnowHistoryDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class HistoryListTables {
    public static String historyListTable(SnowHistoryDTO snowHistoryDTO) throws SQLException,ClassNotFoundException,NullPointerException {
        Properties properties0 = new Properties();
		properties0.put("user", snowHistoryDTO.getSourceConnectionUsername());
		properties0.put("password", snowHistoryDTO.getSourceConnectionPassword());
		properties0.put("db",snowHistoryDTO.getSourceConnectionDatabase());
	    properties0.put("schema",snowHistoryDTO.getSourceConnectionSchema());	
	    Connection con = DriverManager.getConnection(snowHistoryDTO.getSourceConnectionUrl(),properties0);
        Statement stmt0 = con.createStatement();
        Statement stmt1 = con.createStatement();
        Statement stmt2 = con.createStatement();
        JsonObject jsonResponse = new JsonObject(); 
        JsonArray data = new JsonArray();
        
        
  
        ResultSet rs1 = stmt0.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '"+snowHistoryDTO.getSourceConnectionSchema()+"';");
        while(rs1.next()) {
        	JsonObject row = new JsonObject();
        	row.addProperty("tableName",rs1.getString("TABLE_NAME"));
        	data.add(row);
        }
        jsonResponse.add("tableinfo",data);
        return jsonResponse.toString();
        
       }
    }     
