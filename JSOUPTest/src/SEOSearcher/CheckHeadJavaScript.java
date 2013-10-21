package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadJavaScript extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "head内にJavaScript指定のタグがあるかどうか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element body = doc.body();
		Elements event = body.select("[^on]");

		Element head = doc.head();
		Elements metaJs = head.select("meta[http-equiv=Content-Script-Type]");

		if( !event.isEmpty() && metaJs.isEmpty() ){
			return Integer.toString(0);
		}else if( event.isEmpty() && !metaJs.isEmpty() ){
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
