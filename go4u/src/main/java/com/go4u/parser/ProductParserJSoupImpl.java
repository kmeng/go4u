package com.go4u.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductParserJSoupImpl implements ProductParser {
    private Logger logger = LoggerFactory.getLogger(ProductParserJSoupImpl.class);

    private Document document;
    private Map<String, ValueRule> ruleMap = new HashMap<String, ValueRule>();
    private String domain;

    public ProductParserJSoupImpl(List<String> ruleList){
        for(String raw : ruleList){
            ValueRule rule = new ValueRule(raw);
            ruleMap.put(rule.getName(), rule);
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
            document = Jsoup.connect(link).get();
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

        ValueRule valueRule = ruleMap.get(name);
        Elements elements = document.select(valueRule.getConstraint());
        if(!elements.isEmpty()){
            List<String> valueList = new ArrayList<String>();
            for(Element element : elements){
                String singleValue = getTextValue(element, valueRule);
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

        ValueRule valueRule = ruleMap.get(name);
        Elements elements = document.select(valueRule.getConstraint());
        if(!elements.isEmpty()){
            Element element = elements.get(0);
            return getTextValue(element, valueRule);
        }
        return "";
    }

    private String getTextValue(Element element, ValueRule valueRule){
        String targetAttribute = valueRule.getTargetAttribute();
        String extraConstraint = valueRule.getExtraConstraint();
        String value = null;
        if(targetAttribute.equals("text")){
            value = element.text();
        } else if(targetAttribute.equals("html")){
            value = element.html();
        } else {
            value = element.attr(targetAttribute);
        }
        return value;
    }

    private double getDoubleValue(String name){
        String textValue = getTextValue(name);
        try {
           return Double.parseDouble(textValue.replaceAll(",", ""));
        } catch(Exception e){
           return Double.NaN;
        }
    }
}
