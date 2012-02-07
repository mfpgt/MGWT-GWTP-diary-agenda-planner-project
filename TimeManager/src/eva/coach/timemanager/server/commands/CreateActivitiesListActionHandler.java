package eva.coach.timemanager.server.commands;

import java.util.Map;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import eva.coach.timemanager.server.models.ActivitiesList;
import eva.coach.timemanager.server.models.Activity;
import eva.coach.timemanager.server.models.DAO;
import eva.coach.timemanager.shared.commands.CreateActivitiesList;
import eva.coach.timemanager.shared.commands.CreateActivitiesListResult;
import eva.coach.timemanager.shared.commands.CreateActivityResult;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

//Create one ActivityList

public class CreateActivitiesListActionHandler implements
		ActionHandler<CreateActivitiesList, CreateActivitiesListResult> {

	@Inject
	public CreateActivitiesListActionHandler() {
	}

	@Override
	public CreateActivitiesListResult execute(CreateActivitiesList action,
			ExecutionContext context) throws ActionException {
		DAO dao=new DAO();
		ActivitiesList activitiesList;
		//creates the activity
		activitiesList=new ActivitiesList(action.getName(),action.getActivitiesId());
		dao.ofy().put(activitiesList);
		
		//add the activity to the intended additional lists if applicable
		if(action.getActivitiesId()!=null && !action.getActivitiesId().isEmpty()){
			Map<Long,Activity> activities=dao.ofy().get(Activity.class, action.getActivitiesId());
			for(Activity act:activities.values()){
				act.addActivitiesList( act.getName(),new Key<ActivitiesList>(ActivitiesList.class, act.getId()));			
			}
			dao.ofy().put(activities.values());
		}
		
		//returns the activity and a signal of creation to update client lists if necessary
		return new CreateActivitiesListResult(activitiesList.getDTOwithLists());
	}

	@Override
	public void undo(CreateActivitiesList action,
			CreateActivitiesListResult result, ExecutionContext context)
			throws ActionException {
	}

	@Override
	public Class<CreateActivitiesList> getActionType() {
		return CreateActivitiesList.class;
	}
}
