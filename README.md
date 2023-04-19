# INFO6205_FinalProject
PSA Final Project - Travelling Salesman Problem

Introduction:


Finding the shortest path between a number of points and places that must be visited is the goal of the algorithmic problem known as the "travelling salesman problem" (TSP). The cities a salesperson might visit are the points in the problem statement expressed in terms of latitudes and longitudes.


Aim: 


The goal of the project is to determine the shortest path using which each listed city is visited exactly once and finally return back to the starting location.


The problem is named after the analogy of a salesman who must visit several towns and wants to travel the fewest amount of distance possible while doing so. The issue has significant applications in network routing, logistics, and other fields.


The TSP is an NP-hard problem, which means that it is computationally impractical to find an accurate solution for large instances of the problem in linear time. However, there are a variety of approximations, optimization algorithms and heuristics that can be applied to quickly find effective answers.


Approach:


The travelling salesman problem (TSP) can be solved in a number of ways, including accurate and heuristic methods. 


Accurate techniques include dynamic programming, cutting-plane algorithms and branch-and-bound algorithms. These techniques are computationally expensive and might not be practical for widespread use cases of the issue.


Even though the solution is not always optimal, heuristic approaches try to find a good one in a fair period of time, maintaining a balance between space, time and efficiency. These techniques consist of Nearest neighbour, Insertion, Simulated annealing, Ant Colony Optimization and Genetic Algorithms.

Observations and Graphical Analysis:


After applying the Christofide’s Algorithm and the optimization techniques on a set of given points, we can visualise the following observations.


The minimum spanning tree acts as a lower bound for the travelling salesman problem. This means that any number of optimizations applied on top of the minimum spanning tree will not reduce the cost further.
Christofide’s algorithm is calculated from the minimum spanning tree and the other optimizations are applied on top of it.
The Two Opt optimization is more time effective than the Three Opt method as the time complexity is lower in Two Opt.
The trade off on the time does not tend to improve the cost of the tour as the Three Opt tour cost is still higher than the Two Opt method.
The Simulated Annealing method tends to provide the most optimised solution for the given set of points as we have passed the Two Opt tour on which random swaps happen. Even though the cost decrease is very minimal the simulated annealing algorithm achieves the solution in a time effective manner.
The Ant Colony Optimization method did not provide an exactly optimised solution for the given set of points and the execution time exceeds the other optimization methods used.


From running the above algorithms and optimisations, we can conclude that for the given set of points, the Simulated Annealing Optimization algorithm provides the lowest tour cost. The Two Opt Swap method also provides an optimised solution close to the Simulated Annealing method. The Simulated Annealing method can further be optimised by increasing the temperature value or decreasing the cooldown value.


The tour cost in metres for all the algorithms are as following:


Minimum Cost of Spanning Tree is: 515176.2625903744 m
Minimum Cost of Christofide's is: 794638.277210466 m
Minimum Cost of Two Opt is: 637799.0242362794 m 
Minimum Cost of Three Opt is: 648588.4729612446 m
Minimum Cost of Simulated Annealing is: 636902.9388744511 m
Minimum Cost of Greedy Algorithm is : 714090.0347639857 m

Conclusion:

Based on the results obtained from running the algorithms and implementing various optimizations, we can conclude that the Simulated Annealing Optimization algorithm is the most effective in providing the lowest tour cost for the given set of points. The Two Opt Swap method also provides a highly optimised solution, which is very close to the solution provided by the Simulated Annealing method.
