
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

// public class Main {

//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         String command;
//         String galvfail, izejfail, pirmfail, otrfail;

//         loop: while (true) {

//             command = sc.next();

//             switch (command) {
//                 case "comp":
//                     System.out.print("source file name: ");
//                     galvfail = sc.next();
//                     System.out.print("archive name: ");
//                     izejfail = sc.next();
//                     compress(galvfail, izejfail);
//                     break;
//                 case "decomp":
//                     System.out.print("archive name: ");
//                     galvfail = sc.next();
//                     System.out.print("file name: ");
//                     izejfail = sc.next();
//                     decompress(galvfail, izejfail);
//                     break;
//                 case "size":
//                     System.out.print("file name: ");
//                     galvfail = sc.next();
//                     filesize(galvfail);
//                     break;
//                 case "equal":
//                     System.out.print("first file name: ");
//                     pirmfail = sc.next();
//                     System.out.print("second file name: ");
//                     otrfail = sc.next();
//                     System.out.println(equal(pirmfail, otrfail));
//                     break;
//                 case "about":
//                     about();
//                     break;
//                 case "exit":
//                     break loop;
//             }
//         }

//         sc.close();
//     }


// 	public static void compress(String galvfail, String izejfail) {
//         try (FileInputStream in = new FileInputStream(galvfail);
//                 FileOutputStream out = new FileOutputStream(izejfail)) {

//             byte[] bfr = new byte[1024];
//             int bread;

//             List<Byte> blist = new ArrayList<>();

//             while ((bread = in.read(bfr)) != -1) {
//                 for (int i = 0; i < bread; i++) {
//                     blist.add(bfr[i]);
//                 }
//             }
//             byte[] bry = new byte[blist.size()];
//             for (int i = 0; i < blist.size(); i++) {
//                 bry[i] = blist.get(i);
//             }

//             List<Integer> encdat = LZWDict.encoder(bry);

//             for (int code : encdat) {
//                 out.write((code >> 8) & 0xFF); 
//                 out.write(code & 0xFF); 
//             }
//         } catch (IOException e) {
//             System.out.println(e.getMessage());
//         }
//     }

// 	public static void decompress(String galvfail, String izejfail) {
//         try (FileInputStream in = new FileInputStream(galvfail);
//                 FileOutputStream out = new FileOutputStream(izejfail)) {

//             List<Integer> encdat = new ArrayList<>();
//             int b;

//             while ((b = in.read()) != -1) {
//                 int code = (b << 8) | in.read(); 
//                 encdat.add(code);
//             }

//             byte[] decdat = LZWDict.decoder(encdat);

//             out.write(decdat);
//         } catch (IOException e) {
//             System.out.println(e.getMessage());
//         }
//     }

// 	public static void filesize(String galvfail) {
//         try {
//             FileInputStream f = new FileInputStream(galvfail);
//             System.out.println("size: " + f.available());
//             f.close();
//         } catch (IOException ex) {
//             System.out.println(ex.getMessage());
//         }
//     }

// 	public static boolean equal(String galvfail, String izejfail) {
//         try {
//             FileInputStream fil1 = new FileInputStream(galvfail);
//             FileInputStream fil2 = new FileInputStream(izejfail);
//             int b1, b2;
//             byte[] bfr1 = new byte[1000];
//             byte[] bfr2 = new byte[1000];
//             do {
//                 b1 = fil1.read(bfr1);
//                 b2 = fil2.read(bfr2);
//                 if (b1 != b2) {
//                     fil1.close();
//                     fil2.close();
//                     return false;
//                 }
//                 for (int i = 0; i < b1; i++) {
//                     if (bfr1[i] != bfr2[i]) {
//                         fil1.close();
//                         fil2.close();
//                         return false;
//                     }

//                 }
//             } while (!(b1 == -1 && b2 == -1));
//             fil1.close();
//             fil2.close();
//             return true;
//         } catch (IOException ex) {
//             System.out.println(ex.getMessage());
//             return false;
//         }
//     }
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

	// public static void about() {
    //     System.out.println("231RDB251 Daniels Novikovs");
    //     System.out.println("231RDB335 Kirills Bogdanovs");
    // }
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
// class LZWDict {
//     public static List<Integer> encoder(byte[] dat) {
//         if (dat.length == 0) {
//             return new ArrayList<>();
//         }
//         int maxlength = 256;
//         Map<List<Byte>, Integer> dict = new HashMap<>();
//         for (int i = 0; i < maxlength; i++) {
//             List<Byte> seq = new ArrayList<>();
//             seq.add((byte) i);
//             dict.put(seq, i);
//         }

//         List<Byte> tagseq = new ArrayList<>();
//         List<Integer> encdat = new ArrayList<>();

//         for (byte nextbyte : dat) {
//             List<Byte> newseq = new ArrayList<>(tagseq);
//             newseq.add(nextbyte);

//             if (dict.containsKey(newseq)) {
//                 tagseq = newseq;
//             } else {
//                 encdat.add(dict.get(tagseq));
//                 dict.put(newseq, dict.size());
//                 tagseq = new ArrayList<>(Arrays.asList(nextbyte));
//             }
//         }

//         if (tagseq.size()> 0) {
//             encdat.add(dict.get(tagseq));
//         }

//         return encdat;
//     }

//     public static byte[] decoder(List<Integer> enctext) {
//         if (enctext.size() == 0) {
//             return new byte[]{};
//         }

//         Map<Integer, List<Byte>> dictionary = new HashMap<>();
//         int maxlength = 256;
//         for (int i = 0; i < maxlength; i++) {
//             List<Byte> seq = new ArrayList<>();
//             seq.add((byte) i);
//             dictionary.put(i, seq);
//         }

//         List<Byte> result = new ArrayList<>();
//         int pagkod = enctext.get(0);
//         result.addAll(dictionary.get(pagkod));

//         for (int i = 1; i < enctext.size(); i++) {
//             int currentCode = enctext.get(i);
//             List<Byte> ieej = dictionary.getOrDefault(currentCode, new ArrayList<>(dictionary.get(pagkod)));
//             if (currentCode == dictionary.size()) {
//                 ieej.add(dictionary.get(pagkod).get(0));
//             } else if (!dictionary.containsKey(currentCode)) {
//                 throw new IllegalArgumentException("Bad compressed code");
//             }

//             List<Byte> pagkodBytes = dictionary.get(pagkod);
//             byte[] jauniej = Arrays.copyOf(tobytearray(pagkodBytes), pagkodBytes.size() + 1);
//             jauniej[jauniej.length - 1] = ieej.get(0);
//             result.addAll(ieej);
//             dictionary.put(dictionary.size(), tobytelist(jauniej));
//             pagkod = currentCode;
//         }
//         return tobytearray(result);
//     }

//     private static byte[] tobytearray(List<Byte> list) {
//         byte[] ret = new byte[list.size()];
//         for (int i = 0; i < ret.length; i++) {
//             ret[i] = list.get(i);
//         }
//         return ret;
//     }

//     private static List<Byte> tobytelist(byte[] array) {
//         List<Byte> list = new ArrayList<>();
//         for (byte b : array) {
//             list.add(b);
//         }
//         return list;
//     }
// }
// LZW coding end
