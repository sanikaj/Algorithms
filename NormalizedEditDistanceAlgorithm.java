/**
 * 
 */
package com.algorithms.sanika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *  Project 1
 *  Algorithms
    NormalizedEditDistanceAlgorithm.java
	@author Sanika Joshi 
	@id 800843593
 *
 * 
 */
public class NormalizedEditDistanceAlgorithm {

	
	private void calculate_MinEditDistance(String returnedArray,String s1,String s2) {
		int minimumEditDistance = Integer.parseInt(returnedArray.split(",")[1]);
		System.out.println("Minimum Edit Distance " + minimumEditDistance);
		float normalisedEditDistance =  (float)(s1.length() + s2.length() - minimumEditDistance)/(s1.length() + s2.length());
		System.out.println("Normalized Edit Distance is dN = (|S1| + |S2| -d) /|S1|+|S2| =" + normalisedEditDistance);
	}
	
	private void initialize(String s1,String s2) {
		String[][] calculationArray = new String[2][s1.length()+1];
	    String[][] returnedArray = new String[2][s1.length() + 1];
		int i=0,j;
		int lengthOfFirstString = s1.length();
		int lengthOfSecondString = s2.length();
		calculationArray[0][0] = new String("0,0");
	    calculationArray[i+1][0] = s2.charAt(0) +"," + (i+1);

	    for(j=0;j<lengthOfFirstString;j++) {
			calculationArray[i][j+1] = s1.charAt(j) + "," + (j+1);
		}
		returnedArray = copy_Of_NormalizedEditDistance(calculationArray,s1.length(),0,s2);
		
		if(!s1.trim().isEmpty())
		  calculate_MinEditDistance(returnedArray[0][s1.length()],s1,s2);
		else 
		  calculate_MinEditDistance(returnedArray[1][0],s1,s2);		
	}
	
	private String[][] copy_Of_NormalizedEditDistance(String[][] calculationArray,int lengthOfFirstString,int i,String s2) {
		if(i < s2.length()) {
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
		
			for(int m = 1; m <(lengthOfFirstString+1);m ++) {
				calculationArray[0][m] = calculationArray[1][m];
				calculationArray[1][m]="";
			}
			copy_Of_NormalizedEditDistance(calculationArray,lengthOfFirstString,i+1,s2);
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
				   System.out.println("First String(S1) is empty");
			}
			   
			if(file2.length() > 0) {
			   Scanner in2 = new Scanner(new FileReader(filename2));
			   s2 = in2.nextLine();
			   System.out.println("The Second String (S2) is " + s2);
			} else {
			   System.out.println("Second string(S2) is empty");
			}
			
			if(!(s1.trim().isEmpty()) && !(s2.trim().isEmpty())) {
				(new NormalizedEditDistanceAlgorithm()).initialize(s1, s2);	
			} else if((s1.trim().isEmpty()) || (s2.trim().isEmpty())) {
					System.out.println("Normalised Edit Distance is 0");
		    }
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
