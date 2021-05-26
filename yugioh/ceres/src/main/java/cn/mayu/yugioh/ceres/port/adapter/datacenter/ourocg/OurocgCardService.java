package cn.mayu.yugioh.ceres.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.ceres.application.dto.SyncCardResult;
import cn.mayu.yugioh.ceres.port.adapter.datacenter.ourocg.html.CardInfoHtmlHandler;
import cn.mayu.yugioh.ceres.port.adapter.datacenter.ourocg.html.IncludeInfoHandler;
import cn.mayu.yugioh.ceres.port.adapter.datacenter.ourocg.model.OurocgCard;
import cn.mayu.yugioh.ceres.port.adapter.datacenter.ourocg.model.OurocgMetaData;
import cn.mayu.yugioh.common.basic.html.HtmlHandler;
import cn.mayu.yugioh.common.basic.html.HtmlHandlerException;
import cn.mayu.yugioh.common.facade.minerva.model.CardCreateCommand;
import cn.mayu.yugioh.common.facade.minerva.model.CardInfo;
import cn.mayu.yugioh.common.redis.lock.RedisDistributedLock;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

@Slf4j
public class OurocgCardService {

    private OurocgMetaData ourocgMetaData;

    public Flux<SyncCardResult> searchCardList() {
        Lock lock = new RedisDistributedLock("ceres:ourocg");
        try {
            lock.lock();
            return Flux.create(t -> {
                int page = 0;
                int catchCount = 0;
                while (true) {
                    page++;
                    if (!Objects.isNull(ourocgMetaData)) {
                        int totalPage = ourocgMetaData.getMeta().getTotalPage();
                        if (totalPage <= page) {
                            break;
                        }
                    }

                    visitOurocgForCardList(page);
                    HtmlHandler<Map<String, Object>> includeHtmlHandler = new IncludeInfoHandler();
                    List<OurocgCard> OurocgCard = ourocgMetaData.getCards();
                    List<CardInfo> cardInfos = OurocgCard.stream().map(card -> {
                        String adjust = "";
                        List<String> includes = null;
                        try {
                            Map<String, Object> map = includeHtmlHandler.handle(card.getHref());
                            adjust = map.get("adjust") != null ? map.get("adjust").toString() : "";
                            List<Map<String, String>> includeInfos = (List<Map<String, String>>) map.get("includeInfos");
                            includes = includeInfos.stream().map(include -> String.format("%s|%s|%s|%s|%s",
                                    include.get("packName"), include.get("shortName"),
                                    include.get("number"), include.get("rare"), include.get("sellTime"))).collect(Collectors.toList());
                        } catch (HtmlHandlerException e) {
                        }

                        String typeVal = "";
                        if ("1".equals(card.getTypeVal())) {
                            typeVal = "怪兽";
                        }

                        if ("2".equals(card.getTypeVal())) {
                            typeVal = "魔法";
                        }

                        if ("3".equals(card.getTypeVal())) {
                            typeVal = "陷阱";
                        }

                        String linkArrow = card.getLinkArrow();
                        List<String> linkArrows = Lists.newArrayList();
                        if (linkArrow != null && (linkArrow.contains(",") || linkArrow.contains("，"))) {
                            linkArrow = linkArrow.replace("，", ",");
                            linkArrows = Lists.newArrayList(linkArrow.split(","));
                        }

                        String typeSt = card.getTypeSt();
                        List<String> typeSts = null;
                        if (typeSt.contains("|")) {
                            typeSt = typeSt.replace("怪兽|", "")
                                    .replace("魔法|", "")
                                    .replace("陷阱|", "");
                            typeSts = Lists.newArrayList(typeSt.split("\\|"));
                        }

                        CardInfo cardInfo = new CardInfo();
                        cardInfo.setName(card.getName());
                        cardInfo.setNameEn(card.getNameEn());
                        cardInfo.setNameJa(card.getNameJa());
                        cardInfo.setNameNw(card.getNameNw());
                        cardInfo.setPassword(card.getPassword());
                        cardInfo.setDesc(effectFormat(card.getDesc()));
                        cardInfo.setDescEn(effectFormat(card.getDescEn()));
                        cardInfo.setDescJa(effectFormat(card.getDescJa()));
                        cardInfo.setDescNw(effectFormat(card.getDescNw()));
                        cardInfo.setLevel(card.getLevel());
                        cardInfo.setAttribute(card.getAttribute());
                        cardInfo.setRace(card.getRace());
                        cardInfo.setAtk(card.getAtk());
                        cardInfo.setDef(card.getDef());
                        cardInfo.setPend(card.getPendL());
                        cardInfo.setLink(card.getLink());
                        cardInfo.setLinkArrow(linkArrows);
                        cardInfo.setImgUrl(card.getImgUrl());
                        cardInfo.setTypeSt(typeSts);
                        cardInfo.setInclude(includes);
                        cardInfo.setAdjust(adjust);
                        cardInfo.setTypeVal(typeVal);
                        return cardInfo;
                    }).collect(Collectors.toList());
                    catchCount += cardInfos.size();
                    t.next(new SyncCardResult(
                            new CardCreateCommand(cardInfos),
                            ourocgMetaData.getMeta().getCount(),
                            catchCount,
                            catchCount * 100 / ourocgMetaData.getMeta().getCount()
                    ));
                }

                t.complete();
            });
        } finally {
            lock.unlock();
        }
    }

    private String effectFormat(String str) {
        if (Objects.isNull(str)) {
            return "";
        }

        while (true) {
            int index = str.indexOf("@#");
            if (!str.contains("@#")) {
                break;
            }

            int nextIndex = str.indexOf("@", index + 3);
            String source = str.substring(index, nextIndex + 1);
            String target = str.substring(index + 2, nextIndex);
            str = str.replace(source, target);
        }

        return str;
    }

    private void visitOurocgForCardList(int page) {
        String cardList = "https://www.ourocg.cn/card/list-5/";
        try {
            String infos = new CardInfoHtmlHandler().handle(cardList + page);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            ourocgMetaData = mapper.readValue(infos, OurocgMetaData.class);
        } catch (Exception e) {
            log.error("visitOurocgForCardList error: ", e);
        }
    }
}