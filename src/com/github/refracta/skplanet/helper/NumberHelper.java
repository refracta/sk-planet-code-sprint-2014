package com.github.refracta.skplanet.helper;


public class NumberHelper {




	/**
	 * 문자와 숫자가 섞여 있으면 무조건 0, 아니면 숫자를 반환한다.
	 *
	 * @param value
	 *            검사할 문자
	 * @return 반환된 숫자
	 */
	public int parseInt(String value) {
		StringBuffer sb = new StringBuffer(value.length());
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (!Character.isDigit(c)) {
				break;
			}
			sb.append(c);
		}
		return sb.length() == value.length() ? Integer.parseInt(sb.toString()) : 0;
	}

	/**
	 * 문자와 숫자가 섞여 있을 때에, 문자 앞 쪽의 연속되는 숫자 부분만 반환한다.
	 *
	 * @param value
	 *            검사할 문자
	 * @return 반환된 숫자
	 */
	public int parseIntPrefix(String value) {
		StringBuffer sb = new StringBuffer(value.length());
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (!Character.isDigit(c)) {
				break;
			}
			sb.append(c);
		}
		return sb.length() == 0 ? 0 : Integer.parseInt(sb.toString());
	}

	/**
	 * 문자를 Integer형으로 반환할 수 없는지 체크한다.
	 *
	 * @param value
	 *            검사할 문자
	 * @return true면 불가능
	 */
	public boolean isNaN(String value) {
		try {
			Integer.parseInt(value);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * 문자를 Double형으로 반환할 수 없는지 체크한다.
	 *
	 * @param value
	 *            검사할 문자
	 * @return true면 불가능
	 */
	public boolean isNaPN(String value) {
		try {
			Double.parseDouble(value);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * 백분율 수의 확률을 테스트 한 결과를 부울로 반환한다. 만약 들어가는 수가 30이라면 true가 나올 확률은 30%, false가 나올 확률은 70%가 된다. 100 이상의 수가
	 * 들어가면 언제나 true를 리턴한다.
	 *
	 * @param percent
	 *            백분율 값
	 *
	 * @return boolean true면 성공
	 */
	public boolean getProbability(double percent) {
		double rand = Math.floor(Math.random() * 100);
		if (rand < percent) {
			return true;
		}
		return false;
	}
}
