package sample.model;

public class Pair {
    private String key;
    private String val;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public static Pair build(String key, String val) {
        Pair res = new Pair();
        res.setKey(key);
        res.setVal(val);

        return res;
    }
}
