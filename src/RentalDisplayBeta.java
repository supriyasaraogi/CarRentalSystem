import javax.swing.*;
import javax.swing.ButtonGroup;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class RentalDisplayBeta extends JFrame{

	Connection conn = null;
	ConnectToRentals ctr = null;
	ResultSet rs = null;
	Transactions transCustomer;
	CustomerPanelData cpd = new CustomerPanelData();
	int selection = -1;
	JFrame rentFrame = new JFrame();
	JPanel parentPanel;
	JPanel customerPanel;
	JPanel addVehiclePanel;
	JPanel returnCarPanel;
	private JButton btnAddVehicle;
	private JButton btnDeleteVehicle;
	private JButton btnReturn;
	private JButton btnUpdate;
	private JLabel lblCustomerid;
	private JLabel lblName;
	private JTextField CustID;
	private JTextField CustName;
	private JLabel lblPhone;
	private JLabel lblDate;
	private JTextField phoneNum;
	private JTextField txtDate;
	private JTextField numDsWks;
	private JButton btnCheck;
	private JLabel lblEnterQuantity;
	private JPanel checkOut;
	private JTextField txtRate;
	private JLabel lblRate;
	private JLabel lblDueDate;
	private JTextField txtDueDate;
	private JTextField txtTotal;
	private JLabel lblTotal;
	private JButton btnBack;
	private JButton btnSubmit;
	private JPanel finishedCheckout;
	/*
	 * createCustomer variables
	 */
	ButtonGroup group;
	JComboBox vehicleTypes;
	/*
	 * checkAvailability variables
	 */
	private double total=0.0;
	private java.sql.Date returnDate;
	private int daysInWeeks;
	/*
	 * populateCheckOut variables
	 */
	private long vId;
	private double priceRate=0.0;
	/*
	 * addVehiclePanel variable below
	 */
	private JPanel addVehicle;
	private JTextField txtOwnerID;
	private JTextField txtName;
	private JTextField txtVehicleID;
	private JTextField txtModel;
	private JLabel lblVehicleModel;
	private JLabel lblVehicleType;
	private JComboBox vehicleBox;
	private JComboBox ownerBox;
	private JLabel lblVehicleYear;
	private JTextField txtYear;
	private JLabel lblSsn;
	private JTextField txtSsn;
	private JLabel lblDriverLicenseNo;
	private JTextField txtLicense;
	private JLabel lblRepresentative;
	private JTextField txtRep;
	private JButton btnBack_1;
	private JButton btnAdd;
	private JPanel AddedVehicle;
	private JPanel Individual;
	private JPanel Company;
	private JPanel Bank;
	private JPanel ownerTypes;
	private JLabel lblSuccessfullyAddedVechicle;
	private JButton btnVehicleMain;
	private AddVehiclePanelData avpd = new AddVehiclePanelData();
	private String[] ownerT = {"select","Bank","Company","Individual"};
	private String[] carTypes = {"select","Compact","Medium","Large","SUV","Van","Truck"};
	private JLabel lblAddress;
	private JTextField txtAddress;
	/*
	 * returningCar variables
	 */
	private JPanel Return;
	private JLabel lblEnterRentalId;
	private JTextField returnRentID;
	private JPanel ReturnResults;
	private JPanel FoundReturn;
	private JPanel NotFoundReturn;
	private JButton btnCheckReturn;
	private JPanel submitReturnPanel;
	private JButton btnReturn_1;
	private JLabel lblAmountdue;
	private JLabel label;
	private JLabel lblRentalNotFound;
	private JPanel successReturn;
	private JLabel lblSuccessfullyReturned;
	private boolean localFlag;
	private JButton btnReturnBack;
	private ResultSet returnCost;
	private double cost;
	/*
	 * updateRates variables begin here
	 */
	private JPanel updatePanel;
	private JPanel updateHolder;
	private JPanel successUpdate;
	private JLabel lblSuccessfullyUpdatedRecord;
	private JButton btnUpdateMain;
	private JLabel lblDailyRate;
	private JTextField txtDailyRate;
	private JLabel lblWeeklyRate;
	private JTextField txtWeeklyRate;
	private JButton btnUpdateBack;
	private JButton btnUpdate_1;
	private JComboBox vehicleTypeBox;
	private JLabel lblVehicleType_1;
	private JButton btnPrint;
	/*
	 * printData variables begin here
	 */
	private JPanel printPanel;
	private JPanel printPanel2;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton btnPrintBack;
	/*
	 * printReport variables begin here
	 */
	private JPanel reportHolder;
	private JButton btnReport;
	private JScrollPane scrollReport;
	private JTextArea txtAreaReport;
	private JButton btnReportBack; 
	/*
	 * deleteRecordPanel variables
	 */
	private JPanel deleteHolder;
	private JScrollPane scrollPane_1;
	private JButton btnDeleteBack;
	private JPanel deletePanel;
	private JList deleteList;
	private JButton btnDltCustomer;
	private JButton btnDltOwner;
	private JButton btnDltVehicle;
	private JPanel deleteBtnPanel;
	private JButton btnDelete;
	private String storedSelection = "";
	private DefaultListModel listModelDelete;
	private int option;
	
	private void makeFrame()
	{
		/*
		 * Uncomment these function calls in order to work on the panels in the Design view
		 * Otherwise the panel will not be visible.
		 */
		createCustomer();
//		finishTransaction();
//		checkAvailability();
//		addVehiclePanel();
//		returningCar();
//		updateRates();
//		printData();
//		deleteRecordPanel();
		deleteRecordPanel();

		//sets the dimensions of the Frame
		rentFrame.setBounds(100, 100, 500, 400);
		rentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rentFrame.getContentPane().add(parentPanel, BorderLayout.CENTER);
		rentFrame.setVisible(true);
	}
	
////////////////////////////////////////////BEGINNING OF DELETION PANEL///////////////////////////////////////////////
	/*
	 * The delete Record panel has the ability to delete an owner, vehicle, or customer
	 * chose the option, select the entry you want to delete(highlighted) and the delete
	 * button will appear. Clicking the submit/delete button completes transaction
	 */
	private void deleteRecordPanel(){
	
		//panel that holds all windows/panels with the buttons
		deleteHolder = new JPanel();
		parentPanel.add(deleteHolder, "name_2936029286381080");
		deleteHolder.setLayout(null);
		
		//holds the buttons and text box
		deletePanel = new JPanel();
		deletePanel.setBounds(141, 11, 333, 339);
		deleteHolder.add(deletePanel);
		deletePanel.setLayout(new BorderLayout(0, 0));
		
		//holds the delete/submit button
		deleteBtnPanel = new JPanel();
		deleteBtnPanel.setBounds(23, 242, 89, 69);
		deleteHolder.add(deleteBtnPanel);
		deleteBtnPanel.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		deletePanel.add(scrollPane_1, BorderLayout.CENTER);
		
		listModelDelete = new DefaultListModel();
		/*
		 * there are three button listeners, all three work essentially the same
		 * 1. They will clear the list
		 * 2. They will populate the list according to the button selected by user
		 * 3. Initiated to the JList as a DefaultListModel
		 * 4. Mouse Listener is set up to reveal delete button upon double clicking record to delete
		 * 5. The selection is saved and later passed into a deletion query when Delete button is pressed
		 */
		btnDltCustomer = new JButton("Customer");
		btnDltCustomer.setBounds(23, 52, 89, 23);
		btnDltCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				listModelDelete.clear();
				addCustomerToDeleteList(listModelDelete);
				deleteList = new JList(listModelDelete);
				scrollPane_1.setViewportView(deleteList);
				option = 0;
				/*
				 * mouse listener for selecting a customer to delete
				 */
				MouseListener mouseListenerCus = new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount()==2) {
							deleteBtnPanel.removeAll();
							deleteBtnPanel.add(btnDelete);
							deleteBtnPanel.repaint();
							deleteBtnPanel.validate();
							String selection = deleteList.getSelectedValue().toString().substring(0, 9);
							System.out.println(selection);
							storedSelection = selection;
						}
					}
				};
				deleteList.addMouseListener(mouseListenerCus);
			}
		});
		deleteHolder.add(btnDltCustomer);
		
		btnDltOwner = new JButton("Owner");
		btnDltOwner.setBounds(23, 132, 89, 23);
		btnDltOwner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				listModelDelete.clear();
				addOwnerToDeleteList(listModelDelete);
				deleteList = new JList(listModelDelete);
				scrollPane_1.setViewportView(deleteList);
				option = 1;
				/*
				 * mouse listener for selecting an owner to delete
				 */
				MouseListener mouseListenerOwn = new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount()==2) {
							deleteBtnPanel.removeAll();
							deleteBtnPanel.add(btnDelete);
							deleteBtnPanel.repaint();
							deleteBtnPanel.validate();
							String selection = deleteList.getSelectedValue().toString().substring(0, 9);
							System.out.println(selection);
							storedSelection = selection;
						}
					}
				};
				deleteList.addMouseListener(mouseListenerOwn);
			}
		});
		deleteHolder.add(btnDltOwner);
		
		btnDltVehicle = new JButton("Vehicle");
		btnDltVehicle.setBounds(23, 208, 89, 23);
		btnDltVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				listModelDelete.clear();
				addCarToDeleteList(listModelDelete);
				deleteList = new JList(listModelDelete);
				scrollPane_1.setViewportView(deleteList);
				option = 2;
				/*
				 * mouse listener for selecting a vehicle to delete
				 */
				MouseListener mouseListenerVehi = new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount()==2) {
							deleteBtnPanel.removeAll();
							deleteBtnPanel.add(btnDelete);
							deleteBtnPanel.repaint();
							deleteBtnPanel.validate();
							String selection = deleteList.getSelectedValue().toString().substring(0, 13);
							System.out.println(selection);
							storedSelection = selection;
						}
					}
				};
				deleteList.addMouseListener(mouseListenerVehi);
				
			}
		});
		deleteHolder.add(btnDltVehicle);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(0, 23, 89, 23);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				/*
				 * Does the actual deletion in the database
				 */
				deleteSelection(storedSelection, option);
			}
		});
