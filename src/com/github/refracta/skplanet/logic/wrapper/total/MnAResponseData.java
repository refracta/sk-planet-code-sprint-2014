package com.github.refracta.skplanet.logic.wrapper.total;

import com.github.refracta.skplanet.logic.wrapper.ad.AdResponseData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 10:44
 */
public class MnAResponseData implements Comparable<MnAResponseData>{
	private Integer mediaNo;
	private ArrayList<AdResponseData> adClickResult;

	@Override
	public String toString() {
		return "{" +
				"미디어 번호=" + mediaNo +
				", 클릭 결과=" + adClickResult +
				'}'+"\n";
	}

	public Integer getMediaNo() {
		return mediaNo;
	}

	public ArrayList<AdResponseData> getAdClickResult() {
		return adClickResult;
	}

	public MnAResponseData(JSONObject object){

			try {
				this.mediaNo = (Integer) object.get("mediaNo");
				JSONArray adClickResults= (JSONArray) object.get("adClickResult");
				adClickResult = new ArrayList<AdResponseData>();
				for (int i = 0; i < adClickResults.length(); i++) {
					JSONObject currentObject = (JSONObject) adClickResults.get(i);
					AdResponseData adResponseData = new AdResponseData(currentObject);
					this.adClickResult.add(adResponseData);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}



	}

	@Override
	public int compareTo(MnAResponseData o) {
		return Integer.compare(this.mediaNo,o.getMediaNo());
	}
}
