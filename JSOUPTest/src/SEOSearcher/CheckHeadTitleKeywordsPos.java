package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadTitleKeywordsPos extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "titleの前部にキーワードがあるか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements title = head.select("title");
		String titleText = title.text();

		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		boolean flag = false;
		for( String keyword : keywords ){
			int index = titleText.indexOf(keyword);
			if( index != -1 && index < titleText.length()/2 ){ flag = true; break; }
		}

		// titleの前半にkeywordsが１つも入っていなかったら警告
		if( !flag ){
			return Integer.toString(0);
		}
		
		return Integer.toString(1);
	}

}
