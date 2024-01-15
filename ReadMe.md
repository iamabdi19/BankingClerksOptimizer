CENG303- Term Project - Group 16

Team members:
20050141037 Abdirahman Abdiweli Osman

# Overview
In this project, I have created an optimization tool for determining the minimum no. of clerks needed to serve bank customers efficiently while meeting specific maximum waiting time requirements. The tool simulates a banking system with three units (loans, casual, commercial) and divides the working day into three work shifts, each with varying customer traffic. Notably, waiting times differ among units, with commercial customers experiencing the shortest waits.
This versatile tool allows users to generate random customer data or input data manually. It then calculates and suggests the minimum number of clerks required for each unit and shift to ensure timely service and meet waiting time constraints.


## Algorithms employed
The core of the program lies in its efficient use of a combination of min-heap data structures and binary search algorithms whose time complexity is O(nlogn), signifying its efficiency for larger datasets. This approach ensures an effective calculation of the minimum number of clerks needed for each unit and shift, adhering to the constraints of maximum waiting times for each customer type. Here's a brief overview of the algorithmic process:

1. Initialization: Creates a min-heap for each unit and shift to store customer data.
2. Data Input: Prompts the user to input customer data manually or to generate random customer data.
3. Clerk Calculation: Employs binary search to estimate the number of clerks required for each unit and shift.
4. Simulation Process: It simulates the working of clerks for each estimated number of clerks. During this simulation, it checks if the actual waiting times for customers exceed the predefined maximum limits.
5. Dynamic Clerk Adjustment: Based on the simulation results, the program dynamically adjusts the number of clerks. If actual waiting times exceed the maximum, the number of clerks is increased. Conversely, if the actual waiting times are less, the number of clerks is decreased. This process continues iteratively until the optimal minimum number of clerks is found for each unit and shift.

## Key Classes 
The program is organized into several key classes:
1. BankingClerksOptimizer: This is the main class containing the main method. It orchestrates the flow of the program and calls other classes and methods.
2. BankCustomer: Represents a customer, encapsulating essential data like arrival time, processing time, and unit type.
3. BankCustomerMinHeap: Implements a min-heap data structure to manage and simulate customer data efficiently.
4. BankClerk: Manages clerk-related functionalities, including customer data input and random data generation.
5. BankClerkMinHeap: Another crucial min-heap structure, used specifically for simulating the clerks' operations.


## Test Case: Manual Customer Data Entry
## User Input:

-option 2 for Manual data entry selected.
-Max wait times: Commercial 4 mins, Loans 6 mins, Casual 7 mins.
-Customer Data:
Shift 1: (5 customers) 09:01 2 1 09:02 10 2 09:30 12 2 10:00 11 2 10:01 12 3
Shift 2: (5 customers) 13:01 2 1 13:02 10 2 13:30 12 2 14:03 11 2 14:11 12 3
Shift 3: (4 customers) 18:01 2 1 18:02 10 2 18:30 12 2 19:00 11 2

## Expected Output:

-Max wait times: Commercial 4 mins, Loans 6 mins, Casual 7 mins.
-Clerks needed per shift:
Shift 1: 1 each for Commercial, Loans, Casual.
Shift 2: 1 each for Commercial, Loans, Casual.
Shift 3: 1 for Commercial and Casual, 0 for Loans.

## How to execute the proram
use the following command in your terminal to run the jar file:
java -jar [path_to_jar_file]

## Contributing
Contributions in the form of bug fixes, new features, or documentation improvements are highly welcome.

## Contact
Feel free to reach out via email: abduraz4917@gmail.com


