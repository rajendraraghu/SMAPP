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

public class listTables {
    public static String[] listTable(MigrationProcessDTO migrationProcessDTO) throws SQLException,ClassNotFoundException,NullPointerException {
       // Connection con = DriverManager.getConnection(migrationProcessDTO.getSourceConnectionUrl(), migrationProcessDTO.getSourceConnectionUsername(), migrationProcessDTO.getSourceConnectionPassword());
        //Statement stmt = con.createStatement();
        Properties properties0 = new Properties();
		properties0.put("user", migrationProcessDTO.getSourceConnectionUsername());
		properties0.put("password", migrationProcessDTO.getSourceConnectionPassword());
		properties0.put("db",migrationProcessDTO.getSourceConnectionDatabase());
	    properties0.put("schema",migrationProcessDTO.getSourceConnectionSchema());	
	    Connection con = DriverManager.getConnection(migrationProcessDTO.getSourceConnectionUrl(),properties0);
        Statement stmt = con.createStatement();
		 
  
        ResultSet rs1 = stmt.executeQuery("SELECT a.TABLE_NAME,b.COLUMN_NAME as PrimaryKey FROM INFORMATION_SCHEMA.TABLES a JOIN INFORMATION_SCHEMA.COLUMNS b ON a.TABLE_NAME = b.TABLE_NAME AND b.COLUMN_KEY = 'PRI' AND a.TABLE_SCHEMA = b.TABLE_SCHEMA  WHERE a.TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionSchema()+"';");
        ArrayList tn = new ArrayList();
        while(rs1.next())
        {
        	String s1 = rs1.getString("TABLE_NAME");
        	s1 =s1.concat("|");
            tn.add(s1.concat(rs1.getString("PrimaryKey")));
        }
        String[] tableNames = (String[])tn.toArray(new String[tn.size()]);
        return tableNames;
    }
}
//SELECT * FROM _v_tables;