package UI;
// Manas Kumar Rai (W1607801)
import static javax.swing.BorderFactory.createEtchedBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Helper.DBHelper;
import Models.Attribute;
import Models.Business;
import Models.BusinessCategories;
import Models.BusinessSubCategories;

public class HW3 {
    ArrayList<String> selectedCategories = new ArrayList<String>();
    ArrayList<String> selectedSubCategories = new ArrayList<String>();
    ArrayList<String> selectedAttributes = new ArrayList<String>();
    ArrayList<String> generatedBusinessIds = new ArrayList<String>();


    private JFrame frame;
    private JButton jButton1;
    private JButton jButton2;
    //private JComboBox<String> jComboBox1;
    private org.jdesktop.swingx.JXDatePicker jComboBox3;
    private org.jdesktop.swingx.JXDatePicker jComboBox4;
    private JComboBox<String> comboBox;
    private JComboBox<String> starComboBox;
    private JComboBox<String> votesComboBox;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel dayLabel;
    private JLabel cityLabel;
    private JLabel stateLabel;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JTable jTable1;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextArea jTextArea1;

    private JLabel lblFrom;
    private JLabel lblTo;
    private JLabel lblYelpSearch;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;

    private DBHelper db;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HW3 window = new HW3();
                    window.frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public HW3() {
        db = new DBHelper();
        db.DBConnect();

        initComponents();
        loadCategories();
    }

    private void initComponents() {

        frame = new JFrame();
        frame.setPreferredSize(new java.awt.Dimension(1600, 900));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        jScrollPane1 = new JScrollPane();
        jScrollPane2 = new JScrollPane();
        jPanel1 = new JPanel();
        jScrollPane3 = new JScrollPane();
        jTable1 = new JTable();
        jScrollPane4 = new JScrollPane();
        jPanel2 = new JPanel();
        jScrollPane5 = new JScrollPane();
        jPanel3 = new JPanel();
        jComboBox3 = new org.jdesktop.swingx.JXDatePicker();
        jComboBox4 = new org.jdesktop.swingx.JXDatePicker();
        comboBox = new JComboBox<String>();
        starComboBox = new JComboBox<String>();
        votesComboBox  = new JComboBox<String>();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
     //   jComboBox1 = new JComboBox<String>();
        dayLabel = new JLabel();
        jTextField1 = new JTextField();
        cityLabel = new JLabel();
        stateLabel = new JLabel();
        jTextField2 = new JTextField();
        jTextArea1 = new JTextArea();


        jScrollPane2.setBorder(createEtchedBorder());
        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel1.setBackground(new java.awt.Color(41, 149, 204));
        jPanel1.setToolTipText("");
        jPanel1.setMaximumSize(new java.awt.Dimension(200, 800));
        jPanel1.setMinimumSize(new java.awt.Dimension(200, 800));
        jPanel1.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane2.setViewportView(jPanel1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] {
                        "Business", "City", "State", "Stars"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);

        jScrollPane4.setMaximumSize(new java.awt.Dimension(300, 800));

        jPanel2.setBackground(new java.awt.Color(41, 149, 204));
        jPanel2.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane4.setViewportView(jPanel2);

        jScrollPane5.setMaximumSize(new java.awt.Dimension(300, 800));

        jPanel3.setBackground(new java.awt.Color(41, 149, 204));
        jPanel3.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane5.setViewportView(jPanel3);

//        jComboBox3.setModel(new DefaultComboBoxModel<String>(new String[] { "None", "00:00",  "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"}));
////        jComboBox3.setEnabled(false);
//
//        jComboBox4.setModel(new DefaultComboBoxModel<String>(new String[] { "None", "00:00",  "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00" }));
//        jComboBox4.setEnabled(false);

        //jComboBox1.setModel(new DefaultComboBoxModel<String>(new String[] { "None", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(2);
       // jTextArea1.setUI(null);
        jTextArea1.setLineWrap (true);
        jTextArea1.setWrapStyleWord (false); 
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        
        
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "AND", "OR" }));
        
        starComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "<", ">","=" }));
        votesComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "<", ">","=" }));

        dayLabel.setText("Review");

        jTextField1.setText("");


        cityLabel.setText("Star");

        stateLabel.setText("Votes");

        jTextField2.setText("");

        JButton jButton1 = new JButton("Search");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });

        lblFrom = new JLabel("From");

        lblTo = new JLabel("To");

        lblYelpSearch = new JLabel("Yelp Search");

        lblNewLabel = new JLabel("");

        lblNewLabel_1 = new JLabel("Condition");
