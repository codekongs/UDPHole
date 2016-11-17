import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPServer {
    public static void main(String[] args) {
        try {
            
            String serverPort = args[0];
            DatagramSocket server = new DatagramSocket(Integer.parseInt(serverPort));

            DatagramPacket packetFromClient = null;

            String sendMessageA = "";
            String sendMessageB = "";
            int portA = 0;
            int portB = 0;
            InetAddress addressA = null;
            InetAddress addressB = null;
            while(true) {
                packetFromClient = UDPUtil.receiveMsg(server);

                String msgFromClient = new String(packetFromClient.getData(), 0, packetFromClient.getLength());

                if (msgFromClient.contains("clientA")) {
                    portA = packetFromClient.getPort();
                    addressA = packetFromClient.getAddress();
                    sendMessageA = "host:" + addressA.getHostAddress() + ",port:" + portA;
                }

                if (msgFromClient.contains("clientB")) {
                    portB = packetFromClient.getPort();
                    addressB = packetFromClient.getAddress();
                    sendMessageB = "host:" + addressB.getHostAddress() + ",port:" + portB;
                }

                if (!sendMessageA.equals("") && !sendMessageB.equals("")) {
                    sendTo(addressA, portA, sendMessageB, server);
                    sendTo(addressB,  portB, sendMessageA, server);
                    sendMessageA = "";
                    sendMessageB = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void sendTo(InetAddress address, int port, String sendMessage, DatagramSocket server) {
        try {
            byte[] sendBuf = sendMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            server.send(sendPacket);
            System.out.println("A B change");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
