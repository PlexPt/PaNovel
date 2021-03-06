package cc.aoeiuv020.panovel.api.site

import org.junit.Test

/**
 * Created by AoEiuV020 on 2018.06.07-13:32:00.
 */
class ZaiduduTest : BaseNovelContextText(Zaidudu::class) {
    @Test
    fun search() {
        search("都市")
        search("龙血战神", "风青阳", "31")
        search("校花的贴身高手", "鱼人二代", "133")
    }

    @Test
    fun detail() {
        detail("31", "31", "龙血战神", "风青阳",
                "http://www.zaidudu.net/files/article/image/0/31/31s.jpg",
                ">\n" +
                        "相传，远古时期的龙祭大陆，是太古神龙的天下，时至今日，神龙灭绝殆尽。\n" +
                        "龙辰，原本是一无是处的纨绔子弟，却因为父亲所遗留的神秘玉佩，吞食远古神龙之传承精血，以亿万龙族之血脉，坐拥美女，鏖战天下！\n" +
                        "破龙脉，凝神丹，武动天河星辰，身渡轮回万劫，任你修为通天彻地，实力霸绝寰宇，我自有远古十大祖龙精血，吞食宇宙天地，掌控无尽生灵，龙之传人，万古长存！",
                "2018-05-18 00:00:00")
        detail("133", "133", "校花的贴身高手", "鱼人二代",
                "http://www.zaidudu.net/modules/article/images/nocover.jpg",
                "《校花的贴身高手》\n" +
                        "一个大山里走出来的绝世高手，一块能预知未来的神秘玉佩……\n" +
                        "林逸是一名普通的高三学生，不过，他还有身负另外一个重任，那就是泡校花！而且还是奉校花老爸之命！\n" +
                        "虽然林逸很不想跟这位难伺候的大小姐打交道，但是长辈之命难违抗，他不得不千里迢迢的转学到了松山市第一高中，给大小姐鞍前马后的当跟班……于是，史上最牛逼的跟班出现了——大小姐的贴身高手！\n" +
                        "看这位跟班如何家致富偷小姐，开始他奉旨泡妞牛x闪闪的人生……\n" +
                        "本书有点儿纯……也有点儿小暧昧……...",
                "2018-06-06 00:00:00")
    }

    @Test
    fun chapters() {
        chapters("31", "第1章 龙形玉佩", "0/31/24682", null,
                "第393章 逆天速 度", "0/31/61590017", null,
                7443)
        chapters("133", "第01章 神奇的任务", "0/133/108834", null,
                "第7020章 单向通道", "0/133/61990904", null,
                7338)
    }

    @Test
    fun content() {
        content("0/31/24682",
                "“小妞，给大爷乐一个，要不大爷给你乐一个也成！”",
                "龙青澜翻了个身，骂道：“我安你祖宗十八代，老子还没死全，就想埋了我，小混蛋，我活过来就是要告诉你一件事，我丹田中有一块龙形玉佩，等我死后挖开我丹田，取出龙形玉佩，才能让我这一生彻底解脱……”",
                67)
        content("0/133/61990904",
                "“好吧，闲聊寒暄到此结束，我们进入正题！”",
                "只是不知道为什么，隐匿阵法被撤除，莫非其中有了什么变故？<",
                51)
    }

}