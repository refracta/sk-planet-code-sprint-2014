package com.github.refracta.skplanet.logic.wrapper.media;

import com.github.refracta.skplanet.helper.HttpClientWrapper;
import com.github.refracta.skplanet.logic.token.TokenConst;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 9:42
 */
public class MediaList {
	private Integer turnNo;
	private Integer timeSeq;
	private ArrayList<MediaData> data;

	public void setData(ArrayList<MediaData> data) {
		this.data = data;
	}

	public int getTurnExpressionNumber() {
		int returnInt = 0;
		for (int i = 0; i < getData().size(); i++) {
			returnInt += getData().get(i).getMaxImpressionCountPerRequest();
		}
		return returnInt;
	}

	@Override
	public String toString() {
		return "미디어 배열{" +
				"턴=" + turnNo +
				", 요청 수=" + timeSeq +
				", \n미디어 데이터=" + data +
				", 모든 최대 광고 노출 가능 횟수의 합 : " + getTurnExpressionNumber() +

				'}';
	}

	public static MediaList getMediaList() {
		HttpClientWrapper extender = new HttpClientWrapper(HttpClientWrapper.CHROME_AGENT);
		return new MediaList(extender.easyRequestGetPage(TokenConst.MEDIA_LIST));
	}

	public Integer getTurnNo() {
		return turnNo;
	}

	public Integer getTimeSeq() {
		return timeSeq;
	}

	public ArrayList<MediaData> getData() {
		return data;
	}

	public MediaList(String text) {
		try {
			JSONObject object = new JSONObject(text);
			this.turnNo = (Integer) object.get("turnNo");
			this.timeSeq = (Integer) object.get("timeSeq");
			initDataArray((JSONArray) object.get("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void initDataArray(JSONArray dataJsonArray) {
		data = new ArrayList<MediaData>();
		for (int i = 0; i < dataJsonArray.length(); i++) {
			try {
				JSONObject currentJSONObject = (JSONObject) dataJsonArray.get(i);
				MediaData mediaData = new MediaData(currentJSONObject);
				data.add(mediaData);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
