package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadCss extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "headタグにCSSの記述があるかどうか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element body = doc.body();
		Elements style = body.select("[style]");

		Element head = doc.head();
		Elements metaCss = head.select("meta[http-equiv=Content-Style-Type]");

		if( !style.isEmpty() && metaCss.isEmpty() ){
			return Integer.toString(0);
		}else if( style.isEmpty() && !metaCss.isEmpty() ){
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
