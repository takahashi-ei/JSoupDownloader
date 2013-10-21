package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadDescriptionWordCount extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "ディスクリプションの文字数が60文字以上110文字以下か";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements metaDescription = head.select("meta[name=description]");
		String description = metaDescription.attr("content");
		if( description.length() >= 110 ){
			return Integer.toString(0);
		}else if( description.length() <= 60 ){
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
