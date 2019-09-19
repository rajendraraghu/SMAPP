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
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import com.canny.snowflakemigration.service.SnowHistoryJobStatusService;
import com.canny.snowflakemigration.service.SnowHistoryProcessStatusService;
import com.canny.snowflakemigration.service.dto.SnowHistoryDTO;
import com.canny.snowflakemigration.service.dto.SnowHistoryProcessStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowHistoryJobStatusDTO;

import java.time.Instant;
import com.opencsv.CSVWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistorySendTableList {
	public static SnowHistoryProcessStatusDTO snowHistoryProcessStatusDTO = new SnowHistoryProcessStatusDTO();
	public static SnowHistoryJobStatusDTO snowHistoryJobStatusDTO = new SnowHistoryJobStatusDTO();
	public static SnowHistoryJobStatusDTO write1;
	public static Logger logger;

	public static String sendSelectedTables(SnowHistoryDTO processDTO,
			SnowHistoryProcessStatusService snowHistoryProcessStatusService,
			SnowHistoryJobStatusService snowHistoryJobStatusService) throws SQLException, ClassNotFoundException {
		String status = new String();
		String timeStamp = new SimpleDateFormat().format(new Date());
		SnowHistoryProcessStatusDTO write = new SnowHistoryProcessStatusDTO();
		int inscnt =0;
		int delcnt =0;
		int srccnt =0;
		// SnowHistoryJobStatusDTO snowHistoryJobStatusDTO = new SnowHistoryJobStatusDTO();
		logger = Logger.getLogger("MySnowHistoryLog");
		FileHandler fh;
        String system = processDTO.getSourceSystem();

		long success_count = 0;
        long failure_count = 0;
		try{
			fh = new FileHandler("F:/POC/CSV/logs/MySnowhistoryLogFile.log");  
		    logger.addHandler(fh);
		    SimpleFormatter formatter = new SimpleFormatter();  
		    fh.setFormatter(formatter);		        
		    logger.info("My first log");
		    logger.info("initiating the connection");


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
		    logger.info("connection created and audit starts");  
		   // snowHistoryProcessStatusDTO.setBatchId(jobid);
		    snowHistoryProcessStatusDTO.setStartTime(Instant.now());
		    snowHistoryProcessStatusDTO.setProcessId(processDTO.getId());
		    snowHistoryProcessStatusDTO.setName(processDTO.getName());
		    snowHistoryProcessStatusDTO.setRunBy("admin");
		     //migrationProcessStatusDTO.setRunby(processDTO.getRunBy());
		    snowHistoryProcessStatusDTO.setTotalTables((long)tablecount);
		    snowHistoryProcessStatusDTO.setStatus("In Progress");
		    snowHistoryProcessStatusDTO.setSuccessTables((long)0);
		    snowHistoryProcessStatusDTO.setErrorTables((long)0);		    
		    write = snowHistoryProcessStatusService.save(snowHistoryProcessStatusDTO);		     
		    logger.info("audit saved");
    		
		    int i = 0;	
		    
						
		       	 while(i<tablecount)
		         {
					 SnowHistoryJobStatusDTO write1 = new SnowHistoryJobStatusDTO();
			       try {
			            String tn = tablesToMigrate[i].replace("[\"", "");
					    String tableName = tn.replaceAll("\"]|\"", "");
					    logger.info("starting bulk history load for the table: "+tableName);										
					   //snowHistoryJobStatusDTO.setJobId((long)500);
					    Statement stmt5 = con1.createStatement();
					    ResultSet rs5 = stmt5.executeQuery("SELECT COUNT(*) as cnt FROM "+tableName);
		                rs5.next();
						srccnt = rs5.getInt("cnt");
					    snowHistoryJobStatusDTO.setSourceCount((long)srccnt);
				        snowHistoryJobStatusDTO.setInsertCount((long)0);				       
				        snowHistoryJobStatusDTO.setDeleteCount((long)0);
		                snowHistoryJobStatusDTO.setBatchId(write.getBatchId());
		                snowHistoryJobStatusDTO.setName(tableName);
		                snowHistoryJobStatusDTO.setStartTime(Instant.now());		              
				        snowHistoryJobStatusDTO.setStatus("IN PROGRESS");
				        write1 = snowHistoryJobStatusService.save(snowHistoryJobStatusDTO);
					    bulkprocess(tableName,con1,con2,processDTO.getId(),system,processDTO.getSourceConnectionDatabase());
					    status = "SUCCESS";
				        success_count = success_count + 1;
				        write1.setEndTime(Instant.now());
					    write1.setStatus("SUCCESS");
					    write1.setSourceCount((long)srccnt);
					    write1.setInsertCount((long)inscnt);
					    write1.setDeleteCount((long)delcnt);
					    write1 = snowHistoryJobStatusService.save(write1);
					    logger.info("bulk process completed for table:"+tableName);
				        i= i + 1;
				    }
					catch (Exception e) 
					{
			        	write1.setEndTime(Instant.now());
			        	write1.setStatus("FAILURE");
				        failure_count = failure_count + 1;
				        // write.setErrorTables (failure_count);
				        write1 = snowHistoryJobStatusService.save(write1);
				        i = i + 1;
					    continue;
					}					
				  }
		       	logger.info("bulk process completed for all the tables");
				write.setRunBy("admin");
		       	write.setStatus("SUCCESS");
				write.setSuccessTables(success_count);
				write.setErrorTables(failure_count);
		        write.setEndTime(Instant.now()); 
		        write = snowHistoryProcessStatusService.save(write);
     	 		con1.close();
		        con2.close();

		}
			catch(Exception e)
		{
     		write.setRunBy("admin");
			write.setSuccessTables(success_count);
		    write.setErrorTables(failure_count);
			write.setStatus("FAILURE --"+e.toString());
		    write.setEndTime(Instant.now());
		    write = snowHistoryProcessStatusService.save(write);
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
			column[i - 1] = metadata.getColumnName(i);
		}
		csvWriter.writeNext(column, false);

		while (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				column[i - 1] = rs.getString(i);
			}
			csvWriter.writeNext(column, false);
		}

		csvWriter.close();
	}

	public static void bulkprocess(String tableName, Connection con1, Connection con2, long processid, String system,
			String dbname) throws SQLException, IOException {

		String query = new String();
		query = "select * from " + tableName;
		stageLoad(query, con1, con2, tableName, system, dbname);
		histLoad(con2, tableName);
	}

	public static String gethashColNames(String ColName) {
		String hashColName = ColName.replace(",", "||'~'||");
		return hashColName;
	}

	public static String getColNames2(Connection con, String tablename, String dname, String dbname)
			throws SQLException {

		logger.info("getcolnames entry");
    	String s = new String();
    	Statement stmt0  = con.createStatement();
    	ResultSet rs9 = null;

    	if(dname.equals("Oracle")) 
		{ rs9 = stmt0.executeQuery("SELECT Column_Name FROM  All_Tab_Columns WHERE Table_Name = '"+tablename+"'");}    		
    	else if (dname.equals("MySQL"))
		{rs9 = stmt0.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+tablename+"' AND TABLE_SCHEMA = '"+dbname+"';");}
    	else if (dname.equals("SQLServer")) 
		{rs9 = stmt0.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG = '"+dbname+"' AND TABLE_NAME = '"+tablename+"';");}
    	else if (dname.equals("Netezza")) 
		{rs9 = stmt0.executeQuery("SELECT COLUMN_NAME FROM _V_SYS_COLUMNS WHERE TABLE_SCHEMA = '"+dbname+"' AND TABLE_NAME  = '"+tablename+"';");}
    	else if (dname.equals("Teradata")) 
		{rs9 = stmt0.executeQuery("SELECT  ColumnName as COLUMN_NAME FROM DBC.ColumnsV WHERE DatabaseName = '"+dbname+"' AND TableName = '"+tablename+"';");}
    	
    	while(rs9.next())
    	{;
    	 s = s.concat(rs9.getString(1));
    	 s = s.concat(",");
    	}
    	logger.info("getcolnames exit");
    	return s.substring(0,s.length()-1);
	}

	public static void stageLoad(String query, Connection con1, Connection con2, String tableName, String system,
			String dbname) throws SQLException, IOException {
		Statement stmt1 = con1.createStatement();
		ResultSet rs1 = stmt1.executeQuery(query);
		String srcCols = getColNames2(con1, tableName, system, dbname);
		String csvFilename = "F:/POC/CSV/" + tableName + ".csv";
		toCSV(rs1, csvFilename);

		logger.info("Stage file writing complete");
		Statement stmt2 = con2.createStatement();
		logger.info("create or replace stage " + tableName
				+ "_stage copy_options = (on_error='skip_file') file_format = (type = 'CSV' field_delimiter = ',' skip_header = 1 FIELD_OPTIONALLY_ENCLOSED_BY = '\"');");
		stmt2.executeQuery("create or replace stage " + tableName
				+ "_stage copy_options = (on_error='skip_file') file_format = (type = 'CSV' field_delimiter = ',' skip_header = 1 FIELD_OPTIONALLY_ENCLOSED_BY = '\"');");
		stmt2.executeQuery("PUT 'file://F:/POC/CSV/" + tableName + ".csv' @" + tableName + "_stage;");
		int m = 1;
		int k = srcCols.replaceAll("[^,]", "").length() + 1;
		String stageCols = "t.$1,";
		while (m < k) {
			m = m + 1;
			stageCols = stageCols + "t.$" + m + ",";
		}
		stageCols = stageCols.substring(0, stageCols.length() - 1);
		String hashCol = gethashColNames(stageCols);
		createAlterDDL(con1, con2, tableName,system);
		logger.info("stagecols:" + stageCols);		
		stmt2.executeQuery("TRUNCATE TABLE " + tableName);
		logger.info("Truncating " + tableName);
		stmt2.executeQuery("INSERT INTO " + tableName + " SELECT " + stageCols + ",0 as sah_isdeleted,MD5(" + hashCol
				+ ") as sah_md5hash,sah_MD5HASH || '~' || TO_VARCHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISSFF6') as sah_md5hashpk FROM @"
				+ tableName + "_stage t;");
				
		logger.info("table creation  and loading (stg) complete");

	}

	public static void histLoad(Connection con2, String tableName) throws SQLException {

		Statement stmt3 = con2.createStatement();
		ResultSet rs5 = stmt3.executeQuery("SELECT COUNT(*) as cnt FROM "+tableName+"history");
		rs5.next();
		delcnt = rs5.getInt("cnt");
		stmt3.executeQuery("TRUNCATE TABLE " + tableName + "history");
		stmt3.executeQuery("INSERT INTO " + tableName
				+ "history SELECT *,1 as sah_currentind,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) as sah_createdTime,null as sah_updatedtime FROM "
				+ tableName + " src;");
        ResultSet rs5 = stmt3.executeQuery("SELECT COUNT(*) as cnt FROM "+tableName+"history");
		rs5.next();
		inscnt = rs5.getInt("cnt");
	}

	public static void createAlterDDL(Connection con1, Connection con2, String tableName,String system) throws SQLException {
		Statement stmt1 = con1.createStatement();
		    	ResultSet rs1 = null;
    	String ddl6 = "";
    	if(system.equals("MySQL")) 
    	{
    		rs1=stmt1.executeQuery("SHOW CREATE TABLE "+tableName);
        	rs1.next();
    		String ddl = rs1.getString("Create Table");
    		int ind1 = ddl.indexOf("ENGINE");
    		String ddl2 = ddl.substring(0,ind1);
    		String ddl3 = ddl2.replaceAll("int\\([0-9]+\\)","int");
    		String ddl4 = ddl3.substring(ddl3.indexOf("("));
    		String ddl5 = ddl4.replaceAll("`","");
    		ddl6 = ddl5.substring(0, ddl5.length()-2);
    	}
        else if(system.equals("Teradata")) {rs1=stmt1.executeQuery("SHOW TABLE "+tableName+";");}

		else if(system.equals("Oracle")) 
        {
			System.out.println("select dbms_metadata.get_ddl('TABLE', '"+tableName+"') as \"Create Table\" from dual");
        	rs1=stmt1.executeQuery("select dbms_metadata.get_ddl('TABLE', '"+tableName+"') as \"Create Table\" from dual");
        	rs1.next();
    		String ddl = rs1.getString("Create Table");
			System.out.println("ddl1:"+ddl);
    		int ind1 = ddl.indexOf("USING");
			System.out.println("ind1:"+ind1);
    		String ddl2 = ddl.substring(0,ind1);
			System.out.println("ddl2:"+ddl2);
    		String ddl3 = ddl2.replaceAll("NUMBER\\(\\*,[0-9]+\\)","NUMBER");
			System.out.println("ddl3:"+ddl3);
    		String ddl4 = ddl3.substring(ddl3.indexOf("("));
			System.out.println("ddl4:"+ddl4);
    		String ddl5 = ddl4.substring(0, ddl4.length()-2);		
			System.out.println("ddl5:"+ddl5);
			ddl5 = ddl5.replaceAll("\"","");
			System.out.println("ddl5:"+ddl5);
			ddl6 = ddl5.replaceAll("ENABLE","");			
			System.out.println("ddl6:"+ddl6);
        }
		else if(system.equals("SQLServer")) 
        {
			ddl6 = "(COL1 int NULL,COL2 varchar(25)";
		}
		String stagecreate = ddl6.concat(",sah_isdeleted INT,sah_MD5HASH TEXT,sah_MD5HASHPK TEXT);");
		String histcreate = ddl6.concat(",sah_isdeleted INT,sah_MD5HASH TEXT,sah_MD5HASHPK TEXT,sah_currentind boolean,sah_createdTime timestamp,sah_updatedtime timestamp);");

		Statement stmt2 = con2.createStatement();
		logger.info("CREATE TABLE IF NOT EXISTS " + tableName + stagecreate);
		logger.info("CREATE TABLE IF NOT EXISTS " + tableName + "history " + histcreate);
		ResultSet rs2 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS " + tableName + stagecreate);
		ResultSet rs3 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS " + tableName + "history " + histcreate);

	}
}