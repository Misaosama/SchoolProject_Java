package trying;

import java.io.*;

import View.Box;

public class SerializaitonDemo {
	
	public static void main(String[] args) throws IOException {
		
		Box a = new Box((float)10.0, (float)10.0, 30,30, (float)100.0, (float)100.0, (float)1.0, (long)123, 0 );
		String filename = "file.ser"; 
		BoxContainer bc = new BoxContainer(); bc.addBox(a);
		
		FileOutputStream file = new FileOutputStream(filename);
		FileInputStream file2 = new FileInputStream(filename); 
		ObjectOutputStream out = new ObjectOutputStream(file); 
		ObjectInputStream in =  new ObjectInputStream(file2);
        // Serialization  
        try
        {    
            //Saving of object in a file 
//            FileOutputStream file = new FileOutputStream(filename); 
//            out = new ObjectOutputStream(file); 
              
            // Method for serialization of object 
            out.writeObject(bc); 
            out.reset();  
            System.out.println("Object has been serialized"); 
  
        } 
          
        catch(IOException ex) 
        { 	
            System.out.println("IOException is caught"); 
            ex.printStackTrace();
        } 
  
  
        BoxContainer b = null; 
        
        // Deserialization 
        try
        {    
            // Reading the object from a file 
            // Method for deserialization of object 
            b = (BoxContainer)in.readObject();
//            in.reset();
              
            System.out.println("Object has been deserialized "); 
            System.out.println("width = " + b.boxes_.get(0).w); 
            System.out.println("height = " + b.boxes_.get(0).h); 
        }catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        }  
        
     // Part 2 
        try
        {    
            // Reading the object from a file 
              
            // Method for deserialization of object 
            a.moveDown();
            a.w = 1000;
            
//            out.close();
//            out = new ObjectOutputStream(file);
            BoxContainer bc2 = new BoxContainer();
            bc2.becomeThisBC(bc);
            
            out.writeObject(bc2);
            
            
            out.close(); 
            file.close(); 
              
//            System.out.println("Object has been deserialized "); 
//            System.out.println("width = " + b.boxes_.get(0).w); 
//            System.out.println("height = " + b.boxes_.get(0).h); 
        }catch(IOException ex) 
        { 
            System.out.println("IOException is caught");
            ex.printStackTrace();
        } 
        
     // Deserialization Part 2
        try
        {    
            // Reading the object from a file 
              
            // Method for deserialization of object 
//        	in = new ObjectInputStream(file2);
            b = (BoxContainer)in.readObject(); 
            
            in.close(); 
            file2.close(); 
              
            System.out.println("Object has been deserialized "); 
            System.out.println("width = " + b.boxes_.get(0).w); 
            System.out.println("height = " + b.boxes_.get(0).h); 
        }
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
		
	}
	
}
