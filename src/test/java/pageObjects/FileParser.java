package pageObjects;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileParser {

	
	public static void extractFolder(String zipFile,String extractFolder) 
	{
	    try
	    {
	        int BUFFER = 2048;
	        File file = new File(zipFile);

	        ZipFile zip = new ZipFile(file);
	        String newPath = extractFolder;

	        new File(newPath).mkdir();
	        Enumeration zipFileEntries = zip.entries();

	        while (zipFileEntries.hasMoreElements())
	        {
	            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
	            String currentEntry = entry.getName();

	            File destFile = new File(newPath, currentEntry);
	            File destinationParent = destFile.getParentFile();

	            destinationParent.mkdirs();

	            if (!entry.isDirectory())
	            {
	                BufferedInputStream is = new BufferedInputStream(zip
	                .getInputStream(entry));
	                int currentByte;
	                byte data[] = new byte[BUFFER];

	                FileOutputStream fos = new FileOutputStream(destFile);
	                BufferedOutputStream dest = new BufferedOutputStream(fos,
	                BUFFER);

	                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
	                    dest.write(data, 0, currentByte);
	                }
	                dest.flush();
	                dest.close();
	                is.close();
	            }


	        }
	    }
	    catch (Exception e) 
	    {
	        System.out.println(e);
	    }

	}
	
	public static File[] listFilesMatching(File root, String regex) {
	    if(!root.isDirectory()) {
	        throw new IllegalArgumentException(root+" is no directory.");
	    }
	    final Pattern p = Pattern.compile(regex); 
	    return root.listFiles(new FileFilter(){
	        public boolean accept(File file) {
	            return p.matcher(file.getName()).matches();
	        }
	    });
	}
	
	
	public static List<String[]> readCSVdata(String filename) {
		List<String[]> data = new ArrayList<String[]>();
		String testRow;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while ((testRow = br.readLine()) != null) {
				String[] line = testRow.split(",");
				data.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found " + filename);
		} catch (IOException e) {
			System.out.println("ERROR: Could not read " + filename);
		}
		return data;
	}
	
	public static void clearDirectory(String path)
	{
	   
        File file = new File(path);
        File[] files = file.listFiles(); 
        
	        for (File f:files) {
	        	if (f.isFile())
	        	{ 
	    			f.delete();
        		}
	        	else{
	        	  System.out.println("cant delete a file due to open or error");
	          }
	        }
	}
	
}
