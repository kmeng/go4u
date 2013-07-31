package com.go4u.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ParserFactory implements InitializingBean {
	private static Map<String, ProductParser> domainRuleMap = new HashMap<String, ProductParser>();
	
	public static ProductParser getParser(String domain){
        if(domainRuleMap.containsKey(domain)){
            return domainRuleMap.get(domain);
        }
		return null;
	}

	public void afterPropertiesSet() throws Exception {
        Map<String, ProductParser> parserMap = initializingParserMap();
        BufferedReader domainParserMapReader = new BufferedReader(new InputStreamReader(ParserFactory.class.getResourceAsStream("/parser/domain-parser")));
        String domainParserMapLine = null;
        while((domainParserMapLine = domainParserMapReader.readLine()) != null){
            String[] domainParserNamePair = domainParserMapLine.split("=");
            String parserName = domainParserNamePair[0];
            String[] domains = domainParserNamePair[1].split(",");
            for(String domain : domains){
                domainRuleMap.put(domain, parserMap.get(parserName));
            }
        }
	}

    private Map<String, ProductParser> initializingParserMap() throws Exception{
        Map<String, ProductParser> parserMap = new HashMap<String, ProductParser>();
        File parserFolder = new File(ParserFactory.class.getResource("/parser").getPath());
        File[] parserFiles = parserFolder.listFiles();
        for(File parserFile : parserFiles){
            if(parserFile.getName().startsWith("parser.")){
                BufferedReader br = new BufferedReader(new FileReader(parserFile));
                String rule = null;
                List<String> ruleList = new ArrayList<String>();
                while((rule = br.readLine()) != null){
                    ruleList.add(rule);
                }
                parserMap.put(parserFile.getName(), new ProductParserImpl(ruleList));
            }
        }

        return parserMap;
    }
}
