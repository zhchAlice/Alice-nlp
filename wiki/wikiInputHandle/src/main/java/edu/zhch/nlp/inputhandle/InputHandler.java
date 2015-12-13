package edu.zhch.nlp.inputhandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.*;

public class InputHandler {
	
	@Resource(name="textConceptsService")
	private static TextConceptsService textConceptsService;

	public static void main(String[] args) {
		if(args.length != 2) {
			return;
		}
		Map<String,Integer> inputTextOne = new HashMap<String, Integer>();
		Map<String,Integer> inputTextTwo = new HashMap<String, Integer>();	
		
		int wordsNumofInputTextOne = readFileWords(args[0],inputTextOne);
		int wordsNumofInputTextTwo = readFileWords(args[0],inputTextTwo);
		
		Map<String, Double> inputTextOneConcepts = textConceptsService.
				generateWeightedConcepts(wordsNumofInputTextOne, inputTextOne);
		Map<String, Double> inputTextTwoConcepts = textConceptsService.
				generateWeightedConcepts(wordsNumofInputTextTwo, inputTextTwo);
		
		double consineSimilarity = textConceptsService.computeConsineSimilarity(
				inputTextOneConcepts, inputTextTwoConcepts);
	}
	
	private static int readFileWords(String fileName, 
			Map<String,Integer> inputText) {
		
		int inputWordNum = 0;
		
		File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
            	String[] tempArray = tempString.split(" ");
            	for(int i=0; i<tempArray.length; i++) {
            		if(!inputText.containsKey(tempArray[i])) {
            			inputText.put(tempArray[i], 1);
            		} else {
            			int value = inputText.get(tempArray[i]);
            			inputText.put(tempArray[0], value+1);
            		}
            	}
            	inputWordNum += tempArray.length;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }		
		return inputWordNum;
	}
}
