package com.github.refracta.skplanet.logic.wrapper.media;

import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-27 오후 5:59
 */
public class MediaClickProfiler {
	private Integer profileArraySize;

	public ArrayList<Integer> getProfileData() {
		return profileData;
	}

	public Integer getProfileArraySize() {
		return profileArraySize;
	}

	private ArrayList<Integer> profileData;
	public MediaClickProfiler(int profileArraySize) {
		this.profileArraySize = profileArraySize;
	this.profileData = new ArrayList<Integer>();
	}
	public void pushData(int click){
		this.profileData.add(click);
		if(this.profileData.size()>profileArraySize){
			this.profileData.remove(0);
		}
	}
	public double getAverage(){
		int total = 0;
		for (int i = 0; i < this.profileData.size(); i++) {
			total+=this.profileData.get(i);
		}
		if(this.profileArraySize==0){
			return 0;
		}
		return (total+0.0)/this.profileArraySize;
	}
	public double getAdvancedStatistic(){
		if(profileData.size()>0){
			return (getAverage()+profileData.get(profileData.size()-1))/2;
		}
		return 0;
	}
	public ClickRank getCurrentRank(){
		return getRank(getAdvancedStatistic());
	}
	public static ClickRank getRank(Double value) {
		ClickRank clickRank = null;
		while (clickRank==null) {
			if (value > ClickRank.SUPER_HIGH.minValue()) {
				clickRank = ClickRank.SUPER_HIGH;
			} else if (value > ClickRank.HIGH.minValue() && value < ClickRank.HIGH.maxValue()) {
				clickRank = ClickRank.HIGH;
			} else if (value > ClickRank.SUPER_NOMAL.minValue() && value < ClickRank.SUPER_NOMAL.maxValue()) {
				clickRank = ClickRank.SUPER_NOMAL;
			} else if (value > ClickRank.NOMAL.minValue() && value < ClickRank.NOMAL.maxValue()) {
				clickRank = ClickRank.NOMAL;
			} else if (value > ClickRank.SUPER_LOW.minValue() && value < ClickRank.SUPER_LOW.maxValue()) {
				clickRank = ClickRank.SUPER_LOW;
			} else if (value > ClickRank.LOW.minValue() && value < ClickRank.LOW.maxValue()) {
				clickRank = ClickRank.LOW;
			}else if(value<=0) {
				clickRank = ClickRank.LOW;
			}
		value += -1.0;
		}
	return clickRank;
	}
	public enum ClickRank {
		SUPER_HIGH(1310.0),
		HIGH(900.0),
		SUPER_NOMAL(775.0),
		NOMAL(50.0),
		SUPER_LOW(10.0),
		LOW(5.0);

		final private Double rankValue;
		final private Double errorValue = 0.33333;

		public Double minValue(){
			return rankValue-rankValue*errorValue;
		}
		public Double maxValue(){
			return rankValue+rankValue*errorValue;
		}
		ClickRank(Double d) {
			rankValue = d;
		}

		public Double value() {
			return rankValue;
		}
	}
}
