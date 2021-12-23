package com.github.refracta.skplanet.logic.wrapper.total;

import com.github.refracta.skplanet.logic.wrapper.ad.AdList;
import com.github.refracta.skplanet.logic.wrapper.media.MediaData;
import com.github.refracta.skplanet.logic.wrapper.media.MediaList;

import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-26 오전 1:20
 */
public class MnAInfo {

	private volatile static MnAInfo infomationSingleton;

	public MediaList getMediaList() {
		return mediaList;
	}

	public AdList getAdList() {
		return adList;
	}

	private volatile MediaList mediaList = MediaList.getMediaList();
	private volatile AdList adList = AdList.getAdList();
	public void reloadInfo(){
//		mediaList = MediaList.getMediaList();
		adList = AdList.getAdList();
	}
public void prudentMedia(Double d){
	ArrayList<MediaData> prudentMediaData = new ArrayList<MediaData>();
	for (int i = 0; i < this.mediaList.getData().size(); i++) {
		MediaData mediaData = this.mediaList.getData().get(i);
		mediaData = new MediaData(mediaData.getMediaNo(), (int) (mediaData.getMaxImpressionCountPerRequest()*d),mediaData.getFillRate());
		prudentMediaData.add(mediaData);
	}
	this.mediaList.setData(prudentMediaData);
}
	public static MnAInfo getInfo() {
		if (infomationSingleton == null) {
			synchronized (MnAInfo.class) {
				if (infomationSingleton == null) {
					infomationSingleton = new MnAInfo();
				}
			}
		}
		return infomationSingleton;
	}

	private MnAInfo() {


	}

}
