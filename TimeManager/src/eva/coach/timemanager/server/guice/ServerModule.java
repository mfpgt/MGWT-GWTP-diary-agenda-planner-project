package eva.coach.timemanager.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;
import eva.coach.timemanager.shared.commands.RefreshAll;
import eva.coach.timemanager.server.commands.RefreshAllActionHandler;
import eva.coach.timemanager.shared.commands.StartActivity;
import eva.coach.timemanager.server.commands.StartActivityActionHandler;
import eva.coach.timemanager.shared.commands.StopActivity;
import eva.coach.timemanager.server.commands.StopActivityActionHandler;
import eva.coach.timemanager.shared.commands.CreateActivity;
import eva.coach.timemanager.server.commands.CreateActivityActionHandler;
import eva.coach.timemanager.shared.commands.CreateActivitiesList;
import eva.coach.timemanager.server.commands.CreateActivitiesListActionHandler;
import eva.coach.timemanager.shared.commands.EliminateActivity;
import eva.coach.timemanager.server.commands.EliminateActivityActionHandler;
import eva.coach.timemanager.shared.commands.EliminateActivitiesLists;
import eva.coach.timemanager.server.commands.EliminateActivitiesListsActionHandler;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {

		bindHandler(RefreshAll.class, RefreshAllActionHandler.class);

		bindHandler(StartActivity.class, StartActivityActionHandler.class);

		bindHandler(StopActivity.class, StopActivityActionHandler.class);

		bindHandler(CreateActivity.class, CreateActivityActionHandler.class);

		bindHandler(CreateActivitiesList.class,
				CreateActivitiesListActionHandler.class);

		bindHandler(EliminateActivity.class,
				EliminateActivityActionHandler.class);

		bindHandler(EliminateActivitiesLists.class,
				EliminateActivitiesListsActionHandler.class);
	}
}
