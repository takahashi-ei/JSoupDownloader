package SEOSearcher;

import StaticField.StaticField;

import java.io.File;

public class GetBodyWeight extends SEOSearcherImp{

	@Override
	public
	String getTitleInExcel() {
		// TODO 自動生成されたメソッド・スタブ
		return "bodyタグ内の文字数のサイズを返す";
	}

	@Override
	public
	String getResult() {
		// TODO 自動生成されたメソッド・スタブ
			File bodyFile = new File
					(StaticField.LOAD_FOLDER_NAME + "\\"+query+"_body\\" + engine + "\\"+ranking+".txt");
			return Long.toString(bodyFile.length());
	}
}
