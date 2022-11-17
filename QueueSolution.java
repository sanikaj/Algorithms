package hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

class Queue {
	int head; 
	int tail;
	ArrayList<Integer> list;
	
	Queue() {
		
		list = new ArrayList<Integer>();
	}
 	public void Enqueue(int element) {
 		ListIterator<Integer> listItr = list.listIterator();
 		while(listItr.hasNext()) {
 			listItr.next(); 
 		}
 		listItr.add(element);
	}
	
	
	public int DeQueue() {
		
		if(list.isEmpty()) {
			return 0;
		} else {
		 	int val= list.get(0);
		 	list.remove(0);
		 	return val;
		}
		
	}
	
	
	public int Peek() {
		
		if(list.isEmpty()) {
			return 0;
		} else {
			return list.get(0);
		}
	}
	
	public void choice(int choice, int element) {
		switch(choice) {
		case 1: 
			Enqueue(element);
			break; 
		case 2:
			int val = DeQueue();
			System.out.println("Value of element" + val);
			break;
			
		case 3:
			int peek = Peek();
			System.out.println(peek);
			break;
		}
	}
}
public class QueueSolution {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      Queue queue  = new Queue();
      try {
    	  // Read the input of the number of queries that are part of the input
    	    
    	  int t = Integer.parseInt(bufferedReader.readLine().trim());
    	  IntStream.range(0, t).forEach(tItr -> {
              try {
                  String str = bufferedReader.readLine();
                  
                  // Java 8 Streams syntax
                  int[] array =
                	        Pattern.compile(" ", Pattern.LITERAL)
                	               .splitAsStream(str)
                	               .mapToInt(Integer::valueOf)
                                   .toArray();
                  if(array.length == 2) {
                	  queue.choice(array[0], array[1]);
                  } else {
                	  queue.choice(array[0],0);
                  }
                  
              } catch(IOException io) {
            	  
              }
    	  });
		
	} catch (NumberFormatException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

      
	}

}
