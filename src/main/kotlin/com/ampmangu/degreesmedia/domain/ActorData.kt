package com.ampmangu.degreesmedia.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "actor_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class ActorData : Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private var id: Long? = null

	@javax.persistence.Column(name = "remote_db_id")
	private val remoteDbId: @NotNull Int? = null

	@Column(name = "title", nullable = false)
	private val title: @NotNull String? = null

	@Column(name = "pic_url", nullable = true)
	private val picUrl: String? = null

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	@JsonIgnoreProperties
	private val person: Person? = null

	companion object {
		private const val serialVersionUID = 1L
	}
}