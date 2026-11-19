package Task04;
import java.io.*;import java.util.*;
public class SerializationDemo {
 static class Hostel implements Serializable{private static final long serialVersionUID=1L;List<Room> rooms=new ArrayList<>();}
 static class Room implements Serializable{private static final long serialVersionUID=1L;List<Resident> residents=new ArrayList<>();}
 static class Resident implements Serializable{private static final long serialVersionUID=1L;String name,reg;transient String password;transient Object liveConnection;
  Resident(String n,String r,String p){name=n;reg=r;password=p;liveConnection=new Object();}}
 public static void main(String[]x)throws Exception{Hostel h=new Hostel();Room room=new Room();room.residents.add(new Resident("Meena","192571068","secret"));h.rooms.add(room);
  File f=File.createTempFile("hostel",".ser");try(ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(f))){o.writeObject(h);}
  try(ObjectInputStream i=new ObjectInputStream(new FileInputStream(f))){Resident r=((Hostel)i.readObject()).rooms.get(0).residents.get(0);
   System.out.println("Resident: "+r.name+" / "+r.reg);System.out.println("Password after restore: "+r.password);System.out.println("Connection after restore: "+r.liveConnection);}f.deleteOnExit();}
}
