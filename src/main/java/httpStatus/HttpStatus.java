package httpStatus;

public enum HttpStatus {

    OKAY ("HTTP/1.1 200 OK"),
    NOT_FOUND ("HTTP/1.1 404 Not Found"),
    REDIRECT ("HTTP/1.1 302 Found"),
    METHOD_NOT_ALLOWED ("HTTP/1.1 405 Method Not Allowed"),
    NOT_AUTHORIZED ("HTTP/1.1 401 Unauthorized"),
    PARTIAL_CONTENT ("HTTP/1.1 206 Partial Content"),
    NO_CONTENT ("HTTP/1.1 204 No Content");

    private final String responseCode;

    private HttpStatus(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return responseCode;
    }

}
