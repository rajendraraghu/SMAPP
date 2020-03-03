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
		String logPath = "logs/SnowDDL";
		String tmpPath = "snowddl_op";
		File logDir=new File(logPath);
		File tmpDir=new File(tmpPath);
		if(logDir.exists()==false)
		{
			logDir.mkdirs();
		}
		if(tmpDir.exists()==false)
		{
			tmpDir.mkdirs();
		}
		try{
			System.out.println("Start of try block");
			String line,tblNm = null;
			boolean parse = false;
			boolean parse1 = false;
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
			outer : while ((line = inpSql.readLine()) != null) {try{
				line = line.toLowerCase();
				line = line.replace("\"","");
				if (sourceType.contains("teradata")) {
					int d = 0;
					if(line.contains("create table")) {
						int count2 = 0;
						if (line.contains ("as")) {
							 d = line.indexOf("as");
							count2 = count2+1;	
						}
						int a = line.indexOf(",");
						int b = line.indexOf(".");
						int count1 = 0;
						int c = 0;
						while (b >= 0) {
							count1 = count1+1;
							c = b +1;
							System.out.println("Index of dot"+b);
							 b = line.indexOf('.', b+1);
						  }
							if (count1 == 0) {
								tblNm = line.substring(13,a-1).replace("`", "");
								System.out.println("Table name:" + tblNm);
							}
							else if (count1 > 0) {
								if (count2 > 0) {
									tblNm = line.substring(c,d-1).replace("`", "");
									System.out.println("Table name:" + tblNm);
								}
								else {
									tblNm = line.substring(c,a-1).replace("`", "");
									System.out.println("Table name:" + tblNm);
								}	
							}
							if(count1 > 0){
								String str = line.substring(13,c);
								System.out.println("substr:"+str);
								line = line.replace(str,"");
							}
							if(parse1) {
								if (count2 >0) {
									snowDDLJobStatusDTO.setEndTime(Instant.now());
									snowDDLJobStatusDTO.setStatus("FAILURE: Copying schema is not allowed");
									SnowDDLJobStatusDTO jobStatus = snowDDLJobStatusService.save(snowDDLJobStatusDTO);

								} else {
									snowDDLJobStatusDTO.setEndTime(Instant.now());
									snowDDLJobStatusDTO.setStatus("SUCCESS");
									SnowDDLJobStatusDTO jobStatus = snowDDLJobStatusService.save(snowDDLJobStatusDTO);
									opSql.write(";");
									opSql.close();
									}
							}
							// line = line.replace(",","(");
							snowDDLJobStatusDTO.setBatchId(processStatus.getBatchId());
							snowDDLJobStatusDTO.setName(tblNm);
							snowDDLJobStatusDTO.setStartTime(Instant.now());
							if (count2 >0) {
								parse1 = true;
								continue outer;
							} else {
							// Resource resource3 = new ClassPathResource("/snowddl_lib/"+tblNm+".sql");
							opSql = new BufferedWriter(new FileWriter("snowddl_op/"+tblNm+".sql"));
							parse1 = true;
							}
						} 
						}
				else {				 
				if(line.contains("create table")) {
					int x = line.indexOf(" (");
					 if(x==-1) { x = (line.length())-1;} 
					int y = line.indexOf(".");
					int count =0;
					int z = 0;
                    while(y >= 0) {
					   count = count+1;	
					   z = y+1;
                       System.out.println("y:"+y);
                       y = line.indexOf('.', y+1);
					 }
					System.out.println("y:"+y);
					System.out.println("count:"+count);
					if(count ==0){tblNm = line.substring(13,x).replace("`", "");}
					else if(count >0){tblNm = line.substring(z,x).replace("`", "");}
					System.out.println("tablename:"+tblNm);
					if(count > 0){
					String str = line.substring(13,z);
					System.out.println("substr:"+str);
					line = line.replace(str,"");}
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
					opSql = new BufferedWriter(new FileWriter("snowddl_op/"+tblNm+".sql"));
					parse = true;
				}
				else if(line.contains("create set table")) {
					int x = line.indexOf(" ,");
					if(x==-1) { x = (line.length())-1;} 
					/*int y = line.indexOf(".");
					int count =0;
					int z = 0;
                     while(y >= 0) {
					   count = count+1;	
					   z = y+1;
                       System.out.println("y:"+y);
                       y = line.indexOf('.', y+1);
					 }
					System.out.println("y:"+y);*/
					tblNm = line.substring(17,x);
					/*else if(count >0){tblNm = line.substring(z,x).replace("`", "");}*/
					System.out.println("tablename:"+tblNm);
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
					opSql = new BufferedWriter(new FileWriter("snowddl_op/"+tblNm+".sql"));
					parse = true;
				}
				else if(line.contains("create multiset table")) {
					int x = line.indexOf(" ,");
					if(x==-1) { x = (line.length())-1;} 
					tblNm = line.substring(22,x);
					System.out.println("tablename:"+tblNm);
					if(parse) {
                        snowDDLJobStatusDTO.setEndTime(Instant.now());
                        snowDDLJobStatusDTO.setStatus("SUCCESS");
                        SnowDDLJobStatusDTO jobStatus = snowDDLJobStatusService.save(snowDDLJobStatusDTO);
						opSql.write(");");
						opSql.close();
					}
				}
                    snowDDLJobStatusDTO.setBatchId(processStatus.getBatchId());
                    snowDDLJobStatusDTO.setName(tblNm);
					snowDDLJobStatusDTO.setStartTime(Instant.now());
					opSql = new BufferedWriter(new FileWriter("snowddl_op/"+tblNm+".sql"));
					parse = true;
				}
				System.out.println("line:"+line);
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
				System.out.println("line:"+line);
				opSql.write(line);
				opSql.newLine();
			}
			catch(Exception e)
			{
				System.out.println("exc:"+e.toString());
				snowDDLJobStatusDTO.setEndTime(Instant.now());
			    snowDDLJobStatusDTO.setStatus("FAILURE");
			    SnowDDLJobStatusDTO jobStatus = snowDDLJobStatusService.save(snowDDLJobStatusDTO);
				opSql.write(");");
			    opSql.close();
				continue;
			}		
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
			System.out.println("exc:"+e.toString());
			processStatus = snowDDLProcessStatusService.save(processStatus);
		}
	return status;
	}
}