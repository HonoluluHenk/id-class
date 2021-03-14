package com.github.honoluluhenk.idclass.integration.jpahibernate.integrationtest.fixtures.fixtures;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static javax.persistence.CascadeType.PERSIST;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ParentEntity extends AbstractEntity<ParentEntity> {

	private String name = "";

	@ManyToOne(cascade = PERSIST)
	private BasicEntity basicEntity;

	@OneToMany(cascade = PERSIST)
	@EqualsAndHashCode.Exclude
	private List<ChildEntity> children = new ArrayList<>();

	public ParentEntity addChild(ChildEntity child) {
		children.add(child);

		return this;
	}
}
