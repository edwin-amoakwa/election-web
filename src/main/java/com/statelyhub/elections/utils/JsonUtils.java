/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.utils;

import com.google.gson.JsonObject;

/**
 * @author Edwin
 */
public class JsonUtils {
  public static String getAsString(JsonObject object, String field) {
    try {
      return object.get(field) != null ? object.get(field).getAsString() : null;
    } catch (Exception e) {
    }
    return null;
  }

  public static String getAsString(jakarta.json.JsonObject object, String field) {
    try {
      return object.get(field) != null ? object.getString(field) : null;
    } catch (Exception e) {
    }
    return null;
  }

  public static Double asDouble(JsonObject object, String field) {
    try {
      return object.get(field) != null ? object.get(field).getAsDouble() : null;
    } catch (Exception e) {
    }
    return null;
  }

  public static JsonObject asJsonObject(JsonObject object, String field) {
    try {
      return object.get(field) != null ? object.get(field).getAsJsonObject() : null;
    } catch (Exception e) {
    }
    return null;
  }

  public static Integer asInt(JsonObject object, String field) {
    try {
      return object.get(field) != null ? object.get(field).getAsInt() : null;
    } catch (Exception e) {
    }
    return null;
  }

  public static boolean asBoolean(jakarta.json.JsonObject object, String field) {
    try {
      return object.get(field) != null ? object.getBoolean(field) : null;
    } catch (Exception e) {
    }
    return false;
  }

  public static boolean asBoolean(JsonObject object, String field) {
    try {
      return object.get(field) != null ? object.get(field).getAsBoolean() : null;
    } catch (Exception e) {
    }
    return false;
  }
}
