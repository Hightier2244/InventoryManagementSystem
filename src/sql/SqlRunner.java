package sql;

import javaClasses.DatabaseConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

//class to call, when the database is not initialized yet
public class SqlRunner {
    // function to run a sql file
    public static void runSqlFile(String fileName) throws IOException, SQLException {
        Connection con = DatabaseConnection.getConnection();
        Statement stmt = con.createStatement();

        BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(SqlRunner.class.getClassLoader().getResourceAsStream(fileName))
        ));
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line);
            if (line.endsWith(";")) {
                stmt.execute(sb.toString());
                sb.setLength(0);
            }
        }
        br.close();
        stmt.close();
        con.close();
    }

    public static void main(String[] args) {
        try {
            runSqlFile("sql/create_tables.sql");
            runSqlFile("sql/stored_procedures.sql");
            runSqlFile("sql/triggers.sql");
            runSqlFile("sql/function.sql");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
