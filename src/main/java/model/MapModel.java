package model;

import logger.ConsoleWriter;
import logger.Logger;
import model.player.PlayerStrategy;
import persistence.MapFileAlteration;

import java.util.*;
import java.util.stream.Collectors;


/**
 * class that defines all entities in the map (continents, countries, borders) and their operations
 * @author Rohit
 * @author Ishaan Bajaj
 * @version build 1
 *
 */
public class MapModel {
	
	private String mapName;
	private List<Continent> continents;
	private List<Country> countries;


	private Map<Continent,List<Country>> continentCountries;
	private Map<Country,List<Country>> borders;

	private static MapModel d_MapModel;

	private Logger d_logger;
	private ConsoleWriter d_consoleWriter;

	private HashMap<String, Continent> d_Continents = new HashMap<>();
	HashMap<String, Country> countryHashMap = new HashMap<>();

	private HashMap<String, Player> d_Players = new HashMap<>();

	private Player d_CurrentPlayer;
	private Boolean d_GameLoaded = false;

	public MapModel() {
		d_logger = new Logger();
		d_consoleWriter = new ConsoleWriter();
		d_logger.addObserver(d_consoleWriter);
	}

	/**
	 * If an instance for MapModel has not been created, create it and return it
	 * @return d_MapModel
	 */
	public static MapModel getInstance() {
		if (Objects.isNull(d_MapModel)) {
			d_MapModel = new MapModel();
		}
		return d_MapModel;
	}
	
	/**
	 * getter method that returns the list of all countries on the map
	 * @return countries
	 */
	public List<Country> getCountries() {
		return countries;
	}
	
	/**
	 * setter method to modify the countries on the map
	 * @param countries
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	
	/**
	 * getter method for the name of the map
	 * @return mapName
	 */
	public String getMapName() {
		return mapName;
	}
	
	/**
	 * setter method to modify the map's name
	 * @param mapName
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	/**
	 * getter method that returns the list of all continents
	 * @return continents
	 */
	public List<Continent> getContinents() {
		return continents;
	}
	
	/**
	 * setter method that modifies the list of continents on the map
	 * @param continents
	 */
	public void setContinents(List<Continent> continents) {
		this.continents = continents;
	}
	
	/**
	 * getter method that returns a map of all continents and their its associated countries
	 * @return continentCountries
	 */
	public Map<Continent, List<Country>> getContinentCountries() {
		return continentCountries;
	}
	
	/**
	 * setter method to modify the map's continents and their associated countries
	 * @param continentCountries
	 */
	public void setContinentCountries(Map<Continent, List<Country>> continentCountries) {
		this.continentCountries = continentCountries;
	}
	
	/**
	 * getter method that returns the map of each country  a list of countries that borders them
	 * @return borders
	 */
	public Map<Country, List<Country>> getBorders() {
		return borders;
	}
	
	/**
	 * setter method to modify the countries' borders
	 * @param borders
	 */
	public void setBorders(Map<Country, List<Country>> borders) {
		this.borders = borders;
	}
	
	/**
	 * method for adding a new continent on the map.
	 * If the map does not contain any continents, initialize map continents with an array list
	 * If the map does not contain a mapping of continents with associated countries, initialize the continent countries with a HashMap
	 * @param continent
	 */
	public void addContinent(Continent continent) {
		
		if(this.continents == null) {
			this.continents = new ArrayList<Continent>();
		}
		if(this.continentCountries == null) {
			this.continentCountries = new HashMap<Continent, List<Country>>();
		}
		this.continents.add(continent);
		this.continentCountries.put(continent, new ArrayList<Country>());
	}
	
