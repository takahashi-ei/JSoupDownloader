package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckH1WordCount extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "h1タブの文字数が30文字以下かどうか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element body = doc.body();
		Elements h1s = body.select("h1");

		for( Element h1 : h1s ){
			if( h1.text().length() > 30 ){
				return Integer.toString(0);
			}
		}
		return Integer.toString(1);
	}

}
