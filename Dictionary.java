import java.util.Scanner;
import java.util.Vector;
import java.io.*;
import java.lang.reflect.Array;

public class Dictionary {
	private int MAX_NUMBER = 37000;
	private int NUMBER_OF_ELEMENTS = 0;
	private String fileName = "dictionary.txt";
	private String[] words =new String[MAX_NUMBER];
	private String[] explains = new String[MAX_NUMBER];
	int maxSim = 100;//For counting similarity
	int indexOfSim = 0;//The index of max similarity
	private String[] extraWords =new String[100];
	private String[] extraExplains = new String[100];
	int NUMBER_OF_EXTRA = 0;
	int maxLen =0;
	int indexMaxLen=0;
	
	public String longStr ="";
	Vector<String> sim = new Vector<String>();
	//int recusion = 0; 
	public Dictionary() {
		buildDictionary();
		
	}
	
	private void buildDictionary() {
		File src_file = new File(fileName);
		//if(src_file.exists())
		try{
			Scanner input = new Scanner(src_file);
			int count = 0;
			String temp =null;
			while(input.hasNext()){//divide the words and their explains
				temp = input.nextLine();
				temp = temp.trim();
				
				for(;count < temp.length();count++){
					if(temp.charAt(count) >= 97 && temp.charAt(count) <= 125)
						break;
				}
				//Ignore the first line
				if(count == temp.length()) {
					count = 0;continue;
				}
				//get word
				int theEndOfWord = count+1;//the start of explanation
				int countOfSpace = 0;//when count of space >=2, this's the end of a word
				for(;theEndOfWord < temp.length();theEndOfWord ++){
					if(temp.charAt(theEndOfWord) == '	'||temp.charAt(theEndOfWord) == '\''||
					(temp.charAt(theEndOfWord) >= '0' && temp.charAt(theEndOfWord) <= '9'))
						countOfSpace ++;
					else countOfSpace = 0;
					if(countOfSpace == 1){
						words[NUMBER_OF_ELEMENTS] = temp.substring(count, theEndOfWord);
						explains[NUMBER_OF_ELEMENTS] = temp.substring(theEndOfWord).trim();
						NUMBER_OF_ELEMENTS ++;
						break;
					}
				}
				count = 0;
			}
			//display to test
			/*
			System.out.println(NUMBER_OF_ELEMENTS);
			for(int i = 0;i<NUMBER_OF_ELEMENTS ;i++){
				System.out.println(i+" word:"+words[i]+" explain:"+explains[i]);
			}
			*/
			input.close();
		}
		catch(Exception e){
			System.out.println("Filename error");
			System.out.println(fileName);
			System.out.println(NUMBER_OF_ELEMENTS +words[NUMBER_OF_ELEMENTS-1]+explains[NUMBER_OF_ELEMENTS-1]);
			e.printStackTrace();
			System.exit(0);
		}
		String tempWords;
	    String tempExplains;
	  
	    for (int i = 0; i < NUMBER_OF_ELEMENTS-1; i++) {   
	        for (int j = i + 1; j < NUMBER_OF_ELEMENTS; j++) {   
	            if (words[i].compareTo(words[j])>0) { // exchange
	                tempWords = words[i];
	                tempExplains = explains[i];
	                words[i] = words[j];
	                explains[i] = explains[j];
	                words[j] = tempWords;
	                explains[j] = tempExplains;
	            }   
	        }   
	    }   
	}
	
	//binary search using recusion(error!) 
	/*
	private int binarySearch(String words,int beginning,int end){
		
		if(beginning == end && this.words[beginning].compareTo(words) ==0)
			return -1;
		
		if(this.words[(end+beginning)/2].compareTo(words)== 0)
			return (end+beginning)/2;
		else if(this.words[(end+beginning)/2].compareTo(words) > 0)
			return binarySearch(words,beginning,(end+beginning)/2-1);
		else //this.words[(end-beginning)/2].compareTo(words) < 0
			return binarySearch(words,(end+beginning)/2+1,end);
		
	}
	*/
	//binary search(right)
	private int binarySearch(String words){
		int low = 0;   
        int high =NUMBER_OF_ELEMENTS-1;   
        while(low <= high) {   
            int middle = (low + high)/2;   
            if(this.words[middle].compareTo(words)== 0) {   
                return middle;   
            }else if(this.words[middle].compareTo(words) > 0) {   
                high = middle - 1;   
            }else {   
                low = middle + 1;   
            }  
        }  
        return -1;  
	}
	
	private int simpleSearch(String words){
		for(int i=0;i<NUMBER_OF_ELEMENTS;i++)
			if(this.words[i].startsWith(words)== true)
				return i;
		return -1;
	}
	//search If existing return the index else return -1
	public int searchMethod(String words){
		//return binarySearch(words);
		return simpleSearch(words);
	}
	//get private data
	public int getNumberOfElements(){
		return NUMBER_OF_ELEMENTS;
	}
	
	public String getWords(int index){
		if(index == -1){
			//System.out.println("FUCK ");
			return "WHAT THE FUCK";
			}
		else
		return words[index];
	}
	public String getExplains(int index){
		if(index == -1){
			//System.out.println("I HOPE IT NEVER APPEAR");
			return "IF IT HAPPENS PLEASE EMAIL ME. 4shinyfuture@gmail.com THANKS ";
			}
		else
		return explains[index];
	}
	
	public int searchMethod2(String words){
		
		return binarySearch(words);
		//return simpleSearch(words);
	}
	
