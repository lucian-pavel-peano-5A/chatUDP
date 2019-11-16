/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatudpclient;

/**
 *
 * @author pavel.lucian
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author pavel.lucian
 */
public class Gui2 extends JFrame implements ActionListener {

    private static DatagramSocket socket;
    private static String IP_address;
    private static InetAddress address;
    private static int UDP_port;
    private String username;
    
    
    JPanel pDati = new JPanel(new GridLayout(8, 2));
    JLabel JTitolo = new JLabel("Chat");
    JLabel JUser = new JLabel("scrivi qui");
    JLabel JSpazio = new JLabel("");
    JTextField JInvioMessaggio = new JTextField("");
    JButton JInvio = new JButton("Invio");
    JTextArea JAreaChat = new JTextArea("");
    JScrollPane scroll = new JScrollPane(JAreaChat);

    public Gui2() {
        this.setVisible(true);
        this.setLayout(new GridLayout(2, 3));
        this.setTitle("Chat di gruppo");
        this.add(pDati);
        this.add(JTitolo);
        this.add(JSpazio);
        this.add(JAreaChat);
        this.add(JUser);
        this.add(JInvioMessaggio);
        this.add(JInvio);

        this.setSize(650, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scroll);
        pack();
        setVisible(true);

        JInvio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                inviaPacchetto(JInvioMessaggio.getText());
                JInvioMessaggio.setText("");

            }

            private void inviaPacchetto(String messaggio, String username) {
                byte[] buffer;
                DatagramPacket userDatagram;

                try {

                    buffer = messaggio.getBytes("UTF-8");

                    userDatagram = new DatagramPacket(buffer, buffer.length, address, UDP_port);
                    socket.send(userDatagram);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void inviaPacchetto(String text) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        Thread ascolta = new Thread() {
	public void run() {
                  riceviPacchetto();
            }

            private void riceviPacchetto() {
               byte[] buffer = new byte[100];
        String received;
        DatagramPacket serverDatagram;

        try {
           
            serverDatagram = new DatagramPacket(buffer, buffer.length);
            while (!Thread.interrupted()){
                socket.receive(serverDatagram); 
                received = new String(serverDatagram.getData(), 0, serverDatagram.getLength(), "ISO-8859-1");
                JAreaChat.append(received+"\n");
                JAreaChat.setCaretPosition(JAreaChat.getDocument().getLength());
            }
            socket.close();

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Gui2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Gui2.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
	};	
        ascolta.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
