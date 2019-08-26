package com.canny.snowflakemigration.service.util;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import com.canny.snowflakemigration.service.SnowHistoryJobStatusService;
import com.canny.snowflakemigration.service.SnowHistoryProcessStatusService;
import com.canny.snowflakemigration.service.dto.SnowHistoryDTO;
import com.canny.snowflakemigration.service.dto.SnowHistoryProcessStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowHistoryJobStatusDTO;
//import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;
import com.opencsv.CSVWriter;


public class HistorySendTableList  {
	public static String sendSelectedTables(SnowHistoryDTO processDTO, SnowHistoryProcessStatusService snowHistoryProcessStatusService,SnowHistoryJobStatusService snowHistoryJobStatusService) throws SQLException,ClassNotFoundException
	{
		String status = new String();
		try{
			
			status = "FAILURE";
			Properties properties0 = new Properties();
			properties0.put("user", processDTO.getSourceConnectionUsername());
			properties0.put("password", processDTO.getSourceConnectionPassword());
			properties0.put("db",processDTO.getSourceConnectionDatabase());
		    properties0.put("schema",processDTO.getSourceConnectionSchema());				 
	        Connection con1 = DriverManager.getConnection(processDTO.getSourceConnectionUrl(), properties0);
	        
	        Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");  
		    Properties properties = new Properties();
		    properties.put("user", processDTO.getSnowflakeConnectionUsername());
		    properties.put("password", processDTO.getSnowflakeConnectionPassword());
		    properties.put("account", processDTO.getSnowflakeConnectionAcct());
            properties.put("warehouse",processDTO.getSnowflakeConnectionWarehouse());
		    properties.put("db",processDTO.getSnowflakeConnectionDatabase());
	        properties.put("schema",processDTO.getSnowflakeConnectionSchema());
		    Connection con2=DriverManager.getConnection(processDTO.getSnowflakeConnectionUrl(),properties);
		    
		   
		    
    		String tablestoMigrate = processDTO.getTablesToMigrate();
		    String[] tablesToMigrate = tablestoMigrate.split(",");		
		    int tablecount = tablesToMigrate.length;
		    int i = 0;			                    
            String system = processDTO.getSourceConnectionName();
		       	 while(i<tablecount)
		         {
			        String tn = tablesToMigrate[i].replace("[\"", "");
					String tableName = tn.replaceAll("\"]|\"", "");
					System.out.println("starting bulk history load for the table: "+tableName);
					bulkprocess(tableName,con1,con2,processDTO.getId(),system,processDTO.getSourceConnectionDatabase());
				    i= i +1;
				  }
		con1.close();
		con2.close();
		}
			catch(Exception e)
		{
			System.out.println("catch exception:"+e);		
		}  
	    return status;
	}
	public static void toCSV(ResultSet rs, String csvFilename)

