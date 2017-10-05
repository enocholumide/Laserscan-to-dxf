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
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.Point3D;
import core.RadioButton;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JSeparator;
import javax.swing.JRadioButton;
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

public class Start extends JFrame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textOutputDir;
	
	File ptsFile, scrFile, dxfFile;
	
	BufferedWriter dxf = null;
	private static int pointID = 283;
	private JTextField otherDelimeter;
	
	private ButtonGroup fileFormatButtonGroup = new ButtonGroup();
	private ButtonGroup delimeterButtonGroup = new ButtonGroup();
	
	private JFileChooser fileChooser, dirChooser;
	
	private JCheckBox exportDxfColorCheckBox, openDxfCheckBox;;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start window = new Start();
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
	public Start() {
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
		Title.setFont(new Font("Tahoma", Font.BOLD, 13));
		Title.setBounds(28, 29, 185, 33);
		frame.getContentPane().add(Title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 150, 621, 2);
		separator.setForeground(new Color(105, 105, 105));
		frame.getContentPane().add(separator);
		
		JLabel lblConvertLaserScan = new JLabel("Converts laser scan data to AutoCAD Dxf file format");
		lblConvertLaserScan.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConvertLaserScan.setBackground(Color.WHITE);
		lblConvertLaserScan.setBounds(54, 79, 296, 33);
		frame.getContentPane().add(lblConvertLaserScan);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 150, 621, 363);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 23, 158, 110);
		panel_2.add(panel);
		panel.setBorder(new TitledBorder(null, "Input File Format", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(null);
		
		RadioButton rdbtnPTS = new RadioButton("PTS", "pts");
		rdbtnPTS.setSelected(true);
		rdbtnPTS.setBounds(6, 28, 65, 23);
		fileFormatButtonGroup.add(rdbtnPTS);
		panel.add(rdbtnPTS);
		
		RadioButton rdbtnXYZ = new RadioButton("XYZ", "xyz");
		rdbtnXYZ.setBounds(6, 54, 65, 23);
		fileFormatButtonGroup.add(rdbtnXYZ);
		panel.add(rdbtnXYZ);
		
		RadioButton rdbtnLAZ = new RadioButton("LAZ", "laz");
		rdbtnLAZ.setBounds(6, 80, 65, 23);
		fileFormatButtonGroup.add(rdbtnLAZ);
		panel.add(rdbtnLAZ);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Select file and output directory", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(178, 23, 433, 110);
		panel_2.add(panel_1);
		panel_1.setLayout(null);
		
		textField = new JTextField("Upload pts file...");
		textField.setBounds(10, 23, 305, 33);
		panel_1.add(textField);
		textField.setEditable(false);
		textField.setColumns(10);
		
		JButton btnUpload = new JButton("Upload");
		btnUpload.setBounds(325, 23, 98, 33);
		panel_1.add(btnUpload);
		btnUpload.setBackground(Color.BLACK);
		btnUpload.setForeground(Color.WHITE);
		
		textOutputDir = new JTextField("Select output directory...");
		textOutputDir.setBounds(10, 67, 305, 33);
		panel_1.add(textOutputDir);
		textOutputDir.setColumns(10);
		
		JButton btnDirectory = new JButton("Save as..");
		btnDirectory.setBounds(325, 67, 98, 33);
		panel_1.add(btnDirectory);
		
		btnDirectory.setBackground(Color.BLACK);
		btnDirectory.setForeground(Color.WHITE);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(105, 105, 105));
		separator_1.setBounds(10, 305, 594, 2);
		panel_2.add(separator_1);
		
		JButton btnDownload = new JButton("Convert");
		btnDownload.setBounds(501, 318, 103, 33);
		panel_2.add(btnDownload);
		btnDownload.setEnabled(false);
		
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
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Delimeter and Separator", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(10, 144, 158, 150);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		RadioButton rdbtnComma = new RadioButton("Comma", ",");
		rdbtnComma.setBounds(6, 24, 71, 23);
		delimeterButtonGroup.add(rdbtnComma);
		panel_3.add(rdbtnComma);
		
		RadioButton rdbtnSpace = new RadioButton("Space", " ");
		rdbtnSpace.setSelected(true);
		rdbtnSpace.setBounds(79, 24, 69, 23);
		delimeterButtonGroup.add(rdbtnSpace);
		panel_3.add(rdbtnSpace);
		
		RadioButton rdbtnOther = new RadioButton("Other", " ");
		rdbtnOther.setBounds(6, 50, 60, 23);
		delimeterButtonGroup.add(rdbtnOther);
		panel_3.add(rdbtnOther);
		
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
		otherDelimeter.setBounds(79, 51, 40, 20);
		panel_3.add(otherDelimeter);
		otherDelimeter.setColumns(10);
		
		otherDelimeter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				//rdbtnOther.setUserData(otherDelimeter.getText());
				rdbtnOther.setActionCommand(otherDelimeter.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(null, "Separator", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setBounds(6, 87, 142, 52);
		panel_3.add(panel_7);
		panel_7.setLayout(null);
		
		JComboBox<Object> cmbSeparator = new JComboBox<Object>();
		cmbSeparator.setModel(new DefaultComboBoxModel<Object>(new String[] {"point<.>"}));
		cmbSeparator.setBounds(10, 21, 118, 20);
		panel_7.add(cmbSeparator);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dxf Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(178, 143, 158, 150);
		panel_2.add(panel_4);
		panel_4.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Optimize Export for", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(10, 87, 138, 52);
		panel_4.add(panel_5);
		panel_5.setLayout(null);
		
		JComboBox<?> cmbOptimizeVersion = new JComboBox<Object>();
		cmbOptimizeVersion.setModel(new DefaultComboBoxModel(new String[] {"2007"}));
		cmbOptimizeVersion.setBounds(10, 21, 118, 20);
		panel_5.add(cmbOptimizeVersion);
		
		exportDxfColorCheckBox = new JCheckBox("Export color");
		exportDxfColorCheckBox.setSelected(true);
		exportDxfColorCheckBox.setBounds(10, 26, 97, 23);
		panel_4.add(exportDxfColorCheckBox);
		
		openDxfCheckBox = new JCheckBox("Open dxf on finish");
		openDxfCheckBox.setBounds(10, 52, 138, 23);
		panel_4.add(openDxfCheckBox);
		
		JLabel lblcAll = new JLabel("(c) 2017 All rights reserved.");
		lblcAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblcAll.setBounds(10, 318, 194, 14);
		panel_2.add(lblcAll);
		
		JLabel lblNewLabel_2 = new JLabel("Free under the Apache open source liscense");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_2.setBounds(10, 337, 326, 14);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(Start.class.getResource("/images/dxf.png")));
		lblNewLabel.setBounds(492, 11, 120, 128);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("v.1.0.0");
		lblNewLabel_1.setBounds(54, 109, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		btnDownload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/*// TODO Auto-generated method stub
				try {
	        		OutputStream srcFile = new BufferedOutputStream(new FileOutputStream("src/srcFile.scr"));
	        		srcFile.close();
	        	} catch(IOException ex) {
	        		ex.printStackTrace();
	        	}
				
				Start starter = new Start();
				try {
					starter.download("src/srcFile.scr", "C:\\Users\\Olumide Enoch\\Music");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				
				//Files.copy(new Path(), new OutputStream());
				
								
				createSCRFromPTS(ptsFile);
				
			}
			
		});
		
		btnDirectory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int returnVal = dirChooser.showOpenDialog(Start.this);
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					textOutputDir.setText(dirChooser.getCurrentDirectory().getPath());
				}
			}
			
		});
		
		btnUpload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int returnVal = fileChooser.showOpenDialog(Start.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					
			        try {
						ptsFile = fileChooser.getSelectedFile();
						
						
						textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
						textOutputDir.setText(fileChooser.getCurrentDirectory().getAbsolutePath());
						
						scrFile = new File(textOutputDir.getText() + (String) File.separator + textField.getText() + ".scr");
						
						
						btnDownload.setEnabled(true);
						btnDownload.setForeground(SystemColor.textHighlightText);
						btnDownload.setBackground(SystemColor.infoText);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        
					
				}
			}
			
		});	
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PTS Files Only", fileFormatButtonGroup.getSelection().getActionCommand());
		fileChooser.setFileFilter(filter);
	}
	
	protected void createSCRFromPTS(File ptsFile) {
		
		scrFile = new File(textOutputDir.getText() + (String) File.separator + textField.getText() + ".scr");
		dxfFile = new File(textOutputDir.getText() + (String) File.separator + textField.getText() + "dxf" + ".dxf");
		
		FileReader isr = null;
		try {
            isr = new FileReader(ptsFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found in res; don't use any extension");
        }
        BufferedReader reader = new BufferedReader(isr);
        BufferedWriter writer = null;
        
              
        String line;
        boolean success = true;
        
        try {
        	dxf = new BufferedWriter(new FileWriter(dxfFile));
        	writeDXFHeader();
        	dxf.write("\n0\nSECTION\n2\nENTITIES");
        	
        	writer = new BufferedWriter(new FileWriter(scrFile));;
        	writer.write("_MULTIPLE _POINT");
        	writer.newLine();
            while (true) {
	            	
	                line = reader.readLine();
	                if(line!=null) {
	                	String[] currentLine = line.split(delimeterButtonGroup.getSelection().getActionCommand());
	                	if(currentLine.length > 6) {
	                		for(int j = 0 ; j <= 2; j++) {
	                			if(j<2) {
	                				writer.write(currentLine[j] + ",");
	                			} else if(j==2) {
	                				writer.write(currentLine[j]);
	                			}
	                		}
	                		writer.newLine();
	                		Point3D point = new Point3D(
	                				Double.parseDouble(currentLine[0]), 
	                				Double.parseDouble(currentLine[1]), 
	                				Double.parseDouble(currentLine[2])
	                		);
	                		point.setColor(new Color(
	                				Integer.parseInt(currentLine[3]), 
	                				Integer.parseInt(currentLine[4]), 
	                				Integer.parseInt(currentLine[5]), 
	                				Integer.parseInt(currentLine[6])));
	                		
	                		writeDXFPoint(point);
	                	}
	                } else {
	                	break;
	                }
            }
            
            dxf.write("\n0");
            dxf.write("\nENDSEC");
  
        } catch (IOException e) {
        	success = false;
            System.err.println("Error reading the file");
        }
        
        finally{
        	try {
				writer.close();
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
        	JOptionPane.showMessageDialog(null, "Operation Successful!", "Finished", JOptionPane.INFORMATION_MESSAGE);
        	
        	if(openDxfCheckBox.isSelected()) {
	        	try {
					Desktop.getDesktop().open(this.dxfFile);
					handleWindowClosingEvent();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	
        } else {
        	JOptionPane.showMessageDialog(null, "Something NOT Successful", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}


	private void writeDXFHeader() {
		
		try {
			dxf.write("999");
			dxf.write("\nDXF created from <PTS to Dxf Converter> by <Olumide Igbiloba>\n999\nAll rights reserved (c)2017");
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
	 * 
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
			if(exportDxfColorCheckBox.isSelected()) {
				dxf.write("\n62");
				dxf.write("\n"+point.getColor().getRed()+","+point.getColor().getGreen()+","+point.getColor().getBlue());
			}
			  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void handleWindowClosingEvent() {
		// TODO Auto-generated method stub
		dispose();
		System.exit(0);
	}
}
