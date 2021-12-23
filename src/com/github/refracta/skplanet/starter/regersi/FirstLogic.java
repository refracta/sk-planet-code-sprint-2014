package com.github.refracta.skplanet.starter.regersi;

import com.github.refracta.skplanet.helper.AnalyzerJSONCreator;
import com.github.refracta.skplanet.logic.wrapper.ad.AdRequestData;
import com.github.refracta.skplanet.logic.wrapper.ad.AdvertisementData;
import com.github.refracta.skplanet.logic.wrapper.schedule.ScheduleResult;
import com.github.refracta.skplanet.starter.Infomation;
import com.github.refracta.skplanet.logic.wrapper.total.MnAInfo;
import com.github.refracta.skplanet.logic.wrapper.total.MnARequestData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-26 오전 12:36
 */
public class FirstLogic {
	static HashMap<Integer, Integer> impressionNumber = new HashMap<Integer, Integer>();

	public static void main(String[] args) throws InterruptedException {
		for (int j = 0; j < 10000; j++) {
			Thread.sleep(202);

			ArrayList<MnARequestData> jsonArray = new ArrayList<MnARequestData>();
			for (int i = 1; i < 41; i++) {
				MnARequestData fillRateCalulateRequestDataV2 = getFillRateCalulateRequestDataV2(i);
				jsonArray.add(fillRateCalulateRequestDataV2);
//				System.out.println(i + "번째 완료");
			}

			String requestScheduleObject = Starter.scheduleUtil.getRequestScheduleObject(jsonArray);
			ScheduleResult request = Starter.scheduleUtil.request(requestScheduleObject);
			impressionNumber = new HashMap<Integer, Integer>();
		AnalyzerJSONCreator.saveAnalyzerJSONString(jsonArray,request);

			MnAInfo.getInfo().reloadInfo();
		}
		Infomation.main(null);
	}//-10,540,434
	//-10,625,234

	public static int getTotalImpressionCount(ArrayList<AdRequestData> adRequestDataArrayList) {
		HashMap<Integer, Integer> mergingMap = getMergingMap(adRequestDataArrayList);
		int total = 0;
		for (Map.Entry<Integer, Integer> entry : mergingMap.entrySet()) {
			total += entry.getValue();
		}
		return total;
	}
	public static int getImpressionUseValue(int adNo){
		return impressionNumber.get(adNo)!=null?impressionNumber.get(adNo):0;
	}
	public static void putImpressionNumber(int adNo,int value){
		if(impressionNumber.get(adNo)!=null){
			impressionNumber.put(adNo,value+impressionNumber.get(adNo));
		}else{
			impressionNumber.put(adNo,value);
		}
	}
	public static MnARequestData getFillRateCalulateRequestDataV2(int mediaCount){
		ArrayList<AdRequestData> adRequestDataArrayList = new ArrayList<AdRequestData>();
		MnARequestData mnARequestData = new MnARequestData(mediaCount, adRequestDataArrayList);
		int increment = 1;
		ArrayList<AdvertisementData> freeAd = MnAInfo.getInfo().getAdList().getFreeAd();
		ArrayList<AdvertisementData> paidAd = MnAInfo.getInfo().getAdList().getPaidAd();
		while (getTotalImpressionCount(adRequestDataArrayList) < mnARequestData.getMaxImpressionCountPerRequest()) {
			Double fRate = mnARequestData.getMediaFillRate() - mnARequestData.getCalculateFillRate();
			if (fRate < 0) {
				//도달치보다 크다
				//FreeAd를 넣어줘야함
				//프리 애드 광고 순회
				//처음꺼부터 사용
				for (int i = 0; i < freeAd.size(); i++) {
					AdvertisementData currentFreeAd = freeAd.get(i);
					if(MnAInfo.getInfo().getAdList().getImpressionHashMap().get(currentFreeAd.getAdNo()) - increment>0){
						AdRequestData adRequestData = new AdRequestData(currentFreeAd.getAdNo(),increment);
						adRequestDataArrayList.add(adRequestData);
						MnAInfo.getInfo().getAdList().changeImpressionHashMapValue(currentFreeAd.getAdNo(),increment*-1);
						break;
					}
				}

			}else{
				for (int i = 0; i < paidAd.size(); i++) {
					AdvertisementData currentPaidAd = paidAd.get(i);
					if(MnAInfo.getInfo().getAdList().getImpressionHashMap().get(currentPaidAd.getAdNo()) - increment>0){
						AdRequestData adRequestData = new AdRequestData(currentPaidAd.getAdNo(),increment);
						adRequestDataArrayList.add(adRequestData);
						MnAInfo.getInfo().getAdList().changeImpressionHashMapValue(currentPaidAd.getAdNo(), increment * -1);
						break;
					}
				}
			}
			mergingAdRequestDataList(adRequestDataArrayList);
		}
		return mnARequestData;
	}