	public void countSim(String words){
		maxSim = 100;
		sim.clear();
		for(int i = 0;i<NUMBER_OF_ELEMENTS;i++){
			if(EditDistance(this.words[i],words)<maxSim){
				maxSim = EditDistance(this.words[i],words);
				indexOfSim = i;
			}
			if(EditDistance(this.words[i],words)< 2)
				sim.add(this.words[i]);
		}
		if(sim.isEmpty() == true)
			sim.add(this.words[indexOfSim]);
	}
	
	public void simClear(){
		sim.clear();
	}
	
	public void simAdd(String word){
		sim.add(word);
	}
	
	public Vector<String> simDisplay(){
		return sim;
	}
	//sort
	/*
	public static void bubbleSort(String[] words,String[] explains) {   
	    String tempWords;
	    String tempExplains;
	  
	    for (int i = 0; i < NUMBER_OF_ELEMENTS-1; i++) {   
	        for (int j = i + 1; j < NUMBER_OF_ELEMENTS; j++) {   
	            if (words[i].compareTo(words[j])>0) { // exchange
	                tempWords = words[i];
	                tempExplains = explains[i];
	                words[i] = words[j];
	                explains[i] = explains[j];
	                words[j] = tempWords;
	                explains[j] = tempExplains;
	            }   
	        }   
	    }   
	} 
	*/ 
	//count the distance
	private static int EditDistance(String source, String target)
    {  
        char[] s=source.toCharArray();  
        char[] t=target.toCharArray();  
        int slen=source.length();  
        int tlen=target.length();  
        int d[][]=new int[slen+1][tlen+1];  
        for(int i=0;i<=slen;i++){  
            d[i][0]=i;  
        }  
        for(int i=0;i<=tlen;i++){  
            d[0][i]=i;  
        }  
        for(int i=1;i<=slen;i++){  
            for(int j=1;j<=tlen;j++){  
                if(s[i-1]==t[j-1]){  
                    d[i][j]=d[i-1][j-1];  
                }
                else{  
                    int insert=d[i][j-1]+1;  
                    int del=d[i-1][j]+1;  
                    int update=d[i-1][j-1]+1;  
                    d[i][j]=Math.min(insert, del)>Math.min(del, update)?Math.min(del, update):Math.min(insert, del);  
                }  
            }  
        }  
        return d[slen][tlen];  
    }  
//Add new dictionary
public void addNew(String words,String explains){
	if(NUMBER_OF_EXTRA<100){
		this.words[NUMBER_OF_EXTRA] =words;
		this.explains[NUMBER_OF_EXTRA] =words+"(added just now): "+explains;
		NUMBER_OF_EXTRA++;
	}
	else System.out.println("FULL");
}

public void addNEW2(String words,String explains) throws Exception{
	if(NUMBER_OF_ELEMENTS<MAX_NUMBER){
		NUMBER_OF_ELEMENTS++;
		String temp = null;
		String tempE = null;
		int changeIndex = 0;
		for(int i=0 ;i<NUMBER_OF_ELEMENTS-1;changeIndex++,i++){
			if(this.words[i].compareTo(words)>0){
				temp = this.words[i];
				tempE = this.explains[i];
				this.words[i] = words;
				this.explains[i] =words+"(added just now): "+explains;
				break;
			}
		}
		for(int i =NUMBER_OF_ELEMENTS;i>changeIndex+1;i--){
			this.words[i] = this.words[i-1];
			this.explains[i] = this.explains[i-1];
		}
		this.words[changeIndex+1]= temp;
		this.explains[changeIndex+1] = tempE;
		File sourceFile = new File(fileName);
		PrintWriter output = new PrintWriter(sourceFile);
		for(int i=0;i<NUMBER_OF_ELEMENTS;i++){
			output.println(this.words[i]+"	"+this.explains[i]);
		}
		output.close();
	}
	else System.out.println("FULL");
	}

	public void subSearch(String words){
		words = words.trim();
		while(words.compareTo("")!=0&&words!=null){
			boolean flag = false;
			maxLen=0;indexMaxLen=0;
			for(int i =0;i<NUMBER_OF_ELEMENTS;i++){
				if(words.startsWith(this.words[i])==true){
					int len =this.words[i].length();
					if(len>maxLen){
						maxLen = len;
						indexMaxLen = i;
						flag =true;
					}
				}
				
				if(i==NUMBER_OF_ELEMENTS-1 && flag==false){
					longStr = longStr+"  notfound: "+words;
					return;
				}
			}
				if(longStr.compareTo("")!=0)
				longStr =longStr+ this.explains[indexMaxLen].trim();
				else
					longStr =" "+this.explains[indexMaxLen].trim();
				words=words.replaceFirst(this.words[indexMaxLen], "");
				System.out.println(words);
				words = words.trim();
		}
	/*
		while(words.compareTo("")!=0&&words!=null){
			
			for(int i =0;i<NUMBER_OF_ELEMENTS;i++){
			if(words.startsWith(this.words[i])==true){
				System.out.println(words);
				if(longStr.compareTo("")!=0)
				longStr =longStr+ this.explains[i];
				else
					longStr =" "+this.explains[i];
				words=words.replaceFirst(this.words[i], "");
				System.out.println(words);
				words = words.trim();
			}
			if (i ==NUMBER_OF_ELEMENTS-1) {
				
				return;
			}
		}
		}
	}
	*/

}
}

