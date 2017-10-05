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
	private String userData = "";
	
	/**
	 * @return the userData
	 */
	public String getUserData() {
		return userData;
	}

	/**
	 * @param userData the userData to set
	 */
	public void setUserData(String userData) {
		this.userData = userData;
	}

	public RadioButton(String title, String actionCommand) {
		super.setText(title);
		super.setActionCommand(actionCommand);
	}
	
	
	

}
