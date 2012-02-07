package eva.coach.timemanager.shared.commands;

import java.util.ArrayList;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import eva.coach.timemanager.shared.commands.CreateActivitiesListResult;

public class CreateActivitiesList extends
		UnsecuredActionImpl<CreateActivitiesListResult> {

	private String name;
	private ArrayList<Long> activitiesId=new ArrayList<Long>();
	
	private CreateActivitiesList() {
	}
	
	public CreateActivitiesList(String name,ArrayList<Long> activitiesId){
		this.name=name;
		this.activitiesId=activitiesId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Long> getActivitiesId() {
		return activitiesId;
	}

	public void setActivitiesId(ArrayList<Long> activitiesId) {
		this.activitiesId = activitiesId;
	}
	
	
	
}
