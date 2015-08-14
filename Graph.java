import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

/**
 *  Algorithms Project 2
    Graph.java
	@author Sanika Joshi
	id: 800843593
	sjoshi21@uncc.edu
 *
 * 
 */

//Represents a vertex in the graph.
class Vertex 
{
	public String     name;   // Vertex name
	public List<Vertex> adj;    // Adjacent list of (vertex,distance)
	public Vertex     prev;   // Previous vertex on shortest path
	public Double      dist;   // Distance of path
	public boolean isDown;
	public String color;
	public Integer f;
	public Vertex() {

	}

	public Vertex( String nm ,boolean isDown)
	{ 
		name = nm; 
		adj = new LinkedList<Vertex>( ); 
		reset( ); 
		this.isDown = isDown;
	}

	public void reset( )
	{ dist =  Graph.INFINITY; prev = null; color="WHITE"; }
}
//represents the Edge in the graph
class Edge {
	private Vertex v;
	private Vertex w; 
	Float weight;
	public boolean isDown;
	Edge(Vertex v, Vertex w, Float weight,boolean isDown) {
		this.v = v;
		this.w= w; 
		this.weight = weight;
		this.isDown = isDown;
	}

}


public class Graph {
	int time;

	public static final Double INFINITY = Double.MAX_VALUE;
	private Map<String,Vertex> vertexMap = new LinkedHashMap<String,Vertex>( );
	private Map<String,Edge> edgeMap = new HashMap<String,Edge>( );
	private Vertex[] array;
	private Vertex minimum;

	private Vertex getVertex( String vertexName )
	{
		Vertex v = vertexMap.get( vertexName );
		if( v == null )
		{
			v = new Vertex( vertexName,false);
			vertexMap.put( vertexName, v );
		}
		return v;
	}

	private Vertex relax(Vertex u,Vertex v,Float w) {
		if(v.dist > (u.dist + edgeMap.get(u.name +"," + v.name).weight)) {
			v.dist = u.dist + edgeMap.get(u.name +"," + v.name).weight;
			v.prev = u;
		}
		return v;
	}

	private void INITIALISE_SINGLE_SOURCE(Vertex source) {
		source.dist =0.0d;
	}

	private void  min_heapify(Vertex[] array,int i) {
		int leftChildIndex = (2*i)+1;
		int rightChildIndex = (2*i) +2;
		int smallestIndex;


		if((leftChildIndex <= (array.length-1)) && (array[leftChildIndex].dist.compareTo(array[i].dist)< 0)) {
			smallestIndex = leftChildIndex;

		} else {
			smallestIndex = i;
		}
		if((rightChildIndex <= (array.length-1)) && (array[rightChildIndex].dist.compareTo(array[i].dist)< 0)) {
			smallestIndex = rightChildIndex;
		} 
		if(smallestIndex != i) {
			Vertex temp = array[i];
			array[i]= array[smallestIndex];
			array[smallestIndex] = temp;
			min_heapify(array,smallestIndex);
		}

	}

	private void build_min_heap(Vertex[] array) {
		int length = array.length;
		for(int i=((length/2)-1);i>=0;i--) {
			min_heapify(array,i);
		}
	}

	private Vertex[] extract_min(Vertex[] array) {
		if(array.length < 1) {
			System.out.println("Heap underflow");
			return null;
		}
		minimum = array[0];
		array[0] = array[(array.length-1)];
		array[(array.length-1)] = null;

		int newLength = array.length -1;
		Vertex[] array2 = new Vertex[newLength];
		for(int i=0;i< array2.length;i++) {
			array2[i]=array[i];	
		}
		return array2;
	}