// Design of UI

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel3)
                                                                                .addGap(50))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel2)
                                                                                .addGap(40)))
                                                                .addGap(32))
                                                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                                                                .addComponent(dayLabel)
                                                                .addGap(6)))
                                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(28)
                                                                .addComponent(cityLabel)
                                                                .addGap(18)
                                                                .addComponent(starComboBox)
                                                                .addGap(18)
                                                                .addComponent(jTextField1, 132, 132, 132))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                               // .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18)
                                                                .addComponent(lblFrom)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(11)
                                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(7)
                                                                .addComponent(lblTo)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(jComboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(22)
                                                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(lblNewLabel)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                                .addComponent(lblNewLabel_1)
                                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(stateLabel)
                                                                .addGap(18)
                                                                .addComponent(votesComboBox)
                                                                .addGap(18)
                                                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)))
                                                				.addGap(18)
                                                				
                                                				
                                                				
                                                .addGap(31))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(304)
                                                .addComponent(lblYelpSearch))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(32)
                                                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 603, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                        		.addGap(18)
                                        		.addComponent(jTextArea1)
                                        		.addGap(300)
                                                .addComponent(jButton1)))
                                .addGap(147))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(16)
                                                        .addComponent(lblYelpSearch)
                                                        .addGap(27)
                                                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18)
                                                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                                                .addComponent(dayLabel)
                                                                //.addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblNewLabel)
                                                                .addComponent(lblFrom)
                                                                .addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblTo)
                                                                .addComponent(jComboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblNewLabel_1)
                                                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(27)
                                                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(cityLabel)
                                                                .addComponent(starComboBox)
                                                                .addComponent(stateLabel)
                                                                .addComponent(votesComboBox)
                                                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        		.addGap(18)
                                                        		.addComponent(jTextArea1)
                                                        .addGap(18)
                                                        .addComponent(jButton1)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(44)
                                                .addComponent(jLabel3))
                                        .addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(jScrollPane5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                                .addComponent(jScrollPane4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)))
                                .addContainerGap(72, Short.MAX_VALUE))
        );

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                if (!(generatedBusinessIds.size() < row)) {
                    frame.setEnabled(false);
                    jTable1.setOpaque(false);
                    Map<String, Object> allInputValues = getAllInputValues();
                    System.out.println( allInputValues );
                    ReviewsPage rp = new ReviewsPage(generatedBusinessIds.get(row), allInputValues);
                    jTextArea1.setText(rp.query);
                    rp.setVisible(true);
                    System.out.println("here");
                    frame.setEnabled(true);
                }
            }

			
        });

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        jTable1.setDefaultRenderer(Object.class, new MyTableCellRender());

//        jTable1.showHorizontalLines(true);
//        jTable1.showVerticalLines(false);
        jTable1.setGridColor(Color.BLACK);
        frame.getContentPane().setLayout(layout);

        frame.pack();
    }


// Load categories in GUI
    private void loadCategories() {
        jPanel1.removeAll();
       Map<String, Object> allCategories = db.getAllCategories();
       ArrayList<BusinessCategories> cats  = (ArrayList<BusinessCategories>) allCategories.get("resultSet");
       jTextArea1.setText( (String) allCategories.get("Query"));
        for(int i=0; i<cats.size(); i++){
            JCheckBox mycheckbox = new JCheckBox();
            mycheckbox.setSize(10,10);
            mycheckbox.setText(cats.get(i).getCategoryName());
            mycheckbox.setForeground(Color.BLACK);
            mycheckbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    Object source = e.getItemSelectable();
                    JCheckBox checkbox = (JCheckBox) source;
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        System.out.println("selected");
                        System.out.println(checkbox.getText());
                        selectedCategories.add(checkbox.getText());

                    } else {
                    	
                        System.out.println("unselected");
                        System.out.println(checkbox.getText());
                        selectedCategories.remove(checkbox.getText());
                    }
                    if(selectedCategories.size()>0) {
                        loadSubCategories();
                        updateData();
                    }else{
                        jPanel2.removeAll();
                        jPanel2.repaint();

                        jPanel3.removeAll();
                        jPanel3.repaint();

                        DefaultTableModel tmodel = new DefaultTableModel();
                        jTable1.removeAll();
                        jTable1.setModel(tmodel);
                        tmodel.addColumn("Business");
                        tmodel.addColumn("City");
                        tmodel.addColumn("State");
                        tmodel.addColumn("Stars");
                    }
                }
            });
            jPanel1.add(mycheckbox);
            frame.pack();
        }

