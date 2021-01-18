package channelpopularity.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * The ContextI Interface holds declaration of all the methods
 * implemented in ChannelContext Class
 */
public interface ContextI {
	public void setCurrentState(StateName nextState);
	public Map getAvailableStates();
	public StateI getCurrentState();
	public void setVideoSet(HashMap<String, String[]> videoIn);
	public HashMap<String, String[]> getVideoSet();
	public void setTotalPopularity(float num);
	public float getTotalPopularity();
	public void addVideo(String s) throws videoAlreadyExistsException;
	public void removeVideo(String s) throws videoDoesNotExistsException ;
	public void updateMetric(String v, String s) throws videoDoesNotExistsException, InvalidInputFormatException, InvalidInputValueException;
	public void adRequest(String v, String s) throws videoDoesNotExistsException, InvalidInputFormatException ;
	
}
