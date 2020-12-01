package cs350Team2;
import cs350f20project.Startup;
import cs350f20project.controller.cli.parser.ParseException;

public class StartClass {

	public static void main(String[] args) throws ParseException  {
		Startup startup = new Startup();
		startup.go();
	}//end main
	
}//end class: startClass
