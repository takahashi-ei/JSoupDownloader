package SEOSearcher;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetHeadDescriptionQueryPosParcent extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "descriptionタグにクエリがどの位置に書かれているかを割合で示す";
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
			double returnValue = ((double)position / (double)descriptionLength) * 100;
			return Double.toString(returnValue);
		}
		return "";
	}
}
