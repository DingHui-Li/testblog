package top.lidh.blog.Entity;

public class visitor {
    private String id;
    private String ip;
    private String adress;
    private String time;
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "visitor{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", adress='" + adress + '\'' +
                ", time='" + time + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
