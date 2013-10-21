package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetH1WordCount extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "先頭にあるh1タブの文字数";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element body = doc.body();
		Elements h1s = body.select("h1");
		if(h1s.size() == 0){
			return "";
		}
		Element h1 = h1s.get(0);

		return Integer.toString(h1.text().length());
	}

}
