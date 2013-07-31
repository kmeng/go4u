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
import org.htmlparser.util.NodeList;

import com.opensymphony.module.sitemesh.taglib.decorator.TitleTag;

public class ProductParserImpl implements ProductParser {
	private Map<String, String> ruleMap = new HashMap<String, String>();
	private NodeList tagsList = null;

	public ProductParserImpl(List<String> ruleList) {
		for (String rawRule : ruleList) {
			String[] nameRulePair = rawRule.split("=");
			ruleMap.put(nameRulePair[0], nameRulePair[1]);
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
		return 0;
	}	
	public String getAttributeValue(Class tagClass,
			Map<String, String> toBeMatchedAttributeMap, String attribute) {
		for (int i = 0; i < tagsList.size(); i++) {
			Node node = tagsList.elementAt(i);
			if (node.getClass() == tagClass) {
				Tag tag = (Tag) node;
				for (String key : toBeMatchedAttributeMap.keySet()) {
					String toBeVerifiedAttributeValue = tag.getAttribute(key);

					if (toBeVerifiedAttributeValue != null
							&& toBeVerifiedAttributeValue
									.equals(toBeMatchedAttributeMap.get(key))) {
						tagsList.remove(i);
						return tag.getAttribute(attribute);
					}
				}
			}
		}
		return null;
	}

	@Override
	public double getDiscountPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getColorList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args){
		Rule rule = new Rule("input[name:'product_name'][type:'hidden'].value");
		rule.getTagClass();
	}

}

class Rule {
	private Class tagClass;
	private Map<String, String> toBeMatchedAttributeMap;
	private String attributeName;
	
	public Rule(String rawString){
		String regex = "([a-zA-Z]+)((\\[([a-zA-Z]+)\\:'([^']+)'\\])+)\\.(.*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(rawString);
		if(matcher.find()){
			//
			getTagClass(matcher.group(1));
			int totalFound = matcher.groupCount();
			for(int i = 2; i < totalFound - 1; i ++){
				
			}
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


}
