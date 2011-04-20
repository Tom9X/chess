package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import logic.Board;
import logic.Builder;
import logic.Piece;
import logic.PieceBuilder;
import logic.Square;
import rules.ObjectivePiece;
import rules.Rules;

/**
 * PieceCustomMenu.java
 * 
 * GUI to handle setup of pieces on the board and special square
 * properties.
 * 
 * @author Drew Hannay & Daniel Opdyke
 * 
 * CSCI 335, Wheaton College, Spring 2011
 * Phase 1
 * February 25, 2011
 */
public class CustomSetupMenu extends JPanel {
	
	
	
	/**
	 * Creates a popup box for the Promotion Options.
	 * 
	 */
	private void promotion() {
		//Create the pop up and set the size, location, and layout.
		final JFrame popup = new JFrame("Promotion Options");
		popup.setSize(500,500);
		popup.setLocationRelativeTo(null);
		popup.setLayout(new FlowLayout());
		popup.setResizable(false);

		
		// LIST - CANT PROMOTE TO
		final DefaultListModel list = new DefaultListModel();
		Object[] allPieces = PieceBuilder.getSet().toArray();
		for (int i = 0; i<allPieces.length; i++){
			list.addElement(allPieces[i]);
		}
		JList piecesList = new JList(list);
		
		// EMPTY LIST - CAN PROMOTE TO
		final DefaultListModel emptyList = new DefaultListModel();
		emptyList.addElement("");
		JList piecesList2 = new JList(emptyList);
		
		/*
		 * ARROW PANEL
		 */
		JButton moveLeft = new JButton();
		moveLeft.setText("<---");
		JButton moveRight = new JButton();
		moveRight.setText("--->");
		JPanel otherCrap = new JPanel();
		otherCrap.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.gridx = 0;
//		c.gridy = 1;
//		otherCrap.add(piecesList, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		otherCrap.add(moveRight, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		otherCrap.add(moveLeft, c);
		
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.gridx = 2;
//		c.gridy = 1;
//		otherCrap.add(canPromoteTo, c);

		// LIST - CANT PROMOTE TO
		piecesList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    piecesList.setLayoutOrientation(JList.VERTICAL);
	    piecesList.setVisibleRowCount(-1);
	    piecesList.setSelectedIndex(0);    
	    // EMPTY LIST - CAN PROMOTE TO
		piecesList2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    piecesList2.setLayoutOrientation(JList.VERTICAL);
	    piecesList2.setVisibleRowCount(-1);
	    piecesList2.setSelectedIndex(0);    




	    // LIST - CANT PROMOTE TO
		JScrollPane scrollPane = new JScrollPane(piecesList);
	    scrollPane.setPreferredSize(new Dimension(200, 200));
		
		ListSelectionModel selectList = piecesList.getSelectionModel();
		selectList.addListSelectionListener(new ListSelectionListener(){
			//TODO
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				
				
				
			}
		});

		
		// EMPTY LIST - CAN PROMOTE TO
		JScrollPane scrollPane2 = new JScrollPane(piecesList2);
	    scrollPane.setPreferredSize(new Dimension(200, 200));
		
