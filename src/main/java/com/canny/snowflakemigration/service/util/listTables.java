package com.canny.snowflakemigration.service.util;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.canny.snowflakemigration.service.dto.MigrationProcessDTO;
import java.util.ArrayList;
import java.util.Arrays;

public class listTables {
    public static String[] listTable(MigrationProcessDTO migrationProcessDTO) throws SQLException,ClassNotFoundException {
        Connection con = DriverManager.getConnection(migrationProcessDTO.getSourceConnectionUrl(), migrationProcessDTO.getSourceConnectionUsername(), migrationProcessDTO.getSourceConnectionPassword());
        Statement stmt = con.createStatement();
        ResultSet rs1 = stmt.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.Tables WHERE TABLE_SCHEMA = '"+migrationProcessDTO.getSourceConnectionDatabase()+"';");
        ArrayList tn = new ArrayList();
        while(rs1.next())
        {
            tn.add(rs1.getString("TABLE_NAME"));
        }
        String[] tableNames = (String[])tn.toArray(new String[tn.size()]);
        return tableNames;
    }
}