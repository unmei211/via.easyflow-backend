package via.easyflow.core.tools.uuid

import org.hibernate.validator.constraints.UUID
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun uuid(): String = Uuid.random().toString()