	/**
	 * method to input a new country into its associated continent
	 * If that particular continent does not exist in the mapping of continentCountries, nothing happens.
	 * If it does, add that country into the map (continentCountries)
	 * If the map does not have any borders set, initialize the border with a linked hash-map and add that country
	 * 
	 * @param continent
	 * @param country
	 */
	public void addContinentCountries(Continent continent, Country country) {
		if(continent != null) {
			country.setContinent(continent);
			if (this.continentCountries.containsKey(continent)) {
				System.out.println(country.getCountryId() + "inside...............");
				this.continentCountries.get(continent).add(country);	
			}
		}
				
		if(this.borders == null) {
			this.borders = new LinkedHashMap<Country, List<Country>>();
		}
		this.borders.put(country, new ArrayList<Country>());
		if(this.countries == null) {
			this.countries = new ArrayList<Country>();
		}
		this.countries.add(country);
		this.borders.put(country, new ArrayList<Country>());
		
		System.out.println(country.getCountryId() + "outside...............");
		
	}
	
	/**
	 * method to add a neighboring country to the map of borders using mainCountry as search key 
	 * @param mainCountry
	 * @param neighbourCountry
	 */
	public void addBorders(Country mainCountry,Country neighbourCountry) {
		
		
		if(this.borders.containsKey(mainCountry)) {
			this.borders.get(mainCountry).add(neighbourCountry);
			mainCountry.getNeighbors().add(neighbourCountry);
		}else {
			this.borders.put(mainCountry,  new ArrayList<Country>());
			System.out.println("ROHIT");
		}
	}

	/**
	 * method to validate the map after loading/showmap
	 * @return response to validateMap
	 */

	public ResponseWrapper validateMap() {

		if(getContinents() == null  ) {
			return new ResponseWrapper(404, "In map there are no continents");
		}
		if(getCountries() == null) {
			return new ResponseWrapper(404, "In map there are no countries");
		}
		Set<Continent> l_continents = new HashSet<Continent>(getContinents());
		Set<Country> l_countries = new HashSet<Country>(getCountries());

		for(Continent cont : l_continents)
		{
			try {
				Integer.parseInt(cont.getContientValue());

			} catch (Exception exc) {
				return new ResponseWrapper(404, " COntinent Value is not good");
			}

		}

		if ("".equals(getMapName()) || getMapName() == null || getContinents().size() == 0
				|| getCountries().isEmpty() || getContinentCountries().size() == 0
				|| getBorders().isEmpty()) {

			return new ResponseWrapper(404, "Map is not created Properly");

		} else if (l_continents.size() != getContinents().size()
				|| l_countries.size() != getCountries().size()) {
			return new ResponseWrapper(404, "Duplicate Continent or Country Found in map");

		} else if (l_countries.size() < 2) {
			return new ResponseWrapper(404, " Countries Should be Atleast 2 in map ");

		}



		Boolean l_countryContinentNotExists = getCountries().stream()
				.anyMatch((country) -> country.getContinent() == null
						|| country.getContinent().getContientValue() == null
						|| "".equals(country.getContinent().getContinentId())
						|| "".equals(country.getContinent().getContientValue()));

		if (Boolean.TRUE.equals(l_countryContinentNotExists)) {
			return new ResponseWrapper(404, "Country's Continent Data is missing");
		}

		Boolean l_countryContinentExistsInContinentsList=false;
		for(Country count : getCountries())
		{
			for(Continent conti : l_continents)
			{
				if(count.getContinent().getContinentId().contains(conti.getContinentId()))
				{
					l_countryContinentExistsInContinentsList=true;
					break;
				}
			}
			if(Boolean.FALSE.equals(l_countryContinentExistsInContinentsList))
			{
				break;
			}

		}

		if (Boolean.FALSE.equals(l_countryContinentExistsInContinentsList)) {
			return new ResponseWrapper(404, " Country's Continent not available in the list ");

		}

//		for(Map.Entry<Country, List<Country>> mapEntry : d_mapModel.getBorders().entrySet()) {
//			System.out.println(mapEntry.getKey().getCountryId() + " " + mapEntry.getKey().getUniqueCountryId());
//		}

		Boolean l_countryBorderRelevantData=false;
		for(Map.Entry<Country, List<Country>> mapEntry : getBorders().entrySet()) {
			boolean isCountryExitInList = false;
			if(mapEntry.getKey() != null) {
				for(Country country : l_countries) {
					if(country.getCountryId().equals(mapEntry.getKey().getCountryId())) {
						isCountryExitInList = true;
					}
				}
			}

			if (! isCountryExitInList) {
				l_countryBorderRelevantData = true;
				break ;
			}


			for(Country neighbourCountries : mapEntry.getValue()) {
				if(neighbourCountries != null) {
					for(Country country : l_countries) {
						if(country.getCountryId().equals(neighbourCountries.getCountryId())) {
							isCountryExitInList = true;
						}
					}

				}else {
					isCountryExitInList = true;

				}

				if (! isCountryExitInList) {
					l_countryBorderRelevantData = true;
					break ;
				}


			}
		}


		if (Boolean.TRUE.equals(l_countryBorderRelevantData)) {
			return new ResponseWrapper(404,
					" Border Data for Countries is not consistent with Countries that are added ");

		}


		Boolean l_countryBorderNotExists = getBorders().entrySet().stream()
				.anyMatch(borderMap -> borderMap.getValue().size() == 0) ? true : false;

		if (Boolean.TRUE.equals(l_countryBorderNotExists)) {
			return new ResponseWrapper(404, " Countries Border Missing ");
		}

		return new ResponseWrapper(200, " VALIDATION SUCCESSFUL ");
	}