	public static MnARequestData getFillRateCalulateRequestData(int mediaCount) {
		ArrayList<AdRequestData> adRequestDataArrayList = new ArrayList<AdRequestData>();
		MnARequestData mnARequestData = new MnARequestData(mediaCount, adRequestDataArrayList);

		int increment = 1;

		while (getTotalImpressionCount(adRequestDataArrayList) < mnARequestData.getMaxImpressionCountPerRequest()) {
			AdRequestData currentData = null;
			mergingAdRequestDataList(adRequestDataArrayList);
			Double fRate = mnARequestData.getMediaFillRate() - mnARequestData.getCalculateFillRate();
			HashMap<Integer, Integer> mergingMap = getMergingMap(adRequestDataArrayList);
			if (fRate < 0) {
				//도달치보다 크다
				int currentAdNum = -1;

				for (int i = 0; i < MnAInfo.getInfo().getAdList().getFreeAd().size(); i++) {
					//광고들을 순회한다
					int impressionCount = 0;
					if (mergingMap.get(i) != null) {
						impressionCount = mergingMap.get(i);
					}
					int saveImpressionCount = impressionNumber.get(MnAInfo.getInfo().getAdList().getFreeAd().get(i).getAdNo()) != null ? impressionNumber.get(MnAInfo.getInfo().getAdList().getFreeAd().get(i).getAdNo()) : 0;
					//여지껏 해당 광고에 할당한 값을 구한다

					if (MnAInfo.getInfo().getAdList().getFreeAd().get(i).getImpressionCount() - impressionCount - increment - saveImpressionCount > 0) {
						//광고의 출현수가 영보다 크면
						currentAdNum = MnAInfo.getInfo().getAdList().getFreeAd().get(i).getAdNo();
						currentData = new AdRequestData(currentAdNum, increment);
						break;
					}
				}
				//맨 앞쪽의 애드를 받아와서 노출횟수가 얼마 남았나 체크

			} else {
				//도달치보다 작다
				int currentAdNum = -1;

				for (int i = 0; i < MnAInfo.getInfo().getAdList().getPaidAd().size(); i++) {
					//광고들을 순회한다
					int impressionCount = 0;
					if (mergingMap.get(i) != null) {
						impressionCount = mergingMap.get(i);
					}
					int saveImpressionCount = impressionNumber.get(MnAInfo.getInfo().getAdList().getPaidAd().get(i).getAdNo()) != null ? impressionNumber.get(MnAInfo.getInfo().getAdList().getPaidAd().get(i).getAdNo()) : 0;
					//여지껏 해당 광고에 할당한 값을 구한다
					int cal = MnAInfo.getInfo().getAdList().getPaidAd().get(i).getImpressionCount() - impressionCount - increment - saveImpressionCount;

					if (MnAInfo.getInfo().getAdList().getPaidAd().get(i).getImpressionCount() - impressionCount - increment - saveImpressionCount > 0) {
						//광고의 출현수가 영보다 크면
//						System.out.println(cal);
						currentAdNum = MnAInfo.getInfo().getAdList().getPaidAd().get(i).getAdNo();
						currentData = new AdRequestData(currentAdNum, increment);
						break;
					}
				}
			}
			if (currentData != null) {
				adRequestDataArrayList.add(currentData);

			} else {
				System.out.println("null-");
			}


		}
		mergingAdRequestDataList(adRequestDataArrayList);
		HashMap<Integer, Integer> inMap = getMergingMap(adRequestDataArrayList);
		for (Map.Entry<Integer,Integer> entry : inMap.entrySet()){
			if(impressionNumber.get(entry.getKey())==null){
				impressionNumber.put(entry.getKey(),entry.getValue());
			}else{
				impressionNumber.put(entry.getKey() ,entry.getValue()+impressionNumber.get(entry.getKey()));
			}

		}
		System.out.println(mnARequestData);
		return mnARequestData;
	}

	public static HashMap<Integer, Integer> getMergingMap(ArrayList<AdRequestData> adRequestDataArrayList) {
		HashMap<Integer, Integer> mergeMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < adRequestDataArrayList.size(); i++) {
			AdRequestData currentAdRequestData = adRequestDataArrayList.get(i);
			if (mergeMap.get(currentAdRequestData.getAdNo()) == null) {
				mergeMap.put(currentAdRequestData.getAdNo(), currentAdRequestData.getPutCount());
			} else {
				mergeMap.put(currentAdRequestData.getAdNo(), currentAdRequestData.getPutCount() + mergeMap.get(currentAdRequestData.getAdNo()));
			}
		}
		return mergeMap;
	}

	public static void mergingAdRequestDataList(ArrayList<AdRequestData> adRequestDataArrayList) {
		HashMap<Integer, Integer> mergeMap = getMergingMap(adRequestDataArrayList);
		adRequestDataArrayList.clear();
		for (Map.Entry<Integer, Integer> entry : mergeMap.entrySet()) {
			adRequestDataArrayList.add(new AdRequestData(entry.getKey(), entry.getValue()));
		}
	}
}
