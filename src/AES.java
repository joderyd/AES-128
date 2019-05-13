import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AES {
    private static String keyString;
    private static String plaintext;





    public static void main(String[] args){
        //Scanner sc = new Scanner(System.in);
        Scanner sc;
        try{
            sc = new Scanner(new File("src/aes_sample.in"));
            String line = sc.nextLine();
            if(line.length() > 15){
                keyString = line.substring(0, 16);
            }
            StringBuilder stringBuilder;
            if(line.length() > 16){
                plaintext = line.substring(16);
                while(plaintext.length()%16 != 0){
                    plaintext += " ";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        String str = "ôÀ  ¡ö\u0004ý4?¬j~jàùò•¹1‹™D4Ù=˜¤äI¯Ø";
        AESutils.hexify(str);

        String st = "helloAES";
        byte[] bytes = st.getBytes();



        //System.out.println(AESutils.hexify("helloAES"));
        //System.out.println(str.length());
    }
}
