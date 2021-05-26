package cn.mayu.yugioh.ceres.port.adapter.datacenter.ourocg.model;

import lombok.Data;

import java.util.List;

@Data
public class Meta {

	private String keyword;
	
	private int count;
	
	private int totalPage;
	
	private int curPage;
	
	private String title;
	
	private String metaDesc;
	
	private String metaKw;

	private String pageType;

	private Suggest suggest;

	@Data
	public static class Suggest {

		private String text;

		private List<String> keyword;
	}
}
