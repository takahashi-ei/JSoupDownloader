package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHxStrong extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "hxとstrongの併用はしているかどうか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Elements hxStrong = doc.select("h2 strong,h3 strong,h4 strong,h5 strong,h6 strong");
		for (Element strong : hxStrong) {
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
