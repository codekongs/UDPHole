import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class UDPClientB {
    public static void main(String[] args){
        
        String serverIP = args[0];
        
        String serverPort = args[1];

        int localPort = 60001;
        try {
            DatagramSocket clientB = new DatagramSocket(60001);
            String helloMsg = "I am clientB";
            
            UDPUtil.sendMsg(helloMsg, serverIP, serverPort, clientB);

            
            DatagramPacket packetFromServer = UDPUtil.receiveMsg(clientB);
            String msgFromServer = new String(packetFromServer.getData(),
                    0, packetFromServer.getLength());
            String[] ipAndPort = UDPUtil.convertStrToIPAndPort(msgFromServer);
            while (true){
                
                UDPUtil.sendMsg("BBBB", ipAndPort[0], ipAndPort[1], clientB);
                DatagramPacket msgFromClientA = UDPUtil.receiveMsg(clientB);
                System.out.println(msgFromClientA.getData().toString());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
