package com;

import java.util.List;
import java.util.Map;

public final class GlideRecordUtils {
	public static final GlideRecordUtils INSTANCE = new GlideRecordUtils();
	private static final int LIST_OF_MAXIMUM_SEARCH_COUNT = 250;
	private static final int GLIDE_QUERY_ITERATION_FREQUENCY_IN_MILLISECONDS = 200;
	private static final String USER_GROUP_ENCODED_QUERY = SystemData.USER_GROUP_ENCODED_QUERY + "^group.name=%s";
	private static final String USER_ROLE_ENCODED_QUERY = SystemData.ACTIVE_USER_ENCODED_QUERY + "^roles=%s";
	private static final String USER_GROUP_WITHOUT_ITIL_ROLE_ENCODED_QUERY = SystemData.USER_GROUP_WITHOUT_ITIL_ROLE_ENCODED_QUERY
			+ "^group.name=%s";

	private GlideRecordUtils() {
	}

	private final RemoteGlideRecord getGlideRecordInstance(String tableName, String encodedQuery,
			Map<String, String> addQueryKeyValue, int setLimitCount) {
		RemoteGlideRecord glide = new GlideRecordForTable(tableName);
		if (CommonUtils.isNotNull(encodedQuery)) {
			glide.addEncodedQuery(encodedQuery);
		}
		if (CommonUtils.isNotNull(addQueryKeyValue)) {
			addQueryKeyValue.keySet().forEach(key -> glide.addQuery(key, addQueryKeyValue.get(key)));
		}
		glide.orderByDesc(SystemData.SYS_CREATED_ON);
		if (setLimitCount != 0) {
			glide.setLimit(setLimitCount);
		}
		glide.query();
		return glide;
	}

	public final RemoteGlideRecord getGlideRecordInstance(String tableName, String encodedQuery,
			Map<String, String> addQueryKeyValue) {
		return getGlideRecordInstance(tableName, encodedQuery, addQueryKeyValue, LIST_OF_MAXIMUM_SEARCH_COUNT);
	}

	private final RemoteGlideRecord getGlideRecordInstance(String tableName, String encodedQuery, String addQueryKey,
			String addQueryValue, int setLimitValue) {
		RemoteGlideRecord glide = new GlideRecordForTable(tableName);
		if (CommonUtils.isNotNull(encodedQuery)) {
			glide.addEncodedQuery(encodedQuery);
		}
		if (CommonUtils.isNotNull(addQueryKey) && CommonUtils.isNotNull(addQueryValue)) {
			glide.addQuery(addQueryKey, addQueryValue);
		}
		glide.orderByDesc(SystemData.SYS_CREATED_ON);
		if (setLimitValue != 0) {
			glide.setLimit(setLimitValue);
		}
		glide.query();
		return glide;
	}

	public final RemoteGlideRecord getGlideRecordInstance(String tableName, String encodedQuery, String addQueryKey,
			String addQueryValue) {
		return getGlideRecordInstance(tableName, encodedQuery, addQueryKey, addQueryValue,
				LIST_OF_MAXIMUM_SEARCH_COUNT);
	}

	public final boolean isGlideInstanceNull(RemoteGlideRecord glide) {
		return (glide != null && glide.getRecordCount() == 0);
	}

	public final boolean isGlideInstanceNotNull(RemoteGlideRecord glide) {
		return !isGlideInstanceNull(glide);
	}

	public final RemoteGlideRecord getSingleRecordInstance(String tableName, String encodedQuery, String addQueryKey,
			String addQueryValue) {
		RemoteGlideRecord glide = null;
		for (int i = 0; i < 5; i++) {
			glide = getGlideRecordInstance(tableName, encodedQuery, addQueryKey, addQueryValue, 1);
			if (isGlideInstanceNotNull(glide)) {
				glide.next();
				break;
			} else {
				CommonUtils.threadSleep(GLIDE_QUERY_ITERATION_FREQUENCY_IN_MILLISECONDS);
			}
		}
		return glide;
	}

	public final RemoteGlideRecord getSingleRecordInstance(String tableName, String encodedQuery) {
		return getSingleRecordInstance(tableName, encodedQuery, null, null);
	}

	public final RemoteGlideRecord getSingleRecordInstance(String tableName, String addQueryKey, String addQueryValue) {
		return getSingleRecordInstance(tableName, null, addQueryKey, addQueryValue);
	}

	public final RemoteGlideRecord getListOfRecordInstance(String tableName, String encodedQuery) {
		return getGlideRecordInstance(tableName, encodedQuery, null, null, 0);
	}

	public final RemoteGlideRecord getListOfRecordInstance(String tableName, String addQueryKey, String addQueryValue) {
		return getGlideRecordInstance(tableName, null, addQueryKey, addQueryValue, 0);
	}

