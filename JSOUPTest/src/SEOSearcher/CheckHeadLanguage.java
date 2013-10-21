package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadLanguage extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "head内に言語指定のタグがあるかどうか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements metaLanguage = head.select("meta[http-equiv=Content-Language]");
		if( metaLanguage.isEmpty() ){
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
