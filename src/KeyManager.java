import java.util.Random;

public class KeyManager {
    public static final int[] roundConstants = {
            1, 2, 4, 8, 16, 32, 64, 128, 27, 54
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
                System.out.println("More than 2 hex-numbers in SubWord!" + hex);
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
        int[] col3 = AESutils.getColumn(prevKey, 3);
        int[] col0 = AESutils.getColumn(prevKey, 0);

        int[] w4 = SubWord(RotWord(col3), AESutils.getSbox(false));
        int[] rcon = new int[]{roundConstants[keyRound], 0, 0, 0};

        for(int i=0; i<4; i++){
            nextKey[i][0] = w4[i] ^ rcon[i] ^ col0[i];
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



    public static void main(String[] args){
        //String key0 = "Thats my Kung Fu";
        //String plain0 = "Two One Nine Two";
        //String key1 = "denviktigastekey";
        //String plain1 = "jagheterjonathan";

        String plain = "ò\u0095¹1\u008B\u0099D4Ù=\u0098¤äI¯Ø";
        String key = "ôÀ  ¡öý4?¬j~jàù";

        int[][] block = AESutils.stringToMatrix(plain);
        int[][] rKey = AESutils.stringToMatrix(key);

        int[][] cipher = AES.encrypt(block, rKey);

        AESutils.printMatrixtoHex(cipher);
    }






}
