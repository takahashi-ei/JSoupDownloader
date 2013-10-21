package SEOSearcher;

import org.jsoup.select.Elements;

public class CheckStrongCount extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "strongタグの回数が1回未満か";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Elements strongs = doc.select("strong");

		if( strongs.size() > 2 ){
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
