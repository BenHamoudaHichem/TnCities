package com.app.tncities.service;

import io.spring.guides.gs_producing_web_service.GetStateRequest;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import io.spring.guides.gs_producing_web_service.Tnresponse;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Collections;
import java.util.stream.Collectors;

@Endpoint
public class TNCitiesService {
    public static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    public static final String URL_STATES="http://www.villes.co/tunisie/";
    public static final String URL_CITIES="http://www.villes.co/tunisie/gouvernorat_?.html";




    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllStatesRequest")
    @ResponsePayload
    public Tnresponse allStates()
    {
        try {
            Document document = Jsoup.connect(TNCitiesService.URL_STATES).get();
            Elements trs = document.getElementsByClass("region").select("a");


            Tnresponse tnresponse=new Tnresponse();
            tnresponse.getData().addAll(trs.stream().map(x->x.text()).collect(Collectors.toSet()));
            Collections.sort(tnresponse.getData());
            return tnresponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStateRequest")
    @ResponsePayload
    public Tnresponse getCitiesByState(@RequestPayload() GetStateRequest state)
    {
        try {
            Document document = Jsoup.connect(URL_CITIES.replace("?", StringUtils.stripAccents(state.getState()).replace(" ","-").toLowerCase())).get();
            Elements trs = document.getElementsByClass("listing-villes liserai ").select("a");
            trs.addAll(document.getElementsByClass("listing-villes liserai  bggris").select("a"));
            Tnresponse tnresponse=new Tnresponse();
            tnresponse.getData().addAll(trs.stream().map(x->x.text()).collect(Collectors.toSet()));
            Collections.sort(tnresponse.getData());

            return tnresponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isNumeric(String text) {
        if (text == null) {
            return false;
        }
        try {
            double parseDouble = Double.parseDouble(text);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
