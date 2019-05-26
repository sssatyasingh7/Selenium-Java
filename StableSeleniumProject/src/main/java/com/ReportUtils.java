package com;

public final class ReportUtils {

	private static ReportUtils reportUtils = null;

	private ReportUtils() {
		
	}
	public static final ReportUtils getInstance() {
		return (reportUtils == null) ? (reportUtils = new ReportUtils()) : reportUtils;
	}

}
