package com.github.refracta.skplanet.starter;

import com.github.refracta.skplanet.logic.wrapper.ad.AdvertisementData;
import com.github.refracta.skplanet.logic.wrapper.total.MnAInfo;

import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-28 오전 2:03
 */
public class AdInfomation {
static ArrayList<AdvertisementData> preDataList = (ArrayList<AdvertisementData>) MnAInfo.getInfo().getAdList().getPaidAd().clone();
	public static void main(String[] args) throws InterruptedException {
		trackingRanking(5);
	}
	public static void trackingRanking(int sleepSecond){
		while (true) {
			MnAInfo.getInfo().reloadInfo();
			ArrayList<AdvertisementData> currentDataList = (ArrayList<AdvertisementData>) MnAInfo.getInfo().getAdList().getPaidAd().clone();
			for (int i = 0; i < currentDataList.size(); i++) {
				AdvertisementData currentData = currentDataList.get(i);
				AdvertisementData preData = null;
				for (int j = 0; j < preDataList.size(); j++) {
					if (preDataList.get(j).getAdNo().equals(currentData.getAdNo())) {
						preData = preDataList.get(j);
					}
				}
				int difference =  currentData.getImpressionCount()-preData.getImpressionCount();
				System.out.println(currentData.toString().replaceAll("\n","") + "　→　" + difference);
			}
			preDataList=currentDataList;
			try {
				Thread.sleep(1000*sleepSecond);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println();
			System.out.println();
		}
	}
}
