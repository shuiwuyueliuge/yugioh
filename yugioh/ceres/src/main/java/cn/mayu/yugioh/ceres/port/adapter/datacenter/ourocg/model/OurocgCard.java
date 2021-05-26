package cn.mayu.yugioh.ceres.port.adapter.datacenter.ourocg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OurocgCard {

	// 卡片密码
	private String password;

	// 当前卡包的编号
	private String serial;

	// 中文名
	private String name;

	// 日文名
	private String nameJa;

	// 英文名
	private String nameEn;

	// nw名称
	private String nameNw;

	// 图片地址
	private String imgUrl;

	// 等级
	private String level;

	// 属性
	private String attribute;

	// 种族
	private String race;

	// 攻击力
	private String atk;

	// 防御力
	private String def;

	// 左刻度
	private String pendL;

	// 右刻度
	private String pendR;

	// 链接数
	private String link;

	// 效果
	private String desc;

	// 限制
	private String ban;

	// nw效果
	private String descNw;

	// 日文效果
	private String descJa;

	// 英文效果
	private String descEn;

	private String id;

	private String hashId;

	private String typeSt;

	private String linkArrow;

	private String rare;

	private String type;

	private String state;

	private String createdAt;

	private String updatedAt;

	private String deletedAt;

	private String rdType;

	private String effectKW;

	private String iCardType;

	private String sCardType;

	private List<String> keyword;

	private String locale;

	private String href;

	private String typeVal;
}
