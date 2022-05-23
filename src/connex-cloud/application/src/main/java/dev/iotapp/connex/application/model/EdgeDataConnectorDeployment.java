package dev.iotapp.connex.application.model;

public class EdgeDataConnectorDeployment {
    private String image;
    private String createOptions;
    private String application;
    private String routes;

    public EdgeDataConnectorDeployment() {
        this.image = "";
        this.createOptions = "";
        this.application = "";
        this.routes = "";
    }

    public EdgeDataConnectorDeployment(String image, String createOptions, String application, String routes) {
        this.image = image;
        this.createOptions = createOptions;
        this.application = application;
        this.routes = routes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateOptions() {
        return createOptions;
    }

    public void setCreateOptions(String createOptions) {
        this.createOptions = createOptions;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }
}
