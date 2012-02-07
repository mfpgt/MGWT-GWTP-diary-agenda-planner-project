package eva.coach.timemanager.server.models;

import java.lang.Long;
import java.util.Date;
import java.lang.String;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
public class Record {

	@Id Long id;
	@Indexed Date date;
	@Indexed String activity;
	String commentary;
	String action;

	private Record() {
	}
	
	public Record(Date date,String activity,String action){
		this.date=date;
		this.activity=activity;
		this.action=action;
		this.commentary="";
	}
	
	public Record(Date date,String activity,String action,String commentary){
		this(date,activity,action);
		this.commentary=commentary;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getActivity() {
		return activity;
	}

	public String getCommentary() {
		return commentary;
	}

	public String getAction() {
		return action;
	}
	
	//date_activityName_action
	//dow mon dd hh:mm:ss zzz yyyy_running_start
	
	public String getSummary(){
		return date.toString()+"_"+activity+"_"+action;
	}
}
