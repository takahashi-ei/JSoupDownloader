package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckImgAlt extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "imgタグにaltが設定されてないものを検出してみる";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Elements imgs = doc.select("img");
		for (Element img : imgs) {
			// alt=""の場合は検出できない方法
			if( !img.hasAttr("alt") ){
				return Integer.toString(0);
			}

			// alt=""の場合も検出できる方法
			String alt_text = img.attr("alt");
			if( alt_text.isEmpty() ){
				return Integer.toString(0);
			}
		}
		
		return Integer.toString(1);
	}

}
