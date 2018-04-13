package listeners;

import models.SSOModel;
import models.token.AccessToken;
import models.token.BaseOrganize;
import models.token.BasePerson;
import models.token.BaseRelation;
import results.sso.OrganizeResult;
import results.sso.PersonResult;
import results.sso.RelationResult;
import utils.SSOUtils;

import javax.persistence.Access;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class SSOModelListener {
    
    @PrePersist
    public static void prePersist(SSOModel ssoModel) {
        if (ssoModel instanceof BaseOrganize) {
            OrganizeResult organizeResult = SSOUtils.organizeAdd((BaseOrganize) ssoModel);
            ssoModel.preUpdate(organizeResult.data.organizeId, organizeResult.data.updateTime);
        } else if (ssoModel instanceof BasePerson) {
            PersonResult personResult = SSOUtils.personAdd((BasePerson) ssoModel);
            ssoModel.preUpdate(personResult.data.personId, personResult.data.updateTime);
        } else if (ssoModel instanceof BaseRelation) {
            RelationResult relationResult = SSOUtils.relationAdd((BaseRelation) ssoModel);
            ssoModel.preUpdate(relationResult.data.relationId, relationResult.data.updateTime);
        }
    }
    
    @PreUpdate
    public static void preUpdate(SSOModel ssoModel) {
        if (ssoModel instanceof BaseOrganize) {
            OrganizeResult organizeResult = SSOUtils.organizeEdit((BaseOrganize) ssoModel);
            ssoModel.preUpdate(organizeResult.data.updateTime);
        } else if (ssoModel instanceof BasePerson) {
            PersonResult personResult = SSOUtils.personEdit((BasePerson) ssoModel);
            ssoModel.preUpdate(personResult.data.updateTime);
        } else if (ssoModel instanceof BaseRelation) {
            RelationResult relationResult = SSOUtils.relationEdit((BaseRelation) ssoModel);
            ssoModel.preUpdate(relationResult.data.updateTime);
        }
    }
}
