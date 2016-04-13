package Mocks;

import Requests.Request;

public class MockRequest extends Request {
    private String path;
    private String httpVerb;
    private String body;

    public MockRequest(String path, String httpVerb, String body, String authorization) {
        super(path, httpVerb, body, authorization);
    }
}

