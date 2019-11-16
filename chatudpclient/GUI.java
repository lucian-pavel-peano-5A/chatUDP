/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatudpclient;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author pavel.lucian
 */
public class GUI extends JFrame implements ActionListener {
    private static DatagramSocket socket;
    private static String IP_address;
    private static InetAddress address;
    private static int UDP_port;
    private String username;
 
    
    JPanel pDati = new JPanel(new GridLayout(8, 2));
    JLabel JTitolo = new JLabel("Chat");
    JLabel JUser = new JLabel("Inserisci il tuo username");
    JLabel JSpazio = new JLabel("");
    JTextField JUserName = new JTextField("");
    JButton JSave = new JButton("Start");
    JTextArea JAreaChat = new JTextArea("");

    public GUI() {
        this.setVisible(true);
        this.setLayout(new GridLayout(2, 3));

        this.add(pDati);
        this.add(JTitolo);
        this.add(JSpazio);
        this.add(JUser);
        this.add(JUserName);
        this.add(JSave);

        this.setSize(650, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setVisible(true);

                JSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                
            }
        });

    }

                public static void Apri()
            {
                Runnable r = new Runnable() {
            public void run() {
                new GUI();
            }
        }; EventQueue.invokeLater(r);

        try {
            
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Gui2();
            }
        });    
}catch (Exception ex) {
            System.out.println(ex.toString());
        }
            }
}