//        jPanel1.validate();
//        jPanel1.repaint();
    }
    private Map< String, Object > getAllInputValues() {
		String fromDate = "";
		String toDate 	= "";
		
        String andOR           			=  "AND";
        String starOperator 			= starComboBox.getSelectedItem().toString();
        String votesOperator 			= votesComboBox.getSelectedItem().toString();
        HashMap< String, Object > hm 	= new HashMap< String, Object >();
        Date fromDateReviews      		=  jComboBox3.getDate(); 
        Date toDateReviews        		=  jComboBox4.getDate();
        
        if( fromDateReviews != null && !fromDateReviews.toString().isEmpty())
        {	
        	DateFormat targetFormat = new SimpleDateFormat("dd-MMM-yy");
        	fromDate 	= targetFormat.format(fromDateReviews);
        }

        if( toDateReviews != null &&  !toDateReviews.toString().isEmpty())
        {	
        	DateFormat targetFormat = new SimpleDateFormat("dd-MMM-yy");
        	toDate 	= targetFormat.format(toDateReviews);
        }
		
        String starsValue     = jTextField1.getText();
        
		if( !starsValue.isEmpty() )
        	hm.put( "starsValue",Integer.parseInt( starsValue) );
        
		String votesValue     = jTextField2.getText();
        if( votesValue != null && !votesValue.isEmpty() )
        	hm.put( "votesValue",Integer.parseInt( votesValue) );
        
        if( !starOperator.isEmpty() )
        	hm.put( "starOperator",starOperator );
        
        if( !votesOperator.isEmpty() )
        	hm.put( "votesOperator",votesOperator );
        
        if( fromDate != null && !fromDate.isEmpty() )
        	hm.put( "fromDate", fromDate);
        
        if( toDate != null && !toDate.isEmpty() )
        	hm.put( "toDate", 	toDate);
        
        hm.put("andOR",andOR);
		return hm;
	}
    private void loadSubCategories(){

        String condition = comboBox.getSelectedItem().toString();
        Map<String, Object> subCategories = db.getSubCategories( selectedCategories, condition);
        ArrayList<BusinessSubCategories> subs  = (ArrayList<BusinessSubCategories>) subCategories.get("resultSet");
        //System.out.println( subs.toString());
        jTextArea1.setText( (String) subCategories.get("Query"));
        jPanel2.removeAll();
        for(int i=0; i<subs.size(); i++) {
            JCheckBox mycheckbox = new JCheckBox();
            mycheckbox.setSize(10, 10);
            mycheckbox.setText(subs.get(i).getSubCategoryName());
            mycheckbox.setForeground(Color.BLACK);
            mycheckbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    Object source = e.getItemSelectable();
                    JCheckBox checkbox = (JCheckBox) source;
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        System.out.println("selected");
                        System.out.println(checkbox.getText());
                        selectedSubCategories.add(checkbox.getText());

                    } else {
                        System.out.println("unselected");
                        System.out.println(checkbox.getText());
                        selectedSubCategories.remove(checkbox.getText());
                    }
                    if(selectedSubCategories.size()>0) {
                        loadAttributes();
                        updateData();
                    }
                    else
                    {
                    	jPanel3.removeAll();
                        jPanel3.repaint();

                        DefaultTableModel tmodel = new DefaultTableModel();
                        jTable1.removeAll();
                        jTable1.setModel(tmodel);
                        tmodel.addColumn("Business");
                        tmodel.addColumn("City");
                        tmodel.addColumn("State");
                        tmodel.addColumn("Stars");
                    }
                }
            });
            jPanel2.add(mycheckbox);
            frame.pack();
        }
//        jPanel2.repaint();
    }

    public void loadAttributes(){
        String condition = comboBox.getSelectedItem().toString();
        Map<String, Object> mapOfAttributes = db.getAttributes( selectedSubCategories, selectedCategories, condition);
        ArrayList<Attribute> attrs  = (ArrayList<Attribute>) mapOfAttributes.get("resultSet");
        System.out.println( "loadAttributes");
        //System.out.println( subs.toString());
        jTextArea1.setText( (String) mapOfAttributes.get("Query"));
        jPanel3.removeAll();
        for(int i=0; i<attrs.size(); i++) {
            JCheckBox mycheckbox = new JCheckBox();
            mycheckbox.setSize(10, 10);
            mycheckbox.setText(attrs.get(i).getAttribute_name());
            mycheckbox.setForeground(Color.BLACK);
            mycheckbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    Object source = e.getItemSelectable();
                    JCheckBox checkbox = (JCheckBox) source;
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        System.out.println("selected");
                        System.out.println(checkbox.getText());
                        selectedAttributes.add(checkbox.getText());

                    } else {
                        System.out.println("unselected");
                        System.out.println(checkbox.getText());
                        selectedAttributes.remove(checkbox.getText());
                    }
                    if(selectedAttributes.size()>0) {
//                        fillTable();
                        updateData();
                    }
                }
            });
            jPanel3.add(mycheckbox);
            frame.pack();
        }

    }

