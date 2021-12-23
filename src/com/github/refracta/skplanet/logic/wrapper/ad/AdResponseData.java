package com.github.refracta.skplanet.logic.wrapper.ad;

import com.github.refracta.skplanet.logic.wrapper.total.MnAInfo;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 10:44
 */
public class AdResponseData {
	private Integer adNo;
	private Integer clickCount;

	@Override
	public String toString() {
		return "{" +
				"광고 번호=" + adNo +
				", 클릭 횟수=" + clickCount +
				'}'+"\n";
	}
	public boolean isFreeAd(){
			return MnAInfo.getInfo().getAdList().getData().get(adNo-1).getAdCost() == 0 ? true : false;
	}
	public Integer getAdNo() {
		return adNo;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public AdResponseData(JSONObject object) {
		try {
			this.adNo = (Integer) object.get("adNo");
			this.clickCount = (Integer) object.get("clickCount");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
