package com.github.refracta.skplanet.starter.regersi;

import com.github.refracta.skplanet.helper.AnalyzerJSONCreator;
import com.github.refracta.skplanet.logic.wrapper.ad.AdRequestData;
import com.github.refracta.skplanet.logic.wrapper.schedule.ScheduleResult;
import com.github.refracta.skplanet.helper.HttpClientWrapper;
import com.github.refracta.skplanet.logic.token.TokenConst;
import com.github.refracta.skplanet.logic.wrapper.schedule.ScheduleUtil;
import com.github.refracta.skplanet.logic.wrapper.total.MnAInfo;
import com.github.refracta.skplanet.logic.wrapper.total.MnARequestData;

import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-27 오전 3:40
 */
public class SecondLogic {
	public static MnARequestData getMnARequestData(int mediaCount){
		ArrayList<AdRequestData> adRequestDataArrayList = new ArrayList<AdRequestData>();
		int paidAddCt = MnAInfo.getInfo().getMediaList().getData().get(mediaCount-1).getPaidAdImpression()/MnAInfo.getInfo().getAdList().getPaidAd().size();
		for (int i = 0; i < MnAInfo.getInfo().getAdList().getPaidAd().size(); i++) {
			adRequestDataArrayList.add(new AdRequestData(MnAInfo.getInfo().getAdList().getPaidAd().get(i).getAdNo(),paidAddCt));
		}
		int freeAddCt = MnAInfo.getInfo().getMediaList().getData().get(mediaCount-1).getFreeAdImpression()/MnAInfo.getInfo().getAdList().getFreeAd().size();
		for (int i = 0; i < MnAInfo.getInfo().getAdList().getFreeAd().size(); i++) {
			adRequestDataArrayList.add(new AdRequestData(MnAInfo.getInfo().getAdList().getFreeAd().get(i).getAdNo(),freeAddCt));
		}
		return new MnARequestData(mediaCount,adRequestDataArrayList);

	}
	public static void main(String[] args) throws InterruptedException {
		HttpClientWrapper httpClientWrapper = new HttpClientWrapper(HttpClientWrapper.CHROME_AGENT);
		System.out.println(httpClientWrapper.easyRequestGetPage(TokenConst.START_NEW_TURN));
		for (int i = 0; i < 9999; i++) {
			ArrayList<MnARequestData> request= new ArrayList<MnARequestData>();
			Thread.sleep(200);
			for (int j = 1; j < 41; j++) {
				request.add(getMnARequestData(j));
			}
			ScheduleUtil scheduleUtil  = new ScheduleUtil();
			String json = scheduleUtil.getRequestScheduleObject(request);
			ScheduleResult request1 = scheduleUtil.request(json);
			AnalyzerJSONCreator.saveAnalyzerJSONString(request,request1);
			MnAInfo.getInfo().reloadInfo();
		}

	}
}
