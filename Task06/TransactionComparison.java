package Task06;

import java.sql.*;

public class TransactionComparison {
    static final String URL = System.getenv().getOrDefault(
        "DB_URL", "jdbc:mysql://localhost:3306/transport_office");
    static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");

    static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    static void insertBatch(Connection con, String prefix, int start, int end,
                            String duplicateId) throws SQLException {
        String sql = "INSERT INTO bus_pass VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = start; i <= end; i++) {
                ps.setString(1, prefix + "_" + i);
                ps.setString(2, "192571068");
                ps.setString(3, "TestStudent" + i);
                ps.setInt(4, 5);
                ps.setDouble(5, 2500.0);
                ps.addBatch();
            }
            if (duplicateId != null) {
                ps.setString(1, duplicateId);
                ps.setString(2, "192571068");
                ps.setString(3, "DuplicateStudent");
                ps.setInt(4, 5);
                ps.setDouble(5, 2500.0);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    static int countRecords(Connection con, String prefix) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bus_pass WHERE pass_id LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    static void runTest(boolean transaction, String prefix) throws SQLException {
        try (Connection con = connect()) {
            con.setAutoCommit(!transaction);
            String firstId = prefix + "_1";
            try {
                insertBatch(con, prefix, 1, 3, null);
                insertBatch(con, prefix, 4, 4, firstId);
                if (transaction) con.commit();
                System.out.println("No failure occurred unexpectedly.");
            } catch (SQLException e) {
                System.out.println("Expected failure: " + e.getMessage());
                if (transaction) {
                    con.rollback();
                    System.out.println("Transaction rolled back.");
                }
            }
            con.setAutoCommit(true);
            System.out.println(
                (transaction ? "TRANSACTION" : "AUTO-COMMIT") +
                " remaining records: " + countRecords(con, prefix)
            );
        }
    }

    public static void main(String[] args) throws Exception {
        String id = "T" + System.currentTimeMillis();
        System.out.println("AUTO-COMMIT TEST");
        runTest(false, "A" + id);
        System.out.println();
        System.out.println("TRANSACTION TEST");
        runTest(true, "B" + id);
    }
}
