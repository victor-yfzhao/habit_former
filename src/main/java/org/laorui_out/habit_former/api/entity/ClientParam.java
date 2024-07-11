package org.laorui_out.habit_former.api.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ClientParam {
    //获取token的源地址
    private static final String baseUrl="https://aip.baidubce.com/oauth/2.0/token";

    //获取凭证的id(APIKEY)
    private String clientID="Gii5F3jajJBZ6b3eJqrvRm8A";

    //获取凭证的密钥(SecretKEY)
    private String clientSecret="t2r9IO2aZ9kfgazlifXOhytAAYOijG7y";

    //大模型接口的访问地址
    private String modelUrl="https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/yi_34b_chat";

    //大模型的access-token
    private String accessToken="24.2041ef6103cb572b02a351b342aad324.2592000.1722395266.282335-89533141";

    //token的有效时间
    private long expiresIn=30;

    //token的有效期
    private Date expiredTime;

    //access-token是否有限（有没有过期）
    private boolean tokenValid = true;
}
