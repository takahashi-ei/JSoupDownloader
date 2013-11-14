package getdifferentRanking;

import java.util.ArrayList;
import java.util.List;

public class UrlAndSearchRankings {
	private String url;
	private List<Integer> rankings;
	
	/**
	 * 引数urlを使って,urlを初期化する
	 * rankingについては,
	 * @param urls
	 */
	public UrlAndSearchRankings(String urls){
		this.url = url;
		rankings = new ArrayList<Integer>();
	}
	
	public void addRanking(int ranking){
		rankings.add(ranking);
	}
	
	String getUrl(){
		return url;
	}
	
	public String output(){
		String returnValue = this.url + " ";
		for(int i = 0; i < this.rankings.size();i++){
			returnValue += this.rankings.get(i) + " ";
		}
		returnValue += "\n";
		return returnValue;
	}
}
