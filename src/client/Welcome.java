package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

import View.MapEditor;

/**
 * Welcome is the main GUIinterface user will see in the first place
 * It contains button jumping to single player mode game, multiplayer mode game,
 * the map editor, the help sessage, the settings and the exit button
 * @author Minghao Zhu
 *
 */
public class Welcome extends JFrame{


	/**
	 * generated serial version
	 */
	private static final long serialVersionUID = 1L;

	/** a main container*/
    public Container content_pane;
    
    /** JPanel title on top of gui**/
    public JPanel title;
    /** JLabel title_label showing the title**/
    public JLabel title_label;
    /** JPanel holding buttons**/
    public JPanel buttons;
    /** JPanel top panel for top button **/
    public JPanel top_panel;
    /** JPanel bot panel for bot button **/
    public JPanel bot_panel;
    
    /** image icon for title**/
    public ImageIcon title_img;
    /** image icon for single player mode**/
    public ImageIcon single_img;
    /** image icon for mutliplayer mode**/
    public ImageIcon multi_img;
    /** image icon for settings**/
    public ImageIcon setting_img;
    /** image icon for settings2**/
    public ImageIcon setting_img2;
    /** image icon for help**/
    public ImageIcon help_img;
    /** image icon for exit**/
    public ImageIcon exit_img;
    /** image icon for space**/
    public ImageIcon space_img;
    /** image icon for map**/
    public ImageIcon map_img;

    /** single player mode button**/
    public JButton single_player_bu;
    /** multi player mode button**/
    public JButton multi_player_bu;
    /** settings button**/
    public JButton setting_bu;
    /** help button**/
    public JButton help_bu;
    /** exit button**/
    public JButton exit_bu;
    /** map editor button**/
    public JButton map_bu;
    /** UIColor**/
    public Color UICOLOR;
    /** a file holding map path for single player mode**/
    public File Map = new File("docs/map2.txt");
    
    /**
     * constructor for Welcome
     * Construct all components of welcome board
     */
	public Welcome() {
		content_pane = this.getContentPane();
		title = new JPanel();
		buttons = new JPanel();
		bot_panel = new JPanel();
		top_panel = new JPanel();
		
		title_img = new ImageIcon("./docs/title.jpg");
		single_img = new ImageIcon("./docs/single.jpg");
		multi_img = new ImageIcon("./docs/multi.jpg");
		setting_img = new ImageIcon("./docs/setting.jpg");
		setting_img2 = new ImageIcon("./docs/setting2.jpg");
		
		help_img = new ImageIcon("./docs/help.jpg");
		exit_img = new ImageIcon("./docs/exit.jpg");
		space_img = new ImageIcon("./docs/space.jpg");
		map_img = new ImageIcon("./docs/map.jpg");
		
		single_player_bu = new JButton();
		multi_player_bu = new JButton();
		setting_bu = new JButton();
		exit_bu = new JButton();
		help_bu = new JButton();
		map_bu = new JButton();
		
		title_label = new JLabel();
		
		UICOLOR = new Color(29, 62, 66);
				
	}
	
	/**
	 * a function to set up GUI 
	 */
	public void setUpGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    	content_pane.setLayout(new BorderLayout());
    	content_pane.setBackground(UICOLOR);
    	setMinimumSize(new Dimension(1200, 800));
    	
    	//title
    	content_pane.add(title, BorderLayout.NORTH);
    	title.add(title_label);
    	title.setBackground(UICOLOR);
    	title_img.setImage(title_img.getImage().getScaledInstance(500, 300,Image.SCALE_DEFAULT ));
    	title_label.setIcon(title_img);
    	
    	
    	//buttons
    	content_pane.add(buttons,BorderLayout.CENTER);
    	buttons.setLayout(new BorderLayout());
    	
    	buttons.setBackground(UICOLOR);
    	
    	buttons.add(top_panel,BorderLayout.NORTH);
    	top_panel.setBackground(UICOLOR);
    	top_panel.add(single_player_bu);
    	top_panel.add(multi_player_bu);
    	
