package controllers;

import readers.*;
import writers.FileWriter;
import writers.Writer;
import parsers.Parser;
import requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;
import java.io.*;
import java.io.File;
import readers.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormController implements Controller {
    private String METHODS_ALLOWED = "GET,POST,PUT,DELETE,OPTIONS";
    private String resourcePath;
    private Writer writer;
    private Reader reader;

    public FormController(Writer writer, Reader reader){
       this(null, writer, reader);
    }

    public FormController(String resourcePath, Writer writer, Reader reader) {
        if (resourcePath == null) {
            this.resourcePath = "../resources/main/form.txt";
            this.writer = writer;
            this.reader = reader;
        } else {
            this.resourcePath = resourcePath;
            this.writer = writer;
            this.reader = reader;
        }
    }

    public String handle(Request request) throws IOException {
        String response = "";
        if (METHODS_ALLOWED.contains(request.getHttpVerb())) {
            if (request.getHttpVerb().equals("GET")) {
                response = get(request);
            } else if (request.getHttpVerb().equals("POST")) {
                response = post(request);
            } else if (request.getHttpVerb().equals("PUT")) {
                response = put(request);
            } else if (request.getHttpVerb().equals("DELETE")) {
                response = delete();
            } else if (request.getHttpVerb().equals("OPTIONS")) {
                response = options();
            }
        } else {
            response = HttpStatus.methodNotAllowed;
        }

        return response;
    }

    private String get(Request request) throws IOException {
        String responseHead = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        String responseBody = reader.read(resourcePath);
        return responseHead + responseBody;
    }

    private String post(Request request) throws IOException {
        String response = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        String textToWrite = request.getParameters();
        writer.write(resourcePath, textToWrite);
        return response;
    }

    private String delete() {
        String response = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        writer.delete(resourcePath);
        return response;
    }

    private String put(Request request) throws IOException {
        String response = HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        String textToWrite = request.getParameters();
        writer.update(resourcePath, textToWrite);
        return response;
    }

    private String options() {
        return  HttpStatus.okay
                + EscapeCharacters.newline
                + "Allow: "
                + METHODS_ALLOWED
                + EscapeCharacters.newline
                + EscapeCharacters.newline;
    }


}
