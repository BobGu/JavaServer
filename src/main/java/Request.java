public class Request {
    private String path;
    private String httpVerb;
    private String body;

    public Request(String path, String httpVerb, String body) {
        this.path = path;
        this.httpVerb = httpVerb;
        this.body = body;
    }

    public String getPath() {
        return path;
    }
    public String getHttpVerb() {
        return httpVerb;
    }
    public String getBody() {
        return body;
    }
}
