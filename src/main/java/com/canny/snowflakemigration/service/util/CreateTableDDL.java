package com.canny.snowflakemigration.service.util;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.time.*;
import com.opencsv.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

public class CreateTableDDL {

    public static String convertToSnowDDL(String sourceType, String[] inpSql) throws IOException {
        Boolean status;
		String opSql;
        if(sourceType.equals("MySQL")) {opSql = "(";}
		else {opSql = "";}
        String errorMsg = "";
        try {
            System.out.println("Start of try block");
            System.out.println(sourceType);
            System.out.println(inpSql);
            String line, tblNm = null;
            boolean parse = false;
            Resource resource1 = new ClassPathResource("/snowddl_lib/" + sourceType.toLowerCase() + "_conversion.csv");
            Resource resource2 = new ClassPathResource("/snowddl_lib/" + sourceType.toLowerCase() + "_ignore.csv");
            BufferedReader convFile = new BufferedReader(new InputStreamReader(resource1.getInputStream()));
            BufferedReader ignoFile = new BufferedReader(new InputStreamReader(resource2.getInputStream()));
            CSVReader convReader = new CSVReaderBuilder(convFile).withSkipLines(1).build();
            CSVReader ignoReader = new CSVReaderBuilder(ignoFile).withSkipLines(1).build();
            List<String[]> convRecords = convReader.readAll();
            List<String[]> ignoRecords = ignoReader.readAll();
            System.out.println("Start of loops");
            List<String> splitDDL = new ArrayList<String>();
            String localline = "";
            System.out.println(inpSql.length);
            outer: for (int i = 1; i < inpSql.length-1; i++) {
                inpSql[i] = inpSql[i].toLowerCase();
                for (String[] record : convRecords) {
                    if (inpSql[i].contains(record[0])) {
                        inpSql[i] = inpSql[i].replace(record[0], record[1]);
                    }
                }

                for (String[] ignoRecord : ignoRecords) {
                    if (inpSql[i].contains(ignoRecord[0])) {
                        continue outer;
                    }
                }

                if(inpSql[i].contains("number")){
                    inpSql[i] = inpSql[i].replaceAll("'","");
                }
                opSql = opSql + inpSql[i];
            }
            status = true;

            // outer : while ((line = inpSql) != null) {
            // line = line.toLowerCase();
            // if(line.contains("create table")) {
            // int x = line.indexOf(" (");
            // tblNm = line.substring(13,x).replace("`", "");
            // if(parse) {
            // opSql.write(");");
            // opSql.close();
            // }
            // opSql = new BufferedWriter(new FileWriter("./snowddl_op/"+tblNm+".sql"));
            // parse = true;
            // }
            // for (String [] record : convRecords) {
            // if (line.contains(record[0])) {
            // line = line.replace(record[0], record[1]);
            // }
            // }
            // for (String [] ignoRecord : ignoRecords) {
            // if (line.contains(ignoRecord[0])) {
            // continue outer;
            // }
            // }
        } catch (Exception e) {
            status = false;
            errorMsg = "FAILURE - " + e;
            System.out.println(e);
        }
        return status ? opSql:errorMsg;
    }
}