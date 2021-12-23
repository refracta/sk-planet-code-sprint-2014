package com.github.refracta.skplanet.starter.regersi;

import com.github.refracta.skplanet.logic.wrapper.ad.AdList;
import com.github.refracta.skplanet.logic.wrapper.ad.AdRequestData;
import com.github.refracta.skplanet.logic.wrapper.media.MediaList;
import com.github.refracta.skplanet.logic.wrapper.schedule.ScheduleUtil;
import com.github.refracta.skplanet.logic.wrapper.total.MnARequestData;

import java.util.ArrayList;


/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 6:56
 */
public class Starter {
	public static ScheduleUtil scheduleUtil = new ScheduleUtil();
	public static MediaList mediaList = MediaList.getMediaList();
	public static AdList adList = AdList.getAdList();

	public static ArrayList<MnARequestData> getEmptyRequestList(){
		ArrayList<MnARequestData> requestList = new ArrayList<MnARequestData>();
		for (int i = 1; i < 41; i++) {
			MnARequestData requestData = new MnARequestData(i,new ArrayList<AdRequestData>());
			requestList.add(requestData);
		}
		return requestList;
	}

	public static void main(String[] args)  {

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			ArrayList<MnARequestData> jsonList = getEmptyRequestList();
		for (int j = 0; j < jsonList.size(); j++) {
			int adNumber = mediaList.getData().get(j).getMaxImpressionCountPerRequest()/20;
			for (int i = 0; i <adList.getData().size(); i++) {
				jsonList.get(j).getAdPutRequest().add(new AdRequestData(adList.getData().get(i).getAdNo(),adNumber));
			}
		}
			System.out.println(jsonList.get(0));
		String json = scheduleUtil.getRequestScheduleObject(jsonList);
//		System.out.println(json);
//		scheduleUtil.request(json);
//		System.out.println(MediaList.getMediaList());
//		System.out.println(AdList.getAdList());

		}
//FR = 유료 / (유료 + 무료) * 100
//		scheduleUtil.requestScheduleObject()





}
