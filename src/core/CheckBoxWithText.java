/**
 * 
 */
package core;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 * @author OlumideEnoch
 *
 */
public class CheckBoxWithText extends JCheckBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7672447960993435754L;
	
	private JTextField textField = new JTextField();
	
	/**
	 * 
	 */
	public CheckBoxWithText(String title) {
		super();
		setText(title);
		super.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					textField.setEnabled(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					textField.setEnabled(false);
				}
			}
			
		});
	}

	/**
	 * @return the textField
	 */
	public JTextField getTextField() {
		return textField;
	}

	/**
	 * @param textField the textField to set
	 */
	public void setTextField(JTextField textField) {
		this.textField = textField;
		this.textField.setToolTipText(super.getToolTipText());
		if(super.isSelected()) {
			this.textField.setEnabled(true);
		} else if(!super.isSelected()) {
			this.textField.setEnabled(false);
		}
	}
}
