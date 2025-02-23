package com.fetch.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Helper {

    public String [] customSplit( String input){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            String value = "";
            if(input.charAt(i)=='\"' || input.charAt(i)=='“' || input.charAt(i)=='”' || input.charAt(i)=='\''){
                value += input.charAt(i);
                for (int j = i+1; j < input.length(); j++) {
                    if(input.charAt(j) != '\"' && input.charAt(j) != '“' && input.charAt(j) != '”' && input.charAt(j) != '\''){
                        value += input.charAt(j);
                    }else{
                        value += input.charAt(j);
                        i = j;
                        result.add(value);
                        break;
                    }
                }
            }
        }

        return result.toArray(new String[0]);
    }

    public boolean isZipOrPostCode(String input){
        boolean hasDigit = false;
        boolean hasLetter = false;
        for(int i = 0; i < input.length(); i++){
            if (Character.isDigit(input.charAt(i))){
                hasDigit = true;
            }
            if (Character.isAlphabetic(input.charAt(i))){
                hasLetter = true;
            }
        }
        return (hasDigit && !hasLetter);
    }

}
