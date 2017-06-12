import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;


public class PPMImageClass {
	
	
	public void HiddenMessage(String message){
		
		getFile f = new getFile();
		
		PPMImage ppm = new PPMImage(f.file);
		
		System.out.println("You encrypted " + "\"" + message + "\"" + " into " + f.file.getName());
		
		//isolate bit in letter you are currently using
		//checks if 8th bit is turned on
		// (1<< 7) -->  1000 0000
		
		int Channel_Position = 0; //Will start hiding message from beginning of char data in ppm image.
		
		for(int Letter_Position =0; Letter_Position < message.length(); Letter_Position++){
			
			for(int Message_Char_Bit=8; Message_Char_Bit>= 0; Message_Char_Bit--){
					
					//hide each bit from char into least significant bit in pixel data.
						
						if(((message.charAt(Letter_Position) & (1 << (Message_Char_Bit - 1))) == 0)){ //Bit is OFF
							
							//replace least significant digit from number to whatever the bit is in current bit for letter.
							//Only changing least significant bit from pixel data.
							
							//Turns OFF Least Significant bit in Color Channel
							ppm.getPixelData()[Channel_Position] &= ~1;
							
							
						}
						//Bit is ON
						else{
							
							
							//Turns ON Least Significant bit in Color Channel
						
							ppm.getPixelData()[Channel_Position] |= 1;
														
						}
					
					//iterate to next channel position to hide next bit.
					Channel_Position ++;	
			}
		}
		
		ppm.writeImage("ImageWithSecret.ppm");
		
	}

	
	public void recoverMessage(){
		
		getFile f = new getFile(); //filechooser
		
		//Select Image with hidden message.
		PPMImage ppm = new PPMImage(f.file);
		
		String empty = "";
		
		
		//Recursion to recover message.
		recoverMessage(0, empty , ppm);
		
	}
	
	private void recoverMessage(int position, String Secret_Message, PPMImage ppm){
		
		String bits = "";
		int pos = position;
		
		for(int i= 0; i< 8; i++){
			
			if((ppm.getPixelData()[pos] & 1) ==0){ //Bit was OFF
				bits += "0";
			}
			else{	//Bit was ON
				bits += "1";
			}
			pos +=1;
		}
		pos++;
		
		
		String[] tempBits = bits.split("");
		
		//after appending zeros
		char Current_Char = BinaryToChar(bits); 
		
		//base case to check if null character has been reached.
		if(Current_Char == '\0'){
			
			System.out.println("Secret Message is: " + Secret_Message);
		}
		else{
			
			//Append uncovered letter to final message
			Secret_Message += Character.toString(Current_Char); 
			
			recoverMessage(pos, Secret_Message, ppm);
			
		}
			
		
	}
	
	public void Negative(){
		
		getFile f = new getFile(); //filechooser
		
		PPMImage ppm = new PPMImage(f.file);
		
		int Channel_Position =0;
		
		for(int i=0; i< ppm.getPixelData().length; i++){
			
			ppm.getPixelData()[Channel_Position] =(char) (255 - (int)ppm.getPixelData()[Channel_Position]);
			Channel_Position++;
			
		}
		
		ppm.writeImage("NegativePinapple.ppm");
		
	}
	
	public void GrayScale(){
		
		getFile f = new getFile(); //filechooser
		
		PPMImage ppm = new PPMImage(f.file);
		
		int Channel_Position =0;
		int Color_Value =0;
		
		
		for(int i=0; i< ppm.getPixelData().length; i+=3){
			
			char redTemp = ppm.getPixelData()[i];
			char GreenTemp = ppm.getPixelData()[i+1];
			char BlueTemp = ppm.getPixelData()[i+2];
			
			int maxValue = 255;
			
			int finalValue = (int) ((redTemp * 0.299) + (GreenTemp * 0.587) + (BlueTemp * 0.114));
			
			//Check if resulting value is greater than 255 color range
			if(finalValue > maxValue){
				finalValue = maxValue;
			}
			
			ppm.getPixelData()[Color_Value] = (char) (finalValue);
			
			ppm.getPixelData()[Color_Value + 1] = (char) (finalValue);
			
			ppm.getPixelData()[Color_Value + 2] = (char) (finalValue);
			
			Channel_Position +=3;
			
			Color_Value+=3;
			
		}
		
		ppm.writeImage("GrayScaleDrink.ppm");
		
	}
	
	public void Sepia(){
		
		getFile f = new getFile(); //filechooser
		
		PPMImage ppm = new PPMImage(f.file);
		
		int Channel_Position =0;
		int Color_Value =0;
		
		
		for(int i=0; i< ppm.getPixelData().length; i+=3){
			
			char redTemp = ppm.getPixelData()[i];
			char GreenTemp = ppm.getPixelData()[i+1];
			char BlueTemp = ppm.getPixelData()[i+2];
			
			int maxValue = 255;
			
			int finalRedValue = (int) ((redTemp * 0.393) + (GreenTemp * 0.769) + (BlueTemp * 0.189));
			
			int finalGreenValue = (int) ((redTemp * 0.349) + (GreenTemp * 0.686) + (BlueTemp * 0.168));
			
			int finalBlueValue =  (int) ((redTemp * 0.272) + (GreenTemp * 0.534) + (BlueTemp * 0.131));
			
			
			//Check if resulting value is greater than 255 color range
			if(finalRedValue > maxValue){
				finalRedValue = maxValue;
			}
			
			if(finalGreenValue > maxValue){
				finalGreenValue = maxValue;
			}
			
			if(finalBlueValue > maxValue){
				finalBlueValue = maxValue;
			}
			
			
			ppm.getPixelData()[Color_Value] = (char) (finalRedValue);
			
			ppm.getPixelData()[Color_Value + 1] = (char) (finalGreenValue);
			
			ppm.getPixelData()[Color_Value + 2] = (char) (finalBlueValue);
			
			
			Channel_Position +=3;
			
			Color_Value+=3;
			
		}
		
		ppm.writeImage("SepiaDrink.ppm");
	}
	
	
	//This works for binary number where the format is correct meaning the significant digit is on the left.
	public char BinaryToChar(String bits){
		
		int count = 7; //Position of current bit
		int result = 0;
		String[] temp;
		
		temp = bits.split("");
		
		for(int i=0; i< bits.length(); i++){
			
			int current = Integer.parseInt(temp[i]);
			
			if(current >0){
				result += Math.pow(2, count);
			}
			count --;
			
		}
		
		
		return (char)result;
	}
	

}
