package Task07;
import java.io.*;import java.nio.file.*;import java.sql.*;import java.util.*;
public class MigrationAndMetadata {
 public static void main(String[]x)throws Exception{String url=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/transport_office");
  try(Connection c=DriverManager.getConnection(url,System.getenv().getOrDefault("DB_USER","root"),System.getenv().getOrDefault("DB_PASSWORD",""))){
   DatabaseMetaData m=c.getMetaData();System.out.println("Database: "+m.getDatabaseProductName()+" "+m.getDatabaseProductVersion());System.out.println("Driver: "+m.getDriverName()+" "+m.getDriverVersion());
   try(ResultSet t=m.getTables(c.getCatalog(),null,"%",new String[]{"TABLE"})){while(t.next())System.out.println("Table: "+t.getString("TABLE_NAME"));}
   Path p=Paths.get(x.length>0?x[0]:"inputs/bus_passes.csv");String sql="INSERT INTO bus_pass VALUES(?,?,?,?,?)";int loaded=0,lineNo=0;List<String>bad=new ArrayList<>();boolean old=c.getAutoCommit();c.setAutoCommit(false);
   try(BufferedReader r=Files.newBufferedReader(p);PreparedStatement ps=c.prepareStatement(sql)){String line;while((line=r.readLine())!=null){lineNo++;String[]f=line.split(",",-1);try{if(f.length!=5)throw new IllegalArgumentException("expected 5 fields");ps.setString(1,f[0]);ps.setString(2,f[1]);ps.setString(3,f[2]);ps.setInt(4,Integer.parseInt(f[3]));ps.setDouble(5,Double.parseDouble(f[4]));ps.addBatch();loaded++;if(loaded%500==0)ps.executeBatch();}catch(Exception e){bad.add("Line "+lineNo+": "+e.getMessage());}}ps.executeBatch();c.commit();}
   catch(Exception e){c.rollback();throw e;}finally{c.setAutoCommit(old);}System.out.println("Loaded rows: "+loaded);System.out.println("Rejected rows: "+bad.size());bad.forEach(System.out::println);
  }
 }
}
