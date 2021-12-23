package com.github.refracta.skplanet.logic.token;

import com.github.refracta.skplanet.helper.CAEWrapper;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 7:42
 */
public class TokenConst {
	private static String WIND_TOKEN = "";
	private static String MY_TOKEN = "";
	public static String START_NEW_TURN = "https://adsche.skplanet.com/api/startNewTurn";
	public static String AD_LIST = "https://adsche.skplanet.com/api/adList";
	public static String MEDIA_LIST = "https://adsche.skplanet.com/api/mediaList";
	public static String SCHEDULE = "https://adsche.skplanet.com/api/schedule";

	public static String getWIND_TOKEN() {
		return CAEWrapper.decode(WIND_TOKEN);
	}

	public static String getMY_TOKEN() {
//		return getWIND_TOKEN();
		return CAEWrapper.decode(MY_TOKEN);
	}
}