	/**
	 * getter method to get the player which has been added to the game
	 * @param p_Id
	 * @return
	 */

	public Player getPlayer(String p_Id) {
		return d_Players.get(p_Id);
	}

	/**
	 * getter method to get all the players inside the hashmap
	 * @return players hashmap
	 */

	public HashMap<String, Player> getPlayers() {
		return d_Players;
	}


	/**
	 * method to put countries inside the list into hashmap with unique id - name
	 * @param p_Id country name
	 * @return countries hashmap
	 */
	public Country getCountry(String p_Id) {

		for (Country country : countries) {
			String uniqueId = country.getCountryId();
			countryHashMap.put(uniqueId, country);
		}
		return countryHashMap.get(p_Id);
	}

	/**
	 * method to get the list of countries inside a map as a string
	 * @param countriesList list of countries
	 * @return string
	 */
	public String getCountriesList(List<Country> countriesList) {
		String l_countList = "";
		for (Country country : countriesList) {
			l_countList += country.getCountryId() + "-";
		}
		return l_countList.length() > 0 ? l_countList.substring(0, l_countList.length() - 1) : "";
	}

	/**
	 * method to display the map and it's contents
	 * @return
	 */
	public ResponseWrapper showMap () {

		ResponseWrapper l_resp = validateMap();
		if(l_resp.getStatusValue()==404)
		{
			System.out.format("\n Map cannot be displayed as Validation Failed \n");
			return l_resp;
		}

		d_logger.setLogMessage(" Continents: ");
		d_logger.setLogMessage("********************");
		d_logger.setLogMessage("  Continent's Name  ");
		d_logger.setLogMessage("********************");

		getContinents().stream().forEach((continent) -> {
			String l_table = "- %-20s%n";
			System.out.format(l_table, continent.getContinentId());
		});

		d_logger.setLogMessage("********************");


		/////////////
		d_logger.setLogMessage("\nCountries:");

		System.out.format("****************************************************************************************************%n");
		System.out.format("        Country   !     Continent    !     Neighbours                                  %n");
		System.out.format("****************************************************************************************************%n");
		for (Map.Entry<Country, List<Country>> entry : getBorders().entrySet()) {
			String l_tablePattern = "- %-25s - %-20s- %-70s %n";
			System.out.format(l_tablePattern, entry.getKey().getCountryId(), entry.getKey().getContinent().getContinentId(),
					getCountriesList(entry.getValue()));
		}

		System.out.format("****************************************************************************************************%n");

		HashMap<String, Player> l_Players = d_MapModel.getPlayers();
		d_logger.setLogMessage("\nPlayers : ");
		if (l_Players != null) {
			l_Players.forEach((key, value) -> d_logger.setLogMessage(key));
			d_logger.setLogMessage("");
		}

		// Showing the Ownership of the players
		d_logger.setLogMessage("Continents allotted to players: ");


		System.out.format("**************************************************%n");
		System.out.format("     Players     !     Allotted Continents       %n");
		System.out.format("**************************************************%n");

		String l_Table1 = "- %-15s- %-30s- %n";

		for (Player l_Player : d_MapModel.getPlayers().values()) {
			System.out.format(l_Table1, l_Player.getName(),
					l_Player.createACaptureList(l_Player.getCapturedCountries()),
					l_Player.getReinforcementArmies());
		}

		System.out.format("**************************************************%n");
		return new ResponseWrapper(200," Show Map Done Successfully");


	}

	/**
	 * method to add player in the game
	 * @param p_PlayerName name of the player - string
	 * @throws Exception if any error occurs
	 */

	public void addPlayer(String p_PlayerName) throws Exception {
		if (this.getPlayers().containsKey(p_PlayerName)) {
			throw new Exception(p_PlayerName  + "exists already.");
		}
		Player l_Player = new Player(PlayerStrategy.getStrategy("human"));
		l_Player.setName(p_PlayerName);
		this.getPlayers().put(p_PlayerName, l_Player);
		d_logger.setLogMessage(p_PlayerName + " added successfully");
	}

	/**
	 * method to remove player with the command
	 * @param p_PlayerName name of the player
	 * @throws Exception if any error occurs
	 */
	public void removePlayer(String p_PlayerName) throws Exception {
		Player l_Player = this.getPlayer(p_PlayerName);
		if (Objects.isNull(l_Player)) {
			throw new Exception("No player with name: " + p_PlayerName);
		}
		this.getPlayers().remove(l_Player.getName());
		d_logger.setLogMessage(p_PlayerName + " removed successfully");
	}

	/**
	 * method to assign countries in the map to different players
	 */
	public void allot() {
		int l_Index = 0;
		List<Player> l_Players = d_MapModel.getPlayers().values().stream().collect(Collectors.toList());

		List<Country> l_ListOfCountries =
				d_MapModel.getCountries();

		Collections.shuffle(l_ListOfCountries);
		for (Country l_Country : l_ListOfCountries) {
			Player l_Player = l_Players.get(l_Index);
			l_Player.getCapturedCountries().add(l_Country);
			l_Country.setPlayer(l_Player);
			d_logger.setLogMessage(l_Country.getCountryId() + " Allotted to " + l_Player.getName());

			if (l_Index < d_MapModel.getPlayers().size() - 1) {
				l_Index++;
			} else {
				l_Index = 0;
			}
		}
	}


	/**
	 * getter method to get player with current turn
	 * @return current player
	 */
	public Player getD_CurrentPlayer() {
		return d_CurrentPlayer;
	}

	/**
	 * Set the current Player
	 *
	 * @param d_CurrentPlayer player
	 */
	public void setD_CurrentPlayer(Player d_CurrentPlayer) {
		this.d_CurrentPlayer = d_CurrentPlayer;
	}

	public Boolean getD_GameLoaded() {
		return d_GameLoaded;
	}

	public void setD_GameLoaded(Boolean d_GameLoaded) {
		this.d_GameLoaded = d_GameLoaded;
	}

	/**
	 * method to clear the map contents in the Map Model
	 */
	public void clearMap() {

		MapModel.getInstance().getPlayers().clear();
		
		if( MapModel.getInstance().getContinents() != null) {
			MapModel.getInstance().getContinents().clear();
		}
		if( MapModel.getInstance().getCountries() != null) {
			MapModel.getInstance().getCountries().clear();
		}
		
		if( MapModel.getInstance().getContinentCountries() != null) {
			MapModel.getInstance().getContinentCountries().clear();
		}
		
		if( MapModel.getInstance().getBorders() != null) {
			MapModel.getInstance().getBorders().clear();
		}
		
		
		
		
	}
}
