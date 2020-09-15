package com.ampmangu.degreesmedia.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Person : Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null

	@Column(name = "name", nullable = false)
	var name: @NotNull String? = null

	@Column(name = "date_added", nullable = false)
	var dateAdded = Instant.now()

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private var type: @NotNull TypePerson? = TypePerson.GENERAL

	@Column(name = "remote_db_id")
	var remoteDbId: Int? = null

	@Column(name = "pic_url", nullable = true)
	var picUrl: String? = null


	@OneToMany(mappedBy = "leftSidePerson", cascade = [CascadeType.PERSIST, CascadeType.REFRESH], orphanRemoval = true, fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnoreProperties(value = ["leftSidePerson", "rightSidePerson"])
	private var relations: MutableSet<PersonRelation> = HashSet()

	@OneToMany(mappedBy = "person", cascade = [CascadeType.MERGE], orphanRemoval = true, fetch = FetchType.EAGER, targetEntity = ActorData::class)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnoreProperties("person")
	var actorDataList: List<ActorData>? = null

	@ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JoinTable(name = "media_relation", joinColumns = [JoinColumn(name = "person_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "media_id", referencedColumnName = "id")])
	@JsonIgnore
	private var mediaIn: MutableSet<Media> = HashSet()


	fun addRelations(relation: PersonRelation) {
		relations.add(relation)
	}

	fun editPicUrl(picUrl: String) {
		if (type === TypePerson.MOVIES) {
			this.picUrl = "https://image.tmdb.org/t/p/w45$picUrl"
			//TODO Add future picurl servers
		} else {
			this.picUrl = picUrl
		}
	}


	fun addMediaIn(mediaIn: Media) {
		this.mediaIn.add(mediaIn)
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || javaClass != other.javaClass) return false
		val person = other as Person
		if (id != person.id) return false
		if (name != person.name) return false
		if (dateAdded != person.dateAdded) return false
		return if (type !== person.type) false else remoteDbId == person.remoteDbId
	}

	override fun hashCode(): Int {
		var result = id.hashCode()
		result = 31 * result + name.hashCode()
		result = 31 * result + dateAdded.hashCode()
		result = 31 * result + type.hashCode()
		result = 31 * result + remoteDbId.hashCode()
		return result
	}

	override fun toString(): String {
		return "Person{" +
				"name='" + name + '\'' +
				", dateAdded=" + dateAdded +
				", type=" + type +
				'}'
	}

	companion object {
		private const val serialVersionUID = 1L
	}
}