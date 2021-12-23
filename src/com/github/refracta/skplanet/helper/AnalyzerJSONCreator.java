package com.github.refracta.skplanet.helper;

import com.github.refracta.skplanet.logic.wrapper.ad.AdRequestData;
import com.github.refracta.skplanet.logic.wrapper.ad.AdResponseData;
import com.github.refracta.skplanet.logic.wrapper.schedule.ScheduleResult;
import com.github.refracta.skplanet.logic.wrapper.total.MnARequestData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-27 오전 12:37
 */
public class AnalyzerJSONCreator {
	public static final String saveFolder = "C:\\Development\\Project\\IDEA Project\\SK Planet\\AnalyzerData\\";
	public static void saveAnalyzerJSONString(ArrayList<MnARequestData> mnARequestDatas, ScheduleResult scheduleResult){
		String analyzerJSONString = getAnalyzerJSONString(mnARequestDatas, scheduleResult);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(saveFolder+"\\"+scheduleResult.getTimeSeq()+".json");
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			byte[] bytes = analyzerJSONString.getBytes();
			bufferedOutputStream.write(bytes);
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
public static String getAnalyzerJSONString(ArrayList<MnARequestData> mnARequestDatas,ScheduleResult scheduleResult){
	Collections.sort(mnARequestDatas);
	Collections.sort(scheduleResult.getData());
	JSONObject object = new JSONObject();
	JSONArray resultArray = new JSONArray();
	try {
		for (int i = 0; i < mnARequestDatas.size(); i++) {

			if(scheduleResult.getData().get(i).getMediaNo().equals(mnARequestDatas.get(i).getMediaNo())){
				JSONObject arrayElement  = new JSONObject();
				arrayElement.put("mediaNo",scheduleResult.getData().get(i).getMediaNo());
				HashMap<String, Integer> dataHashMap = getDataHashMap(mnARequestDatas.get(i).getAdPutRequest(), scheduleResult.getData().get(i).getAdClickResult());
				arrayElement.put("free", dataHashMap.get("free"));
				arrayElement.put("paid", dataHashMap.get("paid"));
				arrayElement.put("freeClickCount", dataHashMap.get("freeClickCount"));
				arrayElement.put("paidClickCount", dataHashMap.get("paidClickCount"));
				resultArray.put(arrayElement);
				//mnARequestDatas.get(i).getAdPutRequest() - 프리랑 페이드
			}
		}
		object.put("timeSeq",scheduleResult.getTimeSeq());
		object.put("resultData",resultArray);

	} catch (JSONException e) {
		e.printStackTrace();
	}
return object.toString();
}
	public static HashMap<String, Integer> getDataHashMap(ArrayList<AdRequestData> adPutRequest, ArrayList<AdResponseData> adClickResult){
		HashMap<String,Integer> returnHashMap = new HashMap<String, Integer>();
		for (int i = 0; i < adPutRequest.size(); i++) {
			AdRequestData currentAdRequestData = adPutRequest.get(i);
			if(currentAdRequestData.isFreeAd()){
				changeHashMap(returnHashMap,"free",currentAdRequestData.getPutCount());
			}else{
				changeHashMap(returnHashMap,"paid",currentAdRequestData.getPutCount());
			}
		}
		for (int i = 0; i < adClickResult.size(); i++) {
			AdResponseData currentAdResponseData = adClickResult.get(i);
			if(currentAdResponseData.isFreeAd()){
				changeHashMap(returnHashMap,"freeClickCount",currentAdResponseData.getClickCount());
			}else{
				changeHashMap(returnHashMap,"paidClickCount",currentAdResponseData.getClickCount());

			}
		}
		return returnHashMap;
	}
	public static void changeHashMap(HashMap<String,Integer> hashMap, String key,Integer value){
		if(hashMap.get(key)!=null){
			hashMap.put(key,hashMap.get(key)+value);
		}else{
			hashMap.put(key,value);
		}
	}
}

