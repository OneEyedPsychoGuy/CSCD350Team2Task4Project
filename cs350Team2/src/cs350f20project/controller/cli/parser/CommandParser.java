package cs350f20project.controller.cli.parser;

import java.util.Arrays;
import java.util.ArrayList;

import cs350f20project.controller.command.*;
import cs350f20project.controller.command.behavioral.*;
import cs350f20project.controller.command.creational.*;
import cs350f20project.controller.command.meta.*;
import cs350f20project.controller.command.structural.*;

public class CommandParser {

	// FIELDS
	
	private MyParserHelper parserHelper;
	private String commandText;
	private ArrayList<String> commandTextArray;

	// CONSTRUCTORS
	
    public CommandParser(MyParserHelper parserHelper, String commandText)
    {
        this.parserHelper = parserHelper;
        this.commandText = commandText;
        this.commandTextArray = new ArrayList<String>(Arrays.asList(this.commandText.split(" ")));
        
        // System.out.println(commandTextArray); //this makes sure that the command entered got converted to an arrayList

        System.out.println("USING TEAM 2 COMMAND PARSER"); //this makes sure that we are using our CommandParser class
    }//end constructor
    
    // PARSE METHODS
    public void parse()
    {
    	A_Command command = null;
    	
    	// missing rule 1 and 67
    	
    	// rules 2, 6-8, 11-12, 15
		if(this.commandTextArray.get(0).equalsIgnoreCase("DO"))
			command = parse_DO();
		
		// rules 22-25, 28-34, 39-49
		else if(this.commandTextArray.get(0).equalsIgnoreCase("CREATE"))
			command = parse_CREATE();
    	
		// rule 51
		else if(this.commandTextArray.get(0).equalsIgnoreCase("@EXIT"))
    		command = new CommandMetaDoExit();
    	
		// rule 52
		else if(this.commandTextArray.get(0).equalsIgnoreCase("@RUN"))
			command = null;
			//command = new CommandMetaDoMetaRun();
		
		// rule 55
		else if(this.commandTextArray.get(0).equalsIgnoreCase("CLOSE"))
			command = null;
			//command = new CommandMetaViewDestroy();
		
		// rule 56
		else if(this.commandTextArray.get(0).equalsIgnoreCase("OPEN"))
			command = null;
			//command = new CommandMetaViewGenerate();
		
		// rule 60
		else if(this.commandTextArray.get(0).equalsIgnoreCase("COMMIT"))
			command = null;
			//command = new CommandStructuralCommit();
		
		// rule 61
		else if(this.commandTextArray.get(0).equalsIgnoreCase("COUPLE"))
			command = null;
			//command = new CommandStructuralCouple();
		
		// rule 62
		else if(this.commandTextArray.get(0).equalsIgnoreCase("LOCATE"))
			command = null;
			//command = new CommandStructuralLocate();
		
		// rule 65
		else if(this.commandTextArray.get(0).equalsIgnoreCase("UNCOUPLE"))
			command = null;
			//command = new CommandStructuralUncouple();
		
		// rule 66
		else if(this.commandTextArray.get(0).equalsIgnoreCase("USE"))
			command = null;
			// unknown command call here. See MyParserHelper
    	
		//schedule command into the parserHelper
    	this.parserHelper.getActionProcessor().schedule(command);
    	
    }//end method: parse

    private A_Command parse_DO() 
    {
    	
    	// rule 2
		if(this.commandTextArray.get(1).equalsIgnoreCase("BRAKE"))
		{
			return null;
			//return new CommandBehavioralBrake(id);
		}//end of rule 2
		
		// rule 6
		else if(
			this.commandTextArray.get(1).equalsIgnoreCase("SELECT") && 
			this.commandTextArray.get(2).equalsIgnoreCase("DRAWBRIDGE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("POSITION")
		)
		{
			return null;
			//return new CommandBehavioralSelectBridge(id, UPorDOWN);
		}//end of rule 6
		
		// rule 7
		else if(
			this.commandTextArray.get(1).equalsIgnoreCase("SELECT") &&
			this.commandTextArray.get(2).equalsIgnoreCase("ROUNDHOUSE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("POSITION")
		)
		{
			return null;
			//return new CommandBehavioralSelectRoundhouse(id, angle, clockWise);
		}//end of rule 7
		
		// rule 8
		else if(
			this.commandTextArray.get(1).equalsIgnoreCase("SELECT") &&
			this.commandTextArray.get(2).equalsIgnoreCase("SWITCH") &&
			this.commandTextArray.get(4).equalsIgnoreCase("PATH")
		) 
		{
			return null;
			//return new CommandBehavioralSelectSwitch(id, primaryorSecondary);
		}//end of rule 8
		
		// rule 11
		else if(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(3).equalsIgnoreCase("DIRECTION")
		) 
		{
			return null;
			//return new CommandBehavioralSetDirection(id, forwardOrBackwards);
		}//end of rule 11
		
		// rule 12
		else if(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(2).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(3).equalsIgnoreCase("ENGINE")
		)
		{
			return null;
			//return new CommandBehavioralSetReference(id);
		}//end of rule 12
		
		// rule 15
		else if(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(3).equalsIgnoreCase("SPEED") 
		) 
		{
			return null;
			//return new CommandBehavioralSetSpeed(id, number);
		}// end of rule 15
		
		return null; //if nothing else, return null for now
		
    }//end method: parse_DO
    
    private A_Command parse_CREATE() {return null;}
    
}//end class: CommandParser
