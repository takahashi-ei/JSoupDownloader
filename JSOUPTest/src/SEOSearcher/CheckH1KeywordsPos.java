package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckH1KeywordsPos extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "h1の前部にキーワードが含まれる";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element body = doc.body();
		Elements h1s = body.select("h1");

		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		for( Element h1 : h1s ){
			boolean flag = false;
			for( String keyword : keywords ){
				int index = h1.text().indexOf(keyword);
				if( index != -1 && index < h1.text().length()/2 ){ flag = true; break; }
			}
			
			// h1の前半にkeywordsが１つも入っていなかったら警告
			if( !flag ){
				return Integer.toString(0);
			}
		}
		return Integer.toString(1);
	}

}
