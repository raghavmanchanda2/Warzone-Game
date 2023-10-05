import controller.MapEngineController;
import logger.ConsoleWriter;
import logger.GeneralException;
import logger.Logger;
import model.ResponseWrapper;
public class MapEngine {
	
	public static String D_CURRENT_MAP ;
	private MapEngineController d_mapEngineController;
	
	private Logger d_logger;
	private ConsoleWriter d_consoleWriter;
	
	
	
	public MapEngine() {
		d_mapEngineController = new MapEngineController();
		d_logger = new Logger();
		d_consoleWriter = new ConsoleWriter();
		d_logger.addObserver(d_consoleWriter);
	}
	
	private void printAvailableMapCommands() {
		d_logger.setLogMessage("****************************************");
		d_logger.setLogMessage("************** MAP ENGINE **************");
		d_logger.setLogMessage("****************************************");
		d_logger.setLogMessage("");
		d_logger.setLogMessage("-> To edit an existing map or create a new map file: editmap filename(.map)");
		d_logger.setLogMessage("-> To continue to the StartUp Phase: continue");
		d_logger.setLogMessage("");
		d_logger.setLogMessage("***** Input any command to proceed *****");
		d_logger.setLogMessage("****(Getting input from the user...)****");
	}
	
	private void printEditMapCommands() {
		d_logger.setLogMessage("****************************************");
		d_logger.setLogMessage("************ EDIT MAP PHASE ************");
		d_logger.setLogMessage("****************************************");
		d_logger.setLogMessage("");
		d_logger.setLogMessage("-> To add a continent : editcontinent -add continentID continentvalue \n"
				+ "-> To remove a continent : editcontinent -remove continentID \n"
				+ "-> To add a country : editcountry -add countryID continentID \n"
				+ "-> To remove a country : editcountry -remove countryID \n"
				+ "-> To add a neighbour : editneighbour -add countryID neighborcountryID \n"
				+ "-> To remove a neighbour : editneighbour -remove countryID neighborcountryID \n"
				+ "-> To show the map : showmap \n"
				+ "-> To save the map : savemap filename \n"
				+ "-> To validate the map : validatemap \n"
				+ "-> To exit map engine : exit");
		d_logger.setLogMessage("");
		d_logger.setLogMessage("***** Input any command to proceed *****");
		d_logger.setLogMessage("****(Getting input from the user...)****");
	}
	
	public void startMapEngine() {
		
		while(true) {
			this.printAvailableMapCommands();
			ResponseWrapper mainMapCommandResponse;
			try {
				mainMapCommandResponse = d_mapEngineController.getMainMapCommandsFromUser();
			} catch (GeneralException exception) {
				mainMapCommandResponse=new ResponseWrapper(404, exception.getMessage());
			}
			d_logger.setLogMessage(mainMapCommandResponse.getDescription());
				if (mainMapCommandResponse.getStatusValue() == 204){
					break;
				}else if(mainMapCommandResponse.getStatusValue() == 200) {
					while(true) {
						this.printEditMapCommands();
						ResponseWrapper editMapCommandResponse;
						try {
							editMapCommandResponse = d_mapEngineController.getEditMapCommandsFromUser();
						} catch (GeneralException exception) {
							editMapCommandResponse=new ResponseWrapper(404, exception.getMessage());
						}
						d_logger.setLogMessage(editMapCommandResponse.getDescription());
						if(editMapCommandResponse.getStatusValue() == 204) {
							break;
						}
					}
				}
		}
			
	}
}