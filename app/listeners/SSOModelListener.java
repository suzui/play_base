package listeners;

import models.sso.SsoModel;
import models.sso.SsoOrganize;
import models.sso.SsoPerson;
import models.sso.SsoRelation;
import play.Play;
import results.sso.OrganizeResult;
import results.sso.PersonResult;
import results.sso.RelationResult;
import utils.SSOUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class SSOModelListener {
    
    @PrePersist
    public static void prePersist(SsoModel ssoModel) {
        if (Play.configuration.getProperty("sso", "off").equals("on")) {
            if (ssoModel instanceof SsoOrganize) {
                OrganizeResult organizeResult = SSOUtils.organizeAdd((SsoOrganize) ssoModel);
                ssoModel.preUpdate(organizeResult.data.organizeId);
            } else if (ssoModel instanceof SsoPerson) {
                PersonResult personResult = SSOUtils.personAdd((SsoPerson) ssoModel);
                ssoModel.preUpdate(personResult.data.personId);
            } else if (ssoModel instanceof SsoRelation) {
                RelationResult relationResult = SSOUtils.relationAdd((SsoRelation) ssoModel);
                ssoModel.preUpdate(relationResult.data.relationId);
            }
        }
    }
    
    @PreUpdate
    public static void preUpdate(SsoModel ssoModel) {
        if (Play.configuration.getProperty("sso", "off").equals("on")) {
            if (ssoModel instanceof SsoOrganize) {
                SsoOrganize ssoOrganize = (SsoOrganize) ssoModel;
                if (ssoOrganize.deleted) {
                    SSOUtils.organizeDelete(ssoOrganize);
                } else {
                    SSOUtils.organizeEdit(ssoOrganize);
                }
            } else if (ssoModel instanceof SsoPerson) {
                SsoPerson ssoPerson = (SsoPerson) ssoModel;
                if (ssoPerson.deleted) {
                    SSOUtils.personDelete(ssoPerson);
                } else {
                    SSOUtils.personEdit(ssoPerson);
                }
            } else if (ssoModel instanceof SsoRelation) {
                SsoRelation ssoRelation = (SsoRelation) ssoModel;
                if (ssoRelation.deleted) {
                    SSOUtils.relationDelete(ssoRelation);
                } else {
                    SSOUtils.relationEdit(ssoRelation);
                }
            }
        }
    }
}
