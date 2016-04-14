package Requests;
public class Request {
    private String path;
    private String httpVerb;
    private String parameters;
    private String authorization;

    public Request(String path, String httpVerb, String body, String authorization) {
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
}
