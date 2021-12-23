package com.github.refracta.skplanet.logic.wrapper.total;

import com.github.refracta.skplanet.helper.AnalyzerJSONCreator;
import com.github.refracta.skplanet.logic.wrapper.ad.AdRequestData;
import com.github.refracta.skplanet.logic.wrapper.ad.AdResponseData;
import com.github.refracta.skplanet.logic.wrapper.ad.AdvertisementData;
import com.github.refracta.skplanet.logic.wrapper.media.MediaClickProfiler;
import com.github.refracta.skplanet.logic.wrapper.schedule.ScheduleResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-27 오후 11:09
 */
public class MAQSPrintUtil {
	private ScheduleResult scheduleResult;
	private ArrayList<MnARequestData> mnARequestDatas;
	private  ArrayList<MediaClickProfiler> mediaPaidAdClickProfilerArrayList;
	public MAQSPrintUtil(ScheduleResult scheduleResult, ArrayList<MnARequestData> mnARequestDatas, ArrayList<MediaClickProfiler> mediaPaidAdClickProfilerArrayList,boolean useJSONGraphTracking) {
		this.scheduleResult = scheduleResult;
		this.mnARequestDatas = mnARequestDatas;
		this.mediaPaidAdClickProfilerArrayList = mediaPaidAdClickProfilerArrayList;
		if(useJSONGraphTracking) {
			AnalyzerJSONCreator.saveAnalyzerJSONString(mnARequestDatas, scheduleResult);
		}
	}
//현재 리로드된 데이터들을 바탕으로 분석함
	@Override
	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("=================================================");
		bf.append("\n");
		bf.append("미디어 광고 송출 데이터 분석 클래스, "+scheduleResult.getTurnNo()+"번째 턴 시도 회차 : "+scheduleResult.getTimeSeq()+"번");
		bf.append("\n");
		for (int i = 0; i < scheduleResult.getData().size(); i++) {
			bf.append("→→→→→→→→→→");
			bf.append("\n");
			int mediaNo = scheduleResult.getData().get(i).getMediaNo();
			bf.append("{미디어 번호 : "+mediaNo+".");
			bf.append("\n");
			bf.append("미디어 정보 : ");
			bf.append("\n");
			bf.append("\t최대 광고 노출 횟수 : "+MnAInfo.getInfo().getMediaList().getData().get(i).getMaxImpressionCountPerRequest());
			bf.append("\n");
			bf.append("\tFillRate : "+MnAInfo.getInfo().getMediaList().getData().get(i).getFillRate()+", 무료 : "+MnAInfo.getInfo().getMediaList().getData().get(i).getFreeAdImpression()+", 유료 : "+MnAInfo.getInfo().getMediaList().getData().get(i).getPaidAdImpression());
			bf.append("\n");
			bf.append("\n");
			bf.append("광고 요청 정보 : ");
			bf.append("\n");


			ArrayList<AdResponseData> adClickResult = scheduleResult.getData().get(i).getAdClickResult();
			for (int j = 0; j < adClickResult.size(); j++) {
				AdResponseData adResponseData = adClickResult.get(j);
				AdRequestData matchRequestData = null;
				ArrayList<AdRequestData> adPutRequest = mnARequestDatas.get(i).getAdPutRequest();
				for (int k = 0; k < adPutRequest.size(); k++) {
					AdRequestData adRequestData = adPutRequest.get(k);
					if(adResponseData.getAdNo().equals(adRequestData.getAdNo())){
						matchRequestData = adRequestData;
					}
				}
				AdvertisementData advertisementData = MnAInfo.getInfo().getAdList().getData().get(matchRequestData.getAdNo() - 1);
				bf.append("\t광고 번호 : "+adResponseData.getAdNo()+".");
				bf.append("\n");
				//bf.append("\t\t광고 정보(업데이트 정보 기준) : ");
//				bf.append("\n");
				bf.append("\t\t광고 가격 : "+advertisementData.getAdCost()+"."+" ("+advertisementData.getIsFreeString()+")");
				bf.append("\n");
				bf.append("\t\t광고 클릭 수 : "+adResponseData.getClickCount());
				bf.append("\n");
				bf.append("\t\t광고 송출 요청 : "+matchRequestData.getPutCount());
				bf.append("\n");
				bf.append("\t\t남은 송출 횟수 : "+advertisementData.getImpressionCount());
				bf.append("\n");
				bf.append("------");
				bf.append("\n");



			}
			bf.append("\n");
			bf.append("프로파일러 데이터 : ");
			bf.append("\n");
			bf.append("\t현재 유료 클릭 변화 추이 : "+mediaPaidAdClickProfilerArrayList.get(i).getProfileData());
			bf.append("\n");
			bf.append("\t현재 유료 클릭 평균 : "+mediaPaidAdClickProfilerArrayList.get(i).getAverage());
			bf.append("\n");
			bf.append("\t현재 유료 클릭 통계값 : "+mediaPaidAdClickProfilerArrayList.get(i).getAdvancedStatistic());
			bf.append("\n");
			bf.append("\t현재 유료 클릭 등급 : "+mediaPaidAdClickProfilerArrayList.get(i).getCurrentRank()+"}");
			bf.append("\n");

			bf.append("\n");
		}
		bf.append("~~~~");
		bf.append("\n");
		bf.append("랭크 프로파일링 : ");
		bf.append("\n");
		HashMap<MediaClickProfiler.ClickRank,Integer> staticsRank = new HashMap<MediaClickProfiler.ClickRank, Integer>();
		for (int i = 0; i < this.mediaPaidAdClickProfilerArrayList.size(); i++) {
			MediaClickProfiler.ClickRank currentRank = this.mediaPaidAdClickProfilerArrayList.get(i).getCurrentRank();
			if(staticsRank.get(currentRank)==null){

				staticsRank.put(currentRank,MnAInfo.getInfo().getMediaList().getData().get(i).getPaidAdImpression());
			}else{
				staticsRank.put(currentRank,staticsRank.get(currentRank)+MnAInfo.getInfo().getMediaList().getData().get(i).getPaidAdImpression());

			}
		}
		for (Map.Entry<MediaClickProfiler.ClickRank,Integer> entry : staticsRank.entrySet()){
			bf.append("\t"+entry.getKey()+" : "+entry.getValue());
			bf.append("\n");
		}
		bf.append("=================================================");
		return bf.toString();
	}
}
