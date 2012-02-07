package eva.coach.timemanager.shared.commands;

import com.gwtplatform.dispatch.shared.Result;

import eva.coach.timemanager.shared.models.ActivitiesListDTO;

public class CreateActivitiesListResult implements Result {

	private ActivitiesListDTO activitiesList;
	
	private CreateActivitiesListResult(){
		
	}
	
	public CreateActivitiesListResult(ActivitiesListDTO activitiesList) {
		this.activitiesList=activitiesList;
	}

	public ActivitiesListDTO getActivitiesList() {
		return activitiesList;
	}

}
