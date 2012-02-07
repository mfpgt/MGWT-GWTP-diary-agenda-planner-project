package eva.coach.timemanager.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import eva.coach.timemanager.client.mgwtTOgwtp.MgwtModule;
import eva.coach.timemanager.client.place.ClientPlaceManager;
import eva.coach.timemanager.client.place.DefaultPlace;
import eva.coach.timemanager.client.place.NameTokens;
import eva.coach.timemanager.client.presenters.MainControlsPresenter;
import eva.coach.timemanager.client.views.CatAndActCreationView;
import eva.coach.timemanager.client.views.MainControlsMobileView;
import eva.coach.timemanager.client.presenters.CatAndActCreationPresenter;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new MgwtModule(ClientPlaceManager.class));

		bindPresenter(MainControlsPresenter.class,
				MainControlsPresenter.MyView.class, MainControlsMobileView.class,
				MainControlsPresenter.MyProxy.class);
		
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.welcome);

		bindPresenter(CatAndActCreationPresenter.class,
				CatAndActCreationPresenter.MyView.class,
				CatAndActCreationView.class,
				CatAndActCreationPresenter.MyProxy.class);
	}
}
