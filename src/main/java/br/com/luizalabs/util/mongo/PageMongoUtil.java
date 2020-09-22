package br.com.luizalabs.util.mongo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author mreboucas
 */
public class PageMongoUtil {

	private PageMongoUtil() {}

	public static Pageable getPageable(int page, int size, Sort sort) {
		return getPage(page, size, sort);
	}

	public static Pageable getPageable(int page, int size) {
		return getPage(page, size, null);
	}

	private static Pageable getPage(int page, int size, Sort sort) {

		Pageable pageable;

		if (sort != null) {
			pageable = PageRequest.of(page, size, sort);
		} else {
			pageable = PageRequest.of(page, size);
		}

		return pageable;
	}
}
