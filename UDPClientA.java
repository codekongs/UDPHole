import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class UDPClientA {
    public static void main(String[] args){
        
        String serverIP = args[0];
        
        String serverPort = args[1];
        
        int localPort = 60000;
        try {
            
            DatagramSocket clientA = new DatagramSocket(localPort);

            
            String helloMsg = "I am clientA";
            UDPUtil.sendMsg(helloMsg, serverIP, serverPort, clientA);

            
            DatagramPacket packetFromServer = UDPUtil.receiveMsg(clientA);
            String msgFromServer = new String(packetFromServer.getData(),
                    0, packetFromServer.getLength());
            String[] ipAndPort = UDPUtil.convertStrToIPAndPort(msgFromServer);
            while (true){
                
                UDPUtil.sendMsg("AAAA", ipAndPort[0], ipAndPort[1], clientA);
                DatagramPacket msgFromClientB = UDPUtil.receiveMsg(clientA);
                System.out.println(msgFromClientB.getData().toString());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
