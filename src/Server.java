import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[256];

    private Server(DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend(){
        while(true){
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
                datagramSocket.receive(datagramPacket);

                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                String messageFromClient  = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Message from client: " + messageFromClient);

                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                datagramSocket.send(datagramPacket);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }


    public static void main(String[] args) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        Server server = new Server(datagramSocket);
        server.receiveThenSend();

    }
}
