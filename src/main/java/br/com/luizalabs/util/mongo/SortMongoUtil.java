package br.com.luizalabs.util.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author mreboucas
 * 
 * Faz o que tu queres, pois eh tudo da lei!!!!
 */
public class SortMongoUtil {
	 
	private SortMongoUtil() {
	}
	
	/**
	 * 
	 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 5 de mai de 2020 as 08:57:37 
	 *
	 * @param direction
	 * @param propertyOrder
	 * @return
	 * @see {@link Direction}
	 */
	public static Sort getSort(Direction direction, String propertyOrder) {
		return getSorts(direction, propertyOrder, null);
	}

	public static Sort getSort(Direction direction, List<String> propertyOrders) {
		return getSorts(direction, propertyOrders, null);
	}
	
	public static Sort getSort(Direction direction, List<String> propertyOrders, String alias) {
		return getSorts(direction, propertyOrders, alias);
	}
	/**
	 * 
	 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 5 de mai de 2020 as 09:52:14 
	 *
	 * @param <Tetha>
	 * @param direction
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> Sort getSorts(Direction direction, T t, String alias) {
		Sort sort = null;
		if (t instanceof Collection) {
			List<String> list = (List<String>) t;
			list = getListWithAlias(alias, list);
			sort = Sort.by(direction, (list.toArray(new String[0])));
		} else {
			sort = Sort.by(direction, t + "");
		}

		return sort;
	}
	
	private static List<String> getListWithAlias(String alias, List<String> list) {
		
		if (StringUtils.isNotBlank(alias)) {
			alias = alias.endsWith(".") ? alias : alias + ".";
			List<String> auxList = new ArrayList<>();
			for (String value : list) {
				auxList.add(alias + value);
			}
			list = new ArrayList<>(auxList);
		}
		return list;
	}
}
