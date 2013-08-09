package com.go4u.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductParserJSoupImpl implements ProductParser {
    private Logger logger = LoggerFactory.getLogger(ProductParserJSoupImpl.class);

    private Document document;
    private Map<String, ValueRules> ruleMap = new HashMap<String, ValueRules>();
    private String domain;
    private String encoding;

    public ProductParserJSoupImpl(List<String> ruleList){
        for(String raw : ruleList){
            if(raw.startsWith("encoding")){
                encoding = raw.split("=")[1];
            } else {
                ValueRules valueRules = new ValueRules(raw);
                ruleMap.put(valueRules.getName(), valueRules);
            }
        }
    }

    public void parse(String link) {
        try {
            String domainRegex = "(https|http)://([^/]+).*";
            Pattern domainPattern = Pattern.compile(domainRegex);
            Matcher domainMatcher = domainPattern.matcher(link);
            if(domainMatcher.find()){
                domain = domainMatcher.group(1) + "://" + domainMatcher.group(2);
            }

            //Some site doesn't set encoding explicitly.
            if(encoding == null){
                document = Jsoup.connect(link).get();
            } else {
                document = Jsoup.parse(new URL(link).openStream(), encoding, link);
            }
        } catch(Exception e){
            logger.error("Error occurs when parsing [" + link + "]: ", e);
        }
    }

    @Override
    public double getPrice() {
        return getDoubleValue("price");
    }

    @Override
    public double getDiscountPrice() {
        return getDoubleValue("discountPrice");
    }

    @Override
    public String getProductName() {
        return getTextValue("name");
    }

    @Override
    public String getImage() {
        String url = getTextValue("image");
        if(url.startsWith("/")){
            return domain + url;
        } else {
            return url;
        }
    }

    @Override
    public List<String> getColorList() {
        return getMultiValues("color");
    }

    @Override
    public List<String> getSizeList() {
        return getMultiValues("size");
    }

    private List<String> getMultiValues(String name){
        if(!ruleMap.containsKey(name)){
            return null;
        }

        ValueRules valueRules = ruleMap.get(name);
        valueRules.reset();
        Elements elements = selectTargetElements(valueRules);
        if(elements != null && !elements.isEmpty()){
            List<String> valueList = new ArrayList<String>();
            for(Element element : elements){
                String singleValue = getTextValue(element, valueRules);
                if(singleValue != null && !singleValue.isEmpty()){
                    valueList.add(singleValue);
                }
            }
            return valueList;
        }
        return null;
    }

    private String getTextValue(String name){
        if(!ruleMap.containsKey(name)){
            return null;
        }

        ValueRules valueRules = ruleMap.get(name);
        valueRules.reset();
        Elements elements = selectTargetElements(valueRules);
        if(elements != null && !elements.isEmpty()){
            Element element = elements.get(0);
            return getTextValue(element, valueRules);
        }
        return "";
    }

    private Elements selectTargetElements(ValueRules valueRules){
        while(valueRules.hasNext()){
            ValueRule valueRule = valueRules.next();
            Elements elements = document.select(valueRule.getConstraint());
            if(elements != null && !elements.isEmpty()){
                return elements;
            }
        }

        return null;
    }

    private String getTextValue(Element element, ValueRules valueRules){
        ValueRule valueRule = valueRules.getCurrent();
        String targetAttribute = valueRule.getTargetAttribute();
        String extraConstraint = valueRule.getExtraConstraint();
        String value = null;
        if(targetAttribute.equals("text")){
            value = element.text();
        } else if(targetAttribute.equals("html")){
            value = element.html();
            if(value.contains("<img")){
               value = value.replaceAll("src=\"/", "src=\"" + domain +  "/");
            }
        } else {
            value = element.attr(targetAttribute);
        }
        return value;
    }

    private double getDoubleValue(String name){
        String textValue = getTextValue(name);
        try {
           return Double.parseDouble(textValue.replaceAll("[^(\\d|\\.)]", ""));
        } catch(Exception e){
           return Double.NaN;
        }
    }
}
