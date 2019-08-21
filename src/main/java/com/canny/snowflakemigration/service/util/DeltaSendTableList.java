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
import com.canny.snowflakemigration.service.dto.DeltaProcessDTO;
//import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;
import com.opencsv.CSVWriter;


public class DeltaSendTableList  {
	public static String sendSelectedTables(DeltaProcessDTO processDTO) throws SQLException,ClassNotFoundException
	{
		String status = new String();
		try{
			int jobid=0;

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

		    Connection con3 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/smapp","postgres","password");
		    Statement st0 = con3.createStatement();

		    // st0.executeUpdate("CREATE TABLE IF NOT EXISTS delta_jobrunstatus (jobId int,JobStartTime timestamp, JobEndTime timestamp,JobStatus varchar(1000),processid bigint,runby varchar(300),tablecount int,SuccessCount int,FailureCount int);");
		    // st0.executeUpdate("CREATE TABLE IF NOT EXISTS delta_tableLoadStatus (jobid int,tableLoadid int,tablename varchar(50),tableLoadStartTime timestamp,tableLoadEndTime timestamp,tableLoadStatus varchar(15),insertcount int,updatecount int, deletecount int, runtype varchar(20),processid bigint,processname varchar(100),sourcename varchar(100),destname varchar(100));");
		    // System.out.println("tables created");

    		String pk1 = processDTO.getPk();
    		String tablestoMigrate = processDTO.getTablesList();
		    String[] pk2 = pk1.split(",");
		    String[] tablesToMigrate = tablestoMigrate.split(",");

		    int tablecount = tablesToMigrate.length;
//		    System.out.println("cdc array length :"+tableNamescdc.length);
//		    System.out.println("bulk array length :"+tableNamesbulk.length);

		    int i = 0;
		    int j = 0;
		    int tableLoadid = 0;
     		String lastruntime = new String();



		    ResultSet rs0 = st0.executeQuery("SELECT COALESCE(jobStartTime,now()::timestamp) FROM delta_jobrunstatus WHERE jobid = (SELECT COALESCE(MAX(jobId),0) FROM delta_jobrunstatus);");
		    Statement st1=con3.createStatement();
		    ResultSet rs9 = st1.executeQuery("SELECT COUNT(*) as cnt FROM delta_jobrunstatus");
		    rs9.next();
		    int cnt = rs9.getInt(1);
		    Statement stmt0=con2.createStatement();

		      if(cnt == 0) {lastruntime = "2005-12-31 00:00:00.000";}
		      else{rs0.next();lastruntime = rs0.getString(1);}
			  System.out.println("Lastruntime: "+ lastruntime);
		      System.out.println("rs0 complete");
		      ResultSet rs1 = st0.executeQuery("SELECT COALESCE(MAX(tableLoadId),0) as tableLoadId FROM delta_tableLoadStatus;");
		      if(rs1.next())
		      {
			    tableLoadid = rs1.getInt(1);
			    System.out.println("TableLoadId: "+ tableLoadid);
			  	System.out.println("rs1 complete");
		        ResultSet rs3 = st0.executeQuery("SELECT COALESCE(MAX(jobId),0) FROM delta_jobrunstatus");
		        if((rs3.next()))
		        {
		         jobid = rs3.getInt(1);
			     jobid = jobid + 1;
			     st0.executeUpdate("INSERT INTO delta_jobrunstatus VALUES("+jobid+",now()::timestamp,NULL,NULL,"+processDTO.getId()+",'"+processDTO.getRunBy()+"',"+tablecount+",0,0);");
                 String system = processDTO.getSourceConnectionName();
		       	//  while(i<tableNamescdc.length && tableNamescdc.length >0 && !tableNamescdc[i].equals("[]"))
		        //  {
			    //     System.out.println("length is :"+tableNamescdc.length+"i value is :"+i);
				// 	String tn = tableNamescdc[i].replace("[\"", "");
				// 	String tableName = tn.replaceAll("\"]|\"", "");
				// 	String key1  = pk2[i].replace("[\"", "");
				// 	String key2 = key1.replaceAll("\"]|\"", "");
		        //     st0.executeUpdate("INSERT INTO delta_tableLoadStatus VALUES("+jobid+","+tableLoadid+",'"+tableName+"',now()::timestamp,NULL,NULL,0,0,0,'cdc',"+processDTO.getId()+",'"+processDTO.getName()+"','"+processDTO.getSourceConnectionName()+"','"+processDTO.getSnowflakeConnectionName()+"');");
        	    // 	System.out.println("starting delta process for the table: "+tableName);
				// 	cdcprocess(lastruntime,tableName,con1,con2,jobid,tableLoadid,processDTO.getId(),system,processDTO.getSourceConnectionDatabase(),key2,col2);
				//     st0.executeUpdate("UPDATE delta_tableLoadStatus SET tableLoadEndTime = now()::timestamp , tableLoadStatus = 'SUCCESS' WHERE tableLoadid = "+tableLoadid+" AND jobid = "+ jobid +";");
				//     i= i +1;
				//     tableLoadid = tableLoadid + 1;
				//   }

        			}
			        else
			        {
			        	st0.executeUpdate("UPDATE delta_tableLoadStatus SET tableLoadEndTime = now()::timestamp , tableLoadStatus = 'FAILURE' WHERE jobid = "+jobid+";");
			        	status = "FAILURE";
			        }

		}
		status = (status=="FAILURE"?"FAILURE":"SUCCESS");
		ResultSet rs7 = st0.executeQuery("SELECT COUNT(tableName) as Successcnt FROM delta_tableLoadStatus WHERE jobid ="+jobid+" and processid = "+processDTO.getId()+" and tableLoadstatus = 'SUCCESS';");
	    rs7.next();
	    int successcnt = rs7.getInt("Successcnt");
	    ResultSet rs8 = st0.executeQuery("SELECT COUNT(tableName) as Failurecnt FROM delta_tableLoadStatus WHERE jobid ="+jobid+" and processid = "+processDTO.getId()+" and tableLoadstatus = 'FAILURE';");
	    rs8.next();
	    int failurecnt = rs8.getInt("Failurecnt");
		st0.executeUpdate("UPDATE delta_jobrunstatus SET JobEndTime = now()::timestamp , JobStatus ='"+status+"', SuccessCount = "+successcnt+" ,FailureCount = "+failurecnt+" WHERE jobid ="+jobid+";");

		con1.close();
		con2.close();
		}
			catch(Exception e)
		{
			System.out.println("catch exception:"+e);
			/*Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");
			Properties properties = new Properties();
			properties.put("user", processDTO.getSnowflakeConnectionUsername());
			properties.put("password", processDTO.getSnowflakeConnectionPassword());
			properties.put("account", processDTO.getSnowflakeConnectionAcct());
	        properties.put("warehouse",processDTO.getSnowflakeConnectionWarehouse());
			properties.put("db",processDTO.getSnowflakeConnectionDatabase());
		    properties.put("schema",processDTO.getSnowflakeConnectionSchema());
			Connection con2=DriverManager.getConnection(processDTO.getSnowflakeConnectionUrl(),properties);*/
			Connection con3 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/smapp","postgres","password");
			Statement st0 = con3.createStatement();
			System.out.println("UPDATE delta_jobrunstatus SET JobEndTime = now()::timestamp , JobStatus = 'FAILURE--"+e.toString().replace("'", "")+"' WHERE jobid = (SELECT MAX(jobId) FROM delta_jobrunstatus);");
			st0.executeUpdate("UPDATE delta_jobrunstatus SET JobEndTime = now()::timestamp , JobStatus = 'FAILURE--"+e.toString().replace("'", "")+"' WHERE jobid = (SELECT MAX(jobId) FROM delta_jobrunstatus);");
			st0.executeUpdate("UPDATE delta_tableLoadStatus SET tableLoadEndTime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) , tableLoadStatus = 'FAILURE' WHERE tableLoadid = (SELECT MAX(tableLoadId) FROM delta_tableLoadStatus);");
			status = "FAILURE - catch"+ e;

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
	public static void bulkprocess(String tableName,Connection con1, Connection con2, int jobid, int tableLoadid,long processid,String system,String dbname, String primarykey)
	throws SQLException, IOException{

	String query = new String();
	//String tn = tableName.replace("[\"", "");
	//String tn1 = tn.replace("\"]", "");
	query = "select * from " +tableName;
	System.out.println("query is :"+query);
	String srcCols = stageLoad(query,con1,con2,tableName,system,dbname);
	histLoad(con2,tableName,"BULK", srcCols,jobid,tableLoadid,processid,primarykey);
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
    public static String stageLoad(String query, Connection con1,Connection con2,String tableName,String system,String dbname) throws SQLException, IOException
    {
    	Statement stmt1=con1.createStatement();
    	ResultSet rs1=stmt1.executeQuery(query);
    	//String srcCols = getColNames(rs1);
    	String srcCols = getColNames2(con1,tableName,system,dbname);
    	//using local file now. Should be replaced with S3 or other filespace
    	String csvFilename = "F:/POC/CSV/"+tableName+".csv";
    	toCSV(rs1,csvFilename);
    	System.out.println("Stage file writing complete");
    	//This section moves file data to stage table and then to snow flake table(staging). Adds columns to hold MD5 hash values.
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
    	stmt2.executeQuery("INSERT INTO "+tableName+" SELECT "+stageCols+",0 as delta_isdeleted,MD5("+hashCol+") as delta_md5hash,delta_MD5HASH || '~' || TO_VARCHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISSFF6') as delta_md5hashpk FROM @"+tableName+"_stage t;");
		// stmt2.executeQuery("INSERT INTO "+tableName+" SELECT "+stageCols+",MD5("+hashCol+") as md5hash,MD5HASH || '~' || TO_VARCHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISSFF6') as md5hash_pk FROM @"+tableName+"_stage t;");
    	System.out.println("table creation  and loading (stg) complete");
    	return srcCols;
    }
    public static void histLoad(Connection con2,String tableName,String process,String srcCols,int jobid,int tableLoadid,long processid,String primarykey) throws SQLException
    {

    	Statement stmt3=con2.createStatement();
    	Connection con3 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/smapp","postgres","password");
		Statement st0 = con3.createStatement();

    	String pk = pkgenNew(primarykey);
    	String pk1 = pk.replace("src","a");
    	String pk2 = pk1.replace("tgt", "b");
    	int secondmergecount;
    //insert new record for Insert and update
	System.out.println("HIST LOAD START");
    	int firstmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using "+tableName+" src on "+pk+" and tgt.delta_md5hash = src.delta_MD5HASH and tgt.delta_currentind =1 when not matched then INSERT ("+srcCols +",delta_currentind ,delta_createdTime,delta_updatedtime,delta_md5hash,delta_md5hashpk) VALUES (src."+srcCols.replace(",", ",src.")+",1,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,src.delta_md5hash,src.delta_md5hashpk)");
    	System.out.println("end of first merge" + firstmergecount);

    	if (process.equals("BULK"))
    	{
    	  //insert new record for delete
    			secondmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using(select a.* from "+tableName+"hist a left join "+tableName+" b ON "+pk2+" where a.delta_currentind =1  and a.delta_isdeleted <> 1 and b.delta_md5hashpk is null)src ON tgt.delta_md5hashpk = src.delta_md5hashpk and tgt.delta_isdeleted = 1 when not matched then INSERT ("+srcCols+",delta_currentind ,delta_createdTime,delta_updatedtime,delta_md5hash,delta_md5hashpk) VALUES (src."+srcCols.replace(",", ",src.")+",1,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,src.delta_md5hash,src.delta_md5hashpk)");
    			System.out.println("end of second merge" + secondmergecount);
         //expire previous record for delete
    			int thirdmergecount = stmt3.executeUpdate("merge into "+tableName+"hist tgt using(select a.* from "+tableName+"hist a left join "+tableName+" b ON "+pk2+" where a.delta_currentind =1  and a.delta_isdeleted <> 1 and b.delta_md5hashpk is null)src  ON tgt.delta_md5hashpk = src.delta_md5hashpk and tgt.delta_isdeleted <>1 and tgt.delta_currentind=1 when matched  then UPDATE SET tgt.delta_currentind = 0 , tgt.delta_updatedtime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP)");
    			System.out.println("end of third merge" + thirdmergecount);
    	}
    	else
    	{
    		//insert new record for delete
    			secondmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using "+tableName+" src on tgt.delta_currentind =1  and tgt.delta_isdeleted = 1 when not matched and src.delta_isdeleted = 1 then INSERT("+srcCols+",delta_currentind ,delta_createdTime,delta_updatedtime,delta_md5hash,delta_md5hashpk) VALUES (src."+srcCols.replace(",", ",src.")+",1,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,src.delta_md5hash,src.delta_md5hashpk)");
    			System.out.println("end of second merge" + secondmergecount);
            //expire previous record for delete
    			int thirdmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using(select a.delta_md5hashpk from "+tableName+"hist a left join "+tableName+" b ON "+pk2+" where a.delta_currentind =1  and a.delta_isdeleted <> 1 and b.delta_isdeleted = 1)src  ON tgt.delta_md5hashpk = src.delta_md5hashpk and tgt.delta_isdeleted <>1 and tgt.delta_currentind=1 when matched  then UPDATE SET tgt.delta_currentind = 0 , tgt.delta_updatedtime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP)");
    			System.out.println("end of third merge" + thirdmergecount);
    	}
    //expire previous record for update
    	int fourthmergecount = stmt3.executeUpdate("merge into "+tableName+"hist tgt using "+tableName+" src on "+pk+" and tgt.delta_md5hash <> src.delta_MD5HASH and tgt.delta_currentind = 1 when matched  then UPDATE SET tgt.delta_currentind = 0 ,tgt.delta_updatedtime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP)");
    	System.out.println("end of fourth merge" + fourthmergecount);
    	System.out.println("Merge hist queries complete");

    	int targetInsertCount = firstmergecount - fourthmergecount;
    	int targetUpdateCount = fourthmergecount;
    	int targetDeleteCount = secondmergecount;

    	System.out.println("Insert : "+ targetInsertCount);
    	System.out.println("Update : "+ targetUpdateCount);
    	System.out.println("Delete : "+ targetDeleteCount);
    	st0.executeUpdate("UPDATE delta_tableLoadStatus SET insertcount ="+targetInsertCount+", updatecount ="+targetUpdateCount+",deletecount ="+targetDeleteCount+" WHERE jobid ="+jobid+" and tableLoadid ="+tableLoadid+" and processid ="+processid+";");
    }
    public static String pkgen(Connection con2,String tableName)
    {
	 try {
		Statement stmt2=con2.createStatement();
		stmt2.executeQuery("create or replace stage my_mysql_stage copy_options = (on_error='skip_file') file_format = (type = 'CSV' field_delimiter = ',' skip_header = 1 FIELD_OPTIONALLY_ENCLOSED_BY = '\"');");
		stmt2.executeQuery("PUT 'file://F:/POC/CSV/Snowparms.csv' @my_mysql_stage;");
		stmt2.executeQuery("CREATE or REPLACE TABLE Snowparms (tblname varchar(80),PK1 varchar(60),PK2 varchar(60),PK3 varchar(60),PK4 varchar(60),PK5 varchar(60),PK6 varchar(60),PK7 varchar(60),PK8 varchar(60),PK9 varchar(60),PK10 varchar(60));");
		stmt2.executeQuery("COPY INTO Snowparms FROM @my_mysql_stage;");
		System.out.println("copy table executed");

		ResultSet rs1 = stmt2.executeQuery("SELECT * FROM Snowparms WHERE tblname = '"+tableName+"';");
		int i = 1;
		String pk="";
		while(rs1.next())
	    {
			System.out.println("inside while loop");
			for(i = 2;i<12;i++)
			{
				if (rs1.getString(i)!= null)
				{
					pk = pk.concat(" and tgt.");
					pk = pk.concat(rs1.getString(i));
					pk = pk.concat("=src.");
					pk = pk.concat(rs1.getString(i));
				    System.out.println(pk);
				}
			}
		 System.out.println("out of for loop");
		 //System.out.println(pk.substring(5));
		}
		return pk.substring(5);
		}
	 catch(Exception e)
		{
		 System.out.println(e);
		 return ("Failure");
		}
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
		String stagecreate = ddl6.concat(",delta_isdeleted INT,delta_MD5HASH TEXT,delta_MD5HASHPK TEXT);");

		String histcreate = ddl6.concat(",delta_isdeleted INT,delta_currentind boolean,delta_updatedtime timestamp,delta_MD5HASH TEXT,delta_createdTime timestamp,delta_MD5HASHPK TEXT);");

		Statement stmt2 = con2.createStatement();
//		ResultSet rs2= stmt2.executeQuery("");
		System.out.println("CREATE TABLE IF NOT EXISTS "+tableName+ stagecreate);
		System.out.println("CREATE TABLE IF NOT EXISTS "+tableName+"hist "+histcreate);
		ResultSet rs2 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+ stagecreate);
		ResultSet rs3 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+"hist "+histcreate);



    }
  /*  public static void pkgen2( Connection con1,Connection con2,String tableName,String system,String dbname)
    {

        Statement stmt0 = con1.createStatement();
        if(system == 'MySQL') {ResultSet rs1 = stmt0.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_KEY = 'PRI' and TABLE_SCHEMA = '"+dbname+"' and TABLE_NAME = '"+tableName+"';");}
        else if (system == 'SQLServer') {ResultSet rs1 = stmt0.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_CATALOG = '"+dbname+"TABLE_NAME = '"+tableName+"';");}
    	//_v_sys_columns
    	int i = 1;
		String pk="";
		while(rs1.next())
	    {
			System.out.println("inside while loop");
			for(i = 2;i<12;i++)
			{
				if (rs1.getString(i)!= null)
				{
					pk = pk.concat(" and tgt.");
					pk = pk.concat(rs1.getString(i));
					pk = pk.concat("=src.");
					pk = pk.concat(rs1.getString(i));
				    System.out.println(pk);
				}
			}
		 System.out.println("out of for loop");
		}
		return pk.substring(5);
    }*/
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
				    System.out.println(pk);
			}
		System.out.println("pk:"+pk.substring(5));
		return pk.substring(5);
		}
	 catch(Exception e)
		{
		 System.out.println(e);
		 return ("Failure");
		}
     }
    }


