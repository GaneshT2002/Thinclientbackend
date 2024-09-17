package com.example.Thin.Service;



import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


import org.springframework.stereotype.Service;

@Service
public class PrinterService {


	    private static String printerIp=Service_properties.printer_ip;
	   
	    private static int printerPort=Service_properties.printer_port;
	    
	public static void ans() {
		System.out.println(printerIp);
	}

    
    public static boolean printReceipt(String text) {
        try {
            Socket socket = new Socket(printerIp, printerPort);
            OutputStream outputStream = socket.getOutputStream();

            
            byte[] initPrinter = new byte[]{0x1B, 0x40}; 

            
            String receiptText = text+"\n\n\n\n";
            outputStream.write(receiptText.getBytes(StandardCharsets.US_ASCII));

            
            byte[] cutPaper = new byte[]{0x1D, 'V', 1};
            outputStream.write(cutPaper);

            outputStream.flush();
            outputStream.close();
            socket.close();

            System.out.println("Receipt printed successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Unable to print the receipt.");
            return false;
        }
    }


    
    public boolean openCashDrawer() {
    	boolean flag = false;
    	
    		
    	
        try (Socket socket = new Socket(printerIp, printerPort);
             OutputStream outputStream = socket.getOutputStream()) {

           
            byte[] openDrawer = new byte[]{0x1B, 0x70, 0x00, 0x19, (byte) 0xFA};
            outputStream.write(openDrawer);

            outputStream.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}