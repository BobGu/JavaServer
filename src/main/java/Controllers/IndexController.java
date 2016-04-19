package controllers;

import parsers.Parser;
import requests.Request;
import httpStatus.HttpStatus;
import resourceCRUD.DirectoryCRUD;
import resourceCRUD.ResourceCRUD;
import specialCharacters.EscapeCharacters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IndexController implements Controller {
    private ResourceCRUD resourceCRUD;
    private String directoryName;

    public IndexController(String directoryName, ResourceCRUD resourceCRUD) {
        this.directoryName = directoryName;
        this.resourceCRUD = resourceCRUD;
    }

    public String handle(Request request) throws IOException {
        return get();
    }

    public String get() throws IOException {
        String response = "";
        response += HttpStatus.okay + EscapeCharacters.newline + EscapeCharacters.newline;
        String directoryAndFiles = resourceCRUD.read(directoryName);

        response += "<html><head><title>bobsjavaserver</title></head><body>";
        response += "<h1>Hello world</h1>";
        response += format(directoryAndFiles);
        response += "</body></html>";

        return response;
    }

    private String format(String directoryAndFiles) {
        String htmlFormat = "";
        String[] dirAndFiles = directoryAndFiles.split(" ");

        for(String file: dirAndFiles) {
            if (file.equals("public")) {
                htmlFormat += "<li> public </li>";
            } else {
                htmlFormat += "<li><a href=http://localhost:5000/" + file + ">" + file + "</a>";
            }
        }
        return htmlFormat;
    }

}
