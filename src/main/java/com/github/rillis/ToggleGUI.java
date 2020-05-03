package com.github.rillis;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class ToggleGUI extends JFrame {

	private JPanel contentPane;
	static InetAddress google = null;
	JLabel lblOn = null;
	static boolean conectado = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						google = InetAddress.getByName("www.google.com");
					} catch (UnknownHostException e1) {
						conectado = false;
						System.out.println("N�o conectado");
					}
					ToggleGUI frame = new ToggleGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ToggleGUI() {
		setTitle("Network Toggle");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblOn = new JLabel();
		lblOn.setOpaque(true);
		if(conectado) {
			lblOn.setBackground(Color.GREEN);
		}else {
			lblOn.setBackground(Color.RED);
		}
		lblOn.setHorizontalAlignment(SwingConstants.CENTER);
		lblOn.setBounds(10, 32, 414, 14);
		contentPane.add(lblOn);
		
		JButton btOff = new JButton("set off");
		btOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblOn.setBackground(Color.YELLOW);
				turnOff();
				
			}
		});
		btOff.setBounds(30, 74, 159, 91);
		contentPane.add(btOff);
		
		JButton btOn = new JButton("set on");
		btOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblOn.setBackground(Color.YELLOW);
				turnOn();
			}
		});
		btOn.setBounds(241, 74, 159, 91);
		contentPane.add(btOn);
	}
	
	public void turnOff() {
		new Thread(){
			public void run() {
				try {
					Runtime.getRuntime().exec("cmd /c ipconfig /release");
					
					Thread.sleep(500);
					
					while(google.isReachable(500)) {
						Runtime.getRuntime().exec("cmd /c ipconfig /release");
					}
					
					lblOn.setBackground(Color.RED);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
			
	}
	
	public void turnOn() {
		new Thread(){
			public void run() {
				try {
					Runtime.getRuntime().exec("cmd /c ipconfig /renew");
					
					Thread.sleep(500);
					
					while(!google.isReachable(500)) {
						Runtime.getRuntime().exec("cmd /c ipconfig /renew");
					}
					
					lblOn.setBackground(Color.GREEN);
					
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}.start();
		
	}
	
	public void teste(String str) throws IOException{
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(str);

		BufferedReader stdInput = new BufferedReader(new 
		     InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new 
		     InputStreamReader(proc.getErrorStream()));
		
		System.out.println("Here is the standard output of the command:\n");
		String s = null;
		while ((s = stdInput.readLine()) != null) {
		    System.out.println(s);
		}

		// Read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
		    System.out.println(s);
		}
	}
}
