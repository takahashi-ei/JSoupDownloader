package getdifferentRanking;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GetQuery {
	public static List<String> getQuery() throws IOException{
		ArrayList<String> returnValue = new ArrayList<String>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("data//query.txt"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		while((line = br.readLine()) != null){
			returnValue.add(line);
		}
		return returnValue;
	}
}
