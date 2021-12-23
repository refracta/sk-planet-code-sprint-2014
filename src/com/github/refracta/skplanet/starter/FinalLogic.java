package com.github.refracta.skplanet.starter;

import com.github.refracta.skplanet.helper.AnalyzerJSONCreator;
import com.github.refracta.skplanet.logic.wrapper.ad.AdList;
import com.github.refracta.skplanet.logic.wrapper.ad.AdRequestData;
import com.github.refracta.skplanet.logic.wrapper.ad.AdvertisementData;
import com.github.refracta.skplanet.logic.wrapper.schedule.ScheduleResult;
import com.github.refracta.skplanet.logic.wrapper.ad.AdCost;
import com.github.refracta.skplanet.logic.wrapper.media.MediaClickProfiler;
import com.github.refracta.skplanet.logic.wrapper.schedule.ScheduleUtil;
import com.github.refracta.skplanet.logic.wrapper.total.MAQSPrintUtil;
import com.github.refracta.skplanet.logic.wrapper.total.MnAInfo;
import com.github.refracta.skplanet.logic.wrapper.total.MnARequestData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-27 오후 5:58
 */
public class FinalLogic {
	static ArrayList<MediaClickProfiler> mediaClickProfilers;

	static {
		mediaClickProfilers = new ArrayList<MediaClickProfiler>();
		for (int i = 0; i < 40; i++) {
			mediaClickProfilers.add(new MediaClickProfiler(5));
		}
	}

	public static void main(String[] args) throws InterruptedException {
		UseTry.startnewturn();
//		firstCommit();
		MnAInfo.getInfo().reloadInfo();
//		MnAInfo.getInfo().prudentMedia(0.00001);
		System.out.println("First Commit");

		for (int t = 0; t < 10000; t++) {


			Thread.sleep(100);
			ArrayList<MnARequestData> mnARequestDatas = new ArrayList<MnARequestData>();
			for (int i = 1; i < 41; i++) {
				MnARequestData commitMnARequestData = getCommitMnARequestData(i);
				mnARequestDatas.add(commitMnARequestData);

			}
			ScheduleUtil scheduleUtil = new ScheduleUtil();
			ScheduleResult request = scheduleUtil.request(mnARequestDatas);
			profilersUpdate(mnARequestDatas, request);
			MnAInfo.getInfo().reloadInfo();
//			MnAInfo.getInfo().prudentMedia(0.1);
			try {
				MAQSPrintUtil maqsPrintUtil = new MAQSPrintUtil(request, mnARequestDatas, mediaClickProfilers, true);
				System.out.println(maqsPrintUtil);
			}catch (IndexOutOfBoundsException ex){

			}

		}


	}

	public static MnARequestData getCommitMnARequestData(int mediaNo) {

		int increment = 1;
		ArrayList<AdRequestData> adRequestDataArrayList = new ArrayList<AdRequestData>();
		MnARequestData mnARequestData = new MnARequestData(mediaNo, adRequestDataArrayList);
		ArrayList<AdvertisementData> freeAd = MnAInfo.getInfo().getAdList().getFreeAd();
		ArrayList<AdvertisementData> paidAd = MnAInfo.getInfo().getAdList().getPaidAd();
		Integer freeAdImpression = MnAInfo.getInfo().getMediaList().getData().get(mediaNo - 1).getFreeAdImpression();
		Integer paidAdImpression = MnAInfo.getInfo().getMediaList().getData().get(mediaNo - 1).getPaidAdImpression();
		while (mnARequestData.getFreeAdRequestAmount() < freeAdImpression) {
			for (int i = 0; i < freeAd.size(); i++) {
				if (MnAInfo.getInfo().getAdList().getImpressionHashMap().get(freeAd.get(i).getAdNo()) - increment >= 0) {
					adRequestDataArrayList.add(new AdRequestData(freeAd.get(i).getAdNo(), increment));
					MnAInfo.getInfo().getAdList().changeImpressionHashMapValue(freeAd.get(i).getAdNo(), -1 * increment);
					break;
				}
			}
		}
		int escapeCount = 0;

		while (mnARequestData.getPaidAdRequestAmount() < paidAdImpression) {

			MediaClickProfiler.ClickRank currentRank = mediaClickProfilers.get(mediaNo - 1).getCurrentRank();

			if (escapeCount > paidAdImpression&&escapeCount < paidAdImpression * 2 + 1) {
				currentRank = MediaClickProfiler.ClickRank.SUPER_HIGH;
			} else if (escapeCount > paidAdImpression * 2 + 1) {
				return new MnARequestData(mediaNo, new ArrayList<AdRequestData>());
			}

			for (int i = getPaidCostStartIndex(currentRank); i < paidAd.size(); i++) {

				if (MnAInfo.getInfo().getAdList().getImpressionHashMap().get(paidAd.get(i).getAdNo()) - increment >= 0) {
					adRequestDataArrayList.add(new AdRequestData(paidAd.get(i).getAdNo(), increment));
					MnAInfo.getInfo().getAdList().changeImpressionHashMapValue(paidAd.get(i).getAdNo(), -1 * increment);
					break;
				}

			}


			escapeCount++;


		}
		mnARequestData.mergingThisAdRequestDataList();
		return mnARequestData;

	}

