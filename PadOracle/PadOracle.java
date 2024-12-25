import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PadOracle {

    private static String hexToAscii(String hexString) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            String str = hexString.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public static void main(String[] args) {
        try {
            Socket r = new Socket("128.186.120.191", 31537);
            List<String> m1 = new ArrayList<>();
            int count = 0;
            String finalIv = "";
            String initialIv = "";
            String initialEnc = "";
            List<String> initialBlocks = new ArrayList<>();
            int n = 0;

            for (int k = 0; k < 16; k++) {
                if (k != 0) {
                    int noBlocks = initialEnc.length() / 32;
                    initialBlocks = new ArrayList<>();
                    for (int i = 0; i < initialEnc.length(); i += 32) {
                        initialBlocks.add(initialEnc.substring(i, i + 32));
                    }
                }

                while (true) {
                    r.getOutputStream().write(("-e " + "00".repeat(k)).getBytes());
                    byte[] buffer = new byte[1024];
                    int bytesRead = r.getInputStream().read(buffer);
                    n += 1;

                    String message = new String(buffer, 0, bytesRead);
                    int encryptionStart = message.indexOf("Encryption:") + "Encryption: 80\\n".length();
                    int encryptionEnd = message.indexOf("IV:");
                    int ivStart = encryptionEnd + "IV: b'".length();
                    int ivEnd = message.indexOf("-e");

                    String encryption = message.substring(encryptionStart, encryptionEnd - 4).replaceAll(" ", "");
                    String iv = message.substring(ivStart, ivEnd).trim();
                    iv = iv.substring(0, iv.length() - 1);

                    int noBlocks = encryption.length() / 32;
                    List<String> blocks = new ArrayList<>();
                    for (int i = 0; i < encryption.length(); i += 32) {
                        blocks.add(encryption.substring(i, i + 32));
                    }

                    if (k == 0) {
                        blocks.set(blocks.size() - 1, blocks.get(count));
                        String verifyMsg = String.join("", blocks);
                        r.getOutputStream().write(("-v " + verifyMsg + " " + iv).getBytes());
                    } else {
                        String newBlock = blocks.get(0);
                        initialBlocks.set(initialBlocks.size() - 1, newBlock);
                        String verifyMsg = String.join("", initialBlocks);
                        r.getOutputStream().write(("-v " + verifyMsg + " " + initialIv).getBytes());
                    }

                    buffer = new byte[1024];
                    bytesRead = r.getInputStream().read(buffer);
                    String res = new String(buffer, 0, bytesRead);

                    if (!res.trim().equals("Invalid")) {
                        System.out.println("Valid " + n);
                        finalIv = iv;
                        if (k == 0) {
                            initialIv = finalIv;
                            initialEnc = encryption;
                        }
                        String previousBlock = k == 0 ? blocks.get(blocks.size() - 2) : initialBlocks.get(initialBlocks.size() - 2);

                        int int1 = Integer.parseInt(finalIv.substring(finalIv.length() - 2), 16);
                        int int2 = Integer.parseInt(previousBlock.substring(previousBlock.length() - 2), 16);

                        int finalByte = 15 ^ int1 ^ int2;
                        String resultHex = String.format("%02x", finalByte);
                        m1.add(resultHex);
                        break;
                    }
                }
            }

            System.out.print("Message in Hexadecimal: ");
            for (String hex : m1) {
                System.out.print(hex + " ");
            }
            System.out.println();

            String asciiResult = hexToAscii(String.join("", m1));
            System.out.println("Message in ASCII : " + asciiResult);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}