package cc.aoeiuv020.panovel.data

import android.content.Context
import cc.aoeiuv020.base.jar.ioExecutorService
import cc.aoeiuv020.panovel.download.DownloadListener
import cc.aoeiuv020.panovel.download.DownloadNotificationManager
import cc.aoeiuv020.panovel.report.Reporter
import cc.aoeiuv020.panovel.settings.GeneralSettings
import org.jetbrains.anko.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by AoEiuV020 on 2018.10.06-19:05:33.
 */
class DownloadManager(
        private val ctx: Context
) : AnkoLogger {
    fun download(novelManager: NovelManager, fromIndex: Int, count: Int, listener: DownloadListener? = null) {
        val novel = novelManager.novel
        val dfm = DownloadNotificationManager(ctx, novel)
        ctx.doAsync({ e ->
            val message = "下载失败，"
            Reporter.post(message, e)
            error(message, e)
            ctx.runOnUiThread {
                // 应该不需要通知dfm, 异常能抛到这里基本上下载还没开始，
                dfm.error(message, e)
                listener?.error(message, e)
            }
        }) {
            val chapters = novelManager.requestChapters(false)
            val cachedList = novelManager.novelContentsCached()
            val size = chapters.size
            val last = minOf(size - fromIndex, count) + fromIndex
            var exists = 0
            var downloads = 0
            var errors = 0
            val left = AtomicInteger(last - fromIndex)
            val nextIndex = AtomicInteger(fromIndex)
            val threadsLimit = GeneralSettings.downloadThreadsLimit
            debug {
                "download start <$fromIndex/$size> * $threadsLimit"
            }
            uiThread {
                dfm.downloadStart(left.get())
                listener?.downloadStart(left.get())
            }
            // 同时启动多个线程下载，
            // 判断一下，线程数不要过多，
            repeat(minOf(threadsLimit, left.get())) {
                ctx.doAsync({ e ->
                    val message = "线程下载异常，"
                    Reporter.post(message, e)
                    error(message, e)
                    ctx.runOnUiThread {
                        dfm.error(message, e)
                        listener?.error(message, e)
                    }
                }, ioExecutorService) {
                    // 每次循环最后再获取，
                    var index = nextIndex.getAndIncrement()
                    // 如果presenter已经detach说明离开了这个页面，不继续下载，
                    // 正在下载的章节不中断，
                    // 上面判断过，线程数不会过多，一进来index会小于size,
                    while (index < last) {
                        debug { "${Thread.currentThread().name} downloading $index" }
                        val chapter = chapters[index]
                        if (cachedList.contains(chapter.extra)) {
                            ++exists
                        } else {
                            try {
                                // 方法返回前请求到正文就已经缓存了，
                                novelManager.requestContent(chapter, false)
                                ++downloads
                            } catch (e: Exception) {
                                val message = "缓存<${novel.bookId}.$index>章节失败，"
                                Reporter.post(message, e)
                                error(message, e)
                                ++errors
                            }
                        }
                        val tmpLeft = left.decrementAndGet()
                        val tmpIndex = index
                        uiThread {
                            debug { "download $tmpIndex, left $tmpLeft" }
                            if (tmpLeft == 0) {
                                dfm.downloadCompletion(exists, downloads, errors)
                                listener?.downloadCompletion(exists, downloads, errors)
                            } else {
                                dfm.downloading(exists, downloads, errors, tmpLeft)
                                listener?.downloading(exists, downloads, errors, tmpLeft)
                            }
                        }
                        index = nextIndex.getAndIncrement()
                    }
                }
            }
        }
    }
}