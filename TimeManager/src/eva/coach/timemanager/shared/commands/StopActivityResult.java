package eva.coach.timemanager.shared.commands;

import com.gwtplatform.dispatch.shared.Result;
import eva.coach.timemanager.shared.models.ActivityDTO;

public class StopActivityResult implements Result {

	private ActivityDTO activity;

	@SuppressWarnings("unused")
	private StopActivityResult() {
		// For serialization only
	}

	public StopActivityResult(ActivityDTO activity) {
		this.activity = activity;
	}

	public ActivityDTO getActivity() {
		return activity;
	}
}
