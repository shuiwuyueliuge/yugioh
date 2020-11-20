package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class VisitResponse {

	private int statusCode;

	private Map<String, String> headers;

	private String html;
}