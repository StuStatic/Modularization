package com.example.stu.http.Constant

/**
 * Created by stu on 2021/1/25.
 *
 */
object HttpConstant {
    //代表客户端版本号
    const val AVS = "avs"
    //代表本次请求的会话，从reqid中取值
    const val SID = "sid"
    //代表本次请求的返回的素材id
    const val IE = "ie"
    //code代表当前step的结果码
    const val STEP_CD = "cd"
    //广告数据返回成功
    const val AD_DATA_SUCCESS = "200"
    //广告数据解析失败
    const val AD_DATA_FAILED = "202"
    //广告加载成功
    const val AD_PLAY_SUCCESS = "300"
    //广告加载失败
    const val AD_PLAY_FAILED = "301"
    //播放器&移动后台监控埋点日志地址
    const val ATM_MONITOR = "http://count.atm.youku.com/mlog"
    const val ATM_PRE = "val.atm.youku.com"

    /**
     * 广告服务器地址,待定
     */
    const val DISPLAY_AD_URL = "http://api.youku.com/image.php"

    /**
     * 埋点请求相关参数常量
     */
    enum class Params(val key: String, val value: String) {
        lvs("lvs", "4"), st("st", "12"), bt_phone("bt", "1"), bt_pad("bt", "0"), os(
            "os",
            "1"
        ),
        p("p", "2"), appid("appid", "xya"), ad_analize("sp", "2"), ad_load("sp", "3");

    }
}