import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class StartFrame extends JFrame {

	private JPanel contentPane;
	public static String msg = null;
	public static String grammar;
	public static String grammarSave;
	public static Hashtable<String, Vector<Vector<String>>> grammarTable = new Hashtable<String,Vector<Vector<String>>>();
	public static String firstKey;
	private boolean firstClick = false;
	protected static boolean jTActive = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame = new StartFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.gc();
	}

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, screenSize.width, screenSize.height);
		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setTitle("Simplficateur de Grammaire");
		URL iconURL = getClass().getResource("/icon.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Run");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmExit);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAide = new JMenuItem("Aide");
		mntmAide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Ce logiciel est un simplificateur de grammaire.\n"
						+ "Il est compose de 4 algorithme : \n"
						+ "- 1 Algorithme : Elimination des symboles inutiles\n"
						+ "- 2 Algorithme : Suppression des epsilon productions\n"
						+ "- 3 Algorithme : Elimination des productions unitaires\n"
						+ "- 4 Algorithme : La forme normale de Chomsky", 
		    			"Aide", 
		  			  JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnAbout.add(mntmAide);
		
		JMenuItem mntmVersion = new JMenuItem("Version");
		mntmVersion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Realise par Mohamed Hassine Ben Taieb\n"
						+ "18/03/2014 v 2.0", 
		    			"Version", 
		  			  JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnAbout.add(mntmVersion);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Label label = new Label("Veuillez saisir votre grammaire \u00E0 simplifier : ");
		label.setForeground(Color.GREEN);
		label.setFont(new Font("Dialog", Font.BOLD, 17));
		label.setBounds(10, 10, 674, 32);
		contentPane.add(label);
		
		final JTextArea textArea = new JTextArea();
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!firstClick){
					textArea.setText(null);
					textArea.setEditable(true);
					textArea.setFont(new Font("Monospaced", Font.BOLD, 16));
					firstClick = true;
				}
			}
		});
		JScrollPane tA = new JScrollPane (textArea);
		textArea.setFont(new Font("Myriad Web Pro", Font.PLAIN, 12));
		tA.setBounds(10, 58, 1346, 203);
		contentPane.add(tA);
		textArea.setEditable(false);
		textArea.setText("{exemple pour demontrer comment enter votre grammaire}\n"
				+ "S->AB|CA\n"
				+ "A->a\n"
				+ "B->AB|EA\n"
				+ "C->aB|b\n"
				+ "D->aC\n"
				+ "E->BA\n"
				+ "{choisissez un algorithme à executer à la fois sinon de tout executer en même temps}");
		
		int posJT = 10;
		
		final JTextArea textArea_1 = new JTextArea();
		JScrollPane tA_1 = new JScrollPane (textArea_1);
		textArea_1.setFont(new Font("Monospaced", Font.BOLD, 16));
		tA_1.setBounds(10, 272, 329, 289);
		contentPane.add(tA_1);
		textArea_1.setEditable(false);
		
		posJT = posJT + 10 + (screenSize.width-50)/4;
		
		final JTextArea textArea_2 = new JTextArea();
		JScrollPane tA_2 = new JScrollPane (textArea_2);
		textArea_2.setFont(new Font("Monospaced", Font.BOLD, 16));
		tA_2.setBounds(349, 272, 329, 289);
		contentPane.add(tA_2);
		textArea_2.setEditable(false);
		
		posJT = posJT + 10 + (screenSize.width-50)/4;
		
		final JTextArea textArea_3 = new JTextArea();
		JScrollPane tA_3 = new JScrollPane (textArea_3);
		textArea_3.setFont(new Font("Monospaced", Font.BOLD, 16));
		tA_3.setBounds(688, 272, 329, 289);
		contentPane.add(tA_3);
		textArea_3.setEditable(false);
		
		posJT = posJT + 10 + (screenSize.width-50)/4;
		
		final JTextArea textArea_4 = new JTextArea();
		JScrollPane tA_4 = new JScrollPane (textArea_4);
		textArea_4.setFont(new Font("Monospaced", Font.BOLD, 16));
		tA_4.setBounds(1027, 272, 329, 289);
		contentPane.add(tA_4);
		textArea_4.setEditable(false);
		
		int posJB = 10+(screenSize.width-50)/8-55;
		
		final JButton btnNewButton = new JButton("Algorithme 1");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_1.setText(null);
				checkJTActive();
				try {
					new Algorithm1();
					textArea_1.append(msg);
				} catch (Exception e) {
					textArea_1.append(e.getMessage());
				}
				affichage(textArea_1);
				jTActive = true;
			}
		});
		btnNewButton.setBounds(posJB, 573, 110, 23);
		contentPane.add(btnNewButton);
		
		posJB = posJB + 10+(screenSize.width-50)/4;
		
		final JButton btnNewButton_1 = new JButton("Algorithme 2");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_2.setText(null);
				checkJTActive();
				try {
					new Algorithm2();
					textArea_2.append(msg);
				} catch (CustomException e1) {
					textArea_2.append(e1.getMessage());
				}
				affichage(textArea_2);
				jTActive = true;
			}
		});
		btnNewButton_1.setBounds(posJB, 573, 110, 23);
		contentPane.add(btnNewButton_1);
		
		posJB = posJB + 10+(screenSize.width-50)/4;
		
		final JButton btnNewButton_2 = new JButton("Algorithme 3");
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_3.setText(null);
				checkJTActive();
				try {
					new Algorithm3();
					textArea_3.append(msg);
				} catch (CustomException e1) {
					textArea_3.append(e1.getMessage());
				}
				affichage(textArea_3);
				jTActive = true;
			}
		});
		btnNewButton_2.setBounds(posJB, 573, 110, 23);
		contentPane.add(btnNewButton_2);
		
		posJB = posJB + 10+(screenSize.width-50)/4;
		
		final JButton btnNewButton_3 = new JButton("Algorithme 4");
		btnNewButton_3.setEnabled(false);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_4.setText(null);
				checkJTActive();
				try {
					new Algorithm4();
					textArea_4.append(msg);
				} catch (CustomException e1) {
					textArea_4.append(e1.getMessage());
				}
				affichage(textArea_4);	
				jTActive = true;
			}
		});
		btnNewButton_3.setBounds(posJB, 573, 110, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Sortir");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton_4.setBounds(1236, 643, 120, 23);
		contentPane.add(btnNewButton_4);
		
		final JButton btnNewButton_5 = new JButton("Executer Tout");
		btnNewButton_5.setEnabled(false);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_1.setText(null);
				textArea_2.setText(null);
				textArea_3.setText(null);
				textArea_4.setText(null);
				try {
					checkJTActive();
					new Algorithm1();
					textArea_1.append(msg);
					affichage(textArea_1);
					jTActive = true;
				} catch (Exception e1) {
					textArea_1.append(e1.getMessage());
				}
				finally{
					try {
						checkJTActive();
						new Algorithm2();
						textArea_2.append(msg);
						affichage(textArea_2);
						jTActive = true;
					} catch (CustomException e1) {
						textArea_2.append(e1.getMessage());
					}
					finally{
						try {
							checkJTActive();
							new Algorithm3();
							textArea_3.append(msg);
							affichage(textArea_3);
							jTActive = true;
						} catch (CustomException e1) {
							textArea_3.append(e1.getMessage());
						}
						finally{
							try {
								checkJTActive();
								new Algorithm4();
								textArea_4.append(msg);
								affichage(textArea_4);
								jTActive = true;
							} catch (CustomException e1) {
								textArea_4.append(e1.getMessage());
							}
						}	
					}
				}
			}
		});
		btnNewButton_5.setBounds(1106, 643, 120, 23);
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Charger");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grammarTable.clear();
				textArea_1.setText(null);
				textArea_2.setText(null);
				textArea_3.setText(null);
				textArea_4.setText(null);
				grammar = textArea.getText();
				grammarSave = new String(grammar);
				jTActive = false;
				try {
					SyntaxAnalyser.main();
					btnNewButton.setEnabled(true);
					btnNewButton_1.setEnabled(true);
					btnNewButton_2.setEnabled(true);
					btnNewButton_3.setEnabled(true);
					btnNewButton_5.setEnabled(true);
				} catch (Exception e1) {
					textArea.setText(e1.getMessage());
					
				}
			}
		});
		btnNewButton_6.setBounds(976, 643, 120, 23);
		contentPane.add(btnNewButton_6);
	}
	
	public static void affichage(JTextArea JT){
		Map map = new TreeMap(grammarTable);
		Vector<Vector<String>> firstValue = (Vector<Vector<String>>) map.get(firstKey);
		for(int k = 0;k<firstValue.size();k++){
			JT.append(firstKey + "->");
		    Vector<String> tempVector2 = firstValue.elementAt(k);
			for(int j = 0;j<tempVector2.size();j++){
				JT.append(tempVector2.elementAt(j));
				if(j+1 !=tempVector2.size())
					JT.append("|");
				else
					JT.append("\n");
				}
		   	}
		Set keys = map.keySet();
		for (Iterator i = keys.iterator(); i.hasNext();) {
		    String key = (String) i.next();
		    Vector<Vector<String>> value = (Vector<Vector<String>>) map.get(key);
		    for(int k = 0;k<value.size();k++){
			    if(key != firstKey){
			    	JT.append(key + "->");
				   Vector<String> tempVector2 = value.elementAt(k);
				   for(int j = 0;j<tempVector2.size();j++){
					   JT.append(tempVector2.elementAt(j));
					   if(j+1 !=tempVector2.size())
						   JT.append("|");
					   else
						   JT.append("\n");
				   }
			    }
		      }
		   }	
	}
	
	public void checkJTActive(){
		if(jTActive){
			grammarTable.clear();
			try {
				SyntaxAnalyser.main();
			} catch (CustomException e) {
				e.printStackTrace();
			}
			
		}
	}
}
