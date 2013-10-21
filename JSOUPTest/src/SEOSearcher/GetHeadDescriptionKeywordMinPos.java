package SEOSearcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetHeadDescriptionKeywordMinPos extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "descriptionタグで一番最初に現れるキーワード";
	}

	@Override
	public
	String getResult() {
		String minKeyword = null;
		int minPlace = Integer.MAX_VALUE;
		int sum = 0;
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements metaDescription = head.select("meta[name=description]");
		String description = metaDescription.attr("content");

		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		for( String keyword : keywords ){
			int index = description.indexOf(keyword);
			if( index != -1 && minPlace > index ){ minPlace = index; minKeyword = keyword;}
			
		}
		return minKeyword;
	}

}
