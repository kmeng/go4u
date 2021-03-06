package com.go4u.parser;

import java.util.List;

public interface ProductParser {
	void parse(String link);
	double getPrice();
	double getDiscountPrice();
	String getProductName();
	String getImage();
	List<String> getColorList();
    List<String> getSizeList();
}
