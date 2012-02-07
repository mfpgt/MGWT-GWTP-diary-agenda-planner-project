/**
 * 
 */
package eva.coach.timemanager.server.models;

import java.util.ConcurrentModificationException;

import com.googlecode.objectify.ObjectifyOpts;

/**
 * @author Martín Felipe Pérez-Guevara Truskowski
 *
 */
public class DAOT extends DAO {
    
    /** Alternate interface to Runnable for executing transactions */
    public static interface Transactable
    {
            void run(DAOT daot);
    }
    
    /**
     * Provides a place to put the result too.  Note that the result
     * is only valid if the transaction completes successfully; otherwise
     * it should be ignored because it is not necessarily valid.
     */
    abstract public static class Transact<T> implements Transactable
    {
            protected T result;
            public T getResult() { return this.result; }
    }
    
    /** Create a default DAOT and run the transaction through it */
    public static void runInTransaction(Transactable t)
    {
            DAOT daot = new DAOT();
            daot.doTransaction(t);
    }
    
    /**
     * Run this task through transactions until it succeeds without an optimistic
     * concurrency failure.
     */
    public static void repeatInTransaction(Transactable t)
    {
            while (true)
            {
                    try
                    {
                            runInTransaction(t);
                            break;
                    }
                    catch (ConcurrentModificationException ex)
                    {
                            
                    }
            }
    }
    
    /** Starts out with a transaction and session cache */
    public DAOT()
    {
            super(new ObjectifyOpts().setSessionCache(true).setBeginTransaction(true));
    }
    
    /** Adds transaction to whatever you pass in */
    public DAOT(ObjectifyOpts opts)
    {
            super(opts.setBeginTransaction(true));
    }
    
    /**
     * Executes the task in the transactional context of this DAO/ofy.
     */
    public void doTransaction(final Runnable task)
    {
            this.doTransaction(new Transactable() {
                    @Override
                    public void run(DAOT daot)
                    {
                            task.run();
                    }
            });
    }

    /**
     * Executes the task in the transactional context of this DAO/ofy.
     */
    public void doTransaction(Transactable task)
    {
            try
            {
                    task.run(this);
                    ofy().getTxn().commit();
            }
            finally
            {
                    if (ofy().getTxn().isActive())
                            ofy().getTxn().rollback();
            }
    }
}
