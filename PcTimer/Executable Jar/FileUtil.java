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
	int[] fileDBData = {25,15,12}; 
		
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
    	{	fileRead();}else {fileWrite(varWorkTime, varBreakTime,varFont);}
    	   
    } // read the file 
    
	    
    public void fileWrite(String s1, String s2, String s3) {
    	    
        List<String> lines = new ArrayList<>();
        lines.add(" WorkTime="+s1);
        lines.add("BreakTime="+s2);
        lines.add("     Font="+s3);
        //List.of(" WorkTime="+varWorkTime , "BreakTime="+varBreakTime,"     Font="+varFont);
        //above line will work with java higher versions.
        try {
       // 	File myObj = new File(path.toString());    
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

    public  void fileRead() {
     try { 
   // 	File myObj = new File(path.toString());
        Scanner myReader = new Scanner(myObj);
        int i =0;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            fileDBData[i] = Integer.parseInt(data.substring(10,12));
           // System.out.print(fileDBData[i]);
        i++;
        }
        varWorkTime =Integer.toString(fileDBData[0]);  
        varBreakTime =Integer.toString(fileDBData[1]);
        varFont =Integer.toString(fileDBData[2]);
        System.out.println(fileDBData[0]+" fileRead " + fileDBData[1]  );
        
       // myReader.close();
     }catch (IOException e) {
            e.printStackTrace();
        }
    	
    }//function ends here 

      
    
}