package org.m2m.api;

public class ModelA {

	private String field1 = "";
	private int field2 = 0;
	private String field5 = "";
	private int field6  = 0;
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(
				String.format("[%s: ", this.getClass().getSimpleName()));
		builder.append(String.format("field1=%s, ", field1));
		builder.append(String.format("field2=%s, ", field2));
		builder.append(String.format("field5=%s, ", field5));
		builder.append(String.format("field6=%s ]", field6));
		return builder.toString();
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public int getField2() {
		return field2;
	}
	public void setField2(int field2) {
		this.field2 = field2;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public String getField5() {
		return field5;
	}

	public void setField6(int field6) {
		this.field6 = field6;
	}

	public int getField6() {
		return field6;
	}
}
