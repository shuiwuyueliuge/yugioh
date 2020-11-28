package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.pegasus.application.CardDTO;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlHandler;
import lombok.SneakyThrows;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OurocgDataCardIterator implements Iterator {

    private int start;

    private boolean next;

    private HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler;

    private String cardUrl;

    public OurocgDataCardIterator(HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler) {
        this.start = 1;
        this.next = true;
        this.cardUrl = "https://www.ourocg.cn/card/list-5/";
        this.cardInfoHtmlHandler = cardInfoHtmlHandler;
    }

    @Override
    public boolean hasNext() {
        return this.next;
    }

    /**
     * {"cards":[{"id":"64025981","hash_id":"WMca6kmW","password":"64025981","name":"始原龙","name_ja":null,"name_en":null,"locale":"1","type_st":"怪兽|效果","type_val":"1","img_url":"https:\/\/p.ocgsoft.cn\/ygopro\/pics\/64025981.jpg","level":"4","attribute":"光","race":"龙","atk":"2000","def":"2000","pend_l":null,"pend_r":null,"link":null,"link_arrow":null,"name_nw":"始原龙","desc":"这个卡名的②③的效果1回合各能使用1次。\r\n①：这张卡的战斗发生的对对方的战斗伤害变成0。\r\n②：从自己墓地把1只龙族怪兽除外才能发动。这张卡的攻击力·守备力直到下个回合的结束时上升除外的怪兽的攻击力数值。\r\n③：这张卡被解放的场合，以自己场上1只表侧表示怪兽为对象才能发动。这个回合，那只怪兽在同1次的战斗阶段中可以作2次攻击。","desc_nw":"这个卡名的②③的效果1回合各能使用1次。\r\n①：这张卡的战斗发生的对对方的战斗伤害变成0。\r\n②：从自己墓地把1只龙族怪兽除外才能发动。这张卡的攻击力·守备力直到下个回合的结束时上升除外的怪兽的攻击力数值。\r\n③：这张卡被解放的场合，以自己场上1只表侧表示怪兽为对象才能发动。这个回合，那只怪兽在同1次的战斗阶段中可以作2次攻击。","rare":null,"package":null,"href":"https:\/\/www.ourocg.cn\/card\/WMca6kmW"},{"id":"63503850","hash_id":"YAco3MDP","password":"63503850","name":"蛮力攻击实施员","name_ja":null,"name_en":null,"locale":"1","type_st":"怪兽|效果|连接","type_val":"1","img_url":"https:\/\/p.ocgsoft.cn\/ygopro\/pics\/63503850.jpg","level":"2","attribute":"暗","race":"电子界","atk":"1600","def":"5","pend_l":null,"pend_r":null,"link":"2","link_arrow":"1,3","name_nw":"蛮力攻击实施员","desc":"效果怪兽2只\r\n这个卡名的效果1回合只能使用1次。\r\n①：丢弃1张手卡，以对方场上1张表侧表示的卡为对象才能发动。对方可以把原本种类（怪兽·魔法·陷阱）和那张表侧表示的卡相同的1张卡从手卡丢弃让这个效果无效。没丢弃的场合，作为对象的表侧表示的卡破坏。","desc_nw":"效果怪兽2只\r\n这个卡名的效果1回合只能使用1次。\r\n①：丢弃1张手卡，以对方场上1张表侧表示的卡为对象才能发动。对方可以把原本种类（怪兽·魔法·陷阱）和那张表侧表示的卡相同的1张卡从手卡丢弃让这个效果无效。没丢弃的场合，作为对象的表侧表示的卡破坏。","rare":null,"package":null,"href":"https:\/\/www.ourocg.cn\/card\/YAco3MDP"},{"id":"54529134","hash_id":"MoclDNVe","password":"54529134","name":"转生炎兽的超转生","name_ja":null,"name_en":null,"locale":"3","type_st":"魔法|速攻","type_val":"2","img_url":"https:\/\/p.ocgsoft.cn\/ygopro\/pics\/54529134.jpg","name_nw":"转生炎兽的超转生","desc":"这个卡名的卡在1回合只能发动1张。\r\n①：以自己场上1只「转生炎兽」连接怪兽为对象才能发动。只用那1只自己怪兽为素材把1只同名「转生炎兽」连接怪兽当作连接召唤从额外卡组特殊召唤。","desc_nw":"这个卡名的卡在1回合只能发动1张。\r\n①：以自己场上1只「转生炎兽」连接怪兽为对象才能发动。只用那1只自己怪兽为素材把1只同名「转生炎兽」连接怪兽当作连接召唤从额外卡组特殊召唤。","rare":null,"package":null,"href":"https:\/\/www.ourocg.cn\/card\/MoclDNVe"},{"id":"37984331","hash_id":"Zlce4nJg","password":"37984331","name":"真艾克佐迪亚","name_ja":null,"name_en":null,"locale":"1","type_st":"怪兽|效果","type_val":"1","img_url":"https:\/\/p.ocgsoft.cn\/ygopro\/pics\/37984331.jpg","level":"1","attribute":"暗","race":"魔法师","atk":"0","def":"0","pend_l":null,"pend_r":null,"link":null,"link_arrow":null,"name_nw":"真艾克佐迪亚","desc":"这张卡在怪兽区域存在，这张卡以外的双方场上的怪兽只有「被封印」通常怪兽4种类的场合，从这张卡的控制者来看的对方决斗胜利。","desc_nw":"这张卡在怪兽区域存在，这张卡以外的双方场上的怪兽只有「被封印」通常怪兽4种类的场合，从这张卡的控制者来看的对方决斗胜利。","rare":null,"package":null,"href":"https:\/\/www.ourocg.cn\/card\/Zlce4nJg"},{"id":"25538345","hash_id":"DYcWEE9Y","password":"25538345","name":"幻影骑士团 破洞鳞甲","name_ja":null,"name_en":null,"locale":"1","type_st":"怪兽|效果","type_val":"1","img_url":"https:\/\/p.ocgsoft.cn\/ygopro\/pics\/25538345.jpg","level":"3","attribute":"暗","race":"战士","atk":"600","def":"1600","pend_l":null,"pend_r":null,"link":null,"link_arrow":null,"name_nw":"幻影骑士团 破洞鳞甲","desc":"这个卡名的①②的效果1回合各能使用1次。\r\n①：丢弃1张手卡才能发动。从卡组把「幻影骑士团 破洞鳞甲」以外的1只「幻影骑士团」怪兽或者1张「幻影」魔法·陷阱卡送去墓地。\r\n②：这张卡在墓地存在，从自己墓地有这张卡以外的「幻影骑士团」怪兽或者「幻影」魔法·陷阱卡被除外的场合才能发动。这张卡特殊召唤。这个效果特殊召唤的这张卡从场上离开的场合除外。","desc_nw":"这个卡名的①②的效果1回合各能使用1次。\r\n①：丢弃1张手卡才能发动。从卡组把「幻影骑士团 破洞鳞甲」以外的1只「幻影骑士团」怪兽或者1张「幻影」魔法·陷阱卡送去墓地。\r\n②：这张卡在墓地存在，从自己墓地有这张卡以外的「幻影骑士团」怪兽或者「幻影」魔法·陷阱卡被除外的场合才能发动。这张卡特殊召唤。这个效果特殊召唤的这张卡从场上离开的场合除外。","rare":null,"package":null,"href":"https:\/\/www.ourocg.cn\/card\/DYcWEE9Y"},{"id":"12571621","hash_id":"pQcW1Px2","password":"12571621","name":"电脑堺豸-豸豸","name_ja":null,"name_en":null,"locale":"1","type_st":"怪兽|效果","type_val":"1","img_url":"https:\/\/p.ocgsoft.cn\/ygopro\/pics\/12571621.jpg","level":"3","attribute":"地","race":"幻龙","atk":"1000","def":"1600","pend_l":null,"pend_r":null,"link":null,"link_arrow":null,"name_nw":"电脑堺豸-豸豸","desc":"这个卡名的效果1回合只能使用1次。\r\n①：这张卡在手卡存在的场合，以自己场上1张「电脑堺」卡为对象才能发动。和那张卡种类（怪兽·魔法·陷阱）不同的1张「电脑堺」卡从卡组送去墓地，这张卡特殊召唤。这个回合的结束阶段，可以从自己墓地选「电脑堺豸-豸豸」以外的1只「电脑堺」怪兽加入手卡。这个回合，自己若非等级或者阶级是3以上的怪兽则不能特殊召唤。","desc_nw":"这个卡名的效果1回合只能使用1次。\r\n①：这张卡在手卡存在的场合，以自己场上1张「电脑堺」卡为对象才能发动。和那张卡种类（怪兽·魔法·陷阱）不同的1张「电脑堺」卡从卡组送去墓地，这张卡特殊召唤。这个回合的结束阶段，可以从自己墓地选「电脑堺豸-豸豸」以外的1只「电脑堺」怪兽加入手卡。这个回合，自己若非等级或者阶级是3以上的怪兽则不能特殊召唤。","rare":null,"package":null,"href":"https:\/\/www.ourocg.cn\/card\/pQcW1Px2"},{"id":"11010","hash_id":"wvsae9","password":null,"name":"背误射击","name_ja":"背誤射撃","name_en":null,"locale":"3","type_st":"陷阱|通常","type_val":"3","img_url":"https:\/\/p.ocgsoft.cn\/11010.jpg","name_nw":"背误射击","desc":"【条件】自己场上的魔法陷阱卡被对手的效果破坏时可以发动。\n【效果】选对手场上的1只表侧表示的效果怪兽（等级6以下）破坏。","desc_nw":"【条件】自己场上的魔法陷阱卡被对手的效果破坏时可以发动。\n【效果】选对手场上的1只表侧表示的效果怪兽（等级6以下）破坏。","rare":"N","package":"RD\/KP03-JP060,","href":"https:\/\/www.ourocg.cn\/card\/wvsae9"},{"id":"11009","hash_id":"9vsR9J","password":null,"name":"三重破3纪录","name_ja":"トリプル3","name_en":null,"locale":"3","type_st":"陷阱|通常","type_val":"3","img_url":"https:\/\/p.ocgsoft.cn\/11009.jpg","name_nw":"三重破3纪录","desc":"【条件】自己场上的表侧表示的通常怪兽（等级3）为3只的场合，对手怪兽的攻击宣言时可以发动。\n【效果】自己场上的全部表侧表示怪兽的攻击力、守备力直到回合结束时上升900。","desc_nw":"【条件】自己场上的表侧表示的通常怪兽（等级3）为3只的场合，对手怪兽的攻击宣言时可以发动。\n【效果】自己场上的全部表侧表示怪兽的攻击力、守备力直到回合结束时上升900。","rare":"N","package":"RD\/KP03-JP059,","href":"https:\/\/www.ourocg.cn\/card\/9vsR9J"},{"id":"11008","hash_id":"43s3Zb","password":null,"name":"强欲之大龟","name_ja":"強欲な大亀","name_en":null,"locale":"3","type_st":"陷阱|通常","type_val":"3","img_url":"https:\/\/p.ocgsoft.cn\/11008.jpg","name_nw":"强欲之大龟","desc":"【条件】对手召唤怪兽召唤时可以发动。\n【效果】自己抽2张。","desc_nw":"【条件】对手召唤怪兽召唤时可以发动。\n【效果】自己抽2张。","rare":"R","package":"RD\/KP03-JP058,","href":"https:\/\/www.ourocg.cn\/card\/43s3Zb"},{"id":"11007","hash_id":"o0sJl9","password":null,"name":"反击鸽心","name_ja":"カウンター・ハート","name_en":null,"locale":"3","type_st":"陷阱|通常","type_val":"3","img_url":"https:\/\/p.ocgsoft.cn\/11007.jpg","name_nw":"反击鸽心","desc":"【条件】自己的攻击表示怪兽（鸟兽族）受到攻击的对手怪兽的攻击宣言时可以发动。\n【效果】攻击过来的怪兽的攻击力直到回合结束时下降[攻击的怪兽的等级]×100。","desc_nw":"【条件】自己的攻击表示怪兽（鸟兽族）受到攻击的对手怪兽的攻击宣言时可以发动。\n【效果】攻击过来的怪兽的攻击力直到回合结束时下降[攻击的怪兽的等级]×100。","rare":"N","package":"RD\/KP03-JP057,","href":"https:\/\/www.ourocg.cn\/card\/o0sJl9"}],"meta":{"keyword":" ","count":11014,"total_page":1102,"cur_page":1,"suggest":{"text":"相关搜索：","keywords":[]},"title":"全部卡片","page_type":"list","list":"5"}}
     * cards 长度小于10说明是最后一页
     */
    @SneakyThrows
    @Override
    public List<CardDTO> next() {
        System.out.println(start);
        List<Map<String, String>> infos = cardInfoHtmlHandler.handle(this.cardUrl + start);
        List<CardDTO> dtoList = OurocgAdapter.getCardDTOList(infos);
        if (dtoList.size() < 10) {
            this.next = false;
        }

        this.start = start + 1;
        return dtoList;
    }
}
