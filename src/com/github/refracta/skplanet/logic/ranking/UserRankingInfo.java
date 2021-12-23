package com.github.refracta.skplanet.logic.ranking;

import java.math.BigInteger;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-26 오전 12:50
 */
public class UserRankingInfo {
	private Integer rank;
	private String name;
	private BigInteger score;
	private Integer turn;
	private Integer turnRequest;
	private String updateTime;

	@Override
	public String toString() {
		return "{" +
				"순위=" + rank +
				", 이름='" + name + '\'' +
				", 점수=" + score +
				", 턴 수=" + turn +
				", 해당 턴의 요청 수=" + turnRequest +
				", 업데이트 일시='" + updateTime + '\'' +
				'}'+"\n";
	}

	public Integer getRank() {
		return rank;
	}

	public String getName() {
		return name;
	}

	public BigInteger getScore() {
		return score;
	}

	public Integer getTurn() {
		return turn;
	}

	public Integer getTurnRequest() {
		return turnRequest;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public UserRankingInfo(Integer rank, String name, BigInteger score, Integer turn, Integer turnRequest, String updateTime) {
		this.rank = rank;
		this.name = name;
		this.score = score;
		this.turn = turn;
		this.turnRequest = turnRequest;
		this.updateTime = updateTime;
	}
}
