package br.com.luizalabs.util.mongo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import br.com.luizalabs.util.mongo.enumeration.ParameterMongoEnum;

/**
 * @author mreboucas
 * 
 * @see: https://www.devglan.com/spring-boot/spring-data-mongodb-queries
 */
public class CriteriaMongoUtil {

	private CriteriaMongoUtil() {}
	/**
	 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 21 de set de 2020 as 22:34:58 
	 *
	 * @param parameterValue - valor a ser pesquisado
	 * @param attributeName - nome do atributo do objeto a ser pesquisado
	 * @param parameterMongoEnum - Condição de busca. @see:{#ParameterMongoEnum.java}
	 * @return Criteria
	 */
	public static Criteria getCriteria(Object parameterValue, String attributeName, ParameterMongoEnum parameterMongoEnum) {
		return getCriteriaByParameters(parameterValue, null, attributeName, parameterMongoEnum);
	}

	/**
	 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 21 de set de 2020 as 22:34:58 
	 *
	 * @param parameterValue e parameterValue2 - os dois servem para realizar o between
	 * @param parameterMongoEnum - Condição de busca. @see:{#ParameterMongoEnum.java}
	 * @return Criteria
	 */
	public static Criteria getCriteria(Object parameterValue, Object parameterValue2, String attributeName, ParameterMongoEnum parameterMongoEnum) {
		return getCriteriaByParameters(parameterValue, parameterValue2, attributeName, parameterMongoEnum);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Criteria getCriteriaByParameters(Object parameterValue, Object parameterValue2, String attributeName, ParameterMongoEnum parameterMongoEnum) {

		Criteria criteria = null;

		if (parameterMongoEnum != null && StringUtils.isNotBlank(attributeName) && ObjectUtils.notEqual(parameterValue, null)) {

			switch (parameterMongoEnum) {
				case LIKE:
					criteria = Criteria.where(attributeName).regex(parameterValue + "");
					break;

				case LIKE_IGNORE_CASE:
					criteria = Criteria.where(attributeName).regex(parameterValue + "", "i");
					break;

				case IN:
					if (parameterValue instanceof Collection) {
						Collection collection = new ArrayList();
						collection.addAll((Collection) parameterValue);
						criteria = Criteria.where(attributeName).in(collection);
					} else {
						criteria = Criteria.where(attributeName).in(parameterValue);
					}
					break;

				case EQUAL:
					criteria = Criteria.where(attributeName).is(parameterValue);
					break;

				case STARTS_WITH:
					criteria = Criteria.where(attributeName).regex(parameterMongoEnum.getRegex() + parameterValue);
					break;

				case ENDS_WITH:
					criteria = Criteria.where(attributeName).regex(parameterValue + parameterMongoEnum.getRegex());
					break;

				case GREATER_THAN:
					criteria = Criteria.where(attributeName).gt(parameterValue);
					break;

				case LESS_THAN:
					criteria = Criteria.where(attributeName).lt(parameterValue);
					break;

				case GREATER_THAN_AND_LESS_THAN:
					criteria = Criteria.where(attributeName).gt(parameterValue).lt(parameterValue2);
					break;

				case GREATER_EQUAL_THAN_AND_LESS_EQUAL_THAN:
					criteria = Criteria.where(attributeName).gte(parameterValue).lte(parameterValue2);
					break;

				case GREATER_EQUAL_THAN_AND_LESS_EQUAL_THAN_DATE_FIELD:
					LocalDateTime lct = convertDateToLocalDateTime((Date) parameterValue);
					Date date = convertLocalDateTimeToDate(lct);
					LocalDateTime lct2 = convertDateToLocalDateTime((Date) parameterValue2);
					Date date2 = convertLocalDateTimeToDate(lct2);
					if (date != null && date2 != null) {
						criteria = Criteria.where(attributeName).gte(date).lte(date2);
					}
					break;

				default:
					break;
			}
		}

		return criteria;
	}

	private static LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
		return dateToConvert != null ? Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
	}

	private static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
		if (localDateTime != null) {
			ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
			return Date.from(zonedDateTime.toInstant());
		}
		return null;
	}
}
