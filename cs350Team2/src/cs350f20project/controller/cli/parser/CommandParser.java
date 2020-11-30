package cs350f20project.controller.cli.parser;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import cs350f20project.controller.ActionProcessor;
import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.*;
import cs350f20project.controller.command.behavioral.*;
import cs350f20project.controller.command.creational.*;
import cs350f20project.controller.command.meta.*;
import cs350f20project.controller.command.structural.*;
import cs350f20project.datatype.Angle;

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
			String file = this.commandTextArray.get(1);
			File tempFile = new File(file);
			if(tempFile.exists()) {
				command = new CommandMetaDoRun(this.commandTextArray.get(1));
			}
			else {
				System.out.println(file + " file does not exist.");
			}
			System.out.println("@RUN " + file);
		}//end of rule 52
		
		// rule 55
		else if
		(
			this.commandTextArray.get(0).equalsIgnoreCase("CLOSE") &&
			this.commandTextArray.get(1).equalsIgnoreCase("VIEW")
		)
    	{
			String id = this.commandTextArray.get(2);
			command = new CommandMetaViewDestroy(id);
			System.out.println("CLOSE VIEW " + id);
		}//end of rule 55
		
		// rule 56 NEEDSFIX
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
			////command = new CommandMetaViewGenerate(id, origin, worldWidth, screenSize)
			System.out.println("OPEN VIEW id1 ORIGIN ( coordinates_world | ( '$' id2 ) ) WORLD WIDTH integer1 SCREEN WIDTH integer2 HEIGHT integer3");
		}//end of rule 56
		
		// rule 60
		else if(this.commandTextArray.get(0).equalsIgnoreCase("COMMIT"))
		{
			command = new CommandStructuralCommit();
			System.out.println("COMMIT");
		}//end of rule 60
		
		// rule 61
		else if
		(
			this.commandTextArray.get(0).equalsIgnoreCase("COUPLE") &&
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(3).equalsIgnoreCase("AND")
		)
		{
			String stock_id1 = this.commandTextArray.get(2);
			String stock_id2 = this.commandTextArray.get(4);
			command = new CommandStructuralCouple(stock_id1, stock_id2);
			System.out.println("COUPLE STOCK id1:" +stock_id1+" AND id2:"+stock_id2);
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
			String stock_id1 = this.commandTextArray.get(2);
			String track_id2	= this.commandTextArray.get(5);
			double distance = Double.parseDouble(this.commandTextArray.get(7));
			TrackLocator track_locator;
			
			if(this.commandTextArray.get(10).equalsIgnoreCase("START")) {
				track_locator = new TrackLocator(track_id2, distance, true);
			}
			else {
				track_locator = new TrackLocator(track_id2, distance, false);
			}
			command = new CommandStructuralLocate(stock_id1, track_locator);
			System.out.println("LOCATE STOCK id1:"+stock_id1+" ON TRACK id2:"+track_id2+" DISTANCE "+distance+" FROM " + this.commandTextArray.get(10));
		}//end of rule 62
		
		// rule 65
		else if
		(
			this.commandTextArray.get(0).equalsIgnoreCase("UNCOUPLE") &&
			this.commandTextArray.get(1).equalsIgnoreCase("STOCK") &&
			this.commandTextArray.get(3).equalsIgnoreCase("AND")
		) 
		{
			String stock_id1 = this.commandTextArray.get(2);
			String stock_id2 = this.commandTextArray.get(4);
			command = new CommandStructuralUncouple(stock_id1, stock_id2);
			System.out.println("UNCOUPLE STOCK id1:"+stock_id1+" AND id2:"+stock_id2);
		}
		// rule 66 NEEDSFIX
		else if
		(
			this.commandTextArray.get(0).equalsIgnoreCase("USE") &&
			this.commandTextArray.get(2).equalsIgnoreCase("AS") &&
			this.commandTextArray.get(3).equalsIgnoreCase("REFERENCE")
			
		) 
		{
			String id = this.commandTextArray.get(1);
			////command = new parserHelper(ActionProcessor x);
			System.out.println("USE id:"+id+" AS REFERENCE coordinates_world");
		}
		//THIS SCHEDULES THE DESIRED COMMAND TO THE PARSERHELPER AFTER ITS BEEN PARSED
    	this.parserHelper.getActionProcessor().schedule(command);
    	
    }//end method: parse

    private A_Command parse_DO() 
    {
    	A_Command command = null;
    	// rule 2
		if(this.commandTextArray.get(1).equalsIgnoreCase("BRAKE"))
		{
			String id = this.commandTextArray.get(2);
			command = new CommandBehavioralBrake(id);
			System.out.println("DO BRAKE id:" + id);
		}//end of rule 2
		
		// rule 6
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SELECT") && 
			this.commandTextArray.get(2).equalsIgnoreCase("DRAWBRIDGE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("POSITION")
		)
		{
			String id = this.commandTextArray.get(3);
			boolean UPorDOWN;
			
			if(this.commandTextArray.get(5).equalsIgnoreCase("UP")) {
				UPorDOWN = true;
			}else {
				UPorDOWN = false;
			}
			command = new CommandBehavioralSelectBridge(id, UPorDOWN);
			System.out.println("DO SELECT DRAWBRIDGE id:"+id+" POSITION " + this.commandTextArray.get(5));
		}//end of rule 6
		
		// rule 7 NEEDSFIX command.execute()?
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SELECT") &&
			this.commandTextArray.get(2).equalsIgnoreCase("ROUNDHOUSE") &&
			this.commandTextArray.get(4).equalsIgnoreCase("POSITION")
		)
		{
			String id = this.commandTextArray.get(3);
			double angleNum = Double.parseDouble(this.commandTextArray.get(5));
			Angle angle = new Angle(angleNum);
			boolean clockWise = false;
			if(this.commandTextArray.get(6).equalsIgnoreCase("CLOCKWISE")) {
				clockWise = true;
			}else if(this.commandTextArray.get(6).equalsIgnoreCase("COUNTERCLOCKWISE")) {
				clockWise = false;
			}
			command = new CommandBehavioralSelectRoundhouse(id, angle, clockWise);
			System.out.println("DO SELECT ROUNDHOUSE id:"+id+" POSITION angle:"+angle+" "+this.commandTextArray.get(6));
		}//end of rule 7
		
		// rule 8
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SELECT") &&
			this.commandTextArray.get(2).equalsIgnoreCase("SWITCH") &&
			this.commandTextArray.get(4).equalsIgnoreCase("PATH")
		) 
		{
			String id = this.commandTextArray.get(3);
			boolean primaryorSecondary;
			if(this.commandTextArray.get(5).equalsIgnoreCase("PRIMARY")) {
				primaryorSecondary = true;
			}else {
				primaryorSecondary = false;
			}
			command = new CommandBehavioralSelectSwitch(id, primaryorSecondary);
			System.out.println("DO SELECT SWITCH id:"+id+" PATH "+ this.commandTextArray.get(5));
			
		}//end of rule 8
		
		// rule 11
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(3).equalsIgnoreCase("DIRECTION")
		) 
		{
			String id = this.commandTextArray.get(2);
			boolean forwardOrBackwards;
			if(this.commandTextArray.get(4).equalsIgnoreCase("FORWARD")) {
				forwardOrBackwards = true;
			} else {
				forwardOrBackwards = false;
			}
			command = new CommandBehavioralSetDirection(id, forwardOrBackwards);
			System.out.println("DO SET id:"+id+" DIRECTION "+this.commandTextArray.get(4));
		}//end of rule 11
		
		// rule 12
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(2).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(3).equalsIgnoreCase("ENGINE")
		)
		{
			String id = this.commandTextArray.get(4);
			command = new CommandBehavioralSetReference(id);
			System.out.println("DO SET REFERENCE ENGINE id:"+id);
		}//end of rule 12
		
		// rule 15
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("SET") &&
			this.commandTextArray.get(3).equalsIgnoreCase("SPEED") 
		) 
		{
			String id = this.commandTextArray.get(2);
			double number = Double.parseDouble(this.commandTextArray.get(4));
			command = new CommandBehavioralSetSpeed(id, number);
			System.out.println("DO SET id:"+id+" SPEED "+number);
		}// end of rule 15
		
		return command; //if nothing else, return null for now
		
    }//end method: parse_DO
    
    private A_Command parse_CREATE() 
    {
    	A_Command command = null;
    	// rule 22
		if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("POWER") &&
			this.commandTextArray.get(2).equalsIgnoreCase("CATENARY") &&
			this.commandTextArray.get(4).equalsIgnoreCase("WITH") &&
			this.commandTextArray.get(5).equalsIgnoreCase("POLES")
		) 
		{
			String id1 = this.commandTextArray.get(3);
			ArrayList<String> id_POLES = new ArrayList<String>();
			for(int i = 6; i < this.commandTextArray.size(); i++) {
				id_POLES.add(this.commandTextArray.get(i));
			}
			command = new CommandCreatePowerCatenary(id1, id_POLES);
			System.out.println("CREATE POWER CATENARY id1:"+id1+" WITH POLES "+id_POLES.toString());
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
			String pole_ID = this.commandTextArray.get(3);
			String track_ID = this.commandTextArray.get(6);
			double distance =  Double.parseDouble(commandTextArray.get(8));
			TrackLocator track_locator;
			if(this.commandTextArray.get(10).equalsIgnoreCase("START")) {
				track_locator = new TrackLocator(track_ID, distance, true);
			}
			else {
				track_locator = new TrackLocator(track_ID, distance, false);
			}
			command = new CommandCreatePowerPole(pole_ID, track_locator);
			System.out.println("CREATE POWER POLE id:"+pole_ID+" ON TRACK id:"+track_ID+" DISTANCE "+distance+" FROM " +this.commandTextArray.get(10));
		}//end of rule 23
		
		// rule 24 NEEDSFIX
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("POWER") &&
			this.commandTextArray.get(2).equalsIgnoreCase("STATION") &&
			this.commandTextArray.get(4).equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray.get(6).equalsIgnoreCase("DELTA") &&
			this.commandTextArray.get(8).equalsIgnoreCase("WITH")
		)
		{
			String station_ID = this.commandTextArray.get(3);
			System.out.println("CREATE POWER STATION id1:"+station_ID+" REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA coordinates_delta WITH ( SUBSTATION | SUBSTATIONS ) idn+");
		}//end of rule 24
    	
		// rule 25 NEEDSFIX
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
			String substation_ID = this.commandTextArray.get(3);
			////command = new CommandCreatePowerSubstation(substation_ID, reference, delta, idCatenaries);
			System.out.println("CREATE POWER SUBSTATION id:"+substation_ID+" REFERENCE ( coordinates_world | ( '$' id2 ) ) DELTA coordinates_delta WITH CATENARIES idn+");
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
			String stockboxCar_ID = this.commandTextArray.get(3);
			command = new CommandCreateStockCarBox(stockboxCar_ID);
			System.out.println("CREATE STOCK CAR id:"+stockboxCar_ID+" AS BOX");
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
			String stockCabooseCar_ID = this.commandTextArray.get(3);
			command = new CommandCreateStockCarCaboose(stockCabooseCar_ID);
			System.out.println("CREATE STOCK CAR id:"+stockCabooseCar_ID+" AS CABOOSE");
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
			String flatbedCar_ID = this.commandTextArray.get(3);
			command = new CommandCreateStockCarFlatbed(flatbedCar_ID);
			System.out.println("CREATE STOCK CAR id:"+flatbedCar_ID+" AS FLATBED");
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
			String passengerCar_ID = this.commandTextArray.get(3);
			command = new CommandCreateStockCarPassenger(passengerCar_ID);
			System.out.println("CREATE STOCK CAR id:"+passengerCar_ID+" AS PASSENGER");
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
			String tankCar_ID = this.commandTextArray.get(3);
			command = new CommandCreateStockCarTank(tankCar_ID);
			System.out.println("CREATE STOCK CAR id:"+tankCar_ID+" AS TANK");
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
			String stockCar_tender_id = this.commandTextArray.get(3);
			command = new CommandCreateStockCarTender(stockCar_tender_id);
			System.out.println("CREATE STOCK CAR id:"+stockCar_tender_id+" AS TENDER");
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
			String engine_ID = this.commandTextArray.get(3);
			String track_ID = this.commandTextArray.get(8);
			double distance = Double.parseDouble(this.commandTextArray.get(10));
			boolean isFacingStart = false;
			TrackLocator track_locator;	
			if(this.commandTextArray.get(12).equalsIgnoreCase("START")) {
				track_locator = new TrackLocator(track_ID, distance, true);
			}
			else {
				track_locator = new TrackLocator(track_ID, distance, false);
			}
			if(this.commandTextArray.get(14).equalsIgnoreCase("START")) {
				isFacingStart=true;
			}
			command = new CommandCreateStockEngineDiesel(engine_ID, track_locator, isFacingStart);
			System.out.println("CREATE STOCK ENGINE id:"+engine_ID+" AS DIESEL ON TRACK id:"+track_ID+" DISTANCE "+distance+" "+this.commandTextArray.get(12)+" FACING "+ this.commandTextArray.get(14));
		}//end of rule 34
		
		// rule 39 NEEDSFIX
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
		}//end of rule 39
		
		// rule 40 NEEDSFIX
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
		}//end of rule 40
		
		// rule 41 NEEDSFIX
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
		
		// rule 42 NEEDSFIX
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
		}//end of rule 43
		
		// rule 44 NEEDSFIX
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
		}//end of rule 44
		
		// rule 45 NEEDSFIX
		else if
		(
			this.commandTextArray.get(1).equalsIgnoreCase("TRACK") &&
			this.commandTextArray.get(2).equalsIgnoreCase("LAYOUT") &&
			this.commandTextArray.get(4).equalsIgnoreCase("WITH") &&
			this.commandTextArray.get(5).equalsIgnoreCase("TRACKS")
		)
		{
			System.out.println("CREATE TRACK LAYOUT id1 WITH TRACKS idn+");
		}//end of rule 45
		
		// rule 46 NEEDSFIX
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
		}//end of rule 46
		
		// rule 47 NEEDSFIX
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
		}//end of rule 47
		
		// rule 48 NEEDSFIX
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
		}//end of rule 48
		
		// rule 49 NEEDSFIX
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
		}//end of rule 49
		
		return command; //if nothing else, return null for now
    
    }//end method: parse_CREATE
    
}//end class: CommandParser
