package org.m2m.api;

public class ModelE extends ModelA{

    private String fieldTest;
    private String field11;
    private int field12 = 0;

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder(super.toString());
        buffer.append(String.format("fieldTest=%s,", fieldTest));
        buffer.append(String.format("field11=%s,", field11));
        buffer.append(String.format("field12=%s,", field12));
        return buffer.toString();
    }

    public String getFieldTest() {
        return fieldTest;
    }

    public void setFieldTest(String fieldTest) {
        this.fieldTest = fieldTest;
    }

    public String getField11() {
        return field11;
    }

    public void setField11(String field11) {
        this.field11 = field11;
    }

    public int getField12() {
        return field12;
    }

    public void setField12(int field12) {
        this.field12 = field12;
    }
}