	public static int getPaidCostStartIndex(MediaClickProfiler.ClickRank currentRank) {
		ArrayList<AdvertisementData> paidAd = MnAInfo.getInfo().getAdList().getPaidAd();

		switch (currentRank) {
			case SUPER_HIGH:
				return AdList.getCostStartIndex(AdCost.COST_200, paidAd);
			case HIGH:
				return AdList.getCostStartIndex(AdCost.COST_10, paidAd);
			case SUPER_NOMAL:
				return AdList.getCostStartIndex(AdCost.COST_1, paidAd);
			case NOMAL:
				return AdList.getCostStartIndex(AdCost.COST_1, paidAd);
			case SUPER_LOW:
				return AdList.getCostStartIndex(AdCost.COST_1, paidAd);
			case LOW:
				return AdList.getCostStartIndex(AdCost.COST_1, paidAd);
		}
		return -1;
	}

	public static void profilersUpdate(ArrayList<MnARequestData> mnARequestDataArrayList, ScheduleResult scheduleResult) {
		Collections.sort(scheduleResult.getData());
		Collections.sort(mnARequestDataArrayList);
		for (int i = 0; i < scheduleResult.getData().size(); i++) {
			if (scheduleResult.getData().get(i).getMediaNo() == (i + 1)) {
				HashMap<String, Integer> dataHashMap = AnalyzerJSONCreator.getDataHashMap(mnARequestDataArrayList.get(i).getAdPutRequest(), scheduleResult.getData().get(i).getAdClickResult());
				mediaClickProfilers.get(i).pushData(dataHashMap.get("paidClickCount"));
			} else {

			}
		}
	}

	public static void firstCommit() {
		ArrayList<MnARequestData> mnARequestDataArrayList = new ArrayList<MnARequestData>();
		for (int i = 1; i < 41; i++) {
			MnARequestData mnARequest = getFirstCommintMnARequest(i);
			mnARequestDataArrayList.add(mnARequest);
		}
		ScheduleUtil scheduleUtil = new ScheduleUtil();
		ScheduleResult request = scheduleUtil.request(mnARequestDataArrayList);
		profilersUpdate(mnARequestDataArrayList, request);
	}

	public static MnARequestData getFirstCommintMnARequest(int mediaNo) {
		ArrayList<AdRequestData> adRequestDataArrayList = new ArrayList<AdRequestData>();
		MnARequestData mnARequestData = new MnARequestData(mediaNo, adRequestDataArrayList);
		int paidAddCt = MnAInfo.getInfo().getMediaList().getData().get(mediaNo - 1).getPaidAdImpression();
//		paidAddCt=0;
		adRequestDataArrayList.add(new AdRequestData(MnAInfo.getInfo().getAdList().getPaidAd().get(MnAInfo.getInfo().getAdList().getPaidAd().size() - 5).getAdNo(), paidAddCt));
		int freeAddCt = MnAInfo.getInfo().getMediaList().getData().get(mediaNo - 1).getFreeAdImpression();
//		freeAddCt=0;
		adRequestDataArrayList.add(new AdRequestData(MnAInfo.getInfo().getAdList().getFreeAd().get(0).getAdNo(), freeAddCt));
		return mnARequestData;
	}

}
