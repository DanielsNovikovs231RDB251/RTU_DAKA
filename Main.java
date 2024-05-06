
// 231RDB251 Daniels Novikovs
// 231RDB295 Antons Denisovs
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.Map.Entry;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
	private static String encodedText;
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
		String fileData = fileReader(sourceFile);
		System.out.println(fileData);
		EncodedData encodedData = HuffmanEncoder.encode(fileData);
		encodedText = encodedData.getEncodedText();
		Map<Character, String> codeTable = encodedData.getCodeTable();
		CodeTableManager.saveCodeTable(resultFile, codeTable);
		fileWriterBitSet(encodedText, resultFile);
		
		
	}

	public static void decomp(String sourceFile, String resultFile) {
		String text = readBitFile(sourceFile);
		Map<Character, String> retrievedCodeTable = CodeTableManager.getCodeTable(sourceFile);
		String decodedText = HuffmanEncoder.decode(text, retrievedCodeTable);
		fileWriterText(decodedText, resultFile);
	}

	public static void size(String sourceFile) {
		try {
			FileInputStream f = new FileInputStream(sourceFile);
			System.out.println("size: " + f.available());
			f.close();
		} catch (IOException ex) {
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
				for (int i = 0; i < k1; i++) {
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
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	public static void saveHuffmanTree(Node root, String fileName)  {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(root);
            oos.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
	public static Node loadHuffmanTree(String fileName)  {
        try {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            return (Node) ois.readObject();
        }catch (Exception e){
            System.out.println("1");
        }
        return null;
    }

	public static void about() {
		// TODO insert information about authors
		System.out.println("231RDB251 Daniels Novikovs");
		System.out.println("231RDB295 Antons Denisovs");
	}
	public static String readBitFile(String fileName){
        StringBuilder bitSequence = new StringBuilder();
        try{
            FileInputStream fis = new FileInputStream(fileName);
            byte[] bytes = fis.readAllBytes();
            BitSet bitSet = BitSet.valueOf(bytes);

            for (int i = 0; i < bitSet.length(); i++) {
                char c = bitSet.get(i) ? '1' : '0';
            bitSequence.append(c);
            }
            fis.close();
        }catch (Exception e){
            System.out.println("er1");
        }
        return bitSequence.toString();
    }

	public static String fileReader(String file) {
		try {
            return Files.readString(Paths.get(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
		}
	}

	public static void fileWriterBitSet(String encodedText, String outFile) {
		BitSet bitSet = new BitSet(encodedText.length());

		for (int i = 0; i < encodedText.length(); i++) {
			char c = encodedText.charAt(i);
			if (c == '1') {
				bitSet.set(i, true);
			} else {
				bitSet.set(i, false);
			}
		}

		String fileName = outFile;

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			byte[] byteArray = bitSet.toByteArray();
			fileOutputStream.write(byteArray);
			System.out.println("BitSet successfully written to file.");
			fileOutputStream.close();
		} catch (IOException e) {
			System.err.println("Error writing BitSet to file: " + e.getMessage());
		}
	}
	public static void fileWriterText(String text, String resultfile){
        try{
            FileOutputStream fos = new FileOutputStream(resultfile);
            fos.write(text.getBytes());
            fos.close();
        }catch (Exception e){
            System.out.println("e");
        }
    }
}

// Huffman coding start
class CodeTableManager {
    private static Map<String, Map<Character, String>> codeTables = new HashMap<>();

    public static void saveCodeTable(String fileName, Map<Character, String> codeTable) {
        codeTables.put(fileName, codeTable);
    }

    public static Map<Character, String> getCodeTable(String fileName) {
        return codeTables.get(fileName);
    }
}
class Node implements Comparable<Node>{
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

class EncodedData {
    private String encodedText;
    private Map<Character, String> codeTable;

    public EncodedData(String encodedText,Map<Character, String> codeTable) {
        this.encodedText = encodedText;
        this.codeTable = codeTable;
    }

    public String getEncodedText() {
        return encodedText;
    }

    public Map<Character, String> getCodeTable() {
        return codeTable;
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

	public static EncodedData encode(String text) {
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

		return new EncodedData(encodedText.toString(), codes);

	}
	public static String decode(String encodedText,Map<Character, String> codeTable ) {
		StringBuilder decodedText = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (char bit : encodedText.toCharArray()) {
            currentCode.append(bit);
            for (Map.Entry<Character, String> entry : codeTable.entrySet()) {
                if (entry.getValue().equals(currentCode.toString())) {
                    decodedText.append(entry.getKey());
                    currentCode.setLength(0);
                    break;
                }
            }
        }
    
        return decodedText.toString();
    }
}


// Huffman coding end
// LZW coding start
class LZWDictionary {
	public static List<Integer> encode(String text) {
		int dictSize = 256;
		Map<String, Integer> dictionary = new HashMap<>();
		for (int i = 0; i < dictSize; i++) {
			dictionary.put(String.valueOf((char) i), i);
		}
		String foundChars = "";
		List<Integer> result = new ArrayList<>();
		for (char character : text.toCharArray()) {
			String charsToAdd = foundChars + character;
			if (dictionary.containsKey(charsToAdd)) {
				foundChars = charsToAdd;
			} else {
				result.add(dictionary.get(foundChars));
				dictionary.put(charsToAdd, dictSize++);
				foundChars = String.valueOf(character);
			}
		}
		if (!foundChars.isEmpty()) {
			result.add(dictionary.get(foundChars));
		}
		return result;
	}
}
