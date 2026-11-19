package Task05;
import java.sql.*;
public class JdbcSafetyDemo {
 static Connection connect()throws SQLException{return DriverManager.getConnection(System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/transport_office"),System.getenv().getOrDefault("DB_USER","root"),System.getenv().getOrDefault("DB_PASSWORD",""));}
 public static void main(String[]x)throws Exception{try(Connection c=connect()){
  try(Statement s=c.createStatement()){s.executeUpdate("CREATE TABLE IF NOT EXISTS demo_users(id VARCHAR(40) PRIMARY KEY,pwd VARCHAR(80) NOT NULL)");s.executeUpdate("DELETE FROM demo_users");s.executeUpdate("INSERT INTO demo_users VALUES('student1','correct-secret')");}
  String attack="' OR '1'='1";String sql="SELECT COUNT(*) FROM demo_users WHERE id='nobody' AND pwd='"+attack+"'";
  try(Statement s=c.createStatement();ResultSet r=s.executeQuery(sql)){r.next();System.out.println("Unsafe login accepted: "+(r.getInt(1)>0));}
  try(PreparedStatement p=c.prepareStatement("SELECT COUNT(*) FROM demo_users WHERE id=? AND pwd=?")){p.setString(1,"nobody");p.setString(2,attack);try(ResultSet r=p.executeQuery()){r.next();System.out.println("PreparedStatement accepted: "+(r.getInt(1)>0));}}
  c.setAutoCommit(false);try(Statement s=c.createStatement()){s.executeUpdate("UPDATE wallet SET balance=balance-500 WHERE wallet_id='HOSTEL'");s.executeUpdate("UPDATE wallet SET balance=balance+500 WHERE wallet_id='CANTEEN'");c.commit();System.out.println("Transfer committed.");}
  catch(SQLException e){c.rollback();throw e;}finally{c.setAutoCommit(true);}
 }}
}
