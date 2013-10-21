package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetHeadDescriptionKeywordPosParcent extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "descriptionのどの位置にキーワードが含まれているか（最小の位置を割合で返す）";
	}

	@Override
	public
	String getResult() {
		int minPlace = Integer.MAX_VALUE;
		String minKeyword = null;
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements metaDescription = head.select("meta[name=description]");
		String description = metaDescription.attr("content");

		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		boolean flag = false;
		for( String keyword : keywords ){
			int index = description.indexOf(keyword);
			if( index != -1 && minPlace > index ){ minPlace = index; minKeyword = keyword;}
		}
		if(description.equals(minKeyword)){
			return Integer.toString(0);
		}
		if(minPlace != Integer.MAX_VALUE){
		return Double.toString(((double)minPlace / (double)description.length()) * 100);
		}
		return "";
	}

}