		ListSelectionModel selectList2 = piecesList2.getSelectionModel();
		selectList2.addListSelectionListener(new ListSelectionListener(){
			//TODO
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				
				
				
			}
		});
		
		
		///////////////////////////////////////////////////////////////
		/*
		 * Add the Submit and Back Buttons.
		 */
		JButton submitButton = new JButton("Ok");
		submitButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					//squareOptions();
					popup.dispose();
				}

			});
		/*
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			}
		});
		*/
		// This adds a new Panel with the option buttons.
		JPanel options = new JPanel();
		options.setLayout(new GridBagLayout());
		options.setSize(50, 50);
		GridBagConstraints cee = new GridBagConstraints();
		
		cee.fill = GridBagConstraints.HORIZONTAL;
		cee.gridx = 0;
		cee.gridy = 1;
		options.add(submitButton, cee);
		
		
		/*
		cee.fill = GridBagConstraints.HORIZONTAL;
		cee.gridx = 0;
		cee.gridy = 2;
		options.add(backButton, cee);
		*/
		
		
		// Add this to the popup.
		// LIST - CANT PROMOTE TO
		popup.add(scrollPane);
		popup.add(otherCrap);
		// EMPTY LIST - CAN PROMOTE TO
		popup.add(scrollPane2);
		popup.add(options);

		
		//Finally, set the pop up to visible.
		popup.setVisible(true);
	}
	
	/**
	 * SetUpListener
	 * 
	 * ActionListener to handle board setup.
	 * 
	 * @author Drew Hannay & Daniel Opdyke
	 */
	
	/**
	 * Int to hold how many objective pieces have been placed
	 */
	private int objectives =0;
	/**
	 * Int to hold the amount of allowable objectives
	 */
	private int allowableObjectives = 1;
	/**
	 * Rules holder for the white rules
	 */
	private Rules whiteRules;
	/**
	 * Rules holder for the black rules
	 */
	private Rules blackRules;
	/**
	 * Constructor.
	 * Initialize the ArrayLists and call initComponents to initialize the GUI.
	 * @param b The builder which is creating the new game type.
	 * @param whiteRules The whiteRules object.
	 * @param blackRules The blackRules object.
	 */
	public CustomSetupMenu(Builder b, Rules whiteRules, Rules blackRules) {
		this.b = b;
		whiteTeam = new ArrayList<Piece>();
		blackTeam = new ArrayList<Piece>();
		this.whiteRules = whiteRules;
		this.blackRules = blackRules;
		initComponents();
	}
	
	/**
	 * @author 
	 *
	 */
	class SetUpListener implements ActionListener {

		/**
		 * The Square we are setting up.
		 */
		private Square square;

		/**
		 * Constructor
		 * Set instance variables to passed parameters
		 * @param square The square we are setting up.
		 * @param board The board on which the square resides.
		 */
		public SetUpListener(Square square) {
			this.square = square;
		}

		/**
		 * Upon clicking, call the options method passing the square
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (square.isOccupied()==false){
			options();
			}
		}
		

		/**
		 * Create a pop up window with customization options.
		 * Let the user choose between adding a piece to the square or
		 * customizing options of the piece.
		 */
		private void options() {
			//Create the pop up and set the size, location and layout.
			final JFrame popup = new JFrame("Customize");
			popup.setSize(300, 90);
			popup.setLocationRelativeTo(null);
			popup.setLayout(new FlowLayout());
			popup.setResizable(false);

			//Create button and add ActionListener
			final JButton squareButton = new JButton("Customize options for this square");
			squareButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					squareOptions();
					popup.dispose();
				}

			});
			popup.add(squareButton);
			
			//Finally, set the pop up to visible.
			popup.setVisible(true);
		}

		/**
		 * GUI to collect the options desired for a Square.
		 * Make the pop up, create the components and add their
		 * ActionListeners to collect information.
		 */
		private void squareOptions() {
			//Create the pop up and set the size, location and layout.
			final JFrame popup = new JFrame("Square Options");
			popup.setSize(370, 120);
			popup.setLocationRelativeTo(null);
			popup.setLayout(new FlowLayout());

			//Create a JButton to hold the JColorChooser.
			final JButton pickColor = new JButton("Set Square Color");
			pickColor.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Color color = JColorChooser.showDialog(popup, "Choose Color", square.getColor());
					if (color == null) return;
					if (color != Square.HIGHLIGHT_COLOR) {//Can't let them pick exactly the highlight color, or they could move to that space from anywhere.
						square.setBackgroundColor(color);
						pickColor.setBackground(color);
						//TODO Weird issue if you hit cancel when selecting a color.
					}
					else {
						//The chances of this happening is EXTREMELY small...
						JOptionPane.showMessageDialog(popup, "That color cannot be selected.",
								"Invalid Color", JOptionPane.INFORMATION_MESSAGE);
					}

				}
			});
			popup.add(pickColor);

			//Create the JCheckBox and add it to the board.
			final JCheckBox uninhabitable = new JCheckBox("Uninhabitable", !square.isHabitable());
			popup.add(uninhabitable);

			//Create button and add ActionListener
			final JButton done = new JButton("Done");
			done.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					//Set the Square as habitable or not, then dispose of the pop up.
					square.setHabitable(!uninhabitable.isSelected());
					popup.dispose();
				}
			});
			popup.add(done);

			//Finally, set the pop up as visible.
			popup.setVisible(true);

		}

	}

	/**
	 * @author 
	 *
	 */
	class SetUpMouseListener implements MouseListener {
		//TODO
		/**
		 * The Square we are setting up.
		 */
		private Square square;
		/**
		 * The square holder for the piece being displayed
		 */
		private Square option;
		/**
		 * The Board on which the Square we are setting up resides.
		 */
		private Board board;
		/**
		 * @param square
		 * @param board
		 * @param option
		 */
		public SetUpMouseListener(Square square, Board board, Square option){
			this.square = square;
			this.board = board;
			this.option = option;
		}
		
		@Override
		public void mouseClicked(MouseEvent e){
			
			int mods = e.getModifiers();
					if (mods == 16){
						setPieceOnBoard(false);
					}
					else if (mods == 4){
						setPieceOnBoard(true);
					}
					else if (mods == 8 || mods == 20){
						square.setPiece(null);
						square.refresh();
					}
		}

		/**
		 * @param isBlack
		 */
		private void setPieceOnBoard(boolean isBlack){
			if (option.isOccupied() == false){
				if ((option.getColor().equals(Color.LIGHT_GRAY) == false) && (option.getColor().equals(Color.getHSBColor(30, 70, 70)) == false)){
					square.setBackgroundColor(option.getColor());
				}
				if (option.isHabitable() == false){
					square.setPiece(option.getPiece());
				}
				square.setHabitable(option.isHabitable());
				square.refresh();
			}
			else{
				if (square.isHabitable() == true){
					Piece p = PieceBuilder.makePiece(option.getPiece().getName(), isBlack, square, board);
//					if(whiteRules.objectivePiece(isBlack) == p) {
//						objectives++;
//						RuleMaker.needsObj = false;
//					}
//					if(allowableObjectives < objectives){
//						JOptionPane.showMessageDialog(null, "You cannot place any more objective pieces");
//						return;
//					}
					if (isBlack)
						blackTeam.add(p);
					else
						whiteTeam.add(p);
					square.setPiece(p);
					square.refresh();
				}
				else{
					JOptionPane.showMessageDialog(null,
						    "This square is uninhabitable.",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);

				}
			}
		}
		
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		
	}

	/**
	 * Generated Serial Version ID
	 */
	private static final long serialVersionUID = 7830479492072657640L;
	// Variables declaration - do not modify
	/**
	 * Builder used to progressively create the new game type.
	 */
	private Builder b;
	/**
	 * ArrayList to hold the pieces on the white team.
	 */
	private ArrayList<Piece> whiteTeam;
	/**
	 * ArrayList to hold the pieces on the black team.
	 */
	private ArrayList<Piece> blackTeam;

	/**
	 * JButton to return to previous screen.
	 */
	private JButton backButton;

	// End of variables declaration

	/**
	 * JButton to submit Board setup and return to the main screen.
	 */
	private JButton submitButton;


	/**
	 * Initialize components of the GUI
	 * Create all the GUI components, set their specific properties and add them to the 
	 * window. Also add any necessary ActionListeners.
	 * @param whiteRules the rules for white team.
	 * @param blackRules the rules for black team.
	 */
	private void initComponents() {

		//Set the layout of this JPanel.
		setLayout(new FlowLayout());
		
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		//Get the array of boards from the builder so we can modify it.
		final Board[] boards = b.getBoards();
		
		 // Create a List with a vertical ScrollBar
		final DefaultListModel list = new DefaultListModel();
		Object[] allPieces = PieceBuilder.getSet().toArray();
		for (int i = 0; i<allPieces.length; i++){
			list.addElement(allPieces[i]);
		}
		list.addElement("Square Options");
//		list.addElement("Remove Piece");
	    final JList piecesList = new JList(list);
	    
		final Board bShowPiece = new Board(2,1,false);
		final JPanel showPiece = new JPanel();
		showPiece.setLayout(new GridLayout(2,1));
		showPiece.setPreferredSize(new Dimension(50,100));
		
		final JButton jb1 = new JButton();
		final JButton jb2 = new JButton();
		jb1.addActionListener(new SetUpListener(bShowPiece.getSquare(1, 1)));
		bShowPiece.getSquare(1, 1).setButton(jb1);
		bShowPiece.getSquare(2, 1).setButton(jb2);//Let the Square know which button it owns.
		showPiece.add(jb1);
		showPiece.add(jb2);
		bShowPiece.getSquare(1, 1).refresh();
		bShowPiece.getSquare(2, 1).refresh();
	    
	    piecesList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    piecesList.setLayoutOrientation(JList.VERTICAL);
	    piecesList.setVisibleRowCount(-1);
	    piecesList.setSelectedIndex(0);
	    Piece toAdd = PieceBuilder.makePiece((String) list.elementAt(0), true, bShowPiece.getSquare(1,1), bShowPiece);
	    Piece toAdd1 = PieceBuilder.makePiece((String) list.elementAt(0), false, bShowPiece.getSquare(1,1), bShowPiece);
    	bShowPiece.getSquare(1, 1).setPiece(toAdd);
    	bShowPiece.getSquare(2, 1).setPiece(toAdd1);
    	bShowPiece.getSquare(1, 1).refresh();
    	bShowPiece.getSquare(2, 1).refresh();
	    
	    ListSelectionModel selectList = piecesList.getSelectionModel();
	    final Color original = bShowPiece.getSquare(1, 1).getColor();
	    selectList.addListSelectionListener(new ListSelectionListener(){
	    	//TODO
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				
		        int selection = lsm.getAnchorSelectionIndex();
		        if (!lsm.getValueIsAdjusting()){
			       	if (((String) list.elementAt(selection)).equals("Square Options")){
			       			bShowPiece.getSquare(1, 1).setPiece(null);
			       			bShowPiece.getSquare(1, 1).setBackgroundColor(original);
			       			bShowPiece.getSquare(1, 1).setHabitable(true);
			       			bShowPiece.getSquare(1, 1).refresh();
			       			jb1.setVisible(true);
			       			jb2.setVisible(false);
			       	}
//			       	else if (((String) list.elementAt(selection)).equals("Remove Piece")){
//
//		       			bShowPiece.getSquare(1, 1).refresh();
//			       		jb2.setVisible(false);
//			       		jb1.setVisible(false);
//			       	}
			        else{
			        	jb2.setVisible(true);
			        	jb1.setVisible(true);
			        	if (bShowPiece.getSquare(1, 1).isHabitable() == false)
			        		bShowPiece.getSquare(1, 1).setHabitable(true);
			        	if (bShowPiece.getSquare(1, 1).getColor().equals(original) == false)
			        		bShowPiece.getSquare(1, 1).setBackgroundColor(original);	
			        	Piece toAdd = PieceBuilder.makePiece((String) list.elementAt(selection), true, bShowPiece.getSquare(1,1), bShowPiece);
			        	Piece toAdd1 = PieceBuilder.makePiece((String) list.elementAt(selection), false, bShowPiece.getSquare(1,1), bShowPiece);
			        	bShowPiece.getSquare(1, 1).setPiece(toAdd);
			        	bShowPiece.getSquare(2, 1).setPiece(toAdd1);
			        	bShowPiece.getSquare(1, 1).refresh();
			        	bShowPiece.getSquare(2, 1).refresh();
		       		}
		        }
			}
	    });

	    JScrollPane scrollPane = new JScrollPane(piecesList);
	    scrollPane.setPreferredSize(new Dimension(200, 200));
		//Loop through the array of boards for setup.
		for (int n = 0; n < boards.length; n++) {
			//Create a JPanel to hold the grid and set the layout to the number of squares in the board.
			final JPanel grid = new JPanel();
			grid.setLayout(new GridLayout(boards[n].numRows(), boards[n].numCols()));
			//Set the size of the grid to the number of rows and columns, scaled by 48, the size of the images.
			grid.setPreferredSize(new Dimension(boards[n].numCols() * 48, boards[n].numRows() * 48));

			//Loop through the board, initializing each Square and adding it's ActionListener.
			int numRows = boards[n].numRows();
			int numCols = boards[n].numCols();
			for (int i = numRows; i > 0; i--) {
				for (int j = 1; j <= numCols; j++) {
					JButton jb = new JButton();
					jb.addMouseListener(new SetUpMouseListener(boards[n].getSquare(i, j), boards[n], bShowPiece.getSquare(1, 1)));
					boards[n].getSquare(i, j).setButton(jb);//Let the Square know which button it owns.
					grid.add(jb);//Add the button to the grid.
					boards[n].getSquare(i, j).refresh(); //Have the square refresh all it's properties now that they're created.
				}
			}
			add(grid);//Add the grid to the main JPanel.
		}

	    add(scrollPane);
	    add(showPiece);
	    
		//Create button and add ActionListener
		backButton = new JButton("Back");


		//Create button and add ActionListener
		submitButton = new JButton("Save and Quit");
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(RuleMaker.needsObj == true){
					JOptionPane.showMessageDialog(null, "You must select a piece to be an objective.");
					return;
				}
				b.whiteTeam = whiteTeam;
				boolean set = false;
				for (Piece p : whiteTeam) {
					if (p.getName().equals("King")) {
						whiteRules.setObjectivePiece(new ObjectivePiece("classic", "King"));
						set = true;
						break;
					}
					//TODO Fix this.
//					else if (p.isObjective()) {
//						whiteRules.setObjectivePiece(new ObjectivePiece("custom objective", p.getName()));
//						System.out.println(p.getName());
//						set = true;
//						break;
//					}
				}
				if (!set) {
					whiteRules.setObjectivePiece(new ObjectivePiece("no objective", ""));
				}
				b.blackTeam = blackTeam;
				set = false;
				for (Piece p : blackTeam) {
					if (p.getName().equals("King")) {
						blackRules.setObjectivePiece(new ObjectivePiece("classic", "King"));
						set = true;
						break;
					}
					//TODO Fix this
//					else if (p.isObjective()) {
//						blackRules.setObjectivePiece(new ObjectivePiece("custom objective", p.getName()));
//						set = true;
//						break;
//					}
				}
				if (!set) {
					blackRules.setObjectivePiece(new ObjectivePiece("no objective", ""));
				}
				b.writeFile(whiteRules, blackRules);
				//Return to the main screen.
				Driver.getInstance().revertPanel();
			}

		});

		
		
		
		
		JButton changePromote = new JButton("Promotion Properties");
		changePromote.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				promotion();
			}
			
		});
		
		JPanel options = new JPanel();
		options.setBorder(BorderFactory.createTitledBorder("Options"));
		options.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		options.add(changePromote, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		options.add(submitButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		options.add(backButton, c);
		
		add(options);
	}
}
