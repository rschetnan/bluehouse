package net.rschetnan.bluehouse;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.http.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BluehouseTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BluehouseTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BluehouseTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        double balance = 1008.00;
        
        for( int i = 0; i < 730; i++)
        {
        	balance += (balance * 0.01);
        	
        	System.out.println("Day " +  i + " balance = " + balance);
        }
    }
    
    public void atestMain()
    {
    	Bluehouse.main(new String[]{"jdbc:sqlite:C:/TopHat/TopHat.db"});
    }
    
    public void atestGetTokenPriceHistoryJson()
    {
    	String json = null;
    	
		try {
			
			json = Bluehouse.getTokenPriceHistoryJson("0x1F573D6Fb3F13d689FF844B4cE37794d79a7FF1C");
			
		} catch (ParseException | URISyntaxException | IOException e) {
			
			fail();
			
		}
    	assertNotNull(json);
    	
    	System.out.print(json);
    }
    
    public void atestGetTokenPriceHistory()
    {
    	String json = null;
    	
		try {
			
			json = Bluehouse.getTokenPriceHistoryJson("0x1F573D6Fb3F13d689FF844B4cE37794d79a7FF1C");
			
		} catch (ParseException | URISyntaxException | IOException e) {
			
			fail();
			
		}
		
		try {
			
			TokenPriceHistory tokenPriceHistory = Bluehouse.getTokenPriceHistory(json);
			
			assertNotNull(tokenPriceHistory);

			History history = tokenPriceHistory.getHistory();
			
			assertNotNull(history);
			
			CountTxs[] countTokens = history.getCountTxs();
			
			assertNotNull(countTokens);
			
			assertTrue(countTokens.length >0);
			
			System.out.println("countTokens.length = " + countTokens.length);
			
			Prices[] prices = history.getPrices();
			
			assertNotNull(prices);
			
			assertTrue(prices.length > 0);
			
			System.out.println("prices.length = " + prices.length);
			
		} catch (JsonProcessingException e) {
			fail(e.getMessage());
		}
    	
    }
    
    public void atestGetSimpleMovingAverages()
    {
    	String json = null;
    	
		try {
			
			json = Bluehouse.getTokenPriceHistoryJson("0xf0Ee6b27b759C9893Ce4f094b49ad28fd15A23e4");
			
		} catch (ParseException | URISyntaxException | IOException e) {
			
			fail();
			
		}
		
		try {
			
			TokenPriceHistory tokenPriceHistory = Bluehouse.getTokenPriceHistory(json);
			
			assertNotNull(tokenPriceHistory);

			History history = tokenPriceHistory.getHistory();
			
			assertNotNull(history);
			
			Prices[] prices = history.getPrices();
			
			assertNotNull(prices);
			
			assertTrue(prices.length > 0);
			
			System.out.println("prices.length = " + prices.length);
			
			double[] pricesAtClose = Stream.of(prices).mapToDouble(p -> p.getClose()).toArray();
			
			double[] movingAverages = Bluehouse.getSimpleMovingAverages(pricesAtClose, 12);
			
			for(int i = 0; i < movingAverages.length; i++)
			{
				System.out.println("movingAverages[" + i + "] = " + movingAverages[i]);
			}
			
			double[] closingPrices = new double[12];
			
			int index = 0;
			
			for(int i = 3; i < 15; i++)
			{
				closingPrices[index++] = prices[i].getClose();
			}
			
			double sma = StatUtils.mean(closingPrices);
			
			System.out.println("sma = " + sma);
			
			System.out.println("movingAverages[14] = " + movingAverages[14]);
			
			assertTrue(movingAverages[14] == sma);
			
		} catch (JsonProcessingException e) {
			fail(e.getMessage());
		}
    	
    }
    
    public void atestGetExponentialMovingAverages()
    {
    	String json = null;
    	
		try {
			
			json = Bluehouse.getTokenPriceHistoryJson("0xf0Ee6b27b759C9893Ce4f094b49ad28fd15A23e4");
			
		} catch (ParseException | URISyntaxException | IOException e) {
			
			fail();
			
		}
		
		try {
			
			TokenPriceHistory tokenPriceHistory = Bluehouse.getTokenPriceHistory(json);
			
			assertNotNull(tokenPriceHistory);

			History history = tokenPriceHistory.getHistory();
			
			assertNotNull(history);
			
			Prices[] prices = history.getPrices();
			
			assertNotNull(prices);
			
			assertTrue(prices.length > 0);
			
			System.out.println("prices.length = " + prices.length);
			
			double[] pricesAtClose = Stream.of(prices).mapToDouble(p -> p.getClose()).toArray();
			
			double[] exponentialMovingAverages = Bluehouse.getExponentialMovingAverages(pricesAtClose, 12);
			
			final double weightingMultiplier = 0.15384615384615384615384615384615;
			
			for(int i = 0; i < exponentialMovingAverages.length; i++)
			{
				System.out.println("price at close = " + pricesAtClose[i]);
				
				if(i >= 12)
				{
	    			// EMA = (Price [today] x K) + (EMA [yesterday] x (1 – K))
					double expected = (pricesAtClose[i] * weightingMultiplier) + (exponentialMovingAverages[i - 1] * (1.0 - weightingMultiplier));
					
					System.out.println("expected = " + expected);
					System.out.println("currentEMA = " + exponentialMovingAverages[i]);
				}
				
			}
			
		} catch (JsonProcessingException e) {
			fail(e.getMessage());
		}
    	
    }
    
    public void atestgetMACD()
    {
    	String json = null;
    	
		try {
			
			json = Bluehouse.getTokenPriceHistoryJson("0xf0Ee6b27b759C9893Ce4f094b49ad28fd15A23e4");
			
		} catch (ParseException | URISyntaxException | IOException e) {
			
			fail();
			
		}
		
		try {
			
			TokenPriceHistory tokenPriceHistory = Bluehouse.getTokenPriceHistory(json);
			
			assertNotNull(tokenPriceHistory);

			History history = tokenPriceHistory.getHistory();
			
			assertNotNull(history);
			
			Prices[] prices = history.getPrices();
			
			assertNotNull(prices);
			
			assertTrue(prices.length > 0);
			
			double[] pricesAtClose = Stream.of(prices).mapToDouble(p -> p.getClose()).toArray();
			
			double[] macd = Bluehouse.getMACD(pricesAtClose);
			
			for(int i = 0; i < macd.length; i++)
			{
				System.out.println("macd[" + i + "] = " + macd[i]);
			}
			
		} catch (JsonProcessingException e) {
			fail(e.getMessage());
		}

    }
    
    public void atestGetSignal()
    {
    	
    	String json = null;
    	
		try {
			
			json = Bluehouse.getTokenPriceHistoryJson("0xf0Ee6b27b759C9893Ce4f094b49ad28fd15A23e4");
			
		} catch (ParseException | URISyntaxException | IOException e) {
			
			fail();
			
		}
		
		try {
			
			TokenPriceHistory tokenPriceHistory = Bluehouse.getTokenPriceHistory(json);
			
			assertNotNull(tokenPriceHistory);

			History history = tokenPriceHistory.getHistory();
			
			assertNotNull(history);
			
			Prices[] prices = history.getPrices();
			
			assertNotNull(prices);
			
			assertTrue(prices.length > 0);
			
			double[] pricesAtClose = Stream.of(prices).mapToDouble(p -> p.getClose()).toArray();
			
			double[] macd = Bluehouse.getMACD(pricesAtClose);
			
			double[] signal = Bluehouse.getExponentialMovingAverages(macd, 9);
			
			
			for(int i = 0; i < signal.length; i++)
			{
				System.out.println("macd[" + i + "] = " + macd[i]);
				System.out.println("signal[" + i + "] = " + signal[i]);
				System.out.println("difference = " + (macd[i] - signal[i]));
			}
			
		} catch (JsonProcessingException e) {
			fail(e.getMessage());
		}
    }
    
    public void atestPrintLastCloseCalculation()
    {
    	String json = null;
    	
		try {
			
			json = Bluehouse.getTokenPriceHistoryJson("0x1F573D6Fb3F13d689FF844B4cE37794d79a7FF1C");
			
		} catch (ParseException | URISyntaxException | IOException e) {
			
			fail();
			
		}
		
		try {
			
			TokenPriceHistory tokenPriceHistory = Bluehouse.getTokenPriceHistory(json);
			
			assertNotNull(tokenPriceHistory);

			History history = tokenPriceHistory.getHistory();
			
			assertNotNull(history);
			
			Prices[] prices = history.getPrices();
			
			Bluehouse.printLastCloseCalculation(prices);
			
			
			
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}

    }
    
    public void atestPrintActionCalculation()
    {
    	String json = null;
    	
		try {
			
			json = Bluehouse.getTokenPriceHistoryJson("0x514910771AF9Ca656af840dff83E8264EcF986CA");
			
		} catch (ParseException | URISyntaxException | IOException e) {
			
			fail();
			
		}
		
		try {
			
			TokenPriceHistory tokenPriceHistory = Bluehouse.getTokenPriceHistory(json);
			
			assertNotNull(tokenPriceHistory);

			History history = tokenPriceHistory.getHistory();
			
			assertNotNull(history);
			
			Prices[] prices = history.getPrices();
			
			Bluehouse.printActionCalculation(prices);
			
			
			
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}

    }

    
}
