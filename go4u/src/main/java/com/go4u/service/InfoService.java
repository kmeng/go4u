package com.go4u.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoService {

    public Map<String, String> retrieveLinkInfo(String link) {
        Map<String, String> tagsMap = new HashMap<String, String>();

        try {
            Parser parser = new Parser();
            parser.setURL(link);
            NodeList tagsList = parser.parse(new NodeFilter(){
                public boolean accept(Node node) {
                    return node instanceof Tag;
                }
            });

            tagsMap.put("main_img", getMainImage(tagsList));
            tagsMap.put("price", getPrice(tagsList));
            tagsMap.put("name", getProductName(tagsList));
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
