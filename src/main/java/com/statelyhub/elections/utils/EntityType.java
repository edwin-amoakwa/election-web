/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.utils;

import com.stately.common.api.MessageResolvable;

/**
 * @author Edwin
 */
public enum EntityType implements MessageResolvable {
  SUBMISSION("SUBMISSION", "Submission"),
  TRANSFER("TRANSFER", "Inventory Transfer");

  private final String label;
  private final String code;

  private EntityType(String code, String label) {
    this.label = label;
    this.code = code;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return label;
  }
  
    public static EntityType resolve(String classType) {
        try {
            return EntityType.valueOf(classType.toUpperCase());

        } catch (Exception e) {
        }
        return null;
    }
}
