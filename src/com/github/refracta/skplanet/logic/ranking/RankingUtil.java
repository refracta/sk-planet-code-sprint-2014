package com.github.refracta.skplanet.logic.ranking;

import com.github.refracta.skplanet.helper.HttpClientWrapper;
import com.github.refracta.skplanet.helper.RegexHelper;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * 개발자 : refracta
 * 날짜   : 2014-05-26 오전 12:47
 */
public class RankingUtil {
	public static final String RANGKING_LINK = "https://codesprint.skplanet.com/2014/ranking/round2_ranking.htm";

	public static ArrayList<UserRankingInfo> getRanking() {
		HttpClientWrapper wrapper = new HttpClientWrapper(HttpClientWrapper.CHROME_AGENT);
		String loadedPage = wrapper.easyRequestHtmlGetPage(RANGKING_LINK);
		ArrayList<String> regexPatternToArray = RegexHelper.regexPatternToArray("(?<=" + "<tr>" + ").+?(?=" + "</tr>" + ")", loadedPage);
		ArrayList<UserRankingInfo> returnList = new ArrayList<UserRankingInfo>();
		for (int i = 1; i < regexPatternToArray.size(); i++) {
			String currentStr = regexPatternToArray.get(i);
			ArrayList<String> tdTag = RegexHelper.regexPatternToArray("(?<=" + "<td>" + ").+?(?=" + "</td>" + ")", currentStr);
			UserRankingInfo userRankingInfo = new UserRankingInfo(Integer.parseInt(tdTag.get(0))
					, tdTag.get(1)
					, new BigInteger(tdTag.get(2).replaceAll("[,]", ""))
					, Integer.parseInt(tdTag.get(3))
					, Integer.parseInt(tdTag.get(4))
					, tdTag.get(5));
			returnList.add(userRankingInfo);
		}
		return returnList;
	}
	public static UserRankingInfo rankingSearch(String name){
		ArrayList<UserRankingInfo> ranking = getRanking();
		for (int i = 0; i < ranking.size(); i++) {
			if(ranking.get(i).getName().equals(name)){
				return ranking.get(i);
			}
		}

		return null;
	}
}
