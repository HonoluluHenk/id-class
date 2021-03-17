package demo;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.github.honoluluhenk.idclass.ID;
import org.hibernate.annotations.Type;

@Entity
public class SomeEntity {
	@Id
	@Type(type = "com.github.honoluluhenk.idclass.integration.jpahibernate.IDType")
	private ID<SomeEntity> id;

	public ID<SomeEntity> getId() {
		return id;
	}

	public void setId(ID<SomeEntity> id) {
		this.id = id;
	}
}

