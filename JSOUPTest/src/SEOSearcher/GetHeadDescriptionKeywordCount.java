package SEOSearcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetHeadDescriptionKeywordCount extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "descriptionタグに使われるキーワードの個数";
	}

	@Override
	public
	String getResult() {
		int sum = 0;
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements metaDescription = head.select("meta[name=description]");
		String description = metaDescription.attr("content");

		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

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
			Matcher m = p.matcher(description);
			while(m.find()){
				sum++;
			}
		}
		return Integer.toString(sum);
	}

}
