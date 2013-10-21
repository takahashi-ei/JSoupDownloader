package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetH1QueryPos extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "先頭にあるh1のどの位置にクエリが含まれているか";
	}

	@Override
	public
	String getResult() {
		int minPlace = Integer.MAX_VALUE;
		// TODO 自動生成されたメソッド・スタブ
		Element body = doc.body();
		Elements h1s = body.select("h1");
		if(h1s.size() == 0){
			return "";
		}
		Element h1 = h1s.get(0);
		String h1Text = h1.text();
		if(h1Text.indexOf(query) == -1){
			return "";
		}
		return Integer.toString(h1Text.indexOf(query));
	}

}
