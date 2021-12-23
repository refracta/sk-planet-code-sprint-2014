package com.github.refracta.skplanet.logic.wrapper.schedule;

import com.github.refracta.skplanet.logic.wrapper.total.MnARequestData;
import com.github.refracta.skplanet.helper.HttpClientWrapper;
import com.github.refracta.skplanet.logic.token.TokenConst;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-25 오후 9:51
 */
public class ScheduleUtil {
	public ScheduleResult request(String json){
		HttpClientWrapper httpClientWrapper = new HttpClientWrapper(HttpClientWrapper.CHROME_AGENT);
		String response = httpClientWrapper.easyRequestPostPage(TokenConst.SCHEDULE, json);
		ScheduleResult scheduleResult = new ScheduleResult(response);
		return scheduleResult;
	}
	public ScheduleResult request(ArrayList<MnARequestData> mnARequestDatas){

		return request(getRequestScheduleObject(mnARequestDatas));
	}
public String getRequestScheduleObject(ArrayList<MnARequestData> mnARequestDatas){
	JSONObject finalObject = new JSONObject();
	JSONArray dataArray = new JSONArray();
	for (int i = 0; i < mnARequestDatas.size(); i++) {
		if (!(mnARequestDatas.get(i).getAdPutRequest().size() ==0)) {
			JSONObject currentObject = mnARequestDatas.get(i).getObject();
			dataArray.put(currentObject);
		}
	}
	try {
		finalObject.put("data",dataArray);
	} catch (JSONException e) {
		e.printStackTrace();
	}
	return finalObject.toString();
}


}
