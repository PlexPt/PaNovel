package cc.aoeiuv020.panovel.server

import android.content.Context
import cc.aoeiuv020.panovel.report.Reporter
import cc.aoeiuv020.panovel.server.service.NovelService
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.error

/**
 *
 * Created by AoEiuV020 on 2018.04.06-02:37:52.
 */
object UpdateManager : AnkoLogger {
    var novelService: NovelService? = null

    data class Notification(
            val id: Int,
            val title: String,
            val message: String,
            val time: Long?
    )

    fun downloadUpdate(ctx: Context, extra: String) {
        debug { "downloadUpdate $extra" }
        ctx.doAsync({ e ->
            val message = "更新通知解析失败,"
            Reporter.post(message, e)
            error(message, e)
        }) {
            //            TODO("接收极光更新通知，")
        }
/*
        Observable.create<Notification> { em ->
            val novelJson: String = gson.fromJson(extra, JsonObject::class.java)
                    .getAsJsonPrimitive("novel")
                    .asString
            val novel: Novel = novelJson.toBean()
            // 无视书架上没有的，
            val novelItem = Bookshelf.list().firstOrNull {
                it.requester.extra == novel.requesterExtra
                        && it.requester.type == novel.requesterType
            } ?: run {
                info { "已经不在书架上，${novel.requesterExtra}" }
                em.onComplete()
                return@create
            }
            val cachedChapters = Cache.chapters.get(novelItem)
            // 只对比长度，时间可空真的很麻烦，
            fun Pair<Date?, Int?>.newerThan(other: List<NovelChapter>): Boolean {
                return (second ?: 0 > other.size)
            }
            if (cachedChapters != null
                    && novel.run { updateTime to chaptersCount }.newerThan(cachedChapters)) {
                val novelContext = NovelContext.getNovelContextByUrl(novelItem.requester.url)
                // TODO: 这里考虑加入失败重试，毕竟只有一次机会，服务器知道了更新就不会推第二次，
                val detail = novelContext.getNovelDetail(novelItem.requester)
                val chapters = novelContext.getNovelChaptersAsc(detail.requester).also { Cache.chapters.put(novelItem, it) }
                if (chapters.run { last().update to size }.newerThan(cachedChapters)) {
                    em.onNext(Notification(
                            id = novelItem.hashCode(),
                            title = "《${novelItem.name}》有更新",
                            message = chapters.last().name,
                            time = novel.updateTime?.time
                    ))
                }
            }
            em.onComplete()
        }.async().subscribe({
            context.notify(it.id, it.message, it.title, time = it.time)
        }, { e ->
            error("更新通知解析失败,", e)
        })
*/
    }

/*
    fun query(requester: Requester): Novel? {
        debug { "query ：${requester.extra}" }
        val service = novelService ?: return null
        return try {
            val novel = Novel().also {
                it.requesterType = requester.type
                it.requesterExtra = requester.extra
            }
            service.query(novel)
        } catch (e: Exception) {
            error("查询失败，", e)
            null
        }

    }
*/

/*
    fun touch(requester: Requester, chaptersCount: Int, updateTime: Date? = null) {
        debug { "touch ：${requester.extra}" }
        novelService ?: return
        ctx.doAsync({ e ->
            val message = "更新通知解析失败,"
            Reporter.post(message, e)
            error(message, e)
        }) {

        }
        Observable.fromCallable {
            val novel = Novel().also {
                it.requesterType = requester.type
                it.requesterExtra = requester.extra
                it.chaptersCount = chaptersCount
                it.updateTime = updateTime
                it.modifyTime = Date()
            }
            novelService?.touch(novel) ?: false
        }.async().subscribe({
            debug { "上传无更新返回 $it: ${requester.extra}" }
        }, { e ->
            error("上传无更新失败，", e)
        })

    }
*/

/*
    fun uploadUpdate(requester: Requester, chaptersCount: Int, updateTime: Date? = null) {
        debug { "uploadUpdate ：${requester.extra}" }
        novelService ?: return
        Observable.fromCallable {
            val novel = Novel().also {
                it.requesterType = requester.type
                it.requesterExtra = requester.extra
                it.chaptersCount = chaptersCount
                it.updateTime = updateTime
            }
            novelService?.uploadUpdate(novel) ?: false
        }.async().subscribe({
            debug { "上传更新返回 $it: ${requester.extra}" }
        }, { e ->
            error("上传更新失败，", e)
        })
    }
*/

    fun create(context: Context) {
        debug { "create ${context.javaClass}" }
/*
        novelService?.let { return }
        Observable.fromCallable {
            if (BuildConfig.DEBUG && Log.isLoggable(loggerTag, Log.DEBUG)) {
                debug { "debug mode," }
                ServerAddress.getAndroidTest()
            } else {
                ServerAddress.getOnline()
            }
        }.async().subscribe({
            debug { "ServerAddress ${it.minVersion}: ${it.data}" }
            val currentVersionName = VersionUtil.getAppVersionName(context)
            if (VersionUtil.compare(it.minVersion, currentVersionName) > 0) {
                warn { "minVersion(${it.minVersion}) > currentVersion($currentVersionName)" }
            } else {
                novelService = NovelServiceImpl(it)
            }
        }, { e ->
            error("获取服务器信息失败, 尝试默认，", e)
            novelService = NovelServiceImpl(ServerAddress())
        })
*/
    }

    fun destroy(context: Context) {
        debug { "destroy ${context.javaClass}" }
        novelService = null
    }

}