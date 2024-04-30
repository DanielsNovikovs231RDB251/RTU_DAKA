// 231RDB251 Daniels Novikovs
// 111RDB111 Ilze Programmētāja
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.Arrays;



public class Main {


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String choiseStr;
		String sourceFile, resultFile, firstFile, secondFile;
		
		loop: while (true) {
			
			choiseStr = sc.next();
								
			switch (choiseStr) {
			case "comp":
				System.out.print("source file name: ");
				sourceFile = sc.next();
				System.out.print("archive name: ");
				resultFile = sc.next();
				comp(sourceFile, resultFile);
				break;
			case "decomp":
				System.out.print("archive name: ");
				sourceFile = sc.next();
				System.out.print("file name: ");
				resultFile = sc.next();
				decomp(sourceFile, resultFile);
				break;
			case "size":
				System.out.print("file name: ");
				sourceFile = sc.next();
				size(sourceFile);
				break;
			case "equal":
				System.out.print("first file name: ");
				firstFile = sc.next();
				System.out.print("second file name: ");
				secondFile = sc.next();
				System.out.println(equal(firstFile, secondFile));
				break;
			case "about":
				about();
				break;
			case "exit":
				break loop;
			}
		}

		sc.close();
	}

	public static void comp(String sourceFile, String resultFile) {
		// TODO: implement this method
	}

	public static void decomp(String sourceFile, String resultFile) {
		// TODO: implement this method
	}
	
	public static void size(String sourceFile) {
		try {
			FileInputStream f = new FileInputStream(sourceFile);
			System.out.println("size: " + f.available());
			f.close();
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	public static boolean equal(String firstFile, String secondFile) {
		try {
			FileInputStream f1 = new FileInputStream(firstFile);
			FileInputStream f2 = new FileInputStream(secondFile);
			int k1, k2;
			byte[] buf1 = new byte[1000];
			byte[] buf2 = new byte[1000];
			do {
				k1 = f1.read(buf1);
				k2 = f2.read(buf2);
				if (k1 != k2) {
					f1.close();
					f2.close();
					return false;
				}
				for (int i=0; i<k1; i++) {
					if (buf1[i] != buf2[i]) {
						f1.close();
						f2.close();
						return false;
					}
						
				}
			} while (!(k1 == -1 && k2 == -1));
			f1.close();
			f2.close();
			return true;
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public static void about() {
		// TODO insert information about authors
		System.out.println("231RDB251 Daniels Novikovs");
		System.out.println("111RDB111 Ilze Programmētāja");
	}
}
// Huffman coding start
class Node implements Comparable<Node> {
    char data;
    int frequency;
    Node left, right;

    public Node(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        left = right = null;
    }

    public Node(int frequency, Node left, Node right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node other) {
        return this.frequency - other.frequency;
    }
}

class HuffmanEncoder {
    public static Map<Character, String> generateCodes(Node root) {
        Map<Character, String> codes = new HashMap<>();
        generateCodesHelper(root, "", codes);
        return codes;
    }

    private static void generateCodesHelper(Node root, String code, Map<Character, String> codes) {
        if (root == null)
            return;

        if (root.left == null && root.right == null) {
            codes.put(root.data, code);
            return;
        }

        generateCodesHelper(root.left, code + "0", codes);
        generateCodesHelper(root.right, code + "1", codes);
    }

    public static String encode(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            pq.offer(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node combined = new Node(left.frequency + right.frequency, left, right);
            pq.offer(combined);
        }

        Node root = pq.poll();
        Map<Character, String> codes = generateCodes(root);

        StringBuilder encodedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedText.append(codes.get(c));
        }

        return encodedText.toString();
    }
}
// Huffman coding end
// LZW coding start
class LZWDictionary {
public Map<byte[], String> dictionary;
private int nextCode;
private int maxCodeLength;
private int currentCodeLength;
private int currentCode; 

public LZWDictionary(int maxCodeLength) {
    dictionary = new HashMap<>();
    for (int i = 0; i < 256; i++) {
		byte[] b = new byte[1];
		b[0] = (byte) i;
		dictionary.put(b, String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0'));
    }
    this.nextCode = 256;
    this.maxCodeLength = maxCodeLength;
    this.currentCodeLength = 9;
    this.currentCode = 256; 
}

public Map<byte[], String> getDictionary() {
    return dictionary;
}

public void addToDictionary(String key) {
	if (nextCode < (1 << maxCodeLength)) {
        byte[] combinedKey = (currentCode + key).getBytes();
        if (!dictionary.containsKey(combinedKey)) {
            dictionary.put(combinedKey, String.format("%" + currentCodeLength + "s", Integer.toBinaryString(nextCode)).replace(' ', '0'));
            nextCode++;
            if (nextCode == (1 << currentCodeLength)) {
                currentCodeLength++;
            }
        }
    }
}
public static void main(String[] args) {
    LZWDictionary lzwDictionary = new LZWDictionary(12); 
    Map<byte[], String> dictionary = lzwDictionary.getDictionary();
    for (Entry<byte[], String> entry : dictionary.entrySet()) {
        System.out.println(Arrays.toString(entry.getKey()) + ": " + entry.getValue());
        }
    }
}
