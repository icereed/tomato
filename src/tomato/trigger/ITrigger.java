package tomato.trigger;

import tomato.entity.AbstractEntity;

public interface ITrigger {
	public boolean triggers(AbstractEntity e);
}
