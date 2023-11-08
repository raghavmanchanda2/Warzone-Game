package model;

import java.util.*;


/**
 * class that defines the characteristics of a country
 * @author Rohit
 * @author Ishaan Bajaj
 * @version build 2
 */
public class Country {
	private int d_uniqueCountryId;
	
	private String d_countryId;

	private Continent d_continent;
	private Map<Country,List<Country>> d_connectedCountries;

	private Set<Country> d_Neighbors;

	
	//-------------------------------------------
	private int d_Armies = 1;
	
	private Player d_countryOwner;
	
	//-------------------------------------------

	/**
	 * default constructor
	 */
	public Country() {
	}
	
	/**
	 * Parameterized constructor
	 * @param p_uniqueCountryId - unique int ID for country
	 * @param p_countryId - name of country
	 */
	public Country(int p_uniqueCountryId, String p_countryId) {
		super();
		this.d_uniqueCountryId = p_uniqueCountryId;
		this.d_countryId = p_countryId;
	}

	/**
	 * Parameterized constructor
	 * @param p_countryId - name of country
	 */
	public Country(String p_countryId) {
		super();
		this.d_countryId = p_countryId;
	}

	/**
	 * Parameterized constructor
	 * @param p_countryId - name of country
	 * @param p_continent - associated continent name
	 */
	public Country(String p_countryId, Continent p_continent) {
		super();
		this.d_countryId = p_countryId;
		this.d_continent = p_continent;
	}
	
	/**
	 * Parameterized constructor
	 * @param p_countryId - name of country
	 * @param p_connectedCountries - map that defines the country and a list of its neighbors
	 */
	public Country(String p_countryId, Map<Country, List<Country>> p_connectedCountries) {
		super();
		this.d_countryId = p_countryId;
		this.d_connectedCountries = p_connectedCountries;
	}

	/**
	 * getter method that returns the ID of the country
	 * @return d_uniqueCountryId - country's ID
	 */
	public int getUniqueCountryId() {
		return d_uniqueCountryId;
	}
	
	/**
	 * setter method which modifies the country's ID
	 * @param p_uniqueCountryId - updated ID value
	 */
	public void setUniqueCountryId(int p_uniqueCountryId) {
		this.d_uniqueCountryId = p_uniqueCountryId;
	}
	
	/**
	 * getter method to retrieve the map of the country's neighboring countries
	 * @return d_connectedCountries - map that defines the country and a list of its neighbors
	 */
	public Map<Country, List<Country>> getConnectedCountries() {
		return d_connectedCountries;
	}
	
	/**
	 * setter method to modify the map of the country's neighboring countries
	 * @param p_connectedCountries - updated map neighbors of country and a list of its neighbors
	 */
	public void setConnectedCountries(Map<Country, List<Country>> p_connectedCountries) {
		this.d_connectedCountries = p_connectedCountries;
	}
	
	/**
	 * getter method to retrieve the name of the country
	 * @return d_countryId - name of the country
	 */
	public String getCountryId() {
		return d_countryId;
	}
	
	/**
	 * setter method to change the country's name
	 * @param p_countryId - country's new name
	 */
	public void setCountryId(String p_countryId) {
		this.d_countryId = p_countryId;
	}
	
	/**
	 * getter method to retrieve the continent where the country is located in
	 * @return d_continent - name of the continent
	 */
	public Continent getContinent() {
		return d_continent;
	}
	
	/**
	 * setter method to change the country's continent
	 * @param p_continent - new continent name
	 */
	public void setContinent(Continent p_continent) {
		this.d_continent = p_continent;
	}


	/**
	 * getter method to get neighbors of countries in a set
	 * @return neighbors - set of neighbors
	 */

	public Set<Country> getNeighbors() {
		if (d_Neighbors == null) {
			d_Neighbors = new HashSet<>();
		}
		return d_Neighbors;
	}

	/**
	 * method to get armies assigned to that country
	 * @return d_Armies - player's armies in that country
	 */

	public int getArmies() {

		return d_Armies;
	}

	
	/**
	 * method to create list of neigbours/borders of countries
	 * @param p_Neighbors - set of neighbors to be shown as a string
	 * @return A hyphen-separated string containing the country IDs of neighbors.
	 * Returns an empty string if the set of neighbors is empty.
	 */


	/**
	 * player deploys a certain amount of armies in the country
	 * @param p_NumberOfArmies - armies value
	 */
	public void armiesDeploy(int p_NumberOfArmies){
		d_Armies += p_NumberOfArmies;
	}


	public void setArmy(int p_NumOfArmies) {
		d_Armies = p_NumOfArmies;
	}
	
	//-------------------------------------------
	public Player getCountryOwner() {
		return d_countryOwner;
	}
	
	public void setCountryOwner(Player player) {
		d_countryOwner = player;
	}
	
	public void setNeutral() {
		d_countryOwner = null;
	}
	
	public void armyUnitDefeat() {
		--d_Armies;
	}
	
	public void armiesRemove(int p_NumberOfArmies) {
		d_Armies -= p_NumberOfArmies;
	}
	//-------------------------------------------
	
}
