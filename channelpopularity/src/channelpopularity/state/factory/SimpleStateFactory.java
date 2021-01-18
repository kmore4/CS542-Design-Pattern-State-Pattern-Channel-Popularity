package channelpopularity.state.factory;

import channelpopularity.state.HIGHLY_POPULAR;
import channelpopularity.state.MILDLY_POPULAR;
import channelpopularity.state.StateI;
import channelpopularity.state.StateName;
import channelpopularity.state.ULTRA_POPULAR;
import channelpopularity.state.UNPOPULAR;

/**
 * @author Kasturi Tarachand More
 *
 */

/**
 * The SimpleStateFactory class which implements SimpleStateFactoryI 
 * interface and returns appropriate state object.
 */
public class SimpleStateFactory implements SimpleStateFactoryI {
	
	/**
	 * returns object of given state's class
	 *
	 * @param  StateName  name of the state
	 * @return given state's class object	   
	 */
	public StateI create(StateName name) {
		if(name.equals(StateName.UNPOPULAR)){
	         return new UNPOPULAR();
	    }
		if(name.equals(StateName.MILDLY_POPULAR)){
	         return new MILDLY_POPULAR();
	    }
		if(name.equals(StateName.HIGHLY_POPULAR)){
	         return new HIGHLY_POPULAR();
	    }
		if(name.equals(StateName.ULTRA_POPULAR)){
	         return new ULTRA_POPULAR();
	    }
		
		return null;
	}
	
	/**
     * Default toString, needed for debugging here.
     * @return null
     */
    public String toString() {
        return null;
    }
}