//    public void fillTable(){
//        DefaultTableModel tmodel = new DefaultTableModel();
//        jTable1.removeAll();
//        jTable1.setModel(tmodel);
//        tmodel.addColumn("Business");
//        tmodel.addColumn("City");
//        tmodel.addColumn("State");
//        tmodel.addColumn("Stars");
//        ArrayList<Business> businesses = db.queryBusiness(selectedAttributes, selectedCategories, selectedSubCategories);
//        for(int i=0; i<businesses.size(); i++){
//            Business business = businesses.get(i);
//            tmodel.addRow(new Object[]{business.getName() , business.getCity(), business.getState(), business.getStars()});
//            generatedBusinessIds.add(business.getBusinessId());
//        }
//
//        frame.pack();
//    }

    public void updateData(){
    	//System.out.println("update Data");
        DefaultTableModel tmodel = new DefaultTableModel();
        jTable1.removeAll();
        jTable1.setModel(tmodel);
        tmodel.addColumn("Business");
        tmodel.addColumn("City");
        tmodel.addColumn("State");
        tmodel.addColumn("Stars");

        String city = jTextField1.getText();
        String state = jTextField2.getText();
        String day = "";
        String toTime = "";
        String fromTime = "";
//        if (jComboBox1.getSelectedItem().toString() != "None") {
//            day = jComboBox1.getSelectedItem().toString();
//
//        }
//        String fromTime = "";
//        if(jComboBox3.getSelectedItem().toString() != "None"){
//            fromTime = jComboBox3.getSelectedItem().toString();
//        }
//
//        String toTime = "";
//        if(jComboBox4.getSelectedItem().toString() != "None"){
//            toTime = jComboBox4.getSelectedItem().toString();
//        }

        String condition = comboBox.getSelectedItem().toString();
        String query = "";
        ArrayList<Business> businesses = new ArrayList<Business>();
        Map<String, Object> queryBusinessByCategory = null;
        Map<String, Object> allInputValues = getAllInputValues();
        if(selectedAttributes.size() ==0 && selectedSubCategories.size() == 0 && selectedCategories.size() >0){
        	
            queryBusinessByCategory = db.queryBusinessByCategory(selectedCategories,condition,allInputValues);
 
        }
        else if(selectedCategories.size() >0 && selectedSubCategories.size()>0 && selectedAttributes.size() ==0){
        	queryBusinessByCategory = db.queryBusinessByCategorySubCategory(selectedCategories,selectedSubCategories,condition,allInputValues);
        	  
        	
        }
        else if(selectedCategories.size()>0 && selectedSubCategories.size()>0 && selectedAttributes.size()>0){
            if(day.length()>0 || fromTime.length()>0 || toTime.length()>0 || city.length() >0 || state.length() >0){
            	queryBusinessByCategory = db.advancedQueryBusiness(selectedCategories, selectedSubCategories, selectedAttributes, city, state, day, fromTime, toTime, condition,allInputValues);
            	
            }else {
            	queryBusinessByCategory = db.queryBusiness(selectedAttributes, selectedCategories, selectedSubCategories, condition, allInputValues);
            }
        }
        query = (String) queryBusinessByCategory.get("Query");
        businesses = (ArrayList<Business>) queryBusinessByCategory.get( "resultSet" );
        jTextArea1.setText(query);
        generatedBusinessIds = new ArrayList<String>();

        System.out.println("row count :"+businesses.size());
        for (int i = 0; i < businesses.size(); i++) {
            Business business = businesses.get(i);
            tmodel.addRow(new Object[]{business.getName(), business.getCity(), business.getState(), business.getStars()});
            generatedBusinessIds.add(business.getBusinessId());
        }
        frame.pack();

    }


    class MyTableCellRender extends DefaultTableCellRenderer {

        public MyTableCellRender() {
        }

        final JLabel headerLabel = new JLabel();
        {
            //setBorder(BorderFactory.createEmptyBorder());
            headerLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.RED));
            headerLabel.setOpaque(true);
            headerLabel.setBackground(Color.WHITE);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            setForeground(Color.black);
            setBackground(Color.white);
            setText(value != null ? value.toString() : "");
            return this;
        }
    }
}
