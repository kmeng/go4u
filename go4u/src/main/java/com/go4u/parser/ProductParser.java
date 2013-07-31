package com.go4u.parser;

import java.util.List;

public interface ProductParser {
	void parse(String link);
	double getPrice();
	double getDiscountPrice();
	String getName();
	String getImage();
	List<String> getColorList();
}
