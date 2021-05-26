package cn.mayu.yugioh.ceres.port.adapter.datacenter.ourocg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OurocgMetaData {

	private List<OurocgCard> cards;
	
	private Meta meta;
}
