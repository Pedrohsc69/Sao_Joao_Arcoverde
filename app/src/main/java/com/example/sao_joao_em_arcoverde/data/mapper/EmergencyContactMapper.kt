package com.example.sao_joao_em_arcoverde.data.mapper

import com.example.sao_joao_em_arcoverde.data.local.entity.EmergencyContactEntity
import com.example.sao_joao_em_arcoverde.data.model.EmergencyContact
import com.example.sao_joao_em_arcoverde.data.model.EmergencyContactType

fun EmergencyContactEntity.toModel(): EmergencyContact {
    return EmergencyContact(
        id = id,
        name = name,
        phone = phone,
        description = description,
        type = type.toEmergencyContactType()
    )
}

fun EmergencyContact.toEntity(): EmergencyContactEntity {
    return EmergencyContactEntity(
        id = id,
        name = name,
        phone = phone,
        description = description,
        type = type.name
    )
}

private fun String.toEmergencyContactType(): EmergencyContactType {
    return runCatching {
        EmergencyContactType.valueOf(this)
    }.getOrDefault(EmergencyContactType.OTHER)
}