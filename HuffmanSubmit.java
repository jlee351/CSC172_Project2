import java.util.HashMap;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class HuffmanSubmit implements Huffman{

    int count;                                  //counts for character
    char ch;                                    //character
    HashMap<Character, Integer> charFrequency;  //character's frequency
    HashMap <Character, String> map;            //huffman code for character
    HashMap<String,Character> mapswap;          //map for string and character swapped

    public HuffmanSubmit(){                     //constructor for HuffmanSubmit
        count = 0;
        ch = 'a';
        charFrequency = new HashMap<>();
        map = new HashMap<>();
        mapswap = new HashMap<>();
    }

    public void encode(String inputFile, String outputFile, String freqFile){   //encode

        BinaryIn in = new BinaryIn(inputFile);      //new BinaryIn
		BinaryOut out = new BinaryOut(outputFile);  //new BinaryOut

        while (!in.isEmpty()) {
			char c = in.readChar();
			charFrequency.put(c, 0);
		}

		in = new BinaryIn(inputFile);                 //new BinaryIn

        while (!in.isEmpty()) {
			char c = in.readChar();
			count += 1;
            charFrequency.put(c, charFrequency.get(c)+1);
		}

        try {
			PrintWriter printWriter = new PrintWriter(freqFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        BufferedWriter outWriter = null;

        try {
            outWriter = new BufferedWriter (new FileWriter(freqFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

        for (char c:charFrequency.keySet()) {
			try {
				String temp = Integer.toBinaryString(c);
				while (temp.length() < 8) {     //Flushes the binary output stream, padding 0s if number of bits written so far is not a multiple of 8.
					temp = "0" + temp;
				}
				outWriter.write(temp + ":" + charFrequency.get(c));
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				outWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        try {
			outWriter.flush();   //from BinaryOut.java
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			outWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Node root = makeQueue();
		codeTree(root,"");

		in=new BinaryIn(inputFile);
		out.write(count);

		while (!in.isEmpty()) {
			char c = in.readChar();
			String binary = map.get(c);
			char[] bin = binary.toCharArray();
			for (char temp:bin) {
				if (temp == '0') {
					out.write(false);
				}else if (temp == '1') {
					out.write(true);
				}
			}

		}

		out.flush();  //from BinaryOut.java
    }

   public void decode(String inputFile, String outputFile, String freqFile){

       BinaryIn in = new BinaryIn(inputFile);
       BinaryOut out=new BinaryOut(outputFile);
       HashMap<Character,Integer> map = new HashMap<>();
       BufferedReader buffRead = null;
       FileReader fileRead = null;

       try {
           fileRead = new FileReader(freqFile);
           buffRead = new BufferedReader(fileRead);
           String temp; //temporary string to store current line

           while ((temp = buffRead.readLine()) != null){
				String[] array = temp.split(":");
				map.put((char)Integer.parseInt(array[0],2), Integer.parseInt(array[1]));
			}
            charFrequency = map;
			map = new HashMap<>();
			codeTree(makeQueue(),"");

            in = new BinaryIn(inputFile);
			String st = "";
			boolean a = true;
			int count = 0;
			int size = in.readInt();

            while(count<size){
				while (!this.mapswap.containsKey(st)){
					a = in.readBoolean();
					if(a == true) {
						st = st + "1";
					}
                    else if(a == false){
                        st = st + "0";
					}
				}
				out.write(mapswap.get(st));
				st = "";
				count += 1;
			}
            out.flush();    //from BinaryOut.java


       } catch(IOException e){
           e.printStackTrace();
       }

   }

   public void codeTree(Node n, String str) {
		if (n.left==null && n.right==null) {
			map.put(n.getChar(), str);
			mapswap.put(str, n.getChar());
			return;
		}
		codeTree(n.left, str + "0");
		codeTree(n.right, str + "1");
	}

    public Node makeQueue() {

		PriorityQueue<Node> o = new PriorityQueue<>();

		for (char key: this.charFrequency.keySet()){
			Node node = new Node(charFrequency.get(key),key);
			o.offer(node);
		}

		while (o.size()>1){
			Node node1 = o.poll();
			Node node2 = o.poll();
			Node temp = new Node(node1.getFreq() + node2.getFreq());
			temp.left = node1;
			temp.right = node2;
			o.offer(temp);
		}

		return o.poll();
	}

   public static void main(String[] args){
       Huffman huffman = new HuffmanSubmit();
       huffman.encode("ur.jpg", "ur.enc", "freq.txt");
       huffman.decode("ur.enc", "ur_dec.jpg", "freq.txt");
   }

}


class Node implements Comparable<Node>{
    Node left;
    Node right;
    int freq;
    char ch;

    public Node(){
        this.freq = 0;
        this.left = null;
        this.right = null;
    }

    public Node(int freq){
        this.freq = freq;
        this.left = null;
        this.right = null;
    }

    public Node(int freq, char ch){
        this.freq = freq;
        this.ch = ch;
        this.left = null;
        this.right = null;
    }

    public char getChar(){
        return this.ch;
    }

    public int getFreq(){
        return this.freq;
    }

    @Override
	public int compareTo(Node o) {
		return this.freq - o.freq;
	}

}