	public final String getRecordValue(RemoteGlideRecord glide, String fieldKey) {
		return isGlideInstanceNotNull(glide) ? glide.getValue(fieldKey) : null;
	}

	public final String getRecordValue(String tableName, String encodedQuery, String addQueryKey, String addQueryValue,
			String fieldKey) {
		return getRecordValue(getSingleRecordInstance(tableName, encodedQuery, addQueryKey, addQueryValue), fieldKey);
	}

	public final String getRecordValue(String tableName, String encodedQuery, String fieldKey) {
		return getRecordValue(tableName, encodedQuery, null, null, fieldKey);
	}

	public final String getRecordValue(String tableName, String addQueryKey, String addQueryValue, String fieldKey) {
		return getRecordValue(tableName, null, addQueryKey, addQueryValue, fieldKey);
	}

	public final String getRecordDisplayValue(RemoteGlideRecord glide, String fieldKey) {
		return isGlideInstanceNotNull(glide) ? glide.getDisplayValue(fieldKey) : null;
	}

	public final String getRecordDisplayValue(String tableName, String encodedQuery, String addQueryKey,
			String addQueryValue, String fieldKey) {
		return getRecordDisplayValue(getSingleRecordInstance(tableName, encodedQuery, addQueryKey, addQueryValue),
				fieldKey);
	}

	public final String getRecordDisplayValue(String tableName, String encodedQuery, String fieldKey) {
		return getRecordDisplayValue(tableName, encodedQuery, null, null, fieldKey);
	}

	public final String getRecordDisplayValue(String tableName, String addQueryKey, String addQueryValue,
			String fieldKey) {
		return getRecordDisplayValue(tableName, null, addQueryKey, addQueryValue, fieldKey);
	}

	public final List<String> getRecordsValue(RemoteGlideRecord glide, String fieldKey) {
		return isGlideInstanceNotNull(glide) ? glide.getListOfValue(fieldKey) : SystemData.EMPTY_LIST_OF_TYPE_STRING;
	}

	public final List<String> getRecordsValue(String tableName, String encodedQuery, String addQueryKey,
			String addQueryValue, String fieldKey) {
		return getRecordsValue(getGlideRecordInstance(tableName, encodedQuery, addQueryKey, addQueryValue, 0),
				fieldKey);
	}

	public final List<String> getRecordsValue(String tableName, String encodedQuery, String fieldKey) {
		return getRecordsValue(tableName, encodedQuery, null, null, fieldKey);
	}

	public final List<String> getRecordsValue(String tableName, String addQueryKey, String addQueryValue,
			String fieldKey) {
		return getRecordsValue(tableName, null, addQueryKey, addQueryValue, fieldKey);
	}

	public final List<String> getRecordsDisplayValue(RemoteGlideRecord glide, String fieldKey) {
		return isGlideInstanceNotNull(glide) ? glide.getListOfDisplayValue(fieldKey)
				: SystemData.EMPTY_LIST_OF_TYPE_STRING;
	}

	public final List<String> getRecordsDisplayValue(String tableName, String encodedQuery, String addQueryKey,
			String addQueryValue, String fieldKey) {
		return getRecordsDisplayValue(getGlideRecordInstance(tableName, encodedQuery, addQueryKey, addQueryValue, 0),
				fieldKey);
	}

	public final List<String> getRecordsDisplayValue(String tableName, String encodedQuery, String fieldKey) {
		return getRecordsDisplayValue(tableName, encodedQuery, null, null, fieldKey);
	}

	public final List<String> getRecordsDisplayValue(String tableName, String addQueryKey, String addQueryValue,
			String fieldKey) {
		return getRecordsDisplayValue(tableName, null, addQueryKey, addQueryValue, fieldKey);
	}

	public final int getRecordCount(String tableName, String encodedQuery, String addQueryKey, String addQueryValue) {
		RemoteGlideRecord glide = getGlideRecordInstance(tableName, encodedQuery, addQueryKey, addQueryValue, 0);
		return isGlideInstanceNotNull(glide) ? glide.getRecordCount() : 0;
	}

	public final int getRecordCount(String tableName, String encodedQuery) {
		return getRecordCount(tableName, encodedQuery, null, null);
	}

	public final int getRecordCount(String tableName, String addQueryKey, String addQueryValue) {
		return getRecordCount(tableName, null, addQueryKey, addQueryValue);
	}

	public final String getRecordSysId(String tableName, String encodedQuery, String addQueryKey,
			String addQueryValue) {
		return getRecordValue(tableName, encodedQuery, addQueryKey, addQueryValue, SystemData.SYS_ID);
	}

	public final String getRecordSysId(String tableName, String encodedQuery) {
		return getRecordSysId(tableName, encodedQuery, null, null);
	}

	public final String getRecordSysId(String tableName, String addQueryKey, String addQueryValue) {
		return getRecordSysId(tableName, null, addQueryKey, addQueryValue);
	}
}
