package eva.coach.timemanager.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import eva.coach.timemanager.shared.models.ActivityDTO;
import java.util.Date;
import java.lang.String;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public class StartActivityEvent extends
		GwtEvent<StartActivityEvent.StartActivityHandler> {

	public static Type<StartActivityHandler> TYPE = new Type<StartActivityHandler>();
	private ActivityDTO activity;
	private Date instant;
	private String commentary;
	private boolean exists;

	public interface StartActivityHandler extends EventHandler {
		void onStartActivity(StartActivityEvent event);
	}

	public interface StartActivityHasHandlers extends HasHandlers {
		HandlerRegistration addStartActivityHandler(StartActivityHandler handler);
	}

	public StartActivityEvent(ActivityDTO activity, Date instant, String commentary,boolean exists) {
		this.activity = activity;
		this.instant = instant;
		this.commentary = commentary;
		this.exists=exists;
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

	public boolean isExists() {
		return exists;
	}

	@Override
	protected void dispatch(StartActivityHandler handler) {
		handler.onStartActivity(this);
	}

	@Override
	public Type<StartActivityHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<StartActivityHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, ActivityDTO activity, Date instant,
			String commentary,boolean exists) {
		source.fireEvent(new StartActivityEvent(activity, instant, commentary,exists));
	}
}
