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
import com.canny.snowflakemigration.service.DeltaProcessJobStatusService;
import com.canny.snowflakemigration.service.DeltaProcessStatusService;
import com.canny.snowflakemigration.service.dto.DeltaProcessDTO;
import com.canny.snowflakemigration.service.dto.DeltaProcessStatusDTO;
import com.canny.snowflakemigration.service.dto.DeltaProcessJobStatusDTO;

import java.time.Instant;
import com.opencsv.CSVWriter;
import java.text.SimpleDateFormat;
import java.util.Date;



public class DeltaSendTableList  {
	public static DeltaProcessStatusDTO deltaProcessStatusDTO = new DeltaProcessStatusDTO();
	public static DeltaProcessJobStatusDTO deltaProcessJobStatusDTO =  new DeltaProcessJobStatusDTO();
	public static DeltaProcessJobStatusDTO write1;									   
	public static Logger logger;
	public static String sendSelectedTables(DeltaProcessDTO processDTO, DeltaProcessStatusService deltaProcessStatusService,DeltaProcessJobStatusService deltaProcessJobStatusService) throws SQLException,ClassNotFoundException
	{
		ResultSet rs3= null; 
		String status = new String();
		String timeStamp = new SimpleDateFormat().format( new Date() );
		DeltaProcessStatusDTO write = new DeltaProcessStatusDTO();
		logger = Logger.getLogger("MyDeltaLog"); 
		FileHandler fh;
		status = "FAILURE";
		String system = processDTO.getSourceType();
		long success_count = 0;
        long failure_count = 0;					   
		try{
			//int jobid=0;

			fh = new FileHandler("F:/POC/CSV/logs/MyDeltaLogFile.log");  
		    logger.addHandler(fh);
		    SimpleFormatter formatter = new SimpleFormatter();  
		    fh.setFormatter(formatter);		        
		    logger.info("Delta log for process id:"+processDTO.getId());
			logger.info("initiating the connection");
			
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
            
		    logger.info("conn created");
		    String pk1 = processDTO.getPk();
    		String tablestoMigrate = processDTO.getTablesList();
		    String[] pk2 = pk1.split(",");
		    String[] tablesToMigrate = tablestoMigrate.split(",");
		    logger.info("variable initialization completed");		    
		    Statement stmt0 = con1.createStatement();
		    int tablecount = tablesToMigrate.length;
		    logger.info("system:"+system);
			
			logger.info("connection created and audit starts");  
		   
		    deltaProcessStatusDTO.setJobStartTime(Instant.now());
		    deltaProcessStatusDTO.setProcessId(processDTO.getId());
		    deltaProcessStatusDTO.setName(processDTO.getName());
		    //deltaProcessStatusDTO.setRunBy("admin");
		    deltaProcessStatusDTO.setRunby(processDTO.getRunBy());
		    deltaProcessStatusDTO.setTableCount((long)tablecount);
		    deltaProcessStatusDTO.setJobStatus("In Progress");
		    deltaProcessStatusDTO.setSuccessCount((long)0);
		    deltaProcessStatusDTO.setFailureCount((long)0);		    
		    write = deltaProcessStatusService.save(deltaProcessStatusDTO);		     
		    logger.info("audit saved");
			
		    int i = 0;
		    while(i < tablecount)
		    {
				DeltaProcessJobStatusDTO write1 = new DeltaProcessJobStatusDTO();
				try{
				String tn = tablesToMigrate[i].replace("[\"", "");
				String tableName = tn.replaceAll("\"]|\"", "");
		        ResultSet rs1 = stmt0.executeQuery("SELECT * FROM "+tableName+";");						
				logger.info("starting delta creation for the table: "+tableName);	   
					   deltaProcessJobStatusDTO.setUpdateCount((long)2);
				       deltaProcessJobStatusDTO.setInsertCount((long)3);				       
				       deltaProcessJobStatusDTO.setDeleteCount((long)4);
		               deltaProcessJobStatusDTO.setJobId(write.getJobId());
		               deltaProcessJobStatusDTO.setTableName(tableName);
		               deltaProcessJobStatusDTO.setTableLoadStartTime(Instant.now());
                       deltaProcessJobStatusDTO.setRunType("bulkdelta");					   
				       deltaProcessJobStatusDTO.setTableLoadStatus("IN PROGRESS");
					   deltaProcessJobStatusDTO.setProcessId(processDTO.getId());
					   deltaProcessJobStatusDTO.setProcessName(processDTO.getName());
					   //deltaProcessJobStatusDTO.setSourceName(processDTO.getSourceConnectionName());
					   //deltaProcessJobStatusDTO.setDestName(processDTO.getSnowflakeConnectionName());
				       write1 = deltaProcessJobStatusService.save(deltaProcessJobStatusDTO);		    	
        		        createAlterDDL(con1,con2,tableName,system);
		        if (rs1.next())
			     {			
					//using local file now. Should be replaced with S3 or other filespace
					String csvFilename = "F:/POC/CSV/"+tableName+".csv";
					String srcCols = getColNames2(con1,tableName,system,processDTO.getSourceConnectionDatabase());
					toCSV(rs1,csvFilename);	
					Statement stmt2=con2.createStatement();
					stmt2.executeQuery("create or replace stage "+tableName+"_stage copy_options = (on_error='skip_file') file_format = (type = 'CSV' field_delimiter = ',' skip_header = 1 FIELD_OPTIONALLY_ENCLOSED_BY = '\"');");
					stmt2.executeQuery("PUT 'file://F:/POC/CSV/"+tableName+".csv' @"+tableName+"_stage;");
					logger.info("stage writing completed");
					int j = 1;
					int k = srcCols.replaceAll("[^,]","").length();
					String stageCols = "t.$1,";
					while(j<=k) {
						j = j+1;
						stageCols =stageCols + "t.$"+j+",";
						}
					stageCols =stageCols.substring(0,stageCols.length() - 1);
					String hashCol = gethashColNames(stageCols);
					logger.info(stageCols);
					logger.info(hashCol);
					String pk3 = pkgenNew(pk2[i]);
					String pk4 = pkgenNull(pk2[i]);
					String pk5 = pk4.replaceAll("y.","t.");
					ResultSet rs2 = stmt2.executeQuery("SELECT * FROM "+tableName+"_today;");
					if(rs2.next())
					{
						stmt2.executeUpdate("TRUNCATE TABLE "+tableName+ "_yesterday");
						logger.info("INSERT INTO "+tableName+"_yesterday SELECT * FROM "+ tableName+ "_today");
						stmt2.executeQuery("INSERT INTO "+tableName+"_yesterday SELECT * FROM "+ tableName+ "_today");
						stmt2.executeQuery("TRUNCATE TABLE "+tableName+ "_today");
						logger.info("INSERT INTO "+tableName+"_today SELECT "+stageCols+",MD5("+hashCol+") as delta_MD5HASH,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) as delta_CREATEDTIME FROM @"+tableName+"_stage t;");
						stmt2.executeQuery("INSERT INTO "+tableName+"_today SELECT "+stageCols+",MD5("+hashCol+") as delta_MD5HASH,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) as delta_CREATEDTIME FROM @"+tableName+"_stage t;");
					}
					else
					{
						logger.info("INSERT INTO "+tableName+"_today SELECT "+stageCols+",MD5("+hashCol+") as delta_MD5HASH,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) as delta_CREATEDTIME FROM @"+tableName+"_stage t;");
						stmt2.executeQuery("INSERT INTO "+tableName+"_today SELECT "+stageCols+",MD5("+hashCol+") as delta_MD5HASH,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) as delta_CREATEDTIME FROM @"+tableName+"_stage t;");
					}
					logger.info("INSERT INTO "+tableName+"_delta SELECT y.*,t.*,CASE WHEN "+pk4+" THEN 'INSERT' WHEN "+pk5+" THEN 'DELETE' WHEN "+pk3+" AND y.delta_MD5HASH <> t.delta_MD5HASH THEN 'UPDATE'  ELSE 'NO CHANGE' END AS CDC FROM "+tableName+"_yesterday y FULL OUTER JOIN "+tableName+"_today t ON "+ pk3);
					stmt2.executeUpdate("INSERT INTO "+tableName+"_delta SELECT y.*,t.*,CASE WHEN "+pk4+" THEN 'INSERT' WHEN "+pk5+" THEN 'DELETE' WHEN "+pk3+" AND y.delta_MD5HASH <> t.delta_MD5HASH THEN 'UPDATE'  ELSE 'NO CHANGE' END AS CDC FROM "+tableName+"_yesterday y FULL OUTER JOIN "+tableName+"_today t ON "+pk3);
					rs3 = stmt2.executeQuery("SELECT COALESCE(INS,0) AS INS,COALESCE(UPD,0) AS UPD,COALESCE(DEL,0) AS DEL,COALESCE(NOCHANGE, 0) AS NOCHANGE FROM (SELECT cdc,COUNT(*) as cnt FROM "+tableName+"_delta GROUP BY cdc ORDER BY cdc)src pivot(MAX(cnt) for cdc in ('INSERT', 'UPDATE', 'DELETE', 'NO CHANGE')) as p (INS,UPD,DEL,NOCHANGE);");
					
				}
				i = i+1;                
				write1.setTableLoadEndTime(Instant.now());
			    write1.setTableLoadStatus("SUCCESS");
				rs3.next();
				System.out.println("DEL:"+rs3.getInt("DEL"));
				write1.setDeleteCount((long)rs3.getInt("DEL"));				
				write1.setInsertCount((long)rs3.getInt("INS"));
				write1.setUpdateCount((long)rs3.getInt("UPD"));
				success_count = success_count + 1;
				write1 = deltaProcessJobStatusService.save(write1);
				logger.info("delta process completed for table:"+tableName);
			}
			catch (Exception e) 
					{
			        	write1.setTableLoadEndTime(Instant.now());
			        	write1.setTableLoadStatus("FAILURE"+e.toString());
				        failure_count = failure_count + 1;
				        // write.setErrorTables (failure_count);
				        write1 = deltaProcessJobStatusService.save(write1);
				        i = i + 1;
					    continue;
					}	
            }					
			status = "SUCCESS";
			logger.info("delta process completed for all the tables");
				write.setRunby(processDTO.getRunBy());
		       	write.setJobStatus("SUCCESS");
				write.setSuccessCount(success_count);
			    write.setFailureCount(failure_count);								
		        write.setJobEndTime(Instant.now()); 
		        write = deltaProcessStatusService.save(write);
			
			con1.close();
			con2.close();		



		    
		}
			catch(Exception e)
		{
			logger.info("catch exception:"+e);
			status = "FAILURE - catch"+ e;
			//write1.setTableLoadEndTime(Instant.now());
			//write1.setTableLoadStatus("FAILURE");
			//write1 = deltaProcessJobStatusService.save(write1);
			logger.info("catch exception:"+e);	
			write.setRunby("admin");
			write.setJobStatus("FAILURE --"+e.toString());
		    write.setJobEndTime(Instant.now());
		    write = deltaProcessStatusService.save(write);

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
	
    public static String gethashColNames(String ColName) {
    	String hashColName = ColName.replace(",","||'~'||");
    	return hashColName;
    }
    public static String getColNames2(Connection con, String tablename, String dname,String dbname) throws SQLException
    {
    	logger.info("getcolnames entry");
    	String s = new String();
    	Statement stmt0  = con.createStatement();
    	ResultSet rs9 = null;

    	if(dname.equals("Oracle")) { rs9 = stmt0.executeQuery("SELECT Column_Name FROM  All_Tab_Columns WHERE Table_Name = '"+tablename+"'");}    		

    	else if (dname.equals("MySQL")) {rs9 = stmt0.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+tablename+"' AND TABLE_SCHEMA = '"+dbname+"';");}
    	else if (dname.equals("SQLServer")) {rs9 = stmt0.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG = '"+dbname+"' AND TABLE_NAME = '"+tablename+"';");}
    	else if (dname.equals("Netezza")) {rs9 = stmt0.executeQuery("SELECT COLUMN_NAME FROM _V_SYS_COLUMNS WHERE TABLE_SCHEMA = '"+dbname+"' AND TABLE_NAME  = '"+tablename+"';");}
    	else if (dname.equals("Teradata")) {rs9 = stmt0.executeQuery("SELECT  ColumnName as COLUMN_NAME FROM DBC.ColumnsV WHERE DatabaseName = '"+dbname+"' AND TableName = '"+tablename+"';");}
    	
		while(rs9.next())
    	{
    	 s = s.concat(rs9.getString(1));
    	 s = s.concat(",");
    	}
    	logger.info("getcolnames exit");
		logger.info(s.substring(0,s.length()-1));
    	return s.substring(0,s.length()-1);
    }
   
    
  
    public static void createAlterDDL(Connection con1,Connection con2,String tableName,String system)  throws SQLException
    {
    	Statement stmt1=con1.createStatement();
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
			logger.info("select dbms_metadata.get_ddl('TABLE', '"+tableName+"') as \"Create Table\" from dual");
        	rs1=stmt1.executeQuery("select dbms_metadata.get_ddl('TABLE', '"+tableName+"') as \"Create Table\" from dual");
        	rs1.next();
    		String ddl = rs1.getString("Create Table");
			logger.info("ddl1:"+ddl);
    		int ind1 = ddl.indexOf("USING");
			logger.info("ind1:"+ind1);
    		String ddl2 = ddl.substring(0,ind1);
			logger.info("ddl2:"+ddl2);
    		String ddl3 = ddl2.replaceAll("NUMBER\\(\\*,[0-9]+\\)","NUMBER");
			logger.info("ddl3:"+ddl3);
    		String ddl4 = ddl3.substring(ddl3.indexOf("("));
			logger.info("ddl4:"+ddl4);
    		String ddl5 = ddl4.substring(0, ddl4.length()-2);		
			logger.info("ddl5:"+ddl5);
			ddl5 = ddl5.replaceAll("\"","");
			logger.info("ddl5:"+ddl5);
			ddl6 = ddl5.replaceAll("ENABLE","");			
			logger.info("ddl6:"+ddl6);
        }
		else if(system.equals("SQLServer")) 
        {
			ddl6 = "(empid int ,empname varchar(50)";
		}
		String stagecreate = ddl6.concat(",delta_MD5HASH TEXT,delta_createdTime timestamp");
		String histcreate = ddl6.concat(",delta_MD5HASH TEXT,delta_createdTime timestamp");

		Statement stmt2 = con2.createStatement();
		logger.info("CREATE TABLE IF NOT EXISTS "+tableName+"_today "+ stagecreate);
		logger.info("CREATE TABLE IF NOT EXISTS "+tableName+"_yesterday "+histcreate);
		ResultSet rs2 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+"_today "+ stagecreate+");");
		ResultSet rs3 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+"_yesterday "+histcreate+");");
		//ResultSet rs4 = stmt2.executeQuery
		logger.info("CREATE TABLE IF NOT EXISTS "+tableName+"_delta src."+stagecreate.replaceAll(",",",src.") +",dest."+ histcreate.replaceAll(",",",dest.")+",cdc varchar(40)");

    }
 
    public static String pkgenNew(String primarykey)
    {
	 try {

		int i = 1;
		String pk="";
		String[] prikey = primarykey.split("-");

			for(i = 0;i< prikey.length;i++)
			{
					pk = pk.concat(" and t.");
					pk = pk.concat(prikey[i]);
					pk = pk.concat("=y.");
					pk = pk.concat(prikey[i]);
					pk = pk.replace("[\"", "");
		            pk = pk.replace("\"]", "");
				    logger.info(pk);
			}
		logger.info("pk:"+pk.substring(5));
		return pk.substring(5);
		}
	 catch(Exception e)
		{

		 logger.info(e.toString());
		 return ("Failure");
		}
     }
	 
	public static String pkgenNull(String primarykey)
    {
	 try {

		int i = 1;
		String pk="";
		String[] prikey = primarykey.split("-");
		

			for(i = 0;i< prikey.length;i++)
			{
					
					pk = pk.concat(" and y.");
					pk = pk.concat(prikey[i]);
					pk = pk.concat(" IS NULL");
			        pk = pk.replace("[\"", "");
		            pk = pk.replace("\"]", "");
					logger.info(pk);
			}
		
		
		logger.info("pk:"+pk.substring(5));
		return pk.substring(5);
		}
	 catch(Exception e)
		{

		 logger.info(e.toString());
		 return ("Failure");
		}
     }
    }