package eva.coach.timemanager.server.commands;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import eva.coach.timemanager.server.models.ActivitiesList;
import eva.coach.timemanager.server.models.Activity;
import eva.coach.timemanager.server.models.DAO;
import eva.coach.timemanager.server.models.Record;
import eva.coach.timemanager.shared.commands.StartActivity;
import eva.coach.timemanager.shared.commands.StartActivityResult;
import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

//Starts an activity and creates the respective records *remember to take out creation code from here

public class StartActivityActionHandler implements
		ActionHandler<StartActivity, StartActivityResult> {

	@Inject
	public StartActivityActionHandler() {
	}

	@Override
	public StartActivityResult execute(StartActivity action,
			ExecutionContext context) throws ActionException {
		
		boolean created=false;
		//create record
		DAO dao=new DAO();
		Record record=new Record(action.getInstant(),action.getActivity().getName(),"start",action.getCommentary());
		dao.ofy().put(record);
		
		Activity activity;
		//updates the activity state and records

		activity=dao.ofy().query(Activity.class).filter("name =", action.getActivity().getName()).get();
		activity.setState(true);
		activity.setLastRecord(record.getDate());
		activity.addRecord(record);
		dao.ofy().put(activity);
		
		//returns the activity to update client lists as necessary
		return new StartActivityResult(activity.getDTO());
	}

	@Override
	public void undo(StartActivity action, StartActivityResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<StartActivity> getActionType() {
		return StartActivity.class;
	}
}
