package simModel;

import java.util.ArrayList;
import java.util.List;

public class UDPs {
	SMThemePark park; // for accessing the clock

	// Constructor
	public UDPs(SMThemePark park) {
		this.park = park;
	}

	// Translate User Defined Procedures into methods
	/*-------------------------------------------------
	                       Example
	                       public int ClerkReadyToCheckOut()
	    {
	    	int num = 0;
	    	Clerk checker;
	    	while(num < model.NumClerks)
	    	{
	    		checker = model.Clerks[num];
	    		if((checker.currentstatus == Clerk.status.READYCHECKOUT)  && checker.list.size() != 0)
	    		{return num;}
	    		num +=1;
	    	}
	    	return -1;
	    }
	------------------------------------------------------------*/

	public boolean checkTrainArrived(int stnIndex) {
		// System.out.println("==UDP==checkTrainArrived==start=="); //TODO
		// delete
		Track curTrack = park.tracks.trackGroup[stnIndex];
		// at least two trains are on the track
		if (null != curTrack.trainGroup && curTrack.trainGroup.size() > 1) {
			Train t1 = curTrack.trainGroup.get(0);
			Train t2 = curTrack.trainGroup.get(1);
			if (t1.status == Constants.TRAIN_STATUS_BOARDING
					&& t2.status == Constants.TRAIN_STATUS_ARRIVED) {
				// System.out.println("return true");//TODO delete
				return true;
			}
		}
		// System.out.println("return false");//TODO delete
		return false;
	}

	// List of stations are ready for unboarding
	protected List<Integer> stationReadyForUnboarding() {
		// System.out.println("UDP==stationReadyForUnboarding==start");//TODO
		// DELETE
		List<Integer> idList = new ArrayList<Integer>();
		for (int i = 0; i < park.tracks.trackGroup.length; i++) {
			Track iTrack = park.tracks.trackGroup[i];
			if (iTrack != null && iTrack.trainGroup != null
					&& iTrack.trainGroup.size() > 0) {
				Train headTrain = iTrack.trainGroup.get(0);
				if (headTrain.status == Constants.TRAIN_STATUS_ARRIVED) {
					idList.add(Integer.valueOf(i));
				}
			}
		}
		// System.out.println("UDP==stationReadyForUnboarding==end");
		return idList;
	}
}
