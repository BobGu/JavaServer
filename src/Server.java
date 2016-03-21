public class Server {

    private int port;

    public Server() {
       this(5000);
    }

    public Server(int port) {
        setPort(port);
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
