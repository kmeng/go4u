package com.go4u.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;

public class ProductParserImpl implements ProductParser {
	private Map<String, Rule> ruleMap = new HashMap<String, Rule>();
	private NodeList tagsList = null;

	public ProductParserImpl(List<String> ruleList) {
		for (String rawRule : ruleList) {
			String[] nameRulePair = rawRule.split("=");
			ruleMap.put(nameRulePair[0], new Rule(nameRulePair[1]));
		}
	}

	public void parse(String link) {
		try {
			Parser parser = new Parser();
			parser.setURL(link);
			tagsList = parser.parse(new NodeFilter() {
				public boolean accept(Node node) {
					return node instanceof Tag;
				}
			});
		} catch (Exception e) {

		}
	}

	@Override
	public double getPrice() {
        return getTagDoubleValue("price");
	}

	@Override
	public double getDiscountPrice() {
        return getTagDoubleValue("discountPrice");
	}

	@Override
	public String getProductName() {
		String name = getTagTextValue("name");
        name = name.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
        return name;
	}

	@Override
	public String getImage() {
        return getTagTextValue("image");
	}

	@Override
	public List<String> getColorList() {
		return null;
	}

    private String getTagTextValue(String name){
        if(ruleMap.containsKey(name)){
            Rule rule = ruleMap.get(name);
            return this.getAttributeValue(rule);
        }
        return null;
    }

    private double getTagDoubleValue(String name){
        try {
            String textValue = getTagTextValue(name);
            return Double.parseDouble(textValue.replaceAll(",", ""));
        } catch(Exception e){
            return Double.NaN;
        }
    }

    private String getAttributeValue(Rule rule) {
        Class tagClass = rule.getTagClass();
        Map<String, String> toBeMatchedAttributeMap = rule.getToBeMatchedAttributeMap();
        String attributeName = rule.getAttributeName();
        for (int i = 0; i < tagsList.size(); i++) {
            Node node = tagsList.elementAt(i);
            if (node.getClass() == tagClass) {
                Tag tag = (Tag) node;
                boolean anyMisMatch = false;
                for (String key : toBeMatchedAttributeMap.keySet()) {
                    String toBeVerifiedAttributeValue = tag.getAttribute(key);

                    if (toBeVerifiedAttributeValue == null
                            || !toBeVerifiedAttributeValue
                            .equals(toBeMatchedAttributeMap.get(key))) {
                        anyMisMatch = true;
                        break;
                    }
                }

                if(!anyMisMatch){
                    return tag.getAttribute(attributeName);
                }
            }
        }
        return null;
    }

}

class Rule {
	private Class tagClass;
	private Map<String, String> toBeMatchedAttributeMap = new HashMap<String, String>();
	private String attributeName;
	
	public Rule(String rawString){
		String regex = "([a-zA-Z]+)((\\[[a-zA-Z]+\\:'[^']+'\\])+)\\.(.*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(rawString);
		if(matcher.find()){
			//
			getTagClass(matcher.group(1));
			getToBeMatchedAttributeMap(matcher.group(2));
			extractAttributeName(matcher.group(4));
		}
	}

	public Class getTagClass() {
		return tagClass;
	}

	public void setTagClass(Class tagClass) {
		this.tagClass = tagClass;
	}

	public Map<String, String> getToBeMatchedAttributeMap() {
		return toBeMatchedAttributeMap;
	}

	public void setToBeMatchedAttributeMap(
			Map<String, String> toBeMatchedAttributeMap) {
		this.toBeMatchedAttributeMap = toBeMatchedAttributeMap;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	private void getTagClass(String tagName) {
		if (tagName.equals("input")) {
			tagClass = InputTag.class;
		}

		if (tagName.equals("image")) {
			tagClass = ImageTag.class;
		}

		if (tagName.equals("span")) {
			tagClass = Span.class;
		}

		if (tagName.equals("title")) {
			tagClass = TitleTag.class;
		}
	}
	
	private void getToBeMatchedAttributeMap(String attributeValuePairsStr){
		String regex = "((\\[([a-zA-Z]+)\\:'([^']+)'\\])+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(attributeValuePairsStr);
		if(matcher.find()){
			toBeMatchedAttributeMap.put(matcher.group(3), matcher.group(4));
			String remainPairStr = attributeValuePairsStr.substring(0, matcher.start(2));
			if(!remainPairStr.isEmpty()){
			    getToBeMatchedAttributeMap(attributeValuePairsStr.substring(0, matcher.start(2)));
			}
		}
	}

	
	private void extractAttributeName(String attributeName){
		this.attributeName = attributeName;
	}
}
