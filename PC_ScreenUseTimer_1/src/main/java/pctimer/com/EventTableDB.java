package pctimer.com;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventTableDB {

    // Embedded URL: Creates 'eventHistoryDB' folder in the project directory if it doesn't exist
    private static final String DB_URL = "jdbc:derby:eventHistoryDB;create=true";
    private static final String SHUTDOWN_URL = "jdbc:derby:;shutdown=true";
    static String tableName = "events" ;
    static String checkSql = "SELECT COUNT(*) FROM SYS.SYSTABLES WHERE TABLENAME = ?";
    String createSql = "CREATE TABLE MY_TABLE (ID INT PRIMARY KEY, NAME VARCHAR(50))";
    static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
 
       static void setupTable() throws SQLException {
    	try (Connection conn = DriverManager.getConnection(DB_URL);
    			PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {   
    		 checkStmt.setString(1, tableName.toUpperCase());
    	  try (ResultSet rs = checkStmt.executeQuery()) {
    	        if (rs.next() && rs.getInt(1) == 0) {
				    	try (Statement stmt = conn.createStatement()) {
				                   String createTableSQL = "CREATE TABLE events ("
				            		//+ "id INT NOT NULL GENERATED ALWAYS AS IDENTITY,"
				                    + "status VARCHAR(50), "
				                    + "start   VARCHAR(50), "
				                   + "ends    VARCHAR(50), "
				                    + "gaps  VARCHAR(50),"
				                   + " selected  BOOLEAN DEFAULT FALSE "
				                   // + "PRIMARY KEY (id)"
				                    + ")";
				            
				            stmt.execute(createTableSQL);
				            System.out.println("Table 'events' initialized.");
				        }
    	        }
    	  }
    	}	  
    	
    }

 

   // CREATE Operation  
  

        public static void saveTableDataToSql() throws SQLException {
        setupTable();
            
            // Define your target SQL insert query
            String query = "INSERT INTO events (status, start , ends ,gaps ,selected ) VALUES (?, ?, ?,?,?)";
           
            int rowCount = MyThread.tableModel.getRowCount();
            int lastRow = rowCount-1;
             
            // Use try-with-resources to automatically close connections
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                
                // Set auto-commit to false to manage the batch transaction manually
                conn.setAutoCommit(false);

                // Iterate over all rows in the JTable model
                for (int i = 0; i < rowCount; i++) {
                    // Extract data using model.getValueAt(row, column)
                    // Use cast or toString() according to your SQL data types
                    String val1 = MyThread.tableModel.getValueAt(i, 0).toString();
                    String val2 = MyThread.tableModel.getValueAt(i, 1).toString();
                    boolean val5 = (boolean) MyThread.tableModel.getValueAt(i, 4);
                    
                    if(i!=lastRow) {
                    String val3 = MyThread.tableModel.getValueAt(i, 2).toString();
                    String val4 = MyThread.tableModel.getValueAt(i, 3).toString();
                 // Bind parameters to the query
                    pstmt.setString(1, val1);
                    pstmt.setString(2, val2);
                    pstmt.setString(3, val3);
                    pstmt.setString(4, val4);
                    pstmt.setBoolean(5, val5);
                    }else {
                	String val3 = LocalTime.now().format(myFormatObj);
                	String val4 =  Integer.toString((LocalTime.now().toSecondOfDay()- MyThread.currentLocalTime.toSecondOfDay())/60);
                	
                	// Bind parameters to the query
                    pstmt.setString(1, val1);
                    pstmt.setString(2, val2);
                    pstmt.setString(3, val3);
                    pstmt.setString(4, val4);
                    pstmt.setBoolean(5, val5);
                     }
                    

                    // Add to batch instead of executing individually for better speed
                    pstmt.addBatch();
                }

                // Execute all rows concurrently
                pstmt.executeBatch();
                conn.commit(); 
                System.out.println("Data saved successfully!");

            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
 

    // READ Operation
  public static void readevents() throws SQLException {
        String sql = "SELECT * FROM events";
        try (Connection conn = DriverManager.getConnection(DB_URL);Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
           
                Object[] data =     {rs.getString("status"), rs.getString("start"), rs.getString("ends"), rs.getString("gaps"), rs.getBoolean("selected")};     
                MyThread.tableModel.addRow(data);
             }
            String query = "DELETE FROM events ";
            int num = stmt.executeUpdate(query);
            System.out.println("Number of records deleted are: "+num);
                         
        
        }
    }

    // UPDATE Operation
    @SuppressWarnings("unused")
	private static void updateUserEmail() throws SQLException {
        String sql = "UPDATE events SET email = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);PreparedStatement pstmt = conn.prepareStatement(sql)) {
           // pstmt.setString(1, newEmail);
          //  pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    // DELETE Operation
    public static void deleteEvents() throws SQLException {
        String sql = "DELETE FROM events ";
        try (Connection conn = DriverManager.getConnection(DB_URL);
        		PreparedStatement pstmt = conn.prepareStatement(sql)) {
         //   pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // SHUTDOWN Operation
    @SuppressWarnings("unused")
	private static void shutdownDerby() {
        try {
            // Shutting down the entire Derby engine triggers a specific XJ015 exception on success
            DriverManager.getConnection(SHUTDOWN_URL);
        } catch (SQLException e) {
            if ("XJ015".equals(e.getSQLState())) {
                System.out.println("\nDerby embedded database shut down cleanly.");
            } else {
                e.printStackTrace();
            }
        }
    }
}
