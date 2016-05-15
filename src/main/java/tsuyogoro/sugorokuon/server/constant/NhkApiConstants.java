/**
 * Copyright (c)
 * 2016 Tsuyoyo. All Rights Reserved.
 */
package tsuyogoro.sugorokuon.server.constant;

public class NhkApiConstants {

    public enum Area {

        SAPPORO("010", "札幌"),
        HAKODATE("011", "函館"),
        ASAHIKAWA("012", "旭川"),
        OBIHIRO("013", "帯広"),
        KUSHIRO("014", "釧路"),
        KITAMI("015", "北見"),
        MURORAN("016", "室蘭"),
        AOMORI("020", "青森"),
        MORIOKA("030", "盛岡"),
        SENDAI("040", "仙台"),
        AKITA("050", "秋田"),
        YAMAGATA("060", "山形"),
        FUKUSHIMA("070", "福島"),
        MITO("080", "水戸"),
        UTSUNOMIYA("090", "宇都宮"),
        MAEBASHO("100", "前橋"),
        SAITAMA("110", "さいたま"),
        CHIBA("120", "千葉"),
        TOKYO("130", "東京"),
        YOKOHAMA("140", "横浜"),
        NIIGATA("150", "新潟"),
        TOYAMA("160", "富山"),
        KANAZAWA("170", "金沢"),
        FUKUI("180", "福井"),
        KOFU("190", "甲府"),
        NAGANO("200", "長野"),
        GIFU("210", "岐阜"),
        SHIZUOKA("220", "静岡"),
        NAGOYA("230", "名古屋"),
        TSU("240", "津"),
        OHTSU("250", "大津"),
        KYOTO("260", "京都"),
        OSAKA("270", "大阪"),
        KOBE("280", "神戸"),
        NARA("290", "奈良"),
        WKAYAMA("300", "和歌山"),
        TOTTORI("310", "鳥取"),
        MATSUE("320", "松江"),
        OKAYAMA("330", "岡山"),
        HIROSHIMA("340", "広島"),
        YAMAGUCHI("350", "山口"),
        TOKUSHIMA("360", "徳島"),
        TAKAMATSU("370", "高松"),
        MAATSUYAMA("380", "松山"),
        KOCHI("390", "高知"),
        FUKUOKA("400", "福岡"),
        KITAKYUSHU("401", "北九州"),
        SAGA("410", "佐賀"),
        NAGASAKI("420", "長崎"),
        KUMAMOTO("430", "熊本"),
        OITA("440", "大分"),
        MIYAZAKI("450", "宮崎"),
        KAGOSHIMA("460", "鹿児島"),
        OKINAWA("470", "沖縄")
        ;

        Area(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public String code;

        public String displayName;
    }

    public enum Service {

        NHK_1("r1", "NHKラジオ第1"),
        NHK_2("r2", "NHKラジオ第2"),
        NHK_FM("r3", "NHK FM")
        ;

        Service(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public String code;

        public String displayName;
    }

    // 番組詳細を取る必要が無いような番組のタイトル。番組情報取得のAPIが300回/日しか呼べないので、回数節約をする。
    public static final String[] UNNECESSARY_DETAIL_PROGRAMS = {
            "ニュース・天気予報",
            "おしらせ・天気予報",
            "ニュース",
            "天気予報",
            "天気予報・おしらせ",
            "交通情報",
            "交通情報・天気予報",
            "ニュース・天気予報・交通情報",
            "天気予報・おしらせ・交通情報",
            "ニュース・天気予報・海上気象・交通情報",
            "ニュース・天気予報・鉄道情報・航空情報・交通情報ほか",
            "天気予報・鉄道情報・交通情報",
            "緊急警報試験信号",
            "交通情報・海上予報",
            "天気予報・海上予報",
            "ニュ－ス・天気予報",
            "天気予報・海上予報・おしらせ",
            "ニュース・天気予報・おしらせ",
            "ニュース・天気予報・おしらせ",
            "交通情報・天気予報・おしらせ",
            "ニュース・天気予報・海上予報",
            "天気予報・交通情報",
            "天気予報・（お知らせ）",
            "天気予報・お知らせ",
            "交通情報・天気予報・お知らせ",
            "防災ボイス・天気予報・おしらせ",
            "ニュース・海上予報・天気予報・交通情報",
            "天気予報・海上予報・航行警報・おしらせ・交通情報",
            "ニュース・天気予報・あすの暦・航行警報",
            "きょうの動き・天気予報",
            "交通情報・ニュース・天気予報\n" +
                    "緊急警報試験信号　ラジオ第１",
            "ラジオ体操",
            "交通情報・海上気象・天気予報",
            "全国の天気・全国交通情報",
            "ＮＨＫきょうのニュース"
    };

}
