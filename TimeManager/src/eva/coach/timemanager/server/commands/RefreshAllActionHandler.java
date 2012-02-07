package eva.coach.timemanager.server.commands;

import java.util.ArrayList;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;

import eva.coach.timemanager.server.models.ActivitiesList;
import eva.coach.timemanager.server.models.Activity;
import eva.coach.timemanager.server.models.DAO;
import eva.coach.timemanager.shared.commands.RefreshAll;
import eva.coach.timemanager.shared.commands.RefreshAllResult;
import eva.coach.timemanager.shared.models.ActivitiesListDTO;
import eva.coach.timemanager.shared.models.ActivityDTO;

import com.google.inject.Inject;
import com.googlecode.objectify.Query;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

//Refresh application completely

public class RefreshAllActionHandler implements
		ActionHandler<RefreshAll, RefreshAllResult> {

	@Inject
	public RefreshAllActionHandler() {
	}

	@Override
	public RefreshAllResult execute(RefreshAll action, ExecutionContext context)
			throws ActionException {
		DAO dao=new DAO(); 
		
		ActivitiesList allList=null;
		//check if an all list exists
		try{
			allList=dao.ofy().query(ActivitiesList.class).filter("name =", "all").get();
		}catch(Exception e){e.printStackTrace();}
		
		//if the list doesnt exist create it (first time opening app-so return everything null)
		if(allList==null){
			allList=new ActivitiesList("all",true);
			//ActivitiesList current=new ActivitiesList("current",true);
			dao.ofy().put(allList);
			//dao.ofy().put(current);
			return new RefreshAllResult(null,null,null,null,null);
		}//otherwise get all activities (only names) and default lists (names and activity objects) -- and later possible other info too
		else{
						
			ArrayList<String> activitiesNames=new ArrayList<String>();
			ArrayList<ActivityDTO> activities=new ArrayList<ActivityDTO>();
			ArrayList<String> lists=new ArrayList<String>();
			ArrayList<ActivitiesListDTO> activitiesLists=new ArrayList<ActivitiesListDTO>();
			
			Query<ActivitiesList> q=dao.ofy().query(ActivitiesList.class);
			for(ActivitiesList list:q){
				if(list.getName().equalsIgnoreCase("all")){
					activitiesNames=list.getActivitiesNames();
					for(Activity act:dao.ofy().get(list.getActivities()).values()){
						activities.add(act.getDTOwithLists());
					}
				}
				else{
					lists.add(list.getName());
					activitiesLists.add(list.getDTOwithLists());
				}
			}
			//get the current activities
			ArrayList<ActivityDTO> currentActivities=new ArrayList<ActivityDTO>();
			Query<Activity> q2=dao.ofy().query(Activity.class).filter("state =", true);
			for(Activity act:q2){
				currentActivities.add(act.getDTO());
			}
			//get the N most recent activities
			final int N=10;
			ArrayList<String> recentActivities=new ArrayList<String>();
			Query<Activity> q3=dao.ofy().query(Activity.class).order("lastRecord").limit(10);
			for(Activity act:q3){
				recentActivities.add(act.getName());
			}
			//all arrays might be empty (have not added recent activities yet)
			return new RefreshAllResult(activitiesNames,activities,lists,activitiesLists,currentActivities);
		}
		
	}

	@Override
	public void undo(RefreshAll action, RefreshAllResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<RefreshAll> getActionType() {
		return RefreshAll.class;
	}
}
