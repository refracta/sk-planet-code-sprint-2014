package com.github.refracta.skplanet.logic.wrapper.schedule;

import com.github.refracta.skplanet.logic.wrapper.total.MnAResponseData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 10:22
 */
public class ScheduleResult {
	private Integer turnNo;
	private Integer timeSeq;
	private ArrayList<MnAResponseData> data;

	@Override
	public String toString() {
		Collections.sort(data);
		return "스케쥴 결과{" +
				"턴=" + turnNo +
				", 요청 횟수=" + timeSeq +
				", \n결과 데이터=" + data +
				'}';
	}

	public Integer getTurnNo() {
		return turnNo;
	}

	public Integer getTimeSeq() {
		return timeSeq;
	}

	public ArrayList<MnAResponseData> getData() {
		return data;
	}

	public ScheduleResult(String text) {
		try {
			JSONObject object = new JSONObject(text);
			this.turnNo = (Integer) object.get("turnNo");
			this.timeSeq = (Integer) object.get("timeSeq");
			initDataArray((JSONArray) object.get("data"));
		} catch (JSONException e) {
			System.err.println(text);
			e.printStackTrace();
		}


	}

	public void initDataArray(JSONArray dataJsonArray) {
		data = new ArrayList<MnAResponseData>();
		for (int i = 0; i < dataJsonArray.length(); i++) {
			try {
				JSONObject currentJSONObject = (JSONObject) dataJsonArray.get(i);
				MnAResponseData mnAResponseData = new MnAResponseData(currentJSONObject);
				data.add(mnAResponseData);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
