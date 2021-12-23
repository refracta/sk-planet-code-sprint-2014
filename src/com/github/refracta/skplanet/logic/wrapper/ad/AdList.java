package com.github.refracta.skplanet.logic.wrapper.ad;

import com.github.refracta.skplanet.helper.HttpClientWrapper;
import com.github.refracta.skplanet.logic.token.TokenConst;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 9:31
 */
public class AdList {
	private Integer turnNo;
	private Integer timeSeq;
	private ArrayList<AdvertisementData> data;
	private ArrayList<AdvertisementData> freeAd;
	private ArrayList<AdvertisementData> paidAd;
	private HashMap<Integer,Integer> impressionHashMap;

	public ArrayList<AdvertisementData> getFreeAd() {
		return freeAd;
	}

	public ArrayList<AdvertisementData> getPaidAd() {
		return paidAd;
	}

	public Integer getTurnNo() {
		return turnNo;
	}

	public Integer getTimeSeq() {
		return timeSeq;
	}

	public ArrayList<AdvertisementData> getData() {
		return data;
	}

	public static int getCostStartIndex(AdCost adCost, ArrayList<AdvertisementData> advertisementDatas){
if(adCost.value()==1){
	int oneTotal = 0;
	for (int i = advertisementDatas.size()-1; i >-1; i--) {
		if(advertisementDatas.get(i).getAdCost().equals(adCost.value())){
			oneTotal+= advertisementDatas.get(i).getImpressionCount();
		}

	}
	if(oneTotal==0){
		return 0;
	}
}
		for (int i = 0; i < advertisementDatas.size(); i++) {
			if(advertisementDatas.get(i).getAdCost().equals(adCost.value())){
				return i;
			}
		}
		return -2;
	}

	@Override
	public String toString() {


		return "광고 배열{" +
				"턴=" + turnNo +
				", 요청 수=" + timeSeq +
				", \n광고 데이터=" + data +
				", \n무료 광고 데이터=" + this.freeAd +
				", \n유료 광고 데이터=" + this.paidAd +
				'}';
	}
public static AdList getAdList(){
	HttpClientWrapper extender = new HttpClientWrapper(HttpClientWrapper.CHROME_AGENT);
	return new AdList(extender.easyRequestGetPage(TokenConst.AD_LIST));
}
	public AdList(String text){
		try {
			JSONObject object = new JSONObject(text);
			this.turnNo = (Integer) object.get("turnNo");
			this.timeSeq = (Integer) object.get("timeSeq");
			initDataArray((JSONArray) object.get("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public HashMap<Integer, Integer> getImpressionHashMap() {
		return impressionHashMap;
	}
	public void changeImpressionHashMapValue(Integer adNo,Integer changeValue){
		if(this.impressionHashMap.get(adNo)!=null){
			this.impressionHashMap.put(adNo, this.impressionHashMap.get(adNo)+changeValue);
		}else{
			System.err.println("말이 안되는 버그");
		}
	}

	public void initDataArray(JSONArray dataJsonArray){
		data = new ArrayList<AdvertisementData>();
		for (int i = 0; i < dataJsonArray.length(); i++) {
			try {
				JSONObject currentJSONObject = (JSONObject) dataJsonArray.get(i);
				AdvertisementData advertisementData = new AdvertisementData(currentJSONObject);
				data.add(advertisementData);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		this.freeAd = new ArrayList<AdvertisementData>();
		this.paidAd = new ArrayList<AdvertisementData>();
		for (int i = 0; i < data.size(); i++) {
			AdvertisementData advertisementData = data.get(i);
			if(advertisementData.staticIsFreeAd(data)){
				freeAd.add(advertisementData);
			}else{
				paidAd.add(advertisementData);
			}

		}
		Collections.sort(freeAd);
		Collections.sort(paidAd);
		impressionHashMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < data.size(); i++) {
			impressionHashMap.put(this.data.get(i).getAdNo(),this.data.get(i).getImpressionCount());
		}
	}
}
