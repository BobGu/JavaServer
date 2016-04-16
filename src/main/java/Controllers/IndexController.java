package controllers;

import parsers.Parser;
import requests.Request;
import httpStatus.HttpStatus;
import specialCharacters.EscapeCharacters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IndexController implements Controller {

    public String handle(Request request) throws IOException {
        return get();
    }

    public String get() throws IOException {
        String response = "";
        response += HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;

        File file = new File("../resources/main/index.html");
        InputStream fileStream = new FileInputStream(file);
        response += Parser.fileToText(fileStream);

        return response;
    }

}
