package eva.coach.timemanager.server.models;

import java.lang.String;
import java.lang.Boolean;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

import eva.coach.timemanager.shared.models.ActivityDTO;

@Unindexed
public class Activity{
	@Id Long id;
	@Indexed String name;
	@Indexed Boolean state;
	@Indexed Date lastRecord;
	ArrayList<Key<Record>> records=new ArrayList<Key<Record>>();
	ArrayList<String> recordsSummary=new ArrayList<String>();
	ArrayList<Key<ActivitiesList>> lists=new ArrayList<Key<ActivitiesList>>();
	ArrayList<String> listsNames=new ArrayList<String>();
	
	private Activity() {
	}
	
	public Activity(String name){
		this.name=name;
		lastRecord=null;
		state=false;
	}
	
	public Activity(String name,ArrayList<Long> listsId){
		this(name);
		DAO dao=new DAO();
		Map<Long,ActivitiesList> newLists=dao.ofy().get(ActivitiesList.class, listsId);
		for(ActivitiesList list:newLists.values()){
			this.lists.add(new Key<ActivitiesList>(ActivitiesList.class,list.getId()));
			this.listsNames.add(list.getName());
		}
	}
	
	public Activity(String name,ArrayList<String> listsNames,ArrayList<Key<ActivitiesList>> listsKeys){
		this(name);
		this.listsNames.addAll(listsNames);
		this.lists.addAll(listsKeys);
	}
	
	//create and initialize simultaneously
	public Activity(String name,Record record){
		this.name=name;
		state=true;
		lastRecord=record.getDate();
		records.add(new Key<Record>(Record.class, record.id));
		recordsSummary.add(record.getSummary());
	}
	
	public void addActivitiesList(String name,Key<ActivitiesList> key){
		this.lists.add(key);
		this.listsNames.add(name);
	}
	
	public void addRecord(Record record){
		lastRecord=record.getDate();
		records.add(new Key<Record>(Record.class, record.id));
		recordsSummary.add(record.getSummary());
	}
	
	public ActivityDTO getDTO(){
		return new ActivityDTO(id,name,lastRecord);
	}
	
	public ActivityDTO getDTOwithLists(){
		return new ActivityDTO(id,name,lastRecord,listsNames);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<Key<Record>> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<Key<Record>> records) {
		this.records = records;
	}

	public ArrayList<String> getRecordsSummary() {
		return recordsSummary;
	}

	public void setRecordsSummary(ArrayList<String> recordsSummary) {
		this.recordsSummary = recordsSummary;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public void setLastRecord(Date lastRecord) {
		this.lastRecord = lastRecord;
	}

	public String getName() {
		return name;
	}

	public Boolean getState() {
		return state;
	}

	public Date getLastRecord() {
		return lastRecord;
	}

	public ArrayList<Key<ActivitiesList>> getLists() {
		return lists;
	}

	public void setLists(ArrayList<Key<ActivitiesList>> lists) {
		this.lists = lists;
	}

	public ArrayList<String> getListsNames() {
		return listsNames;
	}

	public void setListsNames(ArrayList<String> listsNames) {
		this.listsNames = listsNames;
	}
	
}
