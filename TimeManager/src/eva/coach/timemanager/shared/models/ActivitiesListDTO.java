package eva.coach.timemanager.shared.models;

import java.io.Serializable;
import java.lang.String;
import java.lang.Long;
import java.util.ArrayList;

public class ActivitiesListDTO implements Serializable {

	private static final long serialVersionUID = -8290457316764052359L;
	private String name;
	private Long id;
	private ArrayList<String> activitiesName;

	private ActivitiesListDTO() {
	}
	
	public ActivitiesListDTO(String name,Long id) {
		this.name=name;
		this.id=id;
	}
	
	public ActivitiesListDTO(String name,Long id,ArrayList<String> activitiesName) {
		this(name,id);
		this.activitiesName=activitiesName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public ArrayList<String> getActivitiesName() {
		return activitiesName;
	}
	
	
}
