# PadOracle Java Program

This Java program demonstrates a Padding Oracle attack. It connects to a remote server and performs encryption and decryption operations based on a given Padding Oracle.

## Prerequisites

First unzip the folder and follow the below provided steps.

- Java Development Kit (JDK) installed on your machine.

## Import these libraries if any error occurs while running, since I have already included them in the code.
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

## How to Run

1. **Clone the Repository:**

    ```bash
    git clone Hw2_files
    cd Hw2_files
    ```

2. **Compile the Java Code:**

    ```bash
    javac PadOracle.java
    ```

3. **Run the Program:**

    ```bash
    java PadOracle
    ```

4. **Follow the Output:**

    The program will connect to the specified server, perform the Padding Oracle attack, and display the results.

## Configuration

- Adjust the server address and port in the `Socket` constructor (`Socket("128.186.120.191", 31537)`) based on your setup.


