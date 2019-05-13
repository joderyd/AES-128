import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class AESutils {
    private AESutils(){}


    /**
     * Converts a string of 16 bits to a 4x4 Matrix
     *
     * @param string to be converted
     * @return a 4x4 matrix block
     */
    public static int[][] stringToMatrix (String string) {
        int bits = 128;
        int N = (int)Math.sqrt(bits/8);
        int[][] matrix = new int[N][N];

        while(string.length()< bits/8){
            string += " ";
        }

        byte[] bytes = string.getBytes();

        for(int i=0; i< N; i++){
            for(int j=0; j< N; j++){
                matrix[j][i] = (int)bytes[i*4+j];
            }
        }
        return matrix;
    }

    public static String hexify(String ascii){
        char[] chars = ascii.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(char c : chars){
            String hex = Integer.toHexString((int) c);
            stringBuilder.append(hex.toUpperCase());
        }
        return stringBuilder.toString();
    }


    public static void printMatrix(int[][] matrix){
        for(int[] word : matrix){
            for(int i : word){
                System.out.print(i + "\t");
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    public static void printHexMatrix(int[][] matrix){
        for(int[] word : matrix){
            for(int i : word){
                String hex = Integer.toHexString(i);
                System.out.print(hex+ "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printHexArray(int[] array){
        for(int i : array){
            String hex = Integer.toHexString(i);
            if(hex.length() == 1){
                System.out.print("0");
            }
            System.out.println(hex);
        }
        System.out.println();
    }

    public static int[] getColumn(int[][] matrix, int idx){
        int[] column = new int[matrix.length];
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                if(j==idx){
                    column[i] = matrix[i][j];
                }
            }
        }
        return column;
    }

    public static int[][] getSbox(boolean inverted){
        int[][] sBox = new int[16][16];
        File file = new File("src/sBox");
        if(inverted){
            file = new File("src/sBoxInv");
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            for(int i=0; i<16; i++){
                String line = br.readLine();
                String[] sub = line.split("\t");
                for(int j=0; j<16; j++){
                    String byteString = sub[j];
                    int byteValue = Integer.parseInt(byteString, 16);
                    sBox[i][j] = byteValue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sBox;
    }



    public static int[] Xor(int[] a, int[] b){
        int[] result = new int[a.length];
        for(int i=0; i< a.length; i++){
            result[i] = a[i] ^ b[i];
        }
        return result;
    }



}
