package requests;
public class Request {
    private String path;
    private String httpVerb;
    private String parameters;
    private String authorization;
    private String fullRequest;

    public Request(String fullRequest, String path, String httpVerb, String body, String authorization) {
        this.fullRequest = fullRequest;
        this.path = path;
        this.httpVerb = httpVerb;
        this.parameters = body;
        this.authorization = authorization;
    }

    public String getPath() {
        return path;
    }
    public String getHttpVerb() {
        return httpVerb;
    }
    public String getParameters() {
        return parameters;
    }
    public String getAuthorization() {
        return authorization;
    }
    public String getFullRequest() {
        return fullRequest;
    }

}
