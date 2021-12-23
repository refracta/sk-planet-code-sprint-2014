package com.github.refracta.skplanet.helper; /**
 * 개발자 : refracta
 * 날짜   : 13. 8. 15 오후 4:15
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringHelper {

	private NumberHelper numberHelper;

	public StringHelper(NumberHelper numberHelper) {

		this.numberHelper = numberHelper;
	}

	/**
	 * 문자열 배열을 지정 시작 인덱스부터 배열 끝까지 구분자로 연결해서 반환한다.
	 *
	 * @param args      합칠 문자열 배열
	 * @param separator 구분자
	 * @param start     시작 지점
	 * @return 배열의 시작 인덱스부터 끝까지 구분자로 합쳐진 문자열
	 */
	public String join(String[] args, String separator, int start) {
		StringBuffer sb = new StringBuffer();
		for (int i = start; i < args.length; i++) {
			sb.append(args[i]);
			if (i < args.length - 1) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 문자열 배열을 지정 시작 인덱스부터 지정 끝 인덱스까지 구분자로 연결해서 반환한다.
	 *
	 * @param args      합칠 문자열 배열
	 * @param separator 구분자
	 * @param start     시작 지점
	 * @param end       끝 지점
	 * @return 배열의 시작 인덱스부터 끝 인덱스까지 구분자로 합쳐진 문자열
	 */
	public String join(String[] args, String separator, int start, int end) {
		StringBuffer sb = new StringBuffer();
		for (int i = start; i <= end; i++) {
			sb.append(args[i]);
			if (i < end) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 시간 폼을 검사한다.
	 *
	 * @param timeForm 시간 폼 (예시 : 2y10o2s = 2년 10월 2초)
	 * @return true면 폼 형태가 올바름
	 */
	public boolean validatorTimeForm(String timeForm) {
		Matcher matcher = Pattern.compile("^(\\-)?([0-9]*y)?([0-9]*o)?([0-9]*d)?([0-9]*h)?([0-9]*m)?([0-9]*s)?$").matcher(timeForm);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 시간 폼을 입력 받아서 패턴이 참이면 시간의 초를 반환하고 거짓이면 0을 반환한다.
	 *
	 * @param timeForm 시간 폼 (예시 : 2y10o2s = 2년 10월 2초)
	 * @return 초
	 */
	public int getSecondByTimeForm(String timeForm) {
		if (!this.validatorTimeForm(timeForm)) {
			return 0;
		}

		String signText = timeForm.substring(0, 1);
		int sign = 1;
		if (signText.equalsIgnoreCase("-")) {
			timeForm = timeForm.replace("-", "");
			sign = -1;
		}

		int year = 0;
		String yearText = timeForm.split("y")[0];
		if (!yearText.equalsIgnoreCase("")) {
			year = this.numberHelper.parseInt(yearText);
			if (year != 0)
				timeForm = timeForm.replace(yearText + "y", "");
		}

		int month = 0;
		String monthText = timeForm.split("o")[0];
		if (!monthText.equalsIgnoreCase("")) {
			month = this.numberHelper.parseInt(monthText);
			if (month != 0)
				timeForm = timeForm.replace(monthText + "o", "");
		}

		int day = 0;
		String dayText = timeForm.split("d")[0];
		if (!dayText.equalsIgnoreCase("")) {
			day = this.numberHelper.parseInt(dayText);
			if (day != 0)
				timeForm = timeForm.replace(dayText + "d", "");
		}

		int hour = 0;
		String hourText = timeForm.split("h")[0];
		if (!hourText.equalsIgnoreCase("")) {
			hour = this.numberHelper.parseInt(hourText);
			if (hour != 0)
				timeForm = timeForm.replace(hourText + "h", "");
		}

		int minute = 0;
		String minuteText = timeForm.split("m")[0];
		if (!minuteText.equalsIgnoreCase("")) {
			minute = this.numberHelper.parseInt(minuteText);
			if (minute != 0)
				timeForm = timeForm.replace(minuteText + "m", "");
		}

		int second = 0;
		String secondText = timeForm.split("s")[0];
		if (!secondText.equalsIgnoreCase("")) {
			second = this.numberHelper.parseInt(secondText);
			if (second != 0)
				timeForm = timeForm.replace(secondText + "s", "");
		}

		int sum = 0;
		sum += year * 60 * 60 * 24 * 30 * 365;
		sum += month * 60 * 60 * 24 * 30;
		sum += day * 60 * 60 * 24;
		sum += hour * 60 * 60;
		sum += minute * 60;
		sum += second;
		sum *= sign;
		return sum;
	}

	/**
	 * 초 값을 입력 받아서 시간 폼 배열로 반환한다.
	 *
	 * @param second 초
	 * @return 시간 폼 배열 (부호, 년, 월, 일, 시, 분, 초)
	 */
	public static int[] getTimeFormBySecond(int second) {
		if (second == 0) {
			return null;
		}

		int[] timeForm = new int[]{0, 0, 0, 0, 0, 0, 0};

		int unitYear = 60 * 60 * 24 * 30 * 365;
		int unitMonth = 60 * 60 * 24 * 30;
		int unitDay = 60 * 60 * 24;
		int unitHour = 60 * 60;
		int unitMinute = 60;
		int unitSecond = 1;

		if (second > 0) {
			timeForm[0] = 1;
		} else {
			timeForm[0] = -1;
		}

		if (((double) second / unitYear) >= 1) {
			timeForm[1] = (int) Math.floor((double) second / unitYear);
			second -= unitYear * timeForm[1];
		}

		if (((double) second / unitMonth) >= 1) {
			timeForm[2] = (int) Math.floor((double) second / unitMonth);
			second -= unitMonth * timeForm[2];
		}

		if (((double) second / unitDay) >= 1) {
			timeForm[3] = (int) Math.floor((double) second / unitDay);
			second -= unitDay * timeForm[3];
		}

		if (((double) second / unitHour) >= 1) {
			timeForm[4] = (int) Math.floor((double) second / unitHour);
			second -= unitHour * timeForm[4];
		}

		if (((double) second / unitMinute) >= 1) {
			timeForm[5] = (int) Math.floor((double) second / unitMinute);
			second -= unitMinute * timeForm[5];
		}

		if (((double) second / unitSecond) >= 1) {
			timeForm[6] = (int) Math.floor((double) second / unitSecond);
			second -= unitSecond * timeForm[6];
		}

		return timeForm;
	}

	/**
	 * 초 값을 입력 받아서 한글 시간으로 반환한다.
	 *
	 * @param second 초
	 * @return 시간 텍스트
	 */
	public static String getTimeStringBySecond(int second) {
		int[] time = getTimeFormBySecond(second);
		if (time == null) {
			return null;
		}

		StringBuffer timeKor = new StringBuffer();

		if (time[0] <= 0) {
			timeKor.append("-");
		}
		timeKor.append(time[1] + "년 ");
		timeKor.append(time[2] + "월 ");
		timeKor.append(time[3] + "일 ");
		timeKor.append(time[4] + "시 ");
		timeKor.append(time[5] + "분 ");
		timeKor.append(time[6] + "초");

		return timeKor.toString();
	}

	/**
	 * 현재 시간을 표시한다.
	 *
	 * @return 시간 텍스트
	 */
	public static String getCurrentTimeString() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.YEAR) + "년 ");
		sb.append(calendar.get(Calendar.MONTH) + "월 ");
		sb.append(calendar.get(Calendar.DATE) + "일 ");
		sb.append(calendar.get(Calendar.HOUR) + "시 ");
		sb.append(calendar.get(Calendar.MINUTE) + "분 ");
		sb.append(calendar.get(Calendar.SECOND) + "초");

		return sb.toString();
	}

	/**
	 * 콘솔 출력 용도에 맞게 현재 시간을 출력한다.
	 *
	 * @return 시간 텍스트
	 */
	public static String getCurrentTimeStringConsol() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String Hour = calendar.get(Calendar.HOUR) + "";
		String Minute = calendar.get(Calendar.MINUTE) + "";
		String Second = calendar.get(Calendar.SECOND) + "";
		if (Hour.length() == 1) {
			Hour = "0" + Hour;
		}
		if (Minute.length() == 1) {
			Minute = "0" + Minute;
		}
		if (Second.length() == 1) {
			Second = "0" + Second;
		}
		return Hour + ":" + Minute + ":" + Second;
	}

	public static ArrayList<String> getSplitArrayList(String str, String spliter) {
		ArrayList<String> argsList = new ArrayList<String>();
		String[] argsArray = str.split(spliter);
		for (int i = 0; i < argsArray.length; i++) {
			argsList.add(argsArray[i]);
		}
		return argsList;
	}

	public static String regexPattern(String patternstr, String str, int breakPoint) {
		Pattern pattern = Pattern.compile(patternstr);

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

	public static ArrayList<String> regexPatternToArray(String patternstr, String str) {
		Pattern pattern = Pattern.compile(patternstr);
		Matcher patternMatcher = pattern.matcher(str);
		String returnStr = null;
		ArrayList<String> patternMatchStringList = new ArrayList<String>();
		while (patternMatcher.find()) {
			returnStr = patternMatcher.group();
			patternMatchStringList.add(returnStr);
		}
		return patternMatchStringList;
	}

	public static String findAheadAndBehind(String prefix, String middle, String suffix, String target, int breakPoint) {
		if (middle == null) {
			middle = ".+?";
		}
		String regex = "(?<=" + prefix + ")" + middle + "(?=" + suffix + ")";
		return regexPattern(regex, target, breakPoint);
	}

	public static ArrayList<String> findAheadAndBehindToArray(String prefix, String middle, String suffix, String target) {
		if (middle == null) {
			middle = ".+?";
		}
		String regex = "(?<=" + prefix + ")" + middle + "(?=" + suffix + ")";
		return regexPatternToArray(regex, target);

	}


	public static String regexPatternReplace(String patternstr,String str, String replace) {
		Pattern pattern = Pattern.compile(patternstr);
		Matcher patternMatcher = pattern.matcher(str);
		return patternMatcher.replaceAll(replace);
	}
}

