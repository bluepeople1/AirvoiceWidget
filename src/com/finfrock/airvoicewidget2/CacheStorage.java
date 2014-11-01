package com.finfrock.airvoicewidget2;

import java.util.Calendar;

import retiever.RawAirvoiceData;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheStorage {

    private static final String DOLLAR_VALUE_KEY = "dollar_value_";
    private static final String EXPIRE_DATE_KEY = "expire_date_";
    private static final String DATE_KEY = "date_";
    private static final String PLAN_TEXT_KEY = "plan_text_";
    private static final String CACHE_NAME = "com.finfrock.airvoicewidget2.AirvoiceDisplay.Cache";
    
    public void saveCacheData(Context context, int appWidgetId, 
    		RawAirvoiceData rawAirvoiceData) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(CACHE_NAME, 0).edit();
        prefs.putFloat(DOLLAR_VALUE_KEY + appWidgetId, rawAirvoiceData.getDollarValue());
        prefs.putLong(EXPIRE_DATE_KEY + appWidgetId, rawAirvoiceData.getExpireCalendar().getTimeInMillis());
        prefs.putString(PLAN_TEXT_KEY + appWidgetId, rawAirvoiceData.getPlanText());
        prefs.putLong(DATE_KEY + appWidgetId, rawAirvoiceData.getDate().getTimeInMillis());
        
        prefs.commit();
    }
    //prefs.putLong(LAST_UPDATED_KEY + appWidgetId, Calendar.getInstance().getTimeInMillis());
    
    public RawAirvoiceData getRawAirvoiceData(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(CACHE_NAME, 0);
		if (prefs != null) {
			float dollarValue = prefs.getFloat(DOLLAR_VALUE_KEY + appWidgetId, -1.0f);
			long exprireDateLong = prefs.getLong(EXPIRE_DATE_KEY + appWidgetId, -1);
			long dateLong = prefs.getLong(DATE_KEY + appWidgetId, -1);
			String planText = prefs.getString(PLAN_TEXT_KEY + appWidgetId, null);
			
			if(dollarValue < 0 || exprireDateLong < 0 || dateLong < 0 || planText == null){
			  return null;
			} else{
				Calendar exprirecalendar = Calendar.getInstance();
				exprirecalendar.setTimeInMillis(exprireDateLong);
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(dateLong);
				return new RawAirvoiceData(dollarValue, exprirecalendar, planText, calendar);
			}
		} else {
			return null;
		}
    }
    
    public Calendar getLastUpdatedDate(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(CACHE_NAME, 0);
		if (prefs != null) {
			Long timeInMillis = prefs.getLong(DATE_KEY + appWidgetId, -1);
			
			if(timeInMillis == -1){
				return null;
			} else{
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(timeInMillis);
				return calendar;
			}
		} else {
			return null;
		}
    }
    
    public Boolean isThereCachedData(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(CACHE_NAME, 0);
		if (prefs != null && prefs.getString(PLAN_TEXT_KEY + appWidgetId, null) != null) {
			return true;
		} else {
			return false;
		}
    }
}
