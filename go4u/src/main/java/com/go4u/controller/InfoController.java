package com.go4u.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.go4u.service.InfoService;

@Controller
public class InfoController {
    @Autowired
    private InfoService infoService;

    @RequestMapping(value="/info")
    public String  getLinkInfo(@RequestParam String url, HttpServletRequest request){
    	Map<String, String> infoMap = infoService.retrieveLinkInfo(url);
    	request.setAttribute("info", infoMap);
    	request.setAttribute("url", url);
    	request.setAttribute("currency", infoService.getExchangeRate());
        return "/detail";
    }
    
    @RequestMapping(value="/currency", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getExchangeRate(){
    	Map<String, Double> currency = new HashMap<String, Double>();
    	currency.put("currency", infoService.getExchangeRate());
    	return new ResponseEntity<Object>(currency, HttpStatus.OK);
    }
}
