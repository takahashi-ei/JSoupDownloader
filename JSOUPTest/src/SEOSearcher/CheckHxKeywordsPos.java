package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHxKeywordsPos extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "hxの前部にキーワードがあるか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element body = doc.body();
		Elements hxs = body.select("h2,h3,h4,h5,h6");

		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		for( Element hx : hxs ){
			boolean flag = false;
			for( String keyword : keywords ){
				int index = hx.text().indexOf(keyword);
				if( index != -1 && index < hx.text().length()/2 ){ flag = true; break; }
			}
			
			// hxの前半にkeywordsが１つも入っていなかったら警告
			if( !flag ){
				return Integer.toString(0);
			}
		}
		return Integer.toString(1);
	}

}
