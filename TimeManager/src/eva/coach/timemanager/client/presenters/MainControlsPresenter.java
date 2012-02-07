package eva.coach.timemanager.client.presenters;

import java.util.ArrayList;
import java.util.Date;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;

import eva.coach.timemanager.client.events.StartActivityEvent;
import eva.coach.timemanager.client.events.StartActivityEvent.StartActivityHandler;
import eva.coach.timemanager.client.events.StopActivityEvent;
import eva.coach.timemanager.client.events.StopActivityEvent.StopActivityHandler;
import eva.coach.timemanager.client.mgwtTOgwtp.RevealAnimatableDisplayContentEvent;
import eva.coach.timemanager.client.place.NameTokens;
import eva.coach.timemanager.client.views.widgets.ConfirmDialogWithTextBox;
import eva.coach.timemanager.shared.commands.CreateActivitiesList;
import eva.coach.timemanager.shared.commands.CreateActivitiesListResult;
import eva.coach.timemanager.shared.commands.CreateActivity;
import eva.coach.timemanager.shared.commands.CreateActivityResult;
import eva.coach.timemanager.shared.commands.RefreshAll;
import eva.coach.timemanager.shared.commands.RefreshAllResult;
import eva.coach.timemanager.shared.commands.StartActivity;
import eva.coach.timemanager.shared.commands.StartActivityResult;
import eva.coach.timemanager.shared.commands.StopActivity;
import eva.coach.timemanager.shared.commands.StopActivityResult;
import eva.coach.timemanager.shared.models.ActivitiesListDTO;
import eva.coach.timemanager.shared.models.ActivityDTO;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.google.inject.Inject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

