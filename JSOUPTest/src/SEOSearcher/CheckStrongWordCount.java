package SEOSearcher;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckStrongWordCount extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "同じ単語を複数回strongしないかどうか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Set<String> strongWords = new HashSet<String>();
		Elements strongs = doc.select("strong");
		for (Element strong : strongs) {
			if( strongWords.contains(strong.text()) ){
				return Integer.toString(0);
			}else{
				strongWords.add(strong.text());
			}
		}
		return Integer.toString(1);
	}

}
