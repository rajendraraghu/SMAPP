package com.canny.snowflakemigration.service.util;

import java.io.*;
import java.util.List;
import com.opencsv.*;
import com.canny.snowflakemigration.service.dto.SnowDDLDTO;

public class convertDDL {

	public static String convertToSnowDDL(SnowDDLDTO processDTO) throws IOException
	{
		String status = null;
		try{
			String line,tblNm = null;
			boolean parse = false;
			BufferedWriter opSql = null;
			BufferedReader inpSql = new BufferedReader(new FileReader("./src/main/java/com/canny/snowflakemigration/service/util/resources/input.sql"));
			BufferedReader convFile = new BufferedReader(new FileReader("./src/main/java/com/canny/snowflakemigration/service/util/resources/conversion.csv"));
			BufferedReader ignoFile = new BufferedReader(new FileReader("./src/main/java/com/canny/snowflakemigration/service/util/resources/ignore.csv"));
			CSVReader convReader = new CSVReaderBuilder(convFile).withSkipLines(1).build();
			CSVReader ignoReader = new CSVReaderBuilder(ignoFile).withSkipLines(1).build();
			List<String[]> convRecords = convReader.readAll();
			List<String[]> ignoRecords = ignoReader.readAll();
			outer : while ((line = inpSql.readLine()) != null) {
				line = line.toLowerCase();
				if(line.contains("create table")) {
					int x = line.indexOf(" (");
					tblNm = line.substring(13,x).replace("`", "");
					if(parse) {
						opSql.write(");");
						opSql.close();
					}
					opSql = new BufferedWriter(new FileWriter("./src/main/java/com/canny/snowflakemigration/service/util/resources/DDL/"+tblNm+".sql"));
					parse = true;
				}
				for (String [] record : convRecords) {
					if (line.contains(record[0])) {
						line = line.replace(record[0], record[1]);
					}
				}
				for (String [] ignoRecord : ignoRecords) {
					if (line.contains(ignoRecord[0])) {
						continue outer;
					}
				}
				System.out.println(line);
				opSql.write(line);
				opSql.newLine();
			}
			opSql.write(");");
			opSql.close();
			inpSql.close();
			status = "SUCCESS";
		}
		catch(Exception e) {
			status = "FAILURE";
		}
	return status;
	}
}