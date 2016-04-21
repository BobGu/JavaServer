package requests;
public class Request {
    private String path;
    private String httpVerb;
    private String parameters;
    private String authorization;
    private boolean isFile;
    private boolean isImage;

    public Request(String path, String httpVerb, String body, String authorization, boolean isFile, boolean isImage) {
        this.path = path;
        this.httpVerb = httpVerb;
        this.parameters = body;
        this.authorization = authorization;
        this.isFile = isFile;
        this.isImage = isImage;
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
    public boolean getIsFile() {
        return isFile;
    }

    public boolean getIsImage() {
        return isImage;
    }

}
