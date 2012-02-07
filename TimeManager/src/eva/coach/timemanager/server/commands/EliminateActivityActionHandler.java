package eva.coach.timemanager.server.commands;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import eva.coach.timemanager.shared.commands.EliminateActivity;
import eva.coach.timemanager.shared.commands.EliminateActivityResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

//eliminate activities from a list or from the system. Records are kept.

public class EliminateActivityActionHandler implements
		ActionHandler<EliminateActivity, EliminateActivityResult> {

	@Inject
	public EliminateActivityActionHandler() {
	}

	@Override
	public EliminateActivityResult execute(EliminateActivity action,
			ExecutionContext context) throws ActionException {
		return null;
	}

	@Override
	public void undo(EliminateActivity action, EliminateActivityResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<EliminateActivity> getActionType() {
		return EliminateActivity.class;
	}
}
