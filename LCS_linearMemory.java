package com.algorithms.sanika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 *  Project 1
 *  Algorithms : LCS Linear Memory Program
    LCS_linearMemory.java
	@author Sanika Joshi 
	@id 800843593
 *
 * 
 */
public class LCS_linearMemory {
    private static Stack lcsStack;
	private int calculateMinEditDist (String[][] returnedForwardArray,String[][] returnedReverseArray) {
		//TODO return Minimum Edit distance
		// t,4 a,3 g,4, t,3 a,2
		
		int i=1,minimumIndex=0,minimumSum=0;
		minimumSum = Integer.parseInt(returnedForwardArray[0][0].split(",")[1]) + Integer.parseInt(returnedReverseArray[0][0].split(",")[1]);
		while(i<returnedForwardArray[0].length) {
			int sum1 = Integer.parseInt(returnedForwardArray[0][i].split(",")[1]) + Integer.parseInt(returnedReverseArray[0][i].split(",")[1]); 
			if(sum1 < minimumSum) {
				minimumSum = sum1;
				minimumIndex =i;
			}
			i=i+1;
		}
		
		return minimumIndex;
	}
	
	private String initialize(String s1,String s2) {
		String[][] calculationArray = new String[2][s1.length()+1];
		String[][] reverse_calculationArray = new String[2][s1.length()+1];
		
	    String[][] returnedForwardArray = new String[2][s1.length() + 1];
	    String[][] returnedReverseArray = new String[2][s1.length() + 1];
		
	    int i=0,j;
		int lengthOfFirstString = s1.length();
		int lengthOfSecondString = s2.length();
		calculationArray[0][0] = new String("0,0");
		reverse_calculationArray[0][0] = lengthOfFirstString + "," + lengthOfFirstString;
		
		calculationArray[i+1][0] = s2.charAt(0) +"," + (i+1);
		
		
		for(j=0;j<lengthOfFirstString;j++) {
			calculationArray[i][j+1] = s1.charAt(j) + "," + (j+1);
			
		}
		
		
		for(int h=lengthOfFirstString;h>0;h--) {
			reverse_calculationArray[0][h] = s1.charAt(h-1) + "," + ((lengthOfFirstString -h));
			
		}
        
		int minimumIndex;
		 returnedForwardArray = forward_array(calculationArray,s1.length(),0,s2);
		 returnedReverseArray = reverse_array(reverse_calculationArray,s1.length(),((s2.length())-1),0,s1,s2);
		 minimumIndex = calculateMinEditDist(returnedForwardArray,returnedReverseArray);
         
		return  (s2.length()/2) + "," + minimumIndex ; 
		
	}
	
	    private String[][] reverse_array(String[][] calculationArray,int lengthOfFirstString,int i,int initialCount,String s1,String s2) {
	    	if(i >= (s2.length())/2 && s1.length() >0) {
	    		calculationArray[1][s1.length()] = s2.charAt(i) +"," + (initialCount+1);
	    		//System.out.println("Done"+ calculationArray[1][s1.length()]);
	    		for(int k=lengthOfFirstString;k >0;k--) {
	    			//case 1 if symbols are equal 
	    			
	    			if(calculationArray[1][s1.length()].split(",")[0].equals(calculationArray[0][k].split(",")[0])) {
	    				calculationArray[1][k-1]= calculationArray[0][k-1].split(",")[0] + "," + calculationArray[0][k].split(",")[1];
	    			} else if (!(calculationArray[1][s1.length()].split(",")[0].equals(calculationArray[0][k].split(",")[0]))) {
	    				// case 2 if they are not equal take the minimum of the two and add 1
	    		        if(Integer.parseInt(calculationArray[0][k-1].split(",")[1]) <= Integer.parseInt(calculationArray[1][k].split(",")[1])) {
	    		        	Integer sum = Integer.parseInt(calculationArray[0][k-1].split(",")[1]) ;
	    		        	sum = sum +1;
	    		        	calculationArray[1][k-1] = calculationArray[0][k-1].split(",")[0] + "," + sum + "";  
	    		        } else {
	    		        	Integer sum2 = (Integer.parseInt(calculationArray[1][k].split(",")[1]));
	    		        	sum2= sum2+1;
	    		        	calculationArray[1][k-1] = calculationArray[0][k-1].split(",")[0] + "," + sum2 + "";
	    		        }
	    		    }
	    		}
	    		for(int m = 0; m <(lengthOfFirstString+1);m ++) {
	    				calculationArray[0][m] = calculationArray[0][m].split(",")[0] + "," + calculationArray[1][m].split(",")[1];
	    				calculationArray[1][m]="";
	    	    }	    			
	    			reverse_array(calculationArray,lengthOfFirstString,i-1,initialCount+1,s1,s2);
	    	}	
	    	
	    	return calculationArray;
	    }
	    
	    
		private String[][] forward_array(String[][] calculationArray,int lengthOfFirstString,int i,String s2) {
		//forward array till the middle of the array
			
	    if(i < (s2.length()/2) && lengthOfFirstString >0 ) {
		calculationArray[1][0] = s2.charAt(i) +"," + (i+1);
		
		for(int k=1;k <(lengthOfFirstString+1);k++) {
			//case 1 if symbols are equal 
			if(calculationArray[1][0].split(",")[0].equals(calculationArray[0][k].split(",")[0])) {
				calculationArray[1][k]= calculationArray[0][k].split(",")[0] + "," + calculationArray[0][k-1].split(",")[1];
			} else if (!(calculationArray[1][0].split(",")[0].equals(calculationArray[0][k].split(",")[0]))) {
				// case 2 if they are not equal take the minimum of the two and add 1
		        if(Integer.parseInt(calculationArray[1][k-1].split(",")[1]) <= Integer.parseInt(calculationArray[0][k].split(",")[1])) {
		        	Integer sum = Integer.parseInt(calculationArray[1][k-1].split(",")[1]) ;
		        	sum = sum +1;
		        	calculationArray[1][k] = calculationArray[0][k].split(",")[0] + "," + sum + "";  
		        } else {
		        	calculationArray[1][k] = calculationArray[0][k].split(",")[0] + "," + (Integer.parseInt(calculationArray[0][k].split(",")[1]) + 1);
		        }
		    }
		}
		    for(int m = 0; m <(lengthOfFirstString+1);m ++) {
				calculationArray[0][m] = calculationArray[1][m];
				calculationArray[1][m]="";
			}
			
			forward_array(calculationArray,lengthOfFirstString,i+1,s2);
		}
		return calculationArray;
	}


	public static void main(String[] args) {
		try {
			
		System.out.println("Kindly enter filname1");
		Scanner fin = new Scanner(System.in);
		String filename1 = fin.nextLine();
		
		System.out.println("Kindly enter filname2");
		Scanner fin2 = new Scanner(System.in);
		String filename2 = fin2.nextLine();
		
		
		File file1 = new File(filename1);
		File file2 = new File(filename2);
		String s1 = new String();
		String s2 = new String();
		
		if(file1.length() > 0) {
			   Scanner in1 = new Scanner(new FileReader(filename1));
			   s1= in1.nextLine();
			   System.out.println("The First String (S1) is " + s1);
		} else {
			   System.out.println("First String is empty");
		}
		   
		if(file2.length() > 0) {
			   Scanner in2 = new Scanner(new FileReader(filename2));
			   s2 = in2.nextLine();
			   System.out.println("The Second String (S2) is " + s2);
		} else {
			   System.out.println("Second string is empty");
		}
		
	     
         lcsStack =new Stack();
         
         if(!(s1.trim().isEmpty()) || !(s2.trim().isEmpty())) {
        
        	 (new LCS_linearMemory()).lcs_recursive(s2, s1, lcsStack);
         } else {
        	 System.out.println("Either or both of the strings are empty");
         }
         System.out.println("");
          
        System.out.print("LCS(S1,S2)  is ");
        if(lcsStack.isEmpty()) {
        	System.out.print(" empty");
        }
        System.out.println("\n");
        ArrayList<Character> result = new ArrayList<Character>();
        while(!lcsStack.isEmpty()) {
        	
        	result.add((Character) lcsStack.pop());
        }
        
        	for(int i=0;i< result.size() ;i++) {
        		System.out.print(result.get(result.size()-1-i));
        	}
		} catch(FileNotFoundException fileexp) {
			fileexp.printStackTrace();
		}
	}
	
	private void lcs_recursive(String X, String Y,Stack lcsStack) {
		int x=0,y=0;
		if(X.length() == 1) {
			for(int l=0;l< Y.length();l++) {
				  if(X.charAt(0) == (Y.charAt(l))) {
					  lcsStack.push(X.charAt(0));
			    }
						
			}
		}
		
		else if(Y.length() == 1) {
			for(int l=0;l< X.length();l++) {
			  if(Y.charAt(0) == (X.charAt(l))) {
				 lcsStack.push(Y.charAt(0));
			  }
			}
		}
		else {
			//compute X and Y from minimum edit distance
			String values = initialize(Y,X);
			String[] xAndY = values.split(",");
			 x = Integer.parseInt(xAndY[0]);
			 y= Integer.parseInt(xAndY[1]);
			String X_Front = new String();
			String Y_Front = new String();
			String X_Back = new String();
			String Y_Back = new String();
			
			X_Front = X.substring(0, x);
			Y_Front = Y.substring(0,y);
			X_Back = X.substring(x,X.length());
			Y_Back = Y.substring(y,Y.length());
			
			lcs_recursive(X_Front,Y_Front,lcsStack);
			lcs_recursive(X_Back,Y_Back,lcsStack);
		}
	}
}
