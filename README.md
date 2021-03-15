## id-class

Helps binding IDs of objects/entities to their type and thus prevents using IDs for the wrong object/entity - and
implicitly documents the entity-type associated to the ID.

## Motivation

Have you ever mixed up your variables containing UUID/String/Long/... IDs in JPA/Hibernate/JAX-RS or other frameworks?

Like maybe so?

```java
import java.util.UUID;

class SomeEntity {
	@Id
	public UUID id = UUID.randomUUID();
}

class OtherEntity {
	@Id
	public UUID id;
}

class Foo {
	void doStuff(OtherEntity otherEntity) {
		// id gets "transformed" to id of wrong entity:
		UUID id = otherEntity.getId();

		// and now you e.g. try to load SomeEntity
		// with the id of OtherEntity but did not find anything:
		SomeEntity someEntity = entityManager.find(SomeEntity.class, id);
	}
}
```

*id-class* to the rescue: now you can have typed IDs bound to the entity-type:

```java
import com.github.honoluluhenk.idclass.ID;

public class SomeEntity {
	@Id
	public ID<SomeEntity> id = ID.randomUUID(SomeEntity.class); // directly support ID generation
}

class OtherEntity {
	@Id
	public UUID id;
}

class Foo {
	void doStuff(OtherEntity otherEntity) {
		// compiler error: ID<OtherEntity> is not compatible with ID<SomeEntity>
		ID<SomeEntity> id = otherEntity.getId();

		// direct integration into hibernate!
		SomeEntity someEntity = entityManager.find(SomeEntity.class, id);
	}
}

```

(this example is using UUIDs, but there are more and its customizable)

You will also find modules to integrate this nicely into

* [JAX-RS with Jackson](https://github.com/FasterXML/jackson)
* [JPA with Hibernate](https://hibernate.org/)

## Installation/Basic Usage

### Just the basic ID class

Install using maven:

```xml

<dependencies>
	<dependency>
		<groupId>com.github.honoluluhenk.id-class</groupId>
		<artifactId>id-class</artifactId>
		<version>${id-class-parent.version}<!-- see github releases --></version>
	</dependency>
</dependencies>
```

Basic usage:

The ID class mainly consists of various factory methods:

```java
class Foo {
	private ID<Foo> id = ID.randomUUID();
	private ID<Foo> id = ID.of(UUID.fromString("af897f93-9067-48a3-96aa-974511a5c3c4"));
	private ID<Foo> id = ID.of("cb310f0f-596e-48f5-b6b6-25301d589fe5");
	//... and more
}
```

ID is immutable, comes with hashCode()/equals(), a nice toString() and implements Serializable.

See javadoc comments or [ID.java source](id-class/src/main/java/com/github/honoluluhenk/idclass/ID.java).

Most of its value actually comes from using it in one of the integrations, see below.

### Jackson Integration

Install using maven (also pulls in the basic ID class):

```xml

<dependencies>
	<dependency>
		<groupId>com.github.honoluluhenk.id-class</groupId>
		<artifactId>id-class-jackson-integration</artifactId>
		<version>${id-class-parent.version}</version>
	</dependency>
	<!-- optional, depending on your requirements: -->
</dependencies>
```

**You need to declare dependencies to jackson yourself (so you don't end up in dependency hell)!**

Jackson integration contains two parts:

* a [ParamConverter](https://docs.oracle.com/javaee/7/api/javax/ws/rs/ext/ParamConverter.html)
  / [ParamConverterProvider](https://docs.oracle.com/javaee/7/api/javax/ws/rs/ext/ParamConverterProvider.html) to make
  REST @PathParams work (automatically configured by
  the [`@Provider`](https://docs.oracle.com/javaee/7/api/javax/ws/rs/ext/Provider.html) annotation)
* a Jackson module containing Serializer/Deserializer to convert IDs in object bodys.

You also need to manually customize the Jackson
Databind [`ObjectMapper`](https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html)
. There's a module for that:

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.honoluluhenk.idclass.integration.jackson.IDTypeModule;

class MyObjectMapperCustomizer {
	void customize(ObjectMapper om) {
		om.registerModule(new IDTypeModule());
	}
}
```

Now you can use it in all possible places in JAX-RS ressources/DTOs:

```java
class JaxHello {
	private ID<SomeEntity> entityID;
	private String name;
	// getters/setters omitted for brevity
}

@Path("hello")
public class HelloResource {

	@POST
	@Path("{id}")
	public JaxHello sayHello(@PathParam("id") ID<SomeEntity> entityID) {
		return someService.findEntity(entityID);
	}
} 
```

### JPA/Hibernate Integration

Install using maven (also pulls in the basic ID class):

```xml

<dependencies>
	<dependency>
		<groupId>com.github.honoluluhenk.id-class</groupId>
		<artifactId>id-class-jpa-hibernate-integration</artifactId>
		<version>${id-class-parent.version}</version>
	</dependency>
</dependencies>
```

**You need to declare dependencies to hibernate yourself (so you don't end up in dependency hell)!**

No you can start using the converter by attaching the
Hibernate [`@Type` or `@TypeDef` annotation](https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#basic-custom-type)
to the field in your entity:

```java
import javax.persistence.Entity;
import javax.persistence.Id;

import com.github.honoluluhenk.idclass.ID;

@Entity
class SomeEntity {

	@Id
	@Type(type = "com.github.honoluluhenk.idclass.integration.jpahibernate.IDType")
	private ID<Entity> id;

	// ...
}
```

## Credits

Thanks to [Fabio](@xfh) for this great idea!

## License

[GNU Lesser General Public License V3 (LGPLv3)](https://www.gnu.org/licenses/lgpl-3.0.html), see [LICENSE](LICENSE).

