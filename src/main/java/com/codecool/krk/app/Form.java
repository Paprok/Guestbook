package com.codecool.krk.app;

import com.codecool.krk.app.dao.DAO;
import com.codecool.krk.app.models.Comment;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form implements HttpHandler{
    private DAO dao;

    public Form(DAO dao) {
        this.dao = dao;
    }

    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();


        if(method.equals("GET")){
            response = createGetResponse();
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            System.out.println(formData);
            Map inputs = parseFormData(formData);
            dao.putComment(createComment(inputs));
//            response = createGetResponse();
            httpExchange.sendResponseHeaders(303, 0);
        }
    }

    private Comment createComment(Map inputs){
        String nick = (String) inputs.get("nick");
        String text = (String) inputs.get("comment");
        return new Comment(nick, text);
    }

    private String createGetResponse(){
        String response = "<html><head><link rel=\"stylesheet\" href=\"static/style.css\"></head><body>" +
                getCommentsHTML() +
                "<form method=\"POST\" class='card'>\n " +
                "  First name:<br>\n" +
                "  <input type=\"text\" name=\"nick\" value=\"Jon Doe\">\n" +
                "  <br>\n" +
                "  Comment:<br>\n" +
                "  <textarea name=\"comment\"> Your comment here</textarea>\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" value=\"Submit\">\n" +
                "</form> " +
                "</body></html>";
        return response;
    }

    private String getCommentsHTML(){
        StringBuilder commentsHTML = new StringBuilder("<div class ='card' class='comments'>");
        List<Comment> comments = dao.getAllComments();
        for(int i = 0; i < comments.size(); i++){
            int backgroundType = i%2;
            commentsHTML.append(String.format("<p class='back%d'>", backgroundType ));
            commentsHTML.append(comments.get(i).getText());
            commentsHTML.append("<br>");
            commentsHTML.append("Nick: ");
            commentsHTML.append(comments.get(i).getNick());
            commentsHTML.append("</p>");
            commentsHTML.append("<br>");
        }
        commentsHTML.append("</div>");
        return commentsHTML.toString();
    }

    /**
     * Form data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
     * See: https://en.wikipedia.org/wiki/POST_(HTTP)
     */
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
