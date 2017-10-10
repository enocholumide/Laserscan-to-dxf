/**
 * 
 */
package core;

import java.awt.Color;

/**
 * @author OlumideEnoch
 *
 */
public class ACIColor {

	private Color RGB = new Color(0,0,0);
	private int ACI = 255;
	private int sum = 0;
	/**
	 * @param rGB
	 * @param aCI
	 */
	public ACIColor(Color rGB, int aCI) {
		super();
		RGB = rGB;
		ACI = aCI;
		setSum();
	}
	/**
	 * @return the rGB
	 */
	public Color getRGB() {
		return RGB;
	}
	/**
	 * @param rGB the rGB to set
	 */
	public void setRGB(Color rGB) {
		RGB = rGB;
	}
	/**
	 * @return the aCI
	 */
	public int getACI() {
		return ACI;
	}
	/**
	 * @param aCI the aCI to set
	 */
	public void setACI(int aCI) {
		ACI = aCI;
	}
	
	private void setSum() {
		this.sum = this.RGB.getRed() + this.RGB.getGreen() + this.RGB.getBlue();
	}
	
	public int getSum() {
		return this.sum;
	}
	
	public void print() {
		System.out.println(this.RGB.toString() + "ACI: " + getACI());
	}

}
