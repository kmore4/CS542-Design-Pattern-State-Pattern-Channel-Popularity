package channelpopularity.state;

import channelpopularity.context.ContextI;
import channelpopularity.util.Results;
import channelpopularity.validations.InvalidInputFormatException;
import channelpopularity.validations.InvalidInputValueException;
import channelpopularity.validations.videoAlreadyExistsException;
import channelpopularity.validations.videoDoesNotExistsException;

/**
 * @author Kasturi Tarachand More
 *
 */

/**
 * The MILDLY_POPULAR class extends AbstractState class and calls parent 
 * class's respective method
 */
public class MILDLY_POPULAR extends AbstractState{
	Results rs = new Results();
	static final StateName stname1 = StateName.MILDLY_POPULAR;
	static final String stname = stname1.toString();
	
	/**
	 * calls parent class's addVideo method
	 *
	 * @param	ctx		ContextI object
	 * @param	String	Video Name
	 * @return 	NULL
	 * @exception	videoAlreadyExistsException
	 */
	@Override
	public void addVideo(ContextI ctx, String s) throws videoAlreadyExistsException {
		super.addVideo(ctx,s);
		String s2 = stname+"__VIDEO_ADDED::"+s+"\n";
		rs.store(s2);
	}
	
	
	/**
	 * calls parent class's removeVideo method
	 *
	 * @param	ctx		ContextI object
	 * @param	String	Video Name
	 * @return 	NULL
	 * @exception	videoDoesNotExistsException
	 */
	@Override
	public void removeVideo(ContextI ctx, String s) throws videoDoesNotExistsException {
		super.removeVideo(ctx, s);
		String s2 = stname+"__VIDEO_REMOVED::"+s+"\n";
		rs.store(s2);
	}

	
	/**
	 * calls parent class's updateMetric method
	 *
	 * @param	ctx		ContextI object
	 * @param	String	Video Name
	 * @return 	NULL
	 * @exception	videoDoesNotExistsException, InvalidInputFormatException, InvalidInputValueException
	 */
	@Override
	public void updateMetric(ContextI ctx, String v, String s)
			throws videoDoesNotExistsException, InvalidInputFormatException, InvalidInputValueException {
		super.updateMetric(ctx, v, s);
		String s2 = stname+"__POPULARITY_SCORE_UPDATE::"+ctx.getTotalPopularity()+"\n";
		rs.store(s2);
	}

	
	/**
	 * calls parent class's adRequest method and decides whether ad 
	 * request will be accepted or rejected
	 *
	 * @param	ctx		ContextI object
	 * @param	String	Video Name
	 * @return 	NULL
	 * @exception	videoDoesNotExistsException, InvalidInputFormatException
	 */
	@Override
	public void adRequest(ContextI ctx, String v, String s)
			throws videoDoesNotExistsException, InvalidInputFormatException {
		super.adRequest(ctx, v, s);
		String len[] = s.split("=");
		int l = Integer.parseInt(len[1]);
		
		if(l>1 && l<=20) {
			String s2 = stname+"__AD_REQUEST::APPROVED\n";
			rs.store(s2);
		}
		else {
			String s2 = stname+"__AD_REQUEST::REJECTED\n";
			rs.store(s2);
		}
	}

	
	/**
     * Default toString, needed for debugging here.
     * @return String with values that will be used
     */
    public String toString() {
        return ("State: " + stname);
    }

}
