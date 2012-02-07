/**
 * 
 */
package eva.coach.timemanager.server.models;


import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

/**
 * @author Mart�n Felipe P�rez-Guevara Truskowski
 *
 */
public class DAO extends DAOBase {
	static {
        ObjectifyService.register(Activity.class);
        ObjectifyService.register(ActivitiesList.class);
        ObjectifyService.register(Record.class);
    }
	
	public DAO(){
		super();
	}
	
	public DAO(ObjectifyOpts opts)
    {
        super(opts);
    }
}
