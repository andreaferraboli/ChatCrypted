package program.connection;

import java.io.FileNotFoundException;

public interface MessageListener {
    public void onMessage(String fromLogin,String login, String msgBody) throws FileNotFoundException;
}
