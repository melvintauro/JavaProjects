package graph.com;
 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveImage {
	 static BufferedImage img ;
	public SaveImage() throws IOException {
	 // Create a File object with the image path
	String location = System.getProperty("user.dir"); 
	Path path = Paths.get(location+"/src/pctimer/com/images/rotatingKnob.jpg");
	 File imageFile = new File(path.toString());
	 System.out.println(path.toString());
	  img = ImageIO.read(imageFile);
	}
	 // Read the file into a BufferedImage
     
	
    public static void main(String[] args) {
      
        
        try {
            // Specify the output file path
            File outputfile = new File("saved_image.png");
            
            // Save the image (image object, format name, destination file)
            ImageIO.write(img, "png", outputfile);
            
            System.out.println("Image saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }
}
