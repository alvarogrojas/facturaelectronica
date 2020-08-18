package com.rfs.domain.factura;

import java.util.HashMap;

public class OptionSet {

    private HashMap<String,String> data = new HashMap<String, String>();

    public OptionSet() {
//        data.put("password","2222");
//        data.put("certificate","c:\\Users\\aagon\\Development\\310165487908.p12");
    }

    public String valueOf(String key) {
        return data.get(key);
    }

    public void setValue(String key, String value) {
        data.put(key,value);
    }
}
