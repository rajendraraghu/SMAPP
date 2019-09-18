package com.canny.snowflakemigration.service.util;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.canny.snowflakemigration.service.dto.DeltaProcessDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DeltaListTables {
    public static String listTable(DeltaProcessDTO deltaProcessDTO) throws SQLException,ClassNotFoundException,NullPointerException {
       // Connection con = DriverManager.getConnection(deltaProcessDTO.getSourceConnectionUrl(), deltaProcessDTO.getSourceConnectionUsername(), deltaProcessDTO.getSourceConnectionPassword());
        //Statement stmt = con.createStatement();
        Properties properties0 = new Properties();
		properties0.put("user", deltaProcessDTO.getSourceConnectionUsername());
		properties0.put("password", deltaProcessDTO.getSourceConnectionPassword());
		properties0.put("db",deltaProcessDTO.getSourceConnectionDatabase());
	    properties0.put("schema",deltaProcessDTO.getSourceConnectionSchema());
	    Connection con = DriverManager.getConnection(deltaProcessDTO.getSourceConnectionUrl(),properties0);
        Statement stmt0 = con.createStatement();
        Statement stmt1 = con.createStatement();
        Statement stmt2 = con.createStatement();
        JsonObject jsonResponse = new JsonObject();
        JsonArray data = new JsonArray();
		
		ResultSet rs1 = null;
		String system = deltaProcessDTO.getSourceType();
		System.out.println("system:"+system);

        if(system.equals("MySQL")) {rs1 = stmt0.executeQuery("SELECT a.TABLE_NAME,group_concat(b.COLUMN_NAME SEPARATOR'-') as PrimaryKey FROM INFORMATION_SCHEMA.TABLES a LEFT JOIN INFORMATION_SCHEMA.COLUMNS b ON a.TABLE_NAME = b.TABLE_NAME AND b.COLUMN_KEY = 'PRI' AND a.TABLE_SCHEMA = b.TABLE_SCHEMA  WHERE a.TABLE_SCHEMA = '"+deltaProcessDTO.getSourceConnectionSchema()+"' GROUP BY a.TABLE_NAME;");}
        else if(system.equals("SQLServer")) {rs1 = stmt0.executeQuery("SELECT KU.table_name as TABLE_NAME,string_agg(column_name,'-') as PrimaryKey FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS TC INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KU ON TC.CONSTRAINT_TYPE = 'PRIMARY KEY' AND TC.CONSTRAINT_NAME = KU.CONSTRAINT_NAME AND TC.TABLE_CATALOG = '"+deltaProcessDTO.getSourceConnectionDatabase() +"'  GROUP BY KU.TABLE_NAME;");}
        else if(system.equals("Netezza")) {rs1 = stmt0.executeQuery("SELECT DISTINCT TableName as TABLE_NAME,'NoPrimaryKey' as PrimaryKey FROM DBC.ColumnsV WHERE DatabaseName = '"+deltaProcessDTO.getSourceConnectionSchema()+"';");}
        else if(system.equals("Teradata")) {rs1 = stmt0.executeQuery("SELECT DISTINCT TABLE_NAME, 'NoPrimaryKey' as PrimaryKey FROM _V_SYS_COLUMNS WHERE TABLE_SCHEMA = '"+deltaProcessDTO.getSourceConnectionSchema()+"';");}
        else if(system.equals("Oracle")) {
			System.out.println("inside first query loop"+deltaProcessDTO.getSourceConnectionSchema());

			rs1 = stmt0.executeQuery("SELECT cols.TABLE_NAME, LISTAGG(cols.column_name,'-') WITHIN GROUP (ORDER BY cols.column_name)as \"PrimaryKey\" FROM all_constraints cons, all_cons_columns cols WHERE cols.OWNER = '"+deltaProcessDTO.getSourceConnectionSchema()+"' AND cons.constraint_type = 'P' AND cons.constraint_name = cols.constraint_name AND cons.owner = cols.owner GROUP BY cols.TABLE_NAME");

			}

        /*ResultSet rs1 = stmt0.executeQuery("SELECT a.TABLE_NAME,group_concat(b.COLUMN_NAME SEPARATOR'-') as PrimaryKey FROM INFORMATION_SCHEMA.TABLES a LEFT JOIN INFORMATION_SCHEMA.COLUMNS b ON a.TABLE_NAME = b.TABLE_NAME AND b.COLUMN_KEY = 'PRI' AND a.TABLE_SCHEMA = b.TABLE_SCHEMA  WHERE a.TABLE_SCHEMA = '"+deltaProcessDTO.getSourceConnectionSchema()+"' GROUP BY a.TABLE_NAME;");*/
        while(rs1.next()) {
        	JsonObject row = new JsonObject();
        	//JsonElement element1 = new JsonElement();
            //JsonElement element2 = new JsonElement();
            //element1 = (JsonElement)rs1.getString("TABLE_NAME");
        	row.addProperty("tableName",rs1.getString("TABLE_NAME"));
        	row.addProperty("PrimaryKey",rs1.getString("PrimaryKey"));
			
			ResultSet rs2 = null;
			//stmt1.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS");
            if(system.equals("MySQL")) {rs2 = stmt1.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+deltaProcessDTO.getSourceConnectionSchema()+"' AND TABLE_NAME = '"+rs1.getString("TABLE_NAME")+"';");}
            else if(system.equals("SQLServer")) {rs2 = stmt1.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG = '"+deltaProcessDTO.getSourceConnectionDatabase()+"' AND TABLE_NAME = '"+rs1.getString("TABLE_NAME")+"';");}
            else if(system.equals("Netezza")) {rs2 = stmt1.executeQuery("SELECT COLUMN_NAME FROM _V_SYS_COLUMNS WHERE TABLE_SCHEMA = '"+deltaProcessDTO.getSourceConnectionSchema()+"' AND TABLE_NAME  = '"+rs1.getString("TABLE_NAME")+"';");}
            else if(system.equals("Teradata")) {rs2 = stmt1.executeQuery("SELECT  ColumnName as COLUMN_NAME FROM DBC.ColumnsV WHERE DatabaseName = '"+deltaProcessDTO.getSourceConnectionSchema()+"' AND TableName = '"+rs1.getString("TABLE_NAME")+"';");}
            else if(system.equals("Oracle")) {System.out.println("inside loop2 oracle if");
				rs2 = stmt1.executeQuery("SELECT Column_Name as COLUMN_NAME FROM  All_Tab_Columns WHERE  OWNER = '"+deltaProcessDTO.getSourceConnectionSchema()+"' AND  Table_Name = '"+rs1.getString("TABLE_NAME")+"'");}
        	//ResultSet rs2 = stmt1.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+deltaProcessDTO.getSourceConnectionSchema()+"' AND //TABLE_NAME = '"+rs1.getString("TABLE_NAME")+"';");
        	JsonArray cols = new JsonArray();
        	while(rs2.next() )
    		{
    		  //JsonArray row = new JsonArray();
    		  cols.add(new JsonPrimitive(rs2.getString("COLUMN_NAME")));
            }
            row.add("columnList",cols);
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