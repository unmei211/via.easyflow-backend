package via.easyflow.core.tools.version.versionizer

import java.sql.Timestamp

interface IVersionizer<T> {
    fun go(): String
    fun goInstance(): Timestamp
}