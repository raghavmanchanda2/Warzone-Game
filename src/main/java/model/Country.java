package model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * class that defines the characteristics of a country
 * @author Rohit
 * @author Ishaan Bajaj
 * @version build 1
 */
public class Country {
	private int d_uniqueCountryId;
	
	private String d_countryId;
	private String d_countryName;
	private String d_Continent;
	private Player d_Player;
	private Continent d_continent;
	private Map<Country,List<Country>> d_connectedCountries;

	private Set<Country> d_Neighbors;

	private int d_Armies;

	/**
	 * default constructor
	 */
	public Country() {
	}
	
	
	public Country(int p_uniqueCountryId, String p_countryId) {
		super();
		this.d_uniqueCountryId = p_uniqueCountryId;
		this.d_countryId = p_countryId;
	}

	public Country(String p_countryId) {
		super();
		this.d_countryId = p_countryId;
	}

	public Country(String p_countryId, Continent p_continent) {
		super();
		this.d_countryId = p_countryId;
		this.d_continent = p_continent;
	}
	
	public Country(String p_countryId, Map<Country, List<Country>> p_connectedCountries) {
		super();
		this.d_countryId = p_countryId;
		this.d_connectedCountries = p_connectedCountries;
	}

	/**
	 * getter method that returns the ID of the country
	 * @return d_uniqueCountryId country's ID
	 */
	public int getUniqueCountryId() {
		return d_uniqueCountryId;
	}
	
	/**
	 * setter method which modifies the country's ID
	 * @param p_uniqueCountryId value that will modify the ID
	 */
	public void setUniqueCountryId(int p_uniqueCountryId) {
		this.d_uniqueCountryId = p_uniqueCountryId;
	}
	
	/**
	 * getter method to retrieve the map of the country's neighboring countries
	 * @return d_connectedCountries 
	 */
	public Map<Country, List<Country>> getConnectedCountries() {
		return d_connectedCountries;
	}
	
	/**
	 * setter method to modify the map of the country's neighboring countrie
	 * @param p_connectedCountries neighbors of a country
	 */
	public void setConnectedCountries(Map<Country, List<Country>> p_connectedCountries) {
		this.d_connectedCountries = p_connectedCountries;
	}
	
	/**
	 * getter method to retrieve the name of the country
	 * @return d_countryId name of the country
	 */
	public String getCountryId() {
		return d_countryId;
	}
	
	/**
	 * setter method to change the country's name
	 * @param p_countryId country's new name
	 */
	public void setCountryId(String p_countryId) {
		this.d_countryId = p_countryId;
	}
	
	/**
	 * getter method to retrieve the continent where the country is located in
	 * @return d_continent name of the continent
	 */
	public Continent getContinent() {
		return d_continent;
	}
	
	/**
	 * setter method to change the country's continent
	 * @param p_continent new continent name
	 */
	public void setContinent(Continent p_continent) {
		this.d_continent = p_continent;
	}


	/**
	 * getter method to get neighbors of countries in a set
	 * @return neighbors set
	 */

	public Set<Country> getNeighbors() {
		if (d_Neighbors == null) {
			d_Neighbors = new HashSet<>();
		}
		return d_Neighbors;
	}

	/**
	 * method to get armies
	 * @return armies
	 */

	public int getArmies() {

		return d_Armies;
	}

	/**
	 * getter method to get player object
	 * @return player
	 */
	public Player getPlayer() {
		return d_Player;
	}

	/**
	 * setter method to get player object
	 */
	public void setPlayer(Player p_Player) {
		this.d_Player = p_Player;
	}

	/**
	 * method to create list of neigbours/borders of countries
	 * @param p_Neighbors
	 * @return
	 */
	public String createNeighboursList(Set<Country> p_Neighbors) {
		String l_result = "";
		for (Country l_Neighbor : p_Neighbors) {
			l_result += l_Neighbor.getCountryId() + "-";
		}
		return l_result.length() > 0 ? l_result.substring(0, l_result.length() - 1) : "";
	}

	/**
	 * method to deploy armies and calculate
	 * @param p_NumberOfArmies
	 */
	public void armiesDeploy(int p_NumberOfArmies){
		d_Armies += p_NumberOfArmies;
	}
	
	
}