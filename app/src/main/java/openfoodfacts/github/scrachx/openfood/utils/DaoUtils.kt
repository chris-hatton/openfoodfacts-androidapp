package openfoodfacts.github.scrachx.openfood.utils

import android.util.Log
import androidx.annotation.CheckResult
import openfoodfacts.github.scrachx.openfood.repositories.Taxonomy
import org.greenrobot.greendao.AbstractDao
import org.greenrobot.greendao.query.QueryBuilder
import org.greenrobot.greendao.query.WhereCondition
import org.jetbrains.annotations.Contract

/**
 * Checks whether table is empty
 */
@Contract(pure = true)
@CheckResult
fun AbstractDao<*, *>.isEmpty() = count() == 0L

@Contract(pure = true)
fun <T> logDownload(taxonomy: Taxonomy<T>) {
    Log.i(
        "${Taxonomy::class.simpleName}",
        "Refreshed taxonomy '${taxonomy::class.simpleName}' from server"
    )
}

inline fun <T, R> AbstractDao<T, R>.build(builderAction: QueryBuilder<T>.() -> Unit): QueryBuilder<T> {
    return queryBuilder().apply(builderAction)
}

inline fun <T, R> AbstractDao<T, R>.unique(builderAction: QueryBuilder<T>.() -> Unit): T? {
    return build(builderAction).unique()
}

inline fun <T, R> AbstractDao<T, R>.list(builderAction: QueryBuilder<T>.() -> Unit = {}): List<T> {
    return build(builderAction).list()
}

fun <T> QueryBuilder<T>.whereOr(list: List<WhereCondition>) {
    when (list.size) {
        1 -> where(list[0])
        2 -> whereOr(list[0], list[1])
        else -> whereOr(list[0], list[1], *list.toTypedArray().copyOfRange(2, list.size))
    }
}