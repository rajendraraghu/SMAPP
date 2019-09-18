package com.canny.snowflakemigration.service.util;

//import java.awt.List;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;
import com.canny.snowflakemigration.service.dto.MigrationProcessStatusDTO;
import com.canny.snowflakemigration.service.dto.MigrationProcessJobStatusDTO;
import com.canny.snowflakemigration.service.dto.SourceConnectionDTO;
import com.canny.snowflakemigration.service.MigrationProcessJobStatusService;
import com.canny.snowflakemigration.service.MigrationProcessStatusService;
import com.opencsv.CSVWriter;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SendTableList  {
	public static MigrationProcessStatusDTO migrationProcessStatusDTO = new MigrationProcessStatusDTO();
	public static MigrationProcessJobStatusDTO migrationProcessJobStatusDTO = new MigrationProcessJobStatusDTO();
	public static MigrationProcessStatusDTO write;
	public static MigrationProcessJobStatusDTO write1;
	public static SourceConnectionDTO sourceConnDTO = new SourceConnectionDTO();	
	public static Logger logger;
	public static String sendSelectedTables(MigrationProcessDTO processDTO,MigrationProcessStatusService migrationProcessStatusService,MigrationProcessJobStatusService migrationProcessJobStatusService) throws SQLException,ClassNotFoundException
	{
		String status = new String();
		String timeStamp = new SimpleDateFormat().format( new Date() );
		logger = Logger.getLogger("MyLog");  
	    FileHandler fh;
		long failure_count = 0;
		try{
			Long jobid=(long)0;
			fh = new FileHandler("F:/POC/CSV/MyLogFile.log");  
		    logger.addHandler(fh);
		    SimpleFormatter formatter = new SimpleFormatter();  
		    fh.setFormatter(formatter);		        
		    logger.info("My first log");
		    
			Properties properties0 = new Properties();
			properties0.put("user", processDTO.getSourceConnectionUsername());
			properties0.put("password", processDTO.getSourceConnectionPassword());
			properties0.put("db",processDTO.getSourceConnectionDatabase());
		    properties0.put("schema",processDTO.getSourceConnectionSchema());				 
	        Connection con1 = DriverManager.getConnection(processDTO.getSourceConnectionUrl(), properties0);
	        logger.info("source connection connected");
	        
	        Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");  
		    Properties properties = new Properties();
		    properties.put("user", processDTO.getSnowflakeConnectionUsername());
		    properties.put("password", processDTO.getSnowflakeConnectionPassword());
		    properties.put("account", processDTO.getSnowflakeConnectionAcct());
            properties.put("warehouse",processDTO.getSnowflakeConnectionWarehouse());
		    properties.put("db",processDTO.getSnowflakeConnectionDatabase());
	        properties.put("schema",processDTO.getSnowflakeConnectionSchema());
		    Connection con2=DriverManager.getConnection(processDTO.getSnowflakeConnectionUrl(),properties);
		    logger.info("destination connection connected");
		    
     	    String cdc = processDTO.getCdc();
    		String bulk = processDTO.getBulk();
    		String cdcpk = processDTO.getCdcPk();
    		String bulkpk = processDTO.getBulkPk();
    		String cdccol = processDTO.getCdcCols();
    		String tablestoMigrate = processDTO.getTablesToMigrate();
		    String[] tableNamescdc = cdc.split(",");
		    String[] tableNamesbulk = bulk.split(",");
		    String[] pkcdc = cdcpk.split(",");
		    String[] pkbulk = bulkpk.split(",");
		    String[] cdcColumn = cdccol.split(",");
		    String[] tablesToMigrate = tablestoMigrate.split(",");
		
		    int tablecount = tablesToMigrate.length;
		    logger.info("cdc array length :"+tableNamescdc.length);
			logger.info("bulk array length :"+tableNamesbulk.length);
		
		    int i = 0;
		    int j = 0;
		    long tableLoadid = (long)0;
     		String lastruntime = new String();	
     		lastruntime = migrationProcessStatusService.findLastUpdateTime(processDTO.getId());
		    String system = processDTO.getSourceType();
		    
		     migrationProcessStatusDTO.setJobId(jobid);
		     migrationProcessStatusDTO.setJobStartTime(Instant.now());
		     migrationProcessStatusDTO.setProcessId(processDTO.getId());
		     migrationProcessStatusDTO.setName(processDTO.getName());
		     migrationProcessStatusDTO.setRunby("admin");
		     //migrationProcessStatusDTO.setRunby(processDTO.getRunBy());
		     migrationProcessStatusDTO.setTableCount((long)tablecount);
		     migrationProcessStatusDTO.setJobStatus("In Progress");
		     migrationProcessStatusDTO.setSuccessCount((long)0);
		     migrationProcessStatusDTO.setFailureCount((long)0);
		     write = migrationProcessStatusService.save(migrationProcessStatusDTO);
		     long success_count = 0;
		     
		       	 while(i<tableNamescdc.length && tableNamescdc.length >0 && !tableNamescdc[i].equals("[]"))
		         {
					 try {
		       		logger.info("length is :"+tableNamescdc.length+"i value is :"+i);
					String tn = tableNamescdc[i].replace("[\"", "");
					String tableName = tn.replaceAll("\"]|\"", "");
					String key1  = pkcdc[i].replace("[\"", "");
					String key2 = key1.replaceAll("\"]|\"", "");
					String col1  = cdcColumn[i].replace("[\"", "");
					String col2 = col1.replaceAll("\"]|\"", "");
		            
					   migrationProcessJobStatusDTO.setJobId(write.getJobId());
		               migrationProcessJobStatusDTO.setTableLoadId(tableLoadid);
		               migrationProcessJobStatusDTO.setTableName(tableName);
		               migrationProcessJobStatusDTO.setTableLoadStatus("IN PROGRESS");
				       migrationProcessJobStatusDTO.setTableLoadStartTime(Instant.now());
				       migrationProcessJobStatusDTO.setInsertCount((long)0);
				       migrationProcessJobStatusDTO.setUpdateCount((long)0);
				       migrationProcessJobStatusDTO.setDeleteCount((long)0);
				       migrationProcessJobStatusDTO.setRunType("cdc");
				       migrationProcessJobStatusDTO.setProcessId(processDTO.getId());
				       migrationProcessJobStatusDTO.setProcessName(processDTO.getName());
				       migrationProcessJobStatusDTO.setSourceName(processDTO.getSourceConnectionName());
				       migrationProcessJobStatusDTO.setDestName(processDTO.getSnowflakeConnectionName());
				       write1 = migrationProcessJobStatusService.save(migrationProcessJobStatusDTO);
					logger.info("starting cdc delta process for the table: ");
     	    	    logger.info(tableName);
					cdcprocess(lastruntime,tableName,con1,con2,jobid,tableLoadid,processDTO.getId(),system,processDTO.getSourceConnectionDatabase(),key2,col2,migrationProcessJobStatusService);
					success_count = success_count + 1;
					write.setSuccessCount(success_count);
					   write1.setTableLoadEndTime(Instant.now());
					   write1.setTableLoadStatus("SUCCESS");
					   write1 = migrationProcessJobStatusService.save(write1);
				    i= i +1;	
		       		}
					catch (Exception e) {
					migrationProcessJobStatusDTO.setTableLoadEndTime(Instant.now());
					migrationProcessJobStatusDTO.setTableLoadStatus("FAILURE");
					failure_count = failure_count + 1;
					write.setFailureCount(failure_count);
					migrationProcessJobStatusDTO = migrationProcessJobStatusService.save(migrationProcessJobStatusDTO);
					i = i + 1;
					continue;
				}
				 }
		       	 while(j<tableNamesbulk.length &&  tableNamesbulk.length >0 && !tableNamesbulk[j].equals("[]"))
		         {
					 try {
			        logger.info("length is :"+tableNamesbulk.length+"i value is :"+i);
		            String tn = tableNamesbulk[j].replace("[\"", "");
					String tableName = tn.replaceAll("\"]|\"", "");
					String bkey1  = pkbulk[j].replace("[\"", "");
					String bkey2 = bkey1.replaceAll("\"]|\"", "");
					   migrationProcessJobStatusDTO.setJobId(write.getJobId());
		               migrationProcessJobStatusDTO.setTableLoadId(tableLoadid);
		               migrationProcessJobStatusDTO.setTableName(tableName);
					   migrationProcessJobStatusDTO.setTableLoadStatus("IN PROGRESS");
				       migrationProcessJobStatusDTO.setTableLoadStartTime(Instant.now());
				       migrationProcessJobStatusDTO.setInsertCount((long)0);
				       migrationProcessJobStatusDTO.setUpdateCount((long)0);
				       migrationProcessJobStatusDTO.setDeleteCount((long)0);
				       migrationProcessJobStatusDTO.setRunType("bulk");
				       migrationProcessJobStatusDTO.setProcessId(processDTO.getId());
				       migrationProcessJobStatusDTO.setProcessName(processDTO.getName());
				       migrationProcessJobStatusDTO.setSourceName(processDTO.getSourceConnectionName());
				       migrationProcessJobStatusDTO.setDestName(processDTO.getSnowflakeConnectionName());
				       write1 = migrationProcessJobStatusService.save(migrationProcessJobStatusDTO);
     	    	    logger.info("starting bulk process for the table: "+tableName);
					bulkprocess(tableName,con1,con2,jobid,tableLoadid,processDTO.getId(),system,processDTO.getSourceConnectionDatabase(),bkey2,migrationProcessJobStatusService);
					success_count = success_count + 1;
					write.setSuccessCount(success_count);
					   write1.setTableLoadEndTime(Instant.now());
					   write1.setTableLoadStatus("SUCCESS");
					   write1 = migrationProcessJobStatusService.save(write1);
				    j = j+1;
				    
				  }	
				  catch (Exception e) {
					write1.setTableLoadEndTime(Instant.now());
					write1.setTableLoadStatus("FAILURE");
					failure_count = failure_count + 1;
					write.setFailureCount(failure_count);
					migrationProcessJobStatusDTO = migrationProcessJobStatusService.save(write1);
					j = j + 1;
					continue;
				}
				 }
		       	write.setJobStatus("SUCCESS");
				write.setRunby("admin");
		       	status = "SUCCESS";
		        write.setJobEndTime(Instant.now());	      
		        write = migrationProcessStatusService.save(write);
		con1.close();
		con2.close();
		}
			catch(Exception e)
		{
				logger.info("catch exception:"+e);
				write.setRunby("admin");
				write.setJobStatus("FAILURE --"+e.toString());
			    write.setJobEndTime(Instant.now());
				write = migrationProcessStatusService.save(write);
			    write1.setTableLoadEndTime(Instant.now());
			    write1.setTableLoadStatus("FAILURE");
				write1 = migrationProcessJobStatusService.save(write1);
			    status = "FAILURE";
			
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
	public static void bulkprocess(String tableName,Connection con1, Connection con2, long jobid, long tableLoadid,long processid,String system,String dbname, String primarykey,MigrationProcessJobStatusService migrationProcessJobStatusService)
	throws SQLException, IOException{
	
	String query = new String();	
	query = "select * from " +tableName;
	logger.info("query is :"+query);
	String srcCols = stageLoad(query,con1,con2,tableName,system,dbname);
	histLoad(con2,tableName,"BULK", srcCols,jobid,tableLoadid,processid,primarykey,migrationProcessJobStatusService);	
	}
	public static void cdcprocess(String lastruntime,String tableName,Connection con1, Connection con2,long jobid, long tableLoadid,long processid, String system,String dbname,String primarykey,String columncdc,MigrationProcessJobStatusService migrationProcessJobStatusService) 
      throws SQLException, IOException
	{
		String query = new String();	
		logger.info("TN is:"+tableName);
		String c1 = columncdc.replace("[", "");
		String c2 = c1.replace("]", "");
		logger.info("New TN is:"+tableName);
		if(lastruntime.isEmpty())
		{
			logger.info("No job history available in sah_jobrunstatus.Cannot run CDC load.");
		}
		else {
		query = "select * from " +tableName+ " where "+c2+" >'" + lastruntime+"';";		
		String srcCols =stageLoad(query,con1,con2,tableName,system,dbname);
		histLoad(con2,tableName,"CDC", srcCols,jobid,tableLoadid,processid,primarykey,migrationProcessJobStatusService);		
		}
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
    	{;
    	 s = s.concat(rs9.getString(1));
    	 s = s.concat(",");
    	}
    	logger.info("getcolnames exit");
    	return s.substring(0,s.length()-1);
    }
    public static String stageLoad(String query, Connection con1,Connection con2,String tableName,String system,String dbname) throws SQLException, IOException
    {
    	Statement stmt1=con1.createStatement(); 
    	ResultSet rs1=stmt1.executeQuery(query);
       	String srcCols = getColNames2(con1,tableName,system,dbname);
		// String csvFilename = "./temp/"+tableName+".csv";
		String csvFilename = "F:/POC/CSV/"+tableName+".csv";
    	toCSV(rs1,csvFilename);   	
    	logger.info("Stage file writing complete");    	
       	Statement stmt2=con2.createStatement();
    	logger.info("create or replace stage "+tableName+"_stage copy_options = (on_error='skip_file') file_format = (type = 'CSV' field_delimiter = ',' skip_header = 1 FIELD_OPTIONALLY_ENCLOSED_BY = '\"');");
    	stmt2.executeQuery("create or replace stage "+tableName+"_stage copy_options = (on_error='skip_file') file_format = (type = 'CSV' field_delimiter = ',' skip_header = 1 FIELD_OPTIONALLY_ENCLOSED_BY = '\"');");    	
		stmt2.executeQuery("PUT 'file://F:/POC/CSV/"+tableName+".csv' @"+tableName+"_stage;");
		// stmt2.executeQuery("PUT 'file://./temp/"+tableName+".csv' @"+tableName+"_stage;");
    	int m = 1;
    	int k = srcCols.replaceAll("[^,]","").length() + 1;
    	String stageCols = "t.$1,";
    	while(m<k) {
    		m = m+1;
    		stageCols =stageCols + "t.$"+m+",";
    		}
    	stageCols =stageCols.substring(0,stageCols.length() - 1);
    	String hashCol = gethashColNames(stageCols);
		System.out.println(con1+":"+con2+":"+tableName+":"+system+":*:");
    	createAlterDDL(con1,con2,tableName,system);
    	logger.info("stagecols:"+stageCols);
		stmt2.executeQuery("TRUNCATE TABLE "+tableName);
		logger.info("Truncating "+tableName);
    	stmt2.executeQuery("INSERT INTO "+tableName+" SELECT "+stageCols+",0 as sah_isdeleted,MD5("+hashCol+") as sah_md5hash,sah_MD5HASH || '~' || TO_VARCHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISSFF6') as sah_md5hashpk FROM @"+tableName+"_stage t;"); 	
		logger.info("table creation  and loading (stg) complete");
		logger.info("srccols:"+srcCols);
		return srcCols;
    }
    public static void histLoad(Connection con2,String tableName,String process,String srcCols,long jobid,long tableLoadid,long processid,String primarykey,MigrationProcessJobStatusService migrationProcessJobStatusService) throws SQLException
    {
    	
    	Statement stmt3=con2.createStatement();
    	String pk = pkgenNew(primarykey);
    	String pk1 = pk.replace("src","a");
    	String pk2 = pk1.replace("tgt", "b");
    	int secondmergecount;
    //insert new record for Insert and update  
	logger.info("HIST LOAD START");  	
    	int firstmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using "+tableName+" src on "+pk+" and tgt.sah_md5hash = src.sah_MD5HASH and tgt.sah_currentind =1 when not matched then INSERT ("+srcCols +",sah_isdeleted,sah_currentind ,sah_createdTime,sah_updatedtime,sah_md5hash,sah_md5hashpk) VALUES (src."+srcCols.replace(",", ",src.")+",src.sah_isdeleted,1,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,src.sah_md5hash,src.sah_md5hashpk)");
    	logger.info("end of first merge" + firstmergecount);
    
    	if (process.equals("BULK"))
    	{
    	  //insert new record for delete
		        String delsrccols  = srcCols.replace(",sah_isdeleted","");
    			secondmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using(select a.* from "+tableName+"hist a left join "+tableName+" b ON "+pk2+" where a.sah_currentind =1  and a.sah_isdeleted <> 1 and b.sah_md5hashpk is null)src ON tgt.sah_md5hashpk = src.sah_md5hashpk and tgt.sah_isdeleted = 1 when not matched then INSERT ("+delsrccols+",sah_isdeleted,sah_currentind ,sah_createdTime,sah_updatedtime,sah_md5hash,sah_md5hashpk) VALUES (src."+delsrccols.replace(",", ",src.")+",1,1,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,src.sah_md5hash,src.sah_md5hashpk)");
				logger.info("merge into "+tableName+"hist tgt using(select a.* from "+tableName+"hist a left join "+tableName+" b ON "+pk2+" where a.sah_currentind =1  and a.sah_isdeleted <> 1 and b.sah_md5hashpk is null)src ON tgt.sah_md5hashpk = src.sah_md5hashpk and tgt.sah_isdeleted = 1 when not matched then INSERT ("+delsrccols+",sah_isdeleted,sah_currentind ,sah_createdTime,sah_updatedtime,sah_md5hash,sah_md5hashpk) VALUES (src."+delsrccols.replace(",", ",src.")+",1,1,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,src.sah_md5hash,src.sah_md5hashpk)");
    			logger.info("end of second merge" + secondmergecount);
         //expire previous record for delete
    			int thirdmergecount = stmt3.executeUpdate("merge into "+tableName+"hist tgt using(select a.* from "+tableName+"hist a left join "+tableName+" b ON "+pk2+" where a.sah_currentind =1  and a.sah_isdeleted <> 1 and b.sah_md5hashpk is null)src  ON tgt.sah_md5hashpk = src.sah_md5hashpk and tgt.sah_isdeleted <>1 and tgt.sah_currentind=1 when matched  then UPDATE SET tgt.sah_currentind = 0 , tgt.sah_updatedtime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP)");
    			logger.info("end of third merge" + thirdmergecount);
    	}
    	else
    	{
    		//insert new record for delete	
    			secondmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using "+tableName+" src on tgt.sah_currentind =1  and tgt.sah_isdeleted = 1 when not matched and src.sah_isdeleted = 1 then INSERT("+srcCols+",sah_isdeleted,sah_currentind ,sah_createdTime,sah_updatedtime,sah_md5hash,sah_md5hashpk) VALUES (src."+srcCols.replace(",", ",src.")+",src.sah_isdeleted,1,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,src.sah_md5hash,src.sah_md5hashpk)");
    			logger.info("end of second merge" + secondmergecount);
            //expire previous record for delete
    			int thirdmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using(select a.sah_md5hashpk from "+tableName+"hist a left join "+tableName+" b ON "+pk2+" where a.sah_currentind =1  and a.sah_isdeleted <> 1 and b.sah_isdeleted = 1)src  ON tgt.sah_md5hashpk = src.sah_md5hashpk and tgt.sah_isdeleted <>1 and tgt.sah_currentind=1 when matched  then UPDATE SET tgt.sah_currentind = 0 , tgt.sah_updatedtime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP)");
    			logger.info("end of third merge" + thirdmergecount);
    	}
    //expire previous record for update
    	int fourthmergecount = stmt3.executeUpdate("merge into "+tableName+"hist tgt using "+tableName+" src on "+pk+" and tgt.sah_md5hash <> src.sah_MD5HASH and tgt.sah_currentind = 1 when matched  then UPDATE SET tgt.sah_currentind = 0 ,tgt.sah_updatedtime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP)");
    	logger.info("end of fourth merge" + fourthmergecount);	
    	logger.info("Merge hist queries complete");
    	
    	int targetInsertCount = firstmergecount - fourthmergecount;
    	int targetUpdateCount = fourthmergecount;
    	int targetDeleteCount = secondmergecount;
    	
    	logger.info("Insert : "+ targetInsertCount);
    	logger.info("Update : "+ targetUpdateCount);
    	logger.info("Delete : "+ targetDeleteCount);
    	//st0.executeUpdate("UPDATE sah_tableLoadStatus SET insertcount ="+targetInsertCount+", updatecount ="+targetUpdateCount+",deletecount ="+targetDeleteCount+" WHERE jobid ="+jobid+" and tableLoadid ="+tableLoadid+" and processid ="+processid+";");
    	write1.setInsertCount((long)targetInsertCount);
    	write1.setUpdateCount((long)targetUpdateCount);
    	write1.setDeleteCount((long)targetDeleteCount);
    	write1 = migrationProcessJobStatusService.save(write1);
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
        String stagecreate = ddl6.concat(",sah_isdeleted INT,sah_MD5HASH TEXT,sah_MD5HASHPK TEXT);");		
		String histcreate = ddl6.concat(",sah_isdeleted INT,sah_currentind boolean,sah_updatedtime timestamp,sah_MD5HASH TEXT,sah_createdTime timestamp,sah_MD5HASHPK TEXT);");
		
		Statement stmt2 = con2.createStatement();
		logger.info("CREATE TABLE IF NOT EXISTS "+tableName+ stagecreate);
		logger.info("CREATE TABLE IF NOT EXISTS "+tableName+"hist "+histcreate);
		ResultSet rs2 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+ stagecreate);
		ResultSet rs3 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+"hist "+histcreate);
		
		
    	
    }
    public static String pkgenNew(String primarykey)
    {
	 try {
		
		int i = 1;
		String pk="";
		String[] prikey = primarykey.split("-");
		
			for(i = 0;i< prikey.length;i++) 
			{
					pk = pk.concat(" and tgt.");
					pk = pk.concat(prikey[i]);
					pk = pk.concat("=src.");
					pk = pk.concat(prikey[i]);
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
    

