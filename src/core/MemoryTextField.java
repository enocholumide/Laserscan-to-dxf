/**
 * 
 */
package core;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 * @author OlumideEnoch
 *
 */
public class MemoryTextField extends JTextField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2689508280797979476L;
	private List<String> memoryList = new ArrayList<String>();
	private boolean isAlreadyCalculated = false;
	private JCheckBox toggler = new JCheckBox();
	private String originalText = "";
	
	public MemoryTextField(String title, JCheckBox toggler) {
		super.setText(title);
		this.toggler = toggler;
	}
	
	public MemoryTextField(String text) {
		super.setText(text);
		setOriginalText(text);
	}
	
	public MemoryTextField() {
	}
	
	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	/**
	 * @return the memoryList
	 */
	public List<String> getMemoryList() {
		return memoryList;
	}

	/**
	 * @param memoryList the memoryList to set
	 */
	public void setMemoryList(List<String> memoryList) {
		this.memoryList = memoryList;
	}

	/**
	 * @return the isAlreadyCalculated
	 */
	public boolean isAlreadyCalculated() {
		return isAlreadyCalculated;
	}

	/**
	 * @param isAlreadyCalculated the isAlreadyCalculated to set
	 */
	public void setAlreadyCalculated(boolean isAlreadyCalculated) {
		this.isAlreadyCalculated = isAlreadyCalculated;
	}

	/**
	 * @return the toggler
	 */
	public JCheckBox getToggler() {
		return toggler;
	}

	/**
	 * @param toggler the toggler to set
	 */
	public void setToggler(JCheckBox toggler) {
		this.toggler = toggler;
	}
	
	public void addToList(String item) {
		this.memoryList.add(item);
		super.setText(item);
	}
	
	public String getOriginalText() {
		return this.originalText;
	}
	
	public void setToOriginalText() {
		super.setText(originalText);
	}
	
	public boolean isOriginalTextField() {
		return super.getText().equals(this.originalText);
	}
	

}
