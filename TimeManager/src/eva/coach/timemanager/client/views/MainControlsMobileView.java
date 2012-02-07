package eva.coach.timemanager.client.views;

import java.util.ArrayList;
import java.util.Date;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.inject.Inject;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.core.java.util.Collections;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eva.coach.timemanager.client.presenters.MainControlsPresenter;
import eva.coach.timemanager.client.views.widgets.BasicCell;
import eva.coach.timemanager.server.models.ActivitiesList;
import eva.coach.timemanager.shared.commands.RefreshAllResult;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.buttonbar.ButtonBar;
import com.googlecode.mgwt.ui.client.widget.buttonbar.InfoButton;
import com.googlecode.mgwt.ui.client.widget.buttonbar.OrganizeButton;
import com.googlecode.mgwt.ui.client.widget.buttonbar.PlayButton;
import com.googlecode.mgwt.ui.client.widget.buttonbar.PlusButton;
import com.googlecode.mgwt.ui.client.widget.buttonbar.RefreshButton;
import com.googlecode.mgwt.ui.client.widget.buttonbar.StopButton;
import com.googlecode.mgwt.ui.client.widget.buttonbar.TrashButton;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;

import eva.coach.timemanager.shared.models.ActivitiesListDTO;
import eva.coach.timemanager.shared.models.ActivityDTO;


