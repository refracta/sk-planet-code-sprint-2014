package com.github.refracta.skplanet.logic.wrapper.total;

import com.github.refracta.skplanet.logic.wrapper.ad.AdRequestData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 10:15
 */
public class MnARequestData implements Comparable<MnARequestData>{
	private Integer mediaNo;
	private ArrayList<AdRequestData> adPutRequest;



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
	public  void mergingThisAdRequestDataList() {
		HashMap<Integer, Integer> mergeMap = getMergingMap(this.adPutRequest);
		this.adPutRequest.clear();
		for (Map.Entry<Integer, Integer> entry : mergeMap.entrySet()) {
			this.adPutRequest.add(new AdRequestData(entry.getKey(), entry.getValue()));
		}
	}
	public int getFreeAdRequestAmount(){
		mergingThisAdRequestDataList();
	int totalCount = 0;
		for (int i = 0; i < this.adPutRequest.size(); i++) {
			if(this.adPutRequest.get(i).isFreeAd()){
				totalCount+=this.adPutRequest.get(i).getPutCount();
			}
		}
		return totalCount;
	}
	public int getPaidAdRequestAmount(){
		mergingThisAdRequestDataList();
		int totalCount = 0;
		for (int i = 0; i < this.adPutRequest.size(); i++) {
			if(!this.adPutRequest.get(i).isFreeAd()){
				totalCount+=this.adPutRequest.get(i).getPutCount();
			}
		}
		return totalCount;
	}



	public MnARequestData(Integer mediaNo, ArrayList<AdRequestData> adPutRequest) {
		this.mediaNo = mediaNo;
		this.adPutRequest = adPutRequest;
	}
	public Double getMediaFillRate(){
		return MnAInfo.getInfo().getMediaList().getData().get(mediaNo-1).getFillRate();
	}
	public int getMaxImpressionCountPerRequest(){
		return MnAInfo.getInfo().getMediaList().getData().get(mediaNo-1).getMaxImpressionCountPerRequest();
	}
	public Double getCalculateFillRate(){
		int freeAdCount = 0;
		int paidAdCount = 0;
		for (int i = 0; i < adPutRequest.size(); i++) {
		if(adPutRequest.get(i).isFreeAd()){
			freeAdCount+=adPutRequest.get(i).getPutCount();
		}else{
			paidAdCount+=adPutRequest.get(i).getPutCount();
		}
		}
		Double returnValue = paidAdCount*100.0/((paidAdCount+freeAdCount));
		if(paidAdCount+freeAdCount==0.0){
			return 0.0;
		}

	return returnValue;
	}
	public Double getCalculateFillRateAbs(){
		return Math.abs(getMediaFillRate()- getCalculateFillRate());
	}


	public Integer getMediaNo() {
		return mediaNo;
	}

	public ArrayList<AdRequestData> getAdPutRequest() {
		return adPutRequest;
	}

	@Override
	public String toString() {
		return "{" +
				"미디어 번호=" + mediaNo +
				", 광고 요청=" + adPutRequest +
				", FillRate=" + getCalculateFillRate() +
				", FillRate Abs=" + getCalculateFillRateAbs() +
				'}'+"\n";
	}

	public JSONObject getObject() {
		JSONObject object = new JSONObject();
		try {
			object.put("mediaNo", mediaNo);
			JSONArray adPutRequestArray = new JSONArray();
			for (int i = 0; i < adPutRequest.size(); i++) {
				AdRequestData currentData = adPutRequest.get(i);
				adPutRequestArray.put(currentData.getObject());
			}
			object.put("adPutRequest", adPutRequestArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public int compareTo(MnARequestData o) {
		return Integer.compare(this.mediaNo,o.getMediaNo());
	}
}
