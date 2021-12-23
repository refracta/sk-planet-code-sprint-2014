package com.github.refracta.skplanet.starter;

import com.github.refracta.skplanet.logic.wrapper.media.MediaClickProfiler;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-28 오후 12:33
 */
public class RankCalculator {
	public static void main(String[] args) {
		MediaClickProfiler mediaClickProfiler = new MediaClickProfiler(5);
		for (int i = 0; i < 1200; i++) {
			System.out.println(i+" : "+MediaClickProfiler.getRank(i+0.0));
		}
	}
}
