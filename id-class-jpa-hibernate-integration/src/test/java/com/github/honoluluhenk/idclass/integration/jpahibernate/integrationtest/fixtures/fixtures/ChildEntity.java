package com.github.honoluluhenk.idclass.integration.jpahibernate.integrationtest.fixtures.fixtures;

import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ChildEntity extends AbstractEntity<ChildEntity> {

	private String name = "";

}
