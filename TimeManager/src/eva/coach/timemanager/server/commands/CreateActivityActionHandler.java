package eva.coach.timemanager.server.commands;

import java.util.ArrayList;
import java.util.Map;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import eva.coach.timemanager.server.models.ActivitiesList;
import eva.coach.timemanager.server.models.Activity;
import eva.coach.timemanager.server.models.DAO;
import eva.coach.timemanager.server.models.Record;
import eva.coach.timemanager.shared.commands.CreateActivity;
import eva.coach.timemanager.shared.commands.CreateActivityResult;
import eva.coach.timemanager.shared.commands.StartActivityResult;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

//Create an activity and optionally add it to one ActivityList when created

public class CreateActivityActionHandler implements
		ActionHandler<CreateActivity, CreateActivityResult> {

	@Inject
	public CreateActivityActionHandler() {
	}

	@Override
	public CreateActivityResult execute(CreateActivity action,
			ExecutionContext context) throws ActionException {

		DAO dao=new DAO();
		Activity activity;
		//creates the activity
		activity=new Activity(action.getName(),action.getListsId());
		dao.ofy().put(activity);
		
		//updates all list
		ActivitiesList allList= dao.ofy().query(ActivitiesList.class).filter("name =", "all").get();
		allList.addActivity(new Key<Activity>(Activity.class,activity.getId()), activity.getName());
		dao.ofy().put(allList);
		
		//add the activity to the intended additional lists if applicable
		if(action.getListsId()!=null && !action.getListsId().isEmpty()){
			Map<Long,ActivitiesList> newLists=dao.ofy().get(ActivitiesList.class, action.getListsId());
			for(ActivitiesList list:newLists.values()){
				list.addActivity(new Key<Activity>(Activity.class, activity.getId()), activity.getName());			
			}
			dao.ofy().put(newLists.values());
		}
		
		//returns the activity and a signal of creation to update client lists if necessary
		return new CreateActivityResult(activity.getDTOwithLists());
	}

	@Override
	public void undo(CreateActivity action, CreateActivityResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<CreateActivity> getActionType() {
		return CreateActivity.class;
	}
}
