package com.canny.snowflakemigration.service.util;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.time.*;
import com.opencsv.*;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.ClassPathResource;

import com.canny.snowflakemigration.service.impl.SnowDDLProcessStatusServiceImpl;
import com.canny.snowflakemigration.service.mapper.SnowDDLProcessStatusMapper;
import com.canny.snowflakemigration.service.mapper.SnowDDLProcessStatusMapperImpl;
import com.canny.snowflakemigration.domain.SnowDDLProcessStatus;
import com.canny.snowflakemigration.repository.SnowDDLProcessStatusRepository;
import com.canny.snowflakemigration.service.SnowDDLProcessStatusService;
import com.canny.snowflakemigration.service.dto.SnowDDLDTO;
import com.canny.snowflakemigration.service.dto.SnowDDLProcessStatusDTO;
import com.canny.snowflakemigration.service.dto.SnowDDLJobStatusDTO;

public class ConvertDDL {

	// private SnowDDLProcessStatusService snowDDLProcessStatusService;

	// private SnowDDLProcessStatusDTO snowDDLProcessStatusDTO;

	// public convertDDL(SnowDDLProcessStatusService snowDDLProcessStatusService,SnowDDLProcessStatusDTO snowDDLProcessStatusDTO) {
	//  	this.snowDDLProcessStatusService = snowDDLProcessStatusService;
	//  	this.snowDDLProcessStatusDTO = snowDDLProcessStatusDTO;
	//  }

	public static String convertToSnowDDL(SnowDDLDTO snowDDLDTO) throws IOException
	{
		String status = null;
		// SnowDDLProcessStatusDTO snowDDLProcessStatusDTO = new SnowDDLProcessStatusDTO();
		// SnowDDLProcessStatusRepository snowDDLProcessStatusRepository = new SnowDDLProcessStatusRepository();
		// SnowDDLProcessStatusMapper snowDDLProcessStatusMapper = new SnowDDLProcessStatusMapperImpl();
		// SnowDDLProcessStatusService snowDDLProcessStatusService = new SnowDDLProcessStatusServiceImpl(snowDDLProcessStatusRepository, snowDDLProcessStatusMapper);
		try{
			System.out.println("Start of try block");
			String line,tblNm = null;
			boolean parse = false;
			BufferedWriter opSql = null;
			String runBy = snowDDLDTO.getRunBy();
			String inpPath = snowDDLDTO.getSourcePath();
			String processName = snowDDLDTO.getName();
			// snowDDLProcessStatusDTO.setProcessId(snowDDLDTO.getId());
			// snowDDLProcessStatusDTO.setName(processName);
			// snowDDLProcessStatusDTO.setRunby(runBy);
			// snowDDLProcessStatusDTO.setStartTime(Instant.now());
			// return snowDDLProcessStatusDTO;
			// SnowDDLProcessStatusDTO write = snowDDLProcessStatusService.save(snowDDLProcessStatusDTO);
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