package eva.coach.timemanager.server.commands;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import eva.coach.timemanager.shared.commands.EliminateActivitiesLists;
import eva.coach.timemanager.shared.commands.EliminateActivitiesListsResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

//Eliminate activities lists

public class EliminateActivitiesListsActionHandler implements
		ActionHandler<EliminateActivitiesLists, EliminateActivitiesListsResult> {

	@Inject
	public EliminateActivitiesListsActionHandler() {
	}

	@Override
	public EliminateActivitiesListsResult execute(EliminateActivitiesLists action,
			ExecutionContext context) throws ActionException {
		return null;
	}

	@Override
	public void undo(EliminateActivitiesLists action,
			EliminateActivitiesListsResult result, ExecutionContext context)
			throws ActionException {
	}

	@Override
	public Class<EliminateActivitiesLists> getActionType() {
		return EliminateActivitiesLists.class;
	}
}
