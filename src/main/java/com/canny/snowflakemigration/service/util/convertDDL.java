package com.canny.snowflakemigration.service.util;

import java.io.*;
import java.util.List;
import com.opencsv.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import com.canny.snowflakemigration.service.dto.SnowDDLDTO;

public class convertDDL {

	public static String convertToSnowDDL(SnowDDLDTO processDTO) throws IOException
	{
		String status = null;
		try{
			System.out.println("Start of try block");
			String line,tblNm = null;
			boolean parse = false;
			BufferedWriter opSql = null;
			String inpPath = processDTO.getSourcePath();
			Resource resource1 = new ClassPathResource("/snowddl_lib/conversion.csv");
			Resource resource2 = new ClassPathResource("/snowddl_lib/ignore.csv");
			BufferedReader inpSql = new BufferedReader(new FileReader(inpPath));
			BufferedReader convFile = new BufferedReader(new FileReader(resource1.getFile().getPath()));
			BufferedReader ignoFile = new BufferedReader(new FileReader(resource2.getFile().getPath()));
			CSVReader convReader = new CSVReaderBuilder(convFile).withSkipLines(1).build();
			CSVReader ignoReader = new CSVReaderBuilder(ignoFile).withSkipLines(1).build();
			List<String[]> convRecords = convReader.readAll();
			List<String[]> ignoRecords = ignoReader.readAll();
			System.out.println("Start of loops");
			outer : while ((line = inpSql.readLine()) != null) {
				line = line.toLowerCase();
				if(line.contains("create table")) {
					int x = line.indexOf(" (");
					tblNm = line.substring(13,x).replace("`", "");
					if(parse) {
						opSql.write(");");
						opSql.close();
					}
					opSql = new BufferedWriter(new FileWriter("F:/POC/snowDDL/"+tblNm+".sql"));
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
			System.out.println(e);
		}
	return status;
	}
}