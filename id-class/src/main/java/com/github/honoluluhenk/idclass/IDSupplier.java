package com.github.honoluluhenk.idclass;

@FunctionalInterface
public interface IDSupplier<Id> {
	Id getId();
}
