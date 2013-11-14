package actionWithDifferentRanking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import StaticField.StaticFieldForDifferentRanking;

public class WriteDifferentRankHtml extends ActionWithDifferentRankingImp{

	@Override
	public void output() throws IOException{
		if(beforeRank != -1 && afterRank != -1){
				printBeforeHtml();
				printAfterHtml();
		}
		
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void printBeforeHtml() throws IOException,FileNotFoundException{
		int differentRank =  (afterRank > beforeRank) ? (afterRank - beforeRank) : (beforeRank - afterRank); 
		File f = new File("data" + File.separatorChar
				+ StaticFieldForDifferentRanking.SAVE_DERECTORY + File.separatorChar
				+ query + File.separatorChar
				+ differentRank + "_" + beforeRank+ "_" + afterRank + File.separatorChar +
				"before.html");
		f.getParentFile().mkdirs();
		PrintWriter out = null;
		try {
			out= new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), getLetterCodeFromDoc(beforeDoc))));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(new FileInputStream(beforeAllHtml),getLetterCodeFromDoc(beforeDoc)));
		
		String line;
		while((line = br.readLine()) != null){
			out.print(line);
			out.print("\n");
		}
		out.close();
	}
	
	public void printAfterHtml() throws IOException,FileNotFoundException{
		int differentRank = (afterRank > beforeRank) ? (afterRank - beforeRank) : (beforeRank - afterRank); 
		File f = new File("data" + File.separatorChar
				+ StaticFieldForDifferentRanking.SAVE_DERECTORY + File.separatorChar
				+ query + File.separatorChar
				+ differentRank + "_"+ beforeRank+ "_" + afterRank + File.separatorChar +
				"after.html");
		System.out.println("filePlace=" +"data" + File.separatorChar
				+ StaticFieldForDifferentRanking.SAVE_DERECTORY + File.separatorChar
				+ query + File.separatorChar
				+ differentRank + "_"+ beforeRank+ "_" + afterRank + File.separatorChar +
				"after.html");
//		f.getParentFile().mkdirs();
		PrintWriter out = null;
		try {
			out= new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),getLetterCodeFromDoc(afterDoc))));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(new FileInputStream(afterAllHtml),getLetterCodeFromDoc(afterDoc)));
		
		String line;
		while((line = br.readLine()) != null){
			out.print(line);
			out.print("\n");
		}
		out.close();		
	}


}
