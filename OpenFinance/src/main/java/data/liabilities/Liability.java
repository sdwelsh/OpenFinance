/**
 * 
 */
package data.liabilities;

import java.text.DecimalFormat;
import java.time.LocalDate;

/**
 * @author Stephen Welsh
 *
 */
public class Liability {
	
	private String name;
	
	private double totalAmount;
	
	private int yearsToMaturity;

	private int creationYear;

	public Liability(String name, double totalAmount, int yearsToMaturity, int creationYear) {
		this.name = name;
		if(name.trim().equals("")) {
			throw new IllegalArgumentException("Name Cannot be Empty");
		}
		this.totalAmount = totalAmount;
		this.creationYear = creationYear;
		
		if(creationYear != LocalDate.now().getYear()) {
			this.yearsToMaturity = yearsToMaturity - (LocalDate.now().getYear() - creationYear);
		} else {
			this.yearsToMaturity = yearsToMaturity;
		}
	}
	
	public Liability(String name, double totalAmount, int yearsToMaturity) {
		this(name, totalAmount, yearsToMaturity, LocalDate.now().getYear());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getYearsToMaturity() {
		return yearsToMaturity;
	}

	public void setYearsToMaturity(int yearsToMaturity) {
		this.yearsToMaturity = yearsToMaturity;
	}
	
	public int getCreationYear() {
		return creationYear;
	}

	public void setCreationYear(int creationYear) {
		this.creationYear = creationYear;
	}

	public String getTotalAmountString() {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
		return "$" + decimalFormat.format(totalAmount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + creationYear;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + yearsToMaturity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Liability other = (Liability) obj;
		if (creationYear != other.creationYear)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(totalAmount) != Double.doubleToLongBits(other.totalAmount))
			return false;
		if (yearsToMaturity != other.yearsToMaturity)
			return false;
		return true;
	}
	
	
	
	
}
