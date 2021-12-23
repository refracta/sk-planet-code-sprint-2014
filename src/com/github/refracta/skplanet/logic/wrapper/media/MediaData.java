package com.github.refracta.skplanet.logic.wrapper.media;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 9:43
 */
public class MediaData implements Comparable<MediaData> {
	private Integer mediaNo;
	private Integer maxImpressionCountPerRequest;
	private Double fillRate;
	private Integer freeAdImpression = 0;
	private Integer paidAdImpression = 0;

	public Integer getFreeAdImpression() {
		return freeAdImpression;
	}

	public Integer getPaidAdImpression() {
		return paidAdImpression;
	}

	public void calculateImpression(){
		int impressionCounter = 0 ;
		while (impressionCounter<maxImpressionCountPerRequest) {
			Double fRate = fillRate - calculateFillRate();
			if (fRate < 0) {
				//목표치보다 큼
				freeAdImpression++;
			} else {
				//목표치보다 작음
				paidAdImpression++;
			}
			impressionCounter++;
		}
	}
	public Double calculateFillRate(){
		Double returnValue = paidAdImpression*100.0/((paidAdImpression+freeAdImpression));
		if(freeAdImpression+paidAdImpression==0.0){
			return 0.0;
		}
		return returnValue;
	}



	public Integer getMediaNo() {
		return mediaNo;
	}

	public Integer getMaxImpressionCountPerRequest() {
		return maxImpressionCountPerRequest;
	}

	public Double getFillRate() {
		return fillRate;
	}

	@Override
	public String toString() {
		return "{" +
				"미디어 번호=" + mediaNo +
				", 최대 광고 노출 가능 횟수=" + maxImpressionCountPerRequest +
				", FillRate=" + fillRate +
				", 무료 광고 노출 횟수 [FillRate]=" + freeAdImpression +
				", 유료 광고 노출 횟수 [FillRate]=" + paidAdImpression +
				'}'+"\n";
	}

	public MediaData(JSONObject object) {
		try {
			this.mediaNo = (Integer) object.get("mediaNo");
			this.maxImpressionCountPerRequest = (Integer) object.get("maxImpressionCountPerRequest");
			this.fillRate = (Double) object.get("fillRate");
			calculateImpression();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	public MediaData(Integer mediaNo,Integer maxImpressionCountPerRequest,Double fillRate){
		this.mediaNo = mediaNo;
		this.maxImpressionCountPerRequest = maxImpressionCountPerRequest;
		this.fillRate = fillRate;
		calculateImpression();
	}


	@Override
	public int compareTo(MediaData o) {
	int compare = Integer.compare(o.getMaxImpressionCountPerRequest(),this.maxImpressionCountPerRequest);
		if(compare==0){
			return Double.compare(o.getFillRate(),this.fillRate);
		}else{
			return compare;

		}
	}
}
