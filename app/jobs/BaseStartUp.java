package jobs;

import models.back.Admin;
import models.back.Config;
import org.hibernate.Session;
import play.db.jpa.JPA;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import javax.persistence.EntityManager;

@OnApplicationStart
public class BaseStartUp extends Job {
    
    @Override
    public void doJob() throws Exception {
        initAdmin();
        initConfig();
        updateColumn();
    }
    
    private static void initAdmin() {
        final Session s = (Session) JPA.em().getDelegate();
        if (!s.getTransaction().isActive()) {
            s.getTransaction().begin();
        }
        Admin.init();
        s.getTransaction().commit();
    }
    
    private static void initConfig() {
        final Session s = (Session) JPA.em().getDelegate();
        if (!s.getTransaction().isActive()) {
            s.getTransaction().begin();
        }
        Config.init();
        s.getTransaction().commit();
        
    }
    
    private static void updateColumn() {
        final EntityManager em = JPA.em();
        Session s = (Session) em.getDelegate();
        if (!s.getTransaction().isActive())
            s.getTransaction().begin();
        s.getTransaction().commit();
    }
}