	private void dijkstras(Vertex source, Vertex destination) {
		INITIALISE_SINGLE_SOURCE(source);
		Vertex[] arrayNew = new Vertex[vertexMap.size()];

		//Traverse the vertexMap to put all vertices on the Queue
		int i=0;
		Stack<Vertex> stack = new Stack<Vertex>();
		int numberOfVertices = arrayNew.length;
		int count=0;
		for (Map.Entry<String, Vertex> entry : vertexMap.entrySet()) {
			Vertex u =entry.getValue();
			if(!u.isDown) {
				arrayNew[i] = u;
				i++;
			} else {
				count ++;
			}
		}

		Vertex[] arrayOfVertices = new Vertex[arrayNew.length- count];
		for(int m=0; m< (arrayNew.length- count) ;m++) {
			if(arrayNew[m]!=null) {
				arrayOfVertices[m] = arrayNew[m];
			}
		}

		try {
			while(stack.size() < numberOfVertices && (arrayOfVertices!= null)) {
				build_min_heap(arrayOfVertices);
				arrayOfVertices = extract_min(arrayOfVertices);
				Vertex u = new Vertex();
				if(arrayOfVertices != null) {
					u = minimum;
				}
				if(arrayOfVertices != null && u!= null && !u.isDown) {
					stack.push(u);
					for(int j=0;j< u.adj.size();j++) {
						Vertex v= u.adj.get(j);
						if(!v.isDown) {
							String edgeName = u.name+"," + v.name;
							if(!((Edge)edgeMap.get(edgeName)).isDown) {
								Vertex v2= relax(u,v,((Edge)edgeMap.get(edgeName)).weight);
								if(!stack.contains(v2)) {
									for(int k=0;k< arrayOfVertices.length ;k++) {
										if((arrayOfVertices[k].name).equals(v2.name)) {
											arrayOfVertices[k]= v2;
											break;
										}
									}
								}
							}
						}
					}
				}
			} 
		} catch (Exception e) {
			System.out.println("Error " + e.getStackTrace());
		}

		Vertex previous = vertexMap.get(destination.name);
		Float pathLength =0.0f;
		DecimalFormat df = new DecimalFormat("##.##");
		Stack<String> stackForPath = new Stack<String>();
		boolean pathExists = true;
		while(previous != source) {
			if(previous.isDown) {
				System.out.println("Shortest path does not exist");
				break;
			} else {
				stackForPath.push(previous.name);
				String initialVertexName = previous.name;
				if(previous.prev!= null && !previous.prev.isDown) {
					pathLength = pathLength + edgeMap.get(previous.prev.name + "," + initialVertexName).weight;
					previous = previous.prev;
				} else {
					System.out.println("Shortest path doesnt exist");
					pathExists = false;
					break;
				}
			}
		}
		if(pathExists)  {
			System.out.print(source.name + " " );
			while(!stackForPath.isEmpty()) {
				System.out.print(stackForPath.pop() + " ");
			}
			System.out.print(" " + df.format(pathLength));
		}
	}



	private void getEdge(Vertex v,Vertex w,Float weight) {
		String edgeName = v.name.trim() + "," + w.name.trim();
		Edge e = edgeMap.get(edgeName);
		if(e == null) {
			e = new Edge(v,w,weight,false);
			edgeMap.put(edgeName, e);
		} else {
			edgeMap.remove(edgeName);
			e = new Edge(v,w,weight,false);
			edgeMap.put(edgeName, e);
		}

		String reverseEdgeName = w.name.trim() + "," + v.name.trim();
		Edge e2 =edgeMap.get(reverseEdgeName);
		if(e2 == null) {
			e2  = new Edge (w,v,weight,false);
			edgeMap.put(reverseEdgeName, e2);
		}

	}
	
	private void getEdgeSingleDirection(Vertex v,Vertex w,Float weight) {
		String edgeName = v.name.trim() + "," + w.name.trim();
		Edge e = edgeMap.get(edgeName);
		if(e == null) {
			e = new Edge(v,w,weight,false);
			edgeMap.put(edgeName, e);
		} else {
			edgeMap.remove(edgeName);
			e = new Edge(v,w,weight,false);
			edgeMap.put(edgeName, e);
		}
	}

	//ADD Edge
	public void addEdge( String sourceName, String destName,Float weight )
	{
		Vertex v = getVertex( sourceName );
		Vertex w = getVertex( destName );
		if(!v.adj.contains(w)) {
			v.adj.add(w);
			w.adj.add(v);
		}
		getEdge(v,w,weight);
	}

	public void addEdgeSingleDirection( String sourceName, String destName,Float weight )
	{
		Vertex v = getVertex( sourceName );
		Vertex w = getVertex( destName );
		if(!v.adj.contains(w)) {
			v.adj.add(w);
		}
		//getEdgeSingleDirection(v,w,weight);
		getEdgeSingleDirection(v,w,weight);
	}
	
	//DELETE Edge
	public void deleteEdge(String sourceName, String destName) {
		Vertex v = getVertex( sourceName );
		Vertex w = getVertex( destName );

		String edgeName = v.name.trim() + "," + w.name.trim();
		Edge e = edgeMap.get(edgeName);
		if(e!= null) {
			edgeMap.remove(edgeName);
			// this vertex is no longer adjacent to the vertex v
			v.adj.remove(w);

		}
	}

	//EDGE Down
	public void markEdgeDown(String sourceName,String destName) {
		Vertex v = getVertex( sourceName );
		Vertex w = getVertex( destName );

		String edgeName = v.name.trim() + "," + w.name.trim();
		Edge e = edgeMap.get(edgeName);
		if(e!= null) {
			e.isDown = true;
		}

	}

	//EDGE Up
	public void markEdgeUp(String sourceName,String destName) {
		Vertex v = getVertex( sourceName );
		Vertex w = getVertex( destName );

		String edgeName = v.name.trim() + "," + w.name.trim();
		Edge e = edgeMap.get(edgeName);
		if(e!= null) {
			e.isDown = false;
		}
	}

	//Vertex DOWN
	private void markVertexDown(String vertexName) {
		Vertex v = getVertex( vertexName );
		if(v!= null) {
			v.isDown = true;
		}
	}

	//Vertex UP
	private void markVertexUp(String vertexName) {
		Vertex v = getVertex(vertexName);
		if(v!= null) {
			v.isDown = false;
		}
	}


	private void initialiseVertices() {
		for (Map.Entry<String, Vertex> entry : vertexMap.entrySet()) {
			Vertex u = (Vertex)entry.getValue();
			u.color = "WHITE";
			u.prev = null;
			u.dist = Graph.INFINITY;
		}
	}

	//reachable vertices
	private void reachable() {
		SortedMap<String, Vertex> myMap = new TreeMap<String, Vertex>(new alphabetComparator());
		myMap.putAll(vertexMap);

		for (Map.Entry<String, Vertex> entry : myMap.entrySet()) {
			System.out.println(entry.getValue().name);
			initialiseVertices();
			if(!entry.getValue().isDown)
				bfs(entry.getValue());
		}
	}

    //Sort the names
	class alphabetComparator implements Comparator<String>{

		@Override
		public int compare(String v1Name, String v2Name) {
			return v1Name.compareTo(v2Name);
		}

	}

	private void printPath() {
		SortedMap<String, Vertex> myMap = new TreeMap<String, Vertex>(new alphabetComparator());
		myMap.putAll(vertexMap);
		for (Map.Entry<String, Vertex> entry : myMap.entrySet()) {
			String key = entry.getKey();
			Vertex value = (Vertex)entry.getValue();
			String vertexDown = new String();

			if(value.isDown) {
				System.out.println(key + " DOWN");
			} else {
				System.out.println(key); 
				String str= new String();
				Vertex[] arr = new Vertex[value.adj.size()];
				for(int i=0;i< value.adj.size();i++) {
					arr[i]= value.adj.get(i);
				}
				Vertex[] sortedArray = sortGraph(arr);
				for(int i=0;i< sortedArray.length ;i++) {
					Vertex v= sortedArray[i];
					Edge e= edgeMap.get(value.name + "," + (v.name));
					if(e.isDown) {
						str = " DOWN";
					} else { 
						str = "";
					}
					System.out.print(" " + (v).name  + " " + e.weight + str);
					System.out.println("");
				}
			}
		}
	}

