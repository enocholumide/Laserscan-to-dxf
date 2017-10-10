/**
 * 
 */
package core;

import javax.swing.JRadioButton;

/**
 * @author OlumideEnoch
 *
 */
public class RadioButton extends JRadioButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3602197499836281853L;
	private String delimeter = " ";

	public RadioButton(String title, String actionCommand) {
		super.setName(title);
		super.setText(title);
		super.setActionCommand(actionCommand);
	}
	/**
	 * @return the delimeter
	 */
	public String getDelimeter() {
		return delimeter;
	}
	
}
