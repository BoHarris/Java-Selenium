package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader{
    private static final Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")){
            props.load(fis);
        } catch (IOException e){
            System.err.println(("[ConfigReader] Could not load config.properties: " + e.getMessage()));
        }
    }

    private static String envOrProp(String key){
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) return sys;
        return props.getProperty(key);
    }

    public static String baseUrl(){return envOrProp("baseUrl");}
    public static String browser(){return envOrProp("browser");}
    public static boolean headless(){return Boolean.parseBoolean(envOrProp("headless"));}
    public static long implicitWaitSec(){return Long.parseLong(envOrProp("implicitWaitSec"));}
    public static long explicitWaitSec(){return Long.parseLong(envOrProp("explicitWaitSec"));}
    public static long pageLoadTimeoutSec(){return Long.parseLong(envOrProp("pageLoadTimeoutSec"));}
}