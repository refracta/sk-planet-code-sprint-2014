package com.github.refracta.skplanet.logic.wrapper.ad;

import com.github.refracta.skplanet.logic.wrapper.total.MnAInfo;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 10:06
 */
public class AdRequestData {
	private Integer adNo;
	private Integer putCount;
	public boolean isFreeAd(){
		return MnAInfo.getInfo().getAdList().getData().get(adNo-1).getAdCost() == 0 ? true : false;
	}
	public AdRequestData(Integer adNo,Integer putCount){
		this.adNo = adNo;
		this.putCount = putCount;
	}
	public JSONObject getObject(){
		JSONObject object = new JSONObject();
		try {
			object.put("adNo",adNo);
			object.put("putCount",putCount);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	@Override
	public String toString() {
		return "{" +
				"광고 번호=" + adNo +
				", 요청 노출 횟수=" + putCount +
				'}'+"\n";
	}

	public Integer getAdNo() {
		return adNo;
	}

	public Integer getPutCount() {
		return putCount;
	}
}
