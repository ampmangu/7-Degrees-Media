package com.ampmangu.degreesmedia.domain

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Media : Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private var id: Long? = null

	@javax.persistence.Column(name = "remote_db_id")
	private val remoteDbId: Int? = null

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private val type: @NotNull TypeMedia? = TypeMedia.MOVIE

	@Column(name = "date_added", nullable = false)
	private val dateAdded: Instant? = Instant.now()

	@Column(name = "name", nullable = false)
	private val name: @NotNull String? = null

	companion object {
		private const val serialVersionUID = 1L
	}
}