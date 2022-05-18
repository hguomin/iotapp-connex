package dev.iotapp.connex.application.service.aziot;

public class AzSasToken {
    private String value;

    public AzSasToken(String value) {
        this.value = value;
    }
    
    public String get() {
        return value;
    }

    public void set(String value) {
        this.value = value;
    }
}
