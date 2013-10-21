package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadUnusedTag extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "headタグ内に無駄なタグが含まれているか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();

		Elements metaTag = head.select("meta[name=GENERATOR]");
		if( !metaTag.isEmpty() ){
			return Integer.toString(0);
		}

		metaTag = head.select("meta[name=author]");
		if( !metaTag.isEmpty() ){
			return Integer.toString(0);
		}

		metaTag = head.select("meta[name=copyright]");
		if( !metaTag.isEmpty() ){
			return Integer.toString(0);
		}

		metaTag = head.select("meta[name=robots]");
		if( !metaTag.isEmpty() ){
			return Integer.toString(0);
		}

		metaTag = head.select("meta[name=made]");
		if( !metaTag.isEmpty() ){
			return Integer.toString(0);
		}

		metaTag = head.select("meta[name=rating]");
		if( !metaTag.isEmpty() ){
			return Integer.toString(0);
		}

		metaTag = head.select("meta[name=revisit_after]");
		if( !metaTag.isEmpty() ){
			return Integer.toString(0);
		}

		return Integer.toString(1);
	}

}
