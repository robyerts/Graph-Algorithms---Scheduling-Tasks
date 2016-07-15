# Graph-Algorithms---Scheduling-Tasks
- written in java;
- almost finished(some final touches may be needed, but all functionalities are working properly)
- it reads the tasks from the file "graph.txt" in "src/com/company"/ ; "graph2.txt" contains another example ; if you need to see the second example , you will have to change the name of the file in the "readFromFile()" function
- 
File format: 
- first line contains the number of vertices and then the vertices
- each line after the first contains edges and vertices under the format "vertex vertex cost"

Source files are located in package  "src/com/company" ;
- IDE: Intellij IDEA
- lab4-pb2.png contains details about the representation I used for the tasks and their durations

Pseudocode source:
- http://www.cs.ubbcluj.ro/~rlupsa/edu/grafe/graf.pdf
- The "Critical path method" it's described from page 27 and there are 2 representations : by arc and by vertex ;
- The second one is the easiest one to understand, though, in my implementation the task is represented as a mix of vertex and edge ;
- A implementation by vertex would have been ideal and probably easiest .
