package Task06;
import java.sql.*;import java.util.*;
public class PassLoader {
 static Connection connect()throws SQLException{return DriverManager.getConnection(System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/transport_office"),System.getenv().getOrDefault("DB_USER","root"),System.getenv().getOrDefault("DB_PASSWORD",""));}
 static List<String[]> rows(int n){List<String[]> a=new ArrayList<>(n);for(int i=0;i<n;i++)a.add(new String[]{"P"+(100000+i),"19257"+String.format("%04d",i%10000),"Student"+i,""+(i%20+1),""+(2500+i%10*100)});return a;}
 static void clear(Connection c)throws SQLException{try(Statement s=c.createStatement()){s.executeUpdate("DELETE FROM bus_pass");}}
 static void bind(PreparedStatement p,String[] r)throws SQLException{p.setString(1,r[0]);p.setString(2,r[1]);p.setString(3,r[2]);p.setInt(4,Integer.parseInt(r[3]));p.setDouble(5,Double.parseDouble(r[4]));}
 static long stmt(Connection c,List<String[]> a)throws SQLException{clear(c);long t=System.nanoTime();try(Statement s=c.createStatement()){for(String[]r:a)s.executeUpdate("INSERT INTO bus_pass VALUES('"+r[0]+"','"+r[1]+"','"+r[2]+"',"+r[3]+","+r[4]+")");}return(System.nanoTime()-t)/1000000;}
 static long prep(Connection c,List<String[]> a)throws SQLException{clear(c);long t=System.nanoTime();try(PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass VALUES(?,?,?,?,?)")){for(String[]r:a){bind(p,r);p.executeUpdate();}}return(System.nanoTime()-t)/1000000;}
 static long batch(Connection c,List<String[]> a)throws SQLException{clear(c);long t=System.nanoTime();boolean old=c.getAutoCommit();c.setAutoCommit(false);try(PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass VALUES(?,?,?,?,?)")){int n=0;for(String[]r:a){bind(p,r);p.addBatch();if(++n%1000==0)p.executeBatch();}p.executeBatch();c.commit();}catch(SQLException e){c.rollback();throw e;}finally{c.setAutoCommit(old);}return(System.nanoTime()-t)/1000000;}
 public static void main(String[]x)throws Exception{try(Connection c=connect()){for(int n:new int[]{10,1000,10000,100000}){List<String[]>a=rows(n);System.out.println(n+" rows | Statement "+stmt(c,a)+" ms | Prepared "+prep(c,a)+" ms | Batch "+batch(c,a)+" ms");}
  try(Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT * FROM bus_pass LIMIT 1")){ResultSetMetaData m=r.getMetaData();for(int i=1;i<=m.getColumnCount();i++)System.out.println(m.getColumnName(i)+" : "+m.getColumnTypeName(i));}}}
}
