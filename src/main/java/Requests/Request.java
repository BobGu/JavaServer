package Requests;
public class Request {
    private String path;
    private String httpVerb;
    private String parameters;

    public Request(String path, String httpVerb, String body) {
        this.path = path;
        this.httpVerb = httpVerb;
        this.parameters = body;
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
}
