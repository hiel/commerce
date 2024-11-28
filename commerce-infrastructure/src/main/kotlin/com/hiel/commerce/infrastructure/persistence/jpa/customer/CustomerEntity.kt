package com.hiel.commerce.infrastructure.persistence.jpa.customer

import com.hiel.commerce.infrastructure.persistence.jpa.BaseEntity
import com.hiel.commerce.infrastructure.persistence.jpa.user.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Audited
@Entity
@Table(name = "customers", schema = "commerce")
class CustomerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "name", nullable = false)
    val name: String,

    @OneToOne
    @JoinColumn(name = "user_id", columnDefinition = "id")
    val user: UserEntity,
): BaseEntity()
