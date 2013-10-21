package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetHeadDescriptionQueryPos extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "descriptionタグにクエリがどの位置に書かれているか";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		Element head = doc.head();
		Elements metaDescription = head.select("meta[name=description]");
		String description = metaDescription.attr("content");
		System.out.println(description);
		if(description.indexOf(query) != -1){
			int position = description.indexOf(query);
			int descriptionLength = description.length() - query.length();
			if(description.equals(query)){
				return Integer.toString(0);
			}
			return Double.toString(position);
		}
		return "";
	}
}
