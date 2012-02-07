package eva.coach.timemanager.shared.commands;

import com.gwtplatform.dispatch.shared.ActionImpl;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

import eva.coach.timemanager.shared.commands.StartActivityResult;
import java.util.Date;
import java.lang.String;
import eva.coach.timemanager.shared.models.ActivityDTO;
import java.lang.Boolean;

public class StartActivity extends UnsecuredActionImpl<StartActivityResult> {

	private Date instant;
	private String commentary;
	private ActivityDTO activity;

	@SuppressWarnings("unused")
	private StartActivity() {
		// For serialization only
	}

	public StartActivity(Date instant, String commentary, ActivityDTO activity) {
		this.instant = instant;
		this.commentary = commentary;
		this.activity = activity;
	}

	public Date getInstant() {
		return instant;
	}

	public String getCommentary() {
		return commentary;
	}

	public ActivityDTO getActivity() {
		return activity;
	}
	
}
