package eva.coach.timemanager.shared.models;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;

public class ActivityDTO implements Serializable {

	private static final long serialVersionUID = -2896997580921413802L;
	private Long id;
	private String name;
	private Date lastRecord;
	private ArrayList<String> listsNames;

	private ActivityDTO(){}
	
	public ActivityDTO(Long id,String name,Date lastRecord) {
		this.id=id;
		this.name=name;
		this.lastRecord=lastRecord;
	}
	
	public ActivityDTO(Long id,String name,Date lastRecord,ArrayList<String> listsNames) {
		this(id,name,lastRecord);
		this.listsNames=listsNames;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastRecord(Date lastRecord) {
		this.lastRecord = lastRecord;
	}

	public String getName() {
		return name;
	}

	public Date getLastRecord() {
		return lastRecord;
	}

	public ArrayList<String> getListsNames() {
		return listsNames;
	}
	
	
}
