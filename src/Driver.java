import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Driver {
	
	public static void main(String args[]){
		
		PPMImageClass ppmDriver = new PPMImageClass();
		
		String message = "hello world!!@##\0";
		
//		ppmDriver.HiddenMessage(message);
		
		/*Writes message to a new file called ImageWithSecret.ppm */
		//Prints message to console.
		ppmDriver.recoverMessage();		
		
		/* Writes images to source folder. */
//		ppmDriver.Negative();
		
//		ppmDriver.GrayScale();
		
//		ppmDriver.Sepia();
		
		
	}

}
