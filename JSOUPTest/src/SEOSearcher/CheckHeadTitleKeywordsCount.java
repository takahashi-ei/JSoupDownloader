package SEOSearcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckHeadTitleKeywordsCount extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "titleに含まれるキーワード値がチェック値どおりか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements title = head.select("title");
		String titleText = title.text();

		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		// titleに含まれるキーワードの個数をカウント
		int sum = 0;
		for( String keyword : keywords ){
			keyword = keyword.replace("¥", "\\");
			keyword = keyword.replace("*", "\\*");
			keyword = keyword.replace("+", "\\+");
			keyword = keyword.replace(".", "\\.");
			keyword = keyword.replace("?", "\\?");
			keyword = keyword.replace("|", "\\|");
			keyword = keyword.replace("-", "\\-");
			keyword = keyword.replace("$", "\\$");
			keyword = keyword.replace("^", "\\^");
			keyword = keyword.replace("]", "\\]");
			keyword = keyword.replace("[", "\\[");
			keyword = keyword.replace(")", "\\)");
			keyword = keyword.replace("(", "\\(");
			keyword = keyword.replace("}", "\\}");
			keyword = keyword.replace("{", "\\{");
			keyword = keyword.replace("?", "\\?");

			Pattern p = Pattern.compile(keyword);
			Matcher m = p.matcher(titleText);
			while(m.find()){
				sum++;
			}
		}

		// （titleタグ内で使ているキーワードの個数） /（titleタグ内のキーワードの種類） ×2
		double cal = sum * 2.0 / keywords.length;
		if( cal < 0.5 ){
			return Integer.toString(0);
		}else if( cal > 0.75 ){
			return Integer.toString(0);
		}
		return Integer.toString(1);
	}

}
