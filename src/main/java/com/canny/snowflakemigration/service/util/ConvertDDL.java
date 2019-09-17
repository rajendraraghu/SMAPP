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
		SnowDDLProcessStatusDTO processStatus = new SnowDDLProcessStatusDTO();
		try{
			System.out.println("Start of try block");
			String line,tblNm = null;
			boolean parse = false;
			BufferedWriter opSql = null;
			String inpPath = snowDDLDTO.getSourcePath();
            String sourceType = snowDDLDTO.getSourceType();
			System.out.println("Send to Process Status DTO:");
			System.out.println(snowDDLDTO.getId());
			snowDDLProcessStatusDTO.setProcessId(snowDDLDTO.getId());
			snowDDLProcessStatusDTO.setName(snowDDLDTO.getName());
			snowDDLProcessStatusDTO.setRunBy(snowDDLDTO.getRunBy());
			snowDDLProcessStatusDTO.setStartTime(Instant.now());
            snowDDLProcessStatusDTO.setStatus("INPROGRESS");
            snowDDLProcessStatusDTO.setTotalObjects(3L);
            snowDDLProcessStatusDTO.setSuccessObjects(6L);
            snowDDLProcessStatusDTO.setErrorObjects(8L);
			processStatus = snowDDLProcessStatusService.save(snowDDLProcessStatusDTO);
			System.out.println("Send to Process Status DTO Complete");
            System.out.println(processStatus);
            sourceType = sourceType.replaceAll("\\s", "").toLowerCase();
			Resource resource1 = new ClassPathResource("/snowddl_lib/"+sourceType+"_conversion.csv");
			Resource resource2 = new ClassPathResource("/snowddl_lib/"+sourceType+"_ignore.csv");
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
					// totalcount = totalcount++;
					// System.out.println("Total objects count" + totalcount);
                    // snowDDLJobStatusDTO.setBatchId(write.getBatchId());
                    // snowDDLJobStatusDTO.setName(tblNm);
                    // snowDDLJobStatusDTO.setStartTime(Instant.now());
					if(parse) {
                        snowDDLJobStatusDTO.setEndTime(Instant.now());
                        snowDDLJobStatusDTO.setStatus("SUCCESS");
                        SnowDDLJobStatusDTO jobStatus = snowDDLJobStatusService.save(snowDDLJobStatusDTO);
						opSql.write(");");
						opSql.close();
                    }
                    snowDDLJobStatusDTO.setBatchId(processStatus.getBatchId());
                    snowDDLJobStatusDTO.setName(tblNm);
					snowDDLJobStatusDTO.setStartTime(Instant.now());
					// Resource resource3 = new ClassPathResource("/snowddl_lib/"+tblNm+".sql");
					opSql = new BufferedWriter(new FileWriter("./snowddl_op/"+tblNm+".sql"));
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
            SnowDDLJobStatusDTO jobStatus = snowDDLJobStatusService.save(snowDDLJobStatusDTO);
			opSql.write(");");
			opSql.close();
            inpSql.close();
            status = "SUCCESS";
            processStatus.setStatus(status);
            processStatus.setEndTime(Instant.now());
            processStatus = snowDDLProcessStatusService.save(processStatus);
		}
		catch(Exception e) {
			snowDDLJobStatusDTO.setEndTime(Instant.now());
			snowDDLJobStatusDTO.setStatus("FAILURE");
			SnowDDLJobStatusDTO jobStatus = snowDDLJobStatusService.save(snowDDLJobStatusDTO);
			status = "FAILURE";
			// SnowDDLJobStatusDTO jobStatus = snowDDLJobStatusService.save(snowDDLJobStatusDTO);
			processStatus.setStatus(status);
			processStatus.setEndTime(Instant.now());
			System.out.println(e);
			processStatus = snowDDLProcessStatusService.save(processStatus);
		}
	return status;
	}
}