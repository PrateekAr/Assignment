package mypackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

public class CsvReader {
	
	private List<SecurityDetails> securityDetailsList =  new ArrayList<SecurityDetails>();

	public static void main (String args[]) throws IOException{
		CsvReader csvReader = new CsvReader();
		csvReader.readFile();

		csvReader.avgPricePerSecurity();
		
		csvReader.largestConsecutiveRise();
	}


	/**
	 * This method is reading the the csv file present at the given path.
	 * And is returning the list of securities(which is a 2nd column in csv file ) from the file.
	 */
	private void readFile() throws FileNotFoundException,IOException{
		String csvFile = "C:/Users/prateek_arora03/Desktop/CSV/transactions.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		

		try {

		    br = new BufferedReader(new FileReader(csvFile));
		    while ((line = br.readLine()) != null) {

		    	String[] columns = line.split(cvsSplitBy);
		       
		       if(!columns[0].contains("timestamp")){
		    	   SecurityDetails securityDetails = new SecurityDetails();
		           securityDetails.setSecurityName(columns[1].toUpperCase());
		           securityDetails.setTimestamp(columns[0]);
		           securityDetails.setAction(columns[2]);
		           securityDetails.setPrice(columns[3]);
		           System.out.println(securityDetails.getSecurityName());
		           securityDetailsList.add(securityDetails); 
		       }
		      
		    }
		} 
		  finally {
		    if (br != null) {
		        try {
		            br.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}

	/**
	 * avgPricePerSecurity method is asking for input as security name from the user which user wants to calculate the average price for.
	 * This method is fetching the price values from the csv file for a specified security and calculates the average of it and returns the 2 decimal rounded value.
	 */
	
	public void avgPricePerSecurity(){
		
		double avgPrice = 0;
		String securityName;
		Map<String, List<String>> map = null;
		Scanner s1 = new Scanner(System.in);
		try{
			System.out.println("Enter Security name for which average trade price to be calculated :  ");
			securityName = s1.next();
		} finally {
			s1.close();
		}
		if(securityName != null && !"".equals(securityName)){
			securityName = securityName.trim().toUpperCase();
			map = new HashMap<>();
			
			
			
			for(SecurityDetails securityDetails : securityDetailsList){
				if(map.containsKey(securityDetails.getSecurityName())){
					map.get(securityDetails.getSecurityName()).add(securityDetails.getPrice());
				}else{
					List<String> priceList = new ArrayList<String>();
					priceList.add(securityDetails.getPrice());
					
					map.put(securityDetails.getSecurityName(), priceList);
				}
			}
			
			
			double sum = 0;
			double roundedAvgPrice;
			List<String> priceList = map.get(securityName);
			
			if (priceList!= null && !priceList.isEmpty()) {
			for (String price : priceList){
				Double d = Double.valueOf(price);
				
				sum = sum +d;
			}
			
			avgPrice= sum/priceList.size();
			
			roundedAvgPrice = Math.round(avgPrice*100)/100;
			System.out.println(roundedAvgPrice);
		}
		
		else {
			System.out.println("No data");
		}}
		else{
			throw new RuntimeException("No data");
		}
		
	}
	

	/**
	 * This method is fetching the price values of a specified security from the csv file.
	 * And is calculating the largest consecutive rise for that security.
	 * 
	 */
	
	private void largestConsecutiveRise(){
		Map<String, List<String>> map = new HashMap<>();
		
		for(SecurityDetails securityDetails : securityDetailsList){
			if(map.containsKey(securityDetails.getSecurityName())){
				map.get(securityDetails.getSecurityName()).add(securityDetails.getPrice());
			}else{
				List<String> priceList = new ArrayList<String>();
				priceList.add(securityDetails.getPrice());
				
				map.put(securityDetails.getSecurityName(), priceList);
			}
		}
		
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
		    String[] list =  entry.getValue().toArray(new String[0]);
		    System.out.println(entry.getKey() +" : "+entry.getValue());
		    double firstValue = 0;
		    double secondValue = 0;
		    double largestConPrice = 0;
		    double  roundedLargestConPrice = 0; 
		    List<Double> conPriceRiseList = new ArrayList<>();
		    ListIterator<String> iterator = entry.getValue().listIterator(); 

	        while (iterator.hasNext()) {
	        	double firstIterationIndexValue = Double.parseDouble(iterator.next());
	        	double nextIterationIndexValue = iterator.hasNext() ? Double.parseDouble(list[iterator.nextIndex()]) : 0;
	        	
	        	if(nextIterationIndexValue != 0){
	        		if(firstIterationIndexValue < nextIterationIndexValue){
		        		if(firstValue == 0){
		        			firstValue = firstIterationIndexValue;
		        		}
		        		secondValue = nextIterationIndexValue;
		        		conPriceRiseList.add(secondValue - firstValue);
		        	}else{
		        		conPriceRiseList.add(secondValue - firstValue);
		        		firstValue = 0;
		        		secondValue = 0;
		        	}
	        	}

	        }
	        
	        if(!conPriceRiseList.isEmpty()){
	        	Collections.sort(conPriceRiseList);
		        largestConPrice = conPriceRiseList.get(conPriceRiseList.size()-1);
		    
	        }
	        
	       roundedLargestConPrice =  Math.round(largestConPrice*100)/100;
	        
	        System.out.println("Largest consecutive value of security " +entry.getKey() + " is: " +roundedLargestConPrice);
	        
		}
	}
	
	
	}
