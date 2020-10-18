package org.xsmart.system.util;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {

    private Map<String,Object> params = new HashMap<>();

    public RequestParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
