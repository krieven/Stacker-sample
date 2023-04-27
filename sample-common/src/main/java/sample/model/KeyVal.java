package sample.model;

public class KeyVal {
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

    public static KeyVal build(String key, String val) {
        KeyVal res = new KeyVal();
        res.setKey(key);
        res.setVal(val);

        return res;
    }
}