public class MainControlsMobileView extends ViewImpl implements
		MainControlsPresenter.MyView{
	
	RefreshAllResult data=null;
	
	LayoutPanel main;
	
	PlayButton start=new PlayButton();
	StopButton stop=new StopButton();
	PlusButton create=new PlusButton();
	TrashButton eliminate=new TrashButton();
	OrganizeButton edit=new OrganizeButton();
	InfoButton info=new InfoButton();
	RefreshButton refresh=new RefreshButton();
	
	MCheckBox typeSelector=new MCheckBox();
	Timer timer;
	
	MListBox categoryList;
	
	CellList<ActivityDTO> currentActivities;
	CellList<String> listActivities;
	
	int lastCurrentIndex=-1;
	ArrayList<ActivityDTO> renderedCurrentActivities=new ArrayList<ActivityDTO>();
	
	int lastListedIndex=-1;
	ArrayList<String> renderedListedActivities=new ArrayList<String>();
	
	@Inject
	public MainControlsMobileView() {
		main=new LayoutPanel();
		//main.setSize("100%", "100%");
		//play-stop controls with progress bar
		HeaderPanel currentHeader=new HeaderPanel();
		ButtonBar bar=new ButtonBar();
		//start.setHeight("100%");
		
		//bar.add(start);
		//bar.add(stop);
		currentHeader.setCenter("Current");
		//currentHeader.setLeftWidget(bar);
		//currentHeader.setHeight("10%");
		main.add(currentHeader);
		//main.add(bar);
		//activities list
		currentActivities=new CellList<ActivityDTO>(new BasicCell<ActivityDTO>(){

			@Override
			public String getDisplayString(ActivityDTO model) {
				return model.getName()+"   -------:"+getTime(model.getLastRecord());
			}

		});
		
		currentActivities.addCellSelectedHandler(new CellSelectedHandler() {
            @Override
            public void onCellSelected(CellSelectedEvent event) {
            	int currentIndex=event.getIndex();
            	if(lastCurrentIndex!=currentIndex)currentActivities.setSelectedIndex(currentIndex,true);
            	if(lastCurrentIndex!=-1) currentActivities.setSelectedIndex(lastCurrentIndex, false);
            	if(lastCurrentIndex==currentIndex) lastCurrentIndex=-1;
            	else lastCurrentIndex=currentIndex;
            }
		});
		
		ScrollPanel scrollPanel = new ScrollPanel();
        scrollPanel.setScrollingEnabledX(false);
        scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());
        scrollPanel.setWidget(currentActivities);
        
        
        FlowPanel flow=new FlowPanel();
        //flow.add(scrollPanel);
        //flow.setHeight("30%");
        //scrollPanel.setHeight("20%");
        main.add(scrollPanel);
        currentActivities.render("prueba de texto");
        //options panel
		HeaderPanel optionsHeader=new HeaderPanel();
		//typeSelector.setHeight("100%");
		//optionsHeader.setRightWidget(typeSelector);
		ButtonBar bar2=new ButtonBar();
		//create.setHeight("100%");
		bar2.add(start);
		bar2.add(create);
		bar2.add(eliminate);
		bar2.add(edit);
		typeSelector.setImportant(true);
		bar2.add(typeSelector);
		bar2.add(stop);
		bar2.add(info);
		bar2.add(refresh);
		//optionsHeader.setCenterWidget(bar2);
		//optionsHeader.setHeight("10%");
		//main.add(optionsHeader);
		//main.add(typeSelector);
		main.add(bar2);
		//different lists (all by default)
		categoryList=new MListBox();
		categoryList.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent event) {
				ActivitiesListDTO list=null;
				if(categoryList.getValue(categoryList.getSelectedIndex()).equalsIgnoreCase("all")){
					listActivities.render(data.getAllActivities());
					return;
				}
				else{
					for(ActivitiesListDTO act:data.getActivitiesLists()){
						if(act.getName().equalsIgnoreCase(categoryList.getValue(categoryList.getSelectedIndex()))){
							list=act;
							break;
						}
					}
					listActivities.render(list.getActivitiesName());
				}
			}
		});
		//categoryList.setHeight("10%");
		main.add(categoryList);
		//activities on the selected list
		listActivities=new CellList<String>(new BasicCell<String>(){
			@Override
			public String getDisplayString(String model) {
				return model;
			}
		});
		
		listActivities.addCellSelectedHandler(new CellSelectedHandler() {
            @Override
            public void onCellSelected(CellSelectedEvent event) {
            	int currentIndex=event.getIndex();
            	if(lastListedIndex!=currentIndex)listActivities.setSelectedIndex(currentIndex,true);
            	if(lastListedIndex!=-1) listActivities.setSelectedIndex(lastListedIndex, false);
            	if(lastListedIndex==currentIndex) lastListedIndex=-1;
            	else lastListedIndex=currentIndex;
            }
		});
		
		ScrollPanel scrollPanel2 = new ScrollPanel();
        scrollPanel2.setScrollingEnabledX(false);
        scrollPanel2.setWidget(listActivities);
        scrollPanel2.setUsePos(MGWT.getOsDetection().isAndroid());
        FlowPanel flow2=new FlowPanel();
        flow2.add(scrollPanel2);
        //flow2.setHeight("40%");
        //scrollPanel2.setHeight("30%");
        main.add(scrollPanel2);
        //put a tab menu here later
	}

	@Override
	public Widget asWidget() {
		return main;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#startActivity()
	 */
	@Override
	public HasTapHandlers startActivity() {
		return start;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#stopActivity()
	 */
	@Override
	public HasTapHandlers stopActivity() {
		return stop;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#refreshAll(eva.coach.timemanager.shared.commands.RefreshAllResult)
	 */
	@Override
	public void refreshAll(RefreshAllResult result) {
		//if just initialized account go out 
		if(data!=null)return;
		data=result;
		if(result.getAllActivities()==null || result.getAllActivities().isEmpty()){
			return;
		}
		//fill current list
		currentActivities.render(data.getCurrentActivities());
		renderedCurrentActivities=data.getCurrentActivities();
		//fill category list
		categoryList.clear();
		categoryList.addItem("All");
		for(ActivitiesListDTO list:data.getActivitiesLists()){
			categoryList.addItem(list.getName());
		}
		categoryList.setSelectedIndex(0);
		listActivities.render(data.getAllActivities());
		renderedListedActivities=data.getAllActivities();
		//updates other gadgets
		
		//start timer
		setTimer();
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#beginUpdate()
	 */
	@Override
	public void startUpdate(ActivityDTO activity) {
		data.getCurrentActivities().add(activity);
		currentActivities.render(renderedCurrentActivities);
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#stopUpdate()
	 */
	@Override
	public void stopUpdate(ActivityDTO activity) {
		for(ActivityDTO act:data.getCurrentActivities()){
			if(act.getName().equalsIgnoreCase(activity.getName())){
				data.getCurrentActivities().remove(act);
				break;
			}
		}
		lastCurrentIndex=-1;
		currentActivities.render(renderedCurrentActivities);
	}
	
	public void setTimer(){
		timer=new Timer() {
	      public void run() {
	    	  currentActivities.render(renderedCurrentActivities);
	        }
	      };
	      timer.scheduleRepeating(1000);
	}
	
	public void stopTimer(){
		timer.cancel();
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#creation()
	 */
	@Override
	public HasTapHandlers creation() {
		return create;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#editing()
	 */
	@Override
	public HasTapHandlers editing() {
		return edit;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#getSelectedEntity()
	 */
	@Override
	public String getSelectedEntity() {
		if(typeSelector.getValue()) return "activity";
		else return "list";
	}
	
	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#getSelectedActivity()
	 */
	@Override
	public ActivityDTO getSelectedActivityCurrent() {
		if(lastCurrentIndex==-1)return null;
		return renderedCurrentActivities.get(lastCurrentIndex);
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#getSelectedActivityListed()
	 */
	@Override
	public ActivityDTO getSelectedActivityListed() {
		// TODO OPTIMIZE SEARCH ALGORITHM WITH SOME KIND OF MAP DATA SAVING FOR ACTIVITIES
		if(lastListedIndex==-1)return null;
		String name=renderedListedActivities.get(lastListedIndex);
		for(ActivityDTO act:data.getActivities()){
			if(act.getName().equalsIgnoreCase(name))return act;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#getSelectedActivitiesList()
	 */
	@Override
	public ActivitiesListDTO getSelectedActivitiesList() {
		String name=categoryList.getValue(categoryList.getSelectedIndex());
		if(name.equalsIgnoreCase("all")){
			return null;
		}
		else{
			for(ActivitiesListDTO act:data.getActivitiesLists()){
				if(act.getName().equalsIgnoreCase(name)){
					return act;
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#activityUpdate(eva.coach.timemanager.shared.models.ActivityDTO, boolean)
	 */
	@Override
	public void activityUpdate(ActivityDTO activity, boolean created) {
		if(created){
			data.getActivities().add(activity);
			data.getAllActivities().add(activity.getName());
			String currentCategory=categoryList.getValue(categoryList.getSelectedIndex());
			for(String listName:activity.getListsNames()){
				for(ActivitiesListDTO act:data.getActivitiesLists()){
					if(act.getName().equalsIgnoreCase(listName)){
						act.getActivitiesName().add(activity.getName());
						if(listName.equalsIgnoreCase(currentCategory)){
							renderedListedActivities=act.getActivitiesName();
							listActivities.render(renderedListedActivities);
						}
						break;
					}
				}
				
				
			}
			if(currentCategory.equalsIgnoreCase("all")){
				//renderedListedActivities.add(activity.getName());
				listActivities.render(renderedListedActivities);
			}
		}
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#listUpdate(eva.coach.timemanager.shared.models.ActivitiesListDTO, boolean)
	 */
	@Override
	public void listUpdate(ActivitiesListDTO activitiesList, boolean created) {
		if(created){
			data.getActivitiesLists().add(activitiesList);
			categoryList.addItem(activitiesList.getName());
		}
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#getActivityIds(java.util.ArrayList)
	 */
	@Override
	public ArrayList<Long> getActivityIds(ArrayList<String> names) {
		// TODO OPTIMIZE SEARCH ALGORITHM WITH SOME KIND OF MAP DATA SAVING FOR ACTIVITIES
		ArrayList<Long> ids=new ArrayList<Long>();
		for(String name:names){
			for(ActivityDTO act:data.getActivities()){
				if(act.getName().equalsIgnoreCase(name)){
					ids.add(act.getId());
					break;
				}
			}
		}
		return ids;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#getListIds(java.util.ArrayList)
	 */
	@Override
	public ArrayList<Long> getListIds(ArrayList<String> name) {
		// TODO Auto-generated method stub
		ArrayList<Long> ids=new ArrayList<Long>();
		for(String nam:name){
			for(ActivitiesListDTO act:data.getActivitiesLists()){
				if(act.getName().equalsIgnoreCase(nam)){
					ids.add(act.getId());
					break;
				}
			}
		}
		return ids;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#getData()
	 */
	@Override
	public RefreshAllResult getData() {
		return data;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#otherplace()
	 */
	@Override
	public HasTapHandlers otherplace() {
		return info;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#refresh()
	 */
	@Override
	public HasTapHandlers refresh() {
		return refresh;
	}

	/* (non-Javadoc)
	 * @see eva.coach.timemanager.client.presenters.MainControlsPresenter.MyView#setData(eva.coach.timemanager.shared.commands.RefreshAllResult)
	 */
	@Override
	public void setData(RefreshAllResult result) {
		data=result;
	}
	
	public String getTime(Date last){
		Date now=new Date();
		long allseconds=(now.getTime()-last.getTime())/(long)1000;
		int minutes=(int)allseconds/60;
		int hours=(int)minutes/60;
		int remainingMinutes=minutes-(hours*60);
		int seconds=(int)allseconds-(remainingMinutes*60)-(hours*60*60);
		
		String secondsString="";
		String minutesString="";
		String hoursString="";
		if(seconds<10) secondsString=secondsString.concat("0").concat(seconds+"");
		else secondsString=seconds+"";
		if(remainingMinutes<10) minutesString=minutesString.concat("0").concat(remainingMinutes+"");
		else minutesString=remainingMinutes+"";
		if(hours<10)hoursString=hoursString.concat("0").concat(hours+"");
		else hoursString=hours+"";
		
		return hoursString+":"+minutesString+":"+secondsString;
	}
}
