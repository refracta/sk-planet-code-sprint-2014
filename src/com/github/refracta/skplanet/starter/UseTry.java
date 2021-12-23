package com.github.refracta.skplanet.starter;

import com.github.refracta.skplanet.logic.wrapper.ad.AdRequestData;
import com.github.refracta.skplanet.logic.wrapper.schedule.ScheduleUtil;
import com.github.refracta.skplanet.logic.wrapper.total.MnARequestData;
import com.github.refracta.skplanet.helper.HttpClientWrapper;
import com.github.refracta.skplanet.logic.token.TokenConst;

import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-28 오전 12:13
 */
public class UseTry {
	public static void main(String[] args) throws InterruptedException {

		while (true) {

			ArrayList<MnARequestData> mnARequestDatas = new ArrayList<MnARequestData>();
			mnARequestDatas.add(new MnARequestData(1, new ArrayList<AdRequestData>()));
			ScheduleUtil scheduleUtil = new ScheduleUtil();
			System.out.println(scheduleUtil.request(mnARequestDatas));
			Thread.sleep(200);
		/*	long endTime = System.currentTimeMillis();
			long lTime = endTime - startTime;
			long tTime = 200-lTime;
			System.out.println(lTime);
			if(tTime>0) {
				Thread.sleep(tTime);
			}else{
				System.out.println(tTime);
			}*/
		}
	}
	public static void startnewturn(){
		HttpClientWrapper httpClientWrapper = new HttpClientWrapper(HttpClientWrapper.CHROME_AGENT);
		System.out.println(httpClientWrapper.easyRequestGetPage(TokenConst.START_NEW_TURN));
	}
}
