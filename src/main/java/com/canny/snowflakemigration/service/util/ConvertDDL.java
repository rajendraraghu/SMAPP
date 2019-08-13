package com.canny.snowflakemigration.service.util;

import java.io.*;
import java.util.List;
import java.time.*;
import com.opencsv.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import com.canny.snowflakemigration.service.SnowDDLJobStatusService;
import com.canny.snowflakemigration.service.SnowDDLProcessStatusService;
import com.canny.snowflakemigration.service.dto.SnowDDLDTO;
import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowDDLJobStatusDTO;

public class ConvertDDL {

    private static SnowDDLProcessStatusDTO snowDDLProcessStatusDTO = new SnowDDLProcessStatusDTO();
    private static SnowDDLJobStatusDTO snowDDLJobStatusDTO = new SnowDDLJobStatusDTO();

	public static String convertToSnowDDL(SnowDDLDTO snowDDLDTO, SnowDDLProcessStatusService snowDDLProcessStatusService,SnowDDLJobStatusService snowDDLJobStatusService) throws IOException
	{
		String status = null;

		try{
			System.out.println("Start of try block");
			String line,tblNm = null;
			boolean parse = false;
			BufferedWriter opSql = null;
			String runBy = snowDDLDTO.getRunBy();
			String inpPath = snowDDLDTO.getSourcePath();
			String processName = snowDDLDTO.getName();
			System.out.println("Send to Process Status DTO:");
			System.out.println(snowDDLDTO.getId());
			snowDDLProcessStatusDTO.setProcessId(snowDDLDTO.getId());
			snowDDLProcessStatusDTO.setName(processName);
			snowDDLProcessStatusDTO.setRunBy(runBy);
			snowDDLProcessStatusDTO.setStartTime(Instant.now());
            snowDDLProcessStatusDTO.setStatus("INPROGRESS");
            // snowDDLProcessStatusDTO.setTotalObjects();
            // snowDDLProcessStatusDTO.setSuccessObjects();
            // snowDDLProcessStatusDTO.setErrorObjects();
			SnowDDLProcessStatusDTO write = snowDDLProcessStatusService.save(snowDDLProcessStatusDTO);
			System.out.println("Send to Process Status DTO Complete");
			System.out.println(write);
			Resource resource1 = new ClassPathResource("/snowddl_lib/conversion.csv");
			Resource resource2 = new ClassPathResource("/snowddl_lib/ignore.csv");
			BufferedReader inpSql = new BufferedReader(new FileReader(inpPath));
			BufferedReader convFile = new BufferedReader(new InputStreamReader(resource1.getInputStream()));
			BufferedReader ignoFile = new BufferedReader(new InputStreamReader(resource2.getInputStream()));
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
                    // snowDDLJobStatusDTO.setBatchId(write.getBatchId());
                    // snowDDLJobStatusDTO.setName(tblNm);
                    // snowDDLJobStatusDTO.setStartTime(Instant.now());
					if(parse) {
                        snowDDLJobStatusDTO.setEndTime(Instant.now());
                        snowDDLJobStatusDTO.setStatus("SUCCESS");
                        SnowDDLJobStatusDTO job_write = snowDDLJobStatusService.save(snowDDLJobStatusDTO);
						opSql.write(");");
						opSql.close();
                    }
                    snowDDLJobStatusDTO.setBatchId(write.getBatchId());
                    snowDDLJobStatusDTO.setName(tblNm);
                    snowDDLJobStatusDTO.setStartTime(Instant.now());
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
            snowDDLJobStatusDTO.setEndTime(Instant.now());
            snowDDLJobStatusDTO.setStatus("SUCCESS");
            SnowDDLJobStatusDTO job_write = snowDDLJobStatusService.save(snowDDLJobStatusDTO);
			opSql.write(");");
			opSql.close();
            inpSql.close();
            status = "SUCCESS";
            write.setStatus(status);
            write.setEndTime(Instant.now());
            write = snowDDLProcessStatusService.save(write);
		}
		catch(Exception e) {
            status = "FAILURE";
            //snowDDLProcessStatusDTO.setEndTime(Instant.now());
			System.out.println(e);
		}
	return status;
	}
}