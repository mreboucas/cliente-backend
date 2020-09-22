package br.com.luizalabs.util.mongo.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mreboucas
 * 
 * @see https://www.baeldung.com/queries-in-spring-data-mongodb
 * 
 * @see https://stackoverflow.com/questions/33471514/spring-data-mongodb-query-by-json-string
 * 
 * @see https://www.devglan.com/spring-boot/spring-data-mongodb-queries
 */
@Getter
@AllArgsConstructor
public enum ParameterMongoEnum {

	STARTS_WITH("^"),
	ENDS_WITH("$"),
	EQUAL(""),
	GREATER_THAN_AND_LESS_THAN(""),
	GREATER_EQUAL_THAN_AND_LESS_EQUAL_THAN(""),
	GREATER_EQUAL_THAN_AND_LESS_EQUAL_THAN_DATE_FIELD(""),
	GREATER_THAN(""),
	LESS_THAN(""),
	LIKE(""),
	LIKE_IGNORE_CASE(""),
	IN(""),
	;

	private String regex;
}