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
import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;
//import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;
import com.opencsv.CSVWriter;


public class sendTableList  {
	public static String sendSelectedTables(MigrationProcessDTO processDTO) throws SQLException,ClassNotFoundException
	{
		String status = new String();
		try{
			int jobid=0;
			 
	    Connection con1 = DriverManager.getConnection(processDTO.getSourceConnectionUrl(), processDTO.getSourceConnectionUsername(), processDTO.getSourceConnectionPassword());
	    Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");  
		Properties properties = new Properties();
		properties.put("user", processDTO.getSnowflakeConnectionUsername());
		properties.put("password", processDTO.getSnowflakeConnectionPassword());
		properties.put("account", processDTO.getSnowflakeConnectionAcct());
        properties.put("warehouse",processDTO.getSnowflakeConnectionWarehouse());
		properties.put("db",processDTO.getSnowflakeConnectionDatabase());
	    properties.put("schema",processDTO.getSnowflakeConnectionSchema());
		Connection con2=DriverManager.getConnection(processDTO.getSnowflakeConnectionUrl(),properties);
		
		String cdc = processDTO.getCdc();
		String bulk = processDTO.getBulk();
		String[] tableNamescdc = cdc.split(",");
		String[] tableNamesbulk = bulk.split(",");
		
		System.out.println("cdc array length :"+tableNamescdc.length);
		System.out.println("cdc array length :"+tableNamesbulk.length);
		
		int i = 0;
		int j = 0;
		int tableLoadid = 0;
		//int flag = 0;
		//String query = new String();
		String lastruntime = new String();
		
		
		Statement stmt0=con2.createStatement(); 
		ResultSet rs0 = stmt0.executeQuery("SELECT IFNULL(jobStartTime,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP)) FROM jobRunstatus WHERE jobid = (SELECT IFNULL(MAX(jobId),0) FROM jobRunStatus);");
		if(rs0.next())
		    {
			  lastruntime = rs0.getString(1);
		      System.out.println("Lastruntime: "+ lastruntime);
		      System.out.println("rs0 complete");
		      ResultSet rs1 = stmt0.executeQuery("SELECT IFNULL(MAX(tableLoadId),0) as tableLoadId FROM tableLoadStatus;");
		      if(rs1.next()) 
		      {
			    tableLoadid = rs1.getInt(1);
			    System.out.println("TableLoadId: "+ tableLoadid);
			  	System.out.println("rs1 complete");
		        ResultSet rs3 = stmt0.executeQuery("SELECT IFNULL(MAX(jobId),0) FROM jobRunStatus");
		        if(rs3.next())
		        {
			     jobid = rs3.getInt(1);
			     jobid = jobid + 1;
			     stmt0.executeQuery("INSERT INTO jobRunstatus VALUES("+jobid+",TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,NULL,"+processDTO.getId()+");");

		       	 while(i<tableNamescdc.length && !status.equals("FAILURE")&& tableNamescdc.length >0 && !tableNamescdc[i].equals("[]"))
		         {
			        System.out.println("length is :"+tableNamescdc.length+"i value is :"+i);
		            stmt0.executeQuery("INSERT INTO tableLoadStatus VALUES("+jobid+","+tableLoadid+",'"+tableNamescdc[i]+"',TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,NULL,0,0,0,NULL,"+processDTO.getId()+",'"+processDTO.getName()+"','"+processDTO.getSourceConnectionName()+"','"+processDTO.getSnowflakeConnectionName()+"');");
        	    	System.out.println("starting cdc delta process for the table: "+tableNamescdc[i]);
				    cdcprocess(lastruntime,tableNamescdc[i],con1,con2,jobid,tableLoadid,processDTO.getId());
				    stmt0.executeQuery("UPDATE tableLoadStatus SET tableLoadEndTime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) , tableLoadStatus = 'SUCCESS' WHERE tableLoadid = "+tableLoadid+";");
				    i= i +1;
				    tableLoadid = tableLoadid + 1;
				  }
		       	 while(j<tableNamesbulk.length && !status.equals("FAILURE")&& tableNamesbulk.length >0 && !tableNamesbulk[j].equals("[]"))
		         {
			        System.out.println("length is :"+tableNamesbulk.length+"i value is :"+i);
		            stmt0.executeQuery("INSERT INTO tableLoadStatus VALUES("+jobid+","+tableLoadid+",'"+tableNamesbulk[j]+"',TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,NULL,0,0,0,NULL,"+processDTO.getId()+",'"+processDTO.getName()+"','"+processDTO.getSourceConnectionName()+"','"+processDTO.getSnowflakeConnectionName()+"');");
        	    	System.out.println("starting cdc delta process for the table: "+tableNamesbulk[j]);
        	    	bulkprocess(tableNamesbulk[j],con1,con2,jobid,tableLoadid,processDTO.getId());
				    stmt0.executeQuery("UPDATE tableLoadStatus SET tableLoadEndTime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) , tableLoadStatus = 'SUCCESS' WHERE tableLoadid = "+tableLoadid+";");
				    j = j+1;
				    tableLoadid = tableLoadid + 1;
				  }	
				
        			}
			        else
			        {
			        	stmt0.executeQuery("UPDATE tableLoadStatus SET tableLoadEndTime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) , tableLoadStatus = 'FAILURE' WHERE tableLoadid = "+tableLoadid+";");
			        	status = "FAILURE";
			        }
			        

			
		}
		       	 
		stmt0.executeQuery("UPDATE jobRunstatus SET JobEndTime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) , JobStatus = 'SUCCESS' WHERE jobid ="+jobid+";");
		status = (status=="FAILURE"?"FAILURE":"SUCCESS");

    }
   else
   {
	   status = "FAILURE - first if loop";
   }
	        
			     
		
		con1.close();
		con2.close();
		}
		
		
			catch(Exception e)
		{
			System.out.println("catch exception:"+e);
			Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");
			Properties properties = new Properties();
			properties.put("user", processDTO.getSnowflakeConnectionUsername());
			properties.put("password", processDTO.getSnowflakeConnectionPassword());
			properties.put("account", processDTO.getSnowflakeConnectionAcct());
	        properties.put("warehouse",processDTO.getSnowflakeConnectionWarehouse());
			properties.put("db",processDTO.getSnowflakeConnectionDatabase());
		    properties.put("schema",processDTO.getSnowflakeConnectionSchema());
			Connection con2=DriverManager.getConnection(processDTO.getSnowflakeConnectionUrl(),properties);
			Statement stmt0 = con2.createStatement();
			stmt0.executeQuery("UPDATE jobRunStatus SET JobEndTime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) , JobStatus = 'FAILURE' WHERE jobid = (SELECT MAX(jobId) FROM jobRunStatus);");
			status = "FAILURE - catch";
			
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
	public static void bulkprocess(String tableName,Connection con1, Connection con2, int jobid, int tableLoadid,long processid)
	throws SQLException, IOException{
	
	String query = new String();	
	String tn = tableName.replace("[\"", "");
	String tn1 = tn.replace("\"]", "");
	query = "select * from " +tn1;
	String srcCols = stageLoad(query,con1,con2,tn1);
	histLoad(con2,tn1,"BULK", srcCols,jobid,tableLoadid,processid);	

	}
	public static void cdcprocess(String lastruntime,String tableName,Connection con1, Connection con2,int jobid, int tableLoadid,long processid) 
      throws SQLException, IOException
	{
		String query = new String();	
		System.out.println("TN is:"+tableName);
		String tn = tableName.replace("[\"", "");
		String tn1 = tn.replace("\"]", "");
		System.out.println("New TN is:"+tn1);
		
		//cdc column to be made dynamic
		lastruntime = "2005-12-31 00:00:00.000";
		if(lastruntime.isEmpty())
		{
			System.out.println("No job history available in JobRunStatus.Cannot run CDC load.");
		}
		else {
		query = "select * from " +tn1+ " where payment_date >'" + lastruntime+"';";
		String srcCols =stageLoad(query,con1,con2,tn1);
		histLoad(con2,tn1,"CDC", srcCols,jobid,tableLoadid,processid);		
		}
	}
    public static String getColNames(ResultSet rs) throws SQLException {
    	ResultSetMetaData rsmd = rs.getMetaData();
    	int columnCount = rsmd.getColumnCount();
    	String ColName = new String();
    	ColName = ",";
    	for (int i = 1; i <= columnCount; i++ ) {
    		  ColName = ColName.concat(rsmd.getColumnName(i));
    		  ColName = ColName.concat(",");    		  
    		}
    	return ColName.substring(1,ColName.length() - 1);
    }
    public static String gethashColNames(String ColName) {
    	String hashColName = ColName.replace(",","||'~'||");
    	return hashColName;
    }
    public static Connection getConnection2(String source) throws SQLException {
    	 try {
    		 	if(source.equals("Snowflake"))	      Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");
    		 	else if (source.equals("MySQL"))      Class.forName("com.mysql.cj.jdbc.Driver");
    		 	else if (source.equals("SQLServer"))  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		 	else if (source.equals("Oracle"))     Class.forName("oracle.jdbc.driver.OracleDriver");
    		 	else if (source.equals("Teradata"))   Class.forName("com.teradata.jdbc.TeraDriver");
   	     } 
    	 catch (ClassNotFoundException ex) {
   	      System.err.println("Driver not found");
   	    }

   	    // build connection properties
   	    Properties properties = new Properties();
   	    properties.put("user", "Sowmya");     
   	    properties.put("password", "Canny@831007"); 
   	    //properties.put("account", "iv97465");   
   	    //properties.put("warehouse", "COMPUTE_WH"); 
   	    //properties.put("db", "DEMO_DB");     
   	    //properties.put("schema", "PUBLIC");  
   	   
   	    String connectStr = new String();
	 	if(source.equals("Snowflake"))	      connectStr = "jdbc:snowflake://iv97465.east-us-2.azure.snowflakecomputing.com";
	 	else if (source.equals("MySQL"))      connectStr = "jdbc:mysql://localhost:3306/mysqldemodb";
	 	else if (source.equals("SQLServer"))  connectStr = "jdbc:sqlserver://localhost:1433;";
	 	else if (source.equals("Oracle"))     connectStr = "jdbc:oracle:thin:@localhost:1521/orcl";
	 	else if (source.equals("Teradata"))   connectStr = "jdbc:teradata://tdwprod.egtry.com";
   	 
   	    
   	    return DriverManager.getConnection(connectStr, properties);
    	
    }
    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException{
    	FileInputStream fis=new FileInputStream("connection.prop");
    	Properties p=new Properties (); 
        p.load (fis); 
        String dname= (String) p.get ("Dname"); 
        String url= (String) p.get ("URL"); 
        String username= (String) p.get ("Uname"); 
        String password= (String) p.get ("password"); 
        Class.forName(dname); 
        
        return DriverManager.getConnection(url, username, password); 
    }
    public static String getColNames2(Connection con, String tablename, String dname) throws SQLException
    {
    	String s = new String();
    	Statement stmt0  = con.createStatement();
    	ResultSet rs9 ;
    	if(dname.contains("oracle")) 
    		rs9 = stmt0.executeQuery("SELECT Column_Name FROM  All_Tab_Columns WHERE Table_Name = '"+tablename+"'");    		
    	else
    		rs9 = stmt0.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+tablename+"'"); 
    	while(rs9.next())
    	{
    	 s = s.concat(rs9.getString(1));
    	}
    	return s;
    }
    public static String stageLoad(String query, Connection con1,Connection con2,String tableName) throws SQLException, IOException
    {
    	Statement stmt1=con1.createStatement(); 
    	ResultSet rs1=stmt1.executeQuery(query);
    	String srcCols = getColNames(rs1);
    	//using local file now. Should be replaced with S3 or other filespace
    	String csvFilename = "F:/POC/CSV/"+tableName+".csv";
    	toCSV(rs1,csvFilename);   	
    	System.out.println("Stage file writing complete");
    	
    	//This section moves file data to stage table and then to snow flake table(staging). Adds columns to hold MD5 hash values.
    	Statement stmt2=con2.createStatement();
    	stmt2.executeQuery("create or replace stage "+tableName+"_stage copy_options = (on_error='skip_file') file_format = (type = 'CSV' field_delimiter = ',' skip_header = 1 FIELD_OPTIONALLY_ENCLOSED_BY = '\"');");
    	stmt2.executeQuery("PUT 'file://F:/POC/CSV/"+tableName+".csv' @"+tableName+"_stage;");
    	int j = 1;
    	int k = srcCols.replaceAll("[^,]","").length();
    	String stageCols = "t.$1,";
    	while(j<k) {
    		j = j+1;
    		stageCols =stageCols + "t.$"+j+",";
    		}
    	stageCols =stageCols.substring(0,stageCols.length() - 1);
    	String hashCol = gethashColNames(stageCols);
    	//createAlterDDL(Connection con2,String tableName);
    	stmt2.executeQuery("INSERT INTO "+tableName+" SELECT "+stageCols+",MD5("+hashCol+") as md5hash,MD5HASH || '~' || TO_VARCHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISSFF6') as md5hash_pk, 0 as isdeleted FROM @"+tableName+"_stage t;"); 	
    	System.out.println("table creation  and loading (stg) complete");
    	return srcCols;
    }
    public static void histLoad(Connection con2,String tableName,String process,String srcCols,int jobid,int tableLoadid,long processid) throws SQLException
    {
    	
    	Statement stmt3=con2.createStatement();
    	String pk = pkgen(con2,tableName);
    	String pk1 = pk.replace("src","a");
    	String pk2 = pk1.replace("tgt", "b");
    	int secondmergecount;
    //insert new record for Insert and update    	
    	int firstmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using "+tableName+" src on "+pk+" and tgt.md5hash = src.MD5HASH and tgt.current_ind =1 when not matched then INSERT ("+srcCols +",current_ind ,createdTime,updatedtime,md5hash,md5hash_pk) VALUES (src."+srcCols.replace(",", ",src.")+",1,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,src.md5hash,src.md5hash_pk)");
    	System.out.println("end of first merge" + firstmergecount);
    
    	if (process.equals("BULK"))
    	{
    	  //insert new record for delete
    			secondmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using(select a.* from "+tableName+"hist a left join "+tableName+" b ON "+pk2+" where a.current_ind =1  and a.isdeleted <> 1 and b.md5hash_pk is null)src ON tgt.md5hash_pk = src.md5hash_pk and tgt.isdeleted = 1 when not matched then INSERT ("+srcCols+",current_ind ,createdTime,updatedtime,md5hash,md5hash_pk) VALUES (src."+srcCols.replace(",", ",src.")+",1,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,src.md5hash,src.md5hash_pk)");
    			System.out.println("end of second merge" + secondmergecount);
         //expire previous record for delete
    			int thirdmergecount = stmt3.executeUpdate("merge into "+tableName+"hist tgt using(select a.* from "+tableName+"hist a left join "+tableName+" b ON "+pk2+" where a.current_ind =1  and a.isdeleted <> 1 and b.md5hash_pk is null)src  ON tgt.md5hash_pk = src.md5hash_pk and tgt.isdeleted <>1 and tgt.current_ind=1 when matched  then UPDATE SET tgt.current_ind = 0 AND tgt.updatedtime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP)");
    			System.out.println("end of third merge" + thirdmergecount);
    	}
    	else
    	{
    		//insert new record for delete	
    			secondmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using "+tableName+" src on tgt.current_ind =1  and tgt.isdeleted = 1 when not matched and src.isdeleted = 1 then INSERT("+srcCols+",current_ind ,createdTime,updatedtime,md5hash,md5hash_pk) VALUES (src."+srcCols.replace(",", ",src.")+",1,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP),NULL,src.md5hash,src.md5hash_pk)");
    			System.out.println("end of second merge" + secondmergecount);
            //expire previous record for delete
    			int thirdmergecount =  stmt3.executeUpdate("merge into "+tableName+"hist tgt using(select a.md5hash_pk from "+tableName+"hist a left join "+tableName+" b ON "+pk2+" where a.current_ind =1  and a.isdeleted <> 1 and b.isdeleted = 1)src  ON tgt.md5hash_pk = src.md5hash_pk and tgt.isdeleted <>1 and tgt.current_ind=1 when matched  then UPDATE SET tgt.current_ind = 0 AND tgt.updatedtime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP)");
    			System.out.println("end of third merge" + thirdmergecount);
    	}
    //expire previous record for update
    	int fourthmergecount = stmt3.executeUpdate("merge into "+tableName+"hist tgt using "+tableName+" src on "+pk+" and tgt.md5hash <> src.MD5HASH and tgt.current_ind = 1 when matched  then UPDATE SET tgt.current_ind = 0 AND tgt.updatedtime = TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP)");
    	System.out.println("end of fourth merge" + fourthmergecount);	
    	System.out.println("Merge hist queries complete");
    	
    	int targetInsertCount = firstmergecount - fourthmergecount;
    	int targetUpdateCount = fourthmergecount;
    	int targetDeleteCount = secondmergecount;
    	
    	System.out.println("Insert : "+ targetInsertCount);
    	System.out.println("Update : "+ targetUpdateCount);
    	System.out.println("Delete : "+ targetDeleteCount);
    	stmt3.executeQuery("UPDATE tableLoadStatus SET insertcount ="+targetInsertCount+", updatecount ="+targetUpdateCount+",deletecount ="+targetDeleteCount+",runtype='"+process+"' WHERE jobid ="+jobid+" and tableLoadid ="+tableLoadid+" and processid ="+processid+";");
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
    	ResultSet rs1=stmt1.executeQuery("SHOW CREATE TABLE "+tableName);
    	rs1.next();
		String ddl = rs1.getString("Create Table");
		int ind1 = ddl.indexOf("ENGINE");
		String ddl2 = ddl.substring(0,ind1);
		String ddl3 = ddl2.replaceAll("int\\([0-9]+\\)","int");
		String ddl4 = ddl3.substring(ddl3.indexOf("("));
		String ddl5 = ddl4.replaceAll("`","");
		
		
		Statement stmt2 = con2.createStatement();
		ResultSet rs2= stmt2.executeQuery("");
		ResultSet rs3 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+ ddl5);
		
		
    	
    }

}
