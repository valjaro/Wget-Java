
import java.io.*;
import java.net.*;
import java.util.zip.*;

public class Mythread extends Thread {
	String url;
	String originalName = "";
	String extension = "";
	String extension2="";
	Boolean asci;
	Boolean gzi;
	Boolean zi;
	public Mythread(String st, Boolean asc, Boolean gzip, Boolean zip) {
		this.asci = asc;
		this.gzi = gzip;
		this.zi =zip;
		this.url = st;
	}
	public void copy( InputStream in, OutputStream out ) {
	    try {
	    	if (this.zi) {
	    		System.out.println(this.originalName);
	    		((ZipOutputStream)out).putNextEntry(new ZipEntry(this.originalName));
	    	}
	        int buf;
	        while((buf=in.read()) != -1){
	            out.write(buf);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
			System.out.println("Error");
	        e.printStackTrace();
	    }
	}
	public FileOutputStream getExtension(String filename) throws IOException {
		if (this.asci && (this.extension.equals(new String("html"))) ) {
			filename = filename + ".asc";
			this.originalName = filename;
		}
		if (this.zi) {
			//filename = filename + ".zip";
			extension2= extension2 + ".zip";
		}
		if (this.gzi) {
			filename = filename + ".gz";
			extension2 = extension2 + ".gz";
		}
		File outputFile = new File(filename + extension2);
		if (!outputFile.exists()) {
			outputFile.createNewFile();
		}
		return new FileOutputStream(outputFile);
	}
	
	public void run() {
		try {
			URL u = new URL(url);
			URLConnection connection = u.openConnection();
			connection.connect();
			String dir = u.getPath();
			String filename = dir.substring(dir.lastIndexOf('/') + 1);
			if (filename == "") {
				filename = "index.html";
			}
			int i = filename.lastIndexOf('.');
			if (i > 0) {
			     this.extension = filename.substring(i+1, filename.length());
			}
			this.originalName = filename;
			InputStream ins = connection.getInputStream();
			FileOutputStream ous = this.getExtension(filename);
			if (this.gzi) { //Comprovem si es arxiu gzip
				// -gz [-z] [-a]
				GZIPOutputStream gzous = new GZIPOutputStream(ous);
				if (this.zi) {
					ZipOutputStream zous = new ZipOutputStream(gzous);
					if (this.asci && (this.extension.equals(new String("html")))) {
						// -gz -z -a
						this.copy(new Html2Ascii(ins), zous);//copien
					} else {
						// -gz -z
						this.copy(ins, zous);//copien
					}
				} else {
					if (this.asci && (this.extension.equals(new String("html")))) {
						// -gz -a
						this.copy(new Html2Ascii(ins), gzous);//copiem
					} else {
						// -gz
						this.copy(ins, gzous);///copiem
					}
				}
				
			} else if (this.zi) { // comprobem si es arxiu zip
				ZipOutputStream zip_out = new ZipOutputStream(ous);
				if (this.asci && (this.extension.equals(new String("html"))))  {
					// -z -a
					this.copy(new Html2Ascii(ins), zip_out);//copiem
				} else {
					// -z
					this.copy(ins, zip_out);//copiem 
				}
			} else if (this.asci && (this.extension.equals(new String("html")))) {//comprovem si es arxiu ascii
				// -a
				this.copy(new Html2Ascii(ins), ous);
			} else {// Sense paràmetres
				this.copy(ins, ous);
			}
					
		} catch (MalformedURLException e) {
			System.out.println("URL malformada: " + e);
		} catch (IOException e) {
			System.out.println("Error de E/S: " + e);
		}
	}

	
}