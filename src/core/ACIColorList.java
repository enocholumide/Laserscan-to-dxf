package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import toolkit.Toolset;

public class ACIColorList {
	
	private List<ACIColor> colorList = new ArrayList<ACIColor>();
	private int[] redChannel, blueChannel, greenChannel;

	/**
	 * @param colorList
	 */
	public ACIColorList(List<ACIColor> colorList) {
		super();
		this.colorList = colorList;
		setChannels(this.colorList);
	}

	/**
	 * @return the colorList
	 */
	public List<ACIColor> getColorList() {
		return colorList;
	}

	/**
	 * @param colorList the colorList to set
	 */
	public void setColorList(List<ACIColor> colorList) {
		this.colorList = colorList;
	}
	
	public void sortByRedChannel() {
		//Collections.sort(colorList);
	}

	/**
	 * @param redChannels the redChannels to set
	 */
	private void setChannels(List<ACIColor> colorList) {
		int n = colorList.size();
		redChannel = new int[n];
		greenChannel = new int[n];
		blueChannel = new int[n];
		int i = 0;
		for(ACIColor item : colorList) {
			redChannel[i] = item.getRGB().getRed();
			greenChannel[i] = item.getRGB().getGreen();
			blueChannel[i] = item.getRGB().getBlue();
			i++;
		}
		Arrays.sort(redChannel);
		Arrays.sort(greenChannel);
		Arrays.sort(blueChannel);
	}

	/**
	 * @return the redChannel
	 */
	public int[] getRedChannel() {
		return redChannel;
	}

	/**
	 * @return the blueChannel
	 */
	public int[] getBlueChannel() {
		return blueChannel;
	}

	/**
	 * @return the greenChannel
	 */
	public int[] getGreenChannel() {
		return greenChannel;
	}
	
	public int getClosestToColorSum(int value) {
		
		int sumList[] = new int[colorList.size()];
		
		int i = 0;
		for(ACIColor item : colorList) {
			sumList[i] = item.getSum();
			i++;
		}
		
		return Toolset.closestNumber(sumList, value);
		
		
	}
}
