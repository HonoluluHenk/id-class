package com.github.honoluluhenk.idclass;

import lombok.NonNull;

public interface IDSupplier<Id> {
	@NonNull Id getId();
}
