import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class getFile {
	
	File file;

	@SuppressWarnings("hiding")
	public getFile() {
		JButton open = new JButton();
		JFileChooser fc = new JFileChooser();

		
		FileNameExtensionFilter textFilter = new FileNameExtensionFilter("TEXT FILES",  "ppm", "text");
		
		fc.setFileFilter(textFilter);

		if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){
			
			this.file = fc.getSelectedFile();
				
		}
			
	}
}
