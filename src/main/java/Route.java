public class Route {
    private String url;
    private String httpVerb;

    public Route(String httpVerb, String url) {
        this.httpVerb = httpVerb;
        this.url = url;
    }

    public String getHttpVerb() {
        return httpVerb;
    }

    public String getUrl() {
        return url;
    }
}
