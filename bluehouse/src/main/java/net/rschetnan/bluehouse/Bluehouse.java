package net.rschetnan.bluehouse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;


public class Bluehouse 
{
	
	public static void main(String[] args)
    {
		for(Tokens tokens : Tokens.values())
		{
			try {
				printResults(tokens);
				Thread.sleep(3000);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

    }
    
    public static void printActionCalculation(Prices[] prices) throws Exception
    {
    	Action action = Action.SHORT;
    	
    	List<String> actions = Stream.of(Action.values()).map(s -> s.name()).collect(Collectors.toList());
    	
    	Attribute actionAttribute = new Attribute("action", actions);
    	
    	ArrayList<Attribute> attributes = new ArrayList<>();
    	attributes.add(new Attribute("dayOfYear"));
    	attributes.add(new Attribute("timestamp"));
    	attributes.add(new Attribute("month"));
    	attributes.add(new Attribute("year"));
    	attributes.add(new Attribute("dayOfMonth"));
    	attributes.add(new Attribute("dayOfWeek"));
    	attributes.add(new Attribute("price"));
    	attributes.add(actionAttribute);
    	
    	Instances trainingInstances = new Instances("trainingInstances", attributes, 0);
    	trainingInstances.setClassIndex(trainingInstances.numAttributes() - 1);
    	for(int i = 10; i < prices.length - 3; i++)
    	{
   		 	LocalDate localDate = LocalDate.parse(prices[i].getDate());
    		double[] values = new double[trainingInstances.numAttributes()];
    		values[1] = prices[i].getTs();
    		values[3] = localDate.getYear();
    		values[2] = localDate.getMonthValue();
    		values[4] = localDate.getDayOfMonth();
    		values[0] = localDate.getDayOfYear();
    		values[5] = localDate.getDayOfWeek().getValue();
    		values[6] = prices[i].getOpen();
    		values[7] = prices[i].getAction().ordinal();
    		Instance instance = new DenseInstance(1.0, values);
    		trainingInstances.add(instance);
    	}
    	
//    	System.out.println();
//    	System.out.print(trainingInstances.toString());
//    	System.out.println();

    	Classifier classifier = AbstractClassifier.forName("weka.classifiers.rules.OneR", new String[]{"-B", "2"});
    	classifier.buildClassifier(trainingInstances);
    	
    	Evaluation evaluation = new Evaluation(trainingInstances);
    	
    	evaluation.evaluateModel(classifier, trainingInstances);

    	Instances classificationInstances = new Instances("classificationInstances", attributes, 0);
    	classificationInstances.setClassIndex(trainingInstances.numAttributes() - 1);
    	
    	int i = prices.length - 1;
    	
    	String date = (prices[i].getDate());

		LocalDate localDate = LocalDate.parse(date);
		double[] values = new double[trainingInstances.numAttributes()];
		values[1] = prices[i].getTs();
		values[3] = localDate.getYear();
		values[2] = localDate.getMonthValue();
		values[4] = localDate.getDayOfMonth();
		values[0] = localDate.getDayOfYear();
		values[5] = localDate.getDayOfWeek().getValue();
		values[6] = prices[i].getOpen();
		Instance instance = new DenseInstance(1.0, values);
		classificationInstances.add(instance);

		double actionIndex = classifier.classifyInstance(classificationInstances.get(0));
		
		action = Action.valueOf(actions.get((int) actionIndex));
		
		System.out.println("Date: " + date);
		
		System.out.println();
		
		System.out.println("Opening Price: " + prices[i].getOpen());
		
		System.out.println();
		
		System.out.println("Calculated Action: " + action.name());
    	
		System.out.println();
    	
    	System.out.println(evaluation.toSummaryString("Prediction Results", true));

    }
    
    public static void printLastCloseCalculation(Prices[] prices) throws Exception 
    {
    	double close = 0.0;
    	
    	ArrayList<Attribute> attributes = new ArrayList<>();
    	attributes.add(new Attribute("timestamp"));
    	attributes.add(new Attribute("year"));
    	attributes.add(new Attribute("month"));
    	attributes.add(new Attribute("dayOfMonth"));
    	attributes.add(new Attribute("dayOfYear"));
    	attributes.add(new Attribute("dayOfWeek"));
    	attributes.add(new Attribute("price"));
    	attributes.add(new Attribute("close"));
    	
    	Instances trainingInstances = new Instances("trainingInstances", attributes, 0);
    	trainingInstances.setClassIndex(trainingInstances.numAttributes() - 1);
    	for(Prices p : prices)
    	{
   		 	LocalDate localDate = LocalDate.parse(p.getDate());
    		double[] values = new double[trainingInstances.numAttributes()];
    		values[0] = p.getTs();
    		values[1] = localDate.getYear();
    		values[2] = localDate.getMonthValue();
    		values[3] = localDate.getDayOfMonth();
    		values[4] = localDate.getDayOfYear();
    		values[5] = localDate.getDayOfWeek().getValue();
    		values[6] = p.getOpen();
    		values[7] = p.getClose();
    		Instance instance = new DenseInstance(1.0, values);
    		trainingInstances.add(instance);

    	}
    	
//    	System.out.println();
//    	System.out.print(trainingInstances.toString());
//    	System.out.println();
    	Classifier classifier = AbstractClassifier.forName("weka.classifiers.trees.RandomForest", new String[]{"-I", "189", "-K", "2", "-depth", "20"});
    	//Classifier classifier = AbstractClassifier.forName("weka.classifiers.trees.RandomForest", new String[]{"-I", "10", "-K", "0", "-depth", "0"});
    	classifier.buildClassifier(trainingInstances);

    	Evaluation evaluation = new Evaluation(trainingInstances);
    	
    	evaluation.evaluateModel(classifier, trainingInstances);

    	Instances classificationInstances = new Instances("classificationInstances", attributes, 0);
    	classificationInstances.setClassIndex(trainingInstances.numAttributes() - 1);
    	
    	int i = prices.length - 1;
    	
    	String date = prices[i].getDate();

		LocalDate localDate = LocalDate.parse(date);
		double[] values = new double[trainingInstances.numAttributes()];
		values[0] = prices[i].getTs();
		values[1] = localDate.getYear();
		values[2] = localDate.getMonthValue();
		values[3] = localDate.getDayOfMonth();
		values[4] = localDate.getDayOfYear();
		values[5] = localDate.getDayOfWeek().getValue();
		values[6] = prices[i].getOpen();
		Instance instance = new DenseInstance(1.0, values);
		classificationInstances.add(instance);
		
		close = classifier.classifyInstance(classificationInstances.get(0));
		
		System.out.println("Date: " + date);


		System.out.println("Predicted close = " + close);
		
		System.out.println();
    	
    	System.out.println(evaluation.toSummaryString("Prediction Results", true));

    }
    
    public static void printLastHighCalculation(Prices[] prices) throws Exception
    {
    	double high = 0.0;
    	
    	ArrayList<Attribute> attributes = new ArrayList<>();
    	attributes.add(new Attribute("timestamp"));
    	attributes.add(new Attribute("year"));
    	attributes.add(new Attribute("month"));
    	attributes.add(new Attribute("dayOfMonth"));
    	attributes.add(new Attribute("dayOfYear"));
    	attributes.add(new Attribute("dayOfWeek"));
    	attributes.add(new Attribute("price"));
    	attributes.add(new Attribute("high"));
    	
    	Instances trainingInstances = new Instances("trainingInstances", attributes, 0);
    	trainingInstances.setClassIndex(trainingInstances.numAttributes() - 1);
    	for(Prices p : prices)
    	{
   		 	LocalDate localDate = LocalDate.parse(p.getDate());
    		double[] values = new double[trainingInstances.numAttributes()];
    		values[0] = p.getTs();
    		values[1] = localDate.getYear();
    		values[2] = localDate.getMonthValue();
    		values[3] = localDate.getDayOfMonth();
    		values[4] = localDate.getDayOfYear();
    		values[5] = localDate.getDayOfWeek().getValue();
    		values[6] = p.getOpen();
    		values[7] = p.getHigh();
    		Instance instance = new DenseInstance(1.0, values);
    		trainingInstances.add(instance);

    	}
    	
//    	System.out.println();
//    	System.out.print(trainingInstances.toString());
//    	System.out.println();
    	
//    	Classifier classifier = AbstractClassifier.forName("weka.classifiers.trees.RandomForest", new String[]{"-I", "10", "-K", "0", "-depth", "0"});
    	
    	Classifier classifier = AbstractClassifier.forName("weka.classifiers.trees.RandomForest", new String[]{"-I", "189", "-K", "2", "-depth", "20"});

    	classifier.buildClassifier(trainingInstances);

    	Evaluation evaluation = new Evaluation(trainingInstances);
    	
    	evaluation.evaluateModel(classifier, trainingInstances);

    	Instances classificationInstances = new Instances("classificationInstances", attributes, 0);
    	classificationInstances.setClassIndex(trainingInstances.numAttributes() - 1);
    	
    	int i = prices.length - 1;
    	
    	String date = prices[i].getDate();

		LocalDate localDate = LocalDate.parse(prices[i].getDate());
		double[] values = new double[trainingInstances.numAttributes()];
		values[0] = prices[i].getTs();
		values[1] = localDate.getYear();
		values[2] = localDate.getMonthValue();
		values[3] = localDate.getDayOfMonth();
		values[4] = localDate.getDayOfYear();
		values[5] = localDate.getDayOfWeek().getValue();
		values[6] = prices[i].getOpen();
		Instance instance = new DenseInstance(1.0, values);
		classificationInstances.add(instance);
		
		high = classifier.classifyInstance(classificationInstances.get(0));
		
		System.out.println("Date: " + date);

		
		System.out.println("Predicted high = " + high);
		
		System.out.println();
    	
    	System.out.println(evaluation.toSummaryString("Prediction Results", true));

    }
    
    public static void printLastLowCalculation(Prices[] prices) throws Exception
    {
    	double low = 0.0;
    	
    	ArrayList<Attribute> attributes = new ArrayList<>();
    	attributes.add(new Attribute("timestamp"));
    	attributes.add(new Attribute("year"));
    	attributes.add(new Attribute("month"));
    	attributes.add(new Attribute("dayOfMonth"));
    	attributes.add(new Attribute("dayOfYear"));
    	attributes.add(new Attribute("dayOfWeek"));
    	attributes.add(new Attribute("price"));
    	attributes.add(new Attribute("low"));
    	
    	Instances trainingInstances = new Instances("trainingInstances", attributes, 0);
    	trainingInstances.setClassIndex(trainingInstances.numAttributes() - 1);
    	for(Prices p : prices)
    	{
   		 	LocalDate localDate = LocalDate.parse(p.getDate());
    		double[] values = new double[trainingInstances.numAttributes()];
    		values[0] = p.getTs();
    		values[1] = localDate.getYear();
    		values[2] = localDate.getMonthValue();
    		values[3] = localDate.getDayOfMonth();
    		values[4] = localDate.getDayOfYear();
    		values[5] = localDate.getDayOfWeek().getValue();
    		values[6] = p.getOpen();
    		values[7] = p.getLow();
    		Instance instance = new DenseInstance(1.0, values);
    		trainingInstances.add(instance);

    	}
    	
//    	System.out.println();
//    	System.out.print(trainingInstances.toString());
//    	System.out.println();
    	
//    	Classifier classifier = AbstractClassifier.forName("weka.classifiers.trees.RandomForest", new String[]{"-I", "10", "-K", "0", "-depth", "0"});
    	Classifier classifier = AbstractClassifier.forName("weka.classifiers.trees.RandomForest", new String[]{"-I", "189", "-K", "2", "-depth", "20"});
    	classifier.buildClassifier(trainingInstances);

    	Evaluation evaluation = new Evaluation(trainingInstances);
    	
    	evaluation.evaluateModel(classifier, trainingInstances);

    	Instances classificationInstances = new Instances("classificationInstances", attributes, 0);
    	classificationInstances.setClassIndex(trainingInstances.numAttributes() - 1);
    	
    	int i = prices.length - 1;
    	
    	String date = prices[i].getDate();

		LocalDate localDate = LocalDate.parse(prices[i].getDate());
		double[] values = new double[trainingInstances.numAttributes()];
		values[0] = prices[i].getTs();
		values[1] = localDate.getYear();
		values[2] = localDate.getMonthValue();
		values[3] = localDate.getDayOfMonth();
		values[4] = localDate.getDayOfYear();
		values[5] = localDate.getDayOfWeek().getValue();
		values[6] = prices[i].getOpen();
		Instance instance = new DenseInstance(1.0, values);
		classificationInstances.add(instance);
		
		low = classifier.classifyInstance(classificationInstances.get(0));
		
		System.out.println("Date: " + date);
		
		System.out.println("Predicted low = " + low);
		
		System.out.println();
    	
    	System.out.println(evaluation.toSummaryString("Prediction Results", true));

    }
    
    public static void printResults(Tokens tokens) throws Exception
    {
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		
		System.out.println("Token: " + tokens.name() + " - " + tokens.getName());
		
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		
		System.out.println();
		
			
			String json = Bluehouse.getTokenPriceHistoryJson(tokens.getAddress());
			
			TokenPriceHistory tokenPriceHistory = Bluehouse.getTokenPriceHistory(json);
			
			History history = tokenPriceHistory.getHistory();
			
			Prices[] prices = history.getPrices();
			
			double[] closingPrices = Stream.of(prices).mapToDouble(p -> p.getClose()).toArray();
			
			double[] sma5 = getSimpleMovingAverages(closingPrices, 5);
			
			for(int i = 4; i < sma5.length; i++)
			{
				prices[i - 2].setOffsetSMA5(sma5[i]);
			}
			
			double[] sma10 = getSimpleMovingAverages(closingPrices, 10);
			
			for(int i = 9; i < sma10.length; i++)
			{
				prices[i - 2].setOffsetSMA10(sma10[i]);
			}
			
			for(int i = 10; i < prices.length - 3 ; i++)
			{
				Action action = Action.LONG;
				
				if(prices[i].getOffsetSMA5() < prices[i - 1].getOffsetSMA5())
				{
					action = Action.SHORT;
				}
				if(prices[i].getOffsetSMA10() < prices[i - 1].getOffsetSMA10())
				{
					action = Action.SHORT;
				}
				if(prices[i].getOffsetSMA5() < prices[i].getOffsetSMA10())
				{
					action = Action.SHORT;
				}
				
				prices[i].setAction(action);
				
			}
			
			printActionCalculation(prices);
			
			printLastCloseCalculation(prices);
			
			printLastHighCalculation(prices);
			
			printLastLowCalculation(prices);

    }
    
    public static String getTokenPriceHistoryJson(String tokenAddress) throws URISyntaxException, ParseException, IOException
    {
    	URIBuilder query = new URIBuilder("http://api.ethplorer.io/getTokenPriceHistoryGrouped/".concat(tokenAddress).concat("?apiKey=freekey&period=500"));

    	CloseableHttpClient client = HttpClients.createDefault();
        
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        
        String json = new String();

        try ( CloseableHttpResponse response = client.execute(request))
        {
        	
            HttpEntity entity = response.getEntity();
            
            json = EntityUtils.toString(entity);
            
            EntityUtils.consume(entity);
            
        } 

        return json;
    }
    
    public static TokenPriceHistory getTokenPriceHistory(String json) throws JsonMappingException, JsonProcessingException
    {
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		TokenPriceHistory tokenPriceHistory = mapper.readValue(json, TokenPriceHistory.class);
		
		return tokenPriceHistory;

    }
    
    public static double[] getExponentialMovingAverages(double[] prices, int smaLength)
    {
    	double[] exponentialMovingAverages = prices.clone();
    	
    	double weightingMultiplier = 2.0d / (smaLength + 1.0d);
    	
    	for(int i = smaLength; i < exponentialMovingAverages.length; i++)
    	{

			//EMA = (K x (C - P)) + P
			exponentialMovingAverages[i] = (weightingMultiplier * (prices[i] - exponentialMovingAverages[i - 1])) + exponentialMovingAverages[i - 1];
    			
    	}
    	return exponentialMovingAverages;
    }
    
    public static double[] getMACD(double[] prices)
    {
    	double[] macd = new double[prices.length];
    	
    	double[] ema12 = getExponentialMovingAverages(prices, 12);
    	
    	double[] ema26 = getExponentialMovingAverages(prices, 26);
    	
    	for(int i = 0; i < macd.length; i++)
    	{
    		macd[i] = ema12[i] - ema26[i];
    	}
    	
    	return macd;
    }
    
    public static double[] getSimpleMovingAverages(double[] prices, int smaLength)
    {
    	
    	double[] simpleMovingAverages = new double[prices.length];
    	
    	for(int i = smaLength; i < simpleMovingAverages.length; i++)
    	{
			double[] closingValues = new double[smaLength];

			int closingValuesIndex = 0;
			
			for(int j = i - (smaLength - 1); j <= i; j++)
			{
				closingValues[closingValuesIndex++] = prices[j];
			}
			
			simpleMovingAverages[i] = StatUtils.mean(closingValues);
				
    	}

		return simpleMovingAverages;

    }
    
    public static ArrayList<Token> getTokens(String url) throws SQLException
    {
    	
    	ArrayList<Token> tokenList = new ArrayList<>();
    	
		try(Connection connection = DriverManager.getConnection(url); Statement statement = connection.createStatement())
		{
			
			ResultSet resultSet = statement.executeQuery("SELECT address, name, symbol FROM token ORDER BY name");
			
			while(resultSet.next())
			{
				Token token = new Token();
				
				token.setAddress(resultSet.getString("address"));
				
				token.setName(resultSet.getString("name"));
				
				token.setSymbol(resultSet.getString("symbol"));
				
				tokenList.add(token);
				
			}
			
			resultSet.close();
			
		} 

    	return tokenList;
    }

}
