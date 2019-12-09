package chatgui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author pavel.lucian
 */
public class ChatGui extends JFrame implements ActionListener {

    private static DatagramSocket socket;
    private static String IP_address;
    private static InetAddress address;
    private static int UDP_port;
    private String username;

    JPanel ContenutoInvio = new JPanel();

    private static JTextArea areaChat = new JTextArea();
    JScrollPane scroll = new JScrollPane(areaChat); 

    JTextField messaggioField = new JTextField("Scrivi il tuo messaggio qui");
    JButton invia = new JButton("Invia");

    public ChatGui() throws InterruptedException {

        this.setTitle("Chat di gruppo");
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new GridLayout(2, 1));

        ContenutoInvio.setLayout(new GridLayout(1, 2));
        ContenutoInvio.setBorder(new EmptyBorder(60, 20, 60, 20)); 
        messaggioField.setBorder(new EmptyBorder(10, 10, 10, 10)); 
        ContenutoInvio.add(messaggioField);
        ContenutoInvio.add(invia);
        areaChat.setEditable(false); 
        areaChat.setBorder(new EmptyBorder(20, 20, 20, 20)); 
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
        this.add(scroll);
        this.add(ContenutoInvio);

       username = JOptionPane.showInputDialog(null, "Inserisci username");

        invia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                inviaPacchetto(messaggioField.getText(), username);
                messaggioField.setText("");

            }
        });

        Thread ascolta = new Thread() {
            public void run() {
                riceviPacchetto();
            }
        };
        ascolta.start(); 

    }

    public static void inviaPacchetto(String messaggio, String username) {
        byte[] buffer;
        DatagramPacket userDatagram;

        try {
            messaggio = username.concat(" " + messaggio);

            buffer = messaggio.getBytes("UTF-8");

            userDatagram = new DatagramPacket(buffer, buffer.length, address, UDP_port);
            socket.send(userDatagram);
        } catch (IOException ex) {
            Logger.getLogger(ChatGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void riceviPacchetto() {
        byte[] buffer = new byte[100];
        String received;
        DatagramPacket serverDatagram;

        try {
            serverDatagram = new DatagramPacket(buffer, buffer.length);
            while (!Thread.interrupted()) {
                socket.receive(serverDatagram);  
                received = new String(serverDatagram.getData(), 0, serverDatagram.getLength(), "ISO-8859-1");
                areaChat.append(received + "\n");
                areaChat.setCaretPosition(areaChat.getDocument().getLength());
            }
            socket.close();

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ChatGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChatGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws UnknownHostException, SocketException {

        address = InetAddress.getByName(IP_address);
        UDP_port = 1077;

        socket = new DatagramSocket();

        Runnable r = new Runnable() {
            public void run() {
                try {
                    new ChatGui().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ChatGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        EventQueue.invokeLater(r);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
