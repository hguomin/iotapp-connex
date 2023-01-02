//Guomin Huang @16/10/2021
package dev.iotapp.connex.application.messaging.websocket;
import dev.iotapp.connex.application.utils.Json;

import java.util.HashMap;
import java.util.Map;

public class WebsocketMessage {
    private String action;
    private Map<String, Object> data = new HashMap<>();
    private Map<String, Object> headers = new HashMap<>();
    
    public WebsocketMessage() {
        this.action = "";
    }

    public String getAction() {
        return action;
    }

    public void setAction(String id) {
        this.action = id;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public static WebsocketMessage fromJson(String json) {
        return Json.toObject(json, WebsocketMessage.class);
    }
}
