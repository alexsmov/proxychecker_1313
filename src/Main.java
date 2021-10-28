import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("C:/java/ip.txt");
            int i;
            String resultIp = "";
            while ((i = fis.read()) != -1) {
                if (i == 13) continue;
                else if (i == 10) {
                    String[] resultArray = resultIp.split(":");
                    String ip = resultArray[0];
                    int port = Integer.parseInt(resultArray[1]);
                    checkProxy(ip, port);
                    resultIp = "";
                } else if(i == 9){
                    resultIp += ":";
                } else {
                    resultIp += (char) i;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void checkProxy(String ip, int port){
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(ip, port));
            URL url = new URL("https://vozhzhaev.ru/test.php");
            URLConnection connection = url.openConnection(proxy);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            System.out.println("ip: "+ip+":"+port+" рабочий");
            FileOutputStream fos = new FileOutputStream("C:/java/good_ip.txt", true);
            String result = ip+":"+port+"\n";
            byte[] buffer = result.getBytes();
            fos.write(buffer);
            fos.flush();
            fos.close();
            in.close();
        } catch (Exception e) {
            System.out.println("ip: "+ip+":"+port+" НЕ РАБОТАЕТ");
        }
    }
}