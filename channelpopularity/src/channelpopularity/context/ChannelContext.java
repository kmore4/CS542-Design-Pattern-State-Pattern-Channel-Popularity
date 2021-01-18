package channelpopularity.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import channelpopularity.context.ContextI;
import channelpopularity.state.StateI;
import channelpopularity.state.StateName;
import channelpopularity.state.factory.SimpleStateFactoryI;
import channelpopularity.validations.InvalidInputFormatException;
import channelpopularity.validations.InvalidInputValueException;
import channelpopularity.validations.videoAlreadyExistsException;
import channelpopularity.validations.videoDoesNotExistsException;

/**
 * @author Kasturi Tarachand More
 *
 */

/**
 * The ChannelContext class responsible for holding current state 
 * and total popularity.
 */
public class ChannelContext implements ContextI{
	
	HashMap<String, String[]> videos = new HashMap<String, String[]>(); 
	
	float totalPopularity = 0;
	
	private StateI curState;
    private Map<StateName, StateI> availableStates = new HashMap<>();
    
    
    /**
     * Constructor to create avalaibleStates hashmap using given 
     * state names declared in enum and all the names in List.
     * 
     * @param stateFactoryIn SimpleStateFactoryI stateFactoryInterface Object.
     * @param stateNames     List<StateName>     List of all state names.
     * 
     */
    public ChannelContext(SimpleStateFactoryI stateFactoryIn, List<StateName> stateNames) {
	
    	for(StateName name: stateNames) {
    		availableStates.put(name, stateFactoryIn.create(name));
    	}
  		
    	this.curState = stateFactoryIn.create(StateName.UNPOPULAR);
    }

    
    /**
	 * Sets current state
	 *
	 * @param  StateName  StateName to be set
	 * @return NULL	   
	 */
    public void setCurrentState(StateName nextState) {
    	if (availableStates.containsKey(nextState)) {
    		this.curState = availableStates.get(nextState);
    	}		
    } 
   
    
    /**
	 * get Available States HashMap
	 *
	 * @param  NULL  
	 * @return HashMap	Map of Statenames and State
	 */
    public Map getAvailableStates() {
    	return availableStates;	   
    }
   
    
    /**
	 * get current State
	 *
	 * @param  NULL  
	 * @return StateI	Current state
	 */
    public StateI getCurrentState() {
    	return this.curState; 
    }
   
    
    /**
	 * set hashmap of videos present on channel and all its details
	 * individual video popularity, views, likes and dislikes
	 *
	 * @param  HashMap   hashmap of videos
	 * @return NULL
	 */
    public void setVideoSet(HashMap<String, String[]> videoIn) {
    	videos = videoIn;
    }
   
    
    /**
	 * get hashmap of videos present on channel and all its details
	 * individual video popularity, views, likes and dislikes
	 *
	 * @param  NULL
	 * @return HashMap   hashmap of videos
	 */
    public HashMap<String, String[]> getVideoSet() {
    	return videos;
    }
   
    
    /**
	 * set total popularity of channel
	 *
	 * @param	float	popularity value to be set
	 * @return	NULL
	 */
    public void setTotalPopularity(float num) {
    	totalPopularity = num;
    }
   
    /**
	 * get total popularity of channel
	 *
	 * @param	NULL	
	 * @return	float	total popularity of channel
	 */
    public float getTotalPopularity() {
    	return totalPopularity;
    }
   
    
    /**
	 * Call addVideo method of corresponding state child class
	 *
	 * @param	NULL	
	 * @return	NULL
	 * @throws	videoAlreadyExistsException
	 */
    public void addVideo(String s) throws videoAlreadyExistsException {
    	curState.addVideo(this,s);
    }

    
    /**
	 * Call removeVideo method of corresponding state child class
	 *
	 * @param	NULL	
	 * @return	NULL
	 * @throws	videoDoesNotExistsException
	 */
    public void removeVideo(String s) throws videoDoesNotExistsException {
		curState.removeVideo(this,s);
    }

    
    /**
	 * Call updateMetric method of corresponding state child class
	 *
	 * @param	NULL	
	 * @return	NULL
	 * @throws	videoDoesNotExistsException, InvalidInputFormatException,
	 * InvalidInputValueException
	 */
    public void updateMetric(String v, String s) throws videoDoesNotExistsException, InvalidInputFormatException, InvalidInputValueException{
    	curState.updateMetric(this,v,s);
    }

    
    /**
	 * Call adRequest method of corresponding state child class
	 *
	 * @param	NULL	
	 * @return	NULL
	 * @throws	videoDoesNotExistsException, InvalidInputFormatException,
	 * 
	 */
    public void adRequest(String v, String s) throws videoDoesNotExistsException, InvalidInputFormatException {
    	curState.adRequest(this,v,s);
    }
    
    
    /**
     * Default toString, needed for debugging here.
     * @return String with values that will be used
     */
    public String toString() {
        return ("Total Popularity: " + totalPopularity);
    }
}
