package org.m2m.api.model;

import org.m2m.api.mapper.converter.FieldConverter;

public class ModelConverterTest {
	
	public static void main(String[] args) {
		final ModelConverterTest test = new ModelConverterTest();
		
		test.simpleRuleFieldConverter();
	}
	
	public void simpleRuleFieldConverter() {
		final FieldConverter<String, String> rule = (s -> s+" World");
		
		System.out.println(rule.convert("Hello"));
	}
	
	
}
