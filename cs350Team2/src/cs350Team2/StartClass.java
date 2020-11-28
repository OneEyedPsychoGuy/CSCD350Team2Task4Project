package cs350Team2;
import cs350f20project.Startup;
import cs350f20project.controller.ActionProcessor;
import cs350f20project.controller.Controller;
import cs350f20project.controller.cli.CommandLineInterface;
import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.cli.parser.ParseException;
//import cs350f20project.controller.cli.parser.CommandParser; //DAN'S SOLUTION

public class StartClass {

	public static void main(String[] args) throws ParseException  {
		
		Startup startup = new Startup();
		startup.go();
		
		/*
		//MyParserHelper parserHelper = new MyParserHelper();
		MyParserHelper parserHelper = new MyParserHelper(new ActionProcessor(new CommandLineInterface(new Controller())));
		
		String commandText = "@exit";
		CommandParser parser = new CommandParser(parserHelper, commandText);
		parser.parse();
		*/
		

	}//end main
	
}//end class: startClass
