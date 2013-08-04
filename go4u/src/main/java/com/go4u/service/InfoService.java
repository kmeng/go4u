package com.go4u.service;

import com.go4u.parser.ParserFactory;
import com.go4u.parser.ProductParser;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InfoService {

    public Map<String, Object> retrieveLinkInfo(String link) {
        Map<String, Object> tagsMap = new HashMap<String, Object>();

        try {
            String domainRegex = "(https|http)://([^/]+).*";
            Pattern domainPattern = Pattern.compile(domainRegex);
            Matcher domainMatcher = domainPattern.matcher(link);

            if(domainMatcher.find()){
                ProductParser productParser = ParserFactory.getParser(domainMatcher.group(2));
                if(productParser != null){
                    productParser.parse(link);

                    tagsMap.put("main_img", productParser.getImage());
                    tagsMap.put("price", productParser.getPrice());
                    double discountPrice = productParser.getDiscountPrice();
                    if(!Double.isNaN(discountPrice)){
                        tagsMap.put("discountPrice", productParser.getDiscountPrice());
                    }
                    tagsMap.put("name", productParser.getProductName());
                    tagsMap.put("colors", productParser.getColorList());
                    tagsMap.put("sizes", productParser.getSizeList());
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return tagsMap;
    }
    
    public double getExchangeRate(){
    	try {
    		HttpClient httpClient = new HttpClient();	
    		HttpMethod httpGet = new GetMethod("http://web.juhe.cn:8080/finance/exchange/rmbquot?key=0526c41b97d82d987b189a6a065fb476");
    		httpClient.executeMethod(httpGet);
    		String result = httpGet.getResponseBodyAsString();
    		JSONObject jo = new JSONObject(result);
    		JSONObject koreaToChinese = jo.getJSONArray("result").getJSONObject(0).getJSONObject("data17");
    		double value = koreaToChinese.getDouble("bankConversionPri");
    		
    		httpGet.releaseConnection();
    		
    		return value;
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    	return 0;
    }
}
