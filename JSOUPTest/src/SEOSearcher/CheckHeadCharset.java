package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadCharset extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "headタグ内に文字コードが書かれているかどうか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements metaCharset = head.select("meta[charset], meta[http-equiv=Content-Type]");
		if( metaCharset.isEmpty() ){
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
