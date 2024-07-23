import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Library {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/LibraryDB";
    private static final String JDBC_USER = "root"; // Replace with your MySQL username
    private static final String JDBC_PASSWORD = "Admin"; // Replace with your MySQL password

    public static void main(String[] args) {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Create the Books table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Books ("
                    + "book_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "book_name VARCHAR(100) NOT NULL, "
                    + "book_no VARCHAR(20) NOT NULL, "
                    + "issue_date DATE, "
                    + "return_date DATE, "
                    + "is_issued BOOLEAN NOT NULL DEFAULT FALSE)";
            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);

            // Insert 10 book records
            String insertSQL = "INSERT INTO Books (book_name, book_no, issue_date, return_date, is_issued) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            // Sample book data
            String[][] books = {
                {"The Great Gatsby", "B001", "2024-01-15", "2024-01-29", "true"},
                {"1984", "B002", null, null, "false"},
                {"To Kill a Mockingbird", "B003", "2024-02-01", "2024-02-15", "true"},
                {"Pride and Prejudice", "B004", null, null, "false"},
                {"The Catcher in the Rye", "B005", "2024-03-10", "2024-03-24", "true"},
                {"The Hobbit", "B006", null, null, "false"},
                {"Fahrenheit 451", "B007", "2024-04-05", "2024-04-19", "true"},
                {"Jane Eyre", "B008", null, null, "false"},
                {"Animal Farm", "B009", "2024-05-20", "2024-06-03", "true"},
                {"Moby-Dick", "B010", null, null, "false"}
            };

            for (String[] book : books) {
                preparedStatement.setString(1, book[0]);
                preparedStatement.setString(2, book[1]);
                if (book[2] != null) {
                    preparedStatement.setDate(3, java.sql.Date.valueOf(book[2]));
                } else {
                    preparedStatement.setNull(3, java.sql.Types.DATE);
                }
                if (book[3] != null) {
                    preparedStatement.setDate(4, java.sql.Date.valueOf(book[3]));
                } else {
                    preparedStatement.setNull(4, java.sql.Types.DATE);
                }
                preparedStatement.setBoolean(5, Boolean.parseBoolean(book[4]));
                preparedStatement.executeUpdate();
            }

            System.out.println("10 book records inserted successfully.");

            // Close the resources
            preparedStatement.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}