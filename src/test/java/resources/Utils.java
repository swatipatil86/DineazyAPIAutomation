package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Utils {
    public RequestSpecification RequestSpecification() throws IOException
    {
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(getPropertiesValue("baseUri")).setContentType(ContentType.JSON).build();
        return req;
    }
    public static String getPropertiesValue(String key) throws IOException {
        Properties prop=new Properties();
        FileInputStream fis=new FileInputStream("src/test/java/resources/data.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

}