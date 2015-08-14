The project contains the file 
1.Graph.java
2.README.txt 
3.network.txt
Every input works in the class. I used the java compiler. There are two classes Vertex and Edge which represent the vertices and edges in the graph.Vertex class maintains a Linked list which is each vertex adjacency list. In order to store the vertices a vertexMap is used. To store the edges an edgeMap is used. The implementation of the changes required for the Graph are implemented in the Graph.java file. There is a vertexMap to store the vertices and an edgemap to store the edges. 
The complexity of the reachable implementation is O(V^2). The main reason is for each vertex V we find the BFS (complexity of O(V+ E)) and print the reachable vertices so complexity is O(V*(V+E))= O(V^2).


 
