package SEOSearcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckH1KeywordsCount extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "h1タグのキーワードの数が3より小さい";
	}

	@Override
	public
	String getResult() {
		Element body = doc.body();
		Elements h1s = body.select("h1");

		Element head = doc.head();
		Elements metaKeywords = head.select("meta[name=keywords]");
		String[] keywords = metaKeywords.attr("content").split(",");

		for( Element h1 : h1s ){
			for( String keyword : keywords ){
				int sum = 0;
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
				Matcher m = p.matcher(h1.text());
				while(m.find()){
					sum++;
				}
				if(sum < 3){
					return Integer.toString(1);
				}

			}

		}
		return Integer.toString(0);
	}

}
