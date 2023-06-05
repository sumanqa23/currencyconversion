package com.xe.currencyconverter.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String returnDateInMS() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String timestamp = dateFormat.format(new Date());
		return timestamp;
	}

}
