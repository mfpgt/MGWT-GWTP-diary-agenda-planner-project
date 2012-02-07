/**
 * 
 */
package eva.coach.timemanager.client.mgwtTOgwtp;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalytics;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalyticsImpl;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

/**
 *
 * @author Martín Pérez-Guevara
 */
public class MgwtModule extends AbstractGinModule {

  private final Class<? extends PlaceManager> placeManagerClass;

  public MgwtModule(Class<? extends PlaceManager> placeManagerClass) {
    this.placeManagerClass = placeManagerClass;
  }

  @Override
  protected void configure() {
    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
    bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
    bind(AnimatableDisplayPresenter.class).asEagerSingleton();
    bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(Singleton.class);
    bind(PlaceManager.class).to(placeManagerClass).in(Singleton.class);
  }
}