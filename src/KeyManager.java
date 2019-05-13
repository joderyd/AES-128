import java.util.Random;

public class KeyManager {
    public static final int[] roundConstants = {
            1, 2, 4, 8, 10, 20, 40, 80, 27, 36
    };


    public static int[] RotWord(int[] word){
        int[] rotWord = new int[word.length];
        for(int i=0; i<word.length-1; i++){
            rotWord[i] = word[i+1];
        }
        rotWord[word.length-1] = word[0];
        return rotWord;
    }

    public static int[] SubWord(int[] word, int[][] sBox){
        for(int i=0; i< word.length; i++){
            String hex = Integer.toHexString(word[i]);
            int row = 0;
            int col = 0;
            if(hex.length() == 1){
                col = Integer.parseInt(hex, 16);
            }
            if(hex.length() > 1){
                String nibble1 = hex.substring(0, 1);
                String nibble2 = hex.substring(1);
                row = Integer.parseInt(nibble1, 16);
                col = Integer.parseInt(nibble2, 16);
            }
            if(hex.length() > 2){
                System.out.println("More than 2 hex-numbers in SubWord!");
            }
            word[i] = sBox[row][col];
        }
        return word;
    }


    private static byte[] getRandomizedKey(){
        Random random = new Random();
        byte[] bytes = new byte[16];
        for(int i=0; i<16; i++){
            random.nextBytes(bytes);
        }
        return bytes;
    }



    public static int[][] getNextKey(int[][] prevKey, int keyRound){
        int[][] nextKey = new int[4][4];
        //int[] word0 = new int[4];
        int[] col3 = AESutils.getColumn(prevKey, 3);
        int[] col0 = AESutils.getColumn(prevKey, 0);

        int[] w4 = SubWord(RotWord(col3), AESutils.getSbox(false));
        int[] rcon = new int[]{roundConstants[keyRound], 0, 0, 0};

        for(int i=0; i<4; i++){
            nextKey[i][0] = w4[i] ^ rcon[i] ^ col0[i];
            //System.out.println(nextKey[i][0]);
        }
        for(int r=0; r<4; r++){
            for(int c=1; c<4; c++){
                nextKey[r][c] = nextKey[r][c-1] ^ prevKey[r][c];
            }
        }
        return nextKey;
    }

    public static byte[][] getKeyMatrix(String keyString){
        byte[][] keyMatrix = new byte[4][4];
        byte[] bytes = keyString.getBytes();
        if(bytes.length != 16) {
            bytes = getRandomizedKey();
        }

        int idx = 0;
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                keyMatrix[j][i] = bytes[idx];
                idx++;
            }
        }
        return keyMatrix;
    }

    public static int[][] expandKey(int[][] key){
        int[][] expandedKey = new int[4][44];
        for(int i=0; i<4; i++){
            for(int j=0; j<4; i++){
                expandedKey[j][i] = key[j][i];
            }
        }

        for(int i=4; i<44; i++){
            for(int j=0; j<4; i++){
                if(i>=4 && i%4==0){

                }
                expandedKey[j][i] = key[j][i];
            }
        }

        return expandedKey;
    }


    public static void main(String[] args){
        String s1 = "helloAES";

        String key = "Thats my Kung Fu";
        String plain = "Two One Nine Two";

        int[][] rKey = AESutils.stringToMatrix(key);
        int[][] block = AESutils.stringToMatrix(plain);
        //AESutils.printHexMatrix(rKey);
        //AESutils.printMatrix(rKey);
        int[][] sBox = AESutils.getSbox(false);
        int[] testSub = new int[]{207, 79, 60, 9};
        int[] sub = SubWord(testSub, sBox);

        int[][] utub = new int[][]{{43, 40, 171, 9}, {126, 174, 247, 207},
                {21, 210, 21, 79}, {22, 166, 136, 60}
        };
        int[][] nextKey = getNextKey(utub, 0);
        int[][] next = getNextKey(nextKey, 1);



        AESutils.printHexMatrix(nextKey);

        AESutils.printHexMatrix(next);

    }






}
