import java.net.*;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.*;
public class Wget {
	
	
	public static void main(String[] args)throws Exception 
	  { 
		String filename="";
		boolean file = false, asc = false, zip =false, gzip=false;
		
		for (String arguments:args)
		{
			System.out.println(arguments);
			
			if (file)
			{
				filename = arguments;
				file=false;
				
			}
			else 
			{
				if (arguments.equals("-f")) {
					file = true; 
				}else {
					if (arguments.equals("-a")) {
							asc = true; }
						else {
							if(arguments.equals("-z")) {
								zip = true; }
							else {
								if (arguments.equals("-gz")) {
									gzip = true; }
							}
						}
				}
			}
		}
		try {
			InputStream in = new FileInputStream(new File(filename));
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        StringBuilder out = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {	        	
	            out.append(line + "\n");
	            Mythread thread = new Mythread(line,asc,zip,gzip);
				thread.start();
	        }
	        System.out.println(out.toString());   //Prints the string content read from input stream
	        reader.close();
	  } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	  
	 }
}