package eva.coach.timemanager.shared.commands;

import com.gwtplatform.dispatch.shared.ActionImpl;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

import eva.coach.timemanager.shared.commands.StopActivityResult;
import eva.coach.timemanager.shared.models.ActivityDTO;
import java.util.Date;
import java.lang.String;

public class StopActivity extends UnsecuredActionImpl<StopActivityResult> {

	private ActivityDTO activity;
	private Date instant;
	private String commentary;

	@SuppressWarnings("unused")
	private StopActivity() {
		// For serialization only
	}

	public StopActivity(ActivityDTO activity, Date instant, String commentary) {
		this.activity = activity;
		this.instant = instant;
		this.commentary = commentary;
	}

	public ActivityDTO getActivity() {
		return activity;
	}

	public Date getInstant() {
		return instant;
	}

	public String getCommentary() {
		return commentary;
	}
}
