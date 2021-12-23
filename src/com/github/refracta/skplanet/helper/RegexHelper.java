package com.github.refracta.skplanet.helper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 개발자 : refracta
 * 날짜   : 14. 1. 28 오후 11:31
 */
public class RegexHelper {


	private static String getFlag(String regexp){
		int start = regexp.lastIndexOf("/")+1;
		String rtStr = regexp.substring(start,regexp.length());
		if(rtStr.equalsIgnoreCase("")){
			return null;
		}
		return rtStr;
	}
	private static String getPattern(String regexp){
		int start = 1;
		int end = regexp.lastIndexOf("/");
		return regexp.substring(start,end);
	}
	public static String tralsateFromJSRegexIntoJavaRegex(String JSRegex){
		String flag = getFlag(JSRegex);
		String pattern = getPattern(JSRegex);
		if(flag!=null){
			return "(?"+flag+")"+pattern;
		}else{
			return pattern;
		}
	}
	public static ArrayList<String> regexPatternToArray(String patternStr, String str) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher patternMatcher = pattern.matcher(str);
		String returnStr = null;
		ArrayList<String> patternMatchStringList = new ArrayList<String>();
		while (patternMatcher.find()) {
			returnStr = patternMatcher.group();
			patternMatchStringList.add(returnStr);
		}
		return patternMatchStringList;
	}
	public static String regexPattern(String patternStr, String str, int breakPoint) {
		Pattern pattern = Pattern.compile(patternStr);

		Matcher patternMatcher = pattern.matcher(str);

		String returnStr = null;
		int breakCounter = 0;
		while (patternMatcher.find()) {
			returnStr = patternMatcher.group();
			if (breakCounter == breakPoint) {
				break;
			}
			breakCounter++;
		}
		return returnStr;
	}
	public static String regexPatternReplace(String patternStr,String str, String replace) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher patternMatcher = pattern.matcher(str);
		return patternMatcher.replaceAll(replace);
	}
}
