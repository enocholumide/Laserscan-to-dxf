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

	public RadioButton(String title, String actionCommand) {
		super.setText(title);
		super.setActionCommand(actionCommand);
	}
	
}
