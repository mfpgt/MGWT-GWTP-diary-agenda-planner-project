package eva.coach.timemanager.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import eva.coach.timemanager.shared.models.ActivityDTO;
import java.util.Date;
import java.lang.String;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public class StopActivityEvent extends
		GwtEvent<StopActivityEvent.StopActivityHandler> {

	public static Type<StopActivityHandler> TYPE = new Type<StopActivityHandler>();
	private ActivityDTO activity;
	private Date instant;
	private String commentary;

	public interface StopActivityHandler extends EventHandler {
		void onStopActivity(StopActivityEvent event);
	}

	public interface StopActivityHasHandlers extends HasHandlers {
		HandlerRegistration addStopActivityHandler(StopActivityHandler handler);
	}

	public StopActivityEvent(ActivityDTO activity, Date instant, String commentary) {
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

	@Override
	protected void dispatch(StopActivityHandler handler) {
		handler.onStopActivity(this);
	}

	@Override
	public Type<StopActivityHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<StopActivityHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, ActivityDTO activity, Date instant,
			String commentary) {
		source.fireEvent(new StopActivityEvent(activity, instant, commentary));
	}
}
