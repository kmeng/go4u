package com.go4u.parser;

public class ValueRule {
    private String constraint;
    private String targetAttribute;
    private String extraConstraint;

    public ValueRule(String rawString){
        String[] constraints = rawString.split("\\.");
        constraint = constraints[0];
        targetAttribute = constraints[1];
        if(constraints.length == 3){
            extraConstraint = constraints[2];
        }
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public String getTargetAttribute() {
        return targetAttribute;
    }

    public void setTargetAttribute(String targetAttribute) {
        this.targetAttribute = targetAttribute;
    }

    public String getExtraConstraint() {
        return extraConstraint;
    }

    public void setExtraConstraint(String extraConstraint) {
        this.extraConstraint = extraConstraint;
    }
}
