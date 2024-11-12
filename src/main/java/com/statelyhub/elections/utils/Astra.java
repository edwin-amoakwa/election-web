
package com.statelyhub.elections.utils;

import java.io.File;

/**
 *
 * @author Edwin
 */
public class Astra {
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir") 
            + File.separator +"AstraERP_TempFiles" + File.separator;
    
    static{
        try {
            new File(TEMP_DIR).mkdirs();
        } catch (Exception e) {
        }
    }

}
