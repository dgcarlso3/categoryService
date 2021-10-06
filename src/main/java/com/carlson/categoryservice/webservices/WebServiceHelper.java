package com.carlson.categoryservice.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebServiceHelper {

    private RestTemplate restTemplate;

    @Autowired
    public WebServiceHelper(RestTemplate restTemplate) {
        this.restTemplate =restTemplate;
    }

    public Boolean getProductExists(Integer id) {
        String url = "http://localhost:8081/products/" + id + "/exists";

        return restTemplate.getForObject(url, Boolean.class);
    }

}
