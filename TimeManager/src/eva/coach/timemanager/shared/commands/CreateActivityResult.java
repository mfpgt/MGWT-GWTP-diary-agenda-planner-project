package eva.coach.timemanager.shared.commands;

import com.gwtplatform.dispatch.shared.Result;

import eva.coach.timemanager.shared.models.ActivityDTO;

public class CreateActivityResult implements Result {

	ActivityDTO activity;
	
	private CreateActivityResult(){
		
	}
	
	public CreateActivityResult(ActivityDTO activity) {
		this.activity=activity;
	}

	public ActivityDTO getActivity() {
		return activity;
	}
	
	
}
