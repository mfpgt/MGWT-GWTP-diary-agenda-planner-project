package eva.coach.timemanager.shared.commands;

import com.gwtplatform.dispatch.shared.Result;
import eva.coach.timemanager.shared.models.ActivityDTO;

public class StartActivityResult implements Result {

	private ActivityDTO activity;

	@SuppressWarnings("unused")
	private StartActivityResult() {
		// For serialization only
	}

	public StartActivityResult(ActivityDTO activity) {
		this.activity = activity;
	}

	public ActivityDTO getActivity() {
		return activity;
	}
	
}
