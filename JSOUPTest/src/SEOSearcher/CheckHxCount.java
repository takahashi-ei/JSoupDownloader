package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHxCount extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "hxタグを多用していないか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element body = doc.body();
		Elements h2s = body.select("h2");

		if( h2s.size() > 2 ){
			return Integer.toString(0);
		}

		for( int i = 3 ; i<=6 ; i++ ){
			Elements hxs = body.select("h"+i);
			if( hxs.size() > 5 ){
				return Integer.toString(0);
			}
		}
		return Integer.toString(1);
	}

}
