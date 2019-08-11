package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Collator;

import javax.swing.*;

public class Welcome extends JFrame{


	/** a main container*/
    public Container content_pane;
    
    public JPanel title;
    public JLabel title_label;
    public JPanel buttons;
    public JPanel top_panel;
    public JPanel bot_panel;
    
    
    public ImageIcon title_img;
    public ImageIcon single_img;
    public ImageIcon multi_img;
    public ImageIcon setting_img;
    public ImageIcon help_img;
    public ImageIcon exit_img;
    public ImageIcon space_img;

    
    public JButton single_player_bu;
    public JButton multi_player_bu;
    public JButton setting_bu;
    public JButton help_bu;
    public JButton exit_bu;
    
    public Color UICOLOR;
    
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
		help_img = new ImageIcon("./docs/help.jpg");
		exit_img = new ImageIcon("./docs/exit.jpg");
		space_img = new ImageIcon("./docs/space.jpg");
		
		single_player_bu = new JButton();
		multi_player_bu = new JButton();
		setting_bu = new JButton();
		exit_bu = new JButton();
		help_bu = new JButton();
		
		title_label = new JLabel();
		
		UICOLOR = new Color(29, 62, 66);
		
		
	}
	
	public void setUpGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    	content_pane.setLayout(new BorderLayout());
    	content_pane.setBackground(UICOLOR);
    	
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
    	bot_panel.add(setting_bu);
    	bot_panel.add(help_bu);
    	bot_panel.add(exit_bu);
    	
    	JPanel space = new JPanel();
    	content_pane.add(space, BorderLayout.SOUTH);
    	space.setBackground(UICOLOR);
    	JButton space_bu = new JButton(space_img);
    	space_img.setImage(space_img.getImage().getScaledInstance(400, 150,Image.SCALE_DEFAULT ));
    	space.add(space_bu);
    	space_bu.setContentAreaFilled(false);
    	space_bu.setBorderPainted(false);
    	space.setEnabled(false);
    	pack();
    	setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
    	
	}
	

	public void setUpButton() {
		
		
		//single player button
		//single_player_bu.setText("Single Player Mode");
		single_img.setImage(single_img.getImage().getScaledInstance(400, 120,Image.SCALE_DEFAULT ));
		single_player_bu.setIcon(single_img);
		single_player_bu.addActionListener(new ActionListener() {
            //jump to single player game when onclick
            @Override
            public void actionPerformed(ActionEvent e) {
            	
        		Main main = new Main();
        		main.StartGame();            
        		
            }
		});
		
		//multi-player button
		//multi_player_bu.setText("Multi-Player Mode");
		multi_img.setImage(multi_img.getImage().getScaledInstance(400, 120,Image.SCALE_DEFAULT ));
		multi_player_bu.setIcon(multi_img);
		multi_player_bu.addActionListener(new ActionListener() {
	        //jump to multi-player game when onclick
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//TODO: add jump to multi-player
	        }
		});
		
		//settings button
		//setting_bu.setText("Settings");
		setting_img.setImage(setting_img.getImage().getScaledInstance(200, 80,Image.SCALE_DEFAULT ));
		setting_bu.setIcon(setting_img);
		setting_bu.addActionListener(new ActionListener() {
	        //jump to sdettings when onclick
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//TODO: add settings
	        }
		});
		
		//exit button
		//exit_bu.setText("Exit");
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
	        //exit the game when onclick
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//TODO:add help message
	        }
		});
	}
	
	
	public static void main(String[] args) {
		Welcome gui = new Welcome();
        gui.setVisible(true);
       	gui.setUpGUI();
       	gui.setUpButton();
	}
	
	

}
