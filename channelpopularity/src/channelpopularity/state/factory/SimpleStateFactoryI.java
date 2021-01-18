package channelpopularity.state.factory;

import channelpopularity.state.StateI;
import channelpopularity.state.StateName;

/**
 * @author Kasturi Tarachand More
 *
 */

/**
 * The SimpleStateFactoryI Interface holds declaration of the methods
 * implemented in SimpleStateFactory Class
 */
public interface SimpleStateFactoryI {
	public StateI create(StateName name);
}
