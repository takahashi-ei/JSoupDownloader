package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CountHeadUnusedTag extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "headタグに含まれる不要タグの個数";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		int count = 0;
		Elements metaTag = head.select("meta[name=GENERATOR]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=author]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=copyright]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=robots]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=made]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=rating]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}

		metaTag = head.select("meta[name=revisit_after]");
		if( !metaTag.isEmpty() ){
			count+=1;
		}	
		return Integer.toString(count);
	}

}