public class MainControlsPresenter extends
		Presenter<MainControlsPresenter.MyView, MainControlsPresenter.MyProxy> {

	ConfirmDialogWithTextBox startDialog;
	ConfirmDialogWithTextBox stopDialog;
	ConfirmDialogWithTextBox activityCreateDialog;
	ConfirmDialogWithTextBox listCreateDialog;
	ConfirmDialogWithTextBox activityEditDialog;
	ConfirmDialogWithTextBox listEditDialog;
	
	public int currentActivities=0;
	
	public interface MyView extends View {
		HasTapHandlers startActivity();
		HasTapHandlers stopActivity();
		HasTapHandlers creation();
		HasTapHandlers editing();
		HasTapHandlers otherplace();
		HasTapHandlers refresh();
		
		String getSelectedEntity();
		ActivityDTO getSelectedActivityCurrent();
		ActivityDTO getSelectedActivityListed();
		ActivitiesListDTO getSelectedActivitiesList();
		RefreshAllResult getData();
		
		void setData(RefreshAllResult result);
		void refreshAll(RefreshAllResult result);
		void startUpdate(ActivityDTO activity);
		void stopUpdate(ActivityDTO activity);
		void activityUpdate(ActivityDTO activity,boolean created);
		void listUpdate(ActivitiesListDTO activitiesList,boolean created);
		
		ArrayList<Long> getActivityIds(ArrayList<String> names);
		ArrayList<Long> getListIds(ArrayList<String> name);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.welcome)
	public interface MyProxy extends ProxyPlace<MainControlsPresenter> {
	}

	private final DispatchAsync dispatcher;
	private final PlaceManager placeManager;
	
	@Inject
	public MainControlsPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy,final DispatchAsync dispatcher,final PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.dispatcher=dispatcher;
		this.placeManager=placeManager;
		refreshAll();
		createDialogs();
	}
	
	public void refreshAll(){
		dispatcher.execute(new RefreshAll(),
				new AsyncCallback<RefreshAllResult>() {
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
	
					@Override
					public void onSuccess(RefreshAllResult result) {
						System.out.println("toda la informacion actualizada");
						getView().refreshAll(result);
					}
				});
	}
	
	public void createDialogs(){
		
		//creates the start activity dialog
		startDialog=new ConfirmDialogWithTextBox("start activity","",new ConfirmDialogWithTextBox.ConfirmCallback(){

			@Override
			public void onOk() {
				dispatcher.execute(new StartActivity(new Date(),startDialog.getCommentary(),getView().getSelectedActivityListed()),
          				new AsyncCallback<StartActivityResult>() {
          					@Override
          					public void onFailure(Throwable caught) {
          						caught.printStackTrace();
          					}
          	
          					@Override
          					public void onSuccess(StartActivityResult result) {
          						getView().startUpdate(result.getActivity());
          					}
          				});
				
			}

			@Override
			public void onCancel() {
			}
			@Override
			public void onStart() {
			}
		},1);
		
		//creates the stop activity dialog
		stopDialog=new ConfirmDialogWithTextBox("stop activity","",new ConfirmDialogWithTextBox.ConfirmCallback(){

			@Override
			public void onOk() {
				dispatcher.execute(new StopActivity(getView().getSelectedActivityCurrent(),new Date(),stopDialog.getCommentary()),
          				new AsyncCallback<StopActivityResult>() {
          					@Override
          					public void onFailure(Throwable caught) {
          						caught.printStackTrace();
          					}
          	
          					@Override
          					public void onSuccess(StopActivityResult result) {
          						getView().stopUpdate(result.getActivity());
          					}
          				});
				
			}

			@Override
			public void onCancel() {	
			}
			@Override
			public void onStart() {
			}
		},1);
		
		//creates the activity creation dialog
		
		activityCreateDialog=new ConfirmDialogWithTextBox("create activity","",new ConfirmDialogWithTextBox.ConfirmCallback(){
		
			@Override
			public void onOk() {
				
				dispatcher.execute(new CreateActivity(activityCreateDialog.getItemName(),getView().getListIds(activityCreateDialog.getSelectedItems())),
          				new AsyncCallback<CreateActivityResult>() {
          					@Override
          					public void onFailure(Throwable caught) {
          						caught.printStackTrace();
          					}
          	
          					@Override
          					public void onSuccess(CreateActivityResult result) {
          						getView().activityUpdate(result.getActivity(), true);
          					}
          				});
			}

			@Override
			public void onCancel() {	
			}
			@Override
			public void onStart() {
				dispatcher.execute(new CreateActivity(activityCreateDialog.getItemName(),getView().getListIds(activityCreateDialog.getSelectedItems())),
          				new AsyncCallback<CreateActivityResult>() {
          					@Override
          					public void onFailure(Throwable caught) {
          						caught.printStackTrace();
          					}
          	
          					@Override
          					public void onSuccess(CreateActivityResult result) {
          						getView().activityUpdate(result.getActivity(), true);
          						dispatcher.execute(new StartActivity(new Date(),activityCreateDialog.getCommentary(),result.getActivity()),
          		          				new AsyncCallback<StartActivityResult>() {
          		          					@Override
          		          					public void onFailure(Throwable caught) {
          		          						caught.printStackTrace();
          		          					}
          		          	
          		          					@Override
          		          					public void onSuccess(StartActivityResult result) {
          		          						getView().startUpdate(result.getActivity());
          		          					}
          		          				});
          					}
          				});
			}
		},2);

		//creates the list creation dialog
		
		listCreateDialog=new ConfirmDialogWithTextBox("create list","",new ConfirmDialogWithTextBox.ConfirmCallback(){

			@Override
			public void onOk() {
				dispatcher.execute(new CreateActivitiesList(listCreateDialog.getItemName(),getView().getActivityIds(listCreateDialog.getSelectedItems())),
          				new AsyncCallback<CreateActivitiesListResult>() {
          					@Override
          					public void onFailure(Throwable caught) {
          						caught.printStackTrace();
          					}
          	
          					@Override
          					public void onSuccess(CreateActivitiesListResult result) {
          						getView().listUpdate(result.getActivitiesList(), true);
          					}
          				});
			}

			@Override
			public void onCancel() {	
			}
			@Override
			public void onStart() {
			}
		},3);
		
		//editing dialogs
	}
	

	@Override
	protected void revealInParent() {
		//for mobile based on mgwt
		RevealAnimatableDisplayContentEvent.fire(this, this,getAnimation());
		//RevealRootLayoutContentEvent.fire(this, this);
	}

	private Animation getAnimation(){
		return Animation.FLIP;
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		
		//controls
		registerHandler(getView().startActivity().addTapHandler(
				new TapHandler() {
					@Override
					public void onTap(TapEvent event) {
						ActivityDTO activity=getView().getSelectedActivityListed();
						if(activity==null){
							return;
						}
						startDialog.setText(activity.getName()+" commentary:");
						startDialog.show();
					}
				}));
		
		registerHandler(getView().stopActivity().addTapHandler(
				new TapHandler() {
					@Override
					public void onTap(TapEvent event) {
						ActivityDTO activity=getView().getSelectedActivityCurrent();
						if(activity==null){
							return;
						}
						stopDialog.setText(activity.getName()+" commentary:");
						stopDialog.show();
					}
				}));
		
		registerHandler(getView().creation().addTapHandler(
				new TapHandler() {
					@Override
					public void onTap(TapEvent event) {
						//PlaceRequest myRequest = new PlaceRequest(NameTokens.creation).with("entity", getView().getSelectedEntity());
						//placeManager.revealPlace( myRequest,true);
						if(getView().getSelectedEntity().equalsIgnoreCase("activity")){
							activityCreateDialog.setList(getView().getData().getLists());
							activityCreateDialog.show();
						}
						else if(getView().getSelectedEntity().equalsIgnoreCase("list")){
							listCreateDialog.setList(getView().getData().getAllActivities());
							listCreateDialog.show();
						}
					}
				}));
		
		registerHandler(getView().editing().addTapHandler(
				new TapHandler() {
					@Override
					public void onTap(TapEvent event) {
						//PlaceRequest myRequest = new PlaceRequest(NameTokens.creation).with("entity", getView().getSelectedEntity());
						//placeManager.revealPlace( myRequest,true);
						if(getView().getSelectedEntity().equalsIgnoreCase("activity")){
							//the selected activity from the list should be acquired
						}
						else if(getView().getSelectedEntity().equalsIgnoreCase("list")){
							
						}
						
					}
				}));
		
		registerHandler(getView().otherplace().addTapHandler(
				new TapHandler() {
					@Override
					public void onTap(TapEvent event) {
						PlaceRequest myRequest = new PlaceRequest(NameTokens.creation);
						placeManager.revealPlace( myRequest,true);
					}
				}));
		
		registerHandler(getView().refresh().addTapHandler(
				new TapHandler() {
					@Override
					public void onTap(TapEvent event) {
						getView().setData(null);
						refreshAll();
					}
				}));
		
		//events
		/*
		addRegisteredHandler( StartActivityEvent.getType(), new StartActivityHandler() {
	           @Override
	           public void onStartActivity(StartActivityEvent event) {
	        	   dispatcher.execute(new StartActivity(event.getInstant(),event.getCommentary(),event.getActivity(),event.isExists()),
	          				new AsyncCallback<StartActivityResult>() {
	          					@Override
	          					public void onFailure(Throwable caught) {
	          						caught.printStackTrace();
	          					}
	          	
	          					@Override
	          					public void onSuccess(StartActivityResult result) {
	          						getView().startUpdate(result.getActivity(),result.isCreated());
	          					}
	          				});
	           } 
	         } );
		
		
		addRegisteredHandler( StopActivityEvent.getType(), new StopActivityHandler() {
	           @Override
	           public void onStopActivity(StopActivityEvent event) {
	        	   dispatcher.execute(new StopActivity(event.getActivity(),event.getInstant(),event.getCommentary()),
	          				new AsyncCallback<StopActivityResult>() {
	          					@Override
	          					public void onFailure(Throwable caught) {
	          						caught.printStackTrace();
	          					}
	          	
	          					@Override
	          					public void onSuccess(StopActivityResult result) {
	          						getView().stopUpdate(result.getActivity());
	          					}
	          				});
	           } 
	         } );
	         */
	}

	@Override
	protected void onHide() {
		super.onHide();
	}

	@Override
	protected void onReset() {
		super.onReset();
		refreshAll();
	}

	@Override
	protected void onReveal() {
		super.onReveal();
	}

	@Override
	protected void onUnbind() {
		super.onUnbind();
	}
}
