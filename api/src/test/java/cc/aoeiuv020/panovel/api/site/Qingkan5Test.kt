package cc.aoeiuv020.panovel.api.site

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by AoEiuV020 on 2018.06.06-17:10:41.
 */
class Qingkan5Test : BaseNovelContextText(Qingkan5::class) {
    init {
        enabled = false
    }

    @Test
    fun search() {
        search("都市")
        search("烽烟狼卷", "骨头渣子", "342")
        search("九幽天帝", "给力", "5518")
    }

    @Test
    fun detail() {
        detail("342", "342", "烽烟狼卷", "骨头渣子",
                "http://www.qingkan5.com/files/article/image/0/342/342s.jpg",
                "法属圭亚那刚刚归于平静，遥远欧洲大陆上又爆出了惊人的剧烈爆轰，波兰陆军在首都华沙被俄军完整占领后射出了不知道从哪里搞来的五枚短程战术导弹，就好似五颗从天外轰然下坠的流星，随着地面发出剧烈的跳动，大团的浓烟立时混合着无数的建筑碎片弥散在了暗夜的华沙城内。\n" +
                        "尽管只是最为普通的常规弹头，但是五颗总重量接近四吨的战斗部依旧是在俄军控制的区域内制造出了一大片的无人区，两百多名俄军士兵第一时间被冲击波碾碎在了狂暴气流中另外还有更多被飞溅碎片击中的伤员需要进行紧急的救治，完全无法进行有效拦截，短短两三分钟飞行时间让俄军各种野战防空武器只来得及发现根本没有机会做出进一步的反应。\n" +
                        "小半个华沙彻底被烟尘笼罩",
                "2018-05-23 00:00:00")
        detail("5518", "5518", "九幽天帝", "给力",
                "http://www.qingkan5.com/files/article/image/5/5518/5518s.jpg",
                "【2014星创奖第五季参赛作品】\n" +
                        "为夺有望冲击神境至宝万物之源，一代强者九幽大帝陨落，重生为一名叫石枫的少年。\n" +
                        "且看石枫如何凭借前世苏醒的记忆，扼杀各方天才，夺天地造化，踏着累累白骨，回归大帝之位！\n" +
                        "创了个书友群：14865773喜欢本书的朋友们可以加下！",
                "2018-06-06 00:00:00")
    }

    @Test
    fun chapters() {
        chapters("342", "精彩书评1", "0/342/187936", null,
                "2238 灰头土脸", "0/342/12233891", null,
                2153)
        chapters("5518", "1.第1章 大帝归来", "5/5518/2912686", null,
                "第2857章", "5/5518/12256389", null,
                2841)
    }

    @Test
    fun content() {
        content("0/342/12233891",
                "![img](http://www.qingkan5.com/files/article/attachment/0/342/12233891/18142.gif)",
                "![img](http://www.qingkan5.com/files/article/attachment/0/342/12233891/18142.gif)",
                1)
        // 好像只有这本书有大量没用的p标签，不处理，
        content("0/342/187936",
                "给骨哲大大提个醒——《烽烟狼卷》读后感</p>",
                "</p>",
                46)
        // 还有空正文的章节，还不少，这源不行啊，
        content("5/5518/2912686")?.size?.let {
            assertEquals(0, it)
        }
        content("26/26837/12257240",
                "“哈哈哈……”",
                "“妈的，我们可能真的暴露了，他们要跑。”开车的家伙骂了一声，脚下也猛踩油门。",
                15)
    }

}