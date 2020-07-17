/**
 * 
 */
package data.assets.longterm;

import java.text.DecimalFormat;

/**
 * @author Stephen Welsh
 *
 */
public class Asset {
	
	private String name;
	
	private double value;
	
	public Asset(String name, double value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public String getValueString() {
		 DecimalFormat decimalFormat = new DecimalFormat("#.##");
         decimalFormat.setGroupingUsed(true);
         decimalFormat.setGroupingSize(3);
		return "$" + decimalFormat.format(value);
	}
	
}
