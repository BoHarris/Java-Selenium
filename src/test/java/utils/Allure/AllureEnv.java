package utils.Allure;

import utils.ConfigReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Properties;

public final class AllureEnv {
    private AllureEnv(){}

        public static void write(Properties extra) {
            try {
                File resultsDir = new File("target/allure-results");
                Files.createDirectories(resultsDir.toPath());

                Properties p = new Properties();

                p.setProperty("Base Url", ConfigReader.baseUrl());
                p.setProperty("Browser", ConfigReader.browser());
                p.setProperty("Headless", String.valueOf(ConfigReader.headless()));

                p.setProperty("OS", System.getProperty("os.name") + " " + System.getProperty("os.version"));
                p.setProperty("Java", System.getProperty("java.version"));
                p.setProperty("User", System.getProperty("user.name"));

                if (extra != null) p.putAll(extra);

                try (FileOutputStream fos = new FileOutputStream(new File(resultsDir, "environment.properties"))) {
                    p.store(fos, "Allure environment");
            }
        }catch (Exception ignored){}
    }
}