//		deleteBtnPanel.add(btnDelete);
		
		//button that goes back to the main window
		btnDeleteBack = new JButton("Back");
		btnDeleteBack.setBounds(23, 322, 89, 23);
		btnDeleteBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				parentPanel.removeAll();
				parentPanel.add(customerPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		deleteHolder.add(btnDeleteBack);
	}
	//determines which selection was made and which panel is the appropriate one to display
	private void deleteSelection(String selection, int index){
		if(index == 0){
			transCustomer.deleteCustomer(selection);
		}
		else if(index == 1){
			transCustomer.deleteOwner(selection);
		}
		else if(index == 2){
			transCustomer.deleteVehicle(selection);
		}
	}
	/*
	 * following three methods are to populate the lists where
	 * the user can select one record and then proceed to delete.
	 */
	private void addOwnerToDeleteList(DefaultListModel list){
		ResultSet localRS = null;
		localRS = transCustomer.getRecordToDelete();
		try{
			while(localRS.next()){
				String id ="";
				String name ="";
				id = localRS.getString("OwnerId");
				name = localRS.getString("Name");
				String record = id + "   " + name;
				list.addElement(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void addCustomerToDeleteList(DefaultListModel list){
		ResultSet localRS = null;
		localRS = transCustomer.printCustomer();
		try{
			while(localRS.next()){
				String id ="";
				String name ="";
				id = localRS.getString("IdNo");
				name = localRS.getString("Name");
				String record = id + "   " + name;
				list.addElement(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void addCarToDeleteList(DefaultListModel list){
		ResultSet localRS = null;
		localRS = transCustomer.printCars();
		try{
			while(localRS.next()){
				String id ="";
				String type ="";
				String model = "";
				String year = "";
				id = localRS.getString("VehicleId");
				type = localRS.getString("Type");
				model = localRS.getString("Model");
				year = localRS.getString("Year");
				String record = id + "   " + fixString2(10, type) + " " + fixString2(10, model) + " " + year;
				System.out.println(record);
				list.addElement(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	////////////////////////////////////////////END OF DELETION PANEL///////////////////////////////////////////////
	
	////////////////////////////////////////////START OF PRINT REPORT PANEL///////////////////////////////////////////////
	private void printReport(){
		reportHolder = new JPanel();
		parentPanel.add(reportHolder, "name_2677891286620800");
		reportHolder.setLayout(null);
		
		scrollReport = new JScrollPane();
		scrollReport.setBounds(10, 11, 464, 255);
		reportHolder.add(scrollReport);
		
		txtAreaReport = new JTextArea();
		scrollReport.setViewportView(txtAreaReport);
		
		btnReportBack = new JButton("Back");
		btnReportBack.setBounds(199, 301, 89, 23);
		btnReportBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				parentPanel.removeAll();
				parentPanel.add(customerPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		reportHolder.add(btnReportBack);
	}
	////////////////////////////////////////////END OF PRINT REPORT PANEL///////////////////////////////////////////////
	
	////////////////////////////////////////////START OF PRINT ALL DATA PANEL///////////////////////////////////////////////
	private void printData(){
		printPanel = new JPanel();
		parentPanel.add(printPanel);
		printPanel.setLayout(new CardLayout(0,0));
		
		printPanel2 = new JPanel();
		printPanel.add(printPanel2, "name_2570611231437693");
		printPanel2.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 464, 282);
		printPanel2.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		btnPrintBack = new JButton("Back");
		btnPrintBack.setBounds(197, 314, 89, 23);
		btnPrintBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				parentPanel.removeAll();
				parentPanel.add(customerPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		printPanel2.add(btnPrintBack);
	}
	////////////////////////////////////////////END OF PRINT ALL DATA PANEL///////////////////////////////////////////////
	
	////////////////////////////////////////////START OF UPDATE CAR RATES PANEL///////////////////////////////////////////////
	private void updateRates(){
		
		updateHolder = new JPanel();
		parentPanel.add(updateHolder, "name_2568113212649779");
		updateHolder.setLayout(new CardLayout(0, 0));
		
		updatePanel = new JPanel();
		updateHolder.add(updatePanel, "name_2563326442534061");
		updatePanel.setLayout(null);
		
		successUpdate = new JPanel();
		updateHolder.add(successUpdate);
		successUpdate.setLayout(null);
		
		lblDailyRate = new JLabel("Daily Rate");
		lblDailyRate.setBounds(121, 72, 80, 14);
		updatePanel.add(lblDailyRate);
		
		txtDailyRate = new JTextField();
		txtDailyRate.setBounds(228, 69, 86, 20);
		txtDailyRate.setText("0.0");
		updatePanel.add(txtDailyRate);
		txtDailyRate.setColumns(10);
		
		lblWeeklyRate = new JLabel("Weekly Rate");
		lblWeeklyRate.setBounds(121, 129, 80, 14);
		updatePanel.add(lblWeeklyRate);
		
		txtWeeklyRate = new JTextField();
		txtWeeklyRate.setBounds(228, 126, 86, 20);
		txtWeeklyRate.setText("0.0");
		updatePanel.add(txtWeeklyRate);
		txtWeeklyRate.setColumns(10);
		
		vehicleTypeBox = new JComboBox(carTypes);
		vehicleTypeBox.setBounds(228, 184, 86, 20);
		vehicleTypeBox.setSelectedIndex(0);
		updatePanel.add(vehicleTypeBox);
		
		lblVehicleType_1 = new JLabel("Vehicle Type");
		lblVehicleType_1.setBounds(121, 187, 80, 14);
		updatePanel.add(lblVehicleType_1);
		
		btnUpdateBack = new JButton("Back");
		btnUpdateBack.setBounds(75, 258, 89, 23);
		btnUpdateBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				parentPanel.removeAll();
				parentPanel.add(customerPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		updatePanel.add(btnUpdateBack);
		
		btnUpdate_1 = new JButton("Update");
		btnUpdate_1.setBounds(297, 258, 89, 23);
		btnUpdate_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//do stuff
				String type = vehicleTypeBox.getSelectedItem().toString();
				double weekly = Double.parseDouble(txtWeeklyRate.getText());
				double daily = Double.parseDouble(txtDailyRate.getText());
				transCustomer.updateRates(daily, weekly, type);
				updateHolder.removeAll();
				updateHolder.add(successUpdate);
				updateHolder.repaint();
				updateHolder.validate();
			}
		});
		updatePanel.add(btnUpdate_1);
		
		lblSuccessfullyUpdatedRecord = new JLabel("Successfully Updated Record!");
		lblSuccessfullyUpdatedRecord.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSuccessfullyUpdatedRecord.setBounds(117, 138, 268, 30);
		successUpdate.add(lblSuccessfullyUpdatedRecord);
		
		btnUpdateMain = new JButton("Main");
		btnUpdateMain.setBounds(193, 238, 89, 23);
		btnUpdateMain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				parentPanel.removeAll();
				parentPanel.add(customerPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		successUpdate.add(btnUpdateMain);
		

	}
	////////////////////////////////////////////END OF UPDATE CAR RATES PANEL///////////////////////////////////////////////
	
	////////////////////////////////////////////START OF RETURNING CAR PANEL///////////////////////////////////////////////
	private void returningCar(){
		Return = new JPanel();
		parentPanel.add(Return, "name_2525022222654947");
		Return.setLayout(null);
		
		lblEnterRentalId = new JLabel("Enter Rental ID");
		lblEnterRentalId.setBounds(38, 44, 113, 14);
		Return.add(lblEnterRentalId);
		
		returnRentID = new JTextField();
		returnRentID.setBounds(38, 65, 104, 20);
		Return.add(returnRentID);
		returnRentID.setColumns(10);
		
		ReturnResults = new JPanel();
		ReturnResults.setBounds(38, 159, 405, 172);
		Return.add(ReturnResults);
		ReturnResults.setLayout(new CardLayout(0, 0));
		
		FoundReturn = new JPanel();
		FoundReturn.setLayout(null);
		
		lblAmountdue = new JLabel("Amount Due:");
		lblAmountdue.setBounds(33, 31, 81, 14);
		FoundReturn.add(lblAmountdue);
		
		label = new JLabel("$$$");
		label.setBounds(124, 31, 46, 14);
		FoundReturn.add(label);
		
		NotFoundReturn = new JPanel();
		NotFoundReturn.setLayout(null);
		
		lblRentalNotFound = new JLabel("RENTAL NOT FOUND!");
		lblRentalNotFound.setHorizontalAlignment(SwingConstants.CENTER);
		lblRentalNotFound.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRentalNotFound.setBounds(87, 69, 217, 35);
		NotFoundReturn.add(lblRentalNotFound);
		
		successReturn = new JPanel();
		successReturn.setLayout(null);
		
		lblSuccessfullyReturned = new JLabel("Successfully Returned!");
		lblSuccessfullyReturned.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuccessfullyReturned.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSuccessfullyReturned.setBounds(91, 67, 201, 31);
		successReturn.add(lblSuccessfullyReturned);
		
		
		
		submitReturnPanel = new JPanel();
		submitReturnPanel.setBounds(298, 11, 142, 117);
		submitReturnPanel.setLayout(null);
		
		btnReturn_1 = new JButton("Return");
		btnReturn_1.setBounds(26, 53, 89, 23);
		btnReturn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				transCustomer.deleteRental(Integer.parseInt(returnRentID.getText()));;
				ReturnResults.removeAll();
				ReturnResults.add(successReturn, "name_2525844589287403");
				ReturnResults.repaint();
				ReturnResults.validate();
			}
		});
		submitReturnPanel.add(btnReturn_1);
		
		
		btnCheckReturn = new JButton("Check");
		btnCheckReturn.setBounds(183, 64, 89, 23);
		btnCheckReturn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				returnCost = transCustomer.findRental(Integer.parseInt(returnRentID.getText()));
				try{
					if(returnCost.isBeforeFirst())
						localFlag = true;
					if(localFlag){
						returnCost.next();
						cost = returnCost.getDouble("AmountDue");
						label.setText(""+cost+"");
					}
				}
				catch(Exception ex){ex.printStackTrace();}
				if(localFlag){
					//do stuff
					Return.add(submitReturnPanel);
					Return.repaint();
					Return.validate();
					ReturnResults.removeAll();
					ReturnResults.add(FoundReturn, "name_2525298247644409");
					ReturnResults.repaint();
					ReturnResults.validate();
				}
				else{
					ReturnResults.removeAll();
					ReturnResults.add(NotFoundReturn, "name_2525321205992984");
					ReturnResults.repaint();
					ReturnResults.validate();
				}
			}
		});
		Return.add(btnCheckReturn);
		
		btnReturnBack = new JButton("Back");
		btnReturnBack.setBounds(183, 108, 89, 23);
		btnReturnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				parentPanel.removeAll();
				parentPanel.add(customerPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		Return.add(btnReturnBack);
	}
	////////////////////////////////////////////END OF RETURNING CAR PANEL///////////////////////////////////////////////
	
	////////////////////////////////////////////START OF ADDING AND FINSIHING CAR ADD PANEL///////////////////////////////////////////////
	/*
	 * both finishAdd and addVehiclePanel are both used for adding a vehicle.
	 * helper methods can be found at the end of these
	 */
	private void finishAdd(){
		AddedVehicle = new JPanel();
		parentPanel.add(AddedVehicle, "name_2512250924062107");
		AddedVehicle.setLayout(null);
		
		lblSuccessfullyAddedVechicle = new JLabel("Successfully Added Vehicle");
		lblSuccessfullyAddedVechicle.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuccessfullyAddedVechicle.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSuccessfullyAddedVechicle.setBounds(125, 122, 218, 34);
		AddedVehicle.add(lblSuccessfullyAddedVechicle);
		
		btnVehicleMain = new JButton("Main");
		btnVehicleMain.setBounds(193, 217, 89, 23);
		btnVehicleMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				parentPanel.removeAll();
				parentPanel.add(customerPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		AddedVehicle.add(btnVehicleMain);
	}
	private void addVehiclePanel(){
		addVehicle = new JPanel();
		parentPanel.add(addVehicle, "name_2508476451861490");
		addVehicle.setLayout(null);
		
		ownerTypes = new JPanel();
		ownerTypes.setBounds(10, 201, 174, 149);
		addVehicle.add(ownerTypes);
		ownerTypes.setLayout(new CardLayout(0, 0));
		
		Individual = new JPanel();
//		ownerTypes.add(Individual, "name_2509512896870086");
		Individual.setLayout(null);
		
		lblSsn = new JLabel("Ssn");
		lblSsn.setBounds(29, 11, 46, 14);
		Individual.add(lblSsn);
		
		txtSsn = new JTextField();
		txtSsn.setBounds(29, 25, 86, 20);
		Individual.add(txtSsn);
		txtSsn.setColumns(10);
		
		lblDriverLicenseNo = new JLabel("Driver License No.");
		lblDriverLicenseNo.setBounds(29, 56, 113, 14);
		Individual.add(lblDriverLicenseNo);
		
		txtLicense = new JTextField();
		txtLicense.setBounds(29, 73, 86, 20);
		Individual.add(txtLicense);
		txtLicense.setColumns(10);
		
		Bank = new JPanel();
//		ownerTypes.add(Bank, "name_2509520253273416");
		Bank.setLayout(null);
		
		lblRepresentative = new JLabel("Representative");
		lblRepresentative.setBounds(28, 11, 86, 14);
		Bank.add(lblRepresentative);
		
		txtRep = new JTextField();
		txtRep.setBounds(28, 29, 86, 20);
		Bank.add(txtRep);
		txtRep.setColumns(10);
		
		Company = new JPanel();
//		ownerTypes.add(Company, "name_2516134166162913");
		
		JLabel lblOwnerid = new JLabel("OwnerID");
		lblOwnerid.setBounds(36, 24, 74, 14);
		addVehicle.add(lblOwnerid);
		
		txtOwnerID = new JTextField();
		txtOwnerID.setBounds(36, 40, 89, 23);
		addVehicle.add(txtOwnerID);
		txtOwnerID.setColumns(10);
		
		JLabel lblOwnerType = new JLabel("Owner Type");
		lblOwnerType.setBounds(36, 83, 74, 14);
		addVehicle.add(lblOwnerType);
		
		JLabel lblName_1 = new JLabel("Name");
		lblName_1.setBounds(36, 145, 46, 14);
		addVehicle.add(lblName_1);
		
		txtName = new JTextField();
		txtName.setBounds(36, 160, 86, 20);
		addVehicle.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblVehicleId = new JLabel("VehicleId");
		lblVehicleId.setBounds(285, 24, 86, 14);
		addVehicle.add(lblVehicleId);
		
		txtVehicleID = new JTextField();
		txtVehicleID.setBounds(285, 41, 86, 20);
		addVehicle.add(txtVehicleID);
		txtVehicleID.setColumns(10);
		
		txtModel = new JTextField();
		txtModel.setBounds(285, 103, 86, 20);
		addVehicle.add(txtModel);
		txtModel.setColumns(10);
		
		lblVehicleModel = new JLabel("Vehicle Model");
		lblVehicleModel.setBounds(285, 83, 86, 14);
		addVehicle.add(lblVehicleModel);
		
		lblVehicleType = new JLabel("Vehicle Type");
		lblVehicleType.setBounds(285, 145, 74, 14);
		addVehicle.add(lblVehicleType);
		
		vehicleBox = new JComboBox(carTypes);
		vehicleBox.setBounds(285, 160, 86, 20);
		vehicleBox.setSelectedIndex(0);
		addVehicle.add(vehicleBox);
		
		lblVehicleYear = new JLabel("Vehicle Year");
		lblVehicleYear.setBounds(285, 201, 74, 14);
		addVehicle.add(lblVehicleYear);
		
		txtYear = new JTextField();
		txtYear.setBounds(285, 216, 86, 20);
		addVehicle.add(txtYear);
		txtYear.setColumns(10);
		
		btnBack_1 = new JButton("Back");
		btnBack_1.setBounds(238, 313, 89, 23);
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				parentPanel.removeAll();
				parentPanel.add(customerPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		addVehicle.add(btnBack_1);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(337, 313, 89, 23);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finishAdd();
				getVehicleData();
				transCustomer.addVehicle(avpd.oId, avpd.oType, avpd.address, avpd.name, avpd.rep, avpd.dr_lic, 
						avpd.ssn, avpd.vId, avpd.vType, avpd.vModel, avpd.vYear);
				parentPanel.removeAll();
				parentPanel.add(AddedVehicle);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		addVehicle.add(btnAdd);
		
		
		ownerBox = new JComboBox(ownerT);
		ownerBox.setBounds(36, 103, 89, 20);
		ownerBox.setSelectedIndex(0);
		ownerBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String type = ownerBox.getSelectedItem().toString();
				if(type.compareTo("Bank")==0){
					ownerTypes.removeAll();
					ownerTypes.add(Bank);
					ownerTypes.repaint();
					ownerTypes.validate();
				}
				else if(type.compareTo("Individual")==0){
					ownerTypes.removeAll();
					ownerTypes.add(Individual);
					ownerTypes.repaint();
					ownerTypes.validate();
				}
				else{
					ownerTypes.removeAll();
					ownerTypes.add(Company);
					ownerTypes.repaint();
					ownerTypes.validate();
				}
			}
		});
		addVehicle.add(ownerBox);
		
		lblAddress = new JLabel("Address");
		lblAddress.setBounds(285, 258, 86, 14);
		addVehicle.add(lblAddress);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(285, 272, 86, 20);
		addVehicle.add(txtAddress);
		txtAddress.setColumns(10);
	}
	private void getVehicleData(){
		avpd.oId = Integer.parseInt(txtOwnerID.getText());
		avpd.oType = ownerBox.getSelectedItem().toString();
		avpd.vId = Long.parseLong(txtVehicleID.getText());
		avpd.name = txtName.getText();
		avpd.vModel = txtModel.getText();
		avpd.vType = vehicleBox.getSelectedItem().toString();
		avpd.vYear = Integer.parseInt(txtYear.getText());
		avpd.address = txtAddress.getText();
		String type = ownerBox.getSelectedItem().toString();
		if(type.compareTo("Bank")==0)
			avpd.rep = txtRep.getText();
		else if(type.compareTo("Individual")==0){
			avpd.ssn = txtSsn.getText();
			avpd.dr_lic = Integer.parseInt(txtLicense.getText());}
	}
	
	/*
	 * creates the panel to finish adding the customer and rental
	 * This is the third panel which can only lead back to the main menu
	 */
	private void finishTransaction(){
		
		finishedCheckout = new JPanel();
		parentPanel.add(finishedCheckout, "name_2469857954615331");
		finishedCheckout.setLayout(null);
		
		JButton btnMain = new JButton("Main");
		btnMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearAllText();
				parentPanel.removeAll();
				parentPanel.add(customerPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		btnMain.setBounds(200, 237, 89, 23);
		finishedCheckout.add(btnMain);
		
		JLabel lblSuccessfullyRented = new JLabel("Successfully Rented!");
		lblSuccessfullyRented.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuccessfullyRented.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSuccessfullyRented.setBounds(133, 130, 232, 28);
		finishedCheckout.add(lblSuccessfullyRented);
	}
	private void clearAllText(){
		CustID.setText("");
		phoneNum.setText("");;
		txtDate.setText("");
		CustName.setText("");
		numDsWks.setText("");
		group.clearSelection();
		vehicleTypes.setSelectedIndex(0);
	}
	
	
	/*
	 * Panel that checks which cars are available and allows for the transaction to be completed
	 * Uses JPanel checkOut
	 */
	private void checkAvailability()
	{
		
		checkOut = new JPanel();
		parentPanel.add(checkOut, "name_2347077187953552");
		checkOut.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 23, 227, 300);
		checkOut.add(scrollPane);
		
		//creates the list and allows for user to double click in order for action to occur
		DefaultListModel listModel = new DefaultListModel();
		addToList(listModel);
		JList availableCars = new JList(listModel);
		scrollPane.setViewportView(availableCars);
		
		
		txtRate = new JTextField();
		txtRate.setBounds(340, 58, 86, 20);
		checkOut.add(txtRate);
		txtRate.setColumns(10);
		
		lblRate = new JLabel("Rate");
		lblRate.setBounds(342, 39, 46, 14);
		checkOut.add(lblRate);
		
		lblDueDate = new JLabel("Due Date");
		lblDueDate.setBounds(340, 115, 46, 14);
		checkOut.add(lblDueDate);
		
		txtDueDate = new JTextField();
		txtDueDate.setBounds(340, 133, 86, 20);
		checkOut.add(txtDueDate);
		txtDueDate.setColumns(10);
		
		txtTotal = new JTextField();
		txtTotal.setBounds(340, 197, 86, 20);
		checkOut.add(txtTotal);
		txtTotal.setColumns(10);
		
		lblTotal = new JLabel("Total");
		lblTotal.setBounds(340, 178, 46, 14);
		checkOut.add(lblTotal);
		
		btnBack = new JButton("Back");
		btnBack.setBounds(286, 304, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				parentPanel.removeAll();
				parentPanel.add(customerPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		checkOut.add(btnBack);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(385, 304, 89, 23);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				finishTransaction();
				transCustomer.addClient(cpd.cId, cpd.cName, cpd.cPhone);
				transCustomer.addRental(cpd.cDate, cpd.returnDate, priceRate, vId, cpd.cId, cpd.selection, cpd.numDsWks);
				parentPanel.removeAll();
				parentPanel.add(finishedCheckout);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		checkOut.add(btnSubmit);
		
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
//					reset();
					String selection = availableCars.getSelectedValue().toString().substring(0, 13);
					System.out.println(selection);
					populateCheckOut(selection);
				}
			}
		};
		availableCars.addMouseListener(mouseListener);
	}
	//populate the CheckOutPanel after selection
	public void populateCheckOut(String vehicleId){
		vId = Long.parseLong(vehicleId);

		try{
			rs.beforeFirst();
			while(rs.next()){
				if(vId == rs.getLong("VehicleID"))
					if(cpd.selection == 0){
						double dailyR = rs.getDouble("DailyRate");
						txtRate.setText(String.valueOf(dailyR));
						total = cpd.numDsWks * dailyR;
						priceRate = dailyR;}
					else{
						double weeklyR = rs.getDouble("WeeklyRate");
						txtRate.setText(String.valueOf(weeklyR));
						total = cpd.numDsWks * rs.getDouble("WeeklyRate");
						daysInWeeks = cpd.numDsWks*7;
						priceRate = weeklyR;}
					/*
					 * block to add to the date
					 */
//					String startDate=cpd.cDate;
//					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//					java.util.Date javaD = sdf1.parse(startDate);
//					returnDate = new java.sql.Date(javaD.getTime());
				    returnDate = cpd.sqlDate;
					Calendar c = Calendar.getInstance();
					c.setTime(returnDate);
					if(cpd.selection == 0){		c.add(Calendar.DATE, cpd.numDsWks);	}
					else{	c.add(Calendar.DATE,  daysInWeeks);	}
					returnDate = new java.sql.Date(c.getTimeInMillis());
					cpd.sqlReturn = returnDate; cpd.returnDate = returnDate.toString();
					txtDueDate.setText(returnDate.toString());
					txtTotal.setText(String.valueOf(Math.floor(total*100)/100));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//reset total and returnDate for new transaction
	private void reset(){
		total =0.0;
		returnDate = cpd.sqlDate;
		daysInWeeks = 0;
	}
	//add results from the query to the list in the GUI
	private void addToList(DefaultListModel listModel)
	{
		try{
			while(rs.next()){
				long vehicle = rs.getLong("VehicleID");
				String type = rs.getString("Type");
				String model = rs.getString("Model");
				int year = rs.getInt("Year");
				Date date = rs.getDate("ReturnDate");
				Date sD = rs.getDate("StartDate");
				String record = vehicle+" "+type+" "+model+" "+year;
				System.out.println(record);
				//snippet of code that converts a string to a SQL Date, takes the given start date from user.
				String startDate=cpd.cDate;
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date javaD = sdf1.parse(startDate);
				java.sql.Date sqlDate = new java.sql.Date(javaD.getTime());
				cpd.sqlDate = sqlDate;
				
				Calendar c = Calendar.getInstance();
				c.setTime(cpd.sqlDate);
				if(cpd.selection == 0){		c.add(Calendar.DATE, cpd.numDsWks);	}
				else{	c.add(Calendar.DATE,  daysInWeeks);	}
				returnDate = new java.sql.Date(c.getTimeInMillis());
				
				if((sqlDate.compareTo(date))>0)
					listModel.addElement(record);
			}
			rs.first();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void createCustomer()
	{
		
		
		parentPanel = new JPanel();
		parentPanel.setLayout(new CardLayout(0, 0));
		
		customerPanel = new JPanel();
		parentPanel.add(customerPanel, "name_2336906770387693");
		customerPanel.setLayout(null);
		
		btnAddVehicle = new JButton("Add Vehicle");
		btnAddVehicle.setHorizontalAlignment(SwingConstants.LEADING);
		btnAddVehicle.setBounds(36, 40, 89, 23);
		btnAddVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				addVehiclePanel();
				parentPanel.removeAll();
				parentPanel.add(addVehicle);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		customerPanel.add(btnAddVehicle);
		
		btnDeleteVehicle = new JButton("Delete");
		btnDeleteVehicle.setBounds(36, 90, 89, 23);
		btnDeleteVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				deleteRecordPanel();
				parentPanel.removeAll();
				parentPanel.add(deleteHolder);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		customerPanel.add(btnDeleteVehicle);
		
		btnReturn = new JButton("Return");
		btnReturn.setBounds(36, 140, 89, 23);
		btnReturn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				returningCar();
				parentPanel.removeAll();
				parentPanel.add(Return);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		customerPanel.add(btnReturn);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRates();
				parentPanel.removeAll();
				parentPanel.add(updateHolder);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		btnUpdate.setBounds(36, 190, 89, 23);
		customerPanel.add(btnUpdate);
		
		lblCustomerid = new JLabel("CustomerID");
		lblCustomerid.setBounds(184, 23, 76, 14);
		customerPanel.add(lblCustomerid);
		
		lblName = new JLabel("Name");
		lblName.setBounds(338, 23, 46, 14);
		customerPanel.add(lblName);
		
		CustID = new JTextField();
		CustID.setBounds(184, 41, 86, 20);
		CustID.setColumns(10);
		customerPanel.add(CustID);
		CustID.setColumns(10);
		
		CustName = new JTextField();
		CustName.setBounds(338, 41, 86, 20);
		customerPanel.add(CustName);
		CustName.setColumns(10);
		
		lblPhone = new JLabel("Phone");
		lblPhone.setBounds(184, 105, 46, 14);
		customerPanel.add(lblPhone);
		
		lblDate = new JLabel("Date");
		lblDate.setBounds(338, 105, 46, 14);
		customerPanel.add(lblDate);
		
		phoneNum = new JTextField();
		phoneNum.setBounds(184, 121, 86, 20);
		customerPanel.add(phoneNum);
		phoneNum.setColumns(10);
		
		txtDate = new JTextField();
		txtDate.setBounds(338, 121, 86, 20);
		customerPanel.add(txtDate);
		txtDate.setColumns(10);
		
		//Radio button setup
		JRadioButton rdbtnDay = new JRadioButton("");
		rdbtnDay.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnDay.setBounds(186, 217, 29, 23);
		customerPanel.add(rdbtnDay);
		
		JRadioButton rdbtnWeek = new JRadioButton("");
		rdbtnWeek.setBounds(231, 217, 29, 23);
		customerPanel.add(rdbtnWeek);
		group = new ButtonGroup();
		group.add(rdbtnDay);
		group.add(rdbtnWeek);
		//Radio Button end
		
		
		
		JLabel lblDay = new JLabel("Day");
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay.setBounds(172, 204, 46, 14);
		customerPanel.add(lblDay);
		
		JLabel lblWeek = new JLabel("Week");
		lblWeek.setBounds(231, 204, 46, 14);
		customerPanel.add(lblWeek);
		
		numDsWks = new JTextField();
		numDsWks.setBounds(184, 281, 86, 20);
		customerPanel.add(numDsWks);
		numDsWks.setColumns(10);
		
		lblEnterQuantity = new JLabel("Enter Quantity");
		lblEnterQuantity.setBounds(184, 264, 86, 14);
		customerPanel.add(lblEnterQuantity);
		
		String[] vehicleStr = {"select","All","Compact","Medium","Large","SUV","Van","Truck"};
		JLabel lblVehicletype = new JLabel("VehicleType");
		lblVehicletype.setBounds(338, 184, 89, 14);
		customerPanel.add(lblVehicletype);
		vehicleTypes = new JComboBox(vehicleStr);
		vehicleTypes.setSelectedIndex(0);
		vehicleTypes.setBounds(338, 201, 86, 20);
		customerPanel.add(vehicleTypes);
		
		btnCheck = new JButton("Check");
		btnCheck.setBounds(338, 280, 89, 23);
		btnCheck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					//pass the data from this panel to transactions to be processed
					cpd.cId = Integer.parseInt(CustID.getText());
					cpd.cName = CustName.getText();
					cpd.cPhone = phoneNum.getText();
					cpd.cDate = txtDate.getText();
					if(rdbtnDay.isSelected())
						selection = 0;
					else
						selection = 1;
					cpd.selection = selection;
					cpd.numDsWks = Integer.parseInt(numDsWks.getText());
					cpd.type = vehicleTypes.getSelectedItem().toString();
					//pass data to transaction method
					rs = transCustomer.createNewCustomer(cpd.cId, cpd.cName, cpd.cPhone, 
							cpd.cDate, cpd.selection, cpd.numDsWks, cpd.type);
					checkAvailability();
					parentPanel.removeAll();
					parentPanel.add(checkOut);
					parentPanel.repaint();
					parentPanel.validate();
				}
		});
		customerPanel.add(btnCheck);
		
		btnPrint = new JButton("Print");
		btnPrint.setBounds(36, 280, 89, 23);
		btnPrint.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				printData();
				ResultSet rs = transCustomer.printBanks();
				printBanks(rs);
				rs = transCustomer.printCompany();
				printCompany(rs);
				rs = transCustomer.printIndividuals();
				printIndividual(rs);
				rs = transCustomer.printCustomer();
				printCustomers(rs);
				rs = transCustomer.printRentals();
				printRentals(rs);
				rs = transCustomer.printCars();
				printCars(rs);
				parentPanel.removeAll();
				parentPanel.add(printPanel);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		customerPanel.add(btnPrint);
		
		btnReport = new JButton("Report");
		btnReport.setBounds(36, 237, 89, 23);
		btnReport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				printReport();
				ResultSet rs = transCustomer.printOwnerByWeek();
				printOwnerByWeek(rs);
				rs = transCustomer.printTypeByWeek();
				printTypeByWeek(rs);
				rs = transCustomer.printVehicleOwnerType();
				printVehicleOwnerType(rs);
				parentPanel.removeAll();
				parentPanel.add(reportHolder);
				parentPanel.repaint();
				parentPanel.validate();
			}
		});
		customerPanel.add(btnReport);
	}
	//method for printing weekly reports below
	private void printVehicleOwnerType(ResultSet rs){
		String record = "";
		String title = "\n\nVehicle Unit Weekly Report\n";
		String headers = "Vehicle ID \t\t"+fixString(20, "Owner ID")+"\t"+fixString(40, "Type")+"\t"+fixString(40, "Week")+
				"\t"+fixString(40, "Revenue")+"\n";
		txtAreaReport.append(title);
		txtAreaReport.append(headers);
		try{
			while(rs.next()){
				record = rs.getString("VehicleId")+" \t"+fixString(20,rs.getString("OwnerId"))+" \t"+fixString(30, rs.getString("Type"))+
						"\t"+fixString(40, rs.getString("Week"))+"\t"+fixString(40, rs.getString("Revenue"))+"\n"; 
				txtAreaReport.append(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void printTypeByWeek(ResultSet rs){
		String record = "";
		String title = "\n\nCar Type Weekly Report\n";
		String headers = "Car Type \t"+fixString(40, "Week")+"\t"+fixString(40, "Revenue")+ "\n";
		txtAreaReport.append(title);
		txtAreaReport.append(headers);
		try{
			while(rs.next()){
				record = rs.getString("Type")+" \t"+fixString(30,rs.getString("Week"))+" \t"+fixString(40, rs.getString("Revenue"))+"\n"; 
				txtAreaReport.append(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void printOwnerByWeek(ResultSet rs){
		String record = "";
		String title = "Owner Weekly Report\n";
		String headers = "Owner Id \t"+fixString(40, "Week")+"\t"+fixString(40, "Revenue")+ "\n";
		txtAreaReport.setText(title);
		txtAreaReport.append(headers);
		try{
			while(rs.next()){
				record = rs.getString("OwnerId")+" \t"+fixString(30,rs.getString("Week"))+" \t"+fixString(40, rs.getString("Revenue"))+"\n"; 
				txtAreaReport.append(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//all methods for printing are below
	private void printBanks(ResultSet rs){
		String record = "";
		String title = "Banks\n";
		String headers = "Owner Id \t"+fixString(40, "Address")+"\t"+fixString(40, "Bank Name")+ "\t Bank Representative\n";
		textArea.setText(title);
		textArea.append(headers);
		try{
			while(rs.next()){
				record = rs.getString("OwnerId")+" \t"+fixString(30,rs.getString("Address"))+" \t"+fixString(40, rs.getString("Bname"))+" \t"+rs.getString("Representative")+"\n"; 
				textArea.append(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void printCompany(ResultSet rs){
		String record = "";
		String title = "\n\nCompany\n";
		String headers = "Owner Id \t"+fixString(40, "Address")+"\t"+fixString(40, "Company Name")+ "\n";
		textArea.append(title);
		textArea.append(headers);
		try{
			rs.beforeFirst();
			while(rs.next()){
				record = rs.getString("OwnerId")+" \t"+fixString(30,rs.getString("Address"))+" \t"+fixString(40, rs.getString("Cname"))+"\n"; 
				textArea.append(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void printIndividual(ResultSet rs){
		String record = "";
		String title = "\n\nIndividuals\n";
		String headers = "Owner Id \t"+fixString(40, "Address")+"\t"+fixString(50, "SSN")+
				"\t"+fixString(40, "Name")+"\t"+fixString(40, "Drivers License")+"\n";
		textArea.append(title);
		textArea.append(headers);
		try{
			while(rs.next()){
				record = rs.getString("OwnerId")+" \t"+fixString(30,rs.getString("Address"))+" \t"+fixString(40, rs.getString("Ssn"))
				+"\t"+fixString(50, rs.getString("Name"))+"\t"+fixString(40, rs.getString("Drivers_License"))+"\n"; 
				textArea.append(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void printCustomers(ResultSet rs)
	{
		String record = "";
		String title = "\n\nCustomers\n";
		String headers = "Customer Id \t"+fixString(40, "Name")+"\t"+fixString(40, "Phone")+"\n";
		textArea.append(title);
		textArea.append(headers);
		try{
			while(rs.next()){
				record = rs.getString("IdNo")+" \t"+fixString(40,rs.getString("Name"))+" \t"+
						fixString(40, rs.getString("Phone"))+"\n"; 
				textArea.append(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void printRentals(ResultSet rs){
		String record = "";
		String title = "\n\nRentals\n";
		String headers = "Rental Id \t"+fixString(20, "Start Date")+"\t"+fixString(20, "Return Date")+"\t"
				+fixString(20, "Amount Due")+"\t"+fixString(40, "Vehicle ID")+"\t"+fixString(40, "Id No.")+"\n";
		textArea.append(title);
		textArea.append(headers);
		try{
			while(rs.next()){
				record = rs.getString("RentID")+" \t"+fixString(20,rs.getString("StartDate"))+" \t"+
						fixString(20, rs.getString("ReturnDate"))+" \t"+fixString(20,rs.getString("AmountDue"))+
						" \t"+fixString(40,rs.getString("VehicleID"))+" \t"+fixString(40,rs.getString("IdNo"))+"\n"; 
				textArea.append(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private void printCars(ResultSet rs){
		String record = "";
		String title = "\n\nCars\n";
		String headers = "Vehicle Id \t\t"+fixString(40, "Type")+"\t"+fixString(40, "Model")+"\t"
				+fixString(40, "Year")+"\t"+fixString(20, "Daily Rate")+"\t"+fixString(20, "Weekly Rate")
				+"\t"+fixString(40, "Owner ID")+"\n";
		textArea.append(title);
		textArea.append(headers);
		try{
			while(rs.next()){
				record = rs.getString("VehicleID")+" \t"+fixString(20,rs.getString("Type"))+" \t"+
						fixString(20, rs.getString("Model"))+" \t"+fixString(40,rs.getString("Year"))+
						" \t"+fixString(20,rs.getString("DailyRate"))+" \t"+fixString(20,rs.getString("WeeklyRate"))
						+" \t"+fixString(40,rs.getString("OwnerID"))+"\n"; 
				textArea.append(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//helper method to fix the size of a string buy appending empty spaces
	private String fixString(int size, String str){
		for(int i=0; i<(size-str.length()); i++){
			str = str + " ";
		}
		return str;
	}
	private String fixString2(int size, String str){
		int s = size-str.length();
		for(int i=0; i<s; i++){
			str = str + " ";
		}
		return str;
	}
	
	/*
	 * This is the class constructor used in the Main class
	 */
	public RentalDisplayBeta(Connection conn, ConnectToRentals ctr)
	{
		this.conn = conn;
		this.ctr = ctr;
		transCustomer = new Transactions(conn);
		makeFrame();
	}
	/*
	 * These are constructors to hold data from each panel
	 */
	public static class CustomerPanelData	{
		int cId;
		String cName;
		String cPhone;
		String cDate;
		String returnDate;
		java.sql.Date sqlReturn;
		java.sql.Date sqlDate;
		int selection;
		int numDsWks;
		String type;
	}
	public static class AddVehiclePanelData {
		int oId=0;
		long vId=0;
		String vType ="";
		String vModel="";
		String address="";
		int vYear=0;
		String name="";
		String ssn = "";
		int dr_lic =0;
		String rep ="";
		String oType="";
	}
}
