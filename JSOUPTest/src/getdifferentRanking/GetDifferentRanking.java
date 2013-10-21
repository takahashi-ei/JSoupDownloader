package getdifferentRanking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import StaticField.StaticFieldForDifferentRanking;

public class GetDifferentRanking {
	public static void main(String[] args){
		String oneFileName;
		String compareFileName;
		List<String> oneUrlLists = new ArrayList<String>();
		List<String> compareUrlLists = new ArrayList<String>();
		if(StaticFieldForDifferentRanking.G_FLAG){
			oneFileName = StaticFieldForDifferentRanking.ONE_FOLDER + "\\" + StaticFieldForDifferentRanking.QUERY + "_google.txt";
			compareFileName = StaticFieldForDifferentRanking.OTHER_FOLDER + "\\" + StaticFieldForDifferentRanking.QUERY + "_google.txt";
		}else{
			oneFileName = StaticFieldForDifferentRanking.ONE_FOLDER + "\\" + StaticFieldForDifferentRanking.QUERY + "_yahoo.txt";
			compareFileName = StaticFieldForDifferentRanking.OTHER_FOLDER + "\\" + StaticFieldForDifferentRanking.QUERY + "_yahoo.txt";			
		}
		
		try {
			oneUrlLists = getUrlLists(oneFileName);
			compareUrlLists = getUrlLists(compareFileName);
			System.out.println(oneUrlLists.size());
			System.out.println(compareUrlLists.size());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		findDifferent(oneUrlLists,compareUrlLists);

		File differentWriteFile = new File(StaticFieldForDifferentRanking.ONE_DATE + "_" + StaticFieldForDifferentRanking.OTHER_DATE + File.separatorChar +StaticFieldForDifferentRanking.QUERY + "_one.txt");
		differentWriteFile.getParentFile().mkdirs();
		PrintWriter pageBak = null;
		try {
			pageBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(differentWriteFile),"utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}		
		for(int i = 0; i < oneUrlLists.size(); i++){
			pageBak.print(oneUrlLists.get(i) + "\n");
		}
		pageBak.close();
		
		
		differentWriteFile = new File(StaticFieldForDifferentRanking.ONE_DATE + "_" + StaticFieldForDifferentRanking.OTHER_DATE + File.separatorChar +StaticFieldForDifferentRanking.QUERY + "_other.txt");
		differentWriteFile.getParentFile().mkdirs();
		pageBak = null;
		try {
			pageBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(differentWriteFile),"utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}		
		for(int i = 0; i < compareUrlLists.size(); i++){
			pageBak.print(compareUrlLists.get(i) + "\n");
		}
		pageBak.close();
		
		
		
	}
	
	/**
	 * 引数のファイルから,ファイルに書かれた内容を取り出したリストを作る
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static List<String> getUrlLists(String fileName) throws IOException{
		List<String> returnValue = new ArrayList<String>();	
		BufferedReader br = null;
		String line = "";
		

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		while((line = br.readLine()) != null){
			returnValue.add(line);
		}
		br.close();
		return returnValue;
	}
	
	private static void findDifferent(List<String> oneUrlLists,List<String> compareUrlLists){
		List<String> differentUrl = new ArrayList<String>();
		List<String> outputEndUrl = new ArrayList<String>();
		List<Integer> beforeRanking = new ArrayList<Integer>();
		List<Integer> afterRanking = new ArrayList<Integer>();
		//jの値を保存するために使う
		int temp;
		for(int i = 0;i < oneUrlLists.size(); i++){
			temp = -1;
			if(outputEndUrl.contains(oneUrlLists.get(i))){
				continue;
			}
			for(int j = 0; j < compareUrlLists.size(); j++){

				if(oneUrlLists.get(i).equals(compareUrlLists.get(j))){
					temp = j;
					break;
				}
				
			}
			if(i != temp){
				if(temp != -1){
					outputEndUrl.add(oneUrlLists.get(i));
					differentUrl.add(oneUrlLists.get(i));
					beforeRanking.add(i+1);
					afterRanking.add(temp+1);
				}else{
					differentUrl.add(oneUrlLists.get(i));
					outputEndUrl.add(oneUrlLists.get(i));
					beforeRanking.add(i+1);
					afterRanking.add(temp);					
				}
			}
		}
		File differentWriteFile = new File(StaticFieldForDifferentRanking.ONE_DATE + "_" + StaticFieldForDifferentRanking.OTHER_DATE + File.separatorChar +StaticFieldForDifferentRanking.QUERY + "_different.txt");
		differentWriteFile.getParentFile().mkdirs();
		PrintWriter pageBak = null;
		try {
			pageBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(differentWriteFile),"utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		pageBak.print("URL" + ",");
		pageBak.print(StaticFieldForDifferentRanking.ONE_DATE + ",");
		pageBak.print(StaticFieldForDifferentRanking.OTHER_DATE);
		pageBak.print("\n");
		for(int i = 0;i < differentUrl.size(); i++){
			pageBak.print(differentUrl.get(i) + ",");
			pageBak.print(beforeRanking.get(i) + ",");
			pageBak.print(afterRanking.get(i));
			pageBak.print("\n");
		}
		pageBak.close();

	}
}
