package ru.bellintegrator.practice.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 11.03.2018.
 */
public class CustomSuccessResponse {

    private Object data;


    public CustomSuccessResponse() {
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        data = map;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
