package cn.mayu.yugioh.authorization.domain.user;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.Getter;
import lombok.ToString;
import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/29 11:11 上午
 */
@Getter
@ToString
public class UserInfoId extends ValueObject {

    private final String username;

    private final String id;

    public UserInfoId(String username) {
        this.username = username;
        this.id = createId(username);
    }

    private String createId(String username) {
        byte[] usernameBytes = username.getBytes(StandardCharsets.UTF_8);
        return byteToHexString(usernameBytes);
    }

    private String byteToHexString(byte[] bytes) {
        StringBuilder resultHexString = new StringBuilder();
        for (byte b: bytes) {
            String tempStr = Integer.toHexString(b & 0xFF);
            if (tempStr.length() == 1) {
                resultHexString.append(0).append(tempStr);
            } else {
                resultHexString.append(tempStr);
            }
        }

        return resultHexString.toString();
    }
}
