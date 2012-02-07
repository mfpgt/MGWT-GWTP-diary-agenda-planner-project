package eva.coach.timemanager.shared.commands;

import com.gwtplatform.dispatch.shared.Result;

import eva.coach.timemanager.shared.models.ActivitiesListDTO;
import eva.coach.timemanager.shared.models.ActivityDTO;

import java.util.ArrayList;

public class RefreshAllResult implements Result {

	private ArrayList<String> allActivities;
	private ArrayList<String> lists;
	private ArrayList<ActivitiesListDTO> activitiesLists;
	private ArrayList<ActivityDTO> activities;
	private ArrayList<ActivityDTO> currentActivities;

	@SuppressWarnings("unused")
	private RefreshAllResult() {
		// For serialization only
	}

	public RefreshAllResult(ArrayList<String> activitiesNames,ArrayList<ActivityDTO> activities ,ArrayList<String> listsNames, 
			ArrayList<ActivitiesListDTO> activitiesLists,ArrayList<ActivityDTO> currentActivities) {
		this.allActivities = activitiesNames;
		this.lists=listsNames;
		this.activitiesLists=activitiesLists;
		this.activities=activities;
		this.currentActivities=currentActivities;
	}

	public ArrayList<String> getAllActivities() {
		return allActivities;
	}

	public ArrayList<String> getLists() {
		return lists;
	}

	public ArrayList<ActivitiesListDTO> getActivitiesLists() {
		return activitiesLists;
	}

	public ArrayList<ActivityDTO> getActivities() {
		return activities;
	}

	public ArrayList<ActivityDTO> getCurrentActivities() {
		return currentActivities;
	}
	
	
}
