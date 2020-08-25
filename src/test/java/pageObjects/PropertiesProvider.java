package pageObjects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class PropertiesProvider {
	
	InputStream inputStream;

	public Properties prop;

	public PropertiesProvider(String propFileName)
	{
		try {
			this.setPropertiesReader(propFileName);
		} catch(Exception e) {
			System.out.println("Exception: " + e);
		}
	}
	
	public void setPropertiesReader(String propFileName) throws IOException {
		
		try {
			Properties prop = new Properties();
			 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			
			this.prop = prop;

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}
	
	public String getProperty(String propertyName)
	{
		return this.prop.getProperty(propertyName);
	}
	
	public List<String> getPropertiesAsArray(String key) {

	    List<String> result = new LinkedList<String>();

	    String value;

	    for(int i = 0; (value = this.prop.getProperty(key + "." + i)) != null; i++) {
	        result.add(value);
	    }

	    return result;
	}
	

}
