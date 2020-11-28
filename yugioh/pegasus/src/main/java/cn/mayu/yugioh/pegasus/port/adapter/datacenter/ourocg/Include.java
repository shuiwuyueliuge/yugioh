package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Include {
	
	private String cardName;

	private List<IncludeInfo> includeInfos;
	
	private String adjust;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class IncludeInfo {

		private String rare;

		private String packName;

		private String number;

		private String shortName;

		private String sellTime;

		private String href;
	}
}
