package pctimer.com; 

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner; 



public class FileUtil {
	
	
	
    public void fileWrite(String varWorkTime, String varBreakTime, String varFont) {
    	String location = System.getProperty("user.dir");
        System.out.println("Working Directory = " + location);
        Path path = Paths.get(location+"/src/pctimer/com/config/properties.txt");
        System.out.println(path.toString());
        List<String> lines = List.of(" WorkTime="+varWorkTime , "BreakTime="+varBreakTime,"     Font="+varFont);
        
        try {
            Files.write(path, lines, StandardCharsets.UTF_8);
            File myObj = new File(path.toString());
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                data = data.substring(10,12);
                System.out.println(data);
              }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}