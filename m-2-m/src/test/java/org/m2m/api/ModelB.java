package org.m2m.api;

public class ModelB {

	private String field3 = "";
	private int field4 = 0;
	private String field5 ="";
	private int field6 = 0;
	private ModelType type;
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(
				String.format("[%s: ", this.getClass().getSimpleName()));
		builder.append(String.format("field3=%s, ", field3));
		builder.append(String.format("field4=%s, ", field4));
		builder.append(String.format("field5=%s, ", field5));
		builder.append(String.format("field6=%s ]", field6));
		return builder.toString();
	}
	
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public int getField4() {
		return field4;
	}
	public void setField4(int field4) {
		this.field4 = field4;
	}
	public String getField5() {
		return field5;
	}
	public void setField5(String field5) {
		this.field5 = field5;
	}
	public int getField6() {
		return field6;
	}
	public void setField6(int field6) {
		this.field6 = field6;
	}
	public ModelType getType() {
		return type;
	}
	public void setType(ModelType type) {
		this.type = type;
	}
}
