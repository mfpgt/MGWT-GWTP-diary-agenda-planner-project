package eva.coach.timemanager.shared.commands;

import java.util.ArrayList;

import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;
import eva.coach.timemanager.shared.commands.CreateActivityResult;

public class CreateActivity extends UnsecuredActionImpl<CreateActivityResult> {

	private String name;
	private ArrayList<Long> listsId=new ArrayList<Long>(); 
	
	private CreateActivity(){
		
	}
	
	public CreateActivity(String name,ArrayList<Long> listsId) {
		this.name=name;
		this.listsId=listsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Long> getListsId() {
		return listsId;
	}

	public void setListsId(ArrayList<Long> listsId) {
		this.listsId = listsId;
	}
	
}
