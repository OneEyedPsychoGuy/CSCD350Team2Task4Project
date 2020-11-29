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
		{
			System.out.println("@RUN string");
			//command = new CommandMetaDoMetaRun();
		}//end of rule 52
		
		// rule 55
		else if
		(
			this.commandTextArray.get(0).equalsIgnoreCase("CLOSE") &&
			this.commandTextArray.get(1).equalsIgnoreCase("VIEW")
		)
		{
			System.out.println("CLOSE VIEW id");
			//command = new CommandMetaViewDestroy();
		}//end of rule 55
		
		// rule 56
		else if
		(
			this.commandTextArray.get(0).equalsIgnoreCase("OPEN") &&
			this.commandTextArray.get(1).equalsIgnoreCase("VIEW") &&
			this.commandTextArray.get(3).equalsIgnoreCase("ORIGIN") &&
			this.commandTextArray.get(5).equalsIgnoreCase("WORLD") &&
			this.commandTextArray.get(6).equalsIgnoreCase("WIDTH") &&
			this.commandTextArray.get(8).equalsIgnoreCase("SCREEN") &&
			this.commandTextArray.get(9).equalsIgnoreCase("WIDTH") &&
			this.commandTextArray.get(11).equalsIgnoreCase("HEIGHT")
		)
		{
			System.out.println("OPEN VIEW id1 ORIGIN ( coordinates_world | ( '$' id2 ) ) WORLD WIDTH integer1 SCREEN WIDTH integer2 HEIGHT integer3");
			//command = new CommandMetaViewGenerate();
		}//end of rule 56
		
		// rule 60
		else if(this.commandTextArray.get(0).equalsIgnoreCase("COMMIT"))
		{
			System.out.println("COMMIT");
			//command = new CommandStructuralCommit();
		}//end of rule 60
		
		// rule 61
		else if
		(
			this.commandTextArray.get(0).equalsIgnoreCase("COUPLE") &&
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(3).equalsIgnoreCase("AND")
		)
		{
			System.out.println("COUPLE STOCK id1 AND id2");
			//command = new CommandStructuralCouple();
		}//end of rule 61
		
		// rule 62
		else if
		(
			this.commandTextArray.get(0).equalsIgnoreCase("LOCATE") &&
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(3).equalsIgnoreCase("ON") &&
			this.commandTextArray.get(4).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray.get(8).equalsIgnoreCase("FROM")
		)
		{
			System.out.println("LOCATE STOCK id1 ON TRACK id2 DISTANCE number FROM ( START | END )");
			//command = new CommandStructuralLocate();
		}//end of rule 62
		
		// rule 65
		else if
		(
			this.commandTextArray.get(0).equalsIgnoreCase("UNCOUPLE") &&
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(3).equalsIgnoreCase("AND")
		)
			System.out.println("UNCOUPLE STOCK id1 AND id2");
			//command = new CommandStructuralUncouple();
		
		// rule 66
		else if
		(
			this.commandTextArray.get(0).equalsIgnoreCase("USE") &&
			this.commandTextArray.get(2).equalsIgnoreCase("AS") &&
			this.commandTextArray.get(3).equalsIgnoreCase("REFERENCE")
		)
			System.out.println("USE id AS REFERENCE coordinates_world");
			// unknown command call here. See MyParserHelper
    	
		//THIS SCHEDULES THE DESIRED COMMAND TO THE PARSERHELPER AFTER ITS BEEN PARSED
    	this.parserHelper.getActionProcessor().schedule(command);
    	
    }//end method: parse

    private A_Command parse_DO() 
    {
    	
    	// rule 2
		if(this.commandTextArray.get(1).equalsIgnoreCase("BRAKE"))
		{
			System.out.println("DO BRAKE id");
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
			System.out.println("DO SELECT DRAWBRIDGE id POSITION ( UP | DOWN )");
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
			System.out.println("DO SELECT ROUNDHOUSE id POSITION angle ( CLOCKWISE | COUNTERCLOCKWISE )");
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
			System.out.println("DO SELECT SWITCH id PATH ( PRIMARY | SECONDARY )");
			//return new CommandBehavioralSelectSwitch(id, primaryorSecondary);
		}//end of rule 8
		
		// rule 11
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(3).equalsIgnoreCase("DIRECTION")
		) 
		{
			System.out.println("DO SET id DIRECTION ( FORWARD | BACKWARD ) ");
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
			System.out.println("DO SET REFERENCE ENGINE id");
			//return new CommandBehavioralSetReference(id);
		}//end of rule 12
		
		// rule 15
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(3).equalsIgnoreCase("SPEED") 
		) 
		{
			System.out.println("DO SET id SPEED number");
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
			System.out.println("CREATE POWER CATENARY id1 WITH POLES idn+");
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
			System.out.println("CREATE POWER POLE id1 ON TRACK id2 DISTANCE number FROM ( START | END )");
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
			System.out.println("CREATE POWER STATION id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA coordinates_delta WITH ( SUBSTATION | SUBSTATIONS ) idn+");
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
			System.out.println("CREATE POWER SUBSTATION id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA coordinates_delta WITH CATENARIES idn+");
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
			System.out.println("CREATE STOCK CAR id AS BOX");
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
			System.out.println("CREATE STOCK CAR id AS CABOOSE");
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
			System.out.println("CREATE STOCK CAR id AS FLATBED");
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
			System.out.println("CREATE STOCK CAR id AS PASSENGER");
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
			System.out.println("CREATE STOCK CAR id AS TANK");
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
			System.out.println("CREATE STOCK CAR id AS TENDER");
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
			System.out.println("CREATE STOCK ENGINE id1 AS DIESEL ON TRACK id2 DISTANCE number FROM ( START | END ) FACING ( START | END )");
			//return new CommandCreateStockEngineDiesel();
		}//end of rule 34
		
		// rule 39
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("BRIDGE") &&
			this.commandTextArray.get(3).equalsIgnoreCase("DRAW") &&
			this.commandTextArray.get(5).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(7).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(8).equalsIgnoreCase("START") &&
			this.commandTextArray.get(10).equalsIgnoreCase("END") &&
			this.commandTextArray.get(12).equalsIgnoreCase("ANGLE")
		)
		{
			System.out.println("CREATE TRACK BRIDGE DRAW id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2 ANGLE angle");
			//return new CommandCreateTrackBridgeDraw();
		}//end of rule 39
		
		// rule 40
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("BRIDGE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(7).equalsIgnoreCase("START") &&
			this.commandTextArray.get(9).equalsIgnoreCase("END")
		)
		{
			System.out.println("CREATE TRACK BRIDGE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2");
			//return new CommandCreateTrackBridgeFixed();
		}//end of rule 40
		
		// rule 41
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CROSSING") &&
			this.commandTextArray.get(4).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(7).equalsIgnoreCase("START") &&
			this.commandTextArray.get(9).equalsIgnoreCase("END")
		)
		{
			System.out.println("CREATE TRACK CROSSING id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2");
			//return new CommandCreateTrackCrossing();
		}//end of rule 41
		
		// rule 42
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CROSSOVER") &&
			this.commandTextArray.get(4).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(7).equalsIgnoreCase("START") &&
			this.commandTextArray.get(9).equalsIgnoreCase("END") &&
			this.commandTextArray.get(11).equalsIgnoreCase("START") &&
			this.commandTextArray.get(13).equalsIgnoreCase("END")
		)
		{
			System.out.println("CREATE TRACK CROSSOVER id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2 START coordinates_delta3 END coordinates_delta4");
			//return new CommandCreateTrackCrossover();
		}//end of rule 42
		
		// rule 43
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CURVE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(7).equalsIgnoreCase("START") &&
			this.commandTextArray.get(9).equalsIgnoreCase("END")
		)
		{
			System.out.println("CREATE TRACK CURVE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2 ( ( DISTANCE ORIGIN number ) | ( ORIGIN coordinates_delta3 ) )");
			//return new CommandCreateTrackCurve();
		}//end of rule 43
		
		// rule 44
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("END") &&
			this.commandTextArray.get(4).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(7).equalsIgnoreCase("START") &&
			this.commandTextArray.get(9).equalsIgnoreCase("END")
		)
		{
			System.out.println("CREATE TRACK END id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2");
			//return new CommandCreateTrackEnd();
		}//end of rule 44
		
		// rule 45
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("LAYOUT") &&
			this.commandTextArray.get(4).equalsIgnoreCase("WITH") &&
			this.commandTextArray.get(5).equalsIgnoreCase("TRACKS")
		)
		{
			System.out.println("CREATE TRACK LAYOUT id1 WITH TRACKS idn+");
			//return new CommandCreateTrackLayout();
		}//end of rule 45
		
		// rule 46
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("ROUNDHOUSE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(7).equalsIgnoreCase("ORIGIN") &&
			this.commandTextArray.get(9).equalsIgnoreCase("ANGLE") &&
			this.commandTextArray.get(10).equalsIgnoreCase("ENTRY") &&
			this.commandTextArray.get(12).equalsIgnoreCase("START") &&
			this.commandTextArray.get(14).equalsIgnoreCase("END") &&
			this.commandTextArray.get(16).equalsIgnoreCase("WITH") &&
			this.commandTextArray.get(18).equalsIgnoreCase("SPURS") &&
			this.commandTextArray.get(19).equalsIgnoreCase("LENGTH") &&
			this.commandTextArray.get(21).equalsIgnoreCase("TURNTABLE") &&
			this.commandTextArray.get(22).equalsIgnoreCase("LENGTH")
		)
		{
			System.out.println("CREATE TRACK ROUNDHOUSE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA ORIGIN coordinates_delta1 ANGLE ENTRY angle1 START angle2 END angle3 WITH integer SPURS LENGTH number1 TURNTABLE LENGTH number2");
			//return new CommandCreateTrackRoundhouse();
		}//end of rule 46
		
		// rule 47
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("STRAIGHT") &&
			this.commandTextArray.get(4).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(7).equalsIgnoreCase("START") &&
			this.commandTextArray.get(9).equalsIgnoreCase("END")
		)
		{
			System.out.println("CREATE TRACK STRAIGHT id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2");
			//return new CommandCreateTrackStraight();
		}//end of rule 47
		
		// rule 48
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("SWITCH") &&
			this.commandTextArray.get(3).equalsIgnoreCase("TURNOUT") &&
			this.commandTextArray.get(5).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(7).equalsIgnoreCase("STRAIGHT") &&
			this.commandTextArray.get(8).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(9).equalsIgnoreCase("START") &&
			this.commandTextArray.get(11).equalsIgnoreCase("END") &&
			this.commandTextArray.get(13).equalsIgnoreCase("CURVE") &&
			this.commandTextArray.get(14).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(15).equalsIgnoreCase("START") &&
			this.commandTextArray.get(17).equalsIgnoreCase("END") &&
			this.commandTextArray.get(19).equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray.get(20).equalsIgnoreCase("ORIGIN")
		)
		{
			System.out.println("CREATE TRACK SWITCH TURNOUT id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) STRAIGHT DELTA START coordinates_delta1 END coordinates_delta2 CURVE DELTA START coordinates_delta3 END coordinates_delta4 DISTANCE ORIGIN number");
			//return new CommandCreateTrackSwitchTurnout();
		}//end of rule 48
		
		// rule 49
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("SWITCH") &&
			this.commandTextArray.get(3).equalsIgnoreCase("WYE") &&
			this.commandTextArray.get(5).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(7).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(8).equalsIgnoreCase("START") &&
			this.commandTextArray.get(10).equalsIgnoreCase("END") &&
			this.commandTextArray.get(12).equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray.get(13).equalsIgnoreCase("ORIGIN") &&
			this.commandTextArray.get(15).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(16).equalsIgnoreCase("START") &&
			this.commandTextArray.get(18).equalsIgnoreCase("END") &&
			this.commandTextArray.get(20).equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray.get(21).equalsIgnoreCase("ORIGIN")
		)
		{
			System.out.println("CREATE TRACK SWITCH WYE id1 REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA START coordinates_delta1 END coordinates_delta2 DISTANCE ORIGIN number1 DELTA START coordinates_delta3 END coordinates_delta4 DISTANCE ORIGIN number2");
			//return new CommandCreateTrackSwitchWye();
		}//end of rule 49
		
		return null; //if nothing else, return null for now
    
    }//end method: parse_CREATE
    
}//end class: CommandParser
