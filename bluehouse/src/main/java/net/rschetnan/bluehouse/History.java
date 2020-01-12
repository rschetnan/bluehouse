package net.rschetnan.bluehouse;

public class History {
	
	CountTxs[] countTxs;
	Prices[] prices;
	

	/**
	 * @return the prices
	 */
	public Prices[] getPrices() {
		return prices;
	}

	/**
	 * @param prices the prices to set
	 */
	public void setPrices(Prices[] prices) {
		this.prices = prices;
	}

	/**
	 * @return the countTxs
	 */
	public CountTxs[] getCountTxs() {
		return countTxs;
	}

	/**
	 * @param countTxs the countTxs to set
	 */
	public void setCountTxs(CountTxs[] countTxs) {
		this.countTxs = countTxs;
	}

}
