package com.github.refracta.skplanet.logic.wrapper.ad;

import com.github.refracta.skplanet.logic.wrapper.total.MnAInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 9:33
 */
public class AdvertisementData implements Comparable<AdvertisementData> {
	private Integer adNo;
	private Integer impressionCount;
	private Integer adCost;


	public Integer getAdNo() {
		return adNo;
	}

	public Integer getImpressionCount() {
		return impressionCount;
	}

	public Integer getAdCost() {
		return adCost;
	}
	public String getIsFreeString(){
		return isFreeAd() ?"무료":"유료";
	}
	@Override
	public String toString() {
		return "{" +
				"광고 번호=" + adNo +
				", 노출 횟수=" + impressionCount +
				", 광고 단가=" + adCost +
				", 무료 광고 여부=" + isFreeAd() +
				'}' + "\n";
	}

	public AdvertisementData(JSONObject object) {
		try {
			this.adNo = (Integer) object.get("adNo");
			this.impressionCount = (Integer) object.get("impressionCount");
			this.adCost = (Integer) object.get("adCost");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	@Override
	public int compareTo(AdvertisementData o) {
		int compare = Integer.compare(o.getAdCost(),this.adCost);
		if(compare==0){
			int secondCompare =  -1* Integer.compare(o.getImpressionCount(),this.impressionCount);
			if(secondCompare==0){
				return Integer.compare(this.adNo,o.getAdNo());
			}else{
				return secondCompare;
			}
		}else{
			return compare;

		}
	}
	@Deprecated
	public boolean staticIsFreeAd(ArrayList<AdvertisementData> data) {
	return data.get(adNo-1).getAdCost()==0 ? true : false;
	}

	public boolean isFreeAd() {
		return MnAInfo.getInfo().getAdList().getData().get(adNo-1).getAdCost()==0 ? true : false;
	}
}
