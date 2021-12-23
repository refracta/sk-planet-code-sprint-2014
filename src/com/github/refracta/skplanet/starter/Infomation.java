package com.github.refracta.skplanet.starter;

import com.github.refracta.skplanet.logic.ranking.RankingUtil;
import com.github.refracta.skplanet.logic.ranking.UserRankingInfo;
import com.github.refracta.skplanet.logic.wrapper.total.MnAInfo;

import java.math.BigInteger;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-26 오전 12:27
 */
public class Infomation {
	public static void main(String[] args) {
		System.out.println(MnAInfo.getInfo().getAdList());
		System.out.println(MnAInfo.getInfo().getMediaList());
		System.out.println(RankingUtil.getRanking());
		trackingRanking("YOUR NAME");
	}
	public static void trackingRanking(String trackingUser)  {
		UserRankingInfo preRanking = RankingUtil.rankingSearch(trackingUser);
		while (true){
			UserRankingInfo currentRanking = RankingUtil.rankingSearch(trackingUser);

			System.out.println(currentRanking.toString().replace("\n",""));
			Integer changeRank = preRanking.getRank()-currentRanking.getRank();
			Integer changeTurnRequest = currentRanking.getTurnRequest()-preRanking.getTurnRequest();
			BigInteger changeScore = currentRanking.getScore().add(preRanking.getScore().divide(new BigInteger("-1")));
			if(changeRank!=0){
				if(changeRank>0) {
					System.out.println("Change Rank : +" + changeRank);
				}else{
					System.out.println("Change Rank : " + changeRank);

				}
			}
			if(!changeScore.toString().equals("0")){
				if(changeScore.toString().charAt(0)=='-'){
					System.out.println("Change Score : "+changeScore);
				}else{
					System.out.println("Change Score : +"+changeScore);


				}
			}
			if(changeTurnRequest!=0){
				if(changeTurnRequest>0){
					System.out.println("Change Turn Request : +"+changeTurnRequest);
				}else {
					System.out.println("Change Turn Request : "+changeTurnRequest);

				}
			}
			System.out.println();

			preRanking = currentRanking;
			for (int i = 0; i < 60; i++) {
				System.out.print((i + 1)+"...   ");
				if((i+1)%10==0){
					System.out.println();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
