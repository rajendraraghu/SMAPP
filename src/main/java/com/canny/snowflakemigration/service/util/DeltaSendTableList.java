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
		status = "FAILURE";
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
            
		    System.out.println("conn created");
		    String pk1 = processDTO.getPk();
    		String tablestoMigrate = processDTO.getTablesList();
		    String[] pk2 = pk1.split(",");
		    String[] tablesToMigrate = tablestoMigrate.split(",");
		    System.out.println("variable initialization completed");
		    
		    Statement stmt0 = con1.createStatement();

		    int tablecount = tablesToMigrate.length;
		    String system = processDTO.getSourceType();
			System.out.println("system:"+system);
		    int i = 0;
		    while(i < tablecount)
		    {
		    	String tn = tablesToMigrate[i].replace("[\"", "");
				String tableName = tn.replaceAll("\"]|\"", "");
		        ResultSet rs1 = stmt0.executeQuery("SELECT * FROM "+tableName+";");
		        createAlterDDL(con1,con2,tableName);
		        if (rs1.next())
			     {			
					//using local file now. Should be replaced with S3 or other filespace
					String csvFilename = "F:/POC/CSV/"+tableName+".csv";
					String srcCols = getColNames2(con1,tableName,system,processDTO.getSourceConnectionDatabase());
					toCSV(rs1,csvFilename);	
					Statement stmt2=con2.createStatement();
					stmt2.executeQuery("create or replace stage "+tableName+"_stage copy_options = (on_error='skip_file') file_format = (type = 'CSV' field_delimiter = ',' skip_header = 1 FIELD_OPTIONALLY_ENCLOSED_BY = '\"');");
					stmt2.executeQuery("PUT 'file://F:/POC/CSV/"+tableName+".csv' @"+tableName+"_stage;");
					System.out.println("stage writing completed");
					int j = 1;
					int k = srcCols.replaceAll("[^,]","").length();
					String stageCols = "t.$1,";
					while(j<=k) {
						j = j+1;
						stageCols =stageCols + "t.$"+j+",";
						}
					stageCols =stageCols.substring(0,stageCols.length() - 1);
					String hashCol = gethashColNames(stageCols);
					System.out.println(stageCols);
					System.out.println(hashCol);
					ResultSet rs2 = stmt2.executeQuery("SELECT * FROM "+tableName+"_today;");
					if(rs2.next())
					{
						stmt2.executeUpdate("TRUNCATE TABLE "+tableName+ "_yesterday");
						System.out.println("INSERT INTO "+tableName+"_yesterday SELECT * FROM "+ tableName+ "_today");
						stmt2.executeQuery("INSERT INTO "+tableName+"_yesterday SELECT * FROM "+ tableName+ "_today");
						stmt2.executeQuery("TRUNCATE TABLE "+tableName+ "_today");
						System.out.println("INSERT INTO "+tableName+"_today SELECT "+stageCols+",MD5("+hashCol+") as delta_MD5HASH,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) as delta_CREATEDTIME FROM @"+tableName+"_stage t;");
						stmt2.executeQuery("INSERT INTO "+tableName+"_today SELECT "+stageCols+",MD5("+hashCol+") as delta_MD5HASH,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) as delta_CREATEDTIME FROM @"+tableName+"_stage t;");
					}
					else
					{
						System.out.println("INSERT INTO "+tableName+"_today SELECT "+stageCols+",MD5("+hashCol+") as delta_MD5HASH,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) as delta_CREATEDTIME FROM @"+tableName+"_stage t;");
						stmt2.executeQuery("INSERT INTO "+tableName+"_today SELECT "+stageCols+",MD5("+hashCol+") as delta_MD5HASH,TO_TIMESTAMP_NTZ(CURRENT_TIMESTAMP) as delta_CREATEDTIME FROM @"+tableName+"_stage t;");
					}
					System.out.println("INSERT INTO "+tableName+"_delta SELECT y.*,t.*,CASE WHEN y.id IS NULL THEN 'INSERT' WHEN t.id IS NULL THEN 'DELETE' WHEN y.id = t.id AND y.delta_MD5HASH <> t.delta_MD5HASH THEN 'UPDATE'  ELSE 'NO CHANGE' END AS CDC FROM "+tableName+"_yesterday y FULL OUTER JOIN "+tableName+"_today t ON y.id = t.id");
					stmt2.executeUpdate("INSERT INTO "+tableName+"_delta SELECT y.*,t.*,CASE WHEN y.id IS NULL THEN 'INSERT' WHEN t.id IS NULL THEN 'DELETE' WHEN y.id = t.id AND y.delta_MD5HASH <> t.delta_MD5HASH THEN 'UPDATE'  ELSE 'NO CHANGE' END AS CDC FROM "+tableName+"_yesterday y FULL OUTER JOIN "+tableName+"_today t ON y.id = t.id");
				}
				i = i+1;
			}
			status = "SUCCESS";
			con1.close();
			con2.close();			    
		}
			catch(Exception e)
		{
			System.out.println("catch exception:"+e);
			Connection con3 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/smapp","postgres","super");
			Statement st0 = con3.createStatement();
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
    	if(dname.contains("Oracle:thin"))
    		rs9 = stmt0.executeQuery("SELECT Column_Name FROM  All_Tab_Columns WHERE Table_Name = '"+tablename+"'");
    	else
    		rs9 = stmt0.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+tablename+"' AND TABLE_SCHEMA = '"+dbname+"';");
    	while(rs9.next())
    	{
    	 s = s.concat(rs9.getString(1));
    	 s = s.concat(",");
    	}
    	System.out.println("getcolnames exit");
		System.out.println(s.substring(0,s.length()-1));
    	return s.substring(0,s.length()-1);
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
		String stagecreate = ddl6.concat(",delta_MD5HASH TEXT,delta_createdTime timestamp);");
		String histcreate = ddl6.concat(",delta_MD5HASH TEXT,delta_createdTime timestamp);");

		Statement stmt2 = con2.createStatement();
		System.out.println("CREATE TABLE IF NOT EXISTS "+tableName+"_today "+ stagecreate);
		System.out.println("CREATE TABLE IF NOT EXISTS "+tableName+"_yesterday "+histcreate);
		ResultSet rs2 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+"_today "+ stagecreate);
		ResultSet rs3 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+"_yesterday "+histcreate);
		//ResultSet rs4 = stmt2.executeQuery("CREATE TABLE IF NOT EXISTS "+tableName+"_delta "+stagecreate + histcreate);
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
				    System.out.println(pk);
			}
		System.out.println("pk:"+pk.substring(5));
		return pk.substring(5);
		}
	 catch(Exception e)
		{
		 System.out.println(e.toString());
		 return ("Failure");
		}
     }
    }