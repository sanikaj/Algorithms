/**
 * 
 */
package com.algorithms.sanika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 *  Project 1
 *  Algorithms: LCS Entire Table Program
    LCS_EntireTable.java
	@author Sanika Joshi 
	@id 800843593
 *
 * 
 */
public class LCS_EntireTable {
    //Calculation array is stored in a form Eg s1 = 0,0 a,1 g,2 a,3 t,4 
	//                                         s2 =     g,1 t,2 a,3 t,4  
	
	private void back_trace(String[][] calculationArray,String s1,String s2) {
		int i=s2.length();
		int j=s1.length();
		Stack<String> stack = new Stack();
			while(j>0 && i>0) {
			  if(calculationArray[i][j].split(",")[0].equals(calculationArray[i][0].split(",")[0])) {
				  //case 1
				  stack.push(calculationArray[i][j].split(",")[0]);
				  i=i-1;
				  j=j-1;
			  } else if(!(calculationArray[i][j].split(",")[0].equals(calculationArray[i][0]))) {
				  // case 2
				  if(Integer.parseInt(calculationArray[i][j-1].split(",")[1]) <= Integer.parseInt(calculationArray[i-1][j].split(",")[1])) {
					  j=j-1;
				  } else {
					  i=i-1;
				  }
			 }
			
		}
		
		System.out.println("");
		System.out.print("LCS(S1,S2) = ");
		if(stack.isEmpty()) {
			System.out.println(" empty");
		}
		
		ArrayList<String> result = new ArrayList<String>();
        
		while(!stack.isEmpty()) {
			result.add((String) stack.pop());
		}
		
		for(int k=0;k<result.size() ;k++) {
        	System.out.print(result.get(k));
        }
	}
	
	private void calculate_LCS(String s1,String s2) {
		Integer lengthOfFirstString = s1.length();
		Integer lengthOfSecondString = s2.length();
		String[][] calculationArray = new String[lengthOfSecondString+1][lengthOfFirstString +1];
		calculationArray[0][0] = 0+","+0;
		
		for(int m=1;m<(lengthOfFirstString+1);m++) {
			calculationArray[0][m] = s1.charAt(m-1) + "," + (m);
			
		}
		for(int i=1;i <= lengthOfSecondString;i++) {
			calculationArray[i][0] = s2.charAt(i-1) + "," + i;
			for(int j=1;j<=lengthOfFirstString;j++) {
			   if(calculationArray[i][0].split(",")[0].equals(calculationArray[i-1][j].split(",")[0])) {
				   //case 1
				   calculationArray[i][j] = calculationArray[i-1][j].split(",")[0] + "," + calculationArray[i-1][j-1].split(",")[1];
			   } else if(!(calculationArray[i][0].split(",")[0].equals(calculationArray[i-1][j].split(",")[0]))) {
				   //case 2
				   if(Integer.parseInt(calculationArray[i][j-1].split(",")[1]) <= Integer.parseInt(calculationArray[i-1][j].split(",")[1])) {
					   Integer sum = Integer.parseInt(calculationArray[i][j-1].split(",")[1]) + 1;
					   calculationArray[i][j]= calculationArray[i-1][j].split(",")[0] + ","  + sum; 
				   } else {
					   Integer sum = Integer.parseInt(calculationArray[i-1][j].split(",")[1]) + 1;
					   calculationArray[i][j]= calculationArray[i-1][j].split(",")[0] + ","  + sum;
				   }
			   }
			}
			
			
		}
		
		
		back_trace(calculationArray,s1,s2);
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
		   if(!(file1.length() > 0) && !(file2.length() >0)){
			   System.out.println("LCS is empty");
		   } else {
			   (new LCS_EntireTable()).calculate_LCS(s1,s2);
		   }
		} catch(FileNotFoundException fexp) {
			fexp.printStackTrace();
		}
   }

}
