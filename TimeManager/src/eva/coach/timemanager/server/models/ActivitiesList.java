package eva.coach.timemanager.server.models;

import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

import eva.coach.timemanager.shared.models.ActivitiesListDTO;

@Unindexed
public class ActivitiesList  {

	@Id Long id;
	@Indexed String name;
	@Indexed boolean isDefault;
	ArrayList<Key<Activity>> activities=new ArrayList<Key<Activity>>();
	ArrayList<String> activitiesNames=new ArrayList<String>();
	
	private ActivitiesList() {
	}
	
	public ActivitiesListDTO getDTO(){
		return new ActivitiesListDTO(name,id);
	}
	
	public ActivitiesListDTO getDTOwithLists(){
		return new ActivitiesListDTO(name,id,activitiesNames);
	}
	
	public ActivitiesList(String name){
		this.name=name;
		isDefault=true;
	}
	
	public ActivitiesList(String name,ArrayList<Long> listsId){
		this(name);
		DAO dao=new DAO();
		Map<Long,Activity> newActivity=dao.ofy().get(Activity.class, listsId);
		for(Activity activity:newActivity.values()){
			this.activities.add(new Key<Activity>(Activity.class,activity.getId()));
			this.activitiesNames.add(activity.getName());
		}
	}

	public ActivitiesList(String name,boolean isDefault){
		this.name=name;
		this.isDefault=isDefault;
	}
	
	public void addActivity(Key<Activity> key,String name){
		activities.add(key);
		activitiesNames.add(name);
	}
	
	public void removeActivity(String name){
		int i=activitiesNames.indexOf(name);
		activities.remove(i);
		activitiesNames.remove(i);
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public ArrayList<Key<Activity>> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<Key<Activity>> activities) {
		this.activities = activities;
	}

	public ArrayList<String> getActivitiesNames() {
		return activitiesNames;
	}

	public void setActivitiesNames(ArrayList<String> activitiesNames) {
		this.activitiesNames = activitiesNames;
	}
	
	

}
