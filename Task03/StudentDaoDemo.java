package Task03;
import java.util.*;
public class StudentDaoDemo {
 static class Student{int id;String name,dept;Student(int i,String n,String d){id=i;name=n;dept=d;}public String toString(){return id+" | "+name+" | "+dept;}}
 interface Dao<T,K>{void insert(T x);Optional<T> findById(K k);List<T> findAll();boolean update(T x);boolean delete(K k);}
 static class StudentDao implements Dao<Student,Integer>{Map<Integer,Student> rows=new LinkedHashMap<>();
  public void insert(Student s){rows.put(s.id,s);}public Optional<Student> findById(Integer k){return Optional.ofNullable(rows.get(k));}
  public List<Student> findAll(){return new ArrayList<>(rows.values());}public boolean update(Student s){if(!rows.containsKey(s.id))return false;rows.put(s.id,s);return true;}public boolean delete(Integer k){return rows.remove(k)!=null;}}
 public static void main(String[]x){StudentDao d=new StudentDao();d.insert(new Student(101,"Asha","ECE"));d.insert(new Student(102,"Kavin","CSE"));
  System.out.println("Find 101: "+d.findById(101).orElse(null));d.findAll().forEach(System.out::println);d.update(new Student(102,"Kavin R","CSE"));System.out.println("Updated: "+d.findById(102).orElse(null));}
}
