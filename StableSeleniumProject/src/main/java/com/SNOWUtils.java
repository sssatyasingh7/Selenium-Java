package com;

public class SNOWUtils {
	
	/**
	 * 
	 * @param tableName
	 * @return {@link GlideRecordForTable}
	 */
	protected GlideRecordForTable getGlideRecordForTable(String tableName) {
		return new GlideRecordForTable(tableName);
	}

}
