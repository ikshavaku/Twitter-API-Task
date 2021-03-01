package utilities;

import java.io.*;
import java.util.*;

public class ConfigReader {

    public String readEnvironmentProperties(String property) throws Exception {
        FileInputStream propertiesFile = new FileInputStream("src/main/resources/config.properties");
        String propertyValue = null;
        Properties prop = new Properties();
        prop.load(propertiesFile);
        propertyValue = prop.getProperty(property);
        return propertyValue;
    }

    public String getConsumerKey() throws Exception{
        return this.readEnvironmentProperties("consumerkey");
    }

    public  String getConsumerSecret() throws Exception {
        return this.readEnvironmentProperties("consumersecret");
    }

    public String getBaseURI() throws Exception {
        return this.readEnvironmentProperties("baseuri");
    }
}