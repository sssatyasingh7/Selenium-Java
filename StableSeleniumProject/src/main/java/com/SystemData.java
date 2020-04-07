package com;

import java.util.ArrayList;
import java.util.List;

public class SystemData {
	private SystemData() {
	}

	// Report Operations
	public static final String EQUAL_OPER = "Equals";
	public static final String NOT_EQUAL_OPER = "Not Equals";
	public static final String EQUALS_IGNORE_CASE_OPER = "EqualsIgnoreCase";
	public static final String NOT_EQUALS_IGNORE_CASE_OPER = "Not EqualsIgnoreCase";
	public static final String CONTAINS_OPER = "Contains";
	public static final String NOT_CONTAINS_OPER = "Not Contains";
	public static final String CONTAINS_ALL_OPER = "Contains All";
	public static final String NOT_CONTAINS_ALL_OPER = "Not Contains All";

	public static final String SYS_ID = "sys_id";
	public static final String USER_GROUP_ENCODED_QUERY = "user.active=true^user.nameISNOTEMPTY^user.user_nameISNOTEMPTY";
	public static final String ACTIVE_USER_ENCODED_QUERY = "";
	public static final String USER_GROUP_WITHOUT_ITIL_ROLE_ENCODED_QUERY = "";
	public static final String SYS_USER_ITIL_ROLE_ENCODED_QUERY = "roles=itil";
	public static final String ACTIVE_IS_TRUE_ENCODED_QUERY = "active=true";

	public static final String URL_SUFFIX = ".do?sys_id=";
	public static final String URL_LIST_VIEW = "_list.do";
	public static final String URL_QUERY_SYNTAX = "?sysparm_query=";

	public static final String DATE_FORMAT = "MM-dd-yyyy";

	public static final String DATE_TIME_FORMAT = "MM-dd-yyyy HH:mm:ss";

	public static final String TASK_SLA_TABLE_NAME = "task_sla";
	public static final String TASK_SLA_URL = TASK_SLA_TABLE_NAME + URL_SUFFIX;
	public static final String TASK_SLA_LIST_VIEW = TASK_SLA_TABLE_NAME + URL_LIST_VIEW;

	public static final String USER_TABLE_NAME = "sys_user";
	public static final String USER_TABLE_URL = USER_TABLE_NAME + URL_SUFFIX;
	public static final String USER_TABLE_LIST_VIEW = USER_TABLE_NAME + URL_LIST_VIEW;

	public static final String USER_GROUP_TABLE_NAME = "sys_user_group";
	public static final String USER_GROUP_URL = USER_GROUP_TABLE_NAME + URL_SUFFIX;
	public static final String USER_GROUP_LIST_VIEW = USER_GROUP_TABLE_NAME + URL_LIST_VIEW;

	public static final String USER_GROUP_MEMBER_TABLE_NAME = "sys_user_grmember";
	public static final String USER_GROUP_MEMBER_URL = USER_GROUP_MEMBER_TABLE_NAME + URL_SUFFIX;
	public static final String USER_GROUP_MEMBER_LIST_VIEW = USER_GROUP_MEMBER_TABLE_NAME + URL_LIST_VIEW;
	
	public static final String USER_ROLE_TABLE_NAME = "sys_user_role";
	public static final String SYS_CREATED_ON = "sys_created_on";
	
	public static final List<String> EMPTY_LIST_OF_TYPE_STRING = new ArrayList<String>();
}
