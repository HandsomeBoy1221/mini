/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package com.ceshiren.mini.security;

//所有不进行security校验鉴权的API
//   /auth/login
public class ApiPath {
    public ApiPath() {
    }

    public enum GetApi {

        HEALTH_CHECK("/ping"),
        SWAGGER_API_V2_DOCS("/v2/api-docs"),
        SWAGGER_RESOURCE_CONFIGURATION("/swagger-resources/configuration/ui"),
        SWAGGER_RESOURCES("/swagger-resources"),
        SWAGGER_RESOURCES_SECURITY_CONFIGURATION("/swagger-resources/configuration/security"),
        SWAGGER_UI_HTML("swagger-ui.html"),
        SWAGGER_UI("/swagger-ui/**"),//swagger-ui/index.html
        SWAGGER_API_V3_DOCS("/v3/api-docs/**"),
        SWAGGER_CONFIGURATION("/configuration/**"),
        SWAGGER("/swagger*/**");

        private final String path;

        public String getPath() {
            return path;
        }

        GetApi(String path) {
            this.path = path;
        }
    }

    public enum PostApi{
        REGISTER("/auth/register"),
        LOGIN("/auth/login");
        private final String path;

        PostApi(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
