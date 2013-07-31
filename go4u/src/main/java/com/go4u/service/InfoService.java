package com.go4u.service;

import com.go4u.parser.ParserFactory;
import com.go4u.parser.ProductParser;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
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
                    String name = productParser.getProductName();
                    double price = productParser.getPrice();
                    String image = productParser.getImage();

                    tagsMap.put("main_img", image);
                    tagsMap.put("price", price);
                    tagsMap.put("name", name);
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

    private String getMainImage(NodeList nodeList){
        return getTagAttributeValue(nodeList, ImageTag.class, "big_img", "src");
    }

    private String getPrice(NodeList nodeList){
       return getTagAttributeValue(nodeList, InputTag.class, "product_price", "value");
    }

    private String getProductName(NodeList nodeList){
        return getTagAttributeValue(nodeList, InputTag.class, "product_name", "value");
    }

    private String getTagAttributeValue(NodeList nodeList, Class tagClazz, String name, String attributeName){
        for(int i = 0; i < nodeList.size(); i ++){
            Node node = nodeList.elementAt(i);
            if(node.getClass() ==  tagClazz){
                Tag tag = (Tag)node;
                String srcName = tag.getAttribute("name");
                if(srcName == null){
                	srcName = tag.getAttribute("id");
                }
                if(srcName != null && srcName.equals(name)){
                    nodeList.remove(i);
                    return tag.getAttribute(attributeName);
                }
            }
        }

        return null;
    }


    public static void main(String[] args){
        System.out.println(new InfoService().getExchangeRate());
    }
}
