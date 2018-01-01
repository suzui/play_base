package listeners;

import models.BaseModel;

import javax.persistence.PreUpdate;

public class BaseModelListener {
	@PreUpdate
	public static void preUpdate(BaseModel baseModel) {
		baseModel.updateTime = System.currentTimeMillis();
	}
}
