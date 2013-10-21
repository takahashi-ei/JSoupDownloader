package SEOSearcher;

import java.io.File;

import StaticField.StaticField;

public class GetHtmlWeight extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "htmlファイルの大きさを返す";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		File allFile = new File(StaticField.LOAD_FOLDER_NAME + "\\"+query+"\\"+engine+"\\"+ranking+".html");
		return Long.toString(allFile.length());
	}

}
