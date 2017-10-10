package tester;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.ACIColor;
import core.ACIColorList;
import core.CheckBoxWithText;
import core.MemoryTextField;
import core.Point3D;
import core.RadioButton;
import toolkit.Toolset;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;

/** 
 * Copyright 2017 Olumide Igbiloba (https://github.com/enocholumide/Laserscan-to-dxf)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Laserscan to dxf converter: Converts laser scan data to AutoCAD. dxf file format
 *
 * @author Olumdie Igbiloba (https://github.com/enocholumide/Laserscan-to-dxf)
 * 
 * @version 1.0.1 basic
 */
public class Converter extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6645624735229012592L;
	private JFrame frame;
	private MemoryTextField textField;
	private MemoryTextField textOutputDir;
	
	private File ptsFile, scrFile, dxfFile;
	
	BufferedWriter dxf = null;
	private JTextField otherDelimeter;
	
	private ButtonGroup fileFormatButtonGroup = new ButtonGroup();
	private ButtonGroup delimeterButtonGroup = new ButtonGroup();
	
	private JFileChooser fileChooser, dirChooser;
	
	private JCheckBox exportDxfColorCheckBox, openDxfCheckBox;
	private JTextField startTextField;
	private JTextField endTextField;
	private JTextField intervalTextField;;
	
	private CheckBoxWithText startNumberChkbox, endNumberChkbox, useIntervalChkBox;
	private MemoryTextField minX;
	private MemoryTextField minY;
	private MemoryTextField maxX;
	private MemoryTextField maxY;
	
	private BufferedReader reader = null;
	private ACIColorList ACIColors;
	
	private JButton btnConvert;
	private String inputExtension = "pts";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Converter window = new Converter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Converter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		addWindowListener(new WindowAdapter() {
			@Override 
			public void windowClosing(WindowEvent e) { 
				handleWindowClosingEvent();
			} 
		});
		
		ItemListener radioButtonItemListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				handleRadioButtonItemStateChanged(e);
			}
			
		};
		
		setUpRGB_ACI();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension appDim = new Dimension(638, 552);
		
		frame = new JFrame("Laser scan to dxf");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds((int) (screenSize.getWidth()/2) - ((int)appDim.getWidth()/2), (int)screenSize.getHeight()/2 - (int)appDim.getHeight()/2, (int)appDim.getWidth(), (int)appDim.getHeight());
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		fileChooser = new JFileChooser();
		dirChooser = new JFileChooser();
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
		JLabel Title = new JLabel("Laser scan to dxf");
		Title.setBackground(Color.WHITE);
		Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		Title.setBounds(28, 29, 185, 33);
		frame.getContentPane().add(Title);
		
		JSeparator startSeparator = new JSeparator();
		startSeparator.setBounds(0, 150, 621, 2);
		startSeparator.setForeground(new Color(105, 105, 105));
		frame.getContentPane().add(startSeparator);
		
		JLabel lblConvertLaserScan = new JLabel("Converts laser scan data to AutoCAD dxf file format");
		lblConvertLaserScan.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConvertLaserScan.setBackground(Color.WHITE);
		lblConvertLaserScan.setBounds(54, 79, 296, 33);
		frame.getContentPane().add(lblConvertLaserScan);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 150, 621, 363);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JPanel inputFileFormatPanel = new JPanel();
		inputFileFormatPanel.setBounds(10, 23, 158, 110);
		panel_2.add(inputFileFormatPanel);
		inputFileFormatPanel.setBorder(new TitledBorder(null, "Input File Format", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		inputFileFormatPanel.setLayout(null);
		
		RadioButton rdbtnPTS = new RadioButton("PTS", "pts/ ");
		rdbtnPTS.setSelected(true);
		rdbtnPTS.setBounds(6, 28, 52, 23);
		rdbtnPTS.addItemListener(radioButtonItemListener);
		fileFormatButtonGroup.add(rdbtnPTS);
		inputFileFormatPanel.add(rdbtnPTS);

		
		RadioButton rdbtnXYZ = new RadioButton("XYZ", "xyz/ ");
		rdbtnXYZ.setBounds(6, 54, 65, 23);
		rdbtnXYZ.addItemListener(radioButtonItemListener);
		fileFormatButtonGroup.add(rdbtnXYZ);
		inputFileFormatPanel.add(rdbtnXYZ);
		
		RadioButton rdbtnLAZ = new RadioButton("LAZ", "laz/ ");
		rdbtnLAZ.setBounds(6, 80, 65, 23);
		rdbtnLAZ.addItemListener(radioButtonItemListener);
		fileFormatButtonGroup.add(rdbtnLAZ);
		inputFileFormatPanel.add(rdbtnLAZ);
		
		RadioButton rdbtnSVY = new RadioButton("SVY", "svy/,");
		rdbtnSVY.setBounds(73, 54, 52, 23);
		rdbtnSVY.addItemListener(radioButtonItemListener);
		fileFormatButtonGroup.add(rdbtnSVY);
		inputFileFormatPanel.add(rdbtnSVY);
		
		RadioButton rdbtnPTX = new RadioButton("PTX", "ptx/ ");
		rdbtnPTX.setBounds(73, 28, 52, 23);
		rdbtnPTX.addItemListener(radioButtonItemListener);
		fileFormatButtonGroup.add(rdbtnPTX);
		inputFileFormatPanel.add(rdbtnPTX);
		
		JPanel fileInputAndOutputPanel = new JPanel();
		fileInputAndOutputPanel.setBorder(new TitledBorder(null, "Select file and output directory", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		fileInputAndOutputPanel.setBounds(178, 23, 433, 110);
		panel_2.add(fileInputAndOutputPanel);
		fileInputAndOutputPanel.setLayout(null);
		
		textField = new MemoryTextField("Upload pts file...");
		textField.setBounds(10, 23, 305, 33);
		fileInputAndOutputPanel.add(textField);
		textField.setEditable(false);
		textField.setColumns(10);
		
		JButton btnUpload = new JButton("Upload");
		btnUpload.setBounds(325, 23, 98, 33);
		fileInputAndOutputPanel.add(btnUpload);
		btnUpload.setBackground(Color.BLACK);
		btnUpload.setForeground(Color.WHITE);
		
		textOutputDir = new MemoryTextField("Select output directory...");
		textOutputDir.setEnabled(false);
		textOutputDir.setBounds(10, 67, 305, 33);
		fileInputAndOutputPanel.add(textOutputDir);
		textOutputDir.setColumns(10);
		
		JButton btnDirectory = new JButton("Save as..");
		btnDirectory.setEnabled(false);
		btnDirectory.setBounds(325, 67, 98, 33);
		fileInputAndOutputPanel.add(btnDirectory);
		
		btnDirectory.setBackground(Color.BLACK);
		btnDirectory.setForeground(Color.WHITE);
		
		JSeparator endSeparator = new JSeparator();
		endSeparator.setForeground(new Color(105, 105, 105));
		endSeparator.setBounds(10, 305, 594, 2);
		panel_2.add(endSeparator);
		
		btnConvert = new JButton("Convert");
		btnConvert.setBounds(501, 318, 103, 33);
		panel_2.add(btnConvert);
		btnConvert.setEnabled(false);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setForeground(SystemColor.textHighlightText);
		btnCancel.setBackground(new Color(255, 69, 0));
		btnCancel.setBounds(388, 318, 103, 33);
		panel_2.add(btnCancel);
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				handleWindowClosingEvent();
			}
			
		});
		
		JPanel delimeterAndSeparatorPanel = new JPanel();
		delimeterAndSeparatorPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Delimeter and Separator", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		delimeterAndSeparatorPanel.setBounds(10, 144, 158, 150);
		panel_2.add(delimeterAndSeparatorPanel);
		delimeterAndSeparatorPanel.setLayout(null);
		
		RadioButton rdbtnComma = new RadioButton("Comma", ",");
		rdbtnComma.setBounds(6, 24, 71, 23);
		delimeterButtonGroup.add(rdbtnComma);
		delimeterAndSeparatorPanel.add(rdbtnComma);
		
		RadioButton rdbtnDefault = new RadioButton("Default", "Default");
		rdbtnDefault.setSelected(true);
		rdbtnDefault.setBounds(79, 24, 69, 23);
		delimeterButtonGroup.add(rdbtnDefault);
		delimeterAndSeparatorPanel.add(rdbtnDefault);
		
		RadioButton rdbtnOther = new RadioButton("Other", " ");
		rdbtnOther.setBounds(6, 50, 60, 23);
		delimeterButtonGroup.add(rdbtnOther);
		delimeterAndSeparatorPanel.add(rdbtnOther);
			
		rdbtnOther.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					otherDelimeter.setEnabled(true);
					otherDelimeter.setEditable(true);
			    }
			    else if (e.getStateChange() == ItemEvent.DESELECTED) {
			    	otherDelimeter.setEnabled(false);
					otherDelimeter.setEditable(false);
			    }
			}
			
		});
		
		otherDelimeter = new JTextField();
		otherDelimeter.setEditable(false);
		otherDelimeter.setEnabled(false);
		otherDelimeter.setBounds(79, 51, 33, 20);
		delimeterAndSeparatorPanel.add(otherDelimeter);
		otherDelimeter.setColumns(10);
		
		otherDelimeter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {}

			@Override
			public void insertUpdate(DocumentEvent e) {;
				rdbtnOther.setActionCommand(otherDelimeter.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {}
			
		});
		
		JPanel panel_7 = new JPanel();
		panel_7.setToolTipText("<html>\r\nSpecify custom decimal separator<br>\r\ne.g.<br> \r\n50.02 -> point <br>\r\n50,02 -> comma<br>\r\n</html>");
		panel_7.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Decimal separator", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_7.setBounds(6, 87, 142, 52);
		delimeterAndSeparatorPanel.add(panel_7);
		panel_7.setLayout(null);
		
		JComboBox<Object> cmbSeparator = new JComboBox<Object>();
		cmbSeparator.setModel(new DefaultComboBoxModel<Object>(new String[] {"point<.>"}));
		cmbSeparator.setBounds(10, 21, 118, 20);
		panel_7.add(cmbSeparator);
		
		JPanel dxfOptionPanel = new JPanel();
		dxfOptionPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dxf Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		dxfOptionPanel.setBounds(178, 143, 158, 150);
		panel_2.add(dxfOptionPanel);
		dxfOptionPanel.setLayout(null);
		
		JPanel optimizePanel = new JPanel();
		optimizePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Optimize for version", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		optimizePanel.setBounds(10, 87, 138, 52);
		dxfOptionPanel.add(optimizePanel);
		optimizePanel.setLayout(null);
		
		JComboBox<Object> cmbOptimizeVersion = new JComboBox<Object>();
		cmbOptimizeVersion.setModel(new DefaultComboBoxModel<Object>(new String[] {"2007"}));
		cmbOptimizeVersion.setBounds(10, 21, 118, 20);
		optimizePanel.add(cmbOptimizeVersion);
		
		exportDxfColorCheckBox = new JCheckBox("Export color");
		exportDxfColorCheckBox.setToolTipText("<html>\r\nImport color from the laser scan file.\r\n</html>");
		exportDxfColorCheckBox.setSelected(true);
		exportDxfColorCheckBox.setBounds(10, 26, 97, 23);
		dxfOptionPanel.add(exportDxfColorCheckBox);
		
		openDxfCheckBox = new JCheckBox("Open dxf on finish");
		openDxfCheckBox.setToolTipText("<html>\r\nOpen the created dxf file with the default program on<br>\r\nyour computer<br>\r\n</html>");
		openDxfCheckBox.setBounds(10, 52, 138, 23);
		dxfOptionPanel.add(openDxfCheckBox);
		
		JLabel lblCredits = new JLabel("(c) 2017 Olumide Igbiloba (https://github.com/enocholumide/Laserscan-to-dxf)");
		lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCredits.setBounds(10, 337, 368, 14);
		panel_2.add(lblCredits);
		
		JLabel lblLicence = new JLabel("Licensed under the Apache License, Version 2.0");
		lblLicence.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLicence.setBounds(10, 318, 326, 14);
		panel_2.add(lblLicence);
		
		JPanel processingPanel = new JPanel();
		processingPanel.setLayout(null);
		processingPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Data processing options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		processingPanel.setBounds(347, 144, 158, 150);
		panel_2.add(processingPanel);
		
		startNumberChkbox = new CheckBoxWithText("Start at");
		startNumberChkbox.setToolTipText("<html>\r\nIf start number is not selected, default of 1 will be used.<br>\r\nThis means that the points will be proccessed from the second line on the file.<br>\r\n Index of 0 represents the first line.<br>\r\n</html>");
		startNumberChkbox.setSelected(true);
		startNumberChkbox.setBounds(10, 26, 88, 23);
		processingPanel.add(startNumberChkbox);
		
		endNumberChkbox = new CheckBoxWithText("End at");
		endNumberChkbox.setToolTipText("<html>\r\nSpecify the limit of the processed points.<br>\r\nIf end number is not selected, all the points in the file will be proccessed.<br>\r\n</html>");
		endNumberChkbox.setBounds(10, 52, 88, 23);
		processingPanel.add(endNumberChkbox);
		
		useIntervalChkBox = new CheckBoxWithText("Use interval");
		useIntervalChkBox.setToolTipText("<html>\r\nSpecify interval to reduce the number of points<br>\r\nIf no interval is specified, a normal increment of 1 will be used<br>\r\n</html>");
		useIntervalChkBox.setBounds(10, 78, 97, 23);
		processingPanel.add(useIntervalChkBox);
		
		startTextField = new JTextField();
		startTextField.setText("1");
		startTextField.setColumns(10);
		startTextField.setBounds(115, 27, 33, 20);
		startNumberChkbox.setTextField(startTextField);
		processingPanel.add(startTextField);
		
		endTextField = new JTextField();
		endTextField.setColumns(10);
		endTextField.setBounds(115, 53, 33, 20);
		endNumberChkbox.setTextField(endTextField);
		processingPanel.add(endTextField);
		
		intervalTextField = new JTextField();
		intervalTextField.setColumns(10);
		intervalTextField.setBounds(115, 79, 33, 20);
		useIntervalChkBox.setTextField(intervalTextField);
		processingPanel.add(intervalTextField);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 110, 138, 2);
		processingPanel.add(separator);
		
		JCheckBox showInfoChckBox = new JCheckBox("Use interval");
		showInfoChckBox.setEnabled(false);
		showInfoChckBox.setText("Show data info");
		showInfoChckBox.setToolTipText("<html>\r\nSpecify interval to reduce the number of points<br>\r\nIf no interval is specified, a normal increment of 1 will be used<br>\r\n</html>");
		showInfoChckBox.setBounds(10, 119, 138, 23);
		processingPanel.add(showInfoChckBox);
		
		showInfoChckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if (e.getStateChange() == ItemEvent.SELECTED) {
					getDataStatistics();
				} else if(e.getStateChange() == ItemEvent.DESELECTED) {
					
				}
			}
			
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Data info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(515, 144, 96, 150);
		panel_2.add(panel);
		panel.setLayout(null);
		
		JLabel lblMinX = new JLabel("Min X");
		lblMinX.setEnabled(false);
		lblMinX.setBounds(10, 32, 33, 14);
		panel.add(lblMinX);
		
		JLabel lblMinY = new JLabel("Min Y");
		lblMinY.setEnabled(false);
		lblMinY.setBounds(10, 57, 33, 14);
		panel.add(lblMinY);
		
		JLabel lblMaxZ = new JLabel("Max X");
		lblMaxZ.setEnabled(false);
		lblMaxZ.setBounds(10, 82, 44, 14);
		panel.add(lblMaxZ);
		
		minX = new MemoryTextField("-", showInfoChckBox);
		minX.setEditable(false);
		minX.setText("-");
		minX.setColumns(10);
		minX.setBounds(53, 27, 33, 20);
		panel.add(minX);
		
		minY = new MemoryTextField("-", showInfoChckBox);
		minY.setEditable(false);
		minY.setColumns(10);
		minY.setBounds(53, 53, 33, 20);
		panel.add(minY);
		
		maxX = new MemoryTextField("-", showInfoChckBox);
		maxX.setEditable(false);
		maxX.setColumns(10);
		maxX.setBounds(53, 79, 33, 20);
		panel.add(maxX);
		
		JLabel lblMax = new JLabel("Max Y");
		lblMax.setEnabled(false);
		lblMax.setBounds(10, 110, 44, 14);
		panel.add(lblMax);
		
		maxY = new MemoryTextField("-", showInfoChckBox);
		maxY.setEditable(false);
		maxY.setColumns(10);
		maxY.setBounds(53, 107, 33, 20);
		panel.add(maxY);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Converter.class.getResource("/images/dxf-512.png")));
		lblNewLabel.setBounds(521, 11, 91, 128);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("v.1.0 basic");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(28, 57, 85, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Converter.class.getResource("/images/laser.png")));
		lblNewLabel_2.setBounds(426, 11, 85, 128);
		frame.getContentPane().add(lblNewLabel_2);
		
		btnConvert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!(textField.isOriginalTextField() && textOutputDir.isOriginalTextField())) {
					generateDXF(ptsFile);
				} else {
					JOptionPane.showMessageDialog(null, "Error: Check input and output files", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
			
		});
		
		btnDirectory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = dirChooser.showOpenDialog(Converter.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					textOutputDir.setText(dirChooser.getCurrentDirectory().getAbsolutePath()+"\\"+fileChooser.getName(fileChooser.getSelectedFile())+".dxf");
				}
			}
			
		});
		
		
		
		btnUpload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String fileFormat = inputExtension;
				FileNameExtensionFilter filter = new FileNameExtensionFilter(fileFormat + " files Only", fileFormat);
				fileChooser.setFileFilter(filter);
				
				int returnVal = fileChooser.showOpenDialog(Converter.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					
			        try {
						ptsFile = fileChooser.getSelectedFile();
						
						textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
						btnDirectory.setEnabled(true);
						textOutputDir.setEnabled(true);
						textOutputDir.setText(textField.getText()+".dxf");
						
						btnConvert.setEnabled(true);
						btnConvert.setForeground(SystemColor.textHighlightText);
						btnConvert.setBackground(SystemColor.infoText);
												
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
			}	
		});	
		
		
	}
	
	protected void handleRadioButtonItemStateChanged(ItemEvent e) {
		
		if(e.getStateChange() == ItemEvent.SELECTED) {
			
			RadioButton button = (RadioButton) e.getSource();
			System.out.println(button.getActionCommand().split("/")[0]);
			this.inputExtension = button.getActionCommand().split("/")[0];
			
			textField.setToOriginalText();
			textOutputDir.setToOriginalText();
			btnConvert.setEnabled(false);
			
			String buttonName = button.getName();
			if(buttonName.equals("SVY")||buttonName.equals("XYZ")||buttonName.equals("LAZ")||buttonName.equals("PTX")) {
				exportDxfColorCheckBox.setSelected(false);
				exportDxfColorCheckBox.setEnabled(false);
			} else if(buttonName.equals("PTS")) {
				exportDxfColorCheckBox.setEnabled(true);
			}	
		}
	}
	
	/**
	 * Sets up ACI-RGB color map
	 * To be depreciated in further version release.
	 */
	private void setUpRGB_ACI() {

        BufferedReader reader = null;
        String line = "";
        
        List<ACIColor> colorsList = new ArrayList<ACIColor>();

        try {
        	
        	reader = new BufferedReader(new FileReader("src/ACI_RGB_v2.csv"));
        	
            while ((line = reader.readLine()) != null) {
            	
                String[] currentLine = line.split(",");
                Color color = new Color(Integer.parseInt(currentLine[0]), Integer.parseInt(currentLine[1]),Integer.parseInt(currentLine[2]));
                int aciIndex = Integer.parseInt(currentLine[3]);
                
                colorsList.add(new ACIColor(color, aciIndex));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                	reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ACIColors = new ACIColorList(colorsList);
	}
	
	/**
	 * Calculates AutoCAD index color from RGB.
	 * To be depreciated in further version release.
	 * @param color
	 * @return AutoCAD index color from RGB
	 *
	 */
	private int findClosestACIIndex(Color color) {
		
		int cRed = Toolset.closestNumber(ACIColors.getRedChannel(), color.getRed());
		int cGreen = Toolset.closestNumber(ACIColors.getGreenChannel(), color.getGreen());
		int cBlue = Toolset.closestNumber(ACIColors.getBlueChannel(), color.getBlue());
		
		int colorSum = 	cRed + cGreen + cBlue;
		
		/*System.out.println("Red "+  cRed);
		System.out.println("Green "+  cGreen);
		System.out.println("Blue "+  cBlue);*/
		
		List<ACIColor> redSums = new ArrayList<ACIColor>();
		List<ACIColor> greenSums = new ArrayList<ACIColor>();
		List<ACIColor> blueSums = new ArrayList<ACIColor>();
		
		for(ACIColor item : ACIColors.getColorList()) {
			//Red
			if(item.getRGB().getRed() == cRed) {
				redSums.add(item);
			}
			//Green
			if(item.getRGB().getGreen() == cGreen) {
				greenSums.add(item);
			}
			//Blue
			if(item.getRGB().getBlue() == cBlue) {
				blueSums.add(item);
			}
		}
		
		cRed = new ACIColorList(redSums).getClosestToColorSum(colorSum);
		cGreen = new ACIColorList(greenSums).getClosestToColorSum(colorSum);
		cBlue = new ACIColorList(blueSums).getClosestToColorSum(colorSum);
		
		int[] cArrays = {cRed, cGreen, cBlue};
		int finalCloseValue = Toolset.closestNumber(cArrays, colorSum);
		
		for(ACIColor item : ACIColors.getColorList()) {
			if(finalCloseValue == item.getSum()) {
				//item.print();
				return item.getACI();
			}
		}
		return 0;
	}

	/**
	 * Computes the statistics of the data, mean, average etc
	 * Method not yet completed for the v1.0 basic. Therefore data statistics not yet available.
	 */
	private void getDataStatistics() {
		
		List<Double> xValues = new ArrayList<Double>();
		List<Double> yValues = new ArrayList<Double>();
		
		if(reader!=null) {
		
		while (true) {
			
			try {
				String line = reader.readLine();
				
				if(line!=null) {
					
					String[] currentLine = line.split(delimeterButtonGroup.getSelection().getActionCommand());
					
					if(currentLine.length > 2) {
						
						xValues.add(Double.parseDouble(currentLine[0]));
						yValues.add(Double.parseDouble(currentLine[1]));
						/*for(int i = 0; i < 3; i++) {
							
							if(Double.parseDouble(currentLine[0]) < minX) {
								minX = Double.parseDouble(currentLine[0]);
							}
							
							if(Double.parseDouble(currentLine[1]) < minY) {
								minY = Double.parseDouble(currentLine[1]);
							}
							
							if(maxX < Double.parseDouble(currentLine[0])) {
								maxX = Double.parseDouble(currentLine[0]);
							}
							
							if(maxY < Double.parseDouble(currentLine[1])) {
								maxY = Double.parseDouble(currentLine[1]);
							}
							
						}*/
					}
				}
				
				else {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
            
		}
		
		//System.out.println(xValues.get(Toolset.findMinIndex(xValues)));
		//System.out.println(yValues.get(Toolset.findMinIndex(yValues)));
		
		/*this.minX.addToList(String.valueOf(minX));
		this.minY.addToList(String.valueOf(minY));
		
		this.maxX.addToList(String.valueOf(maxX));
		this.maxY.addToList(String.valueOf(maxY));*/
		
		} else {
			System.out.println("No data uploaded");
		}
		
	}
	
	/**
	 * Generates .dxf file
	 * @param inputFIle uploaded laserscan file.
	 */
	private void generateDXF(File inputFIle) {
		
		long lStartTime = System.nanoTime();

		//scrFile = new File(textField.getText()+".scr");
		dxfFile = new File(textOutputDir.getText());
		
		//BufferedWriter writer = null;
		BufferedReader  inputFileBufferedReader = null;
		FileReader inputFileReader = null;
		
		try {
            inputFileReader = new FileReader(inputFIle);
        } catch (FileNotFoundException e) {
            System.err.println("Cannot read input file.");
            JOptionPane.showMessageDialog(null, "Something went wrong!"
        			+ "\nCannot read input file", "Fatal error", JOptionPane.ERROR_MESSAGE);
        }
		
		inputFileBufferedReader = new BufferedReader(inputFileReader);
             
        String line;
        boolean success = true;
        
        int count = 0;
    	int processedPoints = 0;
    	
        try {
        	dxf = new BufferedWriter(new FileWriter(dxfFile));
        	writeDXFHeader();
        	dxf.write("\n0\nSECTION\n2\nENTITIES");
        	
        	// Ignoring writing .scr file
        	// This was the first atemot before deciding to wrte dxf file directly
        	// Disabling it to reduce the processing time for writing the dxf file.
        	
        	/*writer = new BufferedWriter(new FileWriter(scrFile));
        	writer.write("_MULTIPLE _POINT");
        	writer.newLine();*/
        	
        	int start = Integer.parseInt(startNumberChkbox.getTextField().getText());
        	int nextProcessedNumber = 0;
        	int intervalFactor = 0;
        	int dataLength = 3; // Basic X Y Z
        	int startCoord = 0;
        	
        	boolean endIsSelected = endNumberChkbox.isSelected();
        	boolean intervalIsSelected = useIntervalChkBox.isSelected();
        	
        	if(startNumberChkbox.isSelected()){
        		start = Integer.parseInt(startNumberChkbox.getTextField().getText());
        		nextProcessedNumber = start;
        	}
        	if(useIntervalChkBox.isSelected()){
        		intervalFactor = Integer.parseInt(useIntervalChkBox.getTextField().getText());
        	}
        	if(fileFormatButtonGroup.getSelection().getActionCommand().equals("svy")) {
        		startCoord = 1;
        		exportDxfColorCheckBox.setSelected(false);
        	}
        	if(exportDxfColorCheckBox.isSelected()){
        		dataLength = 7;
        	}

        	String delimeter = " ";
        	if(delimeterButtonGroup.getSelection().getActionCommand().equals("Default")) {
        		// Delimeter is after char /, will find better solution later
        		delimeter = fileFormatButtonGroup.getSelection().getActionCommand().split("/")[1];
        	} else if(!delimeterButtonGroup.getSelection().getActionCommand().equals("Default")) {
        		delimeter = delimeterButtonGroup.getSelection().getActionCommand();
        	}

            while (true) {
	                line = inputFileBufferedReader.readLine();
	                
	                if( start <= count) {
	                	if(count == nextProcessedNumber) {
			                if(line!=null) {
			                	String[] currentLine = line.split(delimeter);
			                	if(currentLine.length > dataLength - 1) {
			                		/*for(int j = 0 ; j <= 2; j++) {
			                			if(j<2) {
			                				writer.write(currentLine[j] + ",");
			                			} else if(j==2) {
			                				writer.write(currentLine[j]);
			                			}
			                		}
			                		writer.newLine();*/
			                		Point3D point = new Point3D(
			                				Double.parseDouble(currentLine[startCoord]), 
			                				Double.parseDouble(currentLine[startCoord+1]), 
			                				Double.parseDouble(currentLine[startCoord+2])
			                				);
			                		
			                		if(exportDxfColorCheckBox.isSelected()) {
				                		point.setColor(new Color(
				                				Integer.parseInt(currentLine[startCoord+4]), 
				                				Integer.parseInt(currentLine[startCoord+5]), 
				                				Integer.parseInt(currentLine[startCoord+6])
				                				));
			                		}
			                		
			                		writeDXFPoint(point);
			                		//System.out.println(nextProcessedNumber);
			                		if(intervalIsSelected) {
			                			nextProcessedNumber = count + intervalFactor;
			                		} 
			                		
			                		processedPoints++;
			                	}
			                	
			                } else {
			                	break;
			                }
	                	}
	                }
	                
	                if(endIsSelected){
	                	
		                if( count == Integer.parseInt(endNumberChkbox.getTextField().getText())) {
		                	break;
		                }
	                }
	                
	                count++;
	                if(!intervalIsSelected) {
            			nextProcessedNumber = count ;
            		}
            }
            
            dxf.write("\n0");
            dxf.write("\nENDSEC");
  
        } catch (IOException e) {
        	success = false;
            System.err.println("Fata Error");
            JOptionPane.showMessageDialog(null, "An error has occured.\nError: "
        			+ e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
        
        finally{
        	try {
        		//System.out.println(processedPoints);
				//writer.close();
        		// End dxf writer
				dxf.write("\n0");
	            dxf.write("\nEOF");
				dxf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if(success) {
        	
        	long lEndTime = System.nanoTime();
            long timeElapsed = lEndTime - lStartTime;
             
        	JOptionPane.showMessageDialog(null, "Operation Successful!"
        			+ "\nTotal of  " + processedPoints + " points processed"
        					+ "\nElapsed time in milliseconds: " + timeElapsed / 1000000, "Finished", JOptionPane.INFORMATION_MESSAGE);
        	
        	if(openDxfCheckBox.isSelected()) {
	        	try {
					if(Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(this.dxfFile);
						handleWindowClosingEvent();
					}else {
						JOptionPane.showMessageDialog(null, "Error: Cannot open dxf file.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
        	}
        	
        } else {
        	JOptionPane.showMessageDialog(null, "File reading error", "Error", JOptionPane.ERROR_MESSAGE);
        } 
	}

	/**
	 * Writes the header of the d.xf file. To be called once in the writing process.
	 * To be improved in further release
	 */
	private void writeDXFHeader() {
		
		try {
			dxf.write("999");
			dxf.write("\nDXF created from <Laserscan-to-dxf converter> by <Olumide Igbiloba>\n999\n(c)2017 All rights reserved");
			dxf.write("\n0\nSECTION");
			dxf.write("\n2\nHEADER");
			dxf.write("\n9\n$ACADVER\n1");
			dxf.write("\nAC1006\n9\n$INSBASE\n10\n0.0\n20\n0.0\n30\n0.0");
			dxf.write("\n0\nENDSEC");
			
			/*999
			DXF created from <program name>
			0
			SECTION
			2
			HEADER
			9
			$ACADVER
			1
			AC1006
			9
			$INSBASE
			10
			0.0
			20
			0.0
			30
			0.0
			0
			ENDSEC
			0
			SECTION
			2
			ENTITIES
			*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * Writes an instance of point to a .dxf file
	 * To be improved in further release to take the RGB color value instead if the closest ACI color index
	 * @param point
	 */
	private void writeDXFPoint(Point3D point) {
		try {
			//dxf.newLine();
			dxf.write("\n  0");
			dxf.write("\nPOINT");
			dxf.write("\n8");
			dxf.write("\nPointCloud");
			
			//X
			dxf.write("\n10");
			dxf.write("\n"+String.valueOf(point.getX()));
			//Y
			dxf.write("\n20");
			dxf.write("\n"+String.valueOf(point.getY()));
			//Z
			dxf.write("\n30");
			dxf.write("\n"+String.valueOf(point.getZ()));
			
			//Color
			
			/*int r = point.getColor().getRed();
			int g = point.getColor().getGreen();
			int b = point.getColor().getBlue();*/
			/*int rgb = point.getColor().getRGB();
			int rgbINT = ((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
			
			int red = (rgb>>16)&0x0ff;
			int green=(rgb>>8) &0x0ff;
			int blue= (rgb)    &0x0ff;*/
			
		
			
			
			if(exportDxfColorCheckBox.isSelected()) {
				dxf.write("\n62");
				dxf.write("\n"+findClosestACIIndex(point.getColor()));
				//dxf.write("\n"+rgbINT);
			}
			
			
			/*var r = red & 0xFF;
			var g = green & 0xFF;
			var b = blue & 0xFF;
			var a = alpha & 0xFF;

			var rgb = (r << 24) + (g << 16) + (b << 8) + (a);*/
			  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Closes the application.
	 */
	protected void handleWindowClosingEvent() {
		// TODO Auto-generated method stub
		dispose();
		System.exit(0);
	}
}
