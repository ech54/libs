package org.m2m.api;

public class ModelF extends ModelB {

    private String fieldTest;
    private String field13;
    private int field14;

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder(super.toString());
        buffer.append(String.format("fieldTest=%s,", fieldTest));
        buffer.append(String.format("field13=%s,", field13));
        buffer.append(String.format("field14=%s,", field14));
        return buffer.toString();
    }

    public String getFieldTest() {
        return fieldTest;
    }

    public void setFieldTest(String fieldTest) {
        this.fieldTest = fieldTest;
    }

    public String getField13() {
        return field13;
    }

    public void setField13(String field13) {
        this.field13 = field13;
    }

    public int getField14() {
        return field14;
    }

    public void setField14(int field14) {
        this.field14 = field14;
    }
}
