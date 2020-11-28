package cs350f20project.controller.cli.parser;

import java.util.Arrays;
import java.util.ArrayList;

public class CommandParser {

	// FIELDS
	
	private MyParserHelper parserHelper;
	private String commandText;
	private ArrayList<String> commandTextArray;

	// CONSTRUCTORS
	
    public CommandParser(MyParserHelper parserHelper, String commandText){
        this.parserHelper = parserHelper;
        this.commandText = commandText;
        this.commandTextArray = new ArrayList<String>(Arrays.asList(this.commandText.split(" ")));
        
        System.out.println(commandTextArray); //this makes sure that the command entered got converted to an arrayList

        System.out.println("TEAM 2 PARSER"); //this makes sure that we are referencing our CommandParser class
    }//end constructor
    
    // PARSE DATATYPE
    /*
	private Angle parseAngle(String command) {
        double value = Double.parseDouble(command);
        Angle angle = new Angle(value);
        return angle;
    }
    */
    
    // PARSE METHODS
    public void parse(){
    	
    	/*
        if (this.commandText.equalsIgnoreCase("@exit"))
        {
        	A_Command command = new CommandMetaDoExit();
        	this.parserHelper.getActionProcessor().schedule(command);
        }
        */

    }//end method: parse


}//end class: CommandParser
