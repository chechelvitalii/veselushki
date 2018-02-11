package top.veselushki.telegram.object;

public class InstantView {
    private static final String ICO = "\uD83C\uDF08";
    private static final String INSTANT_VIEW_PREFIX = "<a href=\"https://t.me/iv?url=http%3A%2F%2Fveselushki.top%2F";
    private static final String INSTANT_VIEW_SUFFIX = "%2F&rhash=9bb7ae35f0799e\">" + ICO + "</a>";

    public static String create(String pageName) {
        return INSTANT_VIEW_PREFIX + pageName + INSTANT_VIEW_SUFFIX;
    }
}
