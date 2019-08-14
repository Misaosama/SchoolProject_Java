package View;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;

import javax.swing.border.*;


public class MapEditor extends JFrame {
    /**
     * MapEditor is a Gui to help editing maps
     */
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    /** A message box on top of the user interface  */
    JLabel welcome = new JLabel("Hi, you can either load a map or click on the button to specify a wall or item",JLabel.CENTER);
    
    /** A message board panel holding the message box  */
    JPanel message_board = new JPanel();
    /** A panel showing grid in the middle */
    JPanel show_grid = new JPanel();
    /** A panel holding couple buttons for control */
    JPanel control_center = new JPanel();
    /** A panel holding the apple changes button */
    JPanel bot_panel = new JPanel();
    
    /** A constant holding number of rows */
    private static int GUIOPTION;
    
    /** A constant holding number of rows */
    private static int ROW = 10;
    
    /** A constant holding number of columns */
    private static int COL = 10;
    
    /** A constant holding default UI color */
    private static Color UICOLOR = Color.pink;
    
    /** A constant holding save file path */
    private static String PATH = System.getProperty("user.dir");
    
    /** A constant holding the current grid */
    private static String[][] GRID;
    
    /** A constant holding the current user's grid */
    private static String[][] USERGRID;
    
    /**
     * a function to load file using filechooser
     * use when load file button is clicked or open file menu item is clicked
     */
    public void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(PATH));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(false);
        boolean end = false;
        int value = fileChooser.showOpenDialog(null);
        //if user decide not to continue
        if(value!=JFileChooser.APPROVE_OPTION) {
            welcome.setText("no file selected, try again!");
            end = true;
        }
        File input_file = fileChooser.getSelectedFile();
        if(input_file==null) {
            welcome.setText("wrong file, try again!");
            end = true;
        }
        if (!end) {
            String path = input_file.getAbsolutePath();
            //read the input file
            String read_file_msg = readFile(input_file);
            welcome.setText(read_file_msg);
            if (read_file_msg.equals("all good!")){
                loadGrid();
                welcome.setText("loaded file: " + path + "\n");
            }else {
                welcome.setText(read_file_msg+" try again!");
                end = true;
            }
        }
    }

    /**
     * a function to save file using filechooser
     * use when save file button is clicked or save file menu item is clicked
     *
     */
    public void saveFile() {
        createNewGrid();
        for (int i=0;i<ROW;i++) {
            for(int j =0; j < COL; j++) {
                GRID[i][j] = USERGRID[i][j];
            }
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(PATH));
        int value = fileChooser.showSaveDialog(null);
        File output_file =fileChooser.getSelectedFile();
        boolean end = false;
        //if no file is chosen
        if(value!=JFileChooser.APPROVE_OPTION||output_file==null) {
            end = true;
            welcome.setText("no file chosen, try again!");
        }
        
        if(!end) {
            //check for possible override
            String output = output_file.getAbsolutePath().substring(0,output_file.getAbsolutePath().indexOf("."));
            File check_file = new File(output+".txt");
            if (check_file.exists()) {
                int option = JOptionPane.showConfirmDialog(MapEditor.this, "file about to be overwirtten, do you wish to continue?",
                                                           "warning", JOptionPane.WARNING_MESSAGE);
                if( option != JOptionPane.YES_OPTION){
                    end = true;
                    welcome.setText("save file terminated!");
                }
            }
        }
        if(!end) {
            String output = output_file.getAbsolutePath().substring(0,output_file.getAbsolutePath().indexOf("."));
            String flag = writeFile(output);
            if (flag.equals("error")){
                welcome.setText("file can not be written");
                end = true;
            }
            if(!end) {
                welcome.setText("file "+ output+".txt is generated");
            }
        }
    }    
    
    /**
     * a function to create new grid by asking user's input of row and col
     *
     */
    public void newGrid() {
        String input_str=JOptionPane.showInputDialog(MapEditor.this,"please type in new row and col(3~10), "
                                                     + "seperate by space","new map",JOptionPane.PLAIN_MESSAGE);
        boolean end = false;
        if (input_str!=null) {
            String[] row_col = input_str.split(" ");
            if (row_col.length!=2){
                end = true;
                welcome.setText("wrong format of input, try again!");
            }else {
                if(row_col[0].length()<1||row_col[1].length()<1) {
                    welcome.setText("wrong format of input, try again!");
                    end = true;
                }
            }
            if(!end) {
                for (int i = row_col[0].length();--i>=0;){
                    if (!Character.isDigit(row_col[0].charAt(i))){
                        welcome.setText("wrong format of input, try again!");
                        end = true;
                    }
                }
                for (int i = row_col[1].length();--i>=0;){
                    if (!Character.isDigit(row_col[1].charAt(i))){
                        welcome.setText("wrong format of input, try again!");
                        end = true;
                    }
                }
            }
            if (!end) {
                ROW = Integer.valueOf(row_col[0]);
                COL = Integer.valueOf(row_col[1]);
                if (ROW<3||COL<3) {
                    end = true;
                    welcome.setText("number should be larger than 2");
                }
            }
        }else {
            end = true;
            welcome.setText("wrong format of input, try again!");
        }
        if (!end) {//create new grid if all seeting is valid
            createNewGrid();
            createNewUserGrid();
            loadGrid();
            addGridButton();
        }
    }

    
    /**
     * write the GRID into the file with the output filename and steps
     * @param output the name of output file in string
     * @return a string of error or success message
     */
    public static String writeFile(String output) {
        String flag = "error";
        File output_file = new File(output+".txt");
        try {
            output_file.createNewFile();
        } catch (IOException e) {
            return flag;
        }
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(output_file));
            out.write(ROW + ", " + COL + '\n' );
            for(int i = 0; i<ROW;i++) {
                for(int j = 0; j<COL;j++) {
                    if(j==COL-1) {
                        if(Integer.valueOf(GRID[i][j]) == 0) {
                            out.write("0");
                        }else if(Integer.valueOf(GRID[i][j]) == 1) {
                            out.write("1");
                        }else if(Integer.valueOf(GRID[i][j]) == 2) {
                            out.write("2");
                        }else if(Integer.valueOf(GRID[i][j]) == 3) {
                            out.write("3");
                        }else if(Integer.valueOf(GRID[i][j]) == 4) {
                            out.write("4");
                        }else if(Integer.valueOf(GRID[i][j]) == 5) {
                            out.write("5");
                        }
                    }else {
                    	if(Integer.valueOf(GRID[i][j]) == 0) {
                            out.write("0"+", ");
                        }else if(Integer.valueOf(GRID[i][j]) == 1) {
                            out.write("1"+", ");
                        }else if(Integer.valueOf(GRID[i][j]) == 2) {
                            out.write("2"+", ");
                        }else if(Integer.valueOf(GRID[i][j]) == 3) {
                            out.write("3"+", ");
                        }else if(Integer.valueOf(GRID[i][j]) == 4) {
                            out.write("4"+", ");
                        }else if(Integer.valueOf(GRID[i][j]) == 5) {
                            out.write("5"+", ");
                        }
                    
                    }
                }
                out.write('\n');
            }
            out.flush();
            out.close();
            return "all good";
        } catch (IOException e) {
            return flag;
        }
        
    }
    
    /**
     * load the grid from GRID
     */
    public void loadGrid() {
        welcome.setText("loading.....");
        this.show_grid.removeAll();
        this.show_grid.setVisible(false);
        
        GridLayout gridLayout = new GridLayout(ROW,COL);
        show_grid.setLayout(gridLayout);
        
        for (int i=0; i<ROW; i++) {
            for(int j =0; j < COL; j++) {
                JButton grid_button = new JButton("0");
                if(GRID[i][j].equals("0")) {
                    grid_button.setText("0");
                    grid_button.setBackground(Color.white);
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }else if(GRID[i][j].equals("1")) {
                    grid_button.setText("1");
                    grid_button.setBackground(Color.black);
                    
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }else if(GRID[i][j].equals("2")) {
                    grid_button.setText("2");
                    grid_button.setBackground(Color.red);
                    
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }else if(GRID[i][j].equals("3")) {
                    grid_button.setText("3");
                    grid_button.setBackground(Color.green);
                    
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }else if(GRID[i][j].equals("4")) {
                    grid_button.setText("4");
                    grid_button.setBackground(Color.orange);
                    
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }else if(GRID[i][j].equals("5")) {
                    grid_button.setText("5");
                    grid_button.setBackground(Color.orange);
                    
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }

                final int row_1 = i;
                final int col_1 = j;
                grid_button.addActionListener(new ActionListener() {
                    //change the stats when clicked(dead to alive or alive to dead)
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (grid_button.getText().equals("0")){
                            grid_button.setText("1");
                            grid_button.setBackground(Color.black);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="1";
                            
                        }else if(grid_button.getText().equals("1")){
                            grid_button.setText("2");
                            grid_button.setBackground(Color.red);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="2";
                        }else if(grid_button.getText().equals("2")){
                            grid_button.setText("3");
                            grid_button.setBackground(Color.green);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="3";
                        }else if(grid_button.getText().equals("3")){
                            grid_button.setText("4");
                            grid_button.setBackground(Color.orange);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="4";
                        }else if(grid_button.getText().equals("4")){
                            grid_button.setText("5");
                            grid_button.setBackground(Color.orange);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="5";
                        }else if(grid_button.getText().equals("5")){
                            grid_button.setText("0");
                            grid_button.setBackground(Color.white);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="0";
                        }
                    }
                });
                show_grid.add(grid_button);
            }
        }
        
        this.show_grid.setVisible(true);
        
        welcome.setText("load complete");
        
    }
    
    /**
     * construct and initialize a ROW row and COL columns USERGRID
     * with all string "0"s
     */
    public static void createNewUserGrid() {
        USERGRID = new String[ROW][COL];
        for(int i = 0;i < ROW; i++) {
            for(int j =0; j < COL; j++) {
                USERGRID[i][j]="0";
            }
        }
    }
 
    /**
     * construct and initialize a ROW row and COL columns GRID
     * with all string "0"s
     */
    public static void createNewGrid() {
        GRID = new String[ROW][COL];
        for(int i = 0;i < ROW; i++) {
            for(int j =0; j < COL; j++) {
                GRID[i][j]="0";
            }
        }
    }
    
    /**
     * construct and initialize a ROW row and COL columns GRID
     * with all integer 0
     * @param input_file the input file we are going to read
     * @return return a string message of error or success
     */
    public static String readFile(File input_file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(input_file));
            String size;
            size = br.readLine();
            int seperate = size.indexOf(',');
            if(seperate<1) {
                return "wrong file";
            }
            String row_st = size.substring(0, seperate);
            String col_st = size.substring(seperate + 2);
            //check if row_st is number
            for (int i = row_st.length();--i>=0;){
                if (!Character.isDigit(row_st.charAt(i))){
                    return "row should be number";
                }
            }
            //check if col_st is number
            for (int i = col_st.length();--i>=0;){
                if (!Character.isDigit(col_st.charAt(i))){
                    return "col should be number";
                }
            }
            ROW = Integer.valueOf(row_st);
            COL = Integer.valueOf(col_st);
            if((ROW < 3)||(COL < 3)) {
                return "Row or column should be at least 3";
            }
            //construct and initialize the grid
            createNewGrid();
            //construct and initialize the usergrid
            createNewUserGrid();
            //read the grid from file
            String st;
            for(int i = 0; i < ROW; i++) {
                if ((st = br.readLine()) == null) {
                    return "invalid grid";
                }
                String[] this_line = new String[COL];
                this_line = st.split(",");
                GRID[i] = this_line;
                USERGRID[i] = this_line;
                for(int j = 0; j<GRID[i].length;j++) {
                    GRID[i][j] = GRID[i][j].trim();
                    USERGRID[i][j] = USERGRID[i][j].trim();
                }
            }
            br.close();
            //steps set back to 0
            //STEPS = 0;
            
        } catch (IOException e) {
            return "cannot read file try again";
        }
        return "all good!";
    }
    
    /**
     * add grid button base on USERGRID
     *
     */
    public void addGridButton() {
        this.show_grid.removeAll();
        GridLayout gridLayout = new GridLayout(ROW,COL);
        this.show_grid.setLayout(gridLayout);
        createNewUserGrid();
        for (int i=0;i<ROW;i++) {
            for(int j =0; j < COL; j++) {
                JButton grid_button = new JButton("0");
                grid_button.setBackground(Color.white);
                if(GUIOPTION == 0) {
                    grid_button.setOpaque(true);
                    grid_button.setBorderPainted(false);
                }
                
                if(USERGRID[i][j].equals("0")) {
                    grid_button.setText("0");
                    grid_button.setBackground(Color.white);
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                    
                }else if(USERGRID[i][j].equals("1")) {
                    grid_button.setText("1");
                    grid_button.setBackground(Color.black);
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }else if(USERGRID[i][j].equals("2")) {
                    grid_button.setText("2");
                    grid_button.setBackground(Color.red);
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }else if(USERGRID[i][j].equals("3")) {
                    grid_button.setText("3");
                    grid_button.setBackground(Color.green);
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }else if(USERGRID[i][j].equals("4")) {
                    grid_button.setText("4");
                    grid_button.setBackground(Color.orange);
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }else if(USERGRID[i][j].equals("5")) {
                    grid_button.setText("5");
                    grid_button.setBackground(Color.orange);
                    if(GUIOPTION == 0) {
                        grid_button.setOpaque(true);
                        grid_button.setBorderPainted(false);
                    }
                }
                final int row_1 = i;
                final int col_1 = j;
                grid_button.addActionListener(new ActionListener() {
                    //change the stats when clicked(dead to alive or alive to dead)
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (grid_button.getText().equals("0")){
                            grid_button.setText("1");
                            grid_button.setBackground(Color.black);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="1";
                            
                        }else if(grid_button.getText().equals("1")){
                            grid_button.setText("2");
                            grid_button.setBackground(Color.red);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="2";
                        }else if(grid_button.getText().equals("2")){
                            grid_button.setText("3");
                            grid_button.setBackground(Color.green);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="3";
                        }else if(grid_button.getText().equals("3")){
                            grid_button.setText("4");
                            grid_button.setBackground(Color.orange);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="4";
                        }else if(grid_button.getText().equals("4")){
                            grid_button.setText("5");
                            grid_button.setBackground(Color.orange);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="5";
                        }else if(grid_button.getText().equals("5")){
                            grid_button.setText("0");
                            grid_button.setBackground(Color.white);
                            if(GUIOPTION == 0) {
                                grid_button.setOpaque(true);
                                grid_button.setBorderPainted(false);
                            }
                            USERGRID[row_1][col_1]="0";
                        }
                    }
                });
                this.show_grid.add(grid_button);
            }
        }
    }
    
    /**
     * construct the main MapEditor
     */
    public MapEditor(){
        super("MapEditor");
        setLayout(new BorderLayout());
        try{//use UIManager if possible
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            GUIOPTION = 1;
        }catch(Exception e){
            GUIOPTION = 0;
        }
        //readConfig();
        
        message_board.add(welcome);
        message_board.setPreferredSize(new Dimension(800, 50));
        
        //add panel to the frame
        add(message_board, BorderLayout.NORTH);
        add(show_grid,BorderLayout.CENTER);
        add(control_center,BorderLayout.WEST);
        //add(right_panel,BorderLayout.EAST);
        add(bot_panel,BorderLayout.SOUTH);
        
        message_board.setBackground(UICOLOR);
        show_grid.setBackground(UICOLOR);
        control_center.setBackground(UICOLOR);
        //right_panel.setBackground(UICOLOR);
        bot_panel.setBackground(UICOLOR);
        
        welcome.setFont(new java.awt.Font("Dialog", 1, 20));
        //show grid
        
        addGridButton();
        
        //control center
        BoxLayout boxlayout2=new BoxLayout(control_center, BoxLayout.Y_AXIS);
        control_center.setLayout(boxlayout2);
        
        JButton exit_button = new JButton("exit");
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.exit(0);
            	MapEditor.this.setVisible(false);
            }
        });
        
        
        JButton load_button = new JButton("load map");
        load_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                loadFile();
            }
        });
        
        JButton save_button = new JButton("save map");
        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                saveFile();
            }
        });
        
        JButton new_button = new JButton("new map");
        new_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                newGrid();
            }
        });
        
        JLabel notes1 = new JLabel("a white 0 stands for empty");
        JLabel notes2 = new JLabel("a black 1 stands for walls");
        JLabel notes3 = new JLabel("a red 2 stands for Medical kit");
        JLabel notes4 = new JLabel("a green 3 stands for speed kit");
        JLabel notes5 = new JLabel("a orange 4 stands for enemy");
        JLabel notes6 = new JLabel("a orange 5 stands for boss");
        
        
        //set the layout for buttons
        new_button.setAlignmentY(Component.LEFT_ALIGNMENT);
        load_button.setAlignmentY(Component.LEFT_ALIGNMENT);
        save_button.setAlignmentY(Component.LEFT_ALIGNMENT);
        exit_button.setAlignmentY(Component.LEFT_ALIGNMENT);
        notes1.setAlignmentY(Component.LEFT_ALIGNMENT);
        notes2.setAlignmentY(Component.LEFT_ALIGNMENT);
        notes3.setAlignmentY(Component.LEFT_ALIGNMENT);
        notes4.setAlignmentY(Component.LEFT_ALIGNMENT);
        notes5.setAlignmentY(Component.LEFT_ALIGNMENT);
        notes6.setAlignmentY(Component.LEFT_ALIGNMENT);
        
        new_button.setFont(new java.awt.Font("Dialog", 1, 18));
        load_button.setFont(new java.awt.Font("Dialog", 1, 18));
        save_button.setFont(new java.awt.Font("Dialog", 1, 18));
        exit_button.setFont(new java.awt.Font("Dialog", 1, 18));
        
        notes1.setFont(new java.awt.Font("Dialog", 1, 18));
        notes2.setFont(new java.awt.Font("Dialog", 1, 18));
        notes3.setFont(new java.awt.Font("Dialog", 1, 18));
        notes4.setFont(new java.awt.Font("Dialog", 1, 18));
        notes5.setFont(new java.awt.Font("Dialog", 1, 18));
        notes6.setFont(new java.awt.Font("Dialog", 1, 18));
        
        
        control_center.add(new_button);
        control_center.add(javax.swing.Box.createRigidArea(new Dimension(25, 25)));
        control_center.add(load_button);
        control_center.add(javax.swing.Box.createRigidArea(new Dimension(25, 25)));
        control_center.add(save_button);
        control_center.add(javax.swing.Box.createRigidArea(new Dimension(25, 25)));
        control_center.add(exit_button);
        control_center.add(javax.swing.Box.createRigidArea(new Dimension(25, 25)));
        control_center.add(notes1);
        control_center.add(notes2);
        control_center.add(notes3);
        control_center.add(notes4);
        control_center.add(notes5);
        control_center.add(notes6);
        
        //apply button
        
        JButton apply_button = new JButton("apply changes");
        apply_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                createNewGrid();
                for (int i=0;i<ROW;i++) {
                    for(int j =0; j < COL; j++) {
                        GRID[i][j] = USERGRID[i][j];
                    }
                }
                loadGrid();
            }
        });
        apply_button.setFont(new java.awt.Font("Dialog", 1, 22));
        bot_panel.add(apply_button);
       
        //bot panel
        JLabel bot_message = new JLabel("***click apply to apply the changes made in the map***");
        bot_panel.add(bot_message);
        
        //menu
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        menubar.add(fileMenu);
        
        JMenuItem newFileItem = new JMenuItem("New...");
        JMenuItem openFileItem = new JMenuItem("Open File...");
        JMenuItem saveFileItem = new JMenuItem("Save File...");
        
        openFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                loadFile();
            }
        });
        
        saveFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                saveFile();
            }
        });
        
        newFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                newGrid();
            }
        });
        fileMenu.add(newFileItem);
        fileMenu.add(openFileItem);
        fileMenu.add(saveFileItem);
        
        setJMenuBar(menubar);
        
        pack();
        setSize(1450, 800);
        setMinimumSize(new Dimension(1230, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        MapEditor gui = new MapEditor();
        gui.setVisible(true);
        
    }
}