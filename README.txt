
README 
Author : Sanika Joshi


1.NormalisedEditDistanceAlgorithm - This class takes user input through by reading the files input1.txt and input2.txt. It checks for empty strings. The user input are the two strings s1 and s2. 
Data Structure. The calculation array is a two dimension array eg : Calculation array is stored in a form Eg s1 = 0,0 a,1 g,2 a,3 t,4 
                                                                                                            s2 = g,1 (new calculation values here)
													    then 
													    s2 = t,2 ( new calculation value here)
																											
2.LCS_EntireTable - This class  has a function which computes and returns the longest common subsequence of two sequences. This class takes user input through by reading the files input1.txt and input2.txt. The user input are the two strings s1 and s2. It is based on the algorithm which stores the entire table in memory. 
The Longest common subsequence program is checked for empty strings as well in which either strings can be empty the LCS would be empty.  A two dimensional array is used. 

3. LCS_linearMemory- This class has a function which computes and returns the longest common subsequence which is implemented using a recursive linear memory algorithm .
This class takes user input through by reading the files input1.txt and input2.txt.  This program also has been checked for empty strings as well in which either strings can be empty the LCS would be empty.
This program does not work for some equal strings as the lcs_recursive algorithm in the section of the X comparison should be if(X.size() == 1)  { Compare X[0] to each symbol in Y[0...Y.size()-1] till match. The same for Y. However, currently the algorithm compares each character and pushes it more than once onto the stack if it is present more than once in the string to be compared to.
Same data structure used as "1.NormalisedEditDistanceAlgorithm". 

 
    
