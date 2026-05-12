package com.fourbeat.presentation.model.group

data class GroupUiModel(
    val id: Long,
    val name: String,
    val code: String,
    val capacity: String,
) {
    companion object {
        fun getEmpty(id: Long): GroupUiModel =
            GroupUiModel(
                id = id,
                name = "",
                code = "",
                capacity = "",
            )
    }
}