	private void initialiseGraph(String filename) {
		try {
			FileInputStream fileStream = new FileInputStream(filename);

			BufferedReader brRead = new BufferedReader(new InputStreamReader(fileStream));
			String strLine;
			while ((strLine = brRead.readLine()) != null)   {
				String[] tokens = strLine.split(" ");
				String source = tokens[0];
				String destination = tokens[1];
				Float weight = Float.parseFloat(tokens[2]);

				addEdge(source, destination, weight);
			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void takeUserInput(String[] args) {
		boolean finished = false;
		String filename = new String();
		boolean invalidInput = false;
		if(args.length != 1) {
			System.err.println("Error Kindly enter the File Name while rerunning the code: Eg java Graph network.txt");
			invalidInput = true;
		} else {
			filename = args[0].trim();
			initialiseGraph(filename);
		}
		if(!invalidInput) {
			try {
				while(!finished) {
					System.out.println("\nKindly enter your input or quit to exit");
					System.out.println(" ");
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String consoleInput = br.readLine();
					if(consoleInput.equals("quit")) {
						finished = true;
					} else  {
						String[] arr = consoleInput.split(" ");
						if(arr.length > 0) {
							String userInput = arr[0];
							switch(userInput) { 
							case "addedge":
								if(arr.length == 4) {
									String sourceName = arr[1];
									String destinationName = arr[2];
									String transmitTime = arr[3];
									if(sourceName != null && destinationName != null && transmitTime != null) {
										Float transmitTimeF = Float.valueOf(transmitTime);
										addEdgeSingleDirection(sourceName,destinationName,transmitTimeF);  

									} else {
										if(sourceName == null) {
											System.err.println("Source Name is null");
										} else if(destinationName == null) {
											System.err.println("Destination Name is null");

										} else if(transmitTime == null) {
											System.err.println("Transmit Time is null");
										}
									}
								} else {
									System.err.println("Invalid Input. Valid Input example addedge source destination transmitTime");
								}
								break;
							case "deleteedge" :
								if(arr.length == 3) {
									String sourceNameD = arr[1];
									String destinationNameD = arr[2];
									if(sourceNameD == null) {
										System.err.println("Source Name is null");
									} else if(destinationNameD == null) {
										System.err.println("Destination name is null");
									} else if(vertexMap.get(sourceNameD) == null && vertexMap.get(destinationNameD) != null) {
										System.err.println("Source Vertex does not exist");
									} else if(vertexMap.get(sourceNameD) != null && vertexMap.get(destinationNameD) == null) {
										System.err.println("Destination Vertex does not exist");	
									}  else if(vertexMap.get(sourceNameD) == null && vertexMap.get(destinationNameD) == null) {
										System.err.println("Both Source and Destination Vertex do not exist");
									} else if(sourceNameD!= null && destinationNameD!= null) {
										deleteEdge(sourceNameD,destinationNameD);
									}	 
								} else {
									System.err.println("Invalid Input. Valid Input example addedge source destination transmitTime");
								}
								break;
							case "edgedown":
								if(arr.length == 3) {
									String sourceNameM = arr[1];
									String destinationNameM = arr[2];
									if(sourceNameM == null) {
										System.err.println("Source Name is null");
									} else if(destinationNameM == null) {
										System.err.println("Destination name is null");
									} else if(vertexMap.get(sourceNameM) == null && vertexMap.get(destinationNameM)!= null) {
										System.err.println("Source Vertex does not exist");
									} else if(vertexMap.get(sourceNameM) != null && vertexMap.get(destinationNameM) == null) {
										System.err.println("Destination Vertex does not exist");	
									} else if(vertexMap.get(sourceNameM) == null && vertexMap.get(destinationNameM) == null) {
										System.err.println("Both source and destination vertex do not exist");
									} else if(sourceNameM!= null && destinationNameM!= null) {
										markEdgeDown(sourceNameM,destinationNameM);
									}    
								} else {
									System.err.println("Invalid Input. Valid Input example addedge source destination transmitTime");
								}
								break;
							case "edgeup" :
								if(arr.length == 3) {
									String sourceNameMU = arr[1];
									String destinationNameMU = arr[2];
									if(sourceNameMU == null) {
										System.err.println("Source Name is null");
									} else if(destinationNameMU == null) {
										System.err.println("Destination name is null");
									} else if(vertexMap.get(sourceNameMU) == null && vertexMap.get(destinationNameMU)!= null) {
										System.err.println("Source Vertex does not exist");
									} else if(vertexMap.get(sourceNameMU) != null && vertexMap.get(destinationNameMU) == null) {
										System.err.println("Destination Vertex does not exist");	
									} else if(vertexMap.get(sourceNameMU) == null && vertexMap.get(destinationNameMU) == null) {
										System.err.println("Both source and destination vertex do not exist");
									} else if(sourceNameMU!= null && destinationNameMU!= null) {
										markEdgeUp(sourceNameMU,destinationNameMU);
									}    
								} else {
									System.err.println("Invalid Input. Valid Input example addedge source destination transmitTime");
								}
								break;
							case "vertexdown": 
								if(arr.length == 2) {
									String vertexDown = arr[1];
									if(vertexMap.get(vertexDown) == null) {
										System.err.println("Vertex to be marked down does not exist");
									} else {
										markVertexDown(arr[1]);	
									}
								} else {
									System.err.println("Invalid Input. Valid Input example vertexdown vertexname(eg: Belk)");
								}
								break;
							case "vertexup":
								if(arr.length == 2) {
									String vertexUp = arr[1];
									if(vertexMap.get(vertexUp) == null) {
										System.err.println("Vertex to be marked Up does not exist");
									} else {
										markVertexUp(vertexUp);
									}
								} else {
									System.err.println("Invalid Input. Valid Input example vertexup vertexname(eg: Belk)");
								}
								break;
							case "path":
								if(arr.length == 3) {
									String sourceVertexName = arr[1]; 
									String destinationVertexName = arr[2];
									if(vertexMap.get(sourceVertexName) == null) {
										System.err.println("Source Vertex does not exist");
									} else if(vertexMap.get(destinationVertexName) == null) {
										System.err.println("Destinaton Vertex does not exist");
									} else if (sourceVertexName != null && destinationVertexName != null && !getVertex(sourceVertexName).isDown && !getVertex(destinationVertexName).isDown) {
										Vertex v = getVertex(sourceVertexName );
										Vertex w = getVertex(destinationVertexName);
										initialiseVertices();
										dijkstras(v, w);
									} else if(getVertex(sourceVertexName).isDown) {
										System.out.println("Source node is down");
									} else if(getVertex(destinationVertexName).isDown) {
										System.out.println("Destination Vertex is down");
									}
								} else {
									System.err.println("Invalid Input. Valid Input example path from_vert to_vertex");
								}
								break;
							case "reachable":
								reachable();
								break;
							case "print":
								printPath();
								break;

							default:
								System.err.println("Invalid option selected");
								continue; //this causes control to go back to loop condition

							}

						} else {
							System.err.println("Kindly enter valid input data");
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//Implementation of BFS						
	private void bfs(Vertex source) {
		Vertex v,u;
		Vertex [] arr = new Vertex[vertexMap.size()];
		int p=0;

		for(int j=0;j< source.adj.size();j++) {

			v = source.adj.get(j);
			if(!v.name.equals(source.name)) {
				v.color = "WHITE";
				v.dist = Graph.INFINITY;
				v.prev= null;
			}

		}
		source.color = "GRAY";
		source.dist =0d;
		source.prev = null;
		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(source);
		while(!queue.isEmpty()) {
			u= (Vertex) queue.remove();
			//System.out.println(" " + u.name);
			for(int j=0;j< u.adj.size();j++) {
				v = u.adj.get(j);
				if(!v.name.equals(u.name) && !v.isDown && (edgeMap.get(u.name +"," + v.name)!= null) && (!edgeMap.get(u.name +"," + v.name).isDown)) {
					if(v.color.equals("WHITE")) {
						v.color ="GRAY";
						v.dist = u.dist + 1;
						v.prev = u;
						queue.add(v);
					}
				}
			}

			u.color ="BLACK";
			if(!u.name.equals(source.name)) {
				//System.out.println(" " + u.name);
				arr[p] = u;
				p++;
			}


		}
		Vertex[] sortedArr = sortGraph(arr);
		for(int i=0;i< sortedArr.length ; i++) {
			if(sortedArr[i]!= null)
				System.out.println(" " + ((Vertex)sortedArr[i]).name);
		}
	}



	private Vertex[] sortGraph(Vertex[] arr) {
		Vertex temp;
		for (int i = 0;i < arr.length;i++)
		{
			if(arr[i]!= null) {
				temp = arr[i];
				for (int j = 0;j < arr.length;j++)
				{
					if (i == j) continue;
					if(arr[j] != null) {
						int x = temp.name.compareTo(arr[j].name);
						if (x < 0)
						{
							temp = arr[j];
							arr[j] = arr[i];
							arr[i] = temp;
						}
					}
				}
			}
		}
		return arr;
	}	


	public static void main(String[] args) {
		Graph g = new Graph();   
		g.takeUserInput(args);
	}

}
