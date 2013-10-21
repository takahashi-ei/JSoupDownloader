package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadTitleSymble extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "titleに記号が含まれているか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements title = head.select("title");
		String titleText = title.text();

		if( titleText.matches(".*[!\"#\\$%&'\\(\\)=~`\\{\\+\\*\\}<>\\?_\\-\\^\\\\@\\[;:\\],\\./" + "！”＃＄％＆’（）＝～‘｛＋＊｝＜＞？＿－＾￥＠「；：」、。・].*") ){
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
