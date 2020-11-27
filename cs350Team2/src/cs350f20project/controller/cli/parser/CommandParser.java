package cs350f20project.controller.cli.parser;

import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.meta.CommandMetaDoExit;
import cs350f20project.datatype.Angle;

public class CommandParser {

	// FIELDS
	private MyParserHelper parserHelper;
	private String commandText;

	// CONSTRUCTORS
    public CommandParser(MyParserHelper parserHelper, String commandText){
        this.parserHelper = parserHelper;
        this.commandText = commandText;

        System.out.println("HI USING OUR PARSER");
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

        if (this.commandText.equalsIgnoreCase("@exit"))
        {
        	A_Command command = new CommandMetaDoExit();
        	this.parserHelper.getActionProcessor().schedule(command);
        }

    }//end method: parse


}//end class: CommandParser
