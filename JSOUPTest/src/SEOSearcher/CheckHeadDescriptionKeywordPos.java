package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadDescriptionKeywordPos extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "descriptionの前方にキーワードが含まれているか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements metaDescription = head.select("meta[name=description]");
		String description = metaDescription.attr("content");

		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		boolean flag = false;
		for( String keyword : keywords ){
			int index = description.indexOf(keyword);
			if( index != -1 && index < description.length()/2 ){ flag = true; break; }
		}

		// descriptionの前半にkeywordsが１つも入っていなかったら警告
		if( !flag ){
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
