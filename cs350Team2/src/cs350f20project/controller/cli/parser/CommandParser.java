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
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SELECT") && 
			this.commandTextArray.get(2).equalsIgnoreCase("DRAWBRIDGE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("POSITION")
		)
		{
			return null;
			//return new CommandBehavioralSelectBridge(id, UPorDOWN);
		}//end of rule 6
		
		// rule 7
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SELECT") &&
			this.commandTextArray.get(2).equalsIgnoreCase("ROUNDHOUSE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("POSITION")
		)
		{
			return null;
			//return new CommandBehavioralSelectRoundhouse(id, angle, clockWise);
		}//end of rule 7
		
		// rule 8
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SELECT") &&
			this.commandTextArray.get(2).equalsIgnoreCase("SWITCH") &&
			this.commandTextArray.get(4).equalsIgnoreCase("PATH")
		) 
		{
			return null;
			//return new CommandBehavioralSelectSwitch(id, primaryorSecondary);
		}//end of rule 8
		
		// rule 11
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(3).equalsIgnoreCase("DIRECTION")
		) 
		{
			return null;
			//return new CommandBehavioralSetDirection(id, forwardOrBackwards);
		}//end of rule 11
		
		// rule 12
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(2).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(3).equalsIgnoreCase("ENGINE")
		)
		{
			return null;
			//return new CommandBehavioralSetReference(id);
		}//end of rule 12
		
		// rule 15
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(3).equalsIgnoreCase("SPEED") 
		) 
		{
			return null;
			//return new CommandBehavioralSetSpeed(id, number);
		}// end of rule 15
		
		return null; //if nothing else, return null for now
		
    }//end method: parse_DO
    
    private A_Command parse_CREATE() 
    {
    	
    	// rule 22
		if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("POWER") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CATENARY") &&
			this.commandTextArray.get(4).equalsIgnoreCase("WITH") &&
			this.commandTextArray.get(5).equalsIgnoreCase("POLES")
		) 
		{
			return null;
			//return new CommandCreatePowerCatenary(idl, id_POLES);
		}//end of rule 22
		
		// rule 23
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("POWER") && 
			this.commandTextArray.get(2).equalsIgnoreCase("POLE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("ON") &&
			this.commandTextArray.get(5).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(7).equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray.get(9).equalsIgnoreCase("FROM")
		) 
		{
			return null;
			//return new CommandCreatePowerPole(pole_ID, track_locator);
		}//end of rule 23
		
		// rule 24
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("POWER") &&
			this.commandTextArray.get(2).equalsIgnoreCase("STATION") &&
			this.commandTextArray.get(4).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(8).equalsIgnoreCase("WITH")
		)
		{
			return null;
			//return new CommandCreatePowerStation();
		}//end of rule 24
    	
		// rule 25
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("POWER") &&
			this.commandTextArray.get(2).equalsIgnoreCase("SUBSTATION") &&
			this.commandTextArray.get(4).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(8).equalsIgnoreCase("WITH") &&
			this.commandTextArray.get(9).equalsIgnoreCase("CATENARIES")
		)
		{
			return null;
			//return new CommandCreatePowerSubstation();
		}// end of rule 25
		
		// rule 28
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CAR") &&
			this.commandTextArray.get(4).equalsIgnoreCase("AS") &&
			this.commandTextArray.get(5).equalsIgnoreCase("BOX")
		) 
		{
			return null;
			//return new CommandCreateStockCarBox(id);
		}//end of rule 28
		
		// rule 29
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CAR") &&
			this.commandTextArray.get(4).equalsIgnoreCase("AS") &&
			this.commandTextArray.get(5).equalsIgnoreCase("CABOOSE")
		) 
		{
			return null;
			//return new CommandCreateStockCarCaboose(id);
		}//end of rule 29
		
		// rule 30
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CAR") &&
			this.commandTextArray.get(4).equalsIgnoreCase("AS") &&
			this.commandTextArray.get(5).equalsIgnoreCase("FLATBED")
		) 
		{
			return null;
			//return new CommandCreateStockCarFlatbed(id);
		}//end of rule 30
		
		// rule 31
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CAR") &&
			this.commandTextArray.get(4).equalsIgnoreCase("AS") &&
			this.commandTextArray.get(5).equalsIgnoreCase("PASSENGER")
		) 
		{
			return null;
			//return new CommandCreateStockCarPassenger(id);
		}//end of rule 31
		
		// rule 32
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CAR") &&
			this.commandTextArray.get(4).equalsIgnoreCase("AS") &&
			this.commandTextArray.get(5).equalsIgnoreCase("TANK")
		) 
		{
			return null;
			//return new CommandCreateStockCarTank(id);
		}//end of rule 32
		
		// rule 33
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CAR") &&
			this.commandTextArray.get(4).equalsIgnoreCase("AS") &&
			this.commandTextArray.get(5).equalsIgnoreCase("TENDER")
		) 
		{
			return null;
			//return new CommandCreateStockCarTender(id);
		}//end of rule 33
		
		// rule 34
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("ENGINE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("AS") &&
			this.commandTextArray.get(5).equalsIgnoreCase("DIESEL") &&
			this.commandTextArray.get(6).equalsIgnoreCase("ON") &&
			this.commandTextArray.get(7).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(9).equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray.get(11).equalsIgnoreCase("FROM") &&
			this.commandTextArray.get(13).equalsIgnoreCase("FACING")
		) 
		{
			return null;
			//return new CommandCreateStockEngineDiesel();
		}//end of rule 34
    	
		return null; //if nothing else, return null for now
    
    }//end method: parse_CREATE
    
}//end class: CommandParser
