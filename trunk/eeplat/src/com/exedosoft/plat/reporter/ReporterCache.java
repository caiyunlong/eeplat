package com.exedosoft.plat.reporter;

import java.util.HashMap;
import java.util.Collections;
import java.util.Map;

import com.exedosoft.plat.bo.BOInstance;

public class ReporterCache {

	public static Map<String, BOInstance> dataCache = new HashMap<String, BOInstance>();
	/**
	 * �����ڶ��̻߳����£�ת��Ϊͬ���ķ�ʽ
	 */
	static {
		dataCache = Collections.synchronizedMap(dataCache);
	}

	/**
	 * ��ȡһ��DataCache
	 * 
	 * @param cacheKey
	 * @return
	 */

	public static BOInstance getDataCache(String cacheKey) {

		if (dataCache.get(cacheKey) != null) {
			return dataCache.get(cacheKey);
		} else {
			synchronized (dataCache) {
				BOInstance aCacheInstance = new BOInstance();
				dataCache.put(cacheKey, aCacheInstance);
				return aCacheInstance;
			}
		}
	}
}
