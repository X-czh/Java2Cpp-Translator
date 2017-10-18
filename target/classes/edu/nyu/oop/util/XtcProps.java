package edu.nyu.oop.util;

import java.io.IOException;
import java.util.Properties;

public class XtcProps {
  private static Properties properties = load();

  private static Properties load() {
    Properties properties = new Properties();
    try {
      properties.load(XtcProps.class.getClassLoader().getResourceAsStream("xtc.properties"));
    } catch (IOException e) {
      throw new RuntimeException("Unable to find file xtc.properties.");
    }
    return properties;
  }

  public static String get(String key) {
    return properties.getProperty(key);
  }

  public static String get(String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }

  public static String[] getList(String key) {
    return getList(key, "");
  }

  public static String[] getList(String key, String defaultValue) {
    String l = properties.getProperty(key, defaultValue);
    return l.split(",");
  }

  public static int getInt(String key) {
    return Integer.parseInt(properties.getProperty(key));
  }

  public static int getInt(String key, int defaultValue) {
    String dv = String.valueOf(defaultValue);
    return Integer.parseInt(properties.getProperty(key, dv));
  }

  // Feel free to add more property parsing methods here if necessary.

  public static Properties getProperties() {
    System.out.println();
    return properties;
  }
}