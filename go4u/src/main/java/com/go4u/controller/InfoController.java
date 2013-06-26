package com.go4u.controller;

import com.go4u.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InfoController {
    @Autowired
    private InfoService infoService;

    @RequestMapping(value="/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLinkInfo(@RequestParam String url){
        return new ResponseEntity<Object>(infoService.retrieveLinkInfo(url), HttpStatus.OK);
    }
    
    @RequestMapping(value="/exchange_rate", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getExchangeRate(){
    	return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
