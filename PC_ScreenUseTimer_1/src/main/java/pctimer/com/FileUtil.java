package pctimer.com; 

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; 



public class FileUtil {
	static int[]  fileDBData = {25,15,12,13,11}; 
		
	String varWorkTime =Integer.toString(fileDBData[0]);
	String varBreakTime =Integer.toString(fileDBData[1]);
	String varFont =Integer.toString(fileDBData[2]);
	
	String location = System.getProperty("user.dir");
	Path path1 = Paths.get(location+"/config/");
	
	Path path = Paths.get(location+"/config/properties.txt");
    File myObj;
    
    
    FileUtil() //constructor create file if dose not exits else read.
    {  	
    	try {
			Files.createDirectories(path1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
     myObj = new File(path.toString());
 if( 	myObj.exists())
    	{	fileRead();
    	   
	 
    	}
 else {fileWrite(fileDBData);
	     }
    	   
    } // read the file 
    
	    
    public void fileWrite(int[] DBIntArray ) {
    	    
        List<String> lines = new ArrayList<>();
        lines.add(" WorkTime="+Integer.toString(DBIntArray[0]));
        lines.add("BreakTime="+Integer.toString(DBIntArray[1]));
        lines.add("     Font="+Integer.toString(DBIntArray[2]));
        lines.add("look&feel="+Integer.toString(DBIntArray[3]));
        lines.add("Tolbarori="+Integer.toString(DBIntArray[4]));
        

        try {
        	File myObj = new File(path.toString());    
        	Files.write(path, lines, StandardCharsets.UTF_8);
            Scanner myReader = new Scanner(myObj);
            int i =0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                fileDBData[i]= Integer.parseInt(data.substring(10,12));
               
                 i++;
            }
                 myReader.close();
                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
	public  void fileRead() {
     try { 
    	File myObj = new File(path.toString());
        Scanner myReader = new Scanner(myObj);
        int i =0;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            fileDBData[i] = Integer.parseInt(data.substring(10,12));
            
        i++;
        }
        varWorkTime =Integer.toString(fileDBData[0]);  
        varBreakTime =Integer.toString(fileDBData[1]);
        varFont =Integer.toString(fileDBData[2]);
      
        
        myReader.close();
     
     }catch (IOException e) {
            e.printStackTrace();
        }
    	
    }//function ends here 

      
    
}