To create a README file for running your Java program in a Linux environment on the Linprog system, you can follow these steps:


       markdown

# Substitution Cipher Solver

This Java program decrypts a substitution cipher using the Monte Carlo method. It takes an encrypted text file, a bigrams file, and runs a simulation to recover the original text. Here's how you can run the program on the Linprog system.

## Running the Program on Linprog

1. Connect to Linprog:

   ```bash
   ssh your_username@linprog.gatech.edu

Replace your_username with your actual Linprog username.

Navigate to your project directory. You may use the cd command to change your working directory.

Upload the Java program and the input files (e.g., testInput.txt, bigrams.txt, and war-and-peace.txt) to your Linprog directory using an SCP client or WinSCP if you are on Windows. You can use the scp command for this purpose.

   bash

scp -P 2222 your_local_file your_username@linprog.gatech.edu:~

Once you have uploaded your files, access your Linprog account and navigate to the directory where your files are located.

Compile the Java program using the javac command:

bash

javac MonteCarlo.java

Run the program with the following command, replacing testInput.txt with the name of your encrypted input file:

bash 

java MonteCarlo testInput.txt

The program will start running the Monte Carlo simulation to decrypt the cipher. It will generate an output file named output.txt with the decrypted text.

The program will print the progress, including the iteration number and current score, to the command line. Once it reaches the maximum iterations, it will print the final key.


Example

bash

java MonteCarlo encrypted_text.txt

Output

The program generates an output.txt file containing the decrypted text. The encryption key used will be appended to the end of this file.
Note

    The program supports only 8-bit byte encoding with the US-ASCII character set.

    Make sure you have enough memory to run the program, especially if the input text is large.

Enjoy decrypting the cipher with the Monte Carlo method on Linprog!

