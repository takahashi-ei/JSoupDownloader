package SEOSearcher;

import java.io.File;

import StaticField.StaticField;

public class GetHeadWeight extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "headタグの大きさ";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
		File headFile = new File(StaticField.LOAD_FOLDER_NAME + "\\"+query+"_head\\" + engine + "\\"+ranking+".html");
		return Long.toString(headFile.length());
	}

}
