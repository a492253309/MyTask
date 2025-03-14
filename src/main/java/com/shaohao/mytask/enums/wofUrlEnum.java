package com.shaohao.mytask.enums;


public enum wofUrlEnum {
    /**
     * World of fairy
     */
    Verify_User("GET", "https://www.worldoffairy.com/serve/auth/user-info-v2"),
//    Verify_User("GET", "https://www.worldoffairy.com/serve/auth/user-info"),
    Get_Goods("POST", "https://www.worldoffairy.com/serve/auth/farm/harvest"),
    Sell_Goods("POST", "https://www.worldoffairy.com/serve/auth/shop/sell"),
    Buy_Goods("POST", "https://www.worldoffairy.com/serve/auth/shop/buy"),
    Into_Goods("POST", "https://www.worldoffairy.com/serve/auth/farm/sow-v2"),
    Shop_Inventory("GET", "https://www.worldoffairy.com/serve/auth/shop/items"),
    Refresh_Shop("POST", "https://www.worldoffairy.com/serve/auth/shop/clear-limit"),
    Collect("POST","https://www.worldoffairy.com/serve/auth/ore/collect"),
    Build_Put("POST","https://www.worldoffairy.com/serve/auth/user/building-put"),
    Get_Building("GET","https://www.worldoffairy.com/serve/auth/user/building")
//            {
//    "code": 0,
//            "message": "ok",
//            "data": [
//                {
//                    "type": 1,
//                        "expired_at": 1693829189,
//                        "deposit": false,
//                        "list": [
//                    {
//                        "item_id": 2012,
//                            "count": 234
//                    }
//            ]
//                }
//    ]
//            }
    ;

    private String urlName;
    private String url;

    wofUrlEnum(String urlName, String url) {
        this.urlName = urlName;
        this.url = url;
    }

    public String getUrlName() {
        return urlName;
    }

    public String getUrl() {
        return url;
    }
}
