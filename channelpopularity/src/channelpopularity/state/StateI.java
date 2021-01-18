package channelpopularity.state;

import channelpopularity.context.ContextI;
import channelpopularity.validations.InvalidInputFormatException;
import channelpopularity.validations.InvalidInputValueException;
import channelpopularity.validations.videoAlreadyExistsException;
import channelpopularity.validations.videoDoesNotExistsException;

/** @author Kasturi Tarachand More
 *
 */

/**
 * The StateI Interface holds declaration of the methods
 * implemented in AbstractState Class
 */
public interface StateI {

	void addVideo(ContextI channelContext, String s) throws videoAlreadyExistsException;

	void removeVideo(ContextI channelContext, String s) throws videoDoesNotExistsException;

	void updateMetric(ContextI channelContext, String v, String s) throws videoDoesNotExistsException, InvalidInputFormatException, InvalidInputValueException;

	void adRequest(ContextI channelContext, String v, String s) throws videoDoesNotExistsException, InvalidInputFormatException;

}
