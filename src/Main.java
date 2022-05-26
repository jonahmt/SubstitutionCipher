/**
 * Handles program input.
 *
 * @author Jonah Tharakan
 */
public class Main {

    /**
     *  Main method. Handles program input.
     *  Provided file must be a .txt file with only alphabetical characters (letters)
     *  - 1 argument provided:
     *      [file name]
     *      * Random cipher used, with random seed
     *  - 2 arguments provided:
     *      [cipher] [file name]
     *      * Uses cipher provided, which must be 26 characters long with
     *        no repeating letters.
     *  - 3 arguments provided:
     *      "seed" [seed #] [file name]
     *      * Random cipher used, with seed given by seed #.
     */
    public static void main(String[] args) {

        Mapper mapper = null;
        if (args.length == 0) {
            printHelpMessage();
        } else if (args.length == 1) {
            mapper = new Mapper(args[0]);
        } else if (args.length == 2) {
            mapper = new Mapper(args[0], args[1]);
        } else if (args.length == 3) {
            if (!args[0].equals("seed")) {
                System.out.println("Format error. First argument of a 3 argument input " +
                        "must be 'seed'.");
            }

            int seed = parseSeed(args[1]);
            mapper = new Mapper(seed, args[2]);
        } else {
            System.out.println("Incorrect number of arguments provided.");
            System.exit(0);
        }
        mapper.mapToOutputFile();

    }

    private static int parseSeed(String seedString) {
        int seed = 0;
        try {
            seed = Integer.parseInt(seedString);
        } catch (NumberFormatException e) {
            System.out.println("Given seed is in incorrect format. It must be a number.");
            System.exit(0);
        }
        return seed;
    }

    private static void printHelpMessage() {
        System.out.println("<Write description of program inputs here>");
    }

}
