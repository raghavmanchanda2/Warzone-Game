package business.Order;

import model.Card;
import model.Country;
import model.Player;

/**
 * Class that defines bomb functionalities
 * 
 * @author Kevin
 * @author ishaanbajaj
 * @version build 2
 */
public class BombOrder implements Order{

	/**
	 * Object of class country - the country to target
	 */
	private Country targetCountry;
	/**
	 * Object of player class - to get current turn player
	 */
	private Player player;
	
	/**
	 * parameterized constructor that to build a bomb order
	 * @param p_player - player that wants to execute a bomb order
	 * @param p_targetCountry - target country that will get bombed
	 */
	public BombOrder(Player p_player, Country p_targetCountry) {
		super();
		player = p_player;
		targetCountry = p_targetCountry;
		
	}
	

	/**
	 * Target country will lose half their armies
	 */
	@Override
	public void execute() {
		
		int beforeBomb = targetCountry.getArmies();
		
		int afterBomb = beforeBomb/2;
		
		targetCountry.setArmy(afterBomb);
		
		
	}


	/**
	 * 1. Check if player has a bomb card
	 * 2. Check if target country belongs in the map
	 * 3. check if target country does not belong to the player
	 * 4. If the above are true, remove bomb card from player and return true
	 */
	@Override
	public boolean valid() {
		
		boolean hasCard = false;
		
		for(Card card : player.getCardList()) {
			if(card.getCardType() == Card.CardType.BOMB) {
				hasCard = true;
			}
		}
		if(!hasCard) {
			return false;
		}
		
		if(targetCountry == null) {
			return false;
		}
		else if(!player.getCountriesHold().contains(targetCountry)) {
			for(Country country : player.getCountriesHold()) {
				if(country.getNeighbors().contains(targetCountry)) {
					
					for(Card card : player.getCardList()) {
						if(card.getCardType() == Card.CardType.BOMB) {
							player.getCardList().remove(card);
							break;
						}
					}
					
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Print execution of bomb order
	 */
	@Override
	public void printOrder() {
		// TODO Auto-generated method stub
		
	}

}