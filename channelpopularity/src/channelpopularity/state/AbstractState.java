package channelpopularity.state;

import java.util.HashMap;
import channelpopularity.context.ContextI;
import channelpopularity.validations.InvalidInputFormatException;
import channelpopularity.validations.InvalidInputValueException;
import channelpopularity.validations.videoAlreadyExistsException;
import channelpopularity.validations.videoDoesNotExistsException;

/**
 * @author Kasturi Tarachand More
 *
 */

/**
 * The AbstractState class implements StateI interface and holds the
 * common implementation of all the operations.
 */
public abstract class AbstractState implements StateI {
	String[] values = { "0","0","0","0","0" };
	HashMap<String, String[]> videosIn = new HashMap<String, String[]>();
	float totPop;
	
	
	/**
	 * decides the current state of the channel based on total 
	 * popularity of channel and set it as ContextI's current state
	 *
	 * @param	ctx		ContextI object
	 * @param	float	total popularity of channel
	 * @return 	NULL	   
	 */
	private void decideState(ContextI ctx, float totPop2) {
		// TODO Auto-generated method stub
		if(totPop2>=0 && totPop2<=1000) {
			ctx.setCurrentState(StateName.UNPOPULAR);
		}
		else if(totPop2>1000 && totPop2<=10000) {
			ctx.setCurrentState(StateName.MILDLY_POPULAR);
		}
		else if(totPop2>10000 && totPop2<=100000) {
			ctx.setCurrentState(StateName.HIGHLY_POPULAR);
		}
		else if(totPop2>100000 && totPop2<=Integer.MAX_VALUE) {
			ctx.setCurrentState(StateName.ULTRA_POPULAR);
		}
	}
	
	
	/**
	 * if video already exists then throws error else adds video to 
	 * channel, recalculates total popularity of channel & decides 
	 * current state of the channel.
	 *
	 * @param	ctx		ContextI object
	 * @param	String	Video Name
	 * @return 	NULL
	 * @exception	videoAlreadyExistsException
	 */
	public void addVideo(ContextI ctx, String s) throws videoAlreadyExistsException{
		try {
			videosIn = ctx.getVideoSet();
			
			if(videosIn.containsKey(s)) {
				throw new videoAlreadyExistsException("Video Already Exist. Cannot add " + s);
			}
			else {
				totPop = ctx.getTotalPopularity();
				totPop *= videosIn.size();
				videosIn.put(s, values);
				ctx.setVideoSet(videosIn);
				totPop /= videosIn.size();
				ctx.setTotalPopularity(totPop);
				decideState(ctx,totPop);
			}
		}
		
		catch (videoAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	/**
	 * if video does not exists then throws error else removes video 
	 * from channel, recalculates total popularity of channel & 
	 * decides current state of the channel.
	 *
	 * @param	ctx		ContextI object
	 * @param	String	Video Name
	 * @return 	NULL
	 * @exception	videoDoesNotExistsException
	 */
	public void removeVideo(ContextI ctx, String s) throws videoDoesNotExistsException {
		try {
			videosIn = ctx.getVideoSet();
			if(videosIn.containsKey(s) == false) {
				throw new videoDoesNotExistsException("Video does not Exist. Cannot remove " + s);
			}
			
			else {
				totPop = ctx.getTotalPopularity();
				String[] a = videosIn.get(s);
				totPop *= videosIn.size();
				totPop -= Float.parseFloat(a[0]);
				videosIn.remove(s);
				if(totPop <= 0)	totPop = 0;
				else totPop /= videosIn.size();
				ctx.setTotalPopularity(totPop);
				ctx.setVideoSet(videosIn);
				decideState(ctx,totPop);
			}
		} catch (videoDoesNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}		
	}
	
	
	/**
	 * if video does not exists then throws error else calculates 
	 * popularity of a video based on input provided.
	 *
	 * @param	ctx		ContextI object
	 * @param	String	Video Name
	 * @param	String	Parameters for metric calculation
	 * @return 	NULL
	 * @exception	videoDoesNotExistsException,InvalidInputFormatException
	 * InvalidInputValueException
	 */
	public void updateMetric(ContextI ctx, String v, String s) throws videoDoesNotExistsException, InvalidInputFormatException, InvalidInputValueException {
		try {
			videosIn = ctx.getVideoSet();
			int viewsChange,likesChange,dislikesChange,views,likes,dislikes;
			if(videosIn.containsKey(v) == false) {
				throw new videoDoesNotExistsException("Video does not Exist. Cannot update Metric for " + s);
			}
			else {
				String[] met = s.split(",");
				//System.out.println(s);
				if(met.length != 3) {
					throw new InvalidInputFormatException("Invalid Input Format..!!");
				}
				else {
					if(met[0].contains("=")) {
						String[] attributes = met[0].split("=");
						if(attributes[0].equals("VIEWS")){
							viewsChange = Integer.parseInt(attributes[1]);
						}
						else throw new InvalidInputFormatException("Invalid Input Format..!!");
						
						}
					else {
						throw new InvalidInputFormatException("Invalid Input Format..!!");
					}
					if(met[1].contains("=")) {
						String[] attributes = met[1].split("=");
						if(attributes[0].equals("LIKES")){
							likesChange = Integer.parseInt(attributes[1]);
						}
						else throw new InvalidInputFormatException("Invalid Input Format..!!");
						
						}
					else {
						throw new InvalidInputFormatException("Invalid Input Format..!!");
					}
					if(met[2].contains("=")) {
						String[] attributes = met[2].split("=");
						if(attributes[0].equals("DISLIKES")){
							dislikesChange = Integer.parseInt(attributes[1]);
						}
						else throw new InvalidInputFormatException("Invalid Input Format..!!");
						
						}
					else {
						throw new InvalidInputFormatException("Invalid Input Format..!!");
					}
					
					String[] a = videosIn.get(v);
					float pop = Float.parseFloat(a[0]);
					views = Integer.parseInt(a[1]);
					likes = Integer.parseInt(a[2]);
					dislikes = Integer.parseInt(a[3]);
					
					if(likes + likesChange <0 || dislikes + dislikesChange < 0 || viewsChange<0) {
						throw new InvalidInputValueException("Invalid Views/Likes/Dislikes value..!!");
					}
					
					totPop = ctx.getTotalPopularity();
					totPop *= videosIn.size();
					totPop -= pop;
					views += viewsChange;
					likes += likesChange;
					dislikes += dislikesChange;
					
					pop = views + (2 * (likes - dislikes));
					if (pop <0)	pop  = 0;
					totPop += pop;
					if(totPop > 0 ) 				totPop /= videosIn.size();
					else totPop = 0;
					ctx.setTotalPopularity(totPop);
					
					String[] setVal = { String.valueOf(pop), String.valueOf(views), String.valueOf(likes), String.valueOf(dislikes)};
					videosIn.replace(v, setVal);
					ctx.setVideoSet(videosIn);
					decideState(ctx,totPop);
				}
			}
		} catch (InvalidInputFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (videoDoesNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (InvalidInputValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (NumberFormatException e) {
			System.err.println("ERROR: Views/Likes/Dislikes value should be integer");
			System.exit(1);
		}
		
	}
	
	
	/**
	 * if video does not exists then throws error else validates
	 * length value
	 *
	 * @param	ctx		ContextI object
	 * @param	String	Video Name
	 * @param	String	length of video
	 * @return 	NULL
	 * @exception	videoDoesNotExistsException,InvalidInputFormatException
	 * 
	 */
	public void adRequest(ContextI ctx, String v, String s) throws videoDoesNotExistsException, InvalidInputFormatException {
		try {
			videosIn = ctx.getVideoSet();
			if(videosIn.containsKey(v) == false) {
				throw new videoDoesNotExistsException("Video does not Exist. Cannot process ad request for " + s);
			}
			else {
				if(s.contains("=")) {
					String len[] = s.split("=");
					int l = Integer.parseInt(len[1]);
					if(len[0].equals("LEN") == false) {
						throw new InvalidInputFormatException("Invalid Input Format..!!");
					}
					if(l<=0) {
						throw new InvalidInputFormatException("Ad length should be greater than 0..!!");
					}
				}
			}
		} catch (videoDoesNotExistsException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (InvalidInputFormatException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (NumberFormatException e) {
			System.err.println("ERROR: Len should be integer..!!");
			System.exit(1);
		}
	}
	
	
	/**
     * Default toString, needed for debugging here.
     * @return String with values that will be used
     */
    public String toString() {
        return ("Total Popularity: " + totPop);
    }
}
