package com.canny.snowflakemigration.service.util;

import com.canny.snowflakemigration.service.dto.SnowflakeConnectionDTO;
import com.canny.snowflakemigration.service.dto.SourceConnectionDTO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class TestConnection {
    public static boolean testConnectionSource(SourceConnectionDTO connectionDTO)
            throws SQLException, ClassNotFoundException {
        boolean result = false;
        String source = connectionDTO.getSourceType();
        System.out.println("Inside source test connection:" + source);
        try {
            if (source.toLowerCase().equals("flatfiles")) {
                String type = connectionDTO.getDatabase(), path = connectionDTO.getUrl();
                if (type.toLowerCase().equals("local")) {
                    File f = new File(path);
                    if (f.exists()) {
                        result = true;
                    } else {
                        System.out.println("Specified Path Doesnot Exists");
                        result = false;
                    }
                } else if (type.toLowerCase().equals("internalserver") | type.toLowerCase().equals("externalserver")) {
                    String host = connectionDTO.getHost(),  userName = connectionDTO.getUsername(), password = connectionDTO.getPassword();
                    int port = Integer.parseInt(connectionDTO.getPortNumber());
                    FTPClient ftpClient = new FTPClient();
                    ftpClient.connect(host, port);
                    result = ftpClient.login(userName, password);
                }
            } else {
                if (source.equals("Snowflake")) {
                    Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");
                } else if (source.equals("MySQL")) {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } else if (source.equals("SQLServer")) {
                    System.out.println("sql loop");
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                } else if (source.equals("Oracle:thin")) {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } else if (source.equals("Teradata")) {
                    Class.forName("com.teradata.jdbc.TeraDriver");
                }

                System.out.println("osql loop");
                // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con1 = DriverManager.getConnection(connectionDTO.getUrl(), connectionDTO.getUsername(),
                        connectionDTO.getPassword());
                result = con1.isValid(10);
                System.out.println("Inside try before connection");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            result = false;
        } finally {
            return result;
        }
    }

    public static boolean testConnectionDest(SnowflakeConnectionDTO connectionDTO)
            throws SQLException, ClassNotFoundException {
        boolean result = false;
        try {
            Connection con1 = DriverManager.getConnection(connectionDTO.getUrl(), connectionDTO.getUsername(),
                    connectionDTO.getPassword());
            result = con1.isValid(10);
        } catch (Exception e) {
            result = false;
        } finally {
            return result;
        }
    }

}
