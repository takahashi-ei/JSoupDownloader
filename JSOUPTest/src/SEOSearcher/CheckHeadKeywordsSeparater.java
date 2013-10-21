package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadKeywordsSeparater extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "キーワードをカンマ区切りに書いているか";
	}

	@Override
	public
	String getResult() {
		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");

		
		String[] keywords = metaKeywords.attr("content").split("[、．.。・]");
		if( keywords.length > 1 ){
			return Integer.toString(0);
		}

		return Integer.toString(1);
	}

}
