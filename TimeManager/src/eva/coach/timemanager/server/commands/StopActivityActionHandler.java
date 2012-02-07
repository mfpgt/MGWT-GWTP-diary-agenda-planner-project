package eva.coach.timemanager.server.commands;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import eva.coach.timemanager.server.models.ActivitiesList;
import eva.coach.timemanager.server.models.Activity;
import eva.coach.timemanager.server.models.DAO;
import eva.coach.timemanager.server.models.Record;
import eva.coach.timemanager.shared.commands.StopActivity;
import eva.coach.timemanager.shared.commands.StopActivityResult;
import eva.coach.timemanager.shared.models.ActivityDTO;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

//Stops activity and add the corresponding records

public class StopActivityActionHandler implements
		ActionHandler<StopActivity, StopActivityResult> {

	@Inject
	public StopActivityActionHandler() {
	}

	@Override
	public StopActivityResult execute(StopActivity action, ExecutionContext context)
			throws ActionException {
		
		//create record
		DAO dao=new DAO();
		Record record=new Record(action.getInstant(),action.getActivity().getName(),"stop",action.getCommentary());
		dao.ofy().put(record);
		
		//update activity state and record list
		Activity activity=dao.ofy().query(Activity.class).filter("name =", action.getActivity().getName()).get();
		activity.setState(false);
		activity.addRecord(record);
		activity.setLastRecord(record.getDate());
		dao.ofy().put(activity);
				
		//return updated activity
		return new StopActivityResult(activity.getDTO());
		
	}

	@Override
	public void undo(StopActivity action, StopActivityResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<StopActivity> getActionType() {
		return StopActivity.class;
	}
}
