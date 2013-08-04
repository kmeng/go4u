package com.go4u.parser;

import java.util.ArrayList;
import java.util.List;

public class ValueRules {
    private String name;
    private List<ValueRule> valueRuleList = new ArrayList<ValueRule>();
    private int index = 0;


    public ValueRules(String rawString){
        int firstIndexOfEqual = rawString.indexOf("=");
        name = rawString.substring(0, firstIndexOfEqual);
        String[] constraints = rawString.substring(firstIndexOfEqual + 1).split("@@");
        for(String constraint : constraints){
            valueRuleList.add(new ValueRule(constraint));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ValueRule> getValueRuleList() {
        return valueRuleList;
    }

    public void setValueRuleList(List<ValueRule> valueRuleList) {
        this.valueRuleList = valueRuleList;
    }

    public ValueRule next(){
        ValueRule valueRule = valueRuleList.get(index);
        index = (index == valueRuleList.size() - 1) ? index : index+1;
        return valueRule;
    }

    public boolean hasNext(){
        return valueRuleList != null && !valueRuleList.isEmpty() && index < valueRuleList.size() && index != valueRuleList.size() - 1;
    }

    public int index(){
        return index;
    }

    public void reset(){
        index = 0;
    }

    public ValueRule get(int index){
        return valueRuleList.get(index);
    }

    public ValueRule getCurrent(){
        return valueRuleList.get(this.index == 0 ? 0 : this.index - 1);
    }
}
