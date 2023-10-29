package controller;

import java.util.Scanner;

import business.ExecuteMapsCommands;
import business.MainPlayPhaseBusinessCommands;
import business.SingleGamePlayerCommands;
import logger.GeneralException;
import model.Player;
import model.ResponseWrapper;

public class MainPlayPhaseController {
	
	private Scanner d_MainPlaylSetupCommands;
	private GeneralException gException;
	public static final String INCORRECT_COMMAND="Please enter proper command";
	private MainPlayPhaseBusinessCommands mainPlayPhaseBusinessCommands;

	public MainPlayPhaseController() {
		
		d_MainPlaylSetupCommands = new Scanner(System.in);
		gException=new GeneralException();
		mainPlayPhaseBusinessCommands = new MainPlayPhaseBusinessCommands();
		
	}
	
	public ResponseWrapper getMainPlaySetUpCommandsFromUser(Player currentPlayer) throws GeneralException {
		String l_userEnteredMainPlayCommands = d_MainPlaylSetupCommands.nextLine();

		if (l_userEnteredMainPlayCommands.trim().isEmpty()) {
			
			return new ResponseWrapper(404, INCORRECT_COMMAND); 
		}
		String[] l_splitInitialSetupCommand = l_userEnteredMainPlayCommands.trim().replaceAll(" +", " ").split("\\s+");
		
		gException.validateCommand(l_userEnteredMainPlayCommands);
		
		switch (l_splitInitialSetupCommand[0]) {

		case "deploy":
			
			return mainPlayPhaseBusinessCommands.deploy(currentPlayer,l_splitInitialSetupCommand[1], Integer.parseInt(l_splitInitialSetupCommand[2]));
			
		case "advance":
			return mainPlayPhaseBusinessCommands.advance(currentPlayer,l_splitInitialSetupCommand[1],l_splitInitialSetupCommand[2],Integer.parseInt(l_splitInitialSetupCommand[3]));
		
		case "bomb":
			return mainPlayPhaseBusinessCommands.bomb(currentPlayer, l_splitInitialSetupCommand[1]);
		
		default:
			return new ResponseWrapper(404, INCORRECT_COMMAND); 
		}
		

	}
	
	
	

}
