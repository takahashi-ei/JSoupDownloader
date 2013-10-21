package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetCountForKeywords extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "keywordsに書かれたキーワードの個数を返す";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");
		return Integer.toString(keywords.length);
	}

}
