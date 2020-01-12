package net.rschetnan.bluehouse;

public class Prices {
	
	private long ts;
	private String date;
	private double open;
	private double close;
	private double high;
	private double low;
	private double volume;
	private double volumeConverted;
	private double cap;
	private double average;
	private double macd;
	private double signal;
	private Action action = Action.SHORT;
	private double offsetSMA5;
	private double offsetSMA10;

	
	
	/**
	 * @return the offsetSMA5
	 */
	public double getOffsetSMA5() {
		return offsetSMA5;
	}

	/**
	 * @param offsetSMA5 the offsetSMA5 to set
	 */
	public void setOffsetSMA5(double offsetSMA5) {
		this.offsetSMA5 = offsetSMA5;
	}

	/**
	 * @return the offsetSMA10
	 */
	public double getOffsetSMA10() {
		return offsetSMA10;
	}

	/**
	 * @param offsetSMA10 the offsetSMA10 to set
	 */
	public void setOffsetSMA10(double offsetSMA10) {
		this.offsetSMA10 = offsetSMA10;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	public double getMACDSignalDifference()
	{
		return macd - signal;
	}
	
	/**
	 * @return the macd
	 */
	public double getMacd() {
		return macd;
	}
	/**
	 * @param macd the macd to set
	 */
	public void setMacd(double macd) {
		this.macd = macd;
	}
	/**
	 * @return the signal
	 */
	public double getSignal() {
		return signal;
	}
	/**
	 * @param signal the signal to set
	 */
	public void setSignal(double signal) {
		this.signal = signal;
	}
	/**
	 * @return the ts
	 */
	public long getTs() {
		return ts;
	}
	/**
	 * @param ts the ts to set
	 */
	public void setTs(long ts) {
		this.ts = ts;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the open
	 */
	public double getOpen() {
		return open;
	}
	/**
	 * @param open the open to set
	 */
	public void setOpen(double open) {
		this.open = open;
	}
	/**
	 * @return the close
	 */
	public double getClose() {
		return close;
	}
	/**
	 * @param close the close to set
	 */
	public void setClose(double close) {
		this.close = close;
	}
	/**
	 * @return the high
	 */
	public double getHigh() {
		return high;
	}
	/**
	 * @param high the high to set
	 */
	public void setHigh(double high) {
		this.high = high;
	}
	/**
	 * @return the low
	 */
	public double getLow() {
		return low;
	}
	/**
	 * @param low the low to set
	 */
	public void setLow(double low) {
		this.low = low;
	}
	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(double volume) {
		this.volume = volume;
	}
	/**
	 * @return the volumeConverted
	 */
	public double getVolumeConverted() {
		return volumeConverted;
	}
	/**
	 * @param volumeConverted the volumeConverted to set
	 */
	public void setVolumeConverted(double volumeConverted) {
		this.volumeConverted = volumeConverted;
	}
	/**
	 * @return the cap
	 */
	public double getCap() {
		return cap;
	}
	/**
	 * @param cap the cap to set
	 */
	public void setCap(double cap) {
		this.cap = cap;
	}
	/**
	 * @return the average
	 */
	public double getAverage() {
		return average;
	}
	/**
	 * @param average the average to set
	 */
	public void setAverage(double average) {
		this.average = average;
	}
	
	

}
