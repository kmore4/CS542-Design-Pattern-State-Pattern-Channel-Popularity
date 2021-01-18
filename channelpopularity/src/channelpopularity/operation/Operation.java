package channelpopularity.operation;

/**
 * Operations that can be performed.
 */
public enum Operation {
	
	/**
     * adding video to channel
     */
	ADD_VIDEO,
	
	/**
     * removing video from channel
     */
	REMOVE_VIDEO,
	
	/**
     * updating metrics for already existing video on channel
     */
	METRICS__,
	
	/**
     * ad request for channel
     */
	AD_REQUEST__
}

