import java.io.*;
import java.util.*;

/**
 * Maps a file to another file using the provided or random cipher.
 *
 * @author Jonah Tharakan
 */
public class Mapper {

    // --- Fields:

    /** A reference to the input file. */
    File file;
    /** The contents of the input file. */
    String fileContents;
    /** An array that represents the cipher. Index 0 is what A turns into,
     *  1 is what B turns into, ..., 25 is what Z turns into.
     */
    char[] cipher;

    /** Random object. */
    Random random = new Random();


    // --- Constructors:

    /**
     * Creates a Mapper object with random cipher, given by a random seed.
     */
    public Mapper(String fileName) {
        setRandomCipher();
        createAndVerifyFile(fileName);
    }

    /**
     * Creates a Mapper object with random cipher, given by seed SEED.
     */
    public Mapper(int seed, String fileName) {
        random.setSeed(seed);
        setRandomCipher();
        createAndVerifyFile(fileName);
    }

    /**
     * Creates a Mapper object with cipher CIPHERSTRING.
     * If CIPHERSTRING does not match the correct condtions, prints
     * an error message and ends the progeam.
     */
    public Mapper(String cipherString, String fileName) {
        setGivenCipher(cipherString);
        createAndVerifyFile(fileName);
    }


    // --- Methods:

    /**
     * Sets the field file to be a file given by fileName in the CWD
     * - The file must be a .txt file
     * - Non-alphabetical characters are ignored.
     * If these conditions are not met, prints an error message and ends
     * the program.
     */
    private void createAndVerifyFile(String fileName) {
        file = new File(fileName);
        if (!fileName.endsWith(".txt")) {
            System.out.println("File must be a .txt file.");
            System.exit(0);
        }
        fileContents = readFile(file);
    }

    /**
     * Reads a text file and returns a string with its contents.
     * Non-alphabetical characters are ignored.
     */
    private String readFile(File f) {
        String result = "";
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext("[a-zA-Z]+")) {
                result += sc.next("[a-zA-Z]+");
            }
            sc.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    /**
     * Sets cipher to a random configuration using random.
     */
    private void setRandomCipher() {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        ArrayList<Character> lst = new ArrayList<Character>();
        for (char c : alphabet) {
            lst.add(c);
        }
        Collections.shuffle(lst, random);
        for (int i = 0; i < lst.size(); i++) {
            cipher[i] = lst.get(i).charValue();
        }
    }

    /**
     * Sets cipher to the correct cipher given by CIPHERSTRING.
     * Prints an error message and exits if something is wrong.
     */
    private void setGivenCipher(String cipherString) {
        if (cipherString.length() != 26) {
            System.out.println("Given cipher is not 26 characters");
            System.exit(0);
        }

        cipher = cipherString.toUpperCase().toCharArray();

        // Verification
        HashSet<Character> cipherSet = new HashSet<Character>();
        for (char c : cipher) {
            cipherSet.add(c);
        }
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (char a : alphabet) {
            if (!cipherSet.contains(a)) {
                System.out.println("Given cipher does not contain all 26 unique letters.");
                System.exit(0);
            }
        }
    }

    /**
     * Maps the contents of the input file to an output file
     * using the cipher. Output file will have name given by
     * <input file name>-encoded.txt
     * (<input file name> does not include the .txt part.)
     */
    public void mapToOutputFile() {
        String name = file.getName().substring(0, file.getName().length() - 4);
        name += "-encoded.txt";
        File output = new File(name);

        try {
            FileWriter writer = new FileWriter(output);
            output.createNewFile();
            for (char c : fileContents.toCharArray()) {
                writer.write(cipher[c - 65]);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
