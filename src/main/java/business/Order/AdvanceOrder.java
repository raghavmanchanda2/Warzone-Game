package business.Order;

import java.util.Random;

import model.Country;
import model.Player;

public class AdvanceOrder implements Order{

	private Country fromCountry;
	private Country targetCountry;
	private int to_deploy_armies;
	private Player player;
	
	Random random = new Random();
	int attack, defense;
	

	public AdvanceOrder(Country fromCountry, Country targetCountry, int to_deploy_armies, Player player) {
		super();
		this.fromCountry = fromCountry;
		this.targetCountry = targetCountry;
		this.to_deploy_armies = to_deploy_armies;
		this.player = player;
	}
	
	@Override
	public void execute() {
		
		if(player.getCountriesHold().contains(targetCountry)) {
			targetCountry.armiesDeploy(to_deploy_armies);
		}
		else if(player.getPeaceWith() == targetCountry.getCountryOwner()) {
			System.out.println("You have a peace treaty with: " 
								+ targetCountry.getCountryOwner().getPlayerName() 
								+ " - attack is cancelled on county: "
								+ targetCountry.getCountryId());
		}
		else {
			int totalAttackingArmy = to_deploy_armies;
			int totalDefendingArmy = targetCountry.getArmies();
			
			for(int i = 0; i < totalAttackingArmy; ++i) {
				attack = random.nextInt(10) + 1;
				if(targetCountry.getArmies() == 0) {
					break;
				}
				else if(attack <= 6) {
					targetCountry.armyUnitDefeat();
				}
			}
			
			for(int i = 0; i < totalDefendingArmy; ++i) {
				defense = random.nextInt(10) + 1;
				if(totalAttackingArmy == 0) {
					break;
				}
				else if(defense <= 7) {
					--totalAttackingArmy;
					fromCountry.armyUnitDefeat();
				}
			}
			
			if(totalAttackingArmy > 0 && targetCountry.getArmies() == 0) {
				fromCountry.armiesRemove(totalAttackingArmy);
				targetCountry.armiesDeploy(totalAttackingArmy);
				
				player.addCountry(targetCountry);
			}
		}
		
	}

	@Override
	public boolean valid() {
		
		if(fromCountry == null || targetCountry == null) {
			return false;
		}else if(to_deploy_armies < fromCountry.getArmies()) {
			return true;
		}

		return false;
	}


	@Override
	public void printOrder() {
		// TODO Auto-generated method stub
		System.out.println("Advance Order " + to_deploy_armies + " armies from " + fromCountry + " to " + targetCountry);
		System.out.println("***************************************");
	}

}
