package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckStrongInH1 extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "h1タグにstrongタグが含まれているかどうか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Elements h1Strong = doc.select("h1 strong");
		for (Element strong : h1Strong) {
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
