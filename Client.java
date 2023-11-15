import java.io.*;
import java.net.*;
import java.util.*;

public class CalcClientEx {
    public static void main(String[] args) {
        try {
            // Create File Input Stream Objects
            FileInputStream inner = new FileInputStream("server_info.dat");

            // Create an array to store daily data (automatically initialized to zero)
            String OutputString = "";
            byte arr[] = new byte[16];
            while (true) {            
                int num = inner.read(arr);
                if (num < 0)               
                    break;            
                for (int cnt = 0; cnt < num; cnt++){
                    int value = arr[cnt] & 0xff; // Convert bytes to int
                    char charact = (char) value;
                    OutputString += Character.toString(charact);
                }                           
                
            }
            String[] array = OutputString.split(" ");
            array[0]=array[0].substring(1);
        BufferedReader in = null;
        BufferedWriter out = null;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket(array[0], Integer.parseInt(array[2]));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                System.out.print("Calculation expression (enter in blanks, e.g. 24 + 42)>>"); // prompt
                String outputMessage = scanner.nextLine(); // Read formula from keyboard
                if (outputMessage.equalsIgnoreCase("bye")) {
                    out.write(outputMessage + "\n"); // Send "bye" string
                    out.flush();
                    break; // If the user enters "bye", send it to the server and terminate the connection
                }
                out.write(outputMessage + "\n"); // Send formula string read from keyboard
                out.flush();
                String inputMessage = in.readLine(); // Receive calculation results from the server
                System.out.println("Calculation Results : " + inputMessage);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                scanner.close();
                if (socket != null)
                    socket.close(); // Close Client Socket
            } catch (IOException e) {
                System.out.println("An error occurred while chatting with the server.");
            }
        }
            // Close File Input Stream
            // Release file stream buffers, disconnect process from OS
        inner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}