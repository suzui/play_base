package listeners;

import models.sso.SsoModel;
import models.sso.SsoOrganize;
import models.sso.SsoPerson;
import models.sso.SsoRelation;
import results.sso.OrganizeResult;
import results.sso.PersonResult;
import results.sso.RelationResult;
import utils.SSOUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class SSOModelListener {
    
    @PrePersist
    public static void prePersist(SsoModel ssoModel) {
        if (ssoModel instanceof SsoOrganize) {
            OrganizeResult organizeResult = SSOUtils.organizeAdd((SsoOrganize) ssoModel);
            ssoModel.preUpdate(organizeResult.data.organizeId, organizeResult.data.updateTime);
        } else if (ssoModel instanceof SsoPerson) {
            PersonResult personResult = SSOUtils.personAdd((SsoPerson) ssoModel);
            ssoModel.preUpdate(personResult.data.personId, personResult.data.updateTime);
        } else if (ssoModel instanceof SsoRelation) {
            RelationResult relationResult = SSOUtils.relationAdd((SsoRelation) ssoModel);
            ssoModel.preUpdate(relationResult.data.relationId, relationResult.data.updateTime);
        }
    }
    
    @PreUpdate
    public static void preUpdate(SsoModel ssoModel) {
        if (ssoModel instanceof SsoOrganize) {
            OrganizeResult organizeResult = SSOUtils.organizeEdit((SsoOrganize) ssoModel);
            ssoModel.preUpdate(organizeResult.data.updateTime);
        } else if (ssoModel instanceof SsoPerson) {
            PersonResult personResult = SSOUtils.personEdit((SsoPerson) ssoModel);
            ssoModel.preUpdate(personResult.data.updateTime);
        } else if (ssoModel instanceof SsoRelation) {
            RelationResult relationResult = SSOUtils.relationEdit((SsoRelation) ssoModel);
            ssoModel.preUpdate(relationResult.data.updateTime);
        }
    }
}
