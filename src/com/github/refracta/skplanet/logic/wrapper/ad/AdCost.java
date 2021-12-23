package com.github.refracta.skplanet.logic.wrapper.ad;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-27 오후 9:47
 */
public enum AdCost {
	COST_200(200),
	COST_100(100),
	COST_10(10),
	COST_1(1),
	FREE(0);


	final private Integer value;



	AdCost(Integer d) {
		value = d;
	}

	public Integer value() {
		return value;
	}
}
