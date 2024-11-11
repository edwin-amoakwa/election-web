/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.constants;

import com.stately.common.api.MessageResolvable;

/**
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public enum UserDomain implements MessageResolvable {

    NATIONAL("National User", "National User"),
    //    ELECTORIAL_AREA("Viewer","Viewer"),
    //    ADMINISTRATIVE_AREA("Viewer","Viewer"),
    CONSTITUENCY("Consistuency", "Consistuency"),
    REGION("Region", "Region"),
    POLLING_STATION("Polling Station", "Polling Station");

    private final String code;
    private final String label;

    private UserDomain(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public boolean isRegionUser() {
        return this == REGION;
    }

    public boolean isConstituencyUser() {
        return this == CONSTITUENCY;
    }

    public boolean isNationalUser() {
        return this == NATIONAL;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

}
