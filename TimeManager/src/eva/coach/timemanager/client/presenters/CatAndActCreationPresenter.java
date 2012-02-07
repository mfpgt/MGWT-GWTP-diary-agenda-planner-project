package eva.coach.timemanager.client.presenters;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import eva.coach.timemanager.client.mgwtTOgwtp.RevealAnimatableDisplayContentEvent;
import eva.coach.timemanager.client.place.NameTokens;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class CatAndActCreationPresenter
		extends
		Presenter<CatAndActCreationPresenter.MyView, CatAndActCreationPresenter.MyProxy> {

	boolean creatingActivity=true;
	
	public interface MyView extends View {
		HasClickHandlers creation();
		HasTapHandlers goBack();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.creation)
	public interface MyProxy extends ProxyPlace<CatAndActCreationPresenter> {
	}

	private final PlaceManager placeManager;
	
	@Inject
	public CatAndActCreationPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy,final PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.placeManager=placeManager;
	}

	@Override
	protected void revealInParent() {
		//for mobile based on mgwt
		RevealAnimatableDisplayContentEvent.fire(this, this,getAnimation());
		//RevealRootLayoutContentEvent.fire(this, this);
	}

	private Animation getAnimation(){
		return Animation.POP;
	}

	@Override
	protected void onBind() {
		super.onBind();
		
		registerHandler(getView().goBack().addTapHandler(
				new TapHandler() {
					@Override
					public void onTap(TapEvent event) {
						PlaceRequest myRequest = new PlaceRequest(NameTokens.welcome);
						placeManager.revealPlace( myRequest,true);
					}
				}));
	}

	@Override
	protected void onHide() {
		super.onHide();
	}

	@Override
	protected void onReset() {
		super.onReset();
	}

	@Override
	protected void onReveal() {
		super.onReveal();
	}

	@Override
	protected void onUnbind() {
		super.onUnbind();
	}
}
