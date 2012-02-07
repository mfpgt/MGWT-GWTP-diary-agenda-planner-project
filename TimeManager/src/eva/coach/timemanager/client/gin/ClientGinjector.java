package eva.coach.timemanager.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import eva.coach.timemanager.client.gin.ClientModule;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.google.gwt.inject.client.AsyncProvider;
import eva.coach.timemanager.client.presenters.MainControlsPresenter;
import eva.coach.timemanager.client.presenters.CatAndActCreationPresenter;

@GinModules({ DispatchAsyncModule.class, ClientModule.class })
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	AsyncProvider<MainControlsPresenter> getMainControlsPresenter();

	AsyncProvider<CatAndActCreationPresenter> getCatAndActCreationPresenter();
}
