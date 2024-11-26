package com.adk.expensetracker.errorhandling;

import org.springframework.util.StringUtils;

/**
 * Field Blank Exception used when passed in data is missing a required field
 */
public class FieldBlankException extends RuntimeException {
	
	private static final long serialVersionUID = -8104227912438844281L;

	public FieldBlankException(Class<?> clazz, String missingField, String valueType) {
        super(FieldBlankException.generateMessage(clazz.getSimpleName(), missingField, valueType));
    }

	/**
	 * Generates message for the field blank exception
	 * @param clazz	the name of the class that is missing the field
	 * @param missingField the name of the field that is missing
	 * @param valueType	the value type of the field missing
	 * @return {@link String} of the exception's message
	 */
    private static String generateMessage(String clazz, String missingField, String valueType) {
        return StringUtils.capitalize(clazz) + " was missing value of field '" + missingField + "' which is of " + valueType ;
    }
}