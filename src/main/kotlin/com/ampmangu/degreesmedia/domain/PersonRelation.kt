package com.ampmangu.degreesmedia.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "person_relation")
class PersonRelation : Serializable {
	@EmbeddedId
	var id: PersonRelationId? = null

	@Column(name = "stillvalid")
	var stillValid = true

	@ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.REFRESH])
	@JoinColumn(name = "leftsideid", insertable = false, updatable = false)
	@JsonIgnoreProperties
	var leftSidePerson: Person? = null

	@ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.REFRESH])
	@JoinColumn(name = "rightsideid", insertable = false, updatable = false)
	@JsonIgnoreProperties
	var rightSidePerson: Person? = null

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || javaClass != other.javaClass) return false
		val relation = other as PersonRelation
		if (id != relation.id) return false
		if (stillValid != relation.stillValid) return false
		return if (!leftSidePerson!!.equals(relation.leftSidePerson)) false else rightSidePerson!!.equals(relation.rightSidePerson)
	}

	override fun hashCode(): Int {
		var result = id.hashCode()
		result = 31 * result + stillValid.hashCode()
		result = 31 * result + leftSidePerson.hashCode()
		result = 31 * result + rightSidePerson.hashCode()
		return result
	}

	override fun toString(): String {
		return "PersonRelation{" +
				"id=" + id +
				", stillValid=" + stillValid +
				", leftSidePerson=" + leftSidePerson +
				", rightSidePerson=" + rightSidePerson +
				'}'
	}

	@Embeddable
	class PersonRelationId : Serializable {
		@Column(name = "leftsideid", nullable = false, updatable = false)
		var leftSideId: Int? = null

		@Column(name = "rightsideid", nullable = false, updatable = false)
		var rightSideId: Int? = null
		override fun equals(obj: Any?): Boolean {
			if (obj !is PersonRelationId) {
				return false
			}
			val other = obj
			if (other.leftSideId != leftSideId) {
				return false
			}
			return other.rightSideId == rightSideId
		}

		override fun hashCode(): Int {
			return Objects.hash(leftSideId, rightSideId)
		}

		companion object {
			private const val serialVersionUID = 1L
		}
	}

	companion object {
		private const val serialVersionUID = 1L
	}
}