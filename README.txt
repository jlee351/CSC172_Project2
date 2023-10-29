// Project 2 by Lee, Joon Hyup

// email: jlee351@u.rochester.edu

// Explanation:

BinaryIn.java: GIVEN

BinaryOut.java: GIVEN

Huffman.java: GIVEN

HuffmanSubmit.java:
HuffmanSubmit imports the following libraries from Java:

import java.util.HashMap;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

HuffmanSubmit is a class with five attributes; count, ch, charFrequency, map and mapswap. Each correspond to the count of each character, the character itself, map of char's frequency and two huffman codes for the characters.
encode() has three parameters; inputFile, outputFile, freqFile. Each names of the files encode() needs and generates.
decode() also has three parameters, with the same purpose.
codeTree() is a recursive method that writes the node tree.
makeQueue() parses through charFrequency

class Node is a node class inside the HuffmanSubmit class. It has three constructors 
