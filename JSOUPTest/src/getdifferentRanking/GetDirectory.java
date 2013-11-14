package getdifferentRanking;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.DATA_CONVERSION;
import StaticField.StaticFieldForDifferentRanking;

public class GetDirectory {
	private static List<String> directoryNames = new ArrayList<String>();
	private static int directoryCount = -1;
	
	public static List<String> getDirectoryName(){
		if(directoryCount == -1){
			init();
		}
		return directoryNames;
	}
	
	public static int getDirectoryCount(){
		if(directoryCount == -1){
			init();
		}
		return directoryCount;
	}
	
	private static void init(){
		File dir = new File(StaticFieldForDifferentRanking.DATA_DIRECTORY);
		File[] files = dir.listFiles();
		for(int i = 0; i < files.length;i++){
			if(files[i].isDirectory()){
				directoryNames.add(files[i].toString());
				directoryCount++;
			}
		}
	}
}
