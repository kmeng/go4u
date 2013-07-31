package com.go4u.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ParserFactory implements InitializingBean {
	private Map<String, List<String>> domainRuleListMap = new HashMap<String, List<String>>();
	
	public static ProductParser getParser(String domain){
		return null;
	}

	public void afterPropertiesSet() throws Exception {
		File parserFolder = new File("classpath:/parser");
		File[] parserFiles = parserFolder.listFiles();
		for(File parserFile : parserFiles){
			BufferedReader br = new BufferedReader(new FileReader(parserFile));
			String rule = null;
			List<String> ruleList = new ArrayList<String>();
			while((rule = br.readLine()) != null){
				ruleList.add(rule);
			}
			domainRuleListMap.put(parserFile.getName(), ruleList);
		}
	}
}
