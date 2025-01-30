## **FileManipulator

**Written by:** Hasin Israk Toaha(SSC 25)

### **What is the FileManipulator library.?**
Generally, Java has many built-in classes in JDK to manipulate files. But bigginer people get confused😕.So, you can use it easyly. 

### **How to use it**
Most easy, make object and call functions!

Example 1:

```Java
import com.toaha.file.manipulator.FileManipulator;
import java.lang.*;

public class Main
{
    public static void main(String [] args)
    {
         FileManipulator fm = new FileManipulator("test.txt" , "w");//write mode
         fm.write("Hello, World 🌍");
         
         String a = new String(new FileManipulator("test.txt", "r").read());
         System.out.println(a); //read file by  function read()
         System.out.println("File size is: " + fm.getSize() + " Bytes");
    }

}
```