    	//bot_panel
    	buttons.add(bot_panel,BorderLayout.SOUTH);
    	bot_panel.setBackground(UICOLOR);
    	bot_panel.add(map_bu);
    	bot_panel.add(setting_bu);
    	bot_panel.add(help_bu);
    	bot_panel.add(exit_bu);

    	
    	JPanel space = new JPanel();
    	content_pane.add(space, BorderLayout.SOUTH);
    	space.setBackground(UICOLOR);
    	JLabel space_bu = new JLabel(space_img);
    	space_img.setImage(space_img.getImage().getScaledInstance(400, 150,Image.SCALE_DEFAULT ));
    	space.add(space_bu);
    	space.setEnabled(false);
    	pack();
    	setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
    	
	}
	
	/**
	 * a function to set up onclick listener for all buttons
	 */
	public void setUpButton() {
		
		//single player button
		single_img.setImage(single_img.getImage().getScaledInstance(400, 120,Image.SCALE_DEFAULT ));
		single_player_bu.setIcon(single_img);
		single_player_bu.addActionListener(new ActionListener() {
            //jump to single player game when onclick
            @Override
            public void actionPerformed(ActionEvent e) {
 
        		single_player_bu.setEnabled(false);
        		multi_player_bu.setEnabled(false);
        		exit_bu.setEnabled(false);
        		map_bu.setEnabled(false);
        		setting_bu.setEnabled(false);
        		help_bu.setEnabled(false);
        		exit_bu.setEnabled(false);
        		
        		// a new thread to run the game
				Thread t = new Thread(new Runnable() {
					public void run() {
		        		Main main = new Main(Map);	
		        		main.StartGame(); 
		        		single_player_bu.setEnabled(true);
		        		multi_player_bu.setEnabled(true);
		        		map_bu.setEnabled(true);
		        		setting_bu.setEnabled(true);
		        		help_bu.setEnabled(true);
		        		exit_bu.setEnabled(true);
					}
				});
				t.start();
   
            }
		});
		
		//multi-player button
		multi_img.setImage(multi_img.getImage().getScaledInstance(400, 120,Image.SCALE_DEFAULT ));
		multi_player_bu.setIcon(multi_img);
		multi_player_bu.addActionListener(new ActionListener() {
	        //jump to multi-player game when onclick
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	final JDialog dialog = new JDialog(Welcome.this, "Connect to server", true);
	            dialog.setSize(300, 200);
	            dialog.setResizable(false);
	            dialog.setLocationRelativeTo(Welcome.this);
	            
	            JPanel multi_panel = new JPanel();
	    		multi_panel.setLayout(new GridLayout(3,2));
	    		
	        	JLabel ip_address = new JLabel("ip address：");
	        	JLabel port_num = new JLabel("port："); 
	        	JTextField ip_entry = new JTextField(10);
	        	JTextField port_entry = new JTextField(10);
	        	
	    		ip_address.setHorizontalAlignment(SwingConstants.RIGHT);
	    		port_num.setHorizontalAlignment(SwingConstants.RIGHT);
	    		
	            JButton ok_bu = new JButton("ok");
	            ok_bu.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                	//TODO: jump to multiplayer mode
	                	String ip = ip_entry.getText();
	                	String port = port_entry.getText();
	                	int port_num = 0;
	                	boolean good_input = true;
	                	try{
	                		port_num = Integer.valueOf(port);
	                		System.out.println(port_num);
	                	}catch(Exception ex) {
	                		good_input = false;
	                	}
	                	
	        			if(good_input && port_num!=0){
	        				//Start multiplayer game
	        			}else {
	        				JOptionPane.showMessageDialog(null, "invalid entry, try again");
	        				ip_entry.setText(""); 
	        				port_entry.setText("");
	        			}

	                }
	            });
	            
	            JButton cancel_bu = new JButton("cancel");
	            cancel_bu.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    dialog.dispose();
	                }
	            });
	            
	            multi_panel.add(ip_address);
	            multi_panel.add(ip_entry);  
	            multi_panel.add(port_num);
	            multi_panel.add(port_entry);  
	            multi_panel.add(cancel_bu);
	            multi_panel.add(ok_bu);
	            
	            dialog.add(multi_panel, BorderLayout.CENTER);
	            dialog.setContentPane(multi_panel);
	            dialog.setVisible(true);
	        }
		});
		
		//settings button
		setting_img.setImage(setting_img.getImage().getScaledInstance(200, 80,Image.SCALE_DEFAULT ));
		setting_bu.setIcon(setting_img);
		setting_bu.addActionListener(new ActionListener() {
	        //jump to sdettings when onclick
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	final JDialog dialog = new JDialog(Welcome.this, "Settings", true);
	            dialog.setSize(250, 120);
	            dialog.setResizable(false);
	            dialog.setLocationRelativeTo(Welcome.this);
	            
	            JButton set_file_bu = new JButton("");
	            setting_img2.setImage(setting_img2.getImage().getScaledInstance(250, 120,Image.SCALE_DEFAULT ));
	            set_file_bu.setIcon(setting_img2);
	            set_file_bu.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                	int select = JOptionPane.showConfirmDialog(dialog, "please select local map file", "set new map", JOptionPane.ERROR_MESSAGE);
	                	if( select == JOptionPane.YES_OPTION){
	                        JFileChooser fileChooser = new JFileChooser();
	                        fileChooser.setCurrentDirectory(new File("."));
	                        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	                        fileChooser.setMultiSelectionEnabled(false);
	                        int value = fileChooser.showOpenDialog(null);
	                        if(value == JFileChooser.APPROVE_OPTION) {
	                        	Map = fileChooser.getSelectedFile();
	                        }
	                	}
	                	dialog.dispose();
	                }
	                	
	            });
	            JPanel panel = new JPanel();
	            panel.setLayout(new BorderLayout());
	            panel.add(set_file_bu,BorderLayout.CENTER);
	            dialog.setContentPane(panel);
	            dialog.setVisible(true);
	        }
		});
		
		//exit button
		exit_img.setImage(exit_img.getImage().getScaledInstance(200, 80,Image.SCALE_DEFAULT ));
		exit_bu.setIcon(exit_img);
		exit_bu.addActionListener(new ActionListener() {
	        //exit the game when onclick
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	System.exit(0);
	        }
		});
		
		//help button
		help_img.setImage(help_img.getImage().getScaledInstance(200, 80,Image.SCALE_DEFAULT ));
		help_bu.setIcon(help_img);
		help_bu.addActionListener(new ActionListener() {
	        //present the help message the game when onclick
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            final JDialog dialog = new JDialog(Welcome.this, "Help message", true);
	            dialog.setSize(600, 600);
	            dialog.setResizable(false);
	            dialog.setLocationRelativeTo(Welcome.this);

	            JLabel message1 = new JLabel("Click on Single Player Mode or Multiplayer Mode to start a game."); 
	            JLabel message2 = new JLabel("Press 'w' 's' 'a' 'd' or '↑' '↓' '←' '→' on keyboard to control the tank.");
	            JLabel message3 = new JLabel("Left-click the mouse to shoot a bullet.");
	            JLabel message4 = new JLabel("You can use the map editor to create a new map and set the new map in settings.");
	            JButton okBtn = new JButton("OK");
	            okBtn.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    dialog.dispose();
	                }
	            });
	            JPanel panel = new JPanel();
	            panel.setLayout(new BorderLayout());
	            JPanel panel2 = new JPanel();
	            BoxLayout boxlayout=new BoxLayout(panel2, BoxLayout.Y_AXIS);
	    	    panel2.setLayout(boxlayout);
	    	    panel.add(panel2,BorderLayout.CENTER);
	    	    panel2.add(Box.createRigidArea(new Dimension(25, 25)));
	            panel2.add(message1);
	            panel2.add(Box.createRigidArea(new Dimension(25, 25)));
	            panel2.add(message2);
	            panel2.add(Box.createRigidArea(new Dimension(25, 25)));
	            panel2.add(message3);
	            panel2.add(Box.createRigidArea(new Dimension(25, 25)));
	            panel2.add(message4);
	            
	            panel.add(okBtn,BorderLayout.SOUTH);

	            dialog.setContentPane(panel);
	            dialog.setVisible(true);
	        }
		});
		
		map_img.setImage(map_img.getImage().getScaledInstance(200, 80,Image.SCALE_DEFAULT ));
		map_bu.setIcon(map_img);
		map_bu.addActionListener(new ActionListener() {
            //jump to map editor when onclick
            @Override
            public void actionPerformed(ActionEvent e) {
 
        		single_player_bu.setEnabled(false);
        		multi_player_bu.setEnabled(false);
        		exit_bu.setEnabled(false);
        		map_bu.setEnabled(false);
        		setting_bu.setEnabled(false);
        		help_bu.setEnabled(false);
        		exit_bu.setEnabled(false);
        		//run the map editor in a new thread	
				Thread t = new Thread(new Runnable() {
					public void run() {
				        MapEditor gui2 = new MapEditor();
				        gui2.setVisible(true);
		        		single_player_bu.setEnabled(true);
		        		multi_player_bu.setEnabled(true);
		        		map_bu.setEnabled(true);
		        		setting_bu.setEnabled(true);
		        		help_bu.setEnabled(true);
		        		exit_bu.setEnabled(true);
					}
				});
				t.start();

            }
		});
		
	}
	
	/**
	 * the main function
	 * @param args commandline args
	 */
	public static void main(String[] args) {
		Welcome gui = new Welcome();
        gui.setVisible(true);
       	gui.setUpGUI();
       	gui.setUpButton();
	}
	
	

}
