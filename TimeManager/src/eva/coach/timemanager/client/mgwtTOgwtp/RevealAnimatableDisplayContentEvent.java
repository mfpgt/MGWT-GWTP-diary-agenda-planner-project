package eva.coach.timemanager.client.mgwtTOgwtp;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.gwtplatform.mvp.client.Presenter;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.googlecode.mgwt.mvp.client.Animation;

public class RevealAnimatableDisplayContentEvent
		extends
		GwtEvent<RevealAnimatableDisplayContentEvent.RevealAnimatableDisplayContentHandler> {

	public static Type<RevealAnimatableDisplayContentHandler> TYPE = new Type<RevealAnimatableDisplayContentHandler>();
	private Presenter<?,?> content;
	private Animation animation;

	public interface RevealAnimatableDisplayContentHandler extends EventHandler {
		void onRevealAnimatableDisplayContent(
				RevealAnimatableDisplayContentEvent event);
	}

	public interface RevealAnimatableDisplayContentHasHandlers extends HasHandlers {
		HandlerRegistration addRevealAnimatableDisplayContentHandler(
				RevealAnimatableDisplayContentHandler handler);
	}

	public RevealAnimatableDisplayContentEvent(Presenter<?, ?> content,Animation animation) {
		this.content = content;
		this.animation=animation;
	}

	public Presenter<?,?> getContent() {
		return content;
	}
	
	public Animation getAnimation(){
		return animation;
	}

	@Override
	protected void dispatch(RevealAnimatableDisplayContentHandler handler) {
		handler.onRevealAnimatableDisplayContent(this);
	}

	@Override
	public Type<RevealAnimatableDisplayContentHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<RevealAnimatableDisplayContentHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, Presenter<?, ?> content,Animation animation) {
		source.fireEvent(new RevealAnimatableDisplayContentEvent(content,animation));
	}
}
