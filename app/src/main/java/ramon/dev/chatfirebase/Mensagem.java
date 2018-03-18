package ramon.dev.chatfirebase;

/**
 * Created by ramondev on 3/18/18.
 */

public class Mensagem {
    private String id;
    private String msg;

    public Mensagem() {
    }

    public Mensagem(int id, String msg) {
        this.id = String.valueOf(id);
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
