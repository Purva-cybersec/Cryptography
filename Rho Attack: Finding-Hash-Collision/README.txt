README

###Rho Attack: Finding Hash Collision
This program implements the Rho method to find a collision in a hash function using the truncated SHA-256 algorithm. Collision detection is performed in constant memory, starting with a random point.
Instructions

#Prerequisites
•	First unzip the folder and follow the below provided steps.
•	Make sure the OpenSSL library is available.

#Compilation
Compile the code using the following command:
g++ -o rho_attack RhoAttack.cpp -lssl -lcrypto -std=c++11

#Execution
Run the compiled program with the following command:
./rho_attack

#Output
The program will output information about the collision, including:
•	X and X' values
•	H(X) and H(X') values (truncated SHA-256)
•	SHA-256(X) and SHA-256(X') values
•	Truncated Hash
If no collision is found, a failure message will be displayed.

#Note
•	This program uses constant memory and starts with a random point for each run.
•	If the Rho attack can't detect a cycle after 2^20 steps, it terminates with a failure message.
