- written in java;
- it reads the tasks from the file "graph.txt" in "src/com/company"/ ; "graph2.txt" contains another example ; if you need to see the second example , you will have to change the name of the file in the "readFromFile()" function

Functionalities:
  MAIN MENU
  
  
![alt text](https://github.com/robyerts/Graph-Algorithms---Scheduling-Tasks/blob/master/functionalities.png)

Input File format: 
- first line contains the number of vertices and then the vertices
- each line after the first contains edges and vertices under the format "vertex vertex cost"

NOTE: in the readFromFile() function in graph class, path must be CHANGED ; it is currently set to my current FULL PATH  

Source files are located in package  "src/com/company" ;
- IDE: Intellij IDEA
- lab4-pb2.png contains details about the representation I used for the tasks and their durations

![alt text](https://github.com/robyerts/Graph-Algorithms---Scheduling-Tasks/blob/master/lab4-pb2.png)

Pseudocode source:
- http://www.cs.ubbcluj.ro/~rlupsa/edu/grafe/graf.pdf
- The "Critical path method" is described from page 27 and there are 2 representations : by arc and by vertex ;
- The second one is the easiest one to understand, though, in my implementation, the task is represented as a mix of vertex and edge ;
- A implementation by vertex would have been ideal and probably easier .