	        throws SQLException, IOException {
	    CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFilename));

	    ResultSetMetaData metadata = rs.getMetaData();
	    int columnCount = metadata.getColumnCount();
	    String[] column = new String[columnCount];
	    for (int i = 1; i <= columnCount; i++) {
	        column[i-1] = metadata.getColumnName(i);
	    }
	    csvWriter.writeNext(column, false);

	    while (rs.next()) {
	        for (int i = 1; i <= columnCount; i++) {
	            column[i-1] = rs.getString(i);
	        }
	        csvWriter.writeNext(column, false);
	    }

	    csvWriter.close();
	}
	public static void bulkprocess(String tableName,Connection con1, Connection con2,long processid,String system,String dbname)	throws SQLException, IOException{
	
	String query = new String();	
	query = "select * from " +tableName;
	stageLoad(query,con1,con2,tableName,system,dbname);
	histLoad(con2,tableName);	
	}
	public static String gethashColNames(String ColName) {
    	String hashColName = ColName.replace(",","||'~'||");
    	return hashColName;
    }
    public static String getColNames2(Connection con, String tablename, String dname,String dbname) throws SQLException
    {
    	System.out.println("getcolnames entry");
    	String s = new String();
    	Statement stmt0  = con.createStatement();
    	ResultSet rs9 ;
    	if(dname.contains("oracle")) 
    		rs9 = stmt0.executeQuery("SELECT Column_Name FROM  All_Tab_Columns WHERE Table_Name = '"+tablename+"'");    		
    	else
    		rs9 = stmt0.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+tablename+"' AND TABLE_SCHEMA = '"+dbname+"';"); 
    	while(rs9.next())
    	{
    	 s = s.concat(rs9.getString(1));
    	 s = s.concat(",");
    	}
    	System.out.println("getcolnames exit");
    	return s.substring(0,s.length()-1);
    }
    public static void stageLoad(String query, Connection con1,Connection con2,String tableName,String system,String dbname) throws SQLException, IOException
    {
    	Statement stmt1=con1.createStatement(); 
    	ResultSet rs1=stmt1.executeQuery(query);
    	String srcCols = getColNames2(con1,tableName,system,dbname);
	   	String csvFilename = "F:/POC/CSV/"+tableName+".csv";
    	toCSV(rs1,csvFilename);   	
    	System.out.println("Stage file writing complete");    	
    	Statement stmt2=con2.createStatement();
    	System.out.println("create or replace stage "+tableName+"_stage copy_options = (on_error='skip_file') file_format = (type = 'CSV' field_delimiter = ',' skip_header = 1 FIELD_OPTIONALLY_ENCLOSED_BY = '\"');");
    	stmt2.executeQuery("create or replace stage "+tableName+"_stage copy_options = (on_error='skip_file') file_format = (type = 'CSV' field_delimiter = ',' skip_header = 1 FIELD_OPTIONALLY_ENCLOSED_BY = '\"');");    	
    	stmt2.executeQuery("PUT 'file://F:/POC/CSV/"+tableName+".csv' @"+tableName+"_stage;");
    	int m = 1;
    	int k = srcCols.replaceAll("[^,]","").length() + 1;
    	String stageCols = "t.$1,";
    	while(m<k) {
    		m = m+1;
    		stageCols =stageCols + "t.$"+m+",";
    		}
    	stageCols =stageCols.substring(0,stageCols.length() - 1);
    	String hashCol = gethashColNames(stageCols);
    	createAlterDDL(con1,con2,tableName);
    	System.out.println("stagecols:"+stageCols);
		stmt2.executeQuery("TRUNCATE TABLE "+tableName);
		System.out.println("Truncating "+tableName);
    	stmt2.executeQuery("INSERT INTO "+tableName+" SELECT "+stageCols+",0 as sah_isdeleted,MD5("+hashCol+") as sah_md5hash,sah_MD5HASH || '~' || TO_VARCHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISSFF6') as sah_md5hashpk FROM @"+tableName+"_stage t;"); 	
		System.out.println("table creation  and loading (stg) complete");
    }
    public static void histLoad(Connection con2,String tableName) throws SQLException
    {
    	
    	Statement stmt3=con2.createStatement();  
		stmt3.executeQuery("TRUNCATE TABLE "+tableName+ "hist");
		stmt3.executeQuery("INSERT INTO "+tableName+ "hist SELECT * FROM "+tableName+";");    	
    }   
    public static void createAlterDDL(Connection con1,Connection con2,String tableName)  throws SQLException
    {
    	Statement stmt1=con1.createStatement();
    	ResultSet rs1=stmt1.executeQuery("SHOW CREATE TABLE "+tableName);//exec sp_columns YourTableName//sp_help 'tablename' online//DESC
    	rs1.next();
		String ddl = rs1.getString("Create Table");
		int ind1 = ddl.indexOf("ENGINE");
		String ddl2 = ddl.substring(0,ind1);
		String ddl3 = ddl2.replaceAll("int\\([0-9]+\\)","int");
		String ddl4 = ddl3.substring(ddl3.indexOf("("));
		String ddl5 = ddl4.replaceAll("`","");
		String ddl6 = ddl5.substring(0, ddl5.length()-2);
		String stagecreate = ddl6.concat(",sah_isdeleted INT,sah_MD5HASH TEXT,sah_MD5HASHPK TEXT);");
		
		String histcreate = ddl6.concat(",sah_isdeleted INT,sah_currentind boolean,sah_updatedtime timestamp,sah_MD5HASH TEXT,sah_createdTime timestamp,sah_MD5HASHPK TEXT);");
		
		Statement stmt2 = con2.createStatement();
		System.out.println("CREATE TABLE IF NOT EXISTS "+tableName+ stagecreate);
		System.out.println("CREATE TABLE IF NOT EXISTS "+tableName+"hist "+histcreate);
		ResultSet rs2 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+ stagecreate);
		ResultSet rs3 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+"hist "+histcreate);    	
    }
  
    }
    

