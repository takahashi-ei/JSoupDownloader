package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadTitleCount extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "titleタグの文字数が31文字以下か";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements title = head.select("title");
		String titleText = title.text();
		if( titleText.length() > 31 ){
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
