package com.go4u.parser;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        BufferedReader parserDefinitionReader = new BufferedReader(
                new InputStreamReader(ParserFactory.class.getResourceAsStream("/parser/parsers")));
        String ruleLine;
        while((ruleLine = parserDefinitionReader.readLine()) != null){
            if(ruleLine.startsWith("[") && ruleLine.endsWith("]")){
                String[] domains = ruleLine.replace("[", "").replace("]", "").split(",");
                ProductParser productParser = parseRuleDefinition(parserDefinitionReader);
                for(String domain : domains){
                    domainRuleMap.put(domain, productParser);
                }
            }

        }
	}

    private ProductParser parseRuleDefinition(BufferedReader parserDefinitionReader) throws Exception{
        List<String> ruleList = new ArrayList<String>();
        String rule;
        while((rule = parserDefinitionReader.readLine()) != null){
            if(rule.trim().equals(""))
                break;

            ruleList.add(rule);
        }

        return new ProductParserJSoupImpl(ruleList);
    }
}
