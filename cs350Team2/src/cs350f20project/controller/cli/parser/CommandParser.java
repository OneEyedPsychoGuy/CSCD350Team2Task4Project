package cs350f20project.controller.cli.parser;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.command.*;
import cs350f20project.controller.command.behavioral.*;
import cs350f20project.controller.command.creational.*;
import cs350f20project.controller.command.meta.*;
import cs350f20project.controller.command.structural.*;
import cs350f20project.datatype.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandParser {

	//COMMANDS THAT NEED FIXING: 49
	// FIELDS
	private MyParserHelper parserHelper;
	private String commandText;
	private String[] commandTextArray;
	private String[] semicolonCommandsArray = null;
	private final String INT_REGEX = "^-?[0-9]\\d{0,7}$", DOUBLE_REGEX= "^-?[0-9]{0,8}.?\\d{0,8}$", POS_DOUBLE_REGEX = "^[0-9]{0,8}.?\\d{0,8}$";

	// CONSTRUCTOR
	public CommandParser(MyParserHelper parserHelper, String commandText)
    {
        this.parserHelper = parserHelper;
        this.commandText = commandText;
        if(commandText.contains(";")) {
        	this.semicolonCommandsArray = this.commandText.split("\\s*;\\s*");
        	// debug print statement
			System.out.println("semiColonCommandsArray: "+ Arrays.toString(semicolonCommandsArray));
        }
        this.commandTextArray = this.commandText.split("\\s+");
        System.out.println("USING TEAM 2 COMMAND PARSER"); //this makes sure that we are using our CommandParser class
    }//end of constructor

	// Main Parse Method
	public void parse() {
		// rule 1 rule 65
		if(semicolonCommandsArray!=null) {
			for (int i = 0; i < semicolonCommandsArray.length; i++) {
				this.commandTextArray = semicolonCommandsArray[i].split("\\s+");
				// debug print statement
				System.out.println("commandTextArray: " + Arrays.toString(commandTextArray));
				parseCommands();
			}
		}
        else {
            parseCommands();
        }
	} //end of main parse method

	private Angle parseAngle(String angleStr) {
		Angle angle = null;
		if (angleStr.matches(DOUBLE_REGEX)) {
			angle = new Angle(Double.parseDouble(angleStr));
		}
		return angle;
	}
	// Latitude(int degrees, int minutes, double seconds)
	private Latitude parseLatitude(String lat) {
		Latitude latitude = null;
		if(lat.contains("*") && lat.contains("'") && lat.contains("\"")) {
			String[] latArr = lat.split("\\*'\"");
			int degrees = 0, min = 0; double sec = 0;
			if (latArr.length >= 3) {
				if (latArr[0].matches(INT_REGEX)) {
					int temp = Integer.parseInt(latArr[0]);
					if (temp <= 90 && temp >= -90) {
						degrees = temp;
					}
				} else {
					System.out.println("Invalid angle for latitude.");
					return null;
				}
				if (latArr[1].matches(INT_REGEX)) {
					int temp = Integer.parseInt(latArr[1]);
					if (temp > 0) {
						min = temp;
					}
				} else {
					System.out.println("Invalid minutes for latitude.");
					return null;
				}
				if (latArr[2].matches(POS_DOUBLE_REGEX)) {
					sec = Double.parseDouble(latArr[2]);
				} else {
					System.out.println("Invalid seconds for latitude.");
					return null;
				}
			}
			latitude = new Latitude(degrees, min, sec);
		}
		return latitude;
	} //end of parseLatitude

	//  Longitude(int degrees, int minutes, double seconds)
	private Longitude parseLongitude(String longStr) {
		Longitude longitude = null;
		if(longStr.contains("*") && longStr.contains("'") && longStr.contains("\"")) {
			String[] longArr = longStr.split("\\*'\"");
			int degrees = 0, min = 0; double sec = 0;
			if (longArr.length >= 3) {
				if (longArr[0].matches(INT_REGEX)) {
					int temp = Integer.parseInt(longArr[0]);
					if (temp <= 180 && temp >= -180) {
						degrees = temp;
					}
				} else {
					System.out.println("Invalid angle for longitude.");
					return null;
				}
				if (longArr[1].matches(INT_REGEX)) {
					int temp = Integer.parseInt(longArr[1]);
					if (temp > 0) {
						min = temp;
					}
				} else {
					System.out.println("Invalid minutes for longitude.");
					return null;
				}
				if (longArr[2].matches(POS_DOUBLE_REGEX)) {
					sec = Double.parseDouble(longArr[2]);
				} else {
					System.out.println("Invalid seconds for longitude.");
					return null;
				}
			}
			longitude = new Longitude(degrees, min, sec);
		}
		return longitude;
	} //end of parseLongitude

	// CoordinatesDelta(double x, double y)
	private CoordinatesDelta parseCoordDelta(String coords) {
		CoordinatesDelta coordDelta= null;
		if(coords.contains(":")) {
			String[] coordArr = coords.split(":");
			if (coordArr.length >= 2) {
				String xStr = coordArr[0], yStr = coordArr[1];
				double xNum,yNum;
				if (xStr.matches(DOUBLE_REGEX) && yStr.matches(DOUBLE_REGEX)) {
					xNum = Double.parseDouble(xStr);
					yNum = Double.parseDouble(yStr);
					coordDelta= new CoordinatesDelta(xNum, yNum);
				}
			}
		}
		return coordDelta;
	} //end of parseCoordDelta

	// CoordinatesWorld(Latitude latitude, Longitude longitude)
	private CoordinatesWorld parseCoordWorld(String coords) {
		CoordinatesWorld coordWorld = null;
		if(coords.contains("/")) {
			String[] coordArr = coords.split("/");
			if (coordArr.length >= 2) {
				Latitude latitude = parseLatitude(coordArr[0]);
				Longitude longitude = parseLongitude(coordArr[1]);
				coordWorld = new CoordinatesWorld(latitude, longitude);
			}
		}
		return coordWorld;
	} //end of parseCoordWorld

	// ParseReference
	private CoordinatesWorld parseReferenceOrCoordWorld(String id_Coords) {
    	CoordinatesWorld coordWorld = null;
		if(id_Coords.startsWith("$")) {
			if (parserHelper.hasReference(id_Coords)) {
				coordWorld = parserHelper.getReference(id_Coords);
			}
		}
		else {
			coordWorld = this.parseCoordWorld(id_Coords);
		}
    	return coordWorld;
	} //end of parseReference

	// PARSE METHODS
    private void parseCommands() {
		A_Command command = null;
		// rules 2, 6-8, 11-12, 15
		if (this.commandTextArray.length>=3 && this.commandTextArray[0].equalsIgnoreCase("DO"))
		{
			command = parse_DO();
		} //end of parse_DO call

		// rules 22-25, 28-34, 39-49
		else if (this.commandTextArray.length>=3 && this.commandTextArray[0].equalsIgnoreCase("CREATE"))
		{
			command = parse_CREATE();
		}//end of parse_CREATE call

		// rule 51
		else if(this.commandTextArray.length>=1 && this.commandTextArray[0].equalsIgnoreCase("@EXIT"))
		{
			command = new CommandMetaDoExit();
		}//end of rule 51

		// rule 52
		else if(this.commandTextArray.length>=2 && this.commandTextArray[0].equalsIgnoreCase("@RUN"))
		{
			String file = this.commandTextArray[1];
			File tempFile = new File(file);
			if(tempFile.exists() && !tempFile.isDirectory()) {
				command = new CommandMetaDoRun(file);
			    System.out.println("@RUN " + file);
			}
			else {
				System.out.println(file + " file does not exist.");
			}
		}//end of rule 52

		// rule 55
		else if
		(
			this.commandTextArray.length>=3 &&
			this.commandTextArray[0].equalsIgnoreCase("CLOSE") &&
			this.commandTextArray[1].equalsIgnoreCase("VIEW")
        )
    	{
			String id = this.commandTextArray[2];
			command = new CommandMetaViewDestroy(id);
			System.out.println("CLOSE VIEW " + id);
		}//end of rule 55

		// rule 56 FIXED
		else if
		(
			this.commandTextArray.length>=13 &&
			this.commandTextArray[0].equalsIgnoreCase("OPEN") &&
			this.commandTextArray[1].equalsIgnoreCase("VIEW") &&
			this.commandTextArray[3].equalsIgnoreCase("ORIGIN") &&
			this.commandTextArray[5].equalsIgnoreCase("WORLD") &&
			this.commandTextArray[6].equalsIgnoreCase("WIDTH") &&
			this.commandTextArray[8].equalsIgnoreCase("SCREEN") &&
			this.commandTextArray[9].equalsIgnoreCase("WIDTH") &&
			this.commandTextArray[11].equalsIgnoreCase("HEIGHT")
		)
		{
			String id = this.commandTextArray[2];
			CoordinatesWorld coord = parseReferenceOrCoordWorld(this.commandTextArray[4]);
			String worldWidthStr = this.commandTextArray[7];
			String screenSizeX = this.commandTextArray[10];
			String screenSizeY = this.commandTextArray[12];
			int worldWidth = 0, screenX = 0, screenY = 0;
			if(coord!=null) {
				if (worldWidthStr.matches(INT_REGEX) && screenSizeX.matches(INT_REGEX) && screenSizeY.matches(INT_REGEX)) {
					worldWidth = Integer.parseInt(worldWidthStr);
					screenX = Integer.parseInt(screenSizeX);
					screenY = Integer.parseInt(screenSizeY);
					command = new CommandMetaViewGenerate(id, coord, worldWidth, new CoordinatesScreen(screenX, screenY));
			        System.out.println("OPEN VIEW id1:"+id+" ORIGIN:"+coord+" WORLD WIDTH:"+worldWidth+" SCREEN WIDTH: "+screenX+"HEIGHT:" + screenY);
				}	
			}
		}//end of rule 56

		// rule 60
		else if(this.commandTextArray.length>=1 && this.commandTextArray[0].equalsIgnoreCase("COMMIT"))
		{
			command = new CommandStructuralCommit();
			System.out.println("COMMIT");
		}//end of rule 60

		// rule 61
		else if
		(
			this.commandTextArray.length>=5 &&
			this.commandTextArray[0].equalsIgnoreCase("COUPLE") &&
			this.commandTextArray[1].equalsIgnoreCase("STOCK") &&
			this.commandTextArray[3].equalsIgnoreCase("AND")
		)
		{
			String stock_id1 = this.commandTextArray[2];
			String stock_id2 = this.commandTextArray[4];
			command = new CommandStructuralCouple(stock_id1, stock_id2);
			System.out.println("COUPLE STOCK id1:" +stock_id1+" AND id2:"+stock_id2);
		}//end of rule 61

		// rule 62
		else if
		(
			this.commandTextArray.length>=10 &&
			this.commandTextArray[0].equalsIgnoreCase("LOCATE") &&
			this.commandTextArray[1].equalsIgnoreCase("STOCK") &&
			this.commandTextArray[3].equalsIgnoreCase("ON") &&
			this.commandTextArray[4].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[6].equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray[8].equalsIgnoreCase("FROM")
		)
		{
			String stock_id1 = this.commandTextArray[2];
			String track_id2	= this.commandTextArray[5];
			double distance = Double.parseDouble(this.commandTextArray[7]);
			TrackLocator track_locator;

			if(this.commandTextArray[10].equalsIgnoreCase("START")) {
				track_locator = new TrackLocator(track_id2, distance, true);
			}
			else {
				track_locator = new TrackLocator(track_id2, distance, false);
			}
			command = new CommandStructuralLocate(stock_id1, track_locator);
			System.out.println("LOCATE STOCK id1:"+stock_id1+" ON TRACK id2:"+track_id2+" DISTANCE "+distance+" FROM " + this.commandTextArray[10]);
		}//end of rule 62

		// rule 65
		else if
		(
			this.commandTextArray.length>=5 &&
			this.commandTextArray[0].equalsIgnoreCase("UNCOUPLE") &&
			this.commandTextArray[1].equalsIgnoreCase("STOCK") &&
			this.commandTextArray[3].equalsIgnoreCase("AND")
		)
		{
			String stock_id1 = this.commandTextArray[2];
			String stock_id2 = this.commandTextArray[4];
			command = new CommandStructuralUncouple(stock_id1, stock_id2);
			System.out.println("UNCOUPLE STOCK id1:"+stock_id1+" AND id2:"+stock_id2);
		}
		// rule 66
		else if
		(
			this.commandTextArray.length>=5 &&
			this.commandTextArray[0].equalsIgnoreCase("USE") &&
			this.commandTextArray[2].equalsIgnoreCase("AS") &&
			this.commandTextArray[3].equalsIgnoreCase("REFERENCE")
        )
		{
			String id = this.commandTextArray[1];
            String coordStr = this.commandTextArray[4];
            CoordinatesWorld coordinates = this.parseCoordWorld(coordStr);
            if (coordinates!=null) {
    	        this.parserHelper.addReference(id, coordinates);
				System.out.println("USE id:"+id+" AS REFERENCE:"+ coordinates);
            }
		}//end of rule 66

		// FINAL CHECK
		if (command == null) {
			System.out.println("Invalid command");
		}else {
			//THIS SCHEDULES THE DESIRED COMMAND TO THE PARSERHELPER AFTER ITS BE
			//    {
			//    	A_Command command = nullEN PARSED
			this.parserHelper.getActionProcessor().schedule(command);
		}

    }//end method: parse

    private A_Command parse_DO() {
		A_Command command = null;
    	// rule 2
		if(this.commandTextArray.length>=3 && this.commandTextArray[1].equalsIgnoreCase("BRAKE"))
		{
			String id = this.commandTextArray[2];
			command = new CommandBehavioralBrake(id);
			System.out.println("DO BRAKE id:" + id);
		}//end of rule 2

		// rule 6
		else if
		(
			this.commandTextArray.length>=6 &&
			this.commandTextArray[1].equalsIgnoreCase("SELECT") &&
			this.commandTextArray[2].equalsIgnoreCase("DRAWBRIDGE") &&
			this.commandTextArray[4].equalsIgnoreCase("POSITION")
		)
		{
			String id = this.commandTextArray[3];
			boolean UPorDOWN;
			UPorDOWN = this.commandTextArray[5].equalsIgnoreCase("UP");
			command = new CommandBehavioralSelectBridge(id, UPorDOWN);
			System.out.println("DO SELECT DRAWBRIDGE id:"+id+" POSITION " + this.commandTextArray[5]);
		}//end of rule 6

		// rule 7 QUESTION: Does angle size matter? i.e. less than 360
		else if
		(
			this.commandTextArray.length>=7 &&
			this.commandTextArray[1].equalsIgnoreCase("SELECT") &&
			this.commandTextArray[2].equalsIgnoreCase("ROUNDHOUSE") &&
			this.commandTextArray[4].equalsIgnoreCase("POSITION")
		)
		{
			String id = this.commandTextArray[3];
			String angleStr = this.commandTextArray[5];
			if(angleStr.matches("DOUBLE_REGEX")) {
				double angleNum = Double.parseDouble(angleStr);
				if (angleNum <= 360) {
					Angle angle = new Angle(angleNum);
					String clockwiseStr = this.commandTextArray[6];
					if(clockwiseStr.equalsIgnoreCase("CLOCKWISE") || clockwiseStr.equalsIgnoreCase("COUNTERCLOCKWISE")) {
						boolean clockWise = clockwiseStr.equalsIgnoreCase("CLOCKWISE");
						command = new CommandBehavioralSelectRoundhouse(id, angle, clockWise);
						System.out.println("DO SELECT ROUNDHOUSE id:"+id+" POSITION angle:"+angle+" "+ clockwiseStr.toUpperCase());
					}
				}
			}
		}//end of rule 7

		// rule 8
		else if
		(
			this.commandTextArray.length>=6 &&
			this.commandTextArray[1].equalsIgnoreCase("SELECT") &&
			this.commandTextArray[2].equalsIgnoreCase("SWITCH") &&
			this.commandTextArray[4].equalsIgnoreCase("PATH")
		)
		{
			String id = this.commandTextArray[3];
			String primSec = this.commandTextArray[5];
			if (primSec.equalsIgnoreCase("PRIMARY") || primSec.equalsIgnoreCase("SECONDARY")) {
				boolean primaryorSecondary;
				primaryorSecondary = primSec.equalsIgnoreCase("PRIMARY");
				command = new CommandBehavioralSelectSwitch(id, primaryorSecondary);
				System.out.println("DO SELECT SWITCH id:"+id+" PATH "+primSec.toUpperCase());
			}

		}//end of rule 8

		// rule 11
		else if
		(
			this.commandTextArray.length>=5 &&
			this.commandTextArray[1].equalsIgnoreCase("SET") &&
			this.commandTextArray[3].equalsIgnoreCase("DIRECTION")
		)
		{
			String id = this.commandTextArray[2];
			String forwardStr = this.commandTextArray[4];
			if (forwardStr.equalsIgnoreCase("FORWARD") || forwardStr.equalsIgnoreCase("BACKWARD")) {
				boolean isForward = forwardStr.equalsIgnoreCase("FORWARD");
				command = new CommandBehavioralSetDirection(id, isForward);
				System.out.println("DO SET id:" + id + " DIRECTION " + forwardStr.toUpperCase());
			}
		}//end of rule 11

		// rule 12
		else if
		(
			this.commandTextArray.length>=5 &&
			this.commandTextArray[1].equalsIgnoreCase("SET") &&
			this.commandTextArray[2].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[3].equalsIgnoreCase("ENGINE")
		)
		{
			String id = this.commandTextArray[4];
			command = new CommandBehavioralSetReference(id);
			System.out.println("DO SET REFERENCE ENGINE id:"+id);
		}//end of rule 12

		// rule 15
		else if
		(
			this.commandTextArray.length>=5 &&
			this.commandTextArray[1].equalsIgnoreCase("SET") &&
			this.commandTextArray[3].equalsIgnoreCase("SPEED")
		)
		{
			String id = this.commandTextArray[2];
			String num = this.commandTextArray[4];
			if (num.matches(DOUBLE_REGEX)) {
				double number = Double.parseDouble(num);
				command = new CommandBehavioralSetSpeed(id, number);
				System.out.println("DO SET id:" + id + " SPEED " + number);
			}
		}// end of rule 15

		return command; //if nothing else, return null for now

    }//end method: parse_DO

    private A_Command parse_CREATE()
    {
    	A_Command command = null;
    	// rule 22
		if
		(   this.commandTextArray.length>=7 &&
			this.commandTextArray[1].equalsIgnoreCase("POWER") &&
			this.commandTextArray[2].equalsIgnoreCase("CATENARY") &&
			this.commandTextArray[4].equalsIgnoreCase("WITH") &&
			this.commandTextArray[5].equalsIgnoreCase("POLES")
		)
		{
			String id = this.commandTextArray[3];
			ArrayList<String> id_POLES = new ArrayList<String>();
			for(int i = 6; i < this.commandTextArray.length; i++) {
				id_POLES.add(this.commandTextArray[i]);
			}
			command = new CommandCreatePowerCatenary(id, id_POLES);
			System.out.println("CREATE POWER CATENARY id:"+id+" WITH POLES "+id_POLES.toString());
		}//end of rule 22

		// rule 23 FIXED
		else if
		(
			this.commandTextArray.length>=11 &&
			this.commandTextArray[1].equalsIgnoreCase("POWER") &&
			this.commandTextArray[2].equalsIgnoreCase("POLE") &&
			this.commandTextArray[4].equalsIgnoreCase("ON") &&
			this.commandTextArray[5].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[7].equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray[9].equalsIgnoreCase("FROM")
		)
		{
			String pole_ID = this.commandTextArray[3];
			String track_ID = this.commandTextArray[6];
			String distanceStr = this.commandTextArray[8];
			if (distanceStr.matches(DOUBLE_REGEX)) {
				double distance = Double.parseDouble(commandTextArray[8]);
				String startEnd = this.commandTextArray[10];
				TrackLocator track_locator;
				if (startEnd.equalsIgnoreCase("START") || startEnd.equalsIgnoreCase("END")) {
				    boolean isFromStart = false;
					if(this.commandTextArray[10].equalsIgnoreCase("START")) {
					    isFromStart = true;
					}
					track_locator = new TrackLocator(track_ID, distance, isFromStart);
					command = new CommandCreatePowerPole(pole_ID, track_locator);
					System.out.println("CREATE POWER POLE id:"+pole_ID+" ON TRACK id:"+track_ID+" DISTANCE "+distance+" FROM " +this.commandTextArray[10]);
				}
			}
		}//end of rule 23

		// rule 24 FIXED
		else if
		(
			this.commandTextArray.length>=9 &&
			this.commandTextArray[1].equalsIgnoreCase("POWER") &&
			this.commandTextArray[2].equalsIgnoreCase("STATION") &&
			this.commandTextArray[4].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[6].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[8].equalsIgnoreCase("WITH") &&
		(this.commandTextArray[9].equalsIgnoreCase("SUBSTATION") || this.commandTextArray[9].equalsIgnoreCase("SUBSTATIONS"))
        )
		{
			String station_ID = this.commandTextArray[3];
            CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[5]);
			CoordinatesDelta deltaCoord= this.parseCoordDelta(this.commandTextArray[7]);
			if (reference!=null && deltaCoord!=null) {
				ArrayList<String> substation_IDs = new ArrayList<>();
				for (int i = 10; i < this.commandTextArray.length; i++) {
					substation_IDs.add(this.commandTextArray[i]);
				}
				command = new CommandCreatePowerStation(station_ID, reference, deltaCoord, substation_IDs);
				System.out.println("CREATE POWER STATION id1:"+station_ID+" REFERENCE:"+reference+"  DELTA:"+deltaCoord+" WITH "+this.commandTextArray[9].toUpperCase()+":"+ substation_IDs.toString());
			}
		}//end of rule 24

		// rule 25 FIXED
		else if
		(
			this.commandTextArray.length>=11 &&
			this.commandTextArray[1].equalsIgnoreCase("POWER") &&
			this.commandTextArray[2].equalsIgnoreCase("SUBSTATION") &&
			this.commandTextArray[4].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[6].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[8].equalsIgnoreCase("WITH") &&
			this.commandTextArray[9].equalsIgnoreCase("CATENARIES")
		)
		{
			String substation_ID = this.commandTextArray[3];
			CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[5]);
			CoordinatesDelta deltaCoord= this.parseCoordDelta(this.commandTextArray[7]);
			if (reference!=null && deltaCoord!=null) {
				ArrayList<String> catenary_IDs= new ArrayList<>();
				for (int i = 10; i < this.commandTextArray.length; i++) {
					catenary_IDs.add(this.commandTextArray[i]);
				}
				command = new CommandCreatePowerSubstation(substation_ID, reference, deltaCoord, catenary_IDs);
				System.out.println("CREATE POWER SUBSTATION id:" + substation_ID + " REFERENCE:"+reference+"  DELTA:" +deltaCoord+ " WITH CATENARIES:" + catenary_IDs.toString());
			}
		}// end of rule 25

		// rule 28
		else if
		(
			this.commandTextArray.length>=6 &&
			this.commandTextArray[1].equalsIgnoreCase("STOCK") &&
			this.commandTextArray[2].equalsIgnoreCase("CAR") &&
			this.commandTextArray[4].equalsIgnoreCase("AS") &&
			this.commandTextArray[5].equalsIgnoreCase("BOX")
		)
		{
			String stockboxCar_ID = this.commandTextArray[3];
			command = new CommandCreateStockCarBox(stockboxCar_ID);
			System.out.println("CREATE STOCK CAR id:"+stockboxCar_ID+" AS BOX");
		}//end of rule 28

		// rule 29
		else if
		(
			this.commandTextArray.length>=6 &&
			this.commandTextArray[1].equalsIgnoreCase("STOCK") &&
			this.commandTextArray[2].equalsIgnoreCase("CAR") &&
			this.commandTextArray[4].equalsIgnoreCase("AS") &&
			this.commandTextArray[5].equalsIgnoreCase("CABOOSE")
		)
		{
			String stockCabooseCar_ID = this.commandTextArray[3];
			command = new CommandCreateStockCarCaboose(stockCabooseCar_ID);
			System.out.println("CREATE STOCK CAR id:"+stockCabooseCar_ID+" AS CABOOSE");
		}//end of rule 29

		// rule 30
		else if
		(
			this.commandTextArray.length>=4 &&
			this.commandTextArray[1].equalsIgnoreCase("STOCK") &&
			this.commandTextArray[2].equalsIgnoreCase("CAR") &&
			this.commandTextArray[4].equalsIgnoreCase("AS") &&
			this.commandTextArray[5].equalsIgnoreCase("FLATBED")
        )
		{
			String flatbedCar_ID = this.commandTextArray[3];
			command = new CommandCreateStockCarFlatbed(flatbedCar_ID);
			System.out.println("CREATE STOCK CAR id:"+flatbedCar_ID+" AS FLATBED");
		}//end of rule 30

		// rule 31
		else if
		(
			this.commandTextArray.length>=3 &&
			this.commandTextArray[1].equalsIgnoreCase("STOCK") &&
			this.commandTextArray[2].equalsIgnoreCase("CAR") &&
			this.commandTextArray[4].equalsIgnoreCase("AS") &&
			this.commandTextArray[5].equalsIgnoreCase("PASSENGER")
		)
		{
			String passengerCar_ID = this.commandTextArray[3];
			command = new CommandCreateStockCarPassenger(passengerCar_ID);
			System.out.println("CREATE STOCK CAR id:"+passengerCar_ID+" AS PASSENGER");
		}//end of rule 31

		// rule 32
		else if
		(
			this.commandTextArray.length>=4 &&
			this.commandTextArray[1].equalsIgnoreCase("STOCK") &&
			this.commandTextArray[2].equalsIgnoreCase("CAR") &&
			this.commandTextArray[4].equalsIgnoreCase("AS") &&
			this.commandTextArray[5].equalsIgnoreCase("TANK")
		)
		{
			String tankCar_ID = this.commandTextArray[3];
			command = new CommandCreateStockCarTank(tankCar_ID);
			System.out.println("CREATE STOCK CAR id:"+tankCar_ID+" AS TANK");
		}//end of rule 32

		// rule 33
		else if
		(
			this.commandTextArray.length>=3 &&
			this.commandTextArray[1].equalsIgnoreCase("STOCK") &&
			this.commandTextArray[2].equalsIgnoreCase("CAR") &&
			this.commandTextArray[4].equalsIgnoreCase("AS") &&
			this.commandTextArray[5].equalsIgnoreCase("TENDER")
		)
		{
			String stockCar_tender_id = this.commandTextArray[3];
			command = new CommandCreateStockCarTender(stockCar_tender_id);
			System.out.println("CREATE STOCK CAR id:"+stockCar_tender_id+" AS TENDER");
		}//end of rule 33

		// rule 34
		else if
		(
			this.commandTextArray.length>=15 &&
			this.commandTextArray[1].equalsIgnoreCase("STOCK") &&
			this.commandTextArray[2].equalsIgnoreCase("ENGINE") &&
			this.commandTextArray[4].equalsIgnoreCase("AS") &&
			this.commandTextArray[5].equalsIgnoreCase("DIESEL") &&
			this.commandTextArray[6].equalsIgnoreCase("ON") &&
			this.commandTextArray[7].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[9].equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray[11].equalsIgnoreCase("FROM") &&
			this.commandTextArray[13].equalsIgnoreCase("FACING")
		)
		{
			String engine_ID = this.commandTextArray[3];
			String track_ID = this.commandTextArray[8];
			double distance = Double.parseDouble(this.commandTextArray[10]);
			boolean isFacingStart = false;
			TrackLocator track_locator;
			if(this.commandTextArray[12].equalsIgnoreCase("START")) {
				track_locator = new TrackLocator(track_ID, distance, true);
			}
			else {
				track_locator = new TrackLocator(track_ID, distance, false);
			}
			if(this.commandTextArray[14].equalsIgnoreCase("START")) {
				isFacingStart=true;
			}
			command = new CommandCreateStockEngineDiesel(engine_ID, track_locator, isFacingStart);
			System.out.println("CREATE STOCK ENGINE id:"+engine_ID+" AS DIESEL ON TRACK id:"+track_ID+" DISTANCE "+distance+" "+this.commandTextArray[12]+" FACING "+ this.commandTextArray[14]);
		}//end of rule 34

		// rule 39 FIXED
		else if
		(
			this.commandTextArray.length>=14 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("BRIDGE") &&
			this.commandTextArray[3].equalsIgnoreCase("DRAW") &&
			this.commandTextArray[5].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[7].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[8].equalsIgnoreCase("START") &&
			this.commandTextArray[10].equalsIgnoreCase("END") &&
			this.commandTextArray[12].equalsIgnoreCase("ANGLE")
		)
		{
			String id = this.commandTextArray[4];
			CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[6]);
			CoordinatesDelta deltaStart = this.parseCoordDelta(this.commandTextArray[9]);
			CoordinatesDelta deltaEnd = this.parseCoordDelta(this.commandTextArray[11]);
			PointLocator locator = null;
			String angleStr = this.commandTextArray[13];
			if (reference!=null && deltaStart!=null && deltaEnd!=null) {
				locator = new PointLocator(reference, deltaStart, deltaEnd);
			}
			Angle angle = this.parseAngle(angleStr);
			if (locator!=null && angle != null) {
				command = new CommandCreateTrackBridgeDraw(id, locator, angle);
				System.out.println("CREATE TRACK BRIDGE DRAW id:"+ id+" REFERENCE:"+reference+" DELTA START:"+deltaStart+" END:"+deltaEnd+" ANGLE:" + angle);
			}
		}//end of rule 39

		// rule 40 FIXED
		else if
		(
			this.commandTextArray.length>=11 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("BRIDGE") &&
			this.commandTextArray[4].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[6].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[7].equalsIgnoreCase("START") &&
			this.commandTextArray[9].equalsIgnoreCase("END")
		)
		{
			String id = this.commandTextArray[3];
			CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[5]);
			CoordinatesDelta deltaStart = this.parseCoordDelta(this.commandTextArray[8]);
			CoordinatesDelta deltaEnd = this.parseCoordDelta(this.commandTextArray[10]);
			PointLocator locator = null;
			if (reference!=null && deltaStart!=null && deltaEnd!=null) {
				locator = new PointLocator(reference, deltaStart, deltaEnd);
			}
			if (locator!=null) {
				command = new CommandCreateTrackBridgeFixed(id, locator);
				System.out.println("CREATE TRACK BRIDGE:"+ id +" REFERENCE:"+reference+" DELTA START:"+deltaStart+" END:"+ deltaEnd);
			}
		}//end of rule 40

		// rule 41 FIXED
		else if
		(
			this.commandTextArray.length>=11 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("CROSSING") &&
			this.commandTextArray[4].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[6].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[7].equalsIgnoreCase("START") &&
			this.commandTextArray[9].equalsIgnoreCase("END")
		)
		{
			String id = this.commandTextArray[3];
			CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[5]);
			CoordinatesDelta deltaStart = this.parseCoordDelta(this.commandTextArray[8]);
			CoordinatesDelta deltaEnd = this.parseCoordDelta(this.commandTextArray[10]);
			PointLocator locator = null;
			if (reference!=null && deltaStart!=null && deltaEnd!=null) {
				locator = new PointLocator(reference, deltaStart, deltaEnd);
			}
			if (locator!=null) {
				command = new CommandCreateTrackCrossing(id, locator);
				System.out.println("CREATE TRACK CROSSING:"+ id +" REFERENCE:"+reference+" DELTA START:"+deltaStart+" END:"+ deltaEnd);
			}
		}//end of rule 41

		// rule 42 FIXED
		else if
		(
			this.commandTextArray.length>=11 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("CROSSOVER") &&
			this.commandTextArray[4].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[6].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[7].equalsIgnoreCase("START") &&
			this.commandTextArray[9].equalsIgnoreCase("END") &&
			this.commandTextArray[11].equalsIgnoreCase("START") &&
			this.commandTextArray[13].equalsIgnoreCase("END")
		)
		{
			String id = this.commandTextArray[3];
			CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[5]);
			CoordinatesDelta deltaStart1 = this.parseCoordDelta(this.commandTextArray[8]);
			CoordinatesDelta deltaEnd1 = this.parseCoordDelta(this.commandTextArray[10]);
			CoordinatesDelta deltaStart2 = this.parseCoordDelta(this.commandTextArray[12]);
			CoordinatesDelta deltaEnd2 = this.parseCoordDelta(this.commandTextArray[14]);
			if (reference!=null && deltaStart1!=null && deltaEnd1!=null && deltaStart2!=null && deltaEnd2!=null) {
				command = new CommandCreateTrackCrossover(id, reference, deltaStart1, deltaEnd1, deltaStart2, deltaEnd2);
				System.out.println("CREATE TRACK CROSSOVER:"+ id +" REFERENCE:"+reference+" DELTA START:"+deltaStart1+" END:"+ deltaEnd1 +" START:"+deltaStart2+" END:"+deltaEnd2);
			}
		}//end of rule 42

		// rule 43 FIXED
		else if
		(
			this.commandTextArray.length>=13 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("CURVE") &&
			this.commandTextArray[4].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[6].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[7].equalsIgnoreCase("START") &&
			this.commandTextArray[9].equalsIgnoreCase("END") &&
			((this.commandTextArray[11].equalsIgnoreCase("DISTANCE") && this.commandTextArray[12].equalsIgnoreCase("ORIGI")) ||
			this.commandTextArray[11].equalsIgnoreCase("ORIGIN"))
		)
		{
			String id = this.commandTextArray[3];
			CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[5]);
			CoordinatesDelta deltaStart = this.parseCoordDelta(this.commandTextArray[8]);
			CoordinatesDelta deltaEnd = this.parseCoordDelta(this.commandTextArray[10]);
			if (reference!=null && deltaStart!=null && deltaEnd!=null) {
				if (this.commandTextArray[11].equalsIgnoreCase("DISTANCE")) {
			    	String numStr = this.commandTextArray[13];
			    	if (numStr.matches(DOUBLE_REGEX)) {
			    		double deltaOrigin = Double.parseDouble(numStr);
						command = new CommandCreateTrackCurve(id, reference, deltaStart, deltaEnd, deltaOrigin);
						System.out.println("CREATE TRACK CURVE id:"+id+" REFERENCE:"+reference+" DELTA START:"+deltaStart+" END:"+deltaEnd+ " DISTANCE ORIGIN "+ deltaOrigin);
					}
				}
				else if (this.commandTextArray[11].equalsIgnoreCase("ORIGIN")) {
					CoordinatesDelta origin = this.parseCoordDelta(this.commandTextArray[12]);
					if (origin != null) {
						command = new CommandCreateTrackCurve(id, reference, deltaStart, deltaEnd, origin);
						System.out.println("CREATE TRACK CURVE id:"+id+" REFERENCE:"+reference+" DELTA START:"+deltaStart+" END:"+deltaEnd+ " ORIGIN "+ origin);
					}
				}
			}
		}//end of rule 43

		// rule 44 FIXED
		else if
		(
			this.commandTextArray.length>=11 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("END") &&
			this.commandTextArray[4].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[6].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[7].equalsIgnoreCase("START") &&
			this.commandTextArray[9].equalsIgnoreCase("END")
		)
		{
		    String id  = this.commandTextArray[3];
		    CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[5]);
			CoordinatesDelta deltaStart = this.parseCoordDelta(this.commandTextArray[8]);
			CoordinatesDelta deltaEnd = this.parseCoordDelta(this.commandTextArray[10]);
			if (reference!=null && deltaStart!=null && deltaEnd!=null) {
				PointLocator locator = new PointLocator(reference, deltaStart, deltaEnd);
				command = new CommandCreateTrackEnd(id, locator);
				System.out.println("CREATE TRACK END id:" + id + " REFERENCE:" + reference + " DELTA START:" + deltaStart + " END:" + deltaEnd);
			}
		}//end of rule 44

		// rule 45 FIXED
		else if
		(
			this.commandTextArray.length>=7 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("LAYOUT") &&
			this.commandTextArray[4].equalsIgnoreCase("WITH") &&
			this.commandTextArray[5].equalsIgnoreCase("TRACKS")
		)
		{
			String id = this.commandTextArray[3];
			ArrayList<String> track_IDs = new ArrayList<>();
			for (int i = 6; i < this.commandTextArray.length; i++) {
				track_IDs.add(this.commandTextArray[i]);
			}
			command = new CommandCreateTrackLayout(id, track_IDs);
			System.out.println("CREATE TRACK LAYOUT id:"+id+" WITH TRACKS:"+ track_IDs);
		}//end of rule 45

		// rule 46 FIXED
		else if
		(
			this.commandTextArray.length>=23 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("ROUNDHOUSE") &&
			this.commandTextArray[4].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[6].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[7].equalsIgnoreCase("ORIGIN") &&
			this.commandTextArray[9].equalsIgnoreCase("ANGLE") &&
			this.commandTextArray[10].equalsIgnoreCase("ENTRY") &&
			this.commandTextArray[12].equalsIgnoreCase("START") &&
			this.commandTextArray[14].equalsIgnoreCase("END") &&
			this.commandTextArray[16].equalsIgnoreCase("WITH") &&
			this.commandTextArray[18].equalsIgnoreCase("SPURS") &&
			this.commandTextArray[19].equalsIgnoreCase("LENGTH") &&
			this.commandTextArray[21].equalsIgnoreCase("TURNTABLE") &&
			this.commandTextArray[22].equalsIgnoreCase("LENGTH")
		)
		{
			String id = this.commandTextArray[3];
			CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[5]);
			CoordinatesDelta deltaOrigin = this.parseCoordDelta(this.commandTextArray[8]);
			Angle angleEntry = this.parseAngle(this.commandTextArray[11]);
			Angle angleStart = this.parseAngle(this.commandTextArray[13]);
			Angle angleEnd = this.parseAngle(this.commandTextArray[15]);
			String numStr = this.commandTextArray[17], spurLenStr = this.commandTextArray[20], turnLenStr = this.commandTextArray[23];
			if (numStr.matches(INT_REGEX) && spurLenStr.matches(DOUBLE_REGEX) && turnLenStr.matches(DOUBLE_REGEX)) {
				int num = Integer.parseInt(numStr);
				double spurLen= Double.parseDouble(spurLenStr);
				double turnLen= Double.parseDouble(turnLenStr);
				command = new CommandCreateTrackRoundhouse(id, reference, deltaOrigin, angleEntry, angleStart, angleEnd, num, spurLen, turnLen);
				System.out.println("CREATE TRACK ROUNDHOUSE id:"+id+" REFERENCE:"+reference+" DELTA ORIGIN "+ deltaOrigin+" ANGLE ENTRY"+angleEntry+" START:"+angleStart+" END:"+angleEnd+" WITH:"+ num + " SPURS LENGTH:"+spurLen+" TURNTABLE LENGTH:"+turnLen);
			}
		}//end of rule 46

		// rule 47 FIXED
		else if
		(
			this.commandTextArray.length>=11 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("STRAIGHT") &&
			this.commandTextArray[4].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[6].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[7].equalsIgnoreCase("START") &&
			this.commandTextArray[9].equalsIgnoreCase("END")
		)
		{
			String id = this.commandTextArray[3];
			CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[5]);
			CoordinatesDelta deltaStart = this.parseCoordDelta(this.commandTextArray[8]);
			CoordinatesDelta deltaEnd = this.parseCoordDelta(this.commandTextArray[10]);
			if (reference!=null && deltaStart!=null && deltaEnd!=null) {
			    PointLocator locator = new PointLocator(reference, deltaStart, deltaEnd);
				command = new CommandCreateTrackStraight(id, locator);
				System.out.println("CREATE TRACK STRAIGHT id:"+id+" REFERENCE:"+reference+" DELTA START:"+deltaStart+" END:"+ deltaEnd);
			}
		}//end of rule 47

		// rule 48 FIXED
		else if
		(
			this.commandTextArray.length>=22 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("SWITCH") &&
			this.commandTextArray[3].equalsIgnoreCase("TURNOUT") &&
			this.commandTextArray[5].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[7].equalsIgnoreCase("STRAIGHT") &&
			this.commandTextArray[8].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[9].equalsIgnoreCase("START") &&
			this.commandTextArray[11].equalsIgnoreCase("END") &&
			this.commandTextArray[13].equalsIgnoreCase("CURVE") &&
			this.commandTextArray[14].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[15].equalsIgnoreCase("START") &&
			this.commandTextArray[17].equalsIgnoreCase("END") &&
			this.commandTextArray[19].equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray[20].equalsIgnoreCase("ORIGIN")
		)
		{
			String id = this.commandTextArray[4];
			CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[6]);
			CoordinatesDelta deltaStart1 = this.parseCoordDelta(this.commandTextArray[10]);
			CoordinatesDelta deltaEnd1 = this.parseCoordDelta(this.commandTextArray[12]);
			CoordinatesDelta deltaStart2 = this.parseCoordDelta(this.commandTextArray[16]);
			CoordinatesDelta deltaEnd2 = this.parseCoordDelta(this.commandTextArray[18]);
			String originNumStr = this.commandTextArray[21];
			if (originNumStr.matches(DOUBLE_REGEX) && reference!=null && deltaStart1!=null && deltaEnd1!=null && deltaStart2!=null && deltaEnd2!=null) {
				double originNum = Double.parseDouble(originNumStr);
				CoordinatesDelta deltaOrigin = ShapeArc.calculateDeltaOrigin(reference, deltaStart2, deltaEnd2, originNum);
				command = new CommandCreateTrackSwitchTurnout(id, reference, deltaStart1, deltaEnd1, deltaStart2, deltaEnd2, deltaOrigin);
				System.out.println("CREATE TRACK SWITCH TURNOUT id:" + id + " REFERENCE:" + reference + " STRAIGHT DELTA START" + deltaStart1 + " END:" + deltaEnd1 + " CURVE DELTA START:" + deltaStart2 + " END:" + deltaEnd2 + "  DISTANCE ORIGIN:" + originNum);
			}
		}//end of rule 48

		// rule 49 FIXED
		else if
		(
			this.commandTextArray.length>=23 &&
			this.commandTextArray[1].equalsIgnoreCase("TRACK") &&
			this.commandTextArray[2].equalsIgnoreCase("SWITCH") &&
			this.commandTextArray[3].equalsIgnoreCase("WYE") &&
			this.commandTextArray[5].equalsIgnoreCase("REFERENCE") &&
			this.commandTextArray[7].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[8].equalsIgnoreCase("START") &&
			this.commandTextArray[10].equalsIgnoreCase("END") &&
			this.commandTextArray[12].equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray[13].equalsIgnoreCase("ORIGIN") &&
			this.commandTextArray[15].equalsIgnoreCase("DELTA") &&
			this.commandTextArray[16].equalsIgnoreCase("START") &&
			this.commandTextArray[18].equalsIgnoreCase("END") &&
			this.commandTextArray[20].equalsIgnoreCase("DISTANCE") &&
			this.commandTextArray[21].equalsIgnoreCase("ORIGIN")
		)
		{
			String id = this.commandTextArray[4];
			CoordinatesWorld reference = this.parseReferenceOrCoordWorld(this.commandTextArray[6]);
			CoordinatesDelta deltaStart1 = this.parseCoordDelta(this.commandTextArray[9]);
			CoordinatesDelta deltaEnd1 = this.parseCoordDelta(this.commandTextArray[11]);
			String originNum1Str= this.commandTextArray[14];
			CoordinatesDelta deltaStart2 = this.parseCoordDelta(this.commandTextArray[17]);
			CoordinatesDelta deltaEnd2 = this.parseCoordDelta(this.commandTextArray[19]);
			String originNum2Str= this.commandTextArray[22];
			if (originNum1Str.matches(DOUBLE_REGEX) && reference!=null && deltaStart1!=null && originNum2Str.matches(DOUBLE_REGEX) && deltaEnd1!=null && deltaStart2!=null && deltaEnd2!=null) {
				double originNum1 = Double.parseDouble(originNum1Str);
				double originNum2 = Double.parseDouble(originNum2Str);
				CoordinatesDelta deltaOrigin1 = ShapeArc.calculateDeltaOrigin(reference, deltaStart1, deltaEnd1, originNum1);
				CoordinatesDelta deltaOrigin2 = ShapeArc.calculateDeltaOrigin(reference, deltaStart2, deltaEnd2, originNum2);
				command = new CommandCreateTrackSwitchWye(id, reference, deltaStart1, deltaEnd1, deltaOrigin1, deltaStart2, deltaEnd2, deltaOrigin2);
				System.out.println("CREATE TRACK SWITCH WYE id:"+id+" REFERENCE:"+reference+" DELTA START:"+deltaStart1+" END:"+ deltaEnd2+" DISTANCE ORIGIN:"+ originNum1+" DELTA START:"+ deltaStart2+" END:"+ deltaEnd2+" DISTANCE ORIGIN:"+ originNum2);
			}
		}//end of rule 49

		return command; //if nothing else, return null
    
    }//end method: parse_CREATE

}//end class: CommandParser
